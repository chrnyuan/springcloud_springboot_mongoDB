package com.issmart.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 从application.yml中获取连接配置文件
 * 
 * @author Administrator
 *
 */
@Configuration
@ConfigurationProperties(prefix="spring.data.mongodb")
public class MongoDBConfig {
	private String host;
	private Integer port;
	private String dataBase;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getDataBase() {
		return dataBase;
	}
	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}
}
