package com.issmart.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.issmart.entity.BoothInfoEntity;

@Repository
public interface BoothRepository extends MongoRepository<BoothInfoEntity, String> {
	 
	int deleteByUnitIdAndDeviceMac(String unitId,String deviceMac);
	
	List<BoothInfoEntity> findByUnitIdAndDeviceMac(String unitId,String deviceMac);
}
