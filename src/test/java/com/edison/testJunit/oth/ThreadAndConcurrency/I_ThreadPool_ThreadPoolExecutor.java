package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 创建线程池的推荐方法：ThreadPoolExecutor<p>
 * 实现对一个数字数组分块计算合计值*/
public class I_ThreadPool_ThreadPoolExecutor {
	private static List<Integer> array=new ArrayList<Integer>();
	private static List<Future<Integer>> resList=new ArrayList<Future<Integer>>();
	private static final int SIZE=3;//每次处理数组的个数
	
	public static void main(String[] args)  {
		int totle=0;//汇总结果
		
		for(int i=10;i<1000;i+=100){//初始化数组
			array.add(i);
		}
		
		//单线程遍历的方式
		for(int i:array){
			totle+=i;
		}
		System.out.println("汇总结果是："+totle);
		
		//线程池模式下汇总
		ThreadPoolExecutor pool=new ThreadPoolExecutor(200, 10000,
				                               1000, TimeUnit.MILLISECONDS, 
				                               new ArrayBlockingQueue<Runnable>(20)
//				                               threadFactory,
//				                               handler
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
			/*try{
				totle+=future.get(); //在这里get的话会造成阻塞，直到获得结果才处理下一个数组块，违背了多线程并发的预想
				System.out.println("获取结果："+totle);
			}catch(Exception e){
				e.printStackTrace();
			}*/
			resList.add(future);//把结果集放到这里，后面统一取值，这样不会因get阻塞导致没有达到并发效果
		}
		
		for(Future<Integer> res:resList){
			try{
				totle+=res.get();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		System.out.println("获取结果:"+totle);
		
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
		return totle;
	}
	
}
