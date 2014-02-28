package com.jt.qianxun.core;

import java.util.LinkedList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import com.jt.qianxun.entity.Urls;
import com.jt.qianxun.util.ConcurrentStack;
import com.jt.qianxun.util.PageUtil;

/**
 * @author Liu Ze
 *
 * Create Date Feb 28, 2014 10:52:25 AM
 * product html through url in list
 */

public class ConnHtmlProducer {
	
	String name;
	ConcurrentStack<String> stack = null;
	String spath;
	int parties;
	final CyclicBarrier barrier;
	
	public ConnHtmlProducer(ConcurrentStack<String> stack, String spath, int parties) {
		this.stack = stack;
		this.spath = spath;
		this.parties = parties;
		barrier = new CyclicBarrier(parties, new Runnable() {
			
			@Override
			public void run() {
				System.out.println("finished put in cyclic");
			}
		});
	}
	
	public void startJob() {
		for (int i = 0; i < parties; i++) {
			new Thread(new HtmlWorker(i)).start();
		}
	}
	
	public static void main(String[] args) {
		String rpath = "d:/data/page/";
		ConcurrentStack<String> stack =  new ConcurrentStack<>();
		stack.push("http://blog.fens.me/");
		stack.push("http://blog.fens.me/series-hadoop-family/");
		stack.push("http://blog.fens.me/series/");
		stack.push("http://blog.fens.me/series-mongodb/");
		stack.push("http://blog.fens.me/series-r/");
		stack.push("http://mvnrepository.com/artifact/net.jcip/jcip-annotations/1.0");
		
		ConnHtmlProducer rh1= new ConnHtmlProducer(stack, rpath, 4);
		rh1.startJob();
	}
	
	
	class HtmlWorker implements Runnable {
		
		int id;
		
		public HtmlWorker(int id) {
			this.id = id;
		}
		@Override
		public void run() {
			String url = stack.pop();
			while(url!=null) {
				downHtml(url);
				try {
					barrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
				System.out.println("continue...");
				url = stack.pop();
			}
		}
		
		public void downHtml(String url) {
			
			PageUtil.getHtml(url, spath);
			System.out.println("start download "+url);
		}

	}
}
