package com.edison.testJunit.oth.ii_2_XML;

import java.util.List;

/**解析xml后存储实体类<p>*/
public class Car {
	/*<Car id="x">
		<type>奔驰01</type>
		<product>德国</product>
		<price>55.2</price>
		<carEngine>
			<core>16</core>
			<type>Intel</type>
		</carEngine>
		<carEngine>
			<core>16</core>
			<type>Intel</type>
		</carEngine>
	</Car>*/
	private int id;
	private String type;
	private String product;
	double  price;
	List<CarEngine> engineList;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public List<CarEngine> getEngineList() {
		return engineList;
	}
	public void setEngineList(List<CarEngine> engineList) {
		this.engineList = engineList;
	}
	@Override
	public String toString() {
		return "Car [id=" + id + ", type=" + type + ", product=" + product
				+ ", price=" + price + ", engineList=" + engineList + "]";
	}
	
}
