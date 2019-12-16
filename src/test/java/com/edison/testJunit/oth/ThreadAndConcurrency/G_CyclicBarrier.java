package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**几个线程各自写一个文件，最后写完的线程打印这些文件名称*/
public class G_CyclicBarrier {
	private static CyclicBarrier cyclicBarrier;
	private static final String PATH="D:\\tmp\\tmp\\";
	
	public G_CyclicBarrier() {
		MyRunnable_listFile listfile=this.new MyRunnable_listFile();
		cyclicBarrier=new CyclicBarrier(102,listfile);//所有参与者都到底屏障时，执行listfile
	}
	
	public static void main(String[] args) {
		G_CyclicBarrier gcb=new G_CyclicBarrier();
		MyRunnable_WriteFile wf=gcb.new MyRunnable_WriteFile();
		for(int i=0;i<102;i++) {
			Thread thread=new Thread(wf);
			thread.setName("Write_Thread_"+i);
			thread.start();
			if(i==100)
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
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				boolean isBroken=false;
				while(true) {
					try {
						if(!cyclicBarrier.isBroken()) {//避免对broken的信号await导致直接异常
							System.out.println("线程"+Thread.currentThread().getName()+"进入await()");
							cyclicBarrier.await(); //到达屏障
						}else {//最好不要马上continue
							Thread.yield(); //让其他线程先执行一下
							continue;
						}
						break;
					} catch (InterruptedException e) {//这些异常都需要重置屏障，然后重新await
						System.out.println("线程"+Thread.currentThread().getName()+"收到中断，"
								+ "执行reset后重新await");
						isBroken=true;
					} catch (BrokenBarrierException e) {//await的信号是broken的
						System.out.println("线程"+Thread.currentThread().getName()+"收到异常Broken,"
								+ "重新await()");
						isBroken=true;
					}finally {
						if(isBroken) {
							synchronized (cyclicBarrier) {
								if (cyclicBarrier.isBroken()) {
									cyclicBarrier.reset(); //保证只被一个线程执行一次
								}
							}
							isBroken=false;
						}
					}
				}
			}
		}
		
	}
	
	class MyRunnable_listFile implements Runnable{
		public void run() {
			System.out.println("已达到屏障点，开始打印文件");
			File file=new File(PATH);
			String[] files=file.list();
			Arrays.sort(files);
			for(String filetmp:files) {
				System.out.println(filetmp);
			}
		}
	}
}
