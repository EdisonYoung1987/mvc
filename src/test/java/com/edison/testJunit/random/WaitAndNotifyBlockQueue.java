package com.edison.testJunit.random;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class WaitAndNotifyBlockQueue<E> {
	 private static class Node<E>{
	        E item;
	        Node<E> next;
	        public Node(E item,Node<E> next){
	            this.item = item;
	            this.next = next;
	        }
	    }

	    private volatile Node<E> head;

	    private volatile Node<E> tail;

	    private AtomicInteger count = new AtomicInteger();

	    private final int capacity;

	    private Object readLock = new Object();

	    private Object writeLock = new Object();
	    public WaitAndNotifyBlockQueue(){
	        this.capacity = Integer.MAX_VALUE;
	    }

	    public WaitAndNotifyBlockQueue(int capacity){
	        if(capacity<1){
	            throw new RuntimeException("队列容量必须大于0");
	        }
	        this.capacity = capacity;
	    }

	    public E take(){
	        final AtomicInteger count = this.count;
	        E x = null;
	        synchronized (readLock){
	            try {
	                while(count.get()==0){
	                    System.out.println("开始阻塞当前线程"+ Thread.currentThread());
	                    readLock.wait();
	                }
	                x = dequeue();
	                System.out.println("出队消息"+x+"count="+count.get());
	                count.decrementAndGet();

	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	        synchronized (writeLock){
	            writeLock.notify();
	        }
	        return x;
	    }

	    public void put(E x){
	        final AtomicInteger count = this.count;
	        synchronized (writeLock){
	                try{
	                    while(count.get()==capacity){
	                        System.out.println("开始阻塞当前线程"+ Thread.currentThread());
	                        writeLock.wait();
	                    }
	                    System.out.println("入队消息："+x+"count="+count.get());
	                    enqueue(x);
	                    count.getAndIncrement();
	                }catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	        }
	        synchronized (readLock){
	            readLock.notify();
	        }
	    }

	    private E dequeue(){
	    	if(head==null) {
	    		System.err.println("【"+count.get()+"】");
	    	}
	    	E x = head.item;
	        Node<E> first = head.next;
	        head.next = null;
	        head = first;
	        return x;
	    }

	    private void enqueue(E x){
	        Node<E> newNode = new Node<E>(x,null);
	        if(head==null){
	            head = newNode;
	            tail = newNode;
	        }else{
	            tail.next = newNode;
	            tail = newNode;
	        }
	    }

	    public static void main(String[] args) throws InterruptedException {
	        WaitAndNotifyBlockQueue<Integer> blockingQueue = new WaitAndNotifyBlockQueue<Integer>(100);
	        Thread producer1 = new Thread(()->{
	            for(int i=0;i<100000;i++){
	                blockingQueue.put(i);
	            }
	        });
	        Thread consumer1 = new Thread(()->{
	            for(;;){
	                blockingQueue.take();
	            }
	        });
	        Thread consumer2 = new Thread(()->{
	            for(;;){
	                blockingQueue.take();
	            }
	        });
	        Thread producer2 = new Thread(()->{
	            for(int i=100;i<200000;i++){
	                blockingQueue.put(i);
	            }
	        });
	        producer2.setName("producer2");
	        consumer1.setName("consumer1");
	        producer1.setName("producer1");
	        consumer2.setName("consumer2");
	        producer1.start();
	        producer2.start();
	        TimeUnit.MILLISECONDS.sleep(50);
	        consumer2.start();
	        consumer1.start();


	    }

	}
