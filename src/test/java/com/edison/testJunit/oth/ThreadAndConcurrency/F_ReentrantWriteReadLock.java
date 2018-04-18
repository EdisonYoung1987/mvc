package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.util.HashMap;
import java.util.Map;

/**读写锁ReentrantWriteReadLock的一个模拟实现*/
public class F_ReentrantWriteReadLock {
	private Map<Thread, Integer> readingThreads =new HashMap<Thread, Integer>();
	private Thread writingThread = null; 
	private int writeAccesses    = 0; //写请求，如果有该请求，则屏蔽读请求
	private int writeRequests    = 0; //写线程数，写的时候锁+1，写完-1

	public synchronized void lockRead() throws InterruptedException{
		Thread callingThread = Thread.currentThread();
		while(! canGrantReadAccess(callingThread)){
			wait();
		}

		readingThreads.put(callingThread,(getReadAccessCount(callingThread) + 1));
	}

	/**检查是否可以分配读锁*/
	private boolean canGrantReadAccess(Thread callingThread){
		if(isWriter(callingThread)) return true; //拥有写锁的线程可以加读锁
		if(hasWriter()) return false; //已经存在其他线程在写的不能读
		if(isReader(callingThread)) return true;//重入自己本身的读锁--递归调用？或者多个方法都在读？
		if(hasWriteRequests()) return false;//存在写请求时，不能再读，否则可能一直不能写，写优先于读
		return true;
	}

	/**释放读锁*/
	public synchronized void unlockRead(){
		Thread callingThread = Thread.currentThread();
		if(!isReader(callingThread)){
			throw new IllegalMonitorStateException(
				"Calling Thread does not" +" hold a read lock on this ReadWriteLock");
		}
		int accessCount = getReadAccessCount(callingThread);
		if(accessCount == 1){ 
			readingThreads.remove(callingThread); 
		} else { 
			readingThreads.put(callingThread, (accessCount -1));
		}
		notifyAll();//通知其他所有正在等待读/写锁的线程
	}

	public synchronized void lockWrite() throws InterruptedException{
		writeRequests++;
		Thread callingThread = Thread.currentThread();
		while(!canGrantWriteAccess(callingThread)){
			wait();
		}
		writeRequests--;
		writeAccesses++;
		writingThread = callingThread;
	}

	public synchronized void unlockWrite() throws InterruptedException{
		if(!isWriter(Thread.currentThread())){
			throw new IllegalMonitorStateException(
				"Calling Thread does not hold the write lock on this ReadWriteLock");
		}
		writeAccesses--;
		if(writeAccesses == 0){
			writingThread = null;
		}
		notifyAll();//通知其他所有正在等待读/写锁的线程
	}

	private boolean canGrantWriteAccess(Thread callingThread){
		if(isOnlyReader(callingThread)) return true;
		if(hasReaders()) return false;
		if(writingThread == null) return true;
		if(!isWriter(callingThread)) return false;
		return true;
	}


	private int getReadAccessCount(Thread callingThread){
		Integer accessCount = readingThreads.get(callingThread);
		if(accessCount == null) return 0;
		return accessCount.intValue();
	}


	private boolean hasReaders(){
		return readingThreads.size() > 0;
	}

	private boolean isReader(Thread callingThread){
		return readingThreads.get(callingThread) != null;
	}

	private boolean isOnlyReader(Thread callingThread){
		return readingThreads.size() == 1 &&
			readingThreads.get(callingThread) != null;
	}

	private boolean hasWriter(){
		return writingThread != null;
	}

	private boolean isWriter(Thread callingThread){
		return writingThread == callingThread;
	}

	private boolean hasWriteRequests(){
		return this.writeRequests > 0;
	}
}
