package com.edison.testJunit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.junit.Test;

import com.edison.db.entity.User;
import com.edison.service.UserService;
import com.edison.testJunit.BaseJunitTest;

public class UserServiceTest extends BaseJunitTest{
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserService userService2;
	
	private User user=new User();
	
	@Test
	@Transactional   //标明此方法需使用事务    
    @Rollback(false)  //标明使用完此方法后事务不回滚,true时为回滚   
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
