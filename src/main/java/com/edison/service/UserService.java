package com.edison.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edison.db.entity.User;

/**
 * @author Edison
 * 用户服务类*/
@Service
public class UserService {
	/**
	 * 用户注册*/
	public boolean userRegister(User user){ //其实这里最好使用Context传递上下文，这样可以把信息都传递出去
		//用户合法性检查
		String name=user.getUserName();
		System.out.println("用户【"+name+"】进行注册");
		
		//查询数据库看是否已存在
		boolean exist=false;
		if(exist==true){
			System.out.println("用户已存在");
			return false;
		}else{
			//TODO 将用户插入数据库
			System.out.println("用户已注册成功");
		}
		
		return true;
	}
}
