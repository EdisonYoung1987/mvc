package com.edison.testJunit.oth;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;

import com.edison.db.entity.User;

/**
 * ���ڲ��Է���*/
public class TestReflect {

	@Test
	public void testReflect() {
		try{
			ClassLoader cloader=Thread.currentThread().getContextClassLoader(); //1.��ȡ��ǰ�̵߳��������
			Class clazz=cloader.loadClass("com.edison.db.entity.User");         //2.����ָ��·������
			
			Constructor cons=clazz.getDeclaredConstructor((Class[])null);
			User user=(User)cons.newInstance();                                 //newInstance����ʵ��

			Field field=clazz.getDeclaredField("userName") ;                    //3.��ȡ��������
			field.setAccessible(true);                                          //4.private������Ҫ����setȨ��
			field.set(user, "����");                                           	//5.����ֵ����һ������Ϊ������������

			Method method=clazz.getDeclaredMethod("toString", (Class[])null);

			String res=(String) method.invoke(user,(Object[])null);             //��һ������Ϊ������������
			System.out.println(res);

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
