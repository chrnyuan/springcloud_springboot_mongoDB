package com.issmart.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.issmart.entity.RecommendBoothCollectionEntity;

@Repository
public interface RecommendRepository extends MongoRepository<RecommendBoothCollectionEntity, String> {
	 
	int deleteByUnitIdAndBeaconMac(String unitId,String beaconMac);
	
	RecommendBoothCollectionEntity findByUnitIdAndBeaconMac(String unitId,String beaconMac);
}
