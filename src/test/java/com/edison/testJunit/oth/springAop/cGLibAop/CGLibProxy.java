package com.edison.testJunit.oth.springAop.cGLibAop;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import com.edison.testJunit.oth.springAop.noAop.PerformanceMonitor;

public class CGLibProxy implements MethodInterceptor {
	private Enhancer enhancer=new Enhancer();
	public Object getProxy(Class clazz){
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		return enhancer.create();
	}
	
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		PerformanceMonitor.begin(obj.getClass().getSimpleName()+"."+method.getName());
		Object result=proxy.invokeSuper(obj, args);
		PerformanceMonitor.end();
		return result;
	}

}
