package com.issmart.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.issmart.entity.MemberFeedBackEntity;
import com.issmart.entity.MemberFindLogEntity;
import com.issmart.entity.MemberInfoEntity;
import com.issmart.entity.MemberStickEntity;
import com.issmart.entity.RecommendInfoEntity;
import com.issmart.entity.RecommendMemberCollectionEntity;
import com.issmart.entity.ScoreBalanceEntity;
import com.issmart.entity.ScoreLabelEntity;
import com.issmart.entity.ScoreResultEntity;
import com.issmart.repository.MemberFeedBackRepository;
import com.issmart.repository.MemberFindLogRepository;
import com.issmart.repository.MemberRepository;
import com.issmart.repository.MemberStickRepository;
import com.issmart.repository.RecommendMemberRepository;
import com.issmart.service.RecommendMemberService;
import com.issmart.util.ScoreEnum;
import com.issmart.util.ScoreUtil;
import com.issmart.util.StringUtil;

@Service
public class RecommendMemberServiceImpl implements RecommendMemberService {

	private static final Logger logger = LoggerFactory.getLogger(RecommendMemberServiceImpl.class);

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MemberStickRepository memberStickRepository;

	@Autowired
	private RecommendMemberRepository recommendMemberRepository;

	@Autowired
	private MemberFindLogRepository memberFindLogRepository;
	
	@Autowired
	private MemberFeedBackRepository memberFeedBackRepository;

	/**
	 * 查询推荐信息
	 */
	@Override
	public RecommendMemberCollectionEntity findByUnitIdAndBeaconMac(String unitId,String beaconMac) {
		RecommendMemberCollectionEntity recommendMemberCollectionEntity =  recommendMemberRepository.findByUnitIdAndBeaconMac(unitId,beaconMac);
		if(recommendMemberCollectionEntity == null) {
			recommendMemberCollectionEntity = opeRecommendMemberCollection(unitId,beaconMac);
		}
		return recommendMemberCollectionEntity;
	}
	
	/**
	 * 推荐人员信息冷启动
	 * 
	 * @param recommendCollectionEntity
	 * @param boothInfoEntityList
	 * @return
	 */
	private RecommendMemberCollectionEntity opeRecommendMemberCollection(String unitId,String beaconMac) {
		// 新建推荐对象
		RecommendMemberCollectionEntity recommendMemberCollectionEntity = new RecommendMemberCollectionEntity();
		// 推荐集
		List<RecommendInfoEntity> recommendInfoList = new ArrayList<>();
		// 获取当前所有人员信息
		List<MemberInfoEntity> memberInfoEntityList = memberRepository.findByUnitIdAndBeaconMacNot(unitId,beaconMac);
		// 获取当前人员信息
		MemberInfoEntity memberInfoEntity = memberRepository.findByUnitIdAndBeaconMac(unitId,beaconMac);
		for (MemberInfoEntity memberInfoEntityParam : memberInfoEntityList) {
			RecommendInfoEntity recommendInfoEntity = new RecommendInfoEntity(memberInfoEntityParam.getBeaconMac(),memberInfoEntityParam.getBeaconId());
			int score = 0;
			for (String paramLabel : memberInfoEntityParam.getLabelList()) {
				for (String memberLabel : memberInfoEntity.getLabelList()) {
					if (paramLabel.equals(memberLabel)) {
						score += 1;
					}
				}
			}
			recommendInfoEntity.setScore(score);
			recommendInfoList.add(recommendInfoEntity);
		}
		logger.info(recommendMemberCollectionEntity.getBeaconMac() + "冷启动");
		recommendMemberCollectionEntity.setUnitId(unitId);
		recommendMemberCollectionEntity.setBeaconMac(beaconMac);
		recommendMemberCollectionEntity.setCreatedTimeStamp(System.currentTimeMillis());
		recommendMemberCollectionEntity.setRecommendInfoList(recommendInfoList);
		recommendMemberRepository.deleteByUnitIdAndBeaconMac(recommendMemberCollectionEntity.getUnitId(),recommendMemberCollectionEntity.getBeaconMac());
		return recommendMemberRepository.insert(recommendMemberCollectionEntity);
	}

