package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 使用执行器Executors生成线程池完成H_CallableAndFuture中统计包含关键字的文件数目<p>
 * 这几种方法创建的线程池都有oom的风险，不建议使用，直接使用指定长度的阻塞队列的ThreadPoolExecutor的构造方法*/
public class I_ThreadPool_Executors {

	public static void main(String[] args) {//Executors方法最好不用，使用后面的ThreadPoolExecutor方法
		//下面分配线程池2和3两种方法都不可行，因为在本案例中涉及到递归分配线程，如存在线程1
		//执行过程中分配了线程2、3、4、5、...等，线程2、3、4等等又分配了子线程，如果采用方法2
		//和3，则很大可能存在线程被用满而又无法分配多于线程去完成任务，导致某些线程一直被占用，
		//最后整个程序就卡住了。--除非后面检索时不采用线程递归分配子线程的方式
//		ExecutorService pool=Executors.newCachedThreadPool();//生成一个线程池。
		ExecutorService pool=Executors.newFixedThreadPool(2);//生成一个线程池2，线程数必须大于2，
		                                              //因为MyCallable2和SearchFile各自占用一个线程，且两者有从属关系
//		ExecutorService pool=Executors.newSingleThreadExecutor();//生成一个线程池3。
		MyCallable2 call=new MyCallable2(new File("C:\\Users\\Edison\\git\\mvc"), "class",pool); //生成Callable对象

		//计时开始
		long starttime=System.currentTimeMillis();
		Future<Integer> result=pool.submit(call);//将任务提交给线程池
		
		try{
			System.out.println(result.get());
			//计时结束
			long endtime=System.currentTimeMillis();
			System.out.println("总计耗时(毫秒)："+(endtime-starttime));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}

/**根据路径和关键字查找文件的服务*/
class MyCallable2 implements Callable<Integer>{
	private File path;
	private String key;
	private ExecutorService pool;
	
	public MyCallable2(File path,String key,ExecutorService pool){
		this.path=path;
		this.key=key;
		this.pool=pool;
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
				try{//这里可以优化，增加一个专门遍历文件的Callable的线程池。
					SearchFile searchFile=new SearchFile(basefile, key);
					Future<Integer> task=pool.submit(searchFile); //将查找任务提交给线程池
					totle+=task.get();
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{//目录
				File[] files=basefile.listFiles();
				for(File file:files){ //这里最后采用单线程递归而不是多线程，可以在遍历文件时采用线程池
					totle+=searchFile(file,key); //单线程模式
				}
			}
		}
		return totle;
	}
}

class SearchFile implements Callable<Integer>{//只有一个作用，就是检索当前文件中是否含有关键字
	private File file;
	private String key;
	
	public SearchFile(File file,String key){
		this.file=file;
		this.key=key;
	}


	public Integer call() throws Exception {
		Scanner scan=new Scanner(file);
		while(scan.hasNextLine()){
			String line=scan.nextLine().toUpperCase();
			if(line.indexOf(key.toUpperCase())!=-1){
				scan.close();
				return 1;
			}
		}
		scan.close();
		return 0;
	}
}