package com.issmart.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户访问记录实体
 * 
 * @author Administrator
 *
 */
@Document(collection = "MemberVisit")
public class MemberVisitEntity {
	
	/**
	 * id属性是给mongodb用的，用@Id注解修饰
	 */
    @Id
    private String id;
    /**
     * logstash同步时间
     */
    private long timestamp;
    /**
     * unit
     */
    private String unitId;
    /**
     * beaconMac
     */
    private String beaconMac;
    /**
     * device_mac
     */
    private String deviceMac;
    /**
     * 切片开始时间
     */
    private long startTimeStamp;
    /**
     * 切片结束时间
     */
    private long endTimeStamp;
    
	public MemberVisitEntity(long timestamp, String unitId, String beaconMac, String deviceMac,
			long startTimeStamp, long endTimeStamp) {
		super();
		this.timestamp = timestamp;
		this.unitId = unitId;
		this.beaconMac = beaconMac;
		this.deviceMac = deviceMac;
		this.startTimeStamp = startTimeStamp;
		this.endTimeStamp = endTimeStamp;
	}
	public MemberVisitEntity() {
		super();
	}
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
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getBeaconMac() {
		return beaconMac;
	}
	public void setBeaconMac(String beaconMac) {
		this.beaconMac = beaconMac;
	}
	public String getDeviceMac() {
		return deviceMac;
	}
	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}
	public long getStartTimeStamp() {
		return startTimeStamp;
	}
	public void setStartTimeStamp(long startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}
	public long getEndTimeStamp() {
		return endTimeStamp;
	}
	public void setEndTimeStamp(long endTimeStamp) {
		this.endTimeStamp = endTimeStamp;
	}
}
