package com.chrisp6825.naim.inputManagers;

public class OverWorldKeys {
	
	private static boolean[] keys;
	private static boolean[] pkeys;
	
	public static final int numKeys = 9;
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int M = 4;
	public static final int ESCAPE = 5;
	public static final int ENTER = 6;
	public static final int A = 7;
	public static final int B = 8;
	
	static {
		keys = new boolean[numKeys];
		pkeys = new boolean[numKeys];
	}
	
	public static void update() {
		for(int i = 0; i < numKeys; i++) {
			pkeys[i] = keys[i];
		}
	}
	
	public static void setKey(int k, boolean b) {
		keys[k] = b;
	}
	
	public static boolean isDown(int k) {
		return keys[k];
	}

	public static boolean isPressed(int k) {
		return keys[k] && !pkeys[k];
	}

}
