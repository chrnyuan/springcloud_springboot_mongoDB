package com.issmart.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.issmart.entity.MemberInfoEntity;

@Repository
public interface MemberRepository extends MongoRepository<MemberInfoEntity, String> {
	 
	int deleteByUnitIdAndBeaconMac(String unitIid,String beaconMac);
	
	MemberInfoEntity findByUnitIdAndBeaconMac(String unitIid,String beaconMac);
}
