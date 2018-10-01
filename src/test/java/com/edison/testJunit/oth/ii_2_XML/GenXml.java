package com.edison.testJunit.oth.ii_2_XML;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
/**生成一个超大的xml文件*/
public class GenXml {
	private static final String FILEOUT="/src/main/resources/xml_parser/big.xml";

	public static void main(String[] args) throws Exception{
		 String xpath=System.getProperty("user.dir")+FILEOUT;
		 System.out.println(System.getProperty("user.dir"));
		 File file = new File(xpath);
		 if(file.exists()){
			 file.delete();
		 }
//		 int len =40000; //生成750M左右的XML文件
		 int len =4;     
//		 int len =120000; //生成2.1G左右的XML文件

		 file.createNewFile();
		 BufferedWriter bw = null;  
		 FileWriter fw = new FileWriter(file);  
	     bw = new BufferedWriter(fw);  
	     bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	     bw.newLine();
	     bw.write("<Cars>");
	     bw.newLine();
	     String elspe="<Car id=\"MMM\"><type>奔驰MMM</type><product>德国</product><price>55.2</price><carEngine><core>16</core><type>Intel</type></carEngine><carEngine><core>16</core><type>Intel</type></carEngine></Car>";
	     String el="<Car><type>本田</type><product>中国</product><price>22.5</price><carEngine><core>16</core><type>Intel</type></carEngine><carEngine><core>16</core><type>Intel</type></carEngine></Car>";
	     String elspe2="<Car id=\"1\"><type>法拉利</type><product>德国</product><price>55.2</price><carEngine><core>16</core><type>Intel</type></carEngine><carEngine><core>16</core><type>Intel</type></carEngine></Car>";

	     int icount=100;
	     StringBuffer sbf=new StringBuffer();
	     for(int i=0; i<icount;i++){ 
	    	 sbf.append(el);
	    	 if(i==50){//将特殊的那条加进去，后面测试解析用
	    		 sbf.append(elspe);
	    	 }
	     }
	     for(int i=0; i<len;i++){//共有len台奔驰
	    	 String sbfStr=sbf.toString();
	    	 sbfStr=sbfStr.replaceAll("MMM", ""+(i+1));
	    	 bw.write(sbfStr);
	    	 bw.newLine();
	     }
	     bw.write(elspe2); //把1台法拉利写进去，测试解析用
	     bw.write("</Cars>");
	     bw.flush();
	     bw.close();
	     
	}
}
