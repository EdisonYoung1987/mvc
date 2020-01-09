package com.edison.testJunit.oth.jvm;
/**逃逸分析+栈上分配<p>
 *  vm参数-打开逃逸分析+栈上分配：-server -Xmx10m -Xms10m -XX:+DoEscapeAnalysis -XX:+PrintGC
 *     运行结果：GC只执行了3次，3毫秒完成整个程序<p>
 *  vm参数-关闭逃逸分析+全堆上分配：-server -Xmx10m -Xms10m -XX:-DoEscapeAnalysis -XX:+PrintGC
 *     运行结果：GC发生了1000多次，1500多毫秒完成整个程序<p>
 *  通过-XX:+PrintGC每次GC都会打印日志，当关闭逃逸分析时，GC非常频繁*/
public class C_StackAllocate {
    public static void alloc() {
        byte[] b = new byte[2];
        b[0] = 1;   //这个b对象就是分配到栈上面的
    }

    public static void main(String[] args) throws InterruptedException {
        long b = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            alloc();
        }
        long e = System.currentTimeMillis();
        System.out.println(e - b);
        Thread.sleep(100000);
    }
}
