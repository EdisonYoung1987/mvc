package com.edison.testJunit.oth.aspectJAop.aspectj2;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAspectJAop {
	private ApplicationContext ctx;

	@Test
	public void testSpringAop(){
		//使用AnnotationAwareAspectJAutoProxyCreator自动生成代理
		try{
			ctx = new ClassPathXmlApplicationContext("classpath:com/edison/testJunit/oth/aspectJAop/aspectj2/AspectJAop.xml");
			Waiter waiter=(Waiter) ctx.getBean("waiter");
			Seller seller=(Seller) ctx.getBean("seller");
			
			waiter.greeTo("xx");
			waiter.serveTo("xx");
	
			seller.greeTo("xx");
			seller.serveTo("xx");
			seller.guideTo(new Waiter());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
