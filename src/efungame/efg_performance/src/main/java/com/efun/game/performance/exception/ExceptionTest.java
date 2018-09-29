package com.efun.game.performance.exception;

public class ExceptionTest {
	public static Exception e = new Exception();
	
	
	public static void main(String[] args) {
		final int MAX_COUNT = 50;
		for (int i=0; i<MAX_COUNT; i++) {
			test(i);
		}
	}
	
	public static void test(int index) {
		int MAX_COUNT = 1000000;
		long startTime = System.nanoTime();
		for (int i=0; i<MAX_COUNT; i++) {
			try {
				exception();
			}
			catch (Exception e) {
			}
		}
		long stopTime = System.nanoTime();
//		System.out.printf("index=%d, count = %d, elapsed time = %f ms\n", index, MAX_COUNT, (stopTime-startTime)/1000000.0);
		System.out.printf("index=%d, count = %d, elapsed time = %d ns\n", index, MAX_COUNT, (stopTime-startTime));
	}

	public static void exception() throws Exception {
//		long a=0;
//		long b=System.currentTimeMillis();
//		long c=b/a;
		throw e;
//		throw new Exception();
	}
}
