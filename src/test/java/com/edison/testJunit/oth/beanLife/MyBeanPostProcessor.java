package com.edison.testJunit.oth.beanLife;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

    /**
     * BeanPostProcessor接口包括2个方法
     * postProcessAfterInitialization和postProcessBeforeInitialization，
     * 这两个方法的第一个参数都是要处理的Bean对象，第二个参数都是Bean的name。
     * 返回值也都是要处理的Bean对象。
     * 
     * 此接口的方法可以对Bean的属性进行更改
     */

    public MyBeanPostProcessor() {
        super();
        System.out.println("BeanPostProcessor实现类构造器！！");
    }

    public Object postProcessAfterInitialization(Object obj, String objName)
            throws BeansException {
       /* Person person = (Person) obj;
        person.setName("之后--赵六");*/
        System.out.println("BeanPostProcessor.postProcessAfterInitialization对属性进行更改！" +
                "---"+"obj="+obj+"---objName="+objName);
        return obj;
    }

    public Object postProcessBeforeInitialization(Object obj, String objName)
            throws BeansException {
       /* Person person = (Person) obj;
        person.setName("之前--李四");*/
        System.out.println("BeanPostProcessor.postProcessBeforeInitialization对属性进行更改！" +
                "---"+"obj="+obj+"---objName="+objName);
        return obj;
    }
}