package com.issmart.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.issmart.entity.BoothInfoEntity;
import com.issmart.entity.MemberFeedBackEntity;
import com.issmart.entity.MemberFindLogEntity;
import com.issmart.entity.MemberInfoEntity;
import com.issmart.entity.MemberVisitEntity;
import com.issmart.entity.RecommendCollectionEntity;
import com.issmart.entity.RecommendInfoEntity;
import com.issmart.entity.ScoreBalanceEntity;
import com.issmart.entity.ScoreLabelEntity;
import com.issmart.entity.ScoreResultEntity;
import com.issmart.repository.BoothRepository;
import com.issmart.repository.MemberFeedBackRepository;
import com.issmart.repository.MemberFindLogRepository;
import com.issmart.repository.MemberRepository;
import com.issmart.repository.MemberVisitRepository;
import com.issmart.repository.RecommendRepository;
import com.issmart.service.RecommendService;
import com.issmart.util.ScoreEnum;
import com.issmart.util.ScoreUtil;

@Service
public class RecommendServiceImpl implements RecommendService {

	private static final Logger logger = LoggerFactory.getLogger(RecommendServiceImpl.class);

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private BoothRepository boothRepository;

	@Autowired
	private RecommendRepository recommendRepository;

	@Autowired
	private MemberVisitRepository memberVisitRepository;

	@Autowired
	private MemberFindLogRepository memberFindLogRepository;
	
	@Autowired
	private MemberFeedBackRepository memberFeedBackRepository;

	@Override
	public int firstStart() {
		if (recommendRepository.count() == 0) {
			List<MemberInfoEntity> memberInfoEntityList = memberRepository.findAll();
			List<BoothInfoEntity> boothInfoEntityList = boothRepository.findAll();
			for (MemberInfoEntity memberInfoEntity : memberInfoEntityList) {
				RecommendCollectionEntity recommendCollectionEntity = new RecommendCollectionEntity();
				recommendCollectionEntity.setBeaconMac(memberInfoEntity.getBeaconMac());
				opeRecommendCollection(recommendCollectionEntity, boothInfoEntityList);
				logger.info("推荐系统冷启动成功");
			}
		}
		return 1;
	}

	/**
	 * 组装每个beaconMac的冷启动推荐信息
	 * 
	 * @param recommendCollectionEntity
	 * @param boothInfoEntityList
	 * @return
	 */
	public RecommendCollectionEntity opeRecommendCollection(RecommendCollectionEntity recommendCollectionEntity,
			List<BoothInfoEntity> boothInfoEntityList) {
		MemberInfoEntity memberInfoEntity = memberRepository.findByBeaconMac(recommendCollectionEntity.getBeaconMac());
		List<RecommendInfoEntity> recommendInfoList = new ArrayList<>();
		for (BoothInfoEntity boothInfoEntity : boothInfoEntityList) {
			List<String> deviceMacList = new ArrayList<String>();
			deviceMacList.add(boothInfoEntity.getDeviceMac());
			RecommendInfoEntity recommendInfoEntity = new RecommendInfoEntity(boothInfoEntity.getBoothId(),
					deviceMacList);
			int score = 0;
			for (String boothLabel : boothInfoEntity.getLabelList()) {
				for (String memberLabel : memberInfoEntity.getLabelList()) {
					if (boothLabel.equals(memberLabel)) {
						score += 1;
					}
				}
			}
			recommendInfoEntity.setScore(score);
			recommendInfoList.add(recommendInfoEntity);
		}
		recommendCollectionEntity.setRecommendInfoList(recommendInfoList);
		logger.info(recommendCollectionEntity.getBeaconMac() + "更新列表");
		recommendRepository.deleteByBeaconMac(recommendCollectionEntity.getBeaconMac());
		recommendCollectionEntity.setCreatedTimeStamp(System.currentTimeMillis());
		return recommendRepository.insert(recommendCollectionEntity);
	}

	/**
	 * 查询推荐信息
	 */
	@Override
	public RecommendCollectionEntity findByBeaconMac(String beaconMac) {
		return recommendRepository.findByBeaconMac(beaconMac);
	}