	/**
	 * 推荐人员信息冷启动
	 * 
	 * @param recommendCollectionEntity
	 * @param boothInfoEntityList
	 * @return
	 */
	@Async
	@Override
	public RecommendMemberCollectionEntity opeRecommendCollection(String unitId,String beaconMac) {
		// 新建推荐对象
		RecommendMemberCollectionEntity recommendMemberCollectionEntity = new RecommendMemberCollectionEntity();
		// 推荐集
		List<RecommendInfoEntity> recommendInfoList = new ArrayList<>();
		// 获取当前所有人员信息
		List<MemberInfoEntity> memberInfoEntityList = memberRepository.findByUnitIdAndBeaconMacNot(unitId,beaconMac);
		// 获取当前人员信息
		MemberInfoEntity memberInfoEntity = memberRepository.findByUnitIdAndBeaconMac(unitId,beaconMac);
		for (MemberInfoEntity memberInfoEntityParam : memberInfoEntityList) {
			RecommendInfoEntity recommendInfoEntity = new RecommendInfoEntity(memberInfoEntityParam.getBeaconMac(),memberInfoEntityParam.getBeaconId());
			int score = 0;
			for (String paramLabel : memberInfoEntityParam.getLabelList()) {
				for (String memberLabel : memberInfoEntity.getLabelList()) {
					if (paramLabel.equals(memberLabel)) {
						score += 1;
					}
				}
			}
			recommendInfoEntity.setScore(score);
			recommendInfoList.add(recommendInfoEntity);
		}
		// 如果推荐结果大于100,则只保存前100的推荐结果
//		if(recommendInfoList.size() > 100) {
//			// 按得分倒序排序
//			Collections.sort(recommendInfoList, new Comparator<RecommendInfoEntity>() {
//				@Override
//				public int compare(RecommendInfoEntity o1, RecommendInfoEntity o2) {
//					if (o1.getScore() < o2.getScore()) {
//						return 1;
//					}
//					if (o1.getScore() == o2.getScore()) {
//						return 0;
//					}
//					return -1;
//				}
//			});
//			recommendInfoList = recommendInfoList.subList(0, 100);
//		}
		logger.info(recommendMemberCollectionEntity.getBeaconMac() + "保存用户生成推荐列表");
		recommendMemberCollectionEntity.setUnitId(unitId);
		recommendMemberCollectionEntity.setBeaconMac(beaconMac);
		recommendMemberCollectionEntity.setRecommendInfoList(recommendInfoList);
		recommendMemberCollectionEntity.setCreatedTimeStamp(System.currentTimeMillis());
		recommendMemberRepository.deleteByUnitIdAndBeaconMac(recommendMemberCollectionEntity.getUnitId(),recommendMemberCollectionEntity.getBeaconMac());
		return recommendMemberRepository.insert(recommendMemberCollectionEntity);
	}

