package com.edison.testJunit.oth.ii_2_XML;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**使用DOM4J+SAX 即边读边解析，而不是一次加载到内存中，缓解内存压力<p>
 * 760M左右的文件用了7,8分钟*/
public class C_DOM4J_SAX {
	private static final String FILE="/src/main/resources/xml_parser/big.xml";
	
	public static void main(String[] args) {
	System.out.println(System.currentTimeMillis());
		List<Car> list=new ArrayList<Car>(16); //存放解析后的Car，根据实际情况定义初始长度，否则多次resize扩容也不好
		
		String parent=System.getProperty("user.dir");//工作路径 C:\Users\Edison\git\mvc
		String filepath=parent+FILE;
		System.out.println("待处理xml："+filepath);
		
		//遍历所有标签，找到奔驰和法拉利
		SAXReader reader=new SAXReader();  
		reader.setEncoding("UTF-8");  
		CarHandler handler = new CarHandler(list);  
		reader.addHandler("/Cars/Car", handler);  
		
		try{
			Document doc=reader.read(new FileInputStream(new File(filepath)));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("listsize="+list.size());
		for(Car car:list){
			System.out.println(car);
		}
		System.out.println(System.currentTimeMillis());

	}

}

/**SAX解析xml主要就是要实现一个ElementHandler类
 * 这个不是通用的，看能否通过反射写一个通用的*/
class CarHandler implements ElementHandler{
	List<Car> carlist;
	Car car=null;
	
	public CarHandler(List<Car> list){
		this.carlist=list;
	}
	
	public void onStart(ElementPath elementPath) {//可以处理属性 onStart()只会读到<Car xxx>就会触发，还没有往后面读
		//只处理Car标签含有id的段落
		Element element=elementPath.getCurrent();
		
		Attribute attribute=element.attribute("id");
		if(attribute==null){//说明是普通车辆，不处理
			car=null;
		}else{
			car=new Car();
			car.setId(Integer.parseInt(attribute.getValue()));
		}
	}

	public void onEnd(ElementPath elementPath) { //处理完整的element
		Element element=elementPath.getCurrent();
		if(car==null){//普通车辆不处理
			element.detach();//从内存中删除 没有这一行的话内存会爆掉的。
			return;
		}
		
		Element tmp=element.element("type");
		
		//特殊车辆只处理10辆：9辆奔驰+1辆法拉利
		if(carlist.size()==9 && !"法拉利".equals(tmp.getText())){
			car=null;
			element.detach();
			return;
		}
		
		car.setType(tmp.getText());
		tmp=element.element("product");
		car.setProduct(tmp.getText());
		tmp=element.element("price");
		
		//解析引擎 carEngine 的list
		List<CarEngine> engineList=new ArrayList<CarEngine>(16);
		car.setPrice(Double.parseDouble(tmp.getText()));
		List<Node> list=element.selectNodes("carEngine");
		for(Node node:list){
			CarEngine carEngine=new CarEngine();
			carEngine.setCore(((Element)node).element("core").getText());
			carEngine.setType(((Element)node).element("type").getText());
			engineList.add(carEngine);
		}
		car.setCarEngine(engineList);
		
		//添加到list
		carlist.add(car);
	}
	
}
