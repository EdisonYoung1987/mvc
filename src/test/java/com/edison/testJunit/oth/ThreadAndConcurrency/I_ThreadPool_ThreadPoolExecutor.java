package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 创建线程池的推荐方法：ThreadPoolExecutor<p>
 * 实现对一个数字数组分块计算合计值*/
public class I_ThreadPool_ThreadPoolExecutor {
	private static List<Integer> array=new ArrayList<Integer>(1024);
//	private static List<Future<Integer>> resList=new ArrayList<Future<Integer>>();
	private static HashMap<String,Future<Integer>> resmap=new HashMap<String,Future<Integer>>();//这样可以保留任务具体信息
	private static final int SIZE=3;//每次处理数组的个数
	
	public static void main(String[] args)  {
		int totle=0;//汇总结果
		
		for(int i=1;i<1000;i++){//初始化数组
			array.add(i);
		}
		
		//单线程遍历的方式
		for(int i:array){
			totle+=i;
		}
		System.out.println("汇总结果是："+totle);
		
		//线程池模式下汇总
		ThreadPoolExecutor pool=new ThreadPoolExecutor(2, 4,
				                               1000, TimeUnit.MILLISECONDS, 
				                               new ArrayBlockingQueue<Runnable>(2),
				                               new MyThreadFactory("合并计算线程池"),
				                               new MyRejectedExecutionHandler()
//				                               new ThreadPoolExecutor.AbortPolicy()//这个会导致submit时直接抛异常
//				                               new ThreadPoolExecutor.CallerRunsPolicy()//由任务提交线程自己去执行被拒绝任务的run()方法
//				                               new ThreadPoolExecutor.DiscardOldestPolicy()//很多任务会被丢弃且无记录
//				                               new ThreadPoolExecutor.DiscardPolicy()//直接丢弃任务不做任何处理
				);//线程工厂没用上
//		pool.setRejectedExecutionHandler(handler); 单独设置被拒绝的任务的处理
		totle=0;
		for(int i=0;i<array.size();i+=SIZE){
			CalcuArray calcuArray;
			if(i+SIZE>=array.size()){
				calcuArray=new CalcuArray(array.subList(i, array.size()));
			}else{
				calcuArray=new CalcuArray(array.subList(i, i+SIZE));
			}
			Future<Integer> future=pool.submit(calcuArray);
		
			resmap.put(""+i,future);//把结果集放到这里，后面统一取值，这样不会因get阻塞导致没有达到并发效果
		}
		
		System.out.println("开始依次获取结果");
		
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
	}

}

class CalcuArray implements Callable<Integer>{//计算子数组的和
	private List<Integer> arr;
	public CalcuArray(List<Integer> arr){
		this.arr=arr;
	}
	public Integer call() throws Exception {
		Integer totle=0;
		for(Integer i:arr){
			totle+=i;
		}
		Thread.sleep(100);
		return totle;
	}
	
}

/**如果不同的业务有不同的线程池，可以通过线程池名称加以区分*/
class MyThreadFactory implements ThreadFactory{//仿照DefaultThreadFactory，只不过线程是自定义名称而已
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    MyThreadFactory(String poolName) {//如果不同的业务有不同的线程池，可以通过线程池名称加以区分
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                              Thread.currentThread().getThreadGroup();
        namePrefix =poolName+"-thread-";
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                              namePrefix + threadNumber.getAndIncrement(),
                              0);
        if (t.isDaemon())
            t.setDaemon(false);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
	
}

/**corePoolSize->queue->maximumPoolSize->rejectHandler<p>
 * 这里是用来最终处理无法处理也无法存储的任务的,线程池ThreadPoolExecutor自带了4个拒绝策略类：<p>
 * AbortPolicy。策略就是直接抛出异常 --默认策略<br>
 * CallerRunsPolicy。调用runnable对象的run()方法<br>
 * DiscardOldestPolicy。丢掉队列中最老的任务(队列的head)，然后重试<br>
 * DiscardPolicy。直接丢掉该任务<br>
 * */
class MyRejectedExecutionHandler implements RejectedExecutionHandler{

	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println("当前线程名称："+Thread.currentThread().getName());
		
		//这里调用线程池的submit()方法提交任务时，任务Callable或者Runnable会被封装成FutureTask，肯定未知
		FutureTask<Integer> futureTask=(FutureTask<Integer>)r;
		
		/*1. executor.execute(r): 这肯定是不对的，线程池已经满了，拒绝执行，还放回去，线程池继续拒绝，再放回去，造成死循环
		  2. r.run()：这是针对不需要返回结果的情况，是用主线程去执行这个run方法，这样会导致主线程阻塞
		  3. 记日志并拒绝*/
		if(!executor.isShutdown()) {//线程池异常关闭也可能导致拒绝策略生效
			try {//如果要执行，一定要进行异常捕捉
				boolean flag=futureTask.cancel(false);//false-如果已经执行中，就不要去中断
				if(!flag) {//取消任务失败原因有三种：已经完成、已经被cancel，其他原因，前两者不用管
					futureTask.run(); //无法停止还是执行吧
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
