package com.edison.testJunit.oth.aspectJAop.aspectj1;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectJAdvisor {//@Component注解别忘了
	@Before(value="execution(* greeTo(..))") //Before表示方位，execution是AspectJ的切点表达式，在下一个测试包里面详解
	public void beforeGreeting(){
		System.out.println("前置增强 所有类的greeTo方法");
	}
	
	@Around(value="execution(* serveTo(..))")
	public void aroundServing(ProceedingJoinPoint pjp) throws Throwable{ //这个方法必须要有pjp传入并执行，否则原方法没有执行。
		System.out.println("环绕增强前处理 所有类的serveTo方法");
		pjp.proceed();
		System.out.println("环绕增强后处理 所有类的serveTo方法");
	}
}
