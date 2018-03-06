package com.edison.testJunit.oth.springAop.springAop2;

import org.aopalliance.intercept.MethodInterceptor; //环绕增强得用这个
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

@Component
public class AroundAdvice implements MethodInterceptor{
	public Object invoke(MethodInvocation invocation) throws Throwable {//环绕增强
		String methodName=invocation.getMethod().getName();
		
		System.out.println("around 增强方法开始："+methodName);
		invocation.proceed();
		System.out.println("around 增强方法结束："+methodName);

		return null;
	}
}
