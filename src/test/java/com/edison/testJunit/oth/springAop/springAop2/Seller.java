package com.edison.testJunit.oth.springAop.springAop2;

import org.springframework.stereotype.Component;

@Component
public class Seller {
	public void greeTo(String custmor){//ϣ������ ǰ����ǿ 
		System.out.println("	Seller.greeTo("+custmor+")..");
	}
	
	public void serveTo(String custmor){ //ϣ�����ӻ�����ǿ 
		System.out.println("	Seller.serveTo("+custmor+")..");
	}
}
