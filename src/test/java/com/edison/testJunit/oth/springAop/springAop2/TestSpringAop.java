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
		//ʹ��BeanNameAutoProxyCreator�Զ����ɴ���
		//ȱ����û��ƥ�������
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
		//ʹ��RegexpMethodPointcutAdvisorƥ����+DefaultAdvisorAutoProxyCreator�Զ�����������;
		//ͨ����������ƥ����RegexpMethodPointcutAdvisor���ﵽ����+������ɸѡ�������ֻ��greeToǰ����ǿ����serveTo���к�����ǿ
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
