package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 根据文件内容检索文件的工具.<p>
 * 输入路径名和查找内容即可检索到包含该内容的文件*/
public class G_ArrayBlockingQueue {
	//检索路径和关键字
	private static String path,keyWord;
	private static ArrayBlockingQueue<File> queue=new ArrayBlockingQueue<File>(1024);
	//空文件，用来作为线程查找blockingQueue终点
	public static final File ENDFILE=new File("");
	public static Lock lock=new ReentrantLock();
	public static TreeSet<String> resSet=new TreeSet<String>();
	
	public static void addSet(TreeSet<String> set){
		if(set==null){
			return;
		}
		while(true){
			if(lock.tryLock()){
				resSet.addAll(set);
				lock.unlock();
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		
		//获取检索路径和关键字
		System.out.println("输入检索绝对路径名(如/user/edison/):");
		G_ArrayBlockingQueue.path=scan.nextLine();
		System.out.println("输入检索关键字");
		G_ArrayBlockingQueue.keyWord=scan.nextLine();
		scan.close();
		
		File searchFile=new File(path);
		if(!searchFile.exists()){
			System.out.println("该文件或路径不存在！");
			return;
		}
		
		//分配一个线程用于递归方式读取路径下面所有的文件
		EnumerateFile enumerateFile=new EnumerateFile(queue,searchFile);
		new Thread(enumerateFile).start();//这里不需要等待该线程将所有File列表加入到queue中，因为是阻塞
										  //队列，可以边放边取直到取到空File
		
		//不需等待，边放边取
		SearchContent searchContent=new SearchContent(queue,keyWord);
		for(int i=0;i<1024;i++){
			Thread thread=new Thread(searchContent);
			thread.start();
			try{
				thread.join();
			}catch(Exception e){
				System.out.println("等待线程"+thread.getId()+"失败！");
			}
		}
		
		//检索结束后打印查找结果
		for(String res:resSet){
			System.out.println(res);
		}
	}
	
}

class EnumerateFile implements Runnable{//分配一个线程用于递归方式读取路径下面所有的文件
	BlockingQueue queue;
	File startPath;
	
	public EnumerateFile(BlockingQueue queue,File startPath){
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
	
	public void enumerateFile(BlockingQueue queue,File startFile) throws InterruptedException{
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

class SearchContent implements Runnable{
	private BlockingQueue queue;
	private String keyWord;
	ThreadLocal<TreeSet<String>> localSet=new ThreadLocal<TreeSet<String>>();
	
	public  SearchContent(BlockingQueue queue,String keyWord){
		this.queue=queue;
		this.keyWord=keyWord;
	}
	
	public void run() {
		File file=G_ArrayBlockingQueue.ENDFILE;
		while(true){
			try{
				file=(File)queue.take();
				if(file.equals(G_ArrayBlockingQueue.ENDFILE)){
					queue.put(file);//如果该线程取到结束标志文件，还需要放回去以供其他线程作为完成判断标志;
					G_ArrayBlockingQueue.addSet(localSet.get());
					return;
				}
				Scanner sc=new Scanner(file);
				int lineNum=0;//行数
				
				keyWord=keyWord.toUpperCase(); //忽略大小写
				while(sc.hasNextLine()){
					lineNum++;
					String lineBak=sc.nextLine();
					String line=lineBak.toUpperCase();
					int index=0;//出现下标
					int num=0;//出现次数
					while((index=line.indexOf(keyWord) ) != -1){
						line=line.substring(index+1);
						num++;
					}
					if(num>0){
						this.putToSet(num+" 次"+file.getPath()+":"+lineNum+"行:"+line);
					}
				}
				sc.close();
			}catch(InterruptedException e){
				System.out.println("检索被打断，结果不一定完整");
			}catch(FileNotFoundException e){
				System.out.println("File not exists:"+file.getPath());
			}
		}
	}
	
	public void  putToSet(String result){//将检索到的结果放到set中
		TreeSet<String> set =localSet.get();
		if(set==null){
			set=new TreeSet();
		}
		set.add(result);
		localSet.set(set);
	}
	public void  printSet(){//将检索到的结果放到set中
		TreeSet<String> set =localSet.get();
		if(set==null){
			return;
		}else{
			for(String str:set){
				System.out.println(str);
			}
		}
		
	}
}