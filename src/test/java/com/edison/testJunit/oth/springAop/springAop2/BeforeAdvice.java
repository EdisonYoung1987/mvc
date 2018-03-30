package com.edison.testJunit.oth.springAop.springAop2;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;
//环绕增强得用这个

@Component
public class BeforeAdvice implements MethodBeforeAdvice{
	//method 被增强的方法 args- 方法传入的参数 arg2 -被代理对象
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
			System.out.println("before 增强方法："+method.getName());
	}
}
