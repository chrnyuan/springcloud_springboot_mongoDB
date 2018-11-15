package com.issmart.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.issmart.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class})
public class RecommendServiceTest {
	
	@Autowired
	private RecommendService recommendService;
	
	@Test
	public void testUpdateRecommendCollection() {
		recommendService.updateRecommendCollection("920","F0F8F2DA97E3");
	}

}
