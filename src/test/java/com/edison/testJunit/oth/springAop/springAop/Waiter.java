package com.edison.testJunit.oth.springAop.springAop;

public class Waiter {
	public void greeTo(String custmor){
		System.out.println("您好，"+custmor+",想吃啥子？烤鱼还是点菜？");
	}
	
	public void serveTo(String custmor){
		System.out.println("你的菜齐了");
	}
}
