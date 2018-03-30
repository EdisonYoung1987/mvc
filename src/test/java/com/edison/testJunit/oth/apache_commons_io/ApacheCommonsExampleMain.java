package com.edison.testJunit.oth.apache_commons_io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


/**
 *1）  工具类 
  2） 输入 
  3） 输出 
  4） filters过滤 
  5） Comparators 
  6） 文件监控 */
public class ApacheCommonsExampleMain {
	 private static Logger logger=LoggerFactory.getLogger(ApacheCommonsExampleMain.class);
	 
	 public static void main(String[] args) { 
		 MDC.put("THREAD", ""+Thread.currentThread().getId());
		 logger.info("开始测试");
		 try{
	        UtilityExample.runExample();  
	          
	        FileMonitorExample.runExample();  
	          
	        FiltersExample.runExample();  
	          
	        InputExample.runExample();  
	          
	        OutputExample.runExample();  
	          
	        ComparatorExample.runExample();
		 }catch(Exception e){
//			 e.printStackTrace();
			 logger.error("异常", e);
		 }
	  }  
}
