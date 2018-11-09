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
	@Override
	public String toString() {
		return "RecommendInfoEntity [boothId=" + boothId + ", deviceMacList=" + deviceMacList + ", score=" + score
				+ ", getBoothId()=" + getBoothId() + ", getScore()=" + getScore() + ", getDeviceMacList()="
				+ getDeviceMacList() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
