package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.util.concurrent.locks.ReentrantLock;

class Mythread extends Thread{
	@Override
	public void run(){
		for(int i=0;i<100000;i++){
			//E_1_Concurrency.addI() ;//synchronized方法
			E_1_Concurrency.addI2() ;//ReentrantLock方法
		}
	}
}
/**
 * 使用Synchronized和ReentrantLock两种方式实现并发锁*/
public class E_1_Concurrency {
	private static int i=0;
	private static ReentrantLock relock=new ReentrantLock();
	private static byte[] lock=new byte[0];
	
//	public synchronized static void addI(){  //synchronized修饰静态方法的话，那么这个类的所有对象之间都存在竞争，不好
											 //就是不是静态方法，synchronized修饰普通方法时，如果有其他方法也是synchronized的，
											 //他们之间也会产生竞争
//		i+=1;
//	}
	public static void addI(){ //这样更好一些，锁对象也是特别小byte[0]
		synchronized(lock) {
			i+=1;
		}
	}
	public static void addI2(){
		relock.lock();
		try{
			i+=1;
		}finally{//try-finally保证unlock一定会执行，否则方法抛异常后不释放锁
			relock.unlock();
		}
	}
	public static void main(String[] args) {
		Mythread thread1=new Mythread();
		Mythread thread2=new Mythread();
		thread1.start();
		thread2.start();
		try{ //等待两个线程结束，否则打印出来的i不是最终值
			thread1.join();
			thread2.join();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println(i);//200000正确
		
	}

}
