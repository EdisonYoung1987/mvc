package com.edison.testJunit.oth.ii_2_XML;

import com.edison.testJunit.oth.ii_2_XML.parse_big_xml.base.XmlBase;

public class CarEngine extends XmlBase {
	private String core;
	private String type;
	public String getCore() {
		return core;
	}
	public void setCore(String core) {
		this.core = core;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "CarEngine [core=" + core + ", type=" + type + "]";
	}
	
}
