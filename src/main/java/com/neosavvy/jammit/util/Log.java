package com.neosavvy.jammit.util;

public class Log {

	public static void info(String s, Object... args) {
		if (args != null) {
			System.out.println(String.format(s, args));
		} else {
			System.out.println(s);
		}
	}

}
