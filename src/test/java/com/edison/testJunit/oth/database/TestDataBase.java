package com.edison.testJunit.oth.database;

import org.junit.Ignore;
import org.junit.Test;

import com.edison.testJunit.oth.database.pureJdbc.service.ForumService;

public class TestDataBase {
	@Test
//	@Ignore //�����ʾ��ִ�иò��Է���
	public void testPureJdbc(){//���Դ�jdk apiʵ�ֵ����ݿ����
		ForumService forumServive=new ForumService();
		forumServive.queryForumByName("��");
	}
}
