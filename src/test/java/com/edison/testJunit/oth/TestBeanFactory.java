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
 * 用于测试beanFactory的生成*/
public class TestBeanFactory {

	@Test
	public void testResource() {
		try{
			//1.通用获取资源方式
			Resource res=new PathMatchingResourcePatternResolver().getResource("classpath:springContext.xml");
			
			//2.生成工厂
			DefaultListableBeanFactory beanFactory=new DefaultListableBeanFactory();
			
			//3.生成工厂车间-Reader
			XmlBeanDefinitionReader reader=new XmlBeanDefinitionReader(beanFactory);
			
			//4.车间处理加工材料-Resource
			reader.loadBeanDefinitions(res);
			
			//5.获取bean
			User user=(User) beanFactory.getBean("user");

			if(user!=null){
				System.out.println("user="+user);
			}else{
				System.out.println("user is null");
			}
			
			//5.获取bean
			if(beanFactory.getBean("User")!=null){ //直接就抛异常了，不会返回null，
				                                   //bean属性可以配置null 
				//<bean class="Car">
				//	<property name="color" /><null/></property>
				//</bean> 配置的无色车，哈哈哈 相当于把 <value>xxx</value> 替换为<null/>
				System.out.println("存在User的bean在factory中");
			}else{
				System.out.println("不存在User的bean在factory中");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
