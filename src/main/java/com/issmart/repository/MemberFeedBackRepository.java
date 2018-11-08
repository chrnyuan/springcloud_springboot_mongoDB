package com.issmart.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.issmart.entity.MemberFeedBackEntity;

@Repository
public interface MemberFeedBackRepository extends MongoRepository<MemberFeedBackEntity, String> {
	
	/**
	 * 根据BeaconMac查询大于timestamp的数据
	 * 
	 * @param beaconMac
	 * @return
	 */
	List<MemberFeedBackEntity> findByBeaconMacAndTimestampGreaterThanEqual(String beaconMac, long timestamp);
}
