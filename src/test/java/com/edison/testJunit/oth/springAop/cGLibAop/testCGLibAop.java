package com.edison.testJunit.oth.springAop.cGLibAop;

import org.junit.Test;

import com.edison.testJunit.oth.springAop.jdkAop.ForumServiceImpl;

public class testCGLibAop {
	@Test
	public void proxy(){
		CGLibProxy cgLibProxy=new CGLibProxy();
		ForumServiceImpl proxy=(ForumServiceImpl)cgLibProxy.getProxy(ForumServiceImpl.class);
		proxy.removeForum(11);
		proxy.removeTopic(10);
	}
}
