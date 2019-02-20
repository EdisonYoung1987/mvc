package com.edison.testJunit.oth.ThreadAndConcurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/**仿造LinkedBlockingQueue增加一个哑结点dummy node，这样可以在dequeue和enqueue中实现读写分离<br>
 * 而AQS的哑结点也是一样的。<br>
 * 错误案例参考/HelloSpringMVC/src/test/java/com/edison/testJunit/random/WaitAndNotifyBlockQueue.java*/
public class F_WaitAndNotifyBlockQueue<E> {
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

	    private  AtomicInteger count = new AtomicInteger();

	    private final int capacity;

	    private Object readLock = new Object();

	    private Object writeLock = new Object();
	    
	    public F_WaitAndNotifyBlockQueue(){
	        this(Integer.MAX_VALUE);
	    }

	    public F_WaitAndNotifyBlockQueue(int capacity){
	        if(capacity<1){
	            throw new RuntimeException("队列容量必须大于0");
	        }
	        this.capacity = capacity;
	        head=tail=new Node<E>(null, null);
	    }

	    public E take(){
//	        final AtomicInteger count = this.count;
	        E x = null;
	        synchronized (readLock){
	            try {
	                while(count.get()==0){
	                    System.out.println("开始阻塞当前线程"+ Thread.currentThread());
	                    readLock.wait();
	                }
	                x = dequeue();
	                count.decrementAndGet();
	                System.out.println("出队消息"+x+"count="+count.get()+" "+head.next);


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
//	        final AtomicInteger count = this.count;
	        synchronized (writeLock){
	                try{
	                    while(count.get()==capacity){
	                        System.out.println("开始阻塞当前线程"+ Thread.currentThread());
	                        writeLock.wait();
	                    }
	                    enqueue(x);
	                    count.getAndIncrement();
	                    System.out.println("入队消息："+x+"count="+count.get()+" "+head.next);

	                }catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	        }
	        synchronized (readLock){
	            readLock.notify();
	        }
	    }

	    private E dequeue(){
	        Node<E> first = head.next;
	        E x=first.item; //head节点不存储数据
	        head.next = null;
	        head = first;
	        return x;
	    }

	    private void enqueue(E x){
	        Node<E> newNode = new Node<E>(x,null);
	        tail.next = newNode;//当为第一次插入数据时，此时head.next=newNode,而head是哑结点Dummy node
	        tail = newNode;
	    }

	    public static void main(String[] args) throws InterruptedException {
	        F_WaitAndNotifyBlockQueue<Integer> blockingQueue = new F_WaitAndNotifyBlockQueue<Integer>(100);
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
	            for(int i=200000;i<400000;i++){
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
