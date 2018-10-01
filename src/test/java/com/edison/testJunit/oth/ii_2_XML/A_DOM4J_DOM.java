package com.edison.testJunit.oth.ii_2_XML;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 * 使用dom原理读出整个xml文件，dom:将整个xml加载并解析，耗内存，灵活
 * 处理一个760M的xml文件时，仅是Document doc=reader.read(url); cpu几乎被占满，运行耗时--跑了快20分钟也没跑完。。*/
public class A_DOM4J_DOM {
	static final String FILE="Mapper.xml";
	static final String BLANK="    ";
	private static final String FILEOUT="xml_parser/big.xml";

	
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
//		url=Thread.currentThread().getContextClassLoader().getResource(FILEOUT);//超大文件
		try{
			SAXReader reader=new SAXReader();
			System.out.println(System.currentTimeMillis());
			Document doc=reader.read(url);
			//doctype读取 
			/*System.out.println("DocType="+doc.getDocType().getElementName()+" "+doc.getDocType().getPublicID()
					+" "+doc.getDocType().getSystemID());
			System.out.println("Encoding="+doc.getXMLEncoding());*/
			Element root=doc.getRootElement();
			System.out.println(System.currentTimeMillis());
			parseAndPrint(root, 0);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**递归调用方法解析打印xml文件
	 * @param element 标签
	 * @param level  递归层数，用于打印时的退格控制*/
	public static void parseAndPrint(Element element,int level){
		//打印退格
		System.out.print(getBlank(level));
		
		//节点名字
		String nameString=element.getName();
		System.out.print(nameString);
		
		//属性
		Iterator<Attribute> it=element.attributeIterator();
		while(it.hasNext()){
			Attribute attribute=it.next();
			System.out.print(" "+attribute.getName()+":"+attribute.getValue().trim());
		}
		System.out.println();
		
		//打印子标签
		List<Element> subList=element.elements();
		for(Element element2:subList){
			if(element2.getNodeType()==Node.ELEMENT_NODE ){
				parseAndPrint(element2, level+1);
			}
		}
		
		//如果没有子标签，就打印值
		String text=element.getText();
		if(text!=null && !"".equals(text.trim())){
			System.out.println(getBlank(level+1)+element.getText().trim());  //trim()不止去空格，还会把ascii中排在空格前的前导后导字符都去掉
		}
	}
	
	/**根据递归层级打印起始退格*/
	public static String getBlank(int level){
		StringBuilder sbBuilder=new StringBuilder();
		for(int i=0;i<level;i++){
			sbBuilder.append(BLANK);
		}
		return sbBuilder.toString();
	}
}
