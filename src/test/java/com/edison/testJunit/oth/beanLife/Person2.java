package com.edison.testJunit.oth.beanLife;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Person2 implements BeanFactoryAware, BeanNameAware,
	InitializingBean, DisposableBean{

	/**
	* BeanFactoryAware接口,只声明了一个方法:用来获取BeanFactory
	* BeanNameAware接口,只声明了一个方法:用来获取BeanName
	*/
	
	/**
	* InitializingBean:
	* 在spring初始化bean的时候，如果该bean是实现了InitializingBean接口，
	* 并且同时在配置文件中指定了init-method，
	* 系统则是先调用InitializingBean的afterPropertiesSet方法，
	* 然后在调用init-method中指定的方法。
	* */
	
	/**
	* DisposableBean:
	* 在spring初始化bean的时候，如果该bean是实现了DisposableBean接口，
	* 并且同时在配置文件中指定了destroy-method，
	* 系统则是先调用DisposableBean的destroy方法，
	* 然后在调用destroy-method中指定的方法。
	*/
	
	private String name;
	private BeanFactory beanFactory;
	private String beanName;
	
	public Person2() {
	System.out.println("person2  【构造器】调用Person的构造器实例化");
	}
	
	public String getName() {
	return name;
	}
	
	public void setName(String name) {
	System.out.println("person2  【注入属性】注入属性name,---name="+name);
	this.name = name;
	}
	
	@Override
	public String toString() {
	return "Person [name=" + name + "]";
	}
	
	public void destroy() throws Exception {
	System.out.println("person2  【DiposibleBean接口】调用" +
	        "DiposibleBean.destory()");
	}
	
	public void afterPropertiesSet() throws Exception {
	System.out.println("person2  【InitializingBean接口】调用" +
	        "InitializingBean.afterPropertiesSet()");
	}
	
	public void setBeanName(String beanName) {
	System.out.println("person2  【BeanNameAware接口】调用" +
	        "BeanNameAware.setBeanName()---beanName="+beanName);
	this.beanName = beanName;
	}
	
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
	System.out.println("person2  【BeanFactoryAware接口】调用" +
	        "BeanFactoryAware.setBeanFactory()");
	this.beanFactory = arg0;
	}
	
	
	// 通过<bean>的init-method属性指定的初始化方法
	public void myInit() {
	System.out.println("person2  【init-method】调用" +
	        "<bean>的init-method属性指定的初始化方法");
	}
	
	// 通过<bean>的destroy-method属性指定的初始化方法
	public void myDestory() {
		System.out.println("person2  beanName="+this.beanName);
	System.out.println("person2  【destroy-method】调用" +
	        "<bean>的destroy-method属性指定的初始化方法");
	}
	
}