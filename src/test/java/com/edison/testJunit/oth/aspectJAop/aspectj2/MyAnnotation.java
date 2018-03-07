package com.edison.testJunit.oth.aspectJAop.aspectj2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //表示注解的生命周期:RUNTIME运行期-jvm||CLASS-.class有,jvm没有||SOURCE-源码有,.class文件没有
@Target(ElementType.METHOD)  //表示方法注解，还有FIELD-域注解 PARAMETER-参数注解 TYPE-类 接口等注解 等等
public @interface MyAnnotation { //必须用@interface修饰，而不是interface
	//定义三个成员变量
	String value() default "";
	
	boolean editable() default false;
	
	int id() default 0;
}
