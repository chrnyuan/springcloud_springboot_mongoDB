package com.issmart.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.issmart.entity.RecommendCollectionEntity;
import com.issmart.entity.RecommendInfoEntity;
import com.issmart.entity.ResponseResult;
import com.issmart.service.RecommendService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("recommend/manager")
@EnableAsync
@Api(value = "推荐信息", description = "推荐信息", tags = {"3"})
public class RecommendController {

	@Autowired
	private RecommendService recommendService;
	
	/**
	 * 冷启动设置推荐集
	 * 
	 * @return
	 */
	@ApiOperation(value = "冷启动设置推荐集")
	@RequestMapping(value = "start", method = RequestMethod.GET)
	public @ResponseBody ResponseResult<Integer> firstStart() {
		ResponseResult<Integer> responseResult = new ResponseResult<Integer>();
		responseResult.setData(recommendService.firstStart());
		return responseResult;
	}

	/**
	 * 根据BeaconMac查询推荐信息
	 * 
	 * @return
	 */
	@ApiOperation(value = "查询推荐信息",notes="查询推荐信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "unitId", value = "unitId",paramType = "query", dataType = "String",required=true),
		@ApiImplicitParam(name = "beaconMac", value = "beaconMac",paramType = "query", dataType = "String",required=true),
		@ApiImplicitParam(name = "boothSize", value = "boothSize",paramType = "query", dataType = "Integer",required=true)})
	@RequestMapping(value = "query/recommend", method = RequestMethod.GET)
	public @ResponseBody ResponseResult<RecommendCollectionEntity> findByBeaconMac(@RequestParam("unitId") String unitId,@RequestParam("beaconMac") String beaconMac,@RequestParam("boothSize") int boothSize) {
		ResponseResult<RecommendCollectionEntity> responseResult = new ResponseResult<RecommendCollectionEntity>();
		RecommendCollectionEntity resultData = recommendService.findByUnitIdAndBeaconMac(unitId,beaconMac);
		if(resultData != null && resultData.getRecommendInfoList().size() != 0) {
			List<RecommendInfoEntity> recommendInfoList = resultData.getRecommendInfoList();
			Collections.sort(recommendInfoList, new Comparator<RecommendInfoEntity>() {
				@Override
				public int compare(RecommendInfoEntity o1, RecommendInfoEntity o2) {
					if (o1.getScore() < o2.getScore()) {
						return 1;
					}
					if (o1.getScore() == o2.getScore()) {
						return 0;
					}
					return -1;
				}
			});
			
			int size = recommendInfoList.size();
			int index1 = (size >> 1) - (size >> 4);
			int index2 = (size >> 1) + (size >> 3);
			if(boothSize > recommendInfoList.size()) {
				boothSize = recommendInfoList.size();
			}
			boothSize -= 2;
			List<RecommendInfoEntity> recommendInfoListResult = recommendInfoList.subList(0, boothSize);
			recommendInfoListResult.add(recommendInfoList.get(index1));
			recommendInfoListResult.add(recommendInfoList.get(index2));
			resultData.setRecommendInfoList(recommendInfoListResult);
			recommendService.updateRecommendCollection(unitId,beaconMac);
		} else {
			// 没有推荐数据，则重置冷启动
			recommendService.firstStart();
		}
		responseResult.setData(resultData);
		return responseResult;
	}
	
	public static void main(String[] args) {
		System.out.println(59 >> 2);
		System.out.println(60 << 1);
	}
}
