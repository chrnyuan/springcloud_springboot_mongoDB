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
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoDBConfig {
	private String host;
	private Integer port;
	private String dataBase;
	private String replicaSet;
	private Integer minConnectionsPerHost = 10;
	private Integer connectionsPerHost = 2;

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

	public String getReplicaSet() {
		return replicaSet;
	}

	public void setReplicaSet(String replicaSet) {
		this.replicaSet = replicaSet;
	}

	public Integer getMinConnectionsPerHost() {
		return minConnectionsPerHost;
	}

	public void setMinConnectionsPerHost(Integer minConnectionsPerHost) {
		this.minConnectionsPerHost = minConnectionsPerHost;
	}

	public Integer getConnectionsPerHost() {
		return connectionsPerHost;
	}

	public void setConnectionsPerHost(Integer connectionsPerHost) {
		this.connectionsPerHost = connectionsPerHost;
	}

	// 覆盖默认的MongoDbFactory
//	@Bean
//	MongoDbFactory mongoDbFactory() {
//		// 客户端配置（连接数、副本集群验证）
//		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
//		builder.connectionsPerHost(this.connectionsPerHost);
//		builder.minConnectionsPerHost(this.minConnectionsPerHost);
//		if (this.replicaSet != null) {
//			builder.requiredReplicaSetName(this.replicaSet);
//		}
//		MongoClientOptions mongoClientOptions = builder.build();
//
//		ServerAddress serverAddress = new ServerAddress(this.host, this.port);
//
//		// 创建客户端和Factory
//		MongoClient mongoClient = new MongoClient(serverAddress, mongoClientOptions);
//		MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, this.dataBase);
//		return mongoDbFactory;
//	}
}
