package com.edison.testJunit.oth.ThreadAndConcurrency;

/**
 * 这个是验证在某个线程锁住对象的某个属性时，其他线程能否获取该对象的锁，或者该对象其他属性的对象的锁；
 * 最后证明这个锁是和对象本身挂钩的，只要不是竞争同一个对象，不管这些对象锁是什么关系，都互不干涉*/
public class E_3_LockOneLockAll {
	private static 	LockObject lockObject=new LockObject("abc","def");
	
	public static void main(String[] args) {

		Thread thread1=new Thread() {//锁住lockObject.locka属性
			@Override
			public void run() {
				synchronized (lockObject.locka) {
					System.out.println("线程1锁住了locka属性，并休眠10s");
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
					}finally {
						System.out.println("线程1醒来并释放locka对象");
					}
				}
			}
		};
		
		Thread thread2=new Thread() {//锁住lockObject.lockb属性
			@Override
			public void run() {
				try {//先休眠3s，等待线程1把locka已锁住
					Thread.sleep(3000);
				} catch (InterruptedException e) {}
				
				System.out.println("线程2尝试获取lockb属性的锁");
				synchronized (lockObject.lockb) {
					System.out.println("线程2锁住了lockb属性，并休眠10s");
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
					}finally {
						System.out.println("线程2醒来并释放lockb对象");
					}
				}
			}
		};
		
		Thread thread3=new Thread() {//锁住lockObject对象
			@Override
			public void run() {
				try {//先休眠6s，等待线程1和线程2
					Thread.sleep(6000);
				} catch (InterruptedException e) {}
				
				System.out.println("线程3尝试获取lockObject对象锁");
				synchronized (lockObject) {
					System.out.println("线程3锁住了lockObject对象");
				}
				System.out.println("线程3结束");
			}
		};
		
		//启动线程
		thread1.start();
		thread2.start();
		thread3.start();
	}

}

class LockObject {
	public String locka;
	public String lockb;
	public LockObject(String locka,String lockb) {
		this.locka=locka;
		this.lockb=lockb;
	}
}