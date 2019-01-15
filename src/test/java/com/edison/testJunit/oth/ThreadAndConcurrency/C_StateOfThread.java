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
			System.out.println("--子线程打印日志中 线程进入状态："+Thread.currentThread().getState());
			try{
				Thread.sleep(12);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	public static void main(String[] args){
		//线程组： System->main
		System.out.println("主线程main的所属线程组:"+Thread.currentThread().getThreadGroup().getName()+
				"    \n  父线程组:"+Thread.currentThread().getThreadGroup().getParent().getName()+
				"    \n  父父线程组:"+Thread.currentThread().getThreadGroup().getParent().getParent());
		
		C_StateOfThread.MyRunnable my=new C_StateOfThread().new MyRunnable();
		System.out.println("--线程id："+my.getId()+" 名称:"+my.getName()+" 所属线程组:"+my.getThreadGroup().getName()+
				" \n   是否active:"+my.isAlive());
		System.out.println("--new Thread(my) 线程进入状态："+my.getState());
		
		my.start();;//此时进入新建状态
		System.out.println("--thread.start() 线程进入状态："+my.getState());
		
		try{
			Thread.sleep(2);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("--Thread.sleep(12) 线程进入状态:"+my.getState());
		try{
			my.join();
			my.setPriority(5);
			my.setDaemon(true); //守护线程的所属线程组变成null
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("检测到子线程状态:"+my.getState());

		try{
			Thread.sleep(20);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("检测到子线程状态:"+my.getState());

	}
}
