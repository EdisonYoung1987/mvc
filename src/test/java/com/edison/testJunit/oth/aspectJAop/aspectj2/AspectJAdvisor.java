package com.edison.testJunit.oth.aspectJAop.aspectj2;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectJAdvisor implements Ordered{//@Componentע�������
	//����Ƕ����������˳��ģ������ж�������࣬����е��н��沿�֣���ôorder˳��С����֯�롣
	public int getOrder() {
		return 0;
	}
	
	//Before��ʾ��λ��execution��AspectJ���е���ʽ�ĺ�����
	//��ʾĿ����ִ��ĳ����,ֻƥ�䷽����
	@Before(value="execution(* com.edison.testJunit.oth.aspectJAop.aspectj2.Waiter.greeTo(..))") 
	public void beforeGreeting(){
		System.out.println("ǰ����ǿ Waiter���greeTo����");
	}
	
	//�����е���ʽ�õ�@annotation����ʾֻ�з���ʹ���˸�ע��ľͽ��л�����ǿ
	@Around(value="@annotation(com.edison.testJunit.oth.aspectJAop.aspectj2.MyAnnotation)")
	public void aroundServing(ProceedingJoinPoint pjp) throws Throwable{ //�����������Ҫ��pjp���벢ִ�У�
																		//����ԭ����û��ִ�С�
		System.out.println("������ǿǰ���� Seller���serveTo����");
		pjp.proceed();
		System.out.println("������ǿ���� Seller���serveTo����");
	}
	
	//args()��AspectJ���е���ʽ�ĺ�����Ŀ�귽��ֻ��һ��Waiter���
	@Before(value="args(com.edison.testJunit.oth.aspectJAop.aspectj2.Waiter)") 
	public void beforeGuideTo(){
		System.out.println("ǰ����ǿ �������xxxx(Waiter)����");
	}
	
	//@Around(value="@args(com.edison.testJunit.oth.aspectJAop.aspectj2.MyAnnotation)")
	//����ͱ�ʾ���ĳ���������ֻ��һ���ұ�ע��@MyAnnotationע�⣬�ͻᱻ��ǿ������Ͳ�����
	
	
	
	//�����е㶨��-������ && 
	//withinָ����·������Waiter��β���࣬
	//executionָ������������ΪserveTo�ķ���
	@Before(value="within(com.edison.testJunit.oth.aspectJAop.aspectj2.*Waiter) "
			+ "&& execution(* serveTo(..))") 
	public void beforeServing(){
		System.out.println("ǰ����ǿ Waiter���serveTo����");
	}

	//target(com.edison.testJunit.oth.aspectJAop.aspectj2.Waiter)����ƥ��Waiter�༰��������
	
	//�����ӵ����
	@Before(value="execution(* com.edison.testJunit.oth.aspectJAop.aspectj2.Waiter.greeTo(..))"
			+ " && args(name)") 
	public void beforeGreeting2(String name){//ǰ��args(name)�����;���java.lang.String��
		System.out.println("ǰ����ǿ Waiter���greeTo������ͬʱ��ȡ���е����Σ�"+name);
	}
}
