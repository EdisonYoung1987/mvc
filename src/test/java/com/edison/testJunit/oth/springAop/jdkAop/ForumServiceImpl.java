/**
 * 
 */
package com.edison.testJunit.oth.springAop.jdkAop;

import com.edison.testJunit.oth.springAop.noAop.ForumService;

/**
 * @author Edison
 * ��̳�ӿ�ʵ����
 */
public class ForumServiceImpl implements ForumService {

	public void removeTopic(int topicId) {
		//PerformanceMonitor.begin("removeTopic");
		System.out.println("ForumServiceImpl.removeTopic()");
		//PerformanceMonitor.end();

	}

	public void removeForum(int forumId) {
//		PerformanceMonitor.begin("removeForum");
		System.out.println("ForumServiceImpl.removeTopic()");
		//PerformanceMonitor.end();
	}

}
