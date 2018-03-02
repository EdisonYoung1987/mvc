package com.edison.testJunit.oth.springAop.jdkAop;

import java.lang.reflect.Proxy;

import com.edison.testJunit.oth.springAop.noAop.ForumService;

public class testJdkAop {

	public static void main(String[] args) {
		ForumService forum=new  ForumServiceImpl();  //Ŀ����

		PerformanceHandler handler=new PerformanceHandler(forum); //handler
		ForumService proxy=(ForumService)Proxy.newProxyInstance(forum.getClass().getClassLoader()
				, forum.getClass().getInterfaces(), handler); //ʵ���� forum�Ĵ����໹��ForumService���͵�
		//�����������Ժ�ͨ��������ȥִ�з���
		proxy.removeForum(10);
		proxy.removeTopic(11);
	}

}
