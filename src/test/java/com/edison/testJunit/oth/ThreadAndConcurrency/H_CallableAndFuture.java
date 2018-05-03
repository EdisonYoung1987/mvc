package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 根据指定路径和关键字计算内容含关键字的文件总数目*/
public class H_CallableAndFuture {

	public static void main(String[] args) {
		File file=new File("C:\\Users\\Edison\\git\\mvc");
		String key="class";
		
		Callable<Integer> call=new MyCallable(file,key);
		FutureTask<Integer> futureTask=new FutureTask<Integer>(call);//futureTask的run()调用call的call()方法
		
		//计时开始
		long starttime=System.currentTimeMillis();
		
		Thread thread=new Thread(futureTask);
		thread.start();
		
		try{//获取计算结果
			System.out.println("共计"+futureTask.get()+"个文件含有:"+key);
		}catch( InterruptedException e){
			System.out.println("线程被中断");
		}catch(ExecutionException e){
			System.out.println("计算抛异常");
			e.printStackTrace();
		}
		
		//计时结束
		long endtime=System.currentTimeMillis();
		System.out.println("总计耗时(毫秒)："+(endtime-starttime));
	}

}

/**根据路径和关键字查找文件的服务*/
class MyCallable implements Callable<Integer>{
	private File path;
	private String key;
	
	public MyCallable(File path,String key){
		this.path=path;
		this.key=key;
	}
	
	public Integer call() throws Exception {//接口必须实现方法，由futureTask.run()调用
		int totle=0;
		totle=searchFile(path,key);
		return totle;
	}
	
	public int searchFile(File basefile,String key){//递归调用查找关键字，忽略大小写
		int totle=0;
		if(!basefile.exists()){
			System.err.println("文件不存在："+basefile.getPath());
			return 0;
		}else{
			if(basefile.isFile()){//文件 这里还可以优化，比如不同格式的文件要不同处理，如exe跳过，rar要用工具打开
				try{
					Scanner scan=new Scanner(basefile);
					while(scan.hasNextLine()){
						String line=scan.nextLine().toUpperCase();
						if(line.indexOf(key.toUpperCase())!=-1){
							totle=1;
							return totle;
						}
					}
					scan.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{//目录
				File[] files=basefile.listFiles();
				for(File file:files){
					totle+=searchFile(file,key); //单线程模式
					
					//多线程模式
//					MyCallable mycall=new MyCallable(file, key);
//					FutureTask<Integer> task=new FutureTask<Integer>(mycall);
//					Thread th=new Thread(task);
//					th.start();
//					try{
//						totle+=task.get();
//					}catch(Exception e){
//						e.printStackTrace();
//					}
				}
			}
		}
		return totle;
	}
}
