/**
 * com.edison.testJunit.oth.springAop.springAop这个包主要是通过ProxyFactory方式完成切面定义，
 * 使用ProxyFactory的话，每个需要被代理的类都要定义一个代理类，
 * <!CDATA[<bean id="waiter" class="org.springframework.aop.framework.ProxyFactoryBean">
    	<property name="interceptorNames" value="waiterAdvice"/>
    	<property name="target"  ref="target"/>
    </bean>]>,这个waiter就是target的代理类，这种配置很不方便，这个springAop2下面就是使用spring的两个类通配aop
 */
/**
 * @author Edison
 *
 */
package com.edison.testJunit.oth.springAop.springAop2;