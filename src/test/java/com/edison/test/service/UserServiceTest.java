package com.edison.test.service;

import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;
import org.unitils.spring.annotation.SpringBeanByName;

import com.edison.db.entity.User;
import com.edison.service.UserService;

@Test
@SpringApplicationContext("testspringContext.xml")
public class UserServiceTest {
	@SpringBean("userService")
	private UserService userService;
	
	@SpringBeanByName
	private UserService userService2;
	
	private User user=new User();
	
	@Test
	public void testService(){
		user.setUserName("张三丰");
		user.setCity("重庆");
		user.setSex("1");
		user.setPassword("123456");
		
		try{
			System.out.println("userService="+userService);
			boolean flag=userService.userRegister(user);
			System.out.println("注册结果："+flag);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			System.out.println("userService2="+userService2);
			boolean flag=userService2.userRegister(user);
			System.out.println("注册结果："+flag);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
