package com.edison.testJunit.oth.ii_2_XML;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

/**
 *dom4j+xpath解析xml<p>
 *xpath解析主要用于直到xml格式，直奔内容的情况很方便*/
public class A_DOM4J_DOM_Xpath {
	static final String FILE="BpiamiaMapper.xml";
	static final String FILE2="springContext.xml";

	static final String BLANK="    ";
	
	public static void main(String[] args) {
//		URL url=Thread.currentThread().getContextClassLoader().getResource("xml_parser/Mapper.xml"); //不能以’/'开头，path是从ClassPath根下获取 
		InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream("xml_parser/BpiamiaMapper.xml");												
		InputStream in2=Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE2);												

		try{
			SAXReader reader=new SAXReader();
			
			//这一段是关闭xml解析时dtd验证的，第一个是关闭验证，但还是会联网下载dtd文件，第二个表示不去下载
			reader.setValidation(false);  
			//XERCES_FEATURE_PREFIX +LOAD_EXTERNAL_DTD_FEATURE= "http://apache.org/xml/features/nonvalidating/load-external-dtd"
			reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false); 

			//Document doc=reader.read(url);
			Document doc=reader.read(in);//当mapper是被打包到jar里面的时候，只能使用inputstream方式读取
			
			//doctype读取 
			System.out.println("DocType="+doc.getDocType().getElementName()+" "+doc.getDocType().getPublicID()
					+" "+doc.getDocType().getSystemID());
			System.out.println("Encoding="+doc.getXMLEncoding());
			parseAndPrint(doc);
			
			SAXReader reader2=new SAXReader();//带命名空间的情况
			Document doc2=reader2.read(in2);
			parseAndPrint2(doc2);

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**解析该xml文件的所有select insert update
	 * @param element 标签
	 * @param level  递归层数，用于打印时的退格控制*/
	public static void parseAndPrint(Document doc){
		/**  
		 *Xpath语法的形式：
		 * 第一种形式
        　　　　		/AAA/DDD/BBB： 表示一层一层的，AAA下面 DDD下面的BBB
    　			  第二种形式
       　　　　 		//BBB： 表示和这个名称相同，表示只要名称是BBB，都得到
         	 第三种形式
        　　　　		/*: 所有元素
         	 第四种形式
       　　　　		BBB[1]：　表示第一个BBB元素
        　  　　 		BBB[last()]：表示最后一个BBB元素
         	 第五种形式
        　　　　		//BBB[@id]： 表示只要BBB元素上面有id属性，都得到BBB  //BBB/@id 直接定位到BBB的属性id
         	第六种形式
        　　　　		//BBB[@id='b1'] 表示元素名称是BBB,在BBB上面有id属性，并且id的属性值是b1
        */
		
		// 创建解析路径，就是在普通的解析路径前加上map里的key值
		XPath x = doc.createXPath("//select");
		List<Node> list=x.selectNodes(doc);
		for(Node node:list){
			System.out.println(node.getText());
		}
		
		//update
		x = doc.createXPath("/mapper/update");
		list=x.selectNodes(doc);
		for(Node node:list){ //node可以被转为Element，然后为所欲为
			if(node.getNodeType()==Node.ELEMENT_NODE){
				System.out.println(node.getText().trim());
			}
		}
	}
	
	/**解析带有命名空间的xml
	 * @param element 标签*/
	public static void parseAndPrint2(Document doc){
		String nsURI = doc.getRootElement().getNamespaceURI();
		System.out.println("namespace:"+nsURI); 
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("xmlns", nsURI);
		
		//解析beans/bean/property/value
		XPath x = doc.createXPath("/xmlns:beans/xmlns:bean/xmlns:property/xmlns:value");  //bean相关标签只有一个ns
		x.setNamespaceURIs(map);
		
		List<Node> list=x.selectNodes(doc);
		
		for(Node node:list){
			if(node.getNodeType()==Node.ELEMENT_NODE){
				System.out.println(node.getText().trim());
			}
		}
		
		//定位context：component-scan [@base-package]表示标签含有base-package属性
		map.put("context","http://www.springframework.org/schema/context");
		x = doc.createXPath("/xmlns:beans/context:component-scan[@base-package]");  //beans和component都要有命名空间
		x.setNamespaceURIs(map);//这个每次都不能省啊。。
		Node node=x.selectSingleNode(doc);
		System.out.println(node.getName());
		System.out.println(((Element)node).attributeValue("base-package")); //转为element可以获取其属性等信息
		
		//快速解析context:component-scan 的base-package属性
		map.put("context","http://www.springframework.org/schema/context");
		x = doc.createXPath("/xmlns:beans/context:component-scan/@base-package");  //beans和component都要有命名空间
		x.setNamespaceURIs(map);
		
		node=x.selectSingleNode(doc);
		System.out.println(node.getName()+":"+node.getStringValue());
		
		
	}
}
