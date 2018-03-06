package com.edison.testJunit.oth.springAop.springAop2;

import org.springframework.stereotype.Component;

@Component
public class NoAop {//不希望被加强，名字不是er结尾
	public void greeTo(String custmor){
		System.out.println("	NoAop.greeTo("+custmor+")..");
	}
	
	public void serveTo(String custmor){
		System.out.println("	NoAop.serveTo("+custmor+")..");
	}
}
