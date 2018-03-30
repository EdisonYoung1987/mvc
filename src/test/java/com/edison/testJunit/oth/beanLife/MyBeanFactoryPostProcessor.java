package com.edison.testJunit.oth.beanLife;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor{

    /**
     * BeanFactoryPostProcessor可以对bean的定义（配置元数据）进行处理。
     * 也就是说，Spring IoC容器允许BeanFactoryPostProcessor
     * 在容器实际实例化任何其它的bean之前读取配置元数据，并有可能修改它。
     * 如果你愿意，你可以配置多个BeanFactoryPostProcessor。
     * 你还能通过设置'order'属性来控制BeanFactoryPostProcessor的执行次序。
     * */

    public MyBeanFactoryPostProcessor() {
        super();
        System.out.println("这是BeanFactoryPostProcessor实现类构造器！！");
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0)
            throws BeansException {
        System.out.println("BeanFactoryPostProcessor调用postProcessBeanFactory方法");
        BeanDefinition bd = arg0.getBeanDefinition("person2");
        bd.getPropertyValues().addPropertyValue("name", "张三三");
    }
}
