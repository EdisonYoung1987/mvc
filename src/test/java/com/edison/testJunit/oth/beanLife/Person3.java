package com.edison.testJunit.oth.beanLife;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**这个bean主要用来测试component-scan扫描纳入容器的bean名称是什么*/
@Component
public class Person3{

	public void printMsg() {
		System.out.println("Person3 执行打印");
	}
}