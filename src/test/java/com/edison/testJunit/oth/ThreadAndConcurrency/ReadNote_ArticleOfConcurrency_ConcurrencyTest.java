package com.edison.testJunit.oth.ThreadAndConcurrency;

public class ReadNote_ArticleOfConcurrency_ConcurrencyTest {
	private static final long count = 100000000L;

	public static void main(String[] args) throws InterruptedException {
		concurrency();
		serial();
	}

	private static void concurrency() throws InterruptedException {
		long start = System.currentTimeMillis();
		Thread thread = new Thread(new Runnable() {
			public void run() {
				int a = 0;
				for (long i = 0; i < count; i++) {
					a += 5;
				}
			}
		});
		thread.start();
		int b = 0;
		for (long i = 0; i < count; i++) {
			b--;
		}
		long time = System.currentTimeMillis() - start;//这个时间放到join()之前不准确，因为该语句完全可能在子线程完成前就执行了

		thread.join();
		long time2 = System.currentTimeMillis() - start; //这个时候执行更准确
		System.out.println("concurrency :" + time + "ms,b=" + b);
		System.out.println("concurrency :" + time2 + "ms,b=" + b);

	}

	private	static void serial() {
		long start = System.currentTimeMillis();
		int a = 0;
		for (long i = 0; i < count; i++) {
			a += 5;
		}
		int b = 0;
		for (long i = 0; i < count; i++) {
			b--;
		}
		long time = System.currentTimeMillis() - start;
		System.out.println("serial:" + time + "ms,b=" + b + ",a=" + a);
	}
}
