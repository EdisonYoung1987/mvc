package com.edison.testJunit.oth.springAop.springAop2;

import org.springframework.stereotype.Component;

@Component
public class NoAop {//��ϣ������ǿ�����ֲ���er��β
	public void greeTo(String custmor){
		System.out.println("	NoAop.greeTo("+custmor+")..");
	}
	
	public void serveTo(String custmor){
		System.out.println("	NoAop.serveTo("+custmor+")..");
	}
}
