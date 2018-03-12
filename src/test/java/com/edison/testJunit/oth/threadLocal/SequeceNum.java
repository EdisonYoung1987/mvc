package com.edison.testJunit.oth.threadLocal;

/**
 * Created by Administrator on 2017/10/16.
 */
public class SequeceNum {
    private static ThreadLocal<Integer> t=new ThreadLocal<Integer>();
    private  int i=0;
    public Integer getNextNum(){
        if(t.get()==null){
            i=1;
            t.set(i);
        }else {
            t.set(t.get()+1);
        }
        return t.get();
    }

    public static class TestClient extends Thread{
        private SequeceNum seq;
        public TestClient(SequeceNum seq){
            this.seq=seq;
        }

        public  void run(){
            for(int i=0;i<5;i++){
                System.out.println(Thread.currentThread().getName()+" : "+seq.getNextNum());
            }
        }
    }

    public static void main(String[] args){
        SequeceNum seq=new SequeceNum();
        TestClient t1=new TestClient(seq);
        TestClient t2=new TestClient(seq);
        TestClient t3=new TestClient(seq);
        TestClient t4=new TestClient(seq);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
