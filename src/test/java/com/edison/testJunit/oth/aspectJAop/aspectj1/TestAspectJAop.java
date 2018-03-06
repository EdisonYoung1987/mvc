package com.edison.testJunit.oth.aspectJAop.aspectj1;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAspectJAop {
	private ApplicationContext ctx;

	@Test
	public void testSpringAop(){
		//使用AnnotationAwareAspectJAutoProxyCreator自动生成代理
		try{
			ctx = new ClassPathXmlApplicationContext("classpath:com/edison/testJunit/oth/aspectJAop/aspectj1/AspectJAop.xml");
			Waiter waiter=(Waiter) ctx.getBean("waiter");
			Seller seller=(Seller) ctx.getBean("seller");
			
			waiter.greeTo("xx");
			waiter.serveTo("xx");
	
			seller.greeTo("xx");
			seller.serveTo("xx");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSpringAop2(){
		try{
			//使用xmlns:aop + aop:aspectj-autoproxy 配置自动代理生成器;
			ctx = new ClassPathXmlApplicationContext("classpath:com/edison/testJunit/oth/aspectJAop/aspectj1/AspectJAop2.xml");
			Waiter waiter=(Waiter) ctx.getBean("waiter");
			Seller seller=(Seller) ctx.getBean("seller");
			
			waiter.greeTo("xx");
			waiter.serveTo("xx");
	
			seller.greeTo("xx");
			seller.serveTo("xx");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
