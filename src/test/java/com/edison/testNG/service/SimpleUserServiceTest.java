package com.edison.testNG.service;

import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringBean;
import org.unitils.spring.annotation.SpringBeanByName;
import org.unitils.spring.annotation.SpringBeanByType;

import com.edison.service.UserService;

import static org.junit.Assert.*;

public class SimpleUserServiceTest extends BaseServiceTest {
	
	@SpringBean("userService")
	private UserService userService1;
	@SpringBeanByType
	private UserService userService2;
	@SpringBeanByName
	private  UserService userService;
	
	@Test
	public void testApplicationContext(){
		assertNotNull(applicationContext);
	}
	
	@Test
	public void testUserService(){
		assertNotNull(userService);
		assertNotNull(userService1);
		assertNotNull(userService2);
	}
}
