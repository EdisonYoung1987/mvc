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
	
	public Person2() {
	System.out.println("person2  ��������������Person�Ĺ�����ʵ����");
	}
	
	public String getName() {
	return name;
	}
	
	public void setName(String name) {
	System.out.println("person2  ��ע�����ԡ�ע������name,---name="+name);
	this.name = name;
	}
	
	@Override
	public String toString() {
	return "Person [name=" + name + "]";
	}
	
	public void destroy() throws Exception {
	System.out.println("person2  ��DiposibleBean�ӿڡ�����" +
	        "DiposibleBean.destory()");
	}
	
	public void afterPropertiesSet() throws Exception {
	System.out.println("person2  ��InitializingBean�ӿڡ�����" +
	        "InitializingBean.afterPropertiesSet()");
	}
	
	public void setBeanName(String beanName) {
	System.out.println("person2  ��BeanNameAware�ӿڡ�����" +
	        "BeanNameAware.setBeanName()---beanName="+beanName);
	this.beanName = beanName;
	}
	
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
	System.out.println("person2  ��BeanFactoryAware�ӿڡ�����" +
	        "BeanFactoryAware.setBeanFactory()");
	this.beanFactory = arg0;
	}
	
	
	// ͨ��<bean>��init-method����ָ���ĳ�ʼ������
	public void myInit() {
	System.out.println("person2  ��init-method������" +
	        "<bean>��init-method����ָ���ĳ�ʼ������");
	}
	
	// ͨ��<bean>��destroy-method����ָ���ĳ�ʼ������
	public void myDestory() {
		System.out.println("person2  beanName="+this.beanName);
	System.out.println("person2  ��destroy-method������" +
	        "<bean>��destroy-method����ָ���ĳ�ʼ������");
	}
	
}