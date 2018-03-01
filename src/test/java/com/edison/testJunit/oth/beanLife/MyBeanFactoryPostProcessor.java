package com.edison.testJunit.oth.beanLife;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor{

    /**
     * BeanFactoryPostProcessor���Զ�bean�Ķ��壨����Ԫ���ݣ����д���
     * Ҳ����˵��Spring IoC��������BeanFactoryPostProcessor
     * ������ʵ��ʵ�����κ�������bean֮ǰ��ȡ����Ԫ���ݣ����п����޸�����
     * �����Ը�⣬��������ö��BeanFactoryPostProcessor��
     * �㻹��ͨ������'order'����������BeanFactoryPostProcessor��ִ�д���
     * */

    public MyBeanFactoryPostProcessor() {
        super();
        System.out.println("����BeanFactoryPostProcessorʵ���๹��������");
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0)
            throws BeansException {
        System.out.println("BeanFactoryPostProcessor����postProcessBeanFactory����");
        BeanDefinition bd = arg0.getBeanDefinition("person2");
        bd.getPropertyValues().addPropertyValue("name", "������");
    }
}
