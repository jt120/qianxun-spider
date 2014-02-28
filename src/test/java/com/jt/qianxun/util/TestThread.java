package com.jt.qianxun.util;

import org.junit.Test;

/**
 * @author Liu Ze
 *
 * Create Date Feb 28, 2014 12:03:16 PM
 */

public class TestThread {

	@Test
	public void test01() {
		
		
		MyThread1 mt = new MyThread1();
		mt.start();
		for (int i = 0; i < 1000; i++) {
			System.out.println("main: "+i);
		}
	}
}

class MyThread1 extends Thread {

	@Override
	public void run() {
		for (int i = 0; i < 1000; i++) {
			System.out.println("sub: "+i);
		}
	}
	
}
