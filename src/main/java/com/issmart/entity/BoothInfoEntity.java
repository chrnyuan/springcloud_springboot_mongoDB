package com.issmart.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 展台属性信息
 * 
 * @author Administrator
 *
 */
@Document(collection = "BoothInfo")
public class BoothInfoEntity {
	
	/**
	 * id属性是给mongodb用的，用@Id注解修饰
	 */
    @Id
    private String id;
    /**
     * unitId
     */
    private String unitId;
    /**
     * boothId
     */
    private String boothId;
    /**
     * deviceMac
     */
    private String deviceMac;
    /**
     * 创建时间戳
     */
    private long createdTimeStamp;
    public long getCreatedTimeStamp() {
		return createdTimeStamp;
	}
	public void setCreatedTimeStamp(long createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}
	/**
     * 标签
     */
    private List<String> labelList;
    
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBoothId() {
		return boothId;
	}
	public void setBoothId(String boothId) {
		this.boothId = boothId;
	}
	public String getDeviceMac() {
		return deviceMac;
	}
	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}
	public List<String> getLabelList() {
		return labelList;
	}
	public void setLabelList(List<String> labelList) {
		this.labelList = labelList;
	}
}
