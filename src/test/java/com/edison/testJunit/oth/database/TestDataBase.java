package com.edison.testJunit.oth.database;

import org.junit.Ignore;
import org.junit.Test;

import com.edison.testJunit.oth.database.pureJdbc.service.ForumService;

public class TestDataBase {
	@Test
//	@Ignore //这个表示不执行该测试方法
	public void testPureJdbc(){//测试纯jdk api实现的数据库操作
		ForumService forumServive=new ForumService();
		forumServive.queryForumByName("新");
	}
}
