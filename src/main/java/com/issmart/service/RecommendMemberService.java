package com.issmart.service;

import org.springframework.stereotype.Service;

import com.issmart.entity.RecommendMemberCollectionEntity;

@Service
public interface RecommendMemberService{	
	
	/**
	 * 更新推荐集
	 * 
	 * @return
	 */
	public void updateRecommendCollection(String unitId,String beaconMac);
	
	/**
	 * 根据BeaconMac查询推荐信息
	 * 
	 * @return
	 */
	public RecommendMemberCollectionEntity findByUnitIdAndBeaconMac(String unitId,String beaconMac);
	
	/**
	 * 用户推荐信息冷启动
	 * 
	 * @param unitId
	 * @param beaconMac
	 * @return
	 */
	public RecommendMemberCollectionEntity opeRecommendCollection(String unitId,String beaconMac);
}
