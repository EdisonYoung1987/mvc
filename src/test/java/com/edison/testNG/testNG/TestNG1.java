package com.edison.testNG.testNG;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class TestNG1 {
	
	@BeforeClass
	public void initClass(){
		System.out.println("������ִ��ǰ������");
		/*BeforeClassһ�����ڰ�����Դ�ļ��أ������ݿ�����ӣ���AfterClass�����ͷ���Դ�ȵȲ���*/
	}
	
	@AfterClass
	public void destroyClass(){
		System.out.println("������ִ�к�");
	}
	
	@BeforeMethod
	public void beforeMethod(){
		System.out.println("-------------------------ÿ�η���ִ��ǰ-------------------------");
	}
	
	@AfterMethod
	public void afterMethod(){
		System.out.println("-------------------------ÿ�η���ִ�к�-------------------------");
	}
	
	@Test(timeOut=1) //��ʱʱ��Ϊ10ms
	public void testNG1_1(){
		System.out.println("dosomething  in  NG1_1");
		/*try{
			Thread.sleep(12);
		}catch(Exception e){
			System.out.println("sleep ʧ��");
		} �÷����ᱻinterrupt�׳��쳣*/
		System.out.println("����������(û�н����֤���ѳ�ʱ)��");
		long ret=0l;
		for(int i=0;i<100000;i++){
			for(int j=1;j<100000;j++){
				ret+=i*j;
			}
		}
		System.out.println("�������������ʱ���򲻻ᱻ�����"+ret);
	}
	
	@Test(enabled=false)
	public void testNG1_X(){
		System.out.println("��һ���ǲ�ִ�е�");
	}
	
	@Test
	public void testNG1_2(){
		System.out.println("dosomething  in  NG1_2");
	}
}
