package com.issmart.service;

import org.springframework.stereotype.Service;

import com.issmart.entity.BoothInfoEntity;

@Service
public interface BoothService{
	
	/**
	 * 新建展台信息
	 * 
	 * @return
	 */
	public BoothInfoEntity insert(BoothInfoEntity boothInfoEntity);
}
