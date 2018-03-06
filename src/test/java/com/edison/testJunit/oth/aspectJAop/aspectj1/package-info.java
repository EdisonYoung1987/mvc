/**
 * 这个包主要是简单aspectJ的实现，通过@Aspect注解某个类为切面，然后通过@Before,@Around等等
 * 注解该类的方法为增强，同时定义增强方法的切入点@Before("execution(* greeTo(..))"),
 * 没有类的筛选，没有方法参数筛选，没有增强方法的顺序
 */
/**
 * @author Edison
 *
 */
package com.edison.testJunit.oth.aspectJAop.aspectj1;