	/**
	 * 异步更新推荐列表 异步不能在当前类中调用
	 */
	@Async
	@Override
	public void updateRecommendCollection(String beaconMac) {
		// 获取推荐集
		RecommendCollectionEntity recommendCollectionEntity = recommendRepository.findByBeaconMac(beaconMac);
		// 查询出所有的展会信息
		List<BoothInfoEntity> boothInfoEntityList = boothRepository.findAll();
		// 获取上一次查询时间戳
		long timeStamp = 1000000000000l;
		MemberFindLogEntity memberFindLogEntity = memberFindLogRepository.findByBeaconMac(beaconMac);
		if (memberFindLogEntity != null) {
			timeStamp = memberFindLogEntity.getTimeStamp();
		} else {
			memberFindLogEntity = new MemberFindLogEntity();
			memberFindLogEntity.setBeaconMac(beaconMac);
		}
		// 处理visit行为数据
		List<ScoreBalanceEntity> memberVisitScoreBalanceList = opeMemberVisitData(beaconMac, timeStamp,
				boothInfoEntityList);
		// 获取分数平衡数据
		List<ScoreBalanceEntity> scoreBalanceList = opeScoreBalanceList(memberVisitScoreBalanceList);
		// 平衡数据计算反馈数据生成新的平衡数据
		opeMemberFeedBackData(beaconMac,timeStamp,scoreBalanceList);
		/**
		 * 没有新的行为数据产生
		 */
		if (scoreBalanceList.size() == 0) {
			logger.info("没有新的行为数据产生：");
			return ;
		}
		// 处理以获取booth应加分数
		List<ScoreResultEntity> scoreResultEntityList = opeScoreBalance(scoreBalanceList, recommendCollectionEntity, boothInfoEntityList);
		// 更新推荐列表
		opeGetNewRecommend(recommendCollectionEntity,scoreResultEntityList);
		recommendRepository.deleteByBeaconMac(recommendCollectionEntity.getBeaconMac());
		recommendCollectionEntity.setCreatedTimeStamp(System.currentTimeMillis());
		logger.info("更新推荐列表结果："+recommendRepository.insert(recommendCollectionEntity));
		// 更新查询访问日志
		memberFindLogEntity.setTimeStamp(System.currentTimeMillis());
		memberFindLogRepository.deleteByBeaconMac(beaconMac);
		memberFindLogRepository.insert(memberFindLogEntity);
	}

	/**
	 * 更新列表，计算用户访问行为因素 计算得出每个deviceMac在timeStamp中所应减的分数及相应label应得的分数
	 * 
	 * @param recommendCollectionEntity
	 */
	private List<ScoreBalanceEntity> opeMemberVisitData(String beaconMac, long timeStamp,
			List<BoothInfoEntity> boothInfoEntityList) {
		List<ScoreBalanceEntity> visitScoreBalanceList = new ArrayList<>();
		List<MemberVisitEntity> MemberVisitEntityList = memberVisitRepository
				.findByBeaconMacAndTimestampGreaterThanEqual(beaconMac, timeStamp);
		// 没有访问行为
		if (MemberVisitEntityList.size() == 0) {
			return visitScoreBalanceList;
		}
		for (BoothInfoEntity boothInfoEntity : boothInfoEntityList) {
			double score = 0;
			for (MemberVisitEntity memberVisitEntity : MemberVisitEntityList) {
				if (memberVisitEntity.getDeviceMac().equals(boothInfoEntity.getDeviceMac())) {
					if ((System.currentTimeMillis() - memberVisitEntity.getTimestamp()) < 300000) {
						// 5分钟以内
						score += ScoreEnum.VISITINFIVE.getValue();
					} else if ((System.currentTimeMillis() - memberVisitEntity.getTimestamp()) > 600000) {
						// 大于10分钟以内
						score += ScoreEnum.VISITOUTTEN.getValue();
					} else {
						// 大于5分钟小于10分钟
						score += ScoreEnum.VISITINFIVEANDTEN.getValue();
					}
				}
			}
			if (score != 0) {
				ScoreBalanceEntity scoreBalanceEntity = new ScoreBalanceEntity(boothInfoEntity.getDeviceMac());
				scoreBalanceEntity.setScore(score);
				scoreBalanceEntity.setLabelScore(score);
				visitScoreBalanceList.add(scoreBalanceEntity);
			}
		}
		return visitScoreBalanceList;
	}
	
