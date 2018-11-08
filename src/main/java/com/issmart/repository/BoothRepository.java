package com.issmart.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.issmart.entity.BoothInfoEntity;

@Repository
public interface BoothRepository extends MongoRepository<BoothInfoEntity, String> {
	 
	int deleteByDeviceMac(String deviceMac);
	
	List<BoothInfoEntity> findByDeviceMac(String deviceMac);
}
