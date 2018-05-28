package com.edison.testJunit.oth.ii_2_XML.parse_big_xml.test;

import java.util.ArrayList;
import java.util.List;

import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.base.XmlBase;
import com.edison.testJunit.oth.ii_2_XML.Car;
import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.util.Xml4bigKit;

public class Test {
	private static final String FILEOUT="/src/main/resources/xml_parser/big.xml";


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start=System.currentTimeMillis();
		String xpath=System.getProperty("user.dir")+FILEOUT;
		System.out.println(xpath);
		String dataStartTag =null;
		List<Class<? extends XmlBase>> clslist = new ArrayList<Class<? extends XmlBase>>();
		clslist.add(Car.class);
		try{
			Xml4bigKit.parseXml(xpath, dataStartTag,clslist,new XmlHandler1(),new MyXmlFilter());
		}catch(Exception e){
			e.printStackTrace();
		}
		long end=System.currentTimeMillis();
		System.out.println("共计耗时(秒)："+(end-start)/1000);
	}

}