	/**
	 * 异步更新用户推荐列表 异步不能在当前类中调用
	 */
	@Async
	@Override
	public void updateRecommendCollection(String unitId,String beaconMac) {
		// 获取当前所有人员信息
		List<MemberInfoEntity> memberInfoEntityList = memberRepository.findByUnitIdAndBeaconMacNot(unitId,beaconMac);
		// 获取推荐集
		RecommendMemberCollectionEntity recommendMemberCollectionEntity = recommendMemberRepository.findByUnitIdAndBeaconMac(unitId,beaconMac);
		// 获取上一次查询时间戳
		long timeStamp = 1000000000000l;
		List<MemberFindLogEntity> memberFindLogEntityList = memberFindLogRepository.findByUnitIdAndBeaconMacAndRecommendType(unitId,beaconMac,StringUtil.RECOMMEND_MEMBER);
		MemberFindLogEntity memberFindLogEntity = null;
		if (memberFindLogEntityList != null && memberFindLogEntityList.size() != 0) {
			Collections.sort(memberFindLogEntityList, new Comparator<MemberFindLogEntity>() {
				@Override
				public int compare(MemberFindLogEntity o1, MemberFindLogEntity o2) {
					if (o1.getTimeStamp() < o2.getTimeStamp()) {
						return 1;
					}
					if (o1.getTimeStamp() == o2.getTimeStamp()) {
						return 0;
					}
					return -1;
				}
			});
			memberFindLogEntity = memberFindLogEntityList.get(0);
			timeStamp = memberFindLogEntity.getTimeStamp();
		} else {
			memberFindLogEntity = new MemberFindLogEntity();
			memberFindLogEntity.setUnitId(unitId);
			memberFindLogEntity.setRecommendType(StringUtil.RECOMMEND_MEMBER);
			memberFindLogEntity.setBeaconMac(beaconMac);
		}
		// 平衡数据计算反馈数据生成新的平衡数据
		List<ScoreBalanceEntity> scoreBalanceList = opeMemberFeedBackData(unitId,beaconMac,timeStamp);
		// 平衡数据计算贴一贴数据生成新的平衡数据
		opeMemberStickData(unitId,beaconMac,timeStamp,scoreBalanceList);
		/**
		 * 没有新的行为数据产生
		 */
		if (scoreBalanceList.size() == 0) {
			logger.info("计算推荐用户数据没有新的行为数据产生：");
			return ;
		}
		// 处理以获取最终应加分数
		List<ScoreResultEntity> scoreResultEntityList = opeScoreResult(scoreBalanceList, recommendMemberCollectionEntity, memberInfoEntityList);
		// 更新推荐列表
		opeGetNewRecommend(recommendMemberCollectionEntity,scoreResultEntityList);
		recommendMemberRepository.deleteByUnitIdAndBeaconMac(unitId,beaconMac);
		recommendMemberCollectionEntity.setCreatedTimeStamp(System.currentTimeMillis());
		logger.info("更新用户推荐列表结果："+recommendMemberRepository.insert(recommendMemberCollectionEntity));
		// 更新查询访问日志时间戳
		memberFindLogEntity.setTimeStamp(System.currentTimeMillis());
		memberFindLogRepository.deleteAllByUnitIdAndBeaconMacAndRecommendType(unitId,beaconMac,StringUtil.RECOMMEND_MEMBER);
		memberFindLogRepository.insert(memberFindLogEntity);
	}
	
