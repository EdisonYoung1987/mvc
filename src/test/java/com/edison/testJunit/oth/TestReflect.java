package com.edison.testJunit.oth;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;

import com.edison.db.entity.User;

/**
 * 用于测试反射*/
public class TestReflect {

	@Test
	public void testReflect() {
		try{
			ClassLoader cloader=Thread.currentThread().getContextClassLoader(); //1.获取当前线程的类加载器
			Class clazz=cloader.loadClass("com.edison.db.entity.User");         //2.加载指定路径的类
			
			Constructor cons=clazz.getDeclaredConstructor((Class[])null);
			User user=(User)cons.newInstance();                                 //newInstance生成实例

			Field field=clazz.getDeclaredField("userName") ;                    //3.获取对象属性
			field.setAccessible(true);                                          //4.private属性需要赋予set权限
			field.set(user, "张三");                                           	//5.设置值，第一个参数为属性所属对象

			Method method=clazz.getDeclaredMethod("toString", (Class[])null);

			String res=(String) method.invoke(user,(Object[])null);             //第一个参数为属性所属对象
			System.out.println(res);

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
