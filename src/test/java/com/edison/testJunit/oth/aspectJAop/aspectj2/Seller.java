package com.edison.testJunit.oth.aspectJAop.aspectj2;

import org.springframework.stereotype.Component;

@Component
public class Seller {
	public void greeTo(String custmor){//希望增加 前置增强 
		System.out.println("	Seller.greeTo("+custmor+")..");
	}
	
	@MyAnnotation(value="",id=3,editable=true)
	public void serveTo(String custmor){ //希望增加环绕增强 
		System.out.println("	Seller.serveTo("+custmor+")..");
	}
	
	public void guideTo(Waiter waiter){
		System.out.println("	Seller.guideTo");
	}
}
