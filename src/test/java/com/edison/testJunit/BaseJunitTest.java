package com.edison.testJunit;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)//使用junit4进行测试  
@ContextConfiguration(locations={"classpath:springContext.xml"}) //加载配置文件  
//这个非常关键，如果不加入这个注解配置，事务控制就会完全失效！    
//@Transactional    
//这里的事务关联到配置文件中的事务控制器（transactionManager = "transactionManager"），
//同时指定自动回滚（defaultRollback = true）。这样做操作的数据才不会污染数据库！    
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)  
@FixMethodOrder(MethodSorters.JVM) //这是4.11后才添加的功能，JVM-定义顺序执行
								   //MethodSorters.DEFAULT -随机执行
								   //MethodSorters.NAME_ASCENDING 字母升序执行
public class BaseJunitTest {
	
}
