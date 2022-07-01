package me.wilko.fishing.util;

import java.util.Random;

public class MathUtil {

	public static boolean isInteger(String val) {
		try {
			Integer.parseInt(val);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean chance(int val) {
		return val != 0 && val <= new Random().nextInt(100) + 1;
	}
}
