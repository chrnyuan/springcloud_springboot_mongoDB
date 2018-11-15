package com.issmart.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.issmart.entity.BoothInfoEntity;
import com.issmart.repository.BoothRepository;
import com.issmart.service.BoothService;

@Service
public class BoothServiceImpl implements BoothService{
	
	 private static final Logger logger = LoggerFactory.getLogger(BoothServiceImpl.class);
	
	@Autowired
	private BoothRepository boothRepository;

	@Override
	public BoothInfoEntity insert(BoothInfoEntity boothInfoEntity) {
		boothRepository.deleteByUnitIdAndDeviceMac(boothInfoEntity.getUnitId(),boothInfoEntity.getDeviceMac());
		boothInfoEntity.setCreatedTimeStamp(System.currentTimeMillis());
		BoothInfoEntity boothInfoEntityResult = boothRepository.insert(boothInfoEntity);
		logger.info("保存展台信息"+boothInfoEntityResult.getDeviceMac());
		return boothInfoEntity;
	}
}
