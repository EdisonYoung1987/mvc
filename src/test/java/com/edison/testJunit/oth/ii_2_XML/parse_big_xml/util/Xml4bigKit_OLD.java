package com.edison.testJunit.oth.ii_2_XML.parse_big_xml.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.annotation.Root;
import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.base.Range;
import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.base.XmlBase;
import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.handler.XmlHandler;

public class Xml4bigKit_OLD {
	
	public final static int MAX=30;
	public final static int READ_LEN=20000;//String
	private static BufferedReader in;
	
	/**
	 * 
	 * @param xpath
	 * @param dataStartTag
	 * @param cls
	 * @param handle
	 * @param readLen
	 */
	
	@SuppressWarnings("unchecked")
	public static void parseXml(String xpath,String dataStartTag,List<Class<? extends XmlBase>> cls,XmlHandler handle,long readLen){
		
		 Map<String,List<Object>> reMap =new  HashMap<String,List<Object>>();
		  File file = new File(xpath);
		  long len=0; //MB
		  len=file.length()/1024*1024;
		  
	    try {
				
		  if(len<MAX){// small file
			 
					FileInputStream in = new FileInputStream(file);
					Reader reader=new InputStreamReader(in,"utf-8");
					
					//确定解析起始标签/父标签路径
					SAXReader saxReader = new SAXReader();
				    Document doc = saxReader.read(reader);
				    Element  dataElement =doc.getRootElement();
				    if(dataStartTag!=null){
				    	dataElement=getNodeElement(dataElement,dataStartTag);//定位到要解析的标签页的父标签处，如需解析
				    		// /Cars/car  这里就传入"/Cars",表示解析/Cars下面的/car,而不是/car或者/Cars/oth/car
				    }
				    
				    for(Class<? extends XmlBase> cl:cls){
				    	
				    	  Root rt=cl.getAnnotation(Root.class);
				    	  String rtTag="";
				    	  if(rt!=null){
				    		  rtTag=rt.name();
				    		  
				    	  }else{
				    		  
				    		  rtTag=cl.getSimpleName();
				    		  
				    	  }//确定待解析标签名称，要么是类的注解name，要么是类名
				    	  List<Element> listElement = dataElement.elements(rtTag);
				    	  if(listElement!=null){
				    		  List<Object> list = new ArrayList<Object>();
				    		  for(Element element:listElement){
				    			  Object obj =Class.forName(cl.getName()).newInstance();
						    	  String proccessMethodName = "parseXml";
						    	  Method  proccessMethod = obj.getClass().getMethod(proccessMethodName, Element.class);
						    	  proccessMethod.invoke(obj, element);
						    	  list.add(obj);
				    		  }
				    		  reMap.put(rtTag, list);
				    	  }
				    	 
				    }
				    handle.hande(reMap);
				
		  }else{
			  // big file
			  Reader reader=new InputStreamReader(new FileInputStream(file),"utf-8");
			  in = new BufferedReader(reader);
			  StringBuffer sbcache = new StringBuffer();
			  List<String> tagsList = new ArrayList<String>();
			  
			  for(Class<? extends XmlBase> cl:cls){//获取Class列表中类对应的标签，要么是注解名，要么是类简写
				  Root rt=cl.getAnnotation(Root.class);
		    	  String rtTag="";
		    	  if(rt!=null){
		    		  rtTag=rt.name();
		    	  }else{
		    		  rtTag=cl.getSimpleName();
		    	  }
		    	  tagsList.add(rtTag);
			  }
			  
			  
			  char[] cbuf =new char[READ_LEN];
			  int count =0;
			  int rlen=0;
			  while ((rlen=in.read(cbuf))!=-1){
				   if(rlen!=READ_LEN){
					   char[] tcbuf =new char[rlen];
					   System.arraycopy(cbuf, 0, tcbuf, 0, rlen);
					   sbcache.append(tcbuf);
				   }else{
					   sbcache.append(cbuf);
				   }
				   
				   Range range =Strkit.getRange(sbcache, tagsList);
				  if(range.getFrom()>0){
					  String elementText="";
					  try{
						  
						elementText=sbcache.substring(range.getFrom(), range.getTo());
						Document doc;
						try{
							doc = DocumentHelper.parseText("<Root>"+elementText+"</Root>");  
						}catch(Exception e){
							e.printStackTrace();
							System.out.println(elementText);
							return;
						}
				        Element elem = doc.getRootElement(); 
				        reMap.clear();
				        
				        for(Class<? extends XmlBase> cl:cls){
					    	
					    	  Root rt=cl.getAnnotation(Root.class);
					    	  String rtTag="";
					    	  if(rt!=null){
					    		  rtTag=rt.name();
					    	  }else{
					    		  rtTag=cl.getSimpleName();
					    		  
					    	  }
					    	  List<Element> listElement = elem.elements(rtTag);
					    	  if(listElement!=null){
					    		  List<Object> list =  reMap.get(rtTag);
					    		  if(list == null){
					    			  list = new ArrayList<Object>();
					    			  
					    		  }  
					    		  for(Element element:listElement){
					    			  Object obj =Class.forName(cl.getName()).newInstance();
							    	  String proccessMethodName = "parseXml";
							    	  Method  proccessMethod = obj.getClass().getMethod(proccessMethodName, Element.class);
							    	  proccessMethod.invoke(obj, element);
							    	  list.add(obj);
					    		  }
					    		  reMap.put(rtTag, list);
					    	  }
					    	 
					    }
				        handle.hande(reMap);
					  }catch(Exception e){
						  e.printStackTrace();
						 
						  System.out.println(elementText);
					  }
					  
				  
					  sbcache= sbcache.delete(0,  range.getTo());
				  }else{
					  sbcache= sbcache.delete(0, sbcache.length());
				  }
				
				  count++;
				  if(count%1000==0){
					  System.out.println("handle count:"+count);
				  }
			  }
			  
			 
			 
			  
			  
		  }
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void parseXml(String xpath,String dataStartTag,List<Class<? extends XmlBase>> cls,XmlHandler handle){
		 parseXml(xpath,dataStartTag,cls,handle,READ_LEN);
	}
	
	
	
	private static Element getNodeElement(Element element,String tags){
		if(element==null)return element;
		
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
	

}
