package com.edison.testJunit.oth.ii_2_XML.parse_big_xml.test.vo;
import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.annotation.Column;
import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.base.XmlBase;
public class CarEngine extends XmlBase {


	@Column(name="core")
	private Integer core;       
	@Column(name="type")
	private String type;
	public Integer getCore() {
		return core;
	}
	public void setCore(Integer core) {
		this.core = core;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}   
	
	


}
