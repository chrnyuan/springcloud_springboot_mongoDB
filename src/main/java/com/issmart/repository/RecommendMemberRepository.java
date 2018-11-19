package com.issmart.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.issmart.entity.RecommendMemberCollectionEntity;

@Repository
public interface RecommendMemberRepository extends MongoRepository<RecommendMemberCollectionEntity, String> {
	 
	int deleteByUnitIdAndBeaconMac(String unitId,String beaconMac);
	
	RecommendMemberCollectionEntity findByUnitIdAndBeaconMac(String unitId,String beaconMac);
}
