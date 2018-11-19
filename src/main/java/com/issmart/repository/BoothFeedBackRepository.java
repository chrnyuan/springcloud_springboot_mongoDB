package com.issmart.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.issmart.entity.BoothFeedBackEntity;

@Repository
public interface BoothFeedBackRepository extends MongoRepository<BoothFeedBackEntity, String> {
	
	/**
	 * 根据BeaconMac查询大于timestamp的数据
	 * 
	 * @param beaconMac
	 * @return
	 */
	List<BoothFeedBackEntity> findByUnitIdAndBeaconMacAndTimestampGreaterThanEqual(String unitId,String beaconMac, long timestamp);
}
