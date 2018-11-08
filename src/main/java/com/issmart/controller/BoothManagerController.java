package com.issmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.issmart.entity.BoothInfoEntity;
import com.issmart.entity.ResponseResult;
import com.issmart.service.BoothService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("booth/manager")
@Api(value = "展台信息", description = "展台信息", tags = {"1"})
public class BoothManagerController {

	@Autowired
	private BoothService boothService;
	
	/**
	 * 新建展台信息
	 * 
	 * @return
	 */
	@ApiOperation(value = "新建展台信息")
	@RequestMapping(value = "insert/booth/info", method = RequestMethod.POST)
	public @ResponseBody ResponseResult<BoothInfoEntity> insert(@RequestBody BoothInfoEntity boothInfoEntity) {
		ResponseResult<BoothInfoEntity> responseResult = new ResponseResult<BoothInfoEntity>();
		BoothInfoEntity resultData = boothService.insert(boothInfoEntity);
		responseResult.setData(resultData);
		return responseResult;
	}
}
