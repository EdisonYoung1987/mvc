package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.util.concurrent.Semaphore;

/**信号量：提供一个许可的集合.<p>
 * 资源使用者通过acquire()获取许可，当当前无许可可用时阻塞，直到被中断或者获取许可。<br>
 * 此处模拟一个公共厕所场景*/
public class G_Semaphore {
	private static Semaphore publicToilet=new Semaphore(5);//只有五个坑位
	public static void main(String[] args) throws InterruptedException {
		G_Semaphore.People people=new G_Semaphore().new People();
		for(int i=0;i<20;i++) {//20个人竞争厕所，非公平模式
			Thread thread=new Thread(people,""+i);
			thread.start();
		}
		Thread.sleep(1000);
	}
	
	class People implements Runnable{
		public void run() {
			System.out.println("抢厕所中...");
			for(;;) {
				String name=Thread.currentThread().getName();
				try {
					publicToilet.acquire();//获取资源，这个方法会被中断
					System.out.println(name+"抢到坑位，使用厕所中...");
					Thread.sleep(5000);
					System.out.println(name+"搞定离开");
					publicToilet.release(); //释放资源
					return;
				} catch (InterruptedException e) {
					System.out.println(name+"被打也要继续抢");
				} 
			}
		}
		
	}

}
