package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;



/**
 * 根据文件内容检索文件的工具.<p>
 * 输入路径名和查找内容即可检索到包含该内容的文件*/
public class G_ArrayBlockingQueue {
	//检索路径和关键字
	private static String path,keyWord;
	private static ArrayBlockingQueue<File> queue=new ArrayBlockingQueue<File>(64,true);//线程安全的阻塞队列 公平竞争
	
	//空文件，用来作为线程查找blockingQueue终点
	public static final File ENDFILE=new File("");
	
	//用于存放所有的futureTask，用于最后获取执行结果
	private static HashSet<FutureTask<Integer>> resset=new HashSet<FutureTask<Integer>>(16);
	
	public static void main(String[] args) {
		int total=0; //总的匹配次数
//		Scanner scan=new Scanner(System.in);
		
		//获取检索路径和关键字
		/*System.out.println("输入检索绝对路径名(如/user/edison/):");
		G_ArrayBlockingQueue.path=scan.nextLine();
		System.out.println("输入检索关键字");
		G_ArrayBlockingQueue.keyWord=scan.nextLine();
		scan.close();*/
//		path="C:\\Users\\Edison\\git\\mvc2\\src\\main\\java\\com\\edison";
		path="C:\\Users\\Edison\\git\\mvc2";
		keyWord="class";
		
		File searchFile=new File(path);
		if(!searchFile.exists()){
			System.out.println("该文件或路径不存在！");
			return;
		}
		
		//分配一个线程用于递归方式读取路径下面所有的文件
		EnumerateFile enumerateFile=new EnumerateFile(queue,searchFile);
		new Thread(enumerateFile).start();//这里不需要等待该线程将所有File列表加入到queue中，因为是阻塞
										  //队列，可以边放边取直到取到空File
		
		//多个子线程去遍历刚刚查出来的文件的内容，不需等待，边放边取
		SearchContent searchContentCallable=new SearchContent(queue,keyWord);
		for(int i=0;i<16;i++){
			FutureTask<Integer> futureTask=new FutureTask<Integer>(searchContentCallable); //不要放循环外面
			Thread thread=new Thread(futureTask);
			thread.start();
			
			//total+=futureTask.get();//放在这里执行的话，只有等待这个线程执行完了才能获取，实际上第一个线程可能会执行完所有任务
			resset.add(futureTask);
		}
		
		//当所有线程都启动起来后再去等待结果
		Iterator<FutureTask<Integer>> it=resset.iterator();
		while(it.hasNext()) {
			FutureTask<Integer> futureTask=it.next();
			try {
				total+=futureTask.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("总的匹配数"+total);
		
	}
	
}

class EnumerateFile implements Runnable{//分配一个线程用于递归方式读取路径下面所有的文件
	BlockingQueue<File> queue;
	File startPath;
	
	public EnumerateFile(BlockingQueue<File> queue,File startPath){
		this.queue=queue;
		this.startPath=startPath;
	}
	
	public void run(){
		try{
			enumerateFile(queue, startPath);
			queue.put(G_ArrayBlockingQueue.ENDFILE); //将终止标志文件放入队列，检索线程取到该文件则表示检索完毕退出
		}catch(InterruptedException e){
			System.out.println("检索过程被打断，结果可能不全");
		}
	}
	
	public void enumerateFile(BlockingQueue<File> queue,File startFile) throws InterruptedException{
		if(startFile.isFile()){
				queue.put(startFile);
		}else{
			File[] files=startFile.listFiles();
			for(File file:files){
				enumerateFile(queue, file);
			}
		}
	}
}

class SearchContent implements Callable<Integer>{
	private BlockingQueue<File> queue;
	private String keyWord;
	ThreadLocal<TreeSet<String>> localSet=new ThreadLocal<TreeSet<String>>();//treeSet是自然排序的，即插入顺序
	
	public  SearchContent(BlockingQueue<File> queue,String keyWord){
		this.queue=queue;
		this.keyWord=keyWord;
	}
	
	public Integer call() throws Exception {
		int num=0;//该文件该keyword出现次数
		boolean hasFlag=false;//表示当前行没有keyword
		System.out.println("当前线程启动："+Thread.currentThread().getName());
		
		File file=G_ArrayBlockingQueue.ENDFILE;
		while(true){
				file=(File)queue.take();
				if(file.equals(G_ArrayBlockingQueue.ENDFILE)){//说明文件已经搜索完毕
					queue.put(file);//如果该线程取到结束标志文件，还需要放回去以供其他线程作为完成判断标志;
//					G_ArrayBlockingQueue.addSet(localSet.get());
					printSet();
					return num;
				}
				Scanner sc=new Scanner(file);
				int lineNum=0;//行数
				
				keyWord=keyWord.toUpperCase(); //忽略大小写
				while(sc.hasNextLine()){
					hasFlag=false;
					lineNum++;
					String lineBak=sc.nextLine();
					String line=lineBak.toUpperCase();
					int index=0;//出现下标
					while((index=line.indexOf(keyWord) ) != -1){
						line=line.substring(index+1);
						num++;
						hasFlag=true;
					}
					if(hasFlag){
						this.putToSet(Thread.currentThread().getName()+"	"+file.getPath()+"	"+String.format("%03d", lineNum)+"行	"+lineBak);
					}
				}
				sc.close();
			}
	}
	
	public void  putToSet(String result){//将检索到的结果放到set中
		TreeSet<String> set =localSet.get();
		if(set==null){
			set=new TreeSet<String>();
		}
		set.add(result);
		localSet.set(set);
	}
	public void  printSet(){//将检索到的结果放到set中
		synchronized(this) {TreeSet<String> set =localSet.get(); //这里可能会降低效率
			if(set==null){
				return;
			}else{
				for(String str:set){
					System.out.println(str);
				}
			}
		}
	}
}


