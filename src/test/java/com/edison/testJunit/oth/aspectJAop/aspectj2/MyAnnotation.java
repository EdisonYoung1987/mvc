package com.edison.testJunit.oth.aspectJAop.aspectj2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //��ʾע�����������:RUNTIME������-jvm||CLASS-.class��,jvmû��||SOURCE-Դ����,.class�ļ�û��
@Target(ElementType.METHOD)  //��ʾ����ע�⣬����FIELD-��ע�� PARAMETER-����ע�� TYPE-�� �ӿڵ�ע�� �ȵ�
public @interface MyAnnotation { //������@interface���Σ�������interface
	//����������Ա����
	String value() default "";
	
	boolean editable() default false;
	
	int id() default 0;
}
