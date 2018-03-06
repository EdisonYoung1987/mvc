package com.edison.testJunit.oth.aspectJAop.aspectj1;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectJAdvisor {//@Componentע�������
	@Before(value="execution(* greeTo(..))") //Before��ʾ��λ��execution��AspectJ���е���ʽ������һ�����԰��������
	public void beforeGreeting(){
		System.out.println("ǰ����ǿ �������greeTo����");
	}
	
	@Around(value="execution(* serveTo(..))")
	public void aroundServing(ProceedingJoinPoint pjp) throws Throwable{ //�����������Ҫ��pjp���벢ִ�У�����ԭ����û��ִ�С�
		System.out.println("������ǿǰ���� �������serveTo����");
		pjp.proceed();
		System.out.println("������ǿ���� �������serveTo����");
	}
}
