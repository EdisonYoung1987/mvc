package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MyRunnable implements Runnable{
	private Lock lock;
	private Condition condition;
	private int i=0;
	
	public MyRunnable(){
		this.lock=new ReentrantLock();
		this.condition=lock.newCondition();//将条件对象绑定到lock上，一个lock可以绑定多个条件对象
	}
	
	public void run() {
		lock.lock();
		System.out.println("线程："+Thread.currentThread().getId()+"开始");
		try{
			Random rand=new Random(System.currentTimeMillis());
			int t=0;
			while(i<100){
				t=rand.nextInt(3);
				if(t==2){
					System.out.println("线程："+Thread.currentThread().getId()+"进入await()");
					condition.await(20, TimeUnit.NANOSECONDS); //如果不加await()时间参数，有可能所有线程都进入await状态
//					condition.await();
					System.out.println("线程："+Thread.currentThread().getId()+"醒来");
				}
				i+=t;
				System.out.println("i="+i);
				condition.signalAll();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			System.out.println("线程："+Thread.currentThread().getId()+"结束");
			lock.unlock();
		}
	}
	public void printI(){
		System.out.println(this.i);
	}
}

public class E_2_Condition {
	public static void main(String[] args) {
		MyRunnable mr=new MyRunnable();
		Thread thread1=new Thread(mr);
		Thread thread2=new Thread(mr);
		thread1.start();
		thread2.start();
		try{
			thread1.join();
			thread2.join();
		}catch(Exception e){
			e.printStackTrace();
		}
		mr.printI();
	}

}
