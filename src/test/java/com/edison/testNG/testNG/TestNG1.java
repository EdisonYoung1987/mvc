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
		System.out.println("测试类执行前。。。");
		/*BeforeClass一般用于昂贵资源的加载，如数据库的连接，而AfterClass则是释放资源等等操作*/
	}
	
	@AfterClass
	public void destroyClass(){
		System.out.println("测试类执行后");
	}
	
	@BeforeMethod
	public void beforeMethod(){
		System.out.println("-------------------------每次方法执行前-------------------------");
	}
	
	@AfterMethod
	public void afterMethod(){
		System.out.println("-------------------------每次方法执行后-------------------------");
	}
	
	@Test(timeOut=1) //超时时间为10ms
	public void testNG1_1(){
		System.out.println("dosomething  in  NG1_1");
		/*try{
			Thread.sleep(12);
		}catch(Exception e){
			System.out.println("sleep 失败");
		} 该方法会被interrupt抛出异常*/
		System.out.println("计算结果如下(没有结果则证明已超时)：");
		long ret=0l;
		for(int i=0;i<100000;i++){
			for(int j=1;j<100000;j++){
				ret+=i*j;
			}
		}
		System.out.println("输出结果，如果超时，则不会被输出："+ret);
	}
	
	@Test(enabled=false)
	public void testNG1_X(){
		System.out.println("这一段是不执行的");
	}
	
	@Test
	public void testNG1_2(){
		System.out.println("dosomething  in  NG1_2");
	}
}
