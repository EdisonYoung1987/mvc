package com.edison.testJunit.oth.ThreadAndConcurrency;

/**
 * 启动一个新的线程:两种方式*/
public class A_StartAThread {
	
	class MyRunnable1 implements Runnable{//内部类-实现Runnable.run()的方式
		public void run() {
			for(int i=1;i<11;i++){
				System.out.println("方法1 Thread:"+Thread.currentThread().getId()+":"+i);
			}
		}
		
	}

	class MyRunnable2 extends Thread{
		public void run() {
			for(int i=1;i<11;i++){
				System.out.println("方法2 Thread:"+Thread.currentThread().getId()+":"+i);
			}
		}
	}
	
	public static void main(String[] args) {
		A_StartAThread a_StartAThread=new A_StartAThread();
		A_StartAThread.MyRunnable1 m1= a_StartAThread.new MyRunnable1();//★ 内部类的创建方式
		for(int i=0;i<5;i++){
			//MyRunnable1实例只有一个，而Thead(m1)到时有多个，和下面M2的区别
			new Thread(m1).start();//不能直接调用m1的run方法，那样的话只会执行run()，而没有启动多线程
		}
		
		
		for(int i=0;i<5;i++){
			//注意这里 MyRunnable2因为是Thead子类，所以要启动多线程，就需要实现MyRunnable2的多个实例
			//多线程，实际上就是多个 new ？ extends Thead()
			a_StartAThread.new MyRunnable2().start();//m2继承了Thread，所以可以直接start
		}
	}

}
