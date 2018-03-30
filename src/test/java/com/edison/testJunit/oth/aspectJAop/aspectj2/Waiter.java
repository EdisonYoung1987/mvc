package com.edison.testJunit.oth.aspectJAop.aspectj2;

import org.springframework.stereotype.Component;

@Component
public class Waiter {
	public void greeTo(String custmor){//希望增加 前置增强 
		System.out.println("	Waiter.greeTo("+custmor+")..");
	}
	
	public void serveTo(String custmor){ //希望增加环绕增强 
		System.out.println("	Waiter.serveTo("+custmor+")..");
	}
}
