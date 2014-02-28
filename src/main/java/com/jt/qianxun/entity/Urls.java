package com.jt.qianxun.entity;

import java.util.LinkedList;

/**
 * @author Liu Ze
 *
 * Create Date Feb 28, 2014 11:21:06 AM
 */

public class Urls {
	
	private final LinkedList<String> list;
	
	public Urls() {
		list = new LinkedList<>();
	}
	
	public synchronized void push(String url) {
		list.push(url);
	}
	
	public synchronized String pop() {
		return list.pop();
	}

	public LinkedList<String> getList() {
		return list;
	}
	
	public synchronized int getSize() {
		return list.size();
	}

	
}
