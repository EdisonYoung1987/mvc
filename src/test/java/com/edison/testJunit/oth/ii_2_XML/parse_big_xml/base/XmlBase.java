package com.edison.testJunit.oth.ii_2_XML.parse_big_xml.base;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import oracle.sql.CHAR;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.annotation.IsAttr;
import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.annotation.Column;
public  class XmlBase {
	
	public void parseXml(Element elm) {
		
		if (elm == null)
			return;
		Field[] fields = this.getClass().getDeclaredFields();
		if (fields == null)
			return;
		try {
			for (Field field : fields) {
	
				Column an4field = field.getAnnotation(Column.class);//获取子标签注解
				IsAttr isAttr=field.getAnnotation(IsAttr.class);//获取属性注解
				String xmlNodeName = null; //属性名或者节点名
				if(an4field!=null){
					xmlNodeName = an4field.name();
				}else{
					if(isAttr!=null){
						xmlNodeName=isAttr.name();
					}
					xmlNodeName = field.getName();
					 
				}
				
				Class<?> fieldType = field.getType();
				
				if (XmlBase.class.isAssignableFrom(fieldType)) {// if it is XmlBase subclass
													// then go recursive
					
					Element targer_elm = getNodeElement(elm,xmlNodeName);
					if(targer_elm!=null){
					    String className=field.getType().getCanonicalName();
					    Object obj = Class.forName(className).newInstance();
					    
						Method  proccessMethod = obj.getClass().getMethod("parseXml",Element.class);	
						proccessMethod.invoke(obj, targer_elm);
						
						String firstLetter=field.getName().substring(0, 1).toUpperCase();
						String setMethodName = "set" + firstLetter + field.getName().substring(1); 		
						Method setMethod = this.getClass().getMethod(setMethodName, new Class[] {obj.getClass()});
						setMethod.invoke(this, new Object[] {obj});
					}	
				   
	
				} else if (fieldType == List.class) {//获取list的泛型实际类型
					Type fc = field.getGenericType(); 
					ParameterizedType pt = (ParameterizedType) fc;  
				    Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
	
				    if(genericClazz == String.class || genericClazz == Integer.class || genericClazz == Long.class
						|| genericClazz == Float.class || genericClazz == Double.class){
				
						String firstLetter=field.getName().substring(0, 1).toUpperCase();
						List<Element> ls_elm=getNodeElements(elm,xmlNodeName);
						
						if(ls_elm==null||ls_elm.size()==0){
							continue;
						}
						
						String setMethodName = "set" + firstLetter + field.getName().substring(1); 
						
						if(genericClazz == String.class ){
							List<String> list =new ArrayList<String>();
	
							for(Element item:ls_elm){
								String vl=item.getText();
								if(vl!=null){
									vl=vl.trim();
								}
								vl=parseFilter(vl);
								list.add(vl);
								
							}
							
							Method setMethod = this.getClass().getMethod(setMethodName,List.class);
							setMethod.invoke(this, list);
							
						}else if(fieldType == Integer.class){
							List<Integer> list =new ArrayList<Integer>();
	
							for(Element item:ls_elm){
								String vl=item.getText();
								if(vl!=null){
									vl=vl.trim();
								}
								vl=parseFilter(vl);
								list.add(Integer.valueOf(vl));
								
							}
							
							
							
							Method setMethod = this.getClass().getMethod(setMethodName,List.class);
							setMethod.invoke(this, list);
							
						  
							
						}else if(fieldType == Long.class){
	
							List<Long> list =new ArrayList<Long>();
	
							for(Element item:ls_elm){
								String vl=item.getText();
								if(vl!=null){
									vl=vl.trim();
								}
								vl=parseFilter(vl);
								list.add(Long.valueOf(vl));
								
							}
							
							
							
							Method setMethod = this.getClass().getMethod(setMethodName,List.class);
							setMethod.invoke(this, list);
							
						  
							
						
						}else if(fieldType == Float.class){
	
							List<Float> list =new ArrayList<Float>();
	
							for(Element item:ls_elm){
								String vl=item.getText();
								if(vl!=null){
									vl=vl.trim();
								}
								vl=parseFilter(vl);
								list.add(Float.valueOf(vl));
								
							}
							
							
							
							Method setMethod = this.getClass().getMethod(setMethodName,List.class);
							setMethod.invoke(this, list);
							
						  
							
						}else if(fieldType == Double.class){
							List<Double> list =new ArrayList<Double>();
	
							for(Element item:ls_elm){
								String vl=item.getText();
								if(vl!=null){
									vl=vl.trim();
								}
								vl=parseFilter(vl);
								list.add(Double.valueOf(vl));
								
							}
							
							
							
							Method setMethod = this.getClass().getMethod(setMethodName,List.class);
							setMethod.invoke(this, list);
							
						  
							
						}else{
							List<String> list =new ArrayList<String>();
	
							for(Element item:ls_elm){
								String vl=item.getText();
								if(vl!=null){
									vl=vl.trim();
								}
								vl=parseFilter(vl);
								list.add(vl);
								
							}
							
							Method setMethod = this.getClass().getMethod(setMethodName,List.class);
							setMethod.invoke(this, list);
						}
						
				    }else{// list<Object>
						String firstLetter=field.getName().substring(0, 1).toUpperCase();
						List<Element> ls_elm=getNodeElements(elm,xmlNodeName);
						
						if(ls_elm==null||ls_elm.size()==0){
							continue;
						}
						List<Object> list = new ArrayList<Object>();
						for(Element e:ls_elm){
							Object obj =Class.forName(genericClazz.getName()).newInstance();
							Method  proccessMethod = obj.getClass().getMethod("parseXml", Element.class);	
							proccessMethod.invoke(obj, e);
							list.add(obj);
						}
						String setMethodName = "set" + firstLetter + field.getName().substring(1); 		
						Method setMethod = this.getClass().getMethod(setMethodName,List.class);
						setMethod.invoke(this, list);
				    }
				} else if (fieldType == String.class || fieldType == Integer.class || fieldType == Long.class||fieldType==Boolean.class
					|| fieldType == Float.class || fieldType == Double.class ||fieldType == CHAR.class||fieldType == Byte.class
					||fieldType==int.class||fieldType==byte.class||fieldType==char.class||fieldType==short.class
					||fieldType==boolean.class||fieldType==double.class||fieldType==float.class||fieldType==long.class) {
					//TODO 基本类型及其包装类型的判断以及赋值最好提取出来
					String textvalue=null;
					Object value=null;
					if(isAttr!=null){//说明是属性
						Attribute att=elm.attribute(xmlNodeName);
						if(att!=null){
							textvalue=att.getValue();
						}
					}else{//说明是子节点
						Element targer_elm = getNodeElement(elm,xmlNodeName);
						if (targer_elm != null) {
							textvalue = targer_elm.getText();
						}
					}
					value=textvalue;

					if(textvalue!=null){		
						String firstLetter = field.getName().substring(0, 1).toUpperCase();
						String setMethodName = "set" + firstLetter + field.getName().substring(1);
						
						if(fieldType == Integer.class||fieldType == int.class){
							value=Integer.valueOf(textvalue);
							this.getClass().getMethod(setMethodName, fieldType).invoke(this, value);
						}else if(fieldType == Long.class||fieldType == long.class){
							value=Long.valueOf(textvalue);
							this.getClass().getMethod(setMethodName, fieldType).invoke(this, value);
						}else if(fieldType == Short.class||fieldType == short.class){
							value=Short.valueOf(textvalue);
							this.getClass().getMethod(setMethodName, fieldType).invoke(this, value);
						}else if(fieldType == Float.class||fieldType == float.class){
							value=Float.valueOf(textvalue);
							this.getClass().getMethod(setMethodName, fieldType).invoke(this, value);
						}else if(fieldType == Double.class||fieldType == double.class){
							value=Double.valueOf(textvalue);
							this.getClass().getMethod(setMethodName, fieldType).invoke(this, value);
						}else{
							this.getClass().getMethod(setMethodName, fieldType).invoke(this, value);
						}
					}
				} else {
					System.out.println("当前类型不支持<未继承XmlBase类>:"+fieldType);
					// not implements
				}
				
				
	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**通过第二个参数"ss/sss"获取子标签
	 * */
	private static Element getNodeElement(Element element,String tags){
		if(element==null)
			return element;
		
		Element tmp = element;

		if (tags.indexOf("/") > 0) {
			String[] arr = tags.split("/");
			for (int j = 0; j < arr.length; j++) {
				String sname = arr[j];
				tmp = tmp.element(sname);
				if (tmp == null)
					break;
			}

		} else {
			tmp = tmp.element(tags);
		}
		return tmp;
	}
	
	
	@SuppressWarnings("unchecked")
	private static List<Element> getNodeElements(Element element,String tags){
		if(element==null)return null;
		
		Element tmp = element;

		if (tags.indexOf("/") > 0) {
			String[] arr = tags.split("/");
			for (int j = 0; j < arr.length; j++) {
				String sname = arr[j];
				tmp = tmp.element(sname);
				if (tmp == null)
					break;
			}
			return tmp.elements();

		} else {
			return tmp.elements(tags);
		}
		
	}

	private static String parseFilter(String s) {
		if (s == null)return null;
		s = s.replaceAll("&quot;", "\"");
		s = s.replaceAll("&apos;", "'");
		s = s.replaceAll("&lt;", "<");
		s = s.replaceAll("&gt;", ">");
		s = s.replaceAll("&amp;", "&");
		return s;
	}

//	private static String formatFilter(String s) {
//		if (s == null)
//			return null;
//		s = s.replaceAll("&", "&amp;");
//		s = s.replaceAll("\"", "&quot;");
//		s = s.replaceAll("'", "&apos;");
//		s = s.replaceAll("<", "&lt;");
//		s = s.replaceAll(">", "&gt;");
//		return s;
//	}

}
