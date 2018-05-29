package com.edison.testJunit.oth.ii_2_XML.parse_big_xml.test;

import java.util.List;
import java.util.Map;

import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.handler.XmlHandler;

public class XmlHandler1 implements XmlHandler{
	private List<Object> list;
	
	public XmlHandler1(List<Object> list){
		this.list=list;
	}
	
	public List<Object> getList() {
		return list;
	}

	public void hande(Map<String, List<Object>> reMap) { //这个reMap是解析过程定义的，存储的是key-list的map
		List<Object> rsl=reMap.get("Car");
		if(rsl!=null && rsl.size()>0){
			this.list.addAll(rsl);
		}
	}
}
