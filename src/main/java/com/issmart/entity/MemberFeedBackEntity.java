package com.issmart.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户反馈实体
 * 
 * @author Administrator
 *
 */
@Document(collection = "MemberFeedBack")
public class MemberFeedBackEntity {
	
	/**
	 * id属性是给mongodb用的，用@Id注解修饰
	 */
    @Id
    private String id;
    /**
     * 反馈行为时间
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
     * deviceMac
     */
    private String deviceMac;
    
    /**
     * 反馈类型（like:喜欢，dislike:不喜欢）
     */
    private String feedBackType;
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
	public String getDeviceMac() {
		return deviceMac;
	}
	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}
	public String getFeedBackType() {
		return feedBackType;
	}
	public void setFeedBackType(String feedBackType) {
		this.feedBackType = feedBackType;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
}
