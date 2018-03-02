package com.edison.testJunit.oth.springAop.noAop;


/**
 * ��ǿ�࣬�����Ǽ�ط���ִ��ʱ��*/
public class MethodPerformance {
	private long begin,end;
	private String serviceMethod;
	
	public  MethodPerformance(String serviceMethod){
		this.begin=System.currentTimeMillis();
		this.serviceMethod=serviceMethod;
	}
	
	public void printPerformance(){
		end=System.currentTimeMillis();
		System.out.println(serviceMethod+"ִ��ʱ����"+(end-begin));
	}

}
