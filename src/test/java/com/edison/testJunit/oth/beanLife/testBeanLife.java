package com.edison.testJunit.oth.beanLife;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class testBeanLife {

	public static void main(String[] args) {
       /* System.out.println("���ڿ�ʼ��ʼ��IOC����");
        ApplicationContext context = 
                new FileSystemXmlApplicationContext("C:/Users/Edison/git/mvc/src/test/java/com/edison/testJunit/oth/beanLife/lifeContext.xml");
        System.out.println("IOC������ʼ���ɹ�"); 
        Person person = (Person) context.getBean("person1");
           */
		
		Resource res=new PathMatchingResourcePatternResolver().getResource("file:C:/Users/Edison/git/mvc/src/test/java/com/edison/testJunit/oth/beanLife/lifeContext.xml");
		DefaultListableBeanFactory beanFactory=new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader=new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(res);
        Person person = (Person) beanFactory.getBean("person1");
        
        System.out.println("ʹ��person�����toString:"+person);
        System.out.println("���ڿ�ʼ�ر�������");
//        ((FileSystemXmlApplicationContext)context).registerShutdownHook();
        
    }

}
