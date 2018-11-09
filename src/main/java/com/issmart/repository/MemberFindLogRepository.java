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
	 
	int deleteAllByUnitIdAndBeaconMac(String unitId,String beaconMac);
	
	List<MemberFindLogEntity> findByUnitIdAndBeaconMac(String unitId,String beaconMac);
}
