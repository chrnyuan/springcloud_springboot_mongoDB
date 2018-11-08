package com.issmart.service;

import org.springframework.stereotype.Service;

import com.issmart.entity.MemberFeedBackEntity;
import com.issmart.entity.MemberInfoEntity;
import com.issmart.entity.MemberPressEntity;
import com.issmart.entity.MemberStickEntity;
import com.issmart.entity.MemberVisitEntity;

@Service
public interface MemberService{
	
	/**
	 * 保存用户信息
	 * 
	 * @return
	 */
	public MemberInfoEntity insert(MemberInfoEntity memberInfoEntity);
	
	/**
	 * 新建用户访问记录
	 * 
	 * @return
	 */
	public MemberVisitEntity insertVisit(MemberVisitEntity memberVisitEntity);
	
	/**
	 * 新建用户反馈行为
	 * 
	 * @return
	 */
	public MemberFeedBackEntity insertFeedBack(MemberFeedBackEntity memberFeedBackEntity);
	
	/**
	 * 新建用户按一按行为
	 * 
	 * @return
	 */
	public MemberPressEntity insertPress(MemberPressEntity memberPressEntity);

	/**
	 * 新建用户贴一贴行为
	 * 
	 * @return
	 */
	public MemberStickEntity insertStick(MemberStickEntity memberStickEntity);

}
