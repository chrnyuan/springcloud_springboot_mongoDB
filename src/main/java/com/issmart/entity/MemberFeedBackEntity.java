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
     * targetBeaconMac
     */
    private String targetBeaconMac;
    
    /**
     * 反馈类型（like:喜欢，dislike:不喜欢）
     */
    private String feedBackType;
    
    /**
     * 反馈类型（on:刷新，off:不刷新）
     */
    private String refreshType;
    
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
	public String getRefreshType() {
		return refreshType;
	}
	public void setRefreshType(String refreshType) {
		this.refreshType = refreshType;
	}
}
