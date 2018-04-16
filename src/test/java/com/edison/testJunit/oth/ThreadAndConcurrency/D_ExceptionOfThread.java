package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 设置线程异常捕获器。<p>
 * 因为Thread的run()方法不支持抛异常，所以可以定义一个线程未捕获异常处理器*/
public class D_ExceptionOfThread {
	/**线程未捕获异常处理器*/
	class MyExceptionHandler implements UncaughtExceptionHandler{
		public void uncaughtException(Thread t, Throwable e) {
			System.err.println("当前线程:"+t.getId());
			e.printStackTrace();
		}
	}
	class Mythread extends Thread{
		@Override
		public void run(){
			//如果不设置，将使用线程组的默认处理器，打印线程号和异常信息到system.err
			Thread.currentThread().setUncaughtExceptionHandler(new MyExceptionHandler()); //根据需求设置自己的未捕获异常处理器
			int[] arr={1,2,3,4};
			for(int i=0;i<5;i++){
				System.out.println(arr[i]);
			}
		}
	}
	public static void main(String[] args) {
		D_ExceptionOfThread.Mythread mythread=new D_ExceptionOfThread().new Mythread();
		//这里通过Thread的静态方法，可以为所有线程设置默认处理器，比如把异常信息打印到日志中。
//		Thread.setDefaultUncaughtExceptionHandler(new D_ExceptionOfThread().new MyExceptionHandler());
		mythread.start();
	}

}
