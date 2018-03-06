package com.edison.testJunit.oth.aspectJAop.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Test;

public class TestAnnotation {
	@Test
	public void testAnnotation() {
		try{
			Class clazz=ClassAnnotationed.class;
//			Object obj=clazz.newInstance();
			Method[] methods=clazz.getDeclaredMethods();
			
			for(Method method:methods){
				Annotation[] annotations=method.getAnnotations();
				System.out.println("��������"+method.getName());
				
				for(Annotation annotation:annotations){
					System.out.println("ӵ��ע�⣺annotation.getClass()="+annotation.getClass());
					System.out.println("ӵ��ע�⣺"+annotation.annotationType().getSimpleName());
//					if("MyAnnotation".equals(annotation.getClass().getSimpleName())){
					if(annotation.annotationType().equals(MyAnnotation.class)){
						MyAnnotation myAnnotation=(MyAnnotation)annotation; //ֱ��ǿ��ת���Ϳ�����
						System.out.println("value="+myAnnotation.value());
						System.out.println("editable="+myAnnotation.editable());
						System.out.println("id="+myAnnotation.id());
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
