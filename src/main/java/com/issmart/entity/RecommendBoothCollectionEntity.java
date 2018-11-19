package com.issmart.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 推荐集
 * 
 * @author Administrator
 *
 */
@Document(collection = "RecommendBoothCollection")
public class RecommendBoothCollectionEntity {
	
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
   
    private List<RecommendInfoEntity> RecommendInfoList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBeaconMac() {
		return beaconMac;
	}

	public void setBeaconMac(String beaconMac) {
		this.beaconMac = beaconMac;
	}

	public List<RecommendInfoEntity> getRecommendInfoList() {
		return RecommendInfoList;
	}

	public void setRecommendInfoList(List<RecommendInfoEntity> recommendInfoList) {
		RecommendInfoList = recommendInfoList;
	}

	public long getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(long createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
}
