package com.edison.testJunit.oth.designPattern;

import java.security.Signature;

/**
 * 单例模式*/
public class Singleton {
	private static volatile Singleton singleton; //需要加volatile
	
	private Singleton(){//构造函数私有化，这样只能通过getInstance方法获取
	}

	public static Singleton getInstance(){
		if(singleton==null){ //第一次检查，是的话就进入同步块
			synchronized (Singleton.class) {
				if(singleton==null){//第二次检查，这样后续排队等待同步块的线程拿到cpu执行权限的时候不会重复创建
					/**不加volatile的话，new 对象的时候可能构造函数还没执行完毕就完成了new的操作，
					 * 对象内的一些变量的初始化还没完成，另外一个线程拿到了这个对象是拿到了，
					 * 对象内部的初始化过程还没走完
					 * memory=allocate(); //1:分配对象的内存空间
					   ctorInstance(memory); //2:初始化对象
                       instance=memory; //3:给变量设置引用
                                             顺序可能变成1 3 2 ，这样，singleton有地址了，！=null，但是初始化工作还未完成；
                       
                       *简单来说，不加volatile也能保证单例，但是可能会因为cpu乱序执行，
                       *导致其他线程拿到的是未初始化完成的对象（这并不是我们想要的）*/
					singleton=new Singleton();
				}
			}
		}
		return singleton;
	}
	
	public static void main(String[] args) throws InterruptedException {
		for(int i=0;i<10;i++){
			new Instance().start();
		}
		Thread.sleep(1000);
	}

}
class Instance extends Thread{

	@Override
	public void run() {
		Singleton singleton=Singleton.getInstance();
		System.out.println(singleton+"  "+singleton.hashCode());
	}
	
}
