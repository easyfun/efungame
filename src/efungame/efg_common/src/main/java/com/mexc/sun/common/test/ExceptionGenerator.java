package com.mexc.sun.common.test;

public class ExceptionGenerator {
	public static void createException() {
		int a = 100;
		int b = 0;
		b = a / b;
	}
}
