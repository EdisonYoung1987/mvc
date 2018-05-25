package com.edison.testJunit.oth.ii_2_XML;

public class CarEngine {
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
