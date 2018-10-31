package com.edison.testJunit.oth.netty.bio;

import java.util.Random;

public class Test {

	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			public void run() {
				try{
					Server.start();
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}).start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		new Thread(new Runnable() {
			public void run() {
				char[] op={'+','-','*','/'};
				Random random=new Random(System.currentTimeMillis());
				int i=0;
				while (i<10) {
					String expression=random.nextInt(10000000)+""+op[random.nextInt(4)]+""+random.nextInt(10000);
					Client.send(expression);
					i++;
				}
				
			}
		}).start();
	}

}
