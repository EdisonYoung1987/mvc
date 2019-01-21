package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.util.concurrent.locks.LockSupport;

/**验证子线程在执行park()休眠后，主线程interrupt他*/
public class E_4_ParkAndInterrupt {

	public static void main(String[] args) throws InterruptedException {
		Thread parkThread = new Thread(new Runnable() {
            public void run() {
                System.out.println("park begin");

                //等待获取许可
                LockSupport.park();
                
                //输出thread over.true
                System.out.println("thread over." + Thread.currentThread().isInterrupted());

            }
        });
        parkThread.start();

        Thread.sleep(2000);
        // 唤醒线程
//        parkThread.interrupt(); //通过interrupt方式唤醒
        LockSupport.unpark(parkThread); //发放许可方式唤醒

        System.out.println("main over");

	}

}
