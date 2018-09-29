package com.efun.game.performance.exception;

public class ErrorCodeTest {

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
			int code = errorCode();
			if (0==code) {
				
			} else {
				
			}
		}
		long stopTime = System.nanoTime();
//		System.out.printf("index=%d, count = %d, elapsed time = %f ms\n", index, MAX_COUNT, (stopTime-startTime)/1000000.0);
		System.out.printf("index=%d, count = %d, elapsed time = %d ns\n", index, MAX_COUNT, (stopTime-startTime));
	}
	
	public static int errorCode() {
//		long a=0;
//		long b=System.currentTimeMillis();
//		if (a==0) {
//			return -1;
//		}
//		long c=b/a;
		return 0;
	}
}
