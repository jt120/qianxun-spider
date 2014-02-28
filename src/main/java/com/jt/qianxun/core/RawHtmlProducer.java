package com.jt.qianxun.core;

import java.util.LinkedList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import com.jt.qianxun.entity.Urls;
import com.jt.qianxun.util.PageUtil;

/**
 * @author Liu Ze
 *
 * Create Date Feb 28, 2014 10:52:25 AM
 * product html through url in list
 */

public class RawHtmlProducer {
	
	String name;
	Urls urls = null;
	String spath;
	int parties;
	final CyclicBarrier barrier;
	
	public RawHtmlProducer(Urls urls, String spath, int parties) {
		this.urls = urls;
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
		Urls urls = new Urls();
		LinkedList<String> list =  urls.getList();
		list.add("http://blog.fens.me/");
		list.add("http://blog.fens.me/series-hadoop-family/");
		list.add("http://blog.fens.me/series/");
		list.add("http://blog.fens.me/series-mongodb/");
		list.add("http://blog.fens.me/series-r/");
		int p = list.size();
		
		RawHtmlProducer rh1= new RawHtmlProducer(urls, rpath, p);
		rh1.startJob();
	}
	
	
	class HtmlWorker implements Runnable {
		
		int id;
		
		public HtmlWorker(int id) {
			this.id = id;
		}
		@Override
		public void run() {
			while(!finished()) {
				downHtml();
				try {
					barrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
				System.out.println("continue...");
			}
		}
		
		public void downHtml() {
			String url = urls.pop();
			PageUtil.getHtml(url, spath);
			System.out.println("start download "+url);
		}
		
		public boolean finished() {
			int len = urls.getSize();
			System.out.println("len: "+len);
			return len==0?true:false;
		}

	}
}
