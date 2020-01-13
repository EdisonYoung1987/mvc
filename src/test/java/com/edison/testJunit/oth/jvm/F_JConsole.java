package com.edison.testJunit.oth.jvm;

import com.edison.testJunit.random.TestJVM;

import java.util.ArrayList;
import java.util.List;

public class F_JConsole {
        private static List<TestJVM> list=new ArrayList<TestJVM>();

        private String buffer; //1M
        public F_JConsole() {
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<1024*1024;i++) {
                sb.append("a");
            }
            buffer=sb.toString();

        }
        public static void main(String[] args) throws InterruptedException {

            for(int i=0;i<1024*2;i++) {//2G
                list.add(new TestJVM());
                if(i%50==0) {
                    System.out.println("已使用内存(M)"+i);
                }
                Thread.sleep(100);
            }
        }
}
