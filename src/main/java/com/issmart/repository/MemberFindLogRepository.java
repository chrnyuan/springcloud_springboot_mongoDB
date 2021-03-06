package com.issmart.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.issmart.entity.MemberFindLogEntity;

/**
 * 用户访问日志
 * 
 * @author Administrator
 *
 */
@Repository
public interface MemberFindLogRepository extends MongoRepository<MemberFindLogEntity, String> {
	 
	int deleteAllByUnitIdAndBeaconMacAndRecommendType(String unitId,String beaconMac,String recommendType);
	
	List<MemberFindLogEntity> findByUnitIdAndBeaconMacAndRecommendType(String unitId,String beaconMac,String recommendType);
}
