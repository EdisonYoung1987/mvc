package com.edison.testJunit.oth.aspectJAop.aspectj2;

import org.springframework.stereotype.Component;

@Component
public class Waiter {
	public void greeTo(String custmor){//ϣ������ ǰ����ǿ 
		System.out.println("	Waiter.greeTo("+custmor+")..");
	}
	
	public void serveTo(String custmor){ //ϣ�����ӻ�����ǿ 
		System.out.println("	Waiter.serveTo("+custmor+")..");
	}
}
