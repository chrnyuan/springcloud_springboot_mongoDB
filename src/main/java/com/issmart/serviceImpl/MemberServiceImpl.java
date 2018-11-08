package com.issmart.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.issmart.entity.MemberFeedBackEntity;
import com.issmart.entity.MemberInfoEntity;
import com.issmart.entity.MemberVisitEntity;
import com.issmart.repository.MemberFeedBackRepository;
import com.issmart.repository.MemberRepository;
import com.issmart.repository.MemberVisitRepository;
import com.issmart.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private MemberVisitRepository memberVisitRepository;
	
	@Autowired
	private MemberFeedBackRepository memberFeedBackRepository;

	@Override
	public MemberInfoEntity insert(MemberInfoEntity memberInfoEntity) {
		memberRepository.deleteByBeaconMac(memberInfoEntity.getBeaconMac());
		memberInfoEntity.setCreatedTimeStamp(System.currentTimeMillis());
		MemberInfoEntity memberInfoEntityResult = memberRepository.insert(memberInfoEntity);
		logger.info("更新展台"+memberInfoEntityResult.getBeaconMac());
		return memberInfoEntityResult;
	}

	@Async
	@Override
	public MemberVisitEntity insertVisit(MemberVisitEntity memberVisitEntity) {
		logger.info("新建一条用户访问记录"+memberVisitEntity.getBeaconMac());
		return memberVisitRepository.insert(memberVisitEntity);
	}

	@Override
	public MemberFeedBackEntity insertFeedBack(MemberFeedBackEntity memberLikeFeedBackEntity) {
		logger.info("新建一条用户反馈数据"+memberLikeFeedBackEntity.getBeaconMac());
		return memberFeedBackRepository.insert(memberLikeFeedBackEntity);
	}
}
