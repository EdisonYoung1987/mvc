package com.edison.testJunit.oth.springAop.springAop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor; //环绕增强得用这个
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

public class WaiterAdvice implements MethodBeforeAdvice,AfterReturningAdvice,
	MethodInterceptor,ThrowsAdvice{
	//method 被增强的方法 args- 方法传入的参数 arg2 -被代理对象
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		if(method.getName().matches("greeTo")){
			System.out.println("before【增强】  欢迎光临");
		}else{
			System.out.println("before 不增强方法"+method.getName());
		}
	}

	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		if(method.getName().matches("serveTo")){
			System.out.println("afterReturning【增强】  请慢用");
		}else{
			System.out.println("afterReturning 不增强方法"+method.getName());
		}
		
	}

	/*public Object intercept(Object target, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {//这个好像不行啊，这是实现CGlib动态代理的东西，不是环绕增强
		// TODO Auto-generated method stub
		System.out.println("方法"+method.getName()+"被调用"+System.currentTimeMillis());
		return null;
	}*/

	public Object invoke(MethodInvocation invocation) throws Throwable {//环绕增强
		String methodName=invocation.getMethod().getName();
		Method methods=invocation.getMethod();
		Object[] args=invocation.getArguments(); //获取入参列表
		String custmor=(String)args[0];
		
		if(methodName.equals("greeTo")){
			System.out.println("around 迎接客人："+custmor);
			invocation.proceed(); //执行原映射方法
			System.out.println("around 迎接客人："+custmor);

		}else{
			System.out.println("around 服务客人："+custmor);
			invocation.proceed(); //执行原映射方法
			System.out.println("around 服务客人："+custmor);

		}
		
		return null;
	}
	
	//ThrowsAdvice是一个标签接口，没有方法和属性的接口，不对实现类有任何语义上的要求，只是标识某一特定类
	//其中名字只能是afterThrowing，前三个参数可选，不过只能同时存在或同时不存在，第三个只要是Throwable或其
	//子类即可
	public void afterThrowing(Method method,Object[] args,Object target,Throwable throwable){
		System.out.println("抛异常了");
	}
}
