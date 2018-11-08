package com.issmart.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.issmart.entity.MemberVisitEntity;

@Repository
public interface MemberVisitRepository extends MongoRepository<MemberVisitEntity, String> {
	 
	/**
	 * 根据BeaconMac查询访问数据
	 * 
	 * @param beaconMac
	 * @return
	 */
	List<MemberVisitEntity> findByUnitIdAndBeaconMac(String unitId,String beaconMac);
	
	/**
	 * 根据BeaconMac查询大于timestamp的访问数据
	 * 
	 * @param beaconMac
	 * @return
	 */
	List<MemberVisitEntity> findByUnitIdAndBeaconMacAndTimestampGreaterThanEqual(String unitId,String beaconMac,long timestamp);
	
	/**
	 * 统计当前BeaconMac中大于timestamp的访问数据条数
	 * 
	 * @param beaconMac
	 * @return
	 */
	long countByBeaconMacAndTimestampGreaterThanEqual(String beaconMac,long timestamp);
	
	/**
	 * 统计当前BeaconMac的访问数据条数
	 * 
	 * @param beaconMac
	 * @return
	 */
	long countByBeaconMac(String beaconMac);
	
	/**
	 * 统计当前BeaconMac中访问DeviceMac并大于等于timestamp的访问数据条数
	 * 
	 * @param beaconMac
	 * @return
	 */
	long countByBeaconMacAndDeviceMacAndTimestampGreaterThanEqual(String beaconMac,String deviceMac,long timestamp);
	
	long countByTimestamp(long timestamp);
	
	long countByBeaconMacAndTimestamp(String beaconMac,long timestamp);
	
	long countByTimestampGreaterThan(long timestamp);
}
