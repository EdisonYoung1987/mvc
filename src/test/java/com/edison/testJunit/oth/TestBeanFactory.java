package com.edison.testJunit.oth;

import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
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
			
			//查看类的注册情况
			String[] names= beanFactory.getBeanDefinitionNames();
			for(String name:names){
				System.out.println("容器中注册有："+name);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
