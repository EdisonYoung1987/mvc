package com.edison.testJunit.random;

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

		for(int i=0;i<1024*2;i++) {//3G
			list.add(new TestJVM());
			if(i%50==0) {
				System.out.println("已使用内存(M)"+i);
			}
			Thread.sleep(65);
		}
	}

}
