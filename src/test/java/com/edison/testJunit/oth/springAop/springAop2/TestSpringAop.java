package com.edison.testJunit.oth.springAop.springAop2;

import org.junit.Test;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.support.DefaultActiveProfilesResolver;

public class TestSpringAop {
	private ApplicationContext ctx;

	@Test
	public void testSpringAop(){
		//使用BeanNameAutoProxyCreator自动生成代理
		//缺点是没法匹配参数名
		ctx = new ClassPathXmlApplicationContext("classpath:com/edison/testJunit/oth/springAop/springAop2/aop.xml");
		Waiter waiter=(Waiter) ctx.getBean("waiter");
		Seller seller=(Seller) ctx.getBean("seller");
		NoAop noAop=(NoAop) ctx.getBean("noAop");
		
		waiter.greeTo("xx");
		waiter.serveTo("xx");

		seller.greeTo("xx");
		seller.serveTo("xx");
		
		noAop.greeTo("xx");
		noAop.serveTo("xx");
	}
	
	@Test
	public void testSpringAop2(){
		//使用RegexpMethodPointcutAdvisor匹配器+DefaultAdvisorAutoProxyCreator自动代理生成器;
		//通过定义两个匹配器RegexpMethodPointcutAdvisor，达到了类+方法的筛选，这个就只对greeTo前置增强，对serveTo进行后置增强
		ctx = new ClassPathXmlApplicationContext("classpath:com/edison/testJunit/oth/springAop/springAop2/aop2.xml");
		Waiter waiter=(Waiter) ctx.getBean("waiter");
		Seller seller=(Seller) ctx.getBean("seller");
		NoAop noAop=(NoAop) ctx.getBean("noAop");
		
		waiter.greeTo("xx");
		waiter.serveTo("xx");

		seller.greeTo("xx");
		seller.serveTo("xx");
		
		noAop.greeTo("xx");
		noAop.serveTo("xx");
	}
}