	/**
	 * 更新列表，计算用户反馈行为数据
	 * 
	 * @param recommendCollectionEntity
	 */
	private List<ScoreBalanceEntity> opeMemberFeedBackData(String unitId,String beaconMac, long timestamp) {
		List<ScoreBalanceEntity> scoreBalanceList = new ArrayList<>();
		// 查询反馈数据
		List<MemberFeedBackEntity> feedBackList = memberFeedBackRepository.findByUnitIdAndBeaconMacAndTimestampGreaterThanEqual(unitId,beaconMac, timestamp);
		// 没有反馈行为
		if (feedBackList.size() == 0) return scoreBalanceList;
		for (MemberFeedBackEntity memberFeedBackEntity : feedBackList) {
			double score = 0;
			boolean flag = true;
			// 平衡数据集中存在此反馈
			for (ScoreBalanceEntity scoreBalanceEntity : scoreBalanceList) {
				if (memberFeedBackEntity.getTargetBeaconMac().equals(scoreBalanceEntity.getTargetBeaconMac())) {
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
			// 平衡数据集中不存在此反馈
			if (flag) {
				ScoreBalanceEntity scoreBalanceEntity = new ScoreBalanceEntity();
				if (ScoreUtil.DISLIKE.equals(memberFeedBackEntity.getFeedBackType())) {
					score += ScoreEnum.DISLIKE.getValue();
					scoreBalanceEntity.setExistDisLikeBehavior(true);
				} else {
					score += ScoreEnum.LIKE.getValue();
					scoreBalanceEntity.setExistLikeBehavior(true);
				}
				scoreBalanceEntity.setTargetBeaconMac(memberFeedBackEntity.getTargetBeaconMac());
				scoreBalanceEntity.setLabelScore(score);
				scoreBalanceList.add(scoreBalanceEntity);
			}
		}
		return scoreBalanceList;
	}
	
	/**
	 * 更新列表，计算用户贴一贴行为数据
	 * 
	 * @param recommendCollectionEntity
	 */
	private void opeMemberStickData(String unitId,String beaconMac, long timestamp,List<ScoreBalanceEntity> scoreBalanceList) {
		// 查询贴一贴数据
		List<MemberStickEntity> stickList = memberStickRepository.findByUnitIdAndBeaconMacAndTimestampGreaterThanEqual(unitId,beaconMac, timestamp);
		// 没有贴一贴行为
		if (stickList.size() == 0) return ;
		for (MemberStickEntity memberStickEntity : stickList) {
			double score = 0;
			boolean flag = true;
			// 平衡数据集中存在此反馈deviceMac
			for (ScoreBalanceEntity scoreBalanceEntity : scoreBalanceList) {
				if (memberStickEntity.getTargetBeaconMac().equals(scoreBalanceEntity.getTargetBeaconMac())) {
					flag = false;
					// 存在贴一贴行为
					scoreBalanceEntity.setExistStickBehavior(true);
					if ((System.currentTimeMillis() - memberStickEntity.getTimestamp()) < 300000) {
						// 5分钟以内
						score += ScoreEnum.STICKINFIVE.getValue();
					} else if ((System.currentTimeMillis() - memberStickEntity.getTimestamp()) > 600000) {
						// 大于10分钟
						score += ScoreEnum.STICKOUTTEN.getValue();
					} else {
						// 大于5分钟小于10分钟
						score += ScoreEnum.STICKINFIVEANDTEN.getValue();
					}
				}
				if (score != 0) {
					// 累加标签分数
					scoreBalanceEntity.setLabelScore(scoreBalanceEntity.getLabelScore() + score);
				}
			}
			// 平衡数据集中不存在此贴一贴TargetBeaconMac
			if (flag) {
				ScoreBalanceEntity scoreBalanceEntity = new ScoreBalanceEntity();
				scoreBalanceEntity.setExistStickBehavior(true);
				if ((System.currentTimeMillis() - memberStickEntity.getTimestamp()) < 300000) {
					// 5分钟以内
					score += ScoreEnum.STICKINFIVE.getValue();
				} else if ((System.currentTimeMillis() - memberStickEntity.getTimestamp()) > 600000) {
					// 大于10分钟
					score += ScoreEnum.STICKOUTTEN.getValue();
				} else {
					// 大于5分钟小于10分钟
					score += ScoreEnum.STICKINFIVEANDTEN.getValue();
				}
				scoreBalanceEntity.setTargetBeaconMac(memberStickEntity.getTargetBeaconMac());
				scoreBalanceEntity.setLabelScore(score);
				scoreBalanceList.add(scoreBalanceEntity);
			}
		}
	}

	/**
	 * 处理以获取最终应加分数
	 * 
	 * @return
	 */
	private List<ScoreResultEntity> opeScoreResult(List<ScoreBalanceEntity> scoreBalanceList,
			RecommendMemberCollectionEntity recommendMemberCollectionEntity,List<MemberInfoEntity> memberInfoEntityList) {
		/*
		 * 获取每个标签应加分数
		 */
		List<ScoreLabelEntity> scoreLabelEntityLlist = new ArrayList<>();
		for (ScoreBalanceEntity scoreBalanceEntity : scoreBalanceList) {
			for (MemberInfoEntity memberInfoEntity : memberInfoEntityList) {
				if (scoreBalanceEntity.getTargetBeaconMac().equals(memberInfoEntity.getBeaconMac())) {
					for (String labelId : memberInfoEntity.getLabelList()) {
						ScoreLabelEntity scoreLabelEntity = getScoreLabelEntity(scoreLabelEntityLlist, labelId);
						// 将标签总得分数平均分配到每一个标签中
						scoreLabelEntity.setScore(scoreLabelEntity.getScore()
								+ scoreBalanceEntity.getLabelScore() / memberInfoEntity.getLabelList().size());
						scoreLabelEntityLlist.contains(scoreLabelEntity);
					}
				}
			}
		}
		/*
		 * 获取最终应加分数
		 */
		List<ScoreResultEntity> scoreResultEntityList = new ArrayList<>();
		for (MemberInfoEntity memberInfoEntity : memberInfoEntityList) {
			double score = 0;
			// 遍历当前展台标签根据标签加上分数
			for (String labelId : memberInfoEntity.getLabelList()) {
				for (ScoreLabelEntity scoreLabelEntity : scoreLabelEntityLlist) {
					if (labelId.equals(scoreLabelEntity.getLabelId())) {
						score += scoreLabelEntity.getScore();
					}
				}
			}
			boolean resetBehaviorFlag = false; 
			// 根据是否存在相应行为处理分数
			for (ScoreBalanceEntity scoreBalanceEntity : scoreBalanceList) {
				if(scoreBalanceEntity.getTargetBeaconMac().equals(memberInfoEntity.getBeaconMac())) {
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
				ScoreResultEntity scoreResultEntity = getScoreResultEntity(scoreResultEntityList,memberInfoEntity.getBeaconMac()); 
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
	private void opeGetNewRecommend(RecommendMemberCollectionEntity recommendMemberCollectionEntity,List<ScoreResultEntity> scoreResultEntityList) {
		List<RecommendInfoEntity> RecommendInfoList = recommendMemberCollectionEntity.getRecommendInfoList();
		for (RecommendInfoEntity recommendInfoEntity : RecommendInfoList) {
			for (ScoreResultEntity scoreResultEntity : scoreResultEntityList) {
				if (recommendInfoEntity.getTargetBeaconMac().equals(scoreResultEntity.getTargetBeaconMac())) {
					if (scoreResultEntity.isResetBehavior()) {
						recommendInfoEntity.setScore(ScoreEnum.RESETBEHAVIOR.getValue());
					} else {
						double score = recommendInfoEntity.getScore() + scoreResultEntity.getScore();
						if(score < ScoreEnum.RESETLABELBEHAVIOR.getValue()) {
							if(recommendInfoEntity.getScore() <= ScoreEnum.RESETLABELBEHAVIOR.getValue()) {
								recommendInfoEntity.setScore(recommendInfoEntity.getScore()+ScoreEnum.RESETLABELREREATBEHAVIOR.getValue());
							} else {
								recommendInfoEntity.setScore(ScoreEnum.RESETLABELBEHAVIOR.getValue());	
							}
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
	private ScoreResultEntity getScoreResultEntity(List<ScoreResultEntity> scoreResultEntityList, String beaconMac) {
		for (ScoreResultEntity scoreResultEntity : scoreResultEntityList) {
			if (beaconMac.equals(scoreResultEntity.getTargetBeaconMac())) {
				return scoreResultEntity;
			}
		}
		ScoreResultEntity scoreResultEntity = new ScoreResultEntity();
		scoreResultEntity.setTargetBeaconMac(beaconMac);
		scoreResultEntityList.add(scoreResultEntity);
		return scoreResultEntity;
	}
}
