package com.edison.testJunit.oth.ii_2_XML.parse_big_xml.test;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.filter.XmlFilter;

public class MyXmlFilter implements XmlFilter{

	public boolean accept(Element element) {
		if("Car".equals(element.getName())){
			Attribute attr=element.attribute("id");//获取Car标签中的id属性
			if(attr==null){//普通车辆 
				return false;
			}
		}
		return true;
	}

}
