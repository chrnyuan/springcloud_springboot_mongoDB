package com.issmart.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户查询日志信息
 * 
 * @author Administrator
 *
 */
@Document(collection = "MemberFindLog")
public class MemberFindLogEntity {

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
	 * deviceMac
	 */
	private String beaconMac;
	/**
	 * 上一次查询时间戳
	 */
	private long timeStamp;

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

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
}
