package com.edison.testJunit.oth.springAop.springAop;

import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringAop {
	@Test
	public void testSpringAop(){
		System.out.println("ͨ��ProxyFactoryӲ���뷽ʽ");
		Waiter target=new Waiter();
		ProxyFactory pf=new ProxyFactory();
		pf.setTarget(target);
		pf.addAdvice(new WaiterAdvice());
		Waiter proxy=(Waiter) pf.getProxy();

		proxy.greeTo("����");
		proxy.serveTo("����");
		System.out.println("���Ӳ���뷽ʽ");
		System.out.println();
	}
	
	@Test
	public void testSpringAopXml(){//ͨ��xml���õķ�ʽ
		try{
			System.out.println("ͨ��xml���õķ�ʽʵ��aop");
			ApplicationContext applicationContext=new ClassPathXmlApplicationContext("com/edison/testJunit/oth/springAop/springAop/aop.xml");
			Waiter proxy=(Waiter) applicationContext.getBean("waiter");
	
			proxy.greeTo("����");
			proxy.serveTo("����");
			System.out.println("���xml��ʽ");
			System.out.println();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
