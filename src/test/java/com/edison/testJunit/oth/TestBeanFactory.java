package com.edison.testJunit.oth;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import jdk.internal.util.xml.impl.ReaderUTF8;

import org.junit.Test;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.edison.db.entity.User;

/**
 * ���ڲ���beanFactory������*/
public class TestBeanFactory {

	@Test
	public void testResource() {
		try{
			//1.ͨ�û�ȡ��Դ��ʽ
			Resource res=new PathMatchingResourcePatternResolver().getResource("classpath:springContext.xml");
			
			//2.���ɹ���
			DefaultListableBeanFactory beanFactory=new DefaultListableBeanFactory();
			
			//3.���ɹ�������-Reader
			XmlBeanDefinitionReader reader=new XmlBeanDefinitionReader(beanFactory);
			
			//4.���䴦��ӹ�����-Resource
			reader.loadBeanDefinitions(res);
			
			//5.��ȡbean
			User user=(User) beanFactory.getBean("user");

			if(user!=null){
				System.out.println("user="+user);
			}else{
				System.out.println("user is null");
			}
			
			//5.��ȡbean
			if(beanFactory.getBean("User")!=null){ //ֱ�Ӿ����쳣�ˣ����᷵��null��
				                                   //bean���Կ�������null 
				//<bean class="Car">
				//	<property name="color" /><null/></property>
				//</bean> ���õ���ɫ���������� �൱�ڰ� <value>xxx</value> �滻Ϊ<null/>
				System.out.println("����User��bean��factory��");
			}else{
				System.out.println("������User��bean��factory��");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
