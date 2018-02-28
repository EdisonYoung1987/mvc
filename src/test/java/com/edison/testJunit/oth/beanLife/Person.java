package com.edison.testJunit.oth.beanLife;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Person implements BeanFactoryAware, BeanNameAware,
	InitializingBean, DisposableBean,ApplicationContextAware{

	/**
	* BeanFactoryAware�ӿ�,ֻ������һ������:������ȡBeanFactory
	* BeanNameAware�ӿ�,ֻ������һ������:������ȡBeanName
	*/
	
	/**
	* InitializingBean:
	* ��spring��ʼ��bean��ʱ�������bean��ʵ����InitializingBean�ӿڣ�
	* ����ͬʱ�������ļ���ָ����init-method��
	* ϵͳ�����ȵ���InitializingBean��afterPropertiesSet������
	* Ȼ���ڵ���init-method��ָ���ķ�����
	* */
	
	/**
	* DisposableBean:
	* ��spring��ʼ��bean��ʱ�������bean��ʵ����DisposableBean�ӿڣ�
	* ����ͬʱ�������ļ���ָ����destroy-method��
	* ϵͳ�����ȵ���DisposableBean��destroy������
	* Ȼ���ڵ���destroy-method��ָ���ķ�����
	*/
	
	private String name;
	private BeanFactory beanFactory;
	private String beanName;
	
	public Person() {
	System.out.println("person1  ��������������Person�Ĺ�����ʵ���� Person().involke()");
	}
	
	public String getName() {
	return name;
	}
	
	public void setName(String name) {
	System.out.println("person1  ��ע�����ԡ�ע������name,---name="+name);
	this.name = name;
	}
	
	@Override
	public String toString() {
	return "Person [name=" + name + "]";
	}
	
	public void destroy() throws Exception {
	System.out.println("person1  ��DiposibleBean�ӿڡ�����" +
	        "DiposibleBean.destory()");
	}
	
	public void afterPropertiesSet() throws Exception {
	System.out.println("person1  ��InitializingBean�ӿڡ�����" +
	        "InitializingBean.afterPropertiesSet()");
	}
	
	public void setBeanName(String beanName) {
	System.out.println("person1  ��BeanNameAware�ӿڡ�����" +
	        "BeanNameAware.setBeanName()---beanName="+beanName);
	this.beanName = beanName;
	}
	
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
	System.out.println("person1  ��BeanFactoryAware�ӿڡ�����" +
	        "BeanFactoryAware.setBeanFactory()");
	this.beanFactory = arg0;
	}
	
	
	// ͨ��<bean>��init-method����ָ���ĳ�ʼ������
	public void myInit() {
	System.out.println("person1  ��init-method������" +
	        "<bean>��init-method����ָ���ĳ�ʼ������");
	}
	
	// ͨ��<bean>��destroy-method����ָ���ĳ�ʼ������
	public void myDestory() {
		System.out.println("person1  beanName="+this.beanName);
	System.out.println("person1  ��destroy-method������" +
	        "<bean>��destroy-method����ָ���ĳ�ʼ������");
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		System.out.println("person1  setApplicationContext()������");
		
	}
	
}