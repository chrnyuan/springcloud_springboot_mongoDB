package com.issmart.entity;

import java.util.List;

public class RecommendInfoEntity {

	 /**
     * boothId
     */
    private String boothId;
    
    /**
     * deviceMacList
     */
    private List<String> deviceMacList;
    
    /**
     * deviceMac
     */
    private String deviceMac;
    
    /**
     * targetBeaconMac
     */
    private String targetBeaconMac;
    
    /**
     * targetBeaconId
     */
    private String targetBeaconId;
    
    /**
     * score
     */
    private double score;
    
	public RecommendInfoEntity() {
		super();
	}
	public RecommendInfoEntity(String boothId, List<String> deviceMacList) {
		super();
		this.boothId = boothId;
		this.deviceMacList = deviceMacList;
	}
	public RecommendInfoEntity(String targetBeaconMac, String targetBeaconId) {
		super();
		this.targetBeaconMac = targetBeaconMac;
		this.targetBeaconId = targetBeaconId;
	}
	public String getBoothId() {
		return boothId;
	}
	public void setBoothId(String boothId) {
		this.boothId = boothId;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public List<String> getDeviceMacList() {
		return deviceMacList;
	}
	public void setDeviceMacList(List<String> deviceMacList) {
		this.deviceMacList = deviceMacList;
	}
	public String getDeviceMac() {
		return deviceMac;
	}
	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}
	public String getTargetBeaconMac() {
		return targetBeaconMac;
	}
	public void setTargetBeaconMac(String targetBeaconMac) {
		this.targetBeaconMac = targetBeaconMac;
	}
	public String getTargetBeaconId() {
		return targetBeaconId;
	}
	public void setTargetBeaconId(String targetBeaconId) {
		this.targetBeaconId = targetBeaconId;
	}
	@Override
	public String toString() {
		return "RecommendInfoEntity [boothId=" + boothId + ", deviceMacList=" + deviceMacList + ", deviceMac="
				+ deviceMac + ", targetBeaconMac=" + targetBeaconMac + ", score=" + score + "]";
	}
}
