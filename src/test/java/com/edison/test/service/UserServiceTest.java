package com.edison.test.service;

import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

import com.edison.db.entity.User;
import com.edison.service.UserService;

@Test
@SpringApplicationContext("testspringContext.xml")
public class UserServiceTest {
	@SpringBean("userService")
	private UserService userService;
	
	private User user=new User();
	
	@Test
	public void testService(){
		try{
			user.setUserName("������");
			user.setCity("����");
			user.setSex("1");
			user.setPassword("123456");
			System.out.println("userService="+userService);
			
			boolean flag=userService.userRegister(user);
			System.out.println("ע������"+flag);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
