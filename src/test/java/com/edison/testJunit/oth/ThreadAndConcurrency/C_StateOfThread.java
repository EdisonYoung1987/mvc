package com.edison.testJunit.oth.ThreadAndConcurrency;

/**线程状态<p>
 * 线程状态有：NEW-新建 <br>
 *           RUNNABLE-可运行<br> 
 *           BLOCKED-阻塞<br> 
 *           WAITING-等待<br> 
 *           TIMED_WAITING-计时等待<br> 
 *           TERMINATED-终止*/
public class C_StateOfThread {
	class MyRunnable extends Thread{
		public void run(){
			System.out.println("--线程进入运行状态：thread.run()获取到cpu时间片段啦");
			try{
				Thread.sleep(12);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	public static void main(String[] args){
		C_StateOfThread.MyRunnable my=new C_StateOfThread().new MyRunnable();
		my.setName("我是线程黄");
		System.out.println("--线程id："+my.getId()+" 名称:"+my.getName()+" 所属线程组:"+my.getThreadGroup()+
				" 是否守护线程:"+my.isDaemon()+" 状态: "+my.getState()+" 是否active:"+my.isAlive());
		System.out.println("--线程进入新建状态：new Thread(my)");
		Thread thread=new Thread(my);//此时进入新建状态
		System.out.println("--线程进入可运行状态：thread.start()");//能不能运行还要看cpu能不能给分配时间片段
		thread.start();

		System.out.println("检测到子线程状态:"+thread.getState());
		try{
			Thread.sleep(2);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("检测到子线程状态:"+thread.getState());
		try{
			thread.join();
			thread.setPriority(5);
			thread.setDaemon(true);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("检测到子线程状态:"+thread.getState());

		try{
			Thread.sleep(20);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("检测到子线程状态:"+thread.getState());

	}
}
