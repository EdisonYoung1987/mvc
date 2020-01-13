package com.edison.testJunit.oth.jvm;

import java.math.BigDecimal;

/**用jstack查找cpu占用多的线程*/
public class D_CpuJstack {
    public static void main(String[] args) {
        Runnable r1=new Runnable() {
            @Override
            public void run() {
                BigDecimal num=new BigDecimal("0");
                for(;;){
                    num=num.add(new BigDecimal("0.01"));
                    if(num.compareTo(new BigDecimal("100000000000"))>0)
                        num=BigDecimal.ZERO;
                }
            }
        };
        Runnable r2=new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(r1).start();
        new Thread(r2).start();

        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
