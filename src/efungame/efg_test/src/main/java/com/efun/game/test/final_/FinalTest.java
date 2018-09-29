package com.efun.game.test.final_;

public class FinalTest {
	public static void main(String[] args) {
		User user = new User();
		user.setName(1000);
		user.setAge(1000);
		long start = System.currentTimeMillis();
		for (long i=0; i<10000; i++) {
			for (long j=0; j<1000000; j++) {
				user.getAge();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("not final times=" + (end - start));
		
		UserWithFinal userWithFinal = new UserWithFinal();
		userWithFinal.setName(1000);
		userWithFinal.setAge(1000);
		start = System.currentTimeMillis();
		for (long i=0; i<10000; i++) {
			for (long j=0; j<1000000; j++) {
				userWithFinal.getAge();
			}
		}
		end = System.currentTimeMillis();
		System.out.println("final times=" + (end - start));

	}
}
