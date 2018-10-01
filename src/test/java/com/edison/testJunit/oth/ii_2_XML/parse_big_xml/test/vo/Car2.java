package com.edison.testJunit.oth.ii_2_XML.parse_big_xml.test.vo;
import java.util.List;

import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.annotation.Column;
import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.annotation.Root;
import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.base.XmlBase;
@Root(name="Car")
public class Car2 extends XmlBase{
	@Column(name="type")
	public String type1;
	@Column(name="product")
	public String product1;
	@Column(name="price")
	public Double price1;
	@Column(name="carEngine")
	public List<CarEngine> carEngine;
	
	
	
	public List<CarEngine> getCarEngine() {
		return carEngine;
	}
	public void setCarEngine(List<CarEngine> carEngine) {
		this.carEngine = carEngine;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getProduct1() {
		return product1;
	}
	public void setProduct1(String product1) {
		this.product1 = product1;
	}
	public Double getPrice1() {
		return price1;
	}
	public void setPrice1(Double price1) {
		this.price1 = price1;
	}
	
	
	

}
