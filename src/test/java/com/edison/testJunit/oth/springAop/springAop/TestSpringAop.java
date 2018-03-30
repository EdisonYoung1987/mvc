package com.edison.testJunit.oth.springAop.springAop;

import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringAop {
	@Test
	public void testSpringAop(){
		System.out.println("通过ProxyFactory硬编码方式");
		Waiter target=new Waiter();
		ProxyFactory pf=new ProxyFactory();
		pf.setTarget(target);
		pf.addAdvice(new WaiterAdvice());
		Waiter proxy=(Waiter) pf.getProxy();

		proxy.greeTo("张三");
		proxy.serveTo("张三");
		System.out.println("完成硬编码方式");
		System.out.println();
	}
	
	@Test
	public void testSpringAopXml(){//通过xml配置的方式
		try{
			System.out.println("通过xml配置的方式实现aop");
			ApplicationContext applicationContext=new ClassPathXmlApplicationContext("com/edison/testJunit/oth/springAop/springAop/aop.xml");
			Waiter proxy=(Waiter) applicationContext.getBean("waiter");
	
			proxy.greeTo("张三");
			proxy.serveTo("张三");
			System.out.println("完成xml方式");
			System.out.println();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
