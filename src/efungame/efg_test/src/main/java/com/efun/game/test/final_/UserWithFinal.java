package com.efun.game.test.final_;

public class UserWithFinal {
	private int name;
	private int age;

	public final int getName() {
		return name;
	}

	public final void setName(int name) {
		this.name = name;
	}

	public final int getAge() {
		return age;
	}

	public final void setAge(int age) {
		this.age = age;
	}

}
