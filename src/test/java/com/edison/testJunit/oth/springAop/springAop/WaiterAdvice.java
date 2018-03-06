package com.edison.testJunit.oth.springAop.springAop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor; //������ǿ�������
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

public class WaiterAdvice implements MethodBeforeAdvice,AfterReturningAdvice,
	MethodInterceptor,ThrowsAdvice{
	//method ����ǿ�ķ��� args- ��������Ĳ��� arg2 -���������
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		if(method.getName().matches("greeTo")){
			System.out.println("before����ǿ��  ��ӭ����");
		}else{
			System.out.println("before ����ǿ����"+method.getName());
		}
	}

	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		if(method.getName().matches("serveTo")){
			System.out.println("afterReturning����ǿ��  ������");
		}else{
			System.out.println("afterReturning ����ǿ����"+method.getName());
		}
		
	}

	/*public Object intercept(Object target, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {//��������а�������ʵ��CGlib��̬����Ķ��������ǻ�����ǿ
		// TODO Auto-generated method stub
		System.out.println("����"+method.getName()+"������"+System.currentTimeMillis());
		return null;
	}*/

	public Object invoke(MethodInvocation invocation) throws Throwable {//������ǿ
		String methodName=invocation.getMethod().getName();
		Method methods=invocation.getMethod();
		Object[] args=invocation.getArguments(); //��ȡ����б�
		String custmor=(String)args[0];
		
		if(methodName.equals("greeTo")){
			System.out.println("around ӭ�ӿ��ˣ�"+custmor);
			invocation.proceed(); //ִ��ԭӳ�䷽��
			System.out.println("around ӭ�ӿ��ˣ�"+custmor);

		}else{
			System.out.println("around ������ˣ�"+custmor);
			invocation.proceed(); //ִ��ԭӳ�䷽��
			System.out.println("around ������ˣ�"+custmor);

		}
		
		return null;
	}
	
	//ThrowsAdvice��һ����ǩ�ӿڣ�û�з��������ԵĽӿڣ�����ʵ�������κ������ϵ�Ҫ��ֻ�Ǳ�ʶĳһ�ض���
	//��������ֻ����afterThrowing��ǰ����������ѡ������ֻ��ͬʱ���ڻ�ͬʱ�����ڣ�������ֻҪ��Throwable����
	//���༴��
	public void afterThrowing(Method method,Object[] args,Object target,Throwable throwable){
		System.out.println("���쳣��");
	}
}
