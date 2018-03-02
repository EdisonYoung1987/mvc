package com.edison.testJunit.oth.springAop.jdkAop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.edison.testJunit.oth.springAop.noAop.PerformanceMonitor;

public class PerformanceHandler implements InvocationHandler {
	private Object target;
	public PerformanceHandler(Object target){
		this.target=target;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		PerformanceMonitor.begin(target.getClass().getSimpleName()+"."+method.getName());
		Object object=method.invoke(target, args);
		PerformanceMonitor.end();
		return null;
	}

}
