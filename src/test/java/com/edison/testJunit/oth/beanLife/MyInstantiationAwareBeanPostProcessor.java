package com.edison.testJunit.oth.beanLife;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

public class MyInstantiationAwareBeanPostProcessor 
extends InstantiationAwareBeanPostProcessorAdapter {

/**
 * InstantiationAwareBeanPostProcessor �ӿڱ�����
 * BeanPostProcessor���ӽӿڣ�
 * һ�����Ǽ̳�SpringΪ���ṩ����������
 * InstantiationAwareBeanPostProcessorAdapter��ʹ������
 * 
 * �˽ӿڿ�����Beanʵ����ǰ��Beanʵ������ֱ���в���
 * Ҳ���Զ�Beanʵ����֮��������Բ���
 */

public MyInstantiationAwareBeanPostProcessor (){
    super();
    System.out.println(
            "����InstantiationAwareBeanPostProcessorAdapter" +
            "ʵ���๹��������");
}

// �ӿڷ�����ʵ����Bean֮ǰ����
@Override
public Object postProcessBeforeInstantiation(Class<?> beanClass,
        String beanName) throws BeansException {
    System.out.println("InstantiationAwareBeanPostProcessor" +
            "����postProcessBeforeInstantiation����");
    return null;
}

/**
 * postProcessAfterInitialization��д��BeanPostProcessor�ķ�����
 */
// �ӿڷ�����ʵ����Bean֮�����
@Override
public Object postProcessAfterInitialization(Object bean, 
        String beanName) throws BeansException {
    System.out.println("InstantiationAwareBeanPostProcessor" +
            "����postProcessAfterInitialization����");
    return bean;
}

/**
 * ����������postProcessPropertyValues�����������ԣ�����ֵҲӦ����PropertyValues����
 */
// �ӿڷ���������ĳ������ʱ����
@Override
public PropertyValues postProcessPropertyValues(PropertyValues pvs,
        PropertyDescriptor[] pds, Object bean, String beanName)
        throws BeansException {
    System.out.print("InstantiationAwareBeanPostProcessor" +
            "����postProcessPropertyValues����  ");
    System.out.println("beanName="+beanName);
    return pvs;
}
}
