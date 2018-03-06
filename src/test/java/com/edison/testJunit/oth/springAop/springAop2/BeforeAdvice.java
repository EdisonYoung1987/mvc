package com.edison.testJunit.oth.springAop.springAop2;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;
//������ǿ�������

@Component
public class BeforeAdvice implements MethodBeforeAdvice{
	//method ����ǿ�ķ��� args- ��������Ĳ��� arg2 -���������
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
			System.out.println("before ��ǿ������"+method.getName());
	}
}
