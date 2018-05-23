package com.edison.testJunit.oth.streamAndFile;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**给文件分段加锁：同一JVM进程中，多个线程如果对同一个文件加锁，如果锁冲突，比如都是整个文件加锁或者
 * 分段加锁有重叠部分，则会抛异常，否则可视为该进程加了一个分段锁后又扩展了锁范围，不会抛异常*/
public class H_Nio_FileLock {
	private static final String FILE="D:\\工作\\其他\\buff.txt";
	public static void main(String[] args) {
		LockFile lockFile=new LockFile(Paths.get(FILE), 0, 5);
		LockFile lockFile2=new LockFile(Paths.get(FILE), 6, 8);//如果lockfile2的锁段为3-8，则会抛异常OverlappingFileLockException
		
		Thread thread1=new Thread(lockFile);
		Thread thread2=new Thread(lockFile2);
		thread1.start();
		thread2.start();
		
		try{
			Thread.sleep(6000);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
class LockFile implements Runnable{
	private int start;
	private int end;
	private Path path;
	
	public LockFile(Path path,int start,int end){
		this.path=path;
		this.start=start;
		this.end=end;
	}

	public void run() {
		try{
			System.out.println(Thread.currentThread().getId()+" run():");
			FileChannel filechannel=FileChannel.open(path,  StandardOpenOption.WRITE,StandardOpenOption.READ);
			FileLock filelock=filechannel.lock(start, end, false);
			System.out.println(Thread.currentThread().getId()+" lock():");
			Thread.sleep(5000);
			filelock.release();
			System.out.println(Thread.currentThread().getId()+" release():");
			filechannel.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
