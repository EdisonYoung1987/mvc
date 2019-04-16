package com.edison.testJunit.oth.beanLife;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class testBeanLife {

	public static void main(String[] args) {
        //1.
		System.out.println("现在开始初始化IOC容器");
		//这个也可以
		//ApplicationContext context =   
//                new FileSystemXmlApplicationContext("C:/Users/Edison/git/mvc/src/test/java/com/edison/testJunit/oth/beanLife/lifeContext.xml");
        ApplicationContext context = 
                new ClassPathXmlApplicationContext("com/edison/testJunit/oth/beanLife/lifeContext.xml"); //可以
//        context2=new ClassPathXmlApplicationContext("com/edison/testJunit/oth/beanLife/lifeContext.xml");
        System.out.println("IOC容器初始化成功"); 
        Person person = (Person) context.getBean("com.edison.testJunit.oth.beanLife.Person"); //xml没有指定id，所以使用全限定名取bean
		
		/*//2.
		  Resource res=new PathMatchingResourcePatternResolver().getResource("file:C:/Users/Edison/git/mvc/src/test/java/com/edison/testJunit/oth/beanLife/lifeContext.xml");
		DefaultListableBeanFactory beanFactory=new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader=new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(res);
        Person person = (Person) beanFactory.getBean("person1");*/
        
//        System.out.println("使用person对象的toString:"+person);
        Person2 person2 = (Person2) context.getBean("person2");
      
        System.out.println("现在开始关闭容器！");
        ((AbstractApplicationContext) context).registerShutdownHook();
        
    }

}
