package com.edison.testJunit.oth.beanLife;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

public class MyInstantiationAwareBeanPostProcessor 
extends InstantiationAwareBeanPostProcessorAdapter {

/**
 * InstantiationAwareBeanPostProcessor 接口本质是
 * BeanPostProcessor的子接口，
 * 一般我们继承Spring为其提供的适配器类
 * InstantiationAwareBeanPostProcessorAdapter来使用它，
 * 
 * 此接口可以在Bean实例化前、Bean实例化后分别进行操作
 * 也可以对Bean实例化之后进行属性操作
 */

public MyInstantiationAwareBeanPostProcessor (){
    super();
    System.out.println(
            "这是InstantiationAwareBeanPostProcessorAdapter" +
            "实现类构造器！！");
}

// 接口方法、实例化Bean之前调用
@Override
public Object postProcessBeforeInstantiation(Class<?> beanClass,
        String beanName) throws BeansException {
    System.out.println("InstantiationAwareBeanPostProcessor" +
            "调用postProcessBeforeInstantiation方法");
    return null;
}

/**
 * postProcessAfterInitialization重写了BeanPostProcessor的方法。
 */
// 接口方法、实例化Bean之后调用
@Override
public Object postProcessAfterInitialization(Object bean, 
        String beanName) throws BeansException {
    System.out.println("InstantiationAwareBeanPostProcessor" +
            "调用postProcessAfterInitialization方法");
    return bean;
}

/**
 * 第三个方法postProcessPropertyValues用来操作属性，返回值也应该是PropertyValues对象
 */
// 接口方法、设置某个属性时调用
@Override
public PropertyValues postProcessPropertyValues(PropertyValues pvs,
        PropertyDescriptor[] pds, Object bean, String beanName)
        throws BeansException {
    System.out.print("InstantiationAwareBeanPostProcessor" +
            "调用postProcessPropertyValues方法  ");
    System.out.println("beanName="+beanName);
    return pvs;
}
}
