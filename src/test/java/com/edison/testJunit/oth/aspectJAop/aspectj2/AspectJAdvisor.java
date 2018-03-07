package com.edison.testJunit.oth.aspectJAop.aspectj2;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectJAdvisor implements Ordered{//@Component注解别忘了
	//这个是定义切面类的顺序的，假设有多个切面类，如果切点有交叉部分，那么order顺序小的先织入。
	public int getOrder() {
		return 0;
	}
	
	//Before表示方位，execution是AspectJ的切点表达式的函数，
	//表示目标类执行某方法,只匹配方法名
	@Before(value="execution(* com.edison.testJunit.oth.aspectJAop.aspectj2.Waiter.greeTo(..))") 
	public void beforeGreeting(){
		System.out.println("前置增强 Waiter类的greeTo方法");
	}
	
	//这里切点表达式用的@annotation，表示只有方法使用了该注解的就进行环绕增强
	@Around(value="@annotation(com.edison.testJunit.oth.aspectJAop.aspectj2.MyAnnotation)")
	public void aroundServing(ProceedingJoinPoint pjp) throws Throwable{ //这个方法必须要有pjp传入并执行，
																		//否则原方法没有执行。
		System.out.println("环绕增强前处理 Seller类的serveTo方法");
		pjp.proceed();
		System.out.println("环绕增强后处理 Seller类的serveTo方法");
	}
	
	//args()是AspectJ的切点表达式的函数，目标方法只有一个Waiter入参
	@Before(value="args(com.edison.testJunit.oth.aspectJAop.aspectj2.Waiter)") 
	public void beforeGuideTo(){
		System.out.println("前置增强 所有类的xxxx(Waiter)方法");
	}
	
	//@Around(value="@args(com.edison.testJunit.oth.aspectJAop.aspectj2.MyAnnotation)")
	//这个就表示如果某方法的入参只有一个且标注了@MyAnnotation注解，就会被增强，代码就不上了
	
	
	
	//复合切点定义-与运算 && 
	//within指定该路径下以Waiter结尾的类，
	//execution指定该类中名字为serveTo的方法
	@Before(value="within(com.edison.testJunit.oth.aspectJAop.aspectj2.*Waiter) "
			+ "&& execution(* serveTo(..))") 
	public void beforeServing(){
		System.out.println("前置增强 Waiter类的serveTo方法");
	}

	//target(com.edison.testJunit.oth.aspectJAop.aspectj2.Waiter)则是匹配Waiter类及其子孙类
	
	//绑定连接点参数
	@Before(value="execution(* com.edison.testJunit.oth.aspectJAop.aspectj2.Waiter.greeTo(..))"
			+ " && args(name)") 
	public void beforeGreeting2(String name){//前面args(name)的类型就是java.lang.String了
		System.out.println("前置增强 Waiter类的greeTo方法，同时获取到切点的入参："+name);
	}
}
