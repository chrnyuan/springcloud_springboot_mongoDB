package com.issmart.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户贴一贴行为实体
 * 
 * @author Administrator
 *
 */
@Document(collection = "MemberStick")
public class MemberStickEntity {
	
	/**
	 * id属性是给mongodb用的，用@Id注解修饰
	 */
    @Id
    private String id;
    /**
     * 贴一贴行为时间
     */
    private long timestamp;
    /**
     * unitId
     */
    private String unitId;
    /**
     * beaconMac
     */
    private String beaconMac;
    
    /**
     * 目标BeaconMac
     */
    private String targetBeaconMac;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getBeaconMac() {
		return beaconMac;
	}
	public void setBeaconMac(String beaconMac) {
		this.beaconMac = beaconMac;
	}
	public String getTargetBeaconMac() {
		return targetBeaconMac;
	}
	public void setTargetBeaconMac(String targetBeaconMac) {
		this.targetBeaconMac = targetBeaconMac;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
}
