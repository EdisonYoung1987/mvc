package com.edison.testJunit.oth.ii_2_XML.parse_big_xml.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.annotation.Root;
import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.base.Range;
import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.base.XmlBase;
import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.filter.XmlFilter;
import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.handler.XmlHandler;

public class Xml4bigKit {
	
	public final static int MAX=0;
	public final static int READ_LEN=20000;//String
	private static BufferedReader in;
	
	/**
	 * 
	 * @param xpath
	 * @param dataStartTag
	 * @param cls
	 * @param handle
	 * @param readLen
	 * @throws Exception 
	 */
	
	@SuppressWarnings("unchecked")
	public static void parseXml(String xpath,String dataStartTag,List<Class<? extends XmlBase>> cls,
			 XmlHandler handle,long readLen,XmlFilter xmlfilter) throws Exception{
		
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
			  
			  int tagMaxLen=0; //用于保存标签最大长度
			  for(Class<? extends XmlBase> cl:cls){//获取Class列表中类对应的标签，要么是注解名，要么是类简写
				  Root rt=cl.getAnnotation(Root.class);
		    	  String rtTag="";
		    	  if(rt!=null){
		    		  rtTag=rt.name();
		    	  }else{
		    		  rtTag=cl.getSimpleName();
		    	  }
		    	  if(rtTag.length()>tagMaxLen)
		    		  tagMaxLen=rtTag.length();
		    	  tagsList.add(rtTag);
			  }
			  
			  
			  char[] cbuf =new char[READ_LEN];
			  int count =0;
			  int rlen=0;
			  while ((rlen=in.read(cbuf))!=-1){
				  /**需考虑几种情况：
				   * 1.这个标签内容多，READ_LEN的内容装不下
				   * 2.这个标签内容少，READ_LEN里面有多个*/
				   if(rlen!=READ_LEN){
					   char[] tcbuf =new char[rlen];
					   System.arraycopy(cbuf, 0, tcbuf, 0, rlen);
					   sbcache.append(tcbuf);
				   }else{
					   sbcache.append(cbuf);
				   }
				   
				   parseXmlByStr(sbcache, tagsList, reMap, cls, handle, tagMaxLen,xmlfilter);
				   
				  count++;
				  if(count%1000==0){
					  System.out.println("handle count:"+count);
				  }
			  }
			  
		  }
	    } catch (Exception e) {
	    	throw e;
		}
		
		
	}
	
	
	/**
	 * @param filepath 待解析xml文件路径
	 * @param dataStartTag 起始标签: null-从根标签开始解析 "/root/abc"-从 &lt;root&gt;&lt;abc&gt;标签开始解析
	 * @param cls  待保存解析对象的类的list，暂时不支持同时解析多个标签内容到对象
	 * @param handle 传递解析好的List&lt;Object&gt;到外部调用对象
	 * @param xmlfilter 通过xml的Element进行过滤
	 * @throws Exception
	 */
	public static void parseXml(String filepath,String dataStartTag,List<Class<? extends XmlBase>> cls,
			XmlHandler handle,XmlFilter xmlfilter) throws Exception{
		if(cls.size()==0){
			throw new Exception("需要传入解析内容保存对象");
		}else if(cls.size()>1){
			throw new Exception("暂不支持多标签解析");
		}
		parseXml(filepath,dataStartTag,cls,handle,READ_LEN,xmlfilter);
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
	
	/**
	 * 针对读出的文件内容进行xml解析
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws DocumentException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException */
	public static void parseXmlByStr(StringBuffer sbcache, List<String> tagsList,
			  Map<String,List<Object>> reMap,List<Class<? extends XmlBase>> cls,XmlHandler handle,int tagMaxLen,
			  XmlFilter xmlfilter) 
					  throws InstantiationException, IllegalAccessException, ClassNotFoundException, DocumentException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		  Range range =Strkit.getRange(sbcache, tagsList);
		  if(range.getFrom()>=0){
			   String elementText="";
			   if(range.getTo()>0){//说明找到了一组对应标签
				   elementText=sbcache.substring(range.getFrom(), range.getTo()+1);
				   Document doc;
				   doc = DocumentHelper.parseText("<Root>"+elementText+"</Root>");  
					
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
				    			  if(xmlfilter!=null && !xmlfilter.accept(element)){ //只是过滤掉普通车辆:没有id属性
				    				  continue;
				    			  }
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
				  
			        //已经解析出来了一组，则将这一段从内存中删除 
			        sbcache= sbcache.delete(0,  range.getTo());
			   }
		  }else{//没找到起始标签，那这些内容都没有用(考虑到读取了起始标签一部分的情况<Ca、<Car等不能完整判断)
			  if(sbcache.length()>(tagMaxLen+1)){
				  sbcache= sbcache.delete(0, sbcache.length()-(tagMaxLen+1)); //还需保留"<"
			  }
		   }
		  return;
	}
}
