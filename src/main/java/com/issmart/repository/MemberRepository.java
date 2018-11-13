package com.issmart.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.issmart.entity.MemberInfoEntity;

@Repository
public interface MemberRepository extends MongoRepository<MemberInfoEntity, String> {
	 
	int deleteByUnitIdAndBeaconMac(String unitIid,String beaconMac);
	
	List<MemberInfoEntity> findByUnitIdAndBeaconMacNot(String unitIid,String beaconMac);
	
	List<MemberInfoEntity> findByUnitIdAndCreatedTimeStampGreaterThanEqual(String unitIid,long timestamp);
	
	MemberInfoEntity findByUnitIdAndBeaconMac(String unitIid,String beaconMac);
}
