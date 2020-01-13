package com.edison.testJunit.oth.jvm;

import java.util.ArrayList;
import java.util.List;

/**不停new对象并分配1M大小对象,同时把所有对象add到一个全局静态的list中,用jmap打印
 * 内存日志并用MAT工具进行分析
 * -XX:+HeapDumpOnOutOfMemoryError
 * -XX:HeapDumpPath=d:/temp*/
public class E_Jmap {
    private static List<Object> list=new ArrayList<>(1024);

    public static void main(String[] args) {
        for(int i=0;i<1024;i++) {
            System.out.println("i="+i);
            byte[] obj = new byte[8 * 1024 * 1024];
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(obj);
        }
    }
}
