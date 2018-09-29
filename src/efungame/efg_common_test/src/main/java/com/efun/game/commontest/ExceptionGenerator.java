package com.efun.game.commontest;

public class ExceptionGenerator {
	public static void createException() {
		int a = 100;
		int b = 0;
		b = a / b;
	}
}
