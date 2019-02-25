package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**使用Timer定时调度框架(单线程)执行TimerTask任务(Runnable)*/
public class J_TimerAndTimerTask {
	static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() { //SimpleDateFormat是线程不安全的
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};
	
	public static void main(String[] args) {
		Date d = new Date();
		String dateNowStr = df.get().format(d);
		System.out.println("当前时间：" + dateNowStr);
		
		Timer timer=new Timer();

		Task_Later task1=new Task_Later();//添加第一个任务 6秒后执行
		timer.schedule(task1, 6000); //相当于timer.schedule(task1, 6000, 0)或者schedule(task1, Date time+6000)
		
		Task_Reapet task2=new Task_Reapet(timer);
		timer.schedule(task2, 0, 1000);
		
		//timer调度器在task2中关闭
	}

}
/**一个main方法启动后延迟6秒执行的单次任务：创建一个文件<br>*/
class Task_Later extends TimerTask{

	@Override
	public void run() {
		Date d = new Date();
		String dateNowStr = J_TimerAndTimerTask.df.get().format(d);
		System.out.println("这个是延迟6秒执行的任务，当前时间"+dateNowStr);
		
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
class Task_Reapet extends TimerTask{
	private Timer timer;//用于关闭调度器
	
	public Task_Reapet(Timer timer) {
		this.timer=timer;
	}
	
	@Override
	public void run() {
		Date d = new Date();
		String dateNowStr = J_TimerAndTimerTask.df.get().format(d);
		System.out.println("这个是每1秒执行的循环任务，当前时间"+dateNowStr);
		
		File file=new File("D:\\tmp\\task.txt");
		if(file.exists()) {
			System.out.println("该文件已被创建，退出检测");
			this.cancel();//取消任务
			timer.cancel(); //关闭调度器
		}
		
	}
}
