package com.edison.testJunit.oth;

import java.awt.List;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;

import oracle.sql.CHAR;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import com.edison.db.entity.User;
import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.annotation.Column;
import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.annotation.IsAttr;

/**
 * 用于测试反射*/
public class TestReflect {

	@Test
	public void testReflect() {
		try{
			ClassLoader cloader=Thread.currentThread().getContextClassLoader(); //1.获取当前线程的类加载器
			Class clazz=cloader.loadClass("com.edison.db.entity.User");         //2.加载指定路径的类
			
			Constructor cons=clazz.getDeclaredConstructor((Class[])null);
			Object object=cons.newInstance();                                 //newInstance生成实例

			Field field=clazz.getDeclaredField("userName") ;                    //3.获取对象属性
			field.setAccessible(true);                                          //4.private属性需要赋予set权限
			field.set(object, "张三");                                           	//5.设置值，第一个参数为属性所属对象

			Method method=clazz.getDeclaredMethod("toString", (Class[])null);

			String res=(String) method.invoke(object,(Object[])null);             //第一个参数为属性所属对象
			System.out.println(res);
			
			//解析一段xml内容并放入Car对象中
		    String content="<Car id=\"123\"><type>奔驰MMM</type><product>德国</product><price>55.2</price><carEngine><core>16</core><type>Intel</type></carEngine><carEngine><core>16</core><type>Intel</type></carEngine></Car>";
		    Document doc=DocumentHelper.parseText(content);
		    Element carEle=doc.getRootElement();
		    
		    //加载类并初始化一个对象 1
		    clazz=cloader.loadClass("com.edison.testJunit.oth.ii_2_XML.Car");  
		    Object obj=clazz.newInstance();//还有下面一种方式
		    
		    //加载类并初始化一个对象 2
		    //Object obj=Class.forName(cl.getName()).newInstance();
		    
		    //调用解析方法 将该Element解析到对应的对象中
		    parseXml(obj, carEle);
		   
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**将currElement中的xml内容解析到obj对象中*/
	public static void parseXml(Object obj,Element currElement){
		Class<?> clazz=obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field fieldtmp : fields) {// 给每个域赋值
			// 获取该属性在xml文件中实际对应的表达式，如属性id，可能在xml中是iD
			String xmlNodeName = "";
			IsAttr isAttr = fieldtmp.getAnnotation(IsAttr.class);
			Column column = null;

			if (isAttr != null) {// 表明该属性注解了IsAttr，说明从<Car>标签的属性取值 如<Car id="1">
				xmlNodeName = isAttr.name();// 通过注解的name获取该属性在xml标签中的实际表达式
			} else {
				column = fieldtmp.getAnnotation(Column.class);// 看是否注解了Column，表明取值从子标签取
				if (column != null) {
					xmlNodeName = column.name();
				}
			}

			if ("".equals(xmlNodeName.trim())) {
				xmlNodeName = fieldtmp.getName(); // 标签名和属性名相同
			}
			System.out.println("xmlNodeName=" + xmlNodeName);

			// 获取值
			Class<?> fieldType = fieldtmp.getType();
			System.out.println("fieldType=" + fieldType);

			if (isAttr != null) {// 从标签属性取值，这种只考虑常用类型:基本类型及其包装类 还有String
									// 以及Bigdecimal
				String textValue = currElement.attributeValue(xmlNodeName);
				if (textValue == null || "".equals(textValue)) {
					continue;
				}
				if (isBaseType(fieldType)) {// 是常用类型 属性这块暂时只支持这些类型
					setBaseValue(obj, fieldtmp, textValue); // 针对常用类型赋值
				}
			} else {// 从子标签解析值
				if(isBaseType(fieldType)) {
					Element ele = currElement.element(xmlNodeName);
					if (ele == null) {
						continue;
					}
					String textValue = ele.getText();
					setBaseValue(obj, fieldtmp, textValue);
				}else if(isListType(fieldType)) {// List相关类型
					System.out.println("是list类型");
					setListValue(obj, fieldtmp, currElement);
				}else {
					System.out.println("是普通对象类型");
					Class<?> subclazz=fieldtmp.getClass();//属性值的类型
					try{
						Object subObj=subclazz.newInstance();
					}catch(Exception e){
						System.out.println("创建该类型对象失败"+subclazz.getName());
						continue;
					}
					parseXml(subobj, currElement);
				}
			}
		}
	}
	
	/**判断是否是常用类型: 基本类型或其包装类 或者String类 或者Bigdecimal*/
	public static boolean isBaseType(Class<?> fieldType){
		if(fieldType==String.class || fieldType==BigDecimal.class||
			fieldType == Integer.class || fieldType == Byte.class||fieldType==Boolean.class||fieldType == Boolean.class
			||fieldType == Float.class || fieldType == Double.class ||fieldType == CHAR.class||fieldType == Long.class
			||fieldType==int.class||fieldType==byte.class||fieldType==char.class||fieldType==short.class
			||fieldType==boolean.class||fieldType==double.class||fieldType==float.class||fieldType==long.class){
			return true;
		}
		return false;
	}
	
	/**针对常用类型进行赋值
	 * @param obj 待赋值的对象
	 * @param field 待赋值对象的属性
	 * @param valueStr 值*/
	public static void setBaseValue(Object obj, Field field,String valueStr){
		System.out.println("Field="+field.getName());
		//获取obj.setXxx(xxx)方法
		String fieldName=field.getName();
		String firstCh=fieldName.substring(0, 1);
		String methodName="set"+firstCh.toUpperCase()+fieldName.substring(1);
		Class<?> fieldType=field.getType();
		Method method;
		try{
			method=obj.getClass().getDeclaredMethod(methodName,fieldType );
		
			//根据类型赋值
			if(fieldType==BigDecimal.class){//BigDecimal
				BigDecimal value=new BigDecimal(valueStr);
				method.invoke(obj, value);
			}else if(fieldType == int.class){//基本类型 按频率排序
				int value=Integer.valueOf(valueStr);
				method.invoke(obj, value);
			}else if(fieldType==long.class){
				long value=Long.valueOf(valueStr);
				method.invoke(obj, value);
			}else if(fieldType==float.class){
				float value=Float.valueOf(valueStr);
				method.invoke(obj, value);
			}else if(fieldType==double.class){
				double value=Double.valueOf(valueStr);
				method.invoke(obj, value);
			}else if(fieldType==boolean.class){
				boolean value=Boolean.valueOf(valueStr);
				method.invoke(obj, value);
			}else if(fieldType==char.class){
				char value=valueStr.charAt(0);
				method.invoke(obj, value);
			}else if(fieldType==byte.class){
				byte value=Byte.valueOf(valueStr);
				method.invoke(obj, value);
			}else if(fieldType==short.class){
				short value=Short.valueOf(valueStr);
				method.invoke(obj, value);
			}else{//其他包装类型以及String 都有String作为参数的构造函数
				System.out.println("其他类型 valueStr="+valueStr);
				//Constructor cons=fieldType.getConstructor(String.class);
				method.invoke(obj,fieldType.getConstructor(String.class).newInstance(valueStr) ); //有参构造函数的调用
			}
		}catch(Exception e){ //该对象没有针对该属性的set方法 或者 其他异常
			e.printStackTrace();
			System.out.println(e.getClass().getSimpleName()+": "+e.getMessage());
			return;
		}
		return ;
	}
	
	/**判断是否是常用的List类型:如List ArrayList  LinkedList */
	public static boolean isListType(Class<?> fieldType){
		if(fieldType==List.class || fieldType==ArrayList.class||fieldType == LinkedList.class ){
			return true;
		}
		return false;
	}
	
	/**针对常用List类型进行赋值
	 * @param obj 待赋值的对象
	 * @param field 待赋值对象的属性
	 * @param rootEle 当前主节点*/
	public static void setListValue(Object obj, Field field,Element rootEle){
		System.out.println("对List赋值了");
	}
}