	/**
	 * 更新列表，计算用户反馈行为数据
	 * 
	 * @param recommendCollectionEntity
	 */
	private void opeMemberFeedBackData(String beaconMac, long timestamp,List<ScoreBalanceEntity> scoreBalanceList) {
		// 查询反馈数据
		List<MemberFeedBackEntity> feedBackList = memberFeedBackRepository.findByBeaconMacAndTimestampGreaterThanEqual(beaconMac, timestamp);
		// 没有反馈行为
		if (feedBackList.size() == 0) return ;
		for (MemberFeedBackEntity memberFeedBackEntity : feedBackList) {
			double score = 0;
			boolean flag = true;
			// 平衡数据集中存在此反馈deviceMac
			for (ScoreBalanceEntity scoreBalanceEntity : scoreBalanceList) {
				if (memberFeedBackEntity.getDeviceMac().equals(scoreBalanceEntity.getDeviceMac())) {
					flag = false;
					if (ScoreUtil.DISLIKE.equals(memberFeedBackEntity.getFeedBackType())) {
						score += ScoreEnum.DISLIKE.getValue();
						scoreBalanceEntity.setExistDisLikeBehavior(true);
					} else {
						score += ScoreEnum.LIKE.getValue();
						scoreBalanceEntity.setExistLikeBehavior(true);
					}
				}
				if (score != 0) {
					// 累加标签分数
					scoreBalanceEntity.setLabelScore(scoreBalanceEntity.getLabelScore() + score);
				}
			}
			// 平衡数据集中不存在此反馈deviceMac
			if (flag) {
				ScoreBalanceEntity scoreBalanceEntity = new ScoreBalanceEntity();
				if (ScoreUtil.DISLIKE.equals(memberFeedBackEntity.getFeedBackType())) {
					score += ScoreEnum.DISLIKE.getValue();
					scoreBalanceEntity.setExistDisLikeBehavior(true);
				} else {
					score += ScoreEnum.LIKE.getValue();
					scoreBalanceEntity.setExistLikeBehavior(true);
				}
				scoreBalanceEntity.setDeviceMac(memberFeedBackEntity.getDeviceMac());
				scoreBalanceEntity.setLabelScore(score);
				scoreBalanceList.add(scoreBalanceEntity);
			}
		}
	}

	/**
	 * 处理以获得最终分数平衡数据
	 */
	private List<ScoreBalanceEntity> opeScoreBalanceList(List<ScoreBalanceEntity> memberVisitScoreBalanceList) {
		if (memberVisitScoreBalanceList.size() != 0) {
			for (ScoreBalanceEntity scoreBalanceEntity : memberVisitScoreBalanceList) {
				scoreBalanceEntity.setExistVisitBehavior(true);
			}
		}
		return memberVisitScoreBalanceList;
	}

