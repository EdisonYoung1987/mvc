package com.edison.testJunit.oth.ii_2_XML.parse_big_xml.test;

import java.util.List;
import java.util.Map;

import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.handler.XmlHandler;
import com.edison.testJunit.oth.ii_2_XML.Car;

public class XmlHandler1 implements XmlHandler{

	public void hande(Map<String, List<Object>> reMap) {
		
//		System.out.println("=======hande==========");
		List<Object> list = reMap.get("Car");
		
		for(Object obj:list){
			Car car = (Car) obj;
			
			System.out.println(car);
		}
	}

}
