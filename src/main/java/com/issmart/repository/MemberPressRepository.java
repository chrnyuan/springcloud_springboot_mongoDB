package com.issmart.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.issmart.entity.MemberPressEntity;

@Repository
public interface MemberPressRepository extends MongoRepository<MemberPressEntity, String> {
	
	/**
	 * 根据BeaconMac查询大于timestamp的数据
	 * 
	 * @param beaconMac
	 * @return
	 */
	List<MemberPressEntity> findByUnitIdAndBeaconMacAndTimestampGreaterThanEqual(String unitId,String beaconMac, long timestamp);
}
