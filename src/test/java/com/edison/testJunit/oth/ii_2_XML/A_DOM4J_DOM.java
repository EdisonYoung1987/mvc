package com.edison.testJunit.oth.ii_2_XML;

import java.net.URL;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 使用dom原理读出整个xml文件，dom:将整个xml加载并解析，耗内存，灵活*/
public class A_DOM4J_DOM {
	static final String FILE="Mapper.xml";
	static final String BLANK="    ";
	
	public static void main(String[] args) {
		URL url=Thread.currentThread().getContextClassLoader().getResource("xml_parser/Mapper.xml"); //不能以’/'开头，path是从ClassPath根下获取 
														//file:/C:/Users/Edison/git/mvc/target/classes/xml_parser/Mapper.xml
		URL url2 = A_DOM4J_DOM.class.getResource(""); //当前类的包路径 file:/C:/Users/Edison/git/mvc/target/test-classes/com/edison/testJunit/oth/ii_2_XML/
		URL url3 = A_DOM4J_DOM.class.getResource("/"); //当前ClassPath的路径 file:/C:/Users/Edison/git/mvc/target/test-classes/
		URL url4 = A_DOM4J_DOM.class.getResource("/springContext.xml");	//如果指定了文件名，则先查找target/test-classes，再查找target/classes
													   //输出为 file:/C:/Users/Edison/git/mvc/target/classes/springContext.xml											
		System.out.println(url.toString());
		System.out.println(url2.toString());
		System.out.println(url3.toString());
		System.out.println(url4.toString());
		
		try{
			SAXReader reader=new SAXReader();
			Document doc=reader.read(url);
			Element root=doc.getRootElement();
			parseAndPrint(root, 1);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**递归调用方法解析打印xml文件
	 * @param element 标签
	 * @param level  递归层数，用于打印时的退格控制*/
	public static void parseAndPrint(Element element,int level){
		
	}
	
	/**根据递归层级打印起始退格*/
	public static String getBlank(int level){
		StringBuilder 
	}
}
