package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**几个线程各自写一个文件，最后写完的线程打印这些文件名称*/
public class G_CyclicBarrier {
	private static CyclicBarrier cyclicBarrier;
	private static final String PATH="D:\\tmp\\";
	
	public G_CyclicBarrier() {
		MyRunnable_listFile listfile=this.new MyRunnable_listFile();
		cyclicBarrier=new CyclicBarrier(5,listfile);//所有参与者都到底屏障时，执行listfile
	}
	
	public static void main(String[] args) {
		G_CyclicBarrier gcb=new G_CyclicBarrier();
		MyRunnable_WriteFile wf=gcb.new MyRunnable_WriteFile();
		for(int i=0;i<5;i++) {
			Thread thread=new Thread(wf);
			thread.setName(""+i);
			thread.start();
			if(i==3)
				thread.interrupt();
		}

	}

	class MyRunnable_WriteFile implements Runnable{//写文件

		public void run() {
			String fileName=Thread.currentThread().getName();
			File file=new File(PATH+fileName);
			if(file.exists()) {
				file.delete();
			}
			
			try {
				file.createNewFile();
//				这里有问题
				while(true) {
					try {
						if(!cyclicBarrier.isBroken()) {
							System.out.println("线程"+Thread.currentThread().getName()+"进入await()");
							cyclicBarrier.await(); //到达屏障
						}else {
							continue;
						}
						break;
					} catch (InterruptedException e) {//这些异常都需要重置屏障，然后重新await
//						e.printStackTrace();
//						cyclicBarrier.reset(); //直接这样写的话就会被reset多次，不好
						System.out.println("线程"+Thread.currentThread().getName()+"收到中断，"
								+ "执行reset后重新await");
						if(cyclicBarrier.isBroken()) {
							synchronized (cyclicBarrier) {
								if(cyclicBarrier.isBroken()) {
									cyclicBarrier.reset(); //保证只被一个线程执行一次
								}
							}
						}
					} catch (BrokenBarrierException e) {//该异常两个可能: await时被中断、await的信号是broken的
						System.out.println("线程"+Thread.currentThread().getName()+"收到异常Broken,"
								+ "重新await()");
//						e.printStackTrace();
//						cyclicBarrier.reset();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	class MyRunnable_listFile implements Runnable{
		public void run() {
			File file=new File(PATH);
			String[] files=file.list();
			for(String filetmp:files) {
				System.out.println(filetmp);
			}
		}
	}
}
