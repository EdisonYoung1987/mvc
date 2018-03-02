package com.edison.testJunit.oth.springAop.noAop;
/**
 * ¼à¿Øµ÷¶ÈÀà*/
public class PerformanceMonitor {
	private static ThreadLocal<MethodPerformance> threadLocal=new ThreadLocal<MethodPerformance>();
	
	public static void begin(String methodName){
		System.out.println("PerformanceMonitor.begin()...");
		MethodPerformance mp=null;
		if(threadLocal.get()==null){
			mp=new MethodPerformance(methodName);
			threadLocal.set(mp);
		}
		
	}
	
	public static void end(){
		System.out.println("PerformanceMonitor.end()...");
		MethodPerformance mp=threadLocal.get();
		mp.printPerformance();
	}
}