	/**
	 * 处理以获取visit行为booth应加分数
	 * 
	 * @return
	 */
	private List<ScoreResultEntity> opeScoreBalance(List<ScoreBalanceEntity> scoreBalanceList,
			RecommendCollectionEntity recommendCollectionEntity, List<BoothInfoEntity> boothInfoEntityList) {
		/*
		 * 获取每个标签应加分数
		 */
		List<ScoreLabelEntity> scoreLabelEntityLlist = new ArrayList<>();
		for (ScoreBalanceEntity scoreBalanceEntity : scoreBalanceList) {
			for (BoothInfoEntity boothInfoEntity : boothInfoEntityList) {
				if (scoreBalanceEntity.getDeviceMac().equals(boothInfoEntity.getDeviceMac())) {
					for (String labelId : boothInfoEntity.getLabelList()) {
						ScoreLabelEntity scoreLabelEntity = getScoreLabelEntity(scoreLabelEntityLlist, labelId);
						scoreLabelEntity.setScore(scoreLabelEntity.getScore()
								+ scoreBalanceEntity.getLabelScore() / boothInfoEntity.getLabelList().size());
						scoreLabelEntityLlist.contains(scoreLabelEntity);
					}
				}
			}
		}
		/*
		 * 获取最终每个booth应加分数
		 */
		List<ScoreResultEntity> scoreResultEntityList = new ArrayList<>();
		for (BoothInfoEntity boothInfoEntity : boothInfoEntityList) {
			double score = 0;
			// 遍历当前展台标签根据标签加上分数
			for (String labelId : boothInfoEntity.getLabelList()) {
				for (ScoreLabelEntity scoreLabelEntity : scoreLabelEntityLlist) {
					if (labelId.equals(scoreLabelEntity.getLabelId())) {
						score += scoreLabelEntity.getScore();
					}
				}
			}
			boolean resetBehaviorFlag = false; 
			// 根据是否存在相应行为处理分数
			for (ScoreBalanceEntity scoreBalanceEntity : scoreBalanceList) {
				if(scoreBalanceEntity.getDeviceMac().equals(boothInfoEntity.getDeviceMac())) {
					// 如果存在dislike行为,分数重置
					if (scoreBalanceEntity.isExistDisLikeBehavior()) {
						resetBehaviorFlag = true;
						break;
					}
					// 如果存在like行为,分数重置
					if (scoreBalanceEntity.isExistLikeBehavior()) {
						resetBehaviorFlag = true;
						break;
					}
					// 如果存在press行为,分数重置
					if (scoreBalanceEntity.isExistPressBehavior()) {
						resetBehaviorFlag = true;
						break;
					}
					score = 0;
					score = score - scoreBalanceEntity.getScore();
				}
			}
			if(score != 0 || resetBehaviorFlag) {
				ScoreResultEntity scoreResultEntity = getScoreResultEntity(scoreResultEntityList,boothInfoEntity.getBoothId()); 
				scoreResultEntity.setResetBehavior(resetBehaviorFlag);
				scoreResultEntity.setScore(score);
			}
		}
		return scoreResultEntityList;
	}
	/**
	 * 获取最新的推荐列表
	 * @param recommendCollectionEntity
	 */
	private void opeGetNewRecommend(RecommendCollectionEntity recommendCollectionEntity,List<ScoreResultEntity> scoreResultEntityList) {
		List<RecommendInfoEntity> RecommendInfoList = recommendCollectionEntity.getRecommendInfoList();
		for (RecommendInfoEntity recommendInfoEntity : RecommendInfoList) {
			for (ScoreResultEntity scoreResultEntity : scoreResultEntityList) {
				if (recommendInfoEntity.getBoothId().equals(scoreResultEntity.getBoothId())) {
					if (scoreResultEntity.isResetBehavior()) {
						recommendInfoEntity.setScore(ScoreEnum.RESETBEHAVIOR.getValue());
					} else {
						double score = recommendInfoEntity.getScore() + scoreResultEntity.getScore();
						if(score < ScoreEnum.RESETBEHAVIOR.getValue()) {
							recommendInfoEntity.setScore(ScoreEnum.RESETBEHAVIOR.getValue());
						} else {
							recommendInfoEntity.setScore(score);
						}
					}
				}
			}
		}
	}

	/**
	 * 判断是否已存在对象，存在则返回，否则则新建
	 * 
	 * @return
	 */
	private ScoreLabelEntity getScoreLabelEntity(List<ScoreLabelEntity> scoreLabelEntityList, String labelId) {
		for (ScoreLabelEntity scoreLabelEntity : scoreLabelEntityList) {
			if (labelId.equals(scoreLabelEntity.getLabelId())) {
				return scoreLabelEntity;
			}
		}
		ScoreLabelEntity scoreLabelEntity = new ScoreLabelEntity(labelId);
		scoreLabelEntityList.add(scoreLabelEntity);
		return scoreLabelEntity;
	}
	
	/**
	 * 判断是否已存在对象，存在则返回，否则则新建
	 * 
	 * @return
	 */
	private ScoreResultEntity getScoreResultEntity(List<ScoreResultEntity> scoreResultEntityList, String boothId) {
		for (ScoreResultEntity scoreResultEntity : scoreResultEntityList) {
			if (boothId.equals(scoreResultEntity.getBoothId())) {
				return scoreResultEntity;
			}
		}
		ScoreResultEntity scoreResultEntity = new ScoreResultEntity(boothId);
		scoreResultEntityList.add(scoreResultEntity);
		return scoreResultEntity;
	}
}
