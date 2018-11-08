package com.edison.testJunit.oth.designPattern;

import java.security.Signature;

/**
 * 单例模式*/
public class Singleton {
	private static Singleton singleton;
	
	private Singleton(){//构造函数私有化，这样只能通过getInstance方法获取
	}

	public static Singleton getInstance(){
		if(singleton==null){ //第一次检查，是的话就进入同步块
			synchronized (Singleton.class) {
				if(singleton==null){//第二次检查，这样后续排队等待同步块的线程拿到cpu执行权限的时候不会重复创建
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
