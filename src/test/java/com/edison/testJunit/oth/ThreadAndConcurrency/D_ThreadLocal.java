package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.util.Random;

/**
 * ThreadLocal 线程的本地私有变量，不需要考虑什么同步，线程安全什么的*/
public class D_ThreadLocal extends Thread{
	//最好重写initialValue方法，默认是返回null
	private static ThreadLocal<Integer> SeqNum=new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return 0;
		}};
		
	public D_ThreadLocal(String name) {//构造方法
		super(name);
	}
	
	public void addSeqByOne() { //完全不需要同步锁
		Integer seq=SeqNum.get();
		seq++;
		SeqNum.set(seq);
	}
	
	public Integer getSeqNum() {
		return SeqNum.get();
	}
	
	@Override
	public void run() {
		int num=new Random(Thread.currentThread().getId()).nextInt(100000);
		for(int i=0;i<num;i++) {
			//System.out.println("当前线程："+Thread.currentThread().getName());
			addSeqByOne();
			yield(); //尽量让出cpu时间，这样保证几个线程交叉执行+1
		}
		//注意main方法里面的错误示例，这里就是获取的本身线程的最终计算结果
		System.out.println(Thread.currentThread().getName()+"预期结果是="+num+" 最终的seqnum="+getSeqNum());
		
		//为避免内存泄漏，一定要执行remove清理key和value
		SeqNum.remove();
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		D_ThreadLocal t1=new D_ThreadLocal("线程1");
		D_ThreadLocal t2=new D_ThreadLocal("线程2");
		D_ThreadLocal t3=new D_ThreadLocal("线程3");
		D_ThreadLocal t4=new D_ThreadLocal("线程4");
		t1.start();
		t2.start();
		t3.start();
		t4.start();

		Thread.sleep(2000); //等待子线程结束
		
		//这个是错误的，不管是哪个对象去执行getSeqNum()，最终都是根据当前的线程去获取对应的SeqNum
		//所以这里取到的实际上是main线程的SeqNum,跟子线程是否调用remove清理无关
		System.out.println("错误示例： "+t1.getName()+" 最终的seqnum="+t1.getSeqNum());

	}

}
