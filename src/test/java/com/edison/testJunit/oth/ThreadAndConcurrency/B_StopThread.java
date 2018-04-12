package com.edison.testJunit.oth.ThreadAndConcurrency;

/**
 * 通知线程终止<p>
 * 线程除了执行到代码尾或者return外:<br>
 * 1.还可以通过interrupt方式终止<br>
 * 2.对于实现Runnable的对象，还可以通过设定某一volatile属性来终止*/
public class B_StopThread {
	/**方式1-interrupt方式终止*/
	class MyRunnable1 extends Thread{
		@Override
		public void run(){
			System.out.println("线程1："+Thread.currentThread().getId()+"启动了");
			/*try{
				Thread.sleep(5000);
			}catch(InterruptedException e){//收到第一次主线程的interrupt
			}*/
			long res=0L,i=1L;
			for(;i<100000000;i++){//挂起情况被interrupt()，是不会设置interrupted标志的
				res+=i*(i+1);
			}
			//这里有个问题，如果线程是在挂起的时候，比如sleep，被打断，interrupted()方法是不会返回true的
			if(Thread.currentThread().interrupted()){//查看自己是sleep够了还是中途被唤醒,同时重置打断标志
				System.out.println("半夜被人吵醒了,i=100000000?"+i);
				System.out.println("继续睡");
			}else{
				System.out.println("一直睡到天亮，就等闹钟了");
			}
			int num=0;
			while(!Thread.currentThread().isInterrupted()){//这个方法只会探测有没有人试图打断自己，不重置打断标志
				num++;
			}
			System.out.println("闹钟吵醒，num="+num);
		}
	}
	/**
	 * 方式2-通过属性来终止*/
	class MyRunnable2 implements Runnable{
		volatile boolean  stopFlag=false; //这个volatile非常重要，jvm会要求线程每次读取时都去主内存获取最新值
		                                  //如果不是volatile的话，如果该线程一直在忙，比如num++,则根本不会去主内存
		                                  //获取最新值，而是从线程内存中取
		public void run() {
			System.out.println("线程："+Thread.currentThread().getId()+"启动了");
			int num=0;
			while(!stopFlag){  //进入循环，直到stop被置为true
				num++;
			}
			System.out.println("线程："+Thread.currentThread().getId()+"被停止了,num="+num);
		}
	}
	
	public static void main(String[] args) {
		B_StopThread b_StopThread=new B_StopThread();
		B_StopThread.MyRunnable1 myRunnable1=b_StopThread.new MyRunnable1();
		B_StopThread.MyRunnable2 myRunnable2=b_StopThread.new MyRunnable2();
		
		//interrupt方式中断
		myRunnable1.start();
		try{
			Thread.sleep(1);
		}catch(Exception e){
			e.printStackTrace();
		}
		myRunnable1.interrupt(); //主线程调用该方法，打断myRunnable1线程第一次睡眠
		try{
			Thread.sleep(2000);//这里必须时间长，否则可能存在main()线程退出，而子线程只处理了一次中断请求的可能。
		}catch(Exception e){
			e.printStackTrace();
		}
		myRunnable1.interrupt(); //第二次打断其睡眠,如果sleep过短，在子线程还在计算的情况下，可能两次interrupt都被interrupted捕获
		
		
		//volatile方式中断。
		Thread thread1=new Thread(myRunnable2);
		Thread thread2=new Thread(myRunnable2); //两个线程共用同一个myRunnable对象，所以一旦设置stop标志，都会被停止
		thread1.start();
		thread2.start();
		//分出两个线程后，主线程来执行停止
		try{
			Thread.sleep(2);
		}catch(Exception e){
			e.printStackTrace();
		}
//		for(int i=0;i<1000000000;i++){}
		myRunnable2.stopFlag=true;
		System.out.println("main()结束");
	}

}
