package com.edison.testJunit.random;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**在有些情况，比如通过compareAndSet(field,expect,update)的成功失败来判断某对象是否被变动过，
 * 这时，就会出现ABA的情况，如https://www.cnblogs.com/java20130722/p/3206742.html，而AtomicStampedReference
 * 通过引入版本号，每次操作compareAndSet时都把版本号+1，这样，就可以避免ABA的问题*/
public class ABA {
        private static AtomicInteger atomicInt = new AtomicInteger(100);
        private static AtomicStampedReference<Integer> atomicStampedRef = new AtomicStampedReference<Integer>(100, 0);

        public static void main(String[] args) throws InterruptedException {
               	//线程1,2通过atomicInteger的compareAndSet演示ABA的问题
        		Thread intT1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                                atomicInt.compareAndSet(100, 101);
                                atomicInt.compareAndSet(101, 100);
                        }
                });

                Thread intT2 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                                try {
                                        TimeUnit.SECONDS.sleep(1);
                                } catch (InterruptedException e) {
                                }
                                boolean c3 = atomicInt.compareAndSet(100, 101);
                                System.out.println(c3); // true
                        }
                });

                intT1.start();
                intT2.start();
                intT1.join();
                intT2.join();
                
                //这两个线程通过AtomicStampedReference演示是如何解决ABA问题的
                Thread refT1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                                try {
                                        TimeUnit.SECONDS.sleep(1);
                                	} catch (InterruptedException e) {
                                }
                                int stamp=atomicStampedRef.getStamp();
                                boolean res1= atomicStampedRef.compareAndSet(100, 101, stamp,stamp+1);
                                
                                stamp=atomicStampedRef.getStamp();
                                boolean res2= atomicStampedRef.compareAndSet(101, 100, stamp,stamp+1);
                                System.out.println("res "+res1+" "+res2);
                        }
                });

                Thread refT2 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                                int stamp = atomicStampedRef.getStamp();//这就是版本号
                                try {
                                        TimeUnit.SECONDS.sleep(2);
                                } catch (InterruptedException e) {
                                }
                                //预期值100，需替换为101，因为这里加了版本号，而该线程在sleep(2)后，
                                //先前的版本号已经被修改,所以这里会失败
                                boolean c3 = atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1);
                                System.out.println(c3); // false
                        }
                });

                refT1.start();
                refT2.start();
        }
}
