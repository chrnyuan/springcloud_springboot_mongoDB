package com.issmart.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 人员属性信息
 * 
 * @author Administrator
 *
 */
@Document(collection = "MemberInfo")
public class MemberInfoEntity {

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
     * beaconMac
     */
    private String beaconMac;
    /**
     * 创建时间戳
     */
    private long createdTimeStamp;
    /**
     * 标签
     */
    private List<String> labelList;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public List<String> getLabelList() {
		return labelList;
	}
	public void setLabelList(List<String> labelList) {
		this.labelList = labelList;
	}
	public long getCreatedTimeStamp() {
		return createdTimeStamp;
	}
	public void setCreatedTimeStamp(long createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}
}
