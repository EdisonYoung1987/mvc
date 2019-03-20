package com.edison.testJunit.random;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class TestJVM {
	private static List<TestJVM> list=new ArrayList<TestJVM>();
	
	private String buffer; //1M
	public TestJVM() {
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<1024*1024;i++) {
			sb.append("a");
		}
		buffer=sb.toString();
		
	}
	public static void main(String[] args) throws InterruptedException {
		//查看垃圾回收器有哪些
		List<GarbageCollectorMXBean> l = ManagementFactory.getGarbageCollectorMXBeans();
		for (GarbageCollectorMXBean b : l) {
			System.out.println(b.getName());
		}

		for (int i = 0; i < 1024 * 2; i++) {// 3G
			list.add(new TestJVM());
			if (i % 50 == 0) {
				System.out.println("已使用内存(M)" + i);
			}
			if(i%10==0 && i>0) {
				TestJVM tj=list.get(i);
				tj=null;
			}
			Thread.sleep(65);
		}
	}

}
