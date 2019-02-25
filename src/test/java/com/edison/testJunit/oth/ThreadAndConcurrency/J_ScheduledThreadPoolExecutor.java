package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**使用ScheduledThreadPoolExecutor完成一个定时任务的设定，且在特定情况下退出*/
public class J_ScheduledThreadPoolExecutor {
	static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() { //SimpleDateFormat是线程不安全的
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};
	
	public static void main(String[] args) {
		File file=new File("D:\\tmp\\task.txt");
		if(file.exists()) {
			file.delete();
		}
		
		Date d = new Date();
		String dateNowStr = df.get().format(d);
		System.out.println("当前时间：" + dateNowStr);
		
		ScheduledThreadPoolExecutor pool=new ScheduledThreadPoolExecutor(10
				//, threadFactory, handler
				); //这两个参数就不管了
		Task_Later1 task1=new Task_Later1();
		pool.schedule(task1, 20, TimeUnit.SECONDS);//只执行一次且不关注结果，就不管了
		
		Task_Repeat1 task2=new Task_Repeat1(pool); //循环任务无法传入Callable
		pool.schedule(task2, 1, TimeUnit.SECONDS);
	}
}

/**一个main方法启动后延迟16秒执行的单次任务：创建一个文件<br>*/
class Task_Later1 implements Runnable{

	@Override
	public void run() {
		Date d = new Date();
		String dateNowStr = J_TimerAndTimerTask.df.get().format(d);
		System.out.println("这个是延迟16秒执行的任务，当前时间"+dateNowStr+" 执行线程"+Thread.currentThread().getName());
		
		File file=new File("D:\\tmp\\task.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

/**在延迟指定时间后以指定的间隔时间循环执行定时任务,并在满足一定条件后停止执行<p>
 * 每1秒检测某个文件是否存在，存在则停止自己*/
class Task_Repeat1 implements Runnable{
	private ScheduledThreadPoolExecutor pool;
	
	public Task_Repeat1(ScheduledThreadPoolExecutor pool) {
		this.pool=pool;
	}
	@Override
	public void run() {
		Date d = new Date();
		String dateNowStr = J_TimerAndTimerTask.df.get().format(d);
		System.out.println("这个是每1秒执行的循环任务，当前时间"+dateNowStr+" 执行线程"+Thread.currentThread().getName());
		
		File file=new File("D:\\tmp\\task.txt");
		if(file.exists()) {
			System.out.println("该文件已被创建，退出检测");
			pool.shutdown();
			return;
		}
		
		//未检测到文件则重新加载任务
		pool.schedule(this, 1, TimeUnit.SECONDS);
	}
}
