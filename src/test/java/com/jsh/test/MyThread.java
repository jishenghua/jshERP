package com.jsh.test;

public class MyThread {
	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			MyRunnable  mt = new MyRunnable();    //定义Runnable子类对象
			new Thread(mt, "第" + i + "个线程").start();
		}
	}
}
