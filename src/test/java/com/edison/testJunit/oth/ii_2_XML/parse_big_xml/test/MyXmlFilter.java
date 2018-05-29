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
			}else{
				if("法拉利".equals(element.element("type").getText())){//当前<Car>的子标签<type>的内容
					return true;
				}
				if(!attr.getText().endsWith("0001")){//只处理id以001结尾的标签
					return false;
				}
			}
		}
		return true;
	}

}
