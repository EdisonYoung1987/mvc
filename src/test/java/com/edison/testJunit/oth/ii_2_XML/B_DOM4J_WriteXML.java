package com.edison.testJunit.oth.ii_2_XML;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**写一个xml文件*/
public class B_DOM4J_WriteXML {
	private static final String FILE="/src/main/resources/xml_parser/write.xml";
	private static final String FILEOUT="/src/main/resources/xml_parser/write2.xml";

	public static void main(String[] args)  {
//		URL uri=Thread.currentThread().getContextClassLoader().getResource("xml_parser");//这是写到target/classes里面去
		String parent=System.getProperty("user.dir");//工作路径 C:\Users\Edison\git\mvc
		String filepath=parent+FILE;
		String filepath2=parent+FILEOUT;

		System.out.println("结果文件:"+filepath);
		
		File file=new File(filepath);
		try{
			FileOutputStream fo=new FileOutputStream(file);
			
			//组织xml文件 -整体
			Document doc=DocumentHelper.createDocument(); //用这个创建一个Document
			
			//设置标签
			Element rootElement=doc.addElement("ContactList"); //addElement结果保存下来，还可以进行更细致的设置
			Element contactElement = rootElement.addElement("Contact");
	        Element nameElement = contactElement.addElement("Name");
			
	        //设置属性
	        contactElement.addAttribute("id", "c001");
	        contactElement.addAttribute("region", "北京");

	        //设置文本值
	        nameElement.setText("小明");

	        //输出设置
	        //OutputFormat format = OutputFormat.createCompactFormat();//紧凑的格式.去除空格换行.项目上线的时候
	        OutputFormat format = OutputFormat.createPrettyPrint();//漂亮的格式.有空格和换行.开发调试的时候
	        format.setEncoding("UTF-8"); //指定生成的xml文档的编码 以及 xml头声明的Encoding
	        
	        //输出
	        XMLWriter xmlWriter=new XMLWriter(fo, format);
	        xmlWriter.write(doc);  
			
	        xmlWriter.close();
	        fo.close();
	        
	        //读取该xml文件的部分内容并进行修改
	        Scanner scan=new Scanner(new File(filepath));
	        System.out.println("原内容：");
	        while(scan.hasNextLine()){
	        	System.out.println(scan.nextLine());
	        }
	        scan.close();
	        
	        //修改xml：Contact 的region改为上海，name增加属性
	        SAXReader saxreader=new SAXReader();
	        doc=saxreader.read(new File(filepath));
	        doc.getRootElement().element("Contact").addAttribute("region", "上海");//有则更新，无则新增
	        Element name=doc.getRootElement().element("Contact").element("Name");
	        name.addAttribute("sex", "男");
	        
	        //写到新的xml中
	        xmlWriter=new XMLWriter(new FileOutputStream(new File(filepath2)), format);
	        xmlWriter.write(doc);  
	        xmlWriter.close();
	        
	        scan=new Scanner(new File(filepath2));
	        System.out.println("更新后内容：");
	        while(scan.hasNextLine()){
	        	System.out.println(scan.nextLine());
	        }
	        scan.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
