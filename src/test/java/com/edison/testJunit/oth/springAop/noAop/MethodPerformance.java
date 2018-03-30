package com.edison.testJunit.oth.springAop.noAop;


/**
 * 增强类，功能是监控方法执行时间*/
public class MethodPerformance {
	private long begin,end;
	private String serviceMethod;
	
	public  MethodPerformance(String serviceMethod){
		this.begin=System.currentTimeMillis();
		this.serviceMethod=serviceMethod;
	}
	
	public void printPerformance(){
		end=System.currentTimeMillis();
		System.out.println(serviceMethod+"执行时长："+(end-begin));
	}

}
