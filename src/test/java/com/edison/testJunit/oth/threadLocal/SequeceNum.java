package com.edison.testJunit.oth.threadLocal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017/10/16.
 */
public class SequeceNum {
    private static ThreadLocal<Integer> t=new ThreadLocal<Integer>();
    private  int i=0;
    private Map<String,Integer> map=new ConcurrentHashMap<String,Integer>();
    public Integer getNextNum(){
        /*if(t.get()==null){
            i=1;
            t.set(i);
        }else {
            t.set(t.get()+1);
        }
        return t.get();*/
    	if(map.get("key")==null){
    		map.put("key", 1);
    		return 1;
    	}else{
    		map.put("key", map.get("key")+1);
    		return map.get("key")+1;
    	}

    }

    public static class TestClient extends Thread{
        private SequeceNum seq;
        public TestClient(SequeceNum seq){
            this.seq=seq;
        }

        public  void run(){
            for(int i=0;i<10;i++){
                System.out.println(Thread.currentThread().getName()+" : "+seq.getNextNum());
            }
        }
    }

    public static void main(String[] args){
        SequeceNum seq=new SequeceNum();
        /*TestClient t1=new TestClient(seq);
        TestClient t2=new TestClient(seq);
        TestClient t3=new TestClient(seq);
        TestClient t4=new TestClient(seq);

        t1.start();
        t2.start();
        t3.start();
        t4.start();*/
        for(int i=1;i<5;i++){
        	new TestClient(seq).start();
        }
    }
}
