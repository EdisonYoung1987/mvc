package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**主线程执行await()进入等待，直到计数器=0时返回<br>
 * 其他子线程执行完任务后对计数器-1；<br>
 * 这个比主线程一个个执行thread.join()方便些*/
public class G_CountDownLatch {
	private static CountDownLatch countDownLatch=new CountDownLatch(10);
	
	public static void main(String[] args) {
		G_CountDownLatch.MyRunnable myRunnable=new G_CountDownLatch().new MyRunnable();
		for(int i=0;i<9;i++) {
			new Thread(myRunnable ).start();
		}
		try {
			System.out.println("主线程进入等待");
			boolean isEnough=countDownLatch.await(10, TimeUnit.SECONDS);
			if(isEnough) {
				System.out.println("计数器为0，主线程返回");
			}else {
				System.out.println("主线程等到超时都没等到计数器=0");
			}
		} catch (InterruptedException e) {
		}
	}

	class MyRunnable implements Runnable{

		public void run() {//sleep随机时间后计数器减一
			int time=new Random(Thread.currentThread().getId()).nextInt(4000)+1000;//实际范围就是1000-5000了
			System.out.println("线程"+Thread.currentThread().getName()+"sleep时间："+time);
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
			}finally {
				System.out.println("线程"+Thread.currentThread().getName()+"计数器-1，当前值："+countDownLatch.getCount());
				countDownLatch.countDown();
			}
		}
		
	}
}

