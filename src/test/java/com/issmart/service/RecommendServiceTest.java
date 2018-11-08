package com.issmart.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.issmart.Application;
import com.issmart.entity.MemberFindLogEntity;
import com.issmart.repository.MemberFindLogRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class})
public class RecommendServiceTest {
	
	@Autowired
	private RecommendService recommendService;
	
	@Autowired
	private MemberFindLogRepository memberFindLogRepository;

//	@Test
//	public void testFirstStart() {
//		fail("Not yet implemented");
//	}
//
	@Test
	public void testUpdateRecommendCollection() {
		recommendService.updateRecommendCollection("F0F8F2DA97E3");
	}
//
//	@Test
//	public void testFindByBeaconMac() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testInsertMemberFindLogEntity() {
		MemberFindLogEntity memberFindLogEntity = new MemberFindLogEntity();
		memberFindLogEntity.setBeaconMac("F0F8F2DA97E3");
		memberFindLogEntity.setTimeStamp(System.currentTimeMillis() - 1000000);
		memberFindLogRepository.insert(memberFindLogEntity);
	}
}
