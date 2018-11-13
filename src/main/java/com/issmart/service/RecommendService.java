package com.issmart.service;

import org.springframework.stereotype.Service;

import com.issmart.entity.RecommendBoothCollectionEntity;

@Service
public interface RecommendService{
	
	/**
	 * 冷启动设置推荐集
	 * 
	 * @return
	 */
	public int firstStart();
	
	
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
	public RecommendBoothCollectionEntity findByUnitIdAndBeaconMac(String unitId,String beaconMac);
}
