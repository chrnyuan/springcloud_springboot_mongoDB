package com.issmart.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.issmart.entity.RecommendCollectionEntity;

@Repository
public interface RecommendRepository extends MongoRepository<RecommendCollectionEntity, String> {
	 
	int deleteByBeaconMac(String beaconMac);
	
	RecommendCollectionEntity findByBeaconMac(String beaconMac);
}
