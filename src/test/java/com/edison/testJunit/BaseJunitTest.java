package com.edison.testJunit;

import org.junit.FixMethodOrder;
import org.junit.runners.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)//ʹ��junit4���в���  
@ContextConfiguration(locations={"classpath:springContext.xml"}) //���������ļ�  
//����ǳ��ؼ���������������ע�����ã�������ƾͻ���ȫʧЧ��    
//@Transactional    
//�������������������ļ��е������������transactionManager = "transactionManager"����
//ͬʱָ���Զ��ع���defaultRollback = true�������������������ݲŲ�����Ⱦ���ݿ⣡    
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)  
@FixMethodOrder(MethodSorters.JVM) //����4.11�����ӵĹ��ܣ�JVM-����˳��ִ��
								   //MethodSorters.DEFAULT -���ִ��
								   //MethodSorters.NAME_ASCENDING ��ĸ����ִ��
public class BaseJunitTest {
	
}
