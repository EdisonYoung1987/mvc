package com.edison.testJunit.oth.beanLife;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

    /**
     * BeanPostProcessor�ӿڰ���2������
     * postProcessAfterInitialization��postProcessBeforeInitialization��
     * �����������ĵ�һ����������Ҫ�����Bean���󣬵ڶ�����������Bean��name��
     * ����ֵҲ����Ҫ�����Bean����
     * 
     * �˽ӿڵķ������Զ�Bean�����Խ��и���
     */

    public MyBeanPostProcessor() {
        super();
        System.out.println("����BeanPostProcessorʵ���๹��������");
    }

    public Object postProcessAfterInitialization(Object obj, String objName)
            throws BeansException {
       /* Person person = (Person) obj;
        person.setName("֮��--����");*/
        System.out.println("BeanPostProcessor�ӿڷ���" +
                "postProcessAfterInitialization�����Խ��и��ģ�" +
                "---"+"obj="+obj+"---objName="+objName);
        return obj;
    }

    public Object postProcessBeforeInitialization(Object obj, String objName)
            throws BeansException {
       /* Person person = (Person) obj;
        person.setName("֮ǰ--����");*/
        System.out.println("BeanPostProcessor�ӿڷ���" +
                "postProcessBeforeInitialization�����Խ��и��ģ�" +
                "---"+"obj="+obj+"---objName="+objName);
        return obj;
    }
}