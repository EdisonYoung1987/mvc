package com.edison.testJunit.oth.springAop.noAop;

public class testForum {

	public static void main(String[] args) {
		ForumService forumService=new ForumServiceImpl();
		forumService.removeTopic(10);
		forumService.removeForum(11);

	}

}
