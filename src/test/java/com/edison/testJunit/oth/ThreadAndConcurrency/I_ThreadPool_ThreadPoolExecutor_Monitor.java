package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 基于前面用线程池实现对一个数字数组分块计算合计值的监控*/
public class I_ThreadPool_ThreadPoolExecutor_Monitor {
	private static List<Integer> array=new ArrayList<Integer>(1024);
	private static HashMap<String,Future<Integer>> resmap=new HashMap<String,Future<Integer>>();//这样可以保留任务具体信息
	private static final int SIZE=3;//每次处理数组的个数
	
	public static void main(String[] args) throws InterruptedException  {
		int totle=0;//汇总结果
		
		for(int i=1;i<1000;i++){//初始化数组
			array.add(i);
		}
		
		//单线程遍历的方式
		for(int i:array){
			totle+=i;
		}
		System.out.println("单线程汇总结果是："+totle);
		
		//线程池模式下汇总
		ThreadPoolExecutor pool=new ThreadPoolExecutor(5, 10,
				                               15000, TimeUnit.MILLISECONDS, 
				                               new ArrayBlockingQueue<Runnable>(512),
				                               new MyThreadFactory("合并计算线程池"),
				                               new MyRejectedExecutionHandler()
				);
		
		//启动监控线程池线程
		Thread monitorThread=new Thread(new ThreadPoolMonitor(pool));
//		monitorThread.setDaemon(true);
		monitorThread.start();
		
		totle=0;
		for(int i=0;i<array.size();i+=SIZE){
			CalcuArray calcuArray;
			if(i+SIZE>=array.size()){
				calcuArray=new CalcuArray(array.subList(i, array.size()));
			}else{
				calcuArray=new CalcuArray(array.subList(i, i+SIZE));
			}
			Future<Integer> future=pool.submit(calcuArray);
		
			resmap.put(""+i,future);
		}
		
//		System.out.println("开始依次获取结果");
		
		Set<String> keys=resmap.keySet();
		for(String key:keys){
			try{
				Future<Integer> task=resmap.get(key);
				if(task.isCancelled()) {//这个要放到isDone前面，因为futureTask只要状态不是0-初始，其他都是done。。
					System.err.println("该任务被取消,任务计算起始编号："+key);
					continue;
				}
//				if(task.isDone()) {//只有确认完成才获取结果
					totle+=task.get(); //因为外围for循环只执行一次，所以如果task状态为0-初始,即任务还没执行，这里获取
										//不到结果，如果不再次循环的话，那就获取不到该任务的结果
//				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		System.out.println("最终结果:"+totle);
		
		pool.shutdown();//关闭线程池，否则程序不会停止
		
		monitorThread.join();
		
	}

}

class ThreadPoolMonitor implements Runnable{
	ThreadPoolExecutor pool=null;
	
	ThreadPoolMonitor(ThreadPoolExecutor pool){
		this.pool=pool;
	}
	public void run() {
		//线程池的一些基本不变的参数
		int coreSize=pool.getCorePoolSize();
		int maxSize=pool.getMaximumPoolSize();
		BlockingQueue<Runnable> queue=pool.getQueue();
		long keepAlive=pool.getKeepAliveTime(TimeUnit.SECONDS);
		
		System.out.println("------------------------------------------------------");
		System.out.println("    coreSize    maxSize    queue    keepAliveTime(SECONDS)");
		System.out.println(String.format("%10d%10d    %s  %d", coreSize,maxSize,queue.getClass().getSimpleName(),keepAlive));
		
		//线程池还在运行，其中如果线程池状态是SHUTDOWN，他还可能在处理firstTask或者队列中的任务，该状态不能作为停止判断
		do{
			try {
				Thread.sleep(1000);//10毫秒打印一次
			} catch (InterruptedException e) {}//
			System.out.println("------------------------------------------------------");
			System.out.println("    当前线程池中线程数量    执行任务的线程数    队列中的数量    已完成task数量");
			int ac=pool.getActiveCount();//执行任务的线程数
			int pc=pool.getPoolSize();//当前线程池中线程数量
			long count=pool.getCompletedTaskCount();//已完成task数量
			int size=queue.size();//队列中的数量
			System.out.println(String.format("%10d%10d%10d%10d", pc,ac,size,count));
		}while((pool!=null&&!pool.isTerminating() && !pool.isTerminated() ));
		
		System.out.println("线程池已经关闭");
		
	}
	
}

