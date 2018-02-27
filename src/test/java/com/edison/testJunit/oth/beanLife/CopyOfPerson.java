package com.edison.testJunit.oth.beanLife;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class CopyOfPerson implements BeanFactoryAware, BeanNameAware,
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
	
	public CopyOfPerson() {
	System.out.println("��������������Person�Ĺ�����ʵ����");
	}
	
	public String getName() {
	return name;
	}
	
	public void setName(String name) {
	System.out.println("��ע�����ԡ�ע������name,---name="+name);
	this.name = name;
	}
	
	@Override
	public String toString() {
	return "Person [name=" + name + "]";
	}
	
	public void destroy() throws Exception {
	System.out.println("��DiposibleBean�ӿڡ�����" +
	        "DiposibleBean.destory()");
	}
	
	public void afterPropertiesSet() throws Exception {
	System.out.println("��InitializingBean�ӿڡ�����" +
	        "InitializingBean.afterPropertiesSet()");
	}
	
	public void setBeanName(String beanName) {
	System.out.println("��BeanNameAware�ӿڡ�����" +
	        "BeanNameAware.setBeanName()---beanName="+beanName);
	this.beanName = beanName;
	}
	
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
	System.out.println("��BeanFactoryAware�ӿڡ�����" +
	        "BeanFactoryAware.setBeanFactory()");
	this.beanFactory = arg0;
	}
	
	
	// ͨ��<bean>��init-method����ָ���ĳ�ʼ������
	public void myInit() {
	System.out.println("��init-method������" +
	        "<bean>��init-method����ָ���ĳ�ʼ������");
	}
	
	// ͨ��<bean>��destroy-method����ָ���ĳ�ʼ������
	public void myDestory() {
	System.out.println("��destroy-method������" +
	        "<bean>��destroy-method����ָ���ĳ�ʼ������");
	}
	
}