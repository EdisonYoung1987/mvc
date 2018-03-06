package com.edison.testJunit.oth.springAop.springAop2;

import org.aopalliance.intercept.MethodInterceptor; //������ǿ�������
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

@Component
public class AroundAdvice implements MethodInterceptor{
	public Object invoke(MethodInvocation invocation) throws Throwable {//������ǿ
		String methodName=invocation.getMethod().getName();
		
		System.out.println("around ��ǿ������ʼ��"+methodName);
		invocation.proceed();
		System.out.println("around ��ǿ����������"+methodName);

		return null;
	}
}
