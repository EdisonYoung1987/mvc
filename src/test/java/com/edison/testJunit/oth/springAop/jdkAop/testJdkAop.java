package com.edison.testJunit.oth.springAop.jdkAop;

import java.lang.reflect.Proxy;

import com.edison.testJunit.oth.springAop.noAop.ForumService;

public class testJdkAop {

	public static void main(String[] args) {
		ForumService forum=new  ForumServiceImpl();  //目标类

		PerformanceHandler handler=new PerformanceHandler(forum); //handler
		ForumService proxy=(ForumService)Proxy.newProxyInstance(forum.getClass().getClassLoader()
				, forum.getClass().getInterfaces(), handler); //实际上 forum的代理类还是ForumService类型的
		//代理类生成以后，通过代理类去执行方法
		proxy.removeForum(10);
		proxy.removeTopic(11);
	}

}
