/**
 * com.edison.testJunit.oth.springAop.springAop�������Ҫ��ͨ��ProxyFactory��ʽ������涨�壬
 * ʹ��ProxyFactory�Ļ���ÿ����Ҫ��������඼Ҫ����һ�������࣬
 * <!CDATA[<bean id="waiter" class="org.springframework.aop.framework.ProxyFactoryBean">
    	<property name="interceptorNames" value="waiterAdvice"/>
    	<property name="target"  ref="target"/>
    </bean>]>,���waiter����target�Ĵ����࣬�������úܲ����㣬���springAop2�������ʹ��spring��������ͨ��aop
 */
/**
 * @author Edison
 *
 */
package com.edison.testJunit.oth.springAop.springAop2;