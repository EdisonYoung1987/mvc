package com.edison.testJunit.oth.aspectJAop.aspectj1;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAspectJAop {
	private ApplicationContext ctx;

	@Test
	public void testSpringAop(){
		//ʹ��AnnotationAwareAspectJAutoProxyCreator�Զ����ɴ���
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
			//ʹ��xmlns:aop + aop:aspectj-autoproxy �����Զ�����������;
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
