/**
 * @author Administrator
 *
 * Feb 27, 2014
 */
package com.jt.qianxun.util;

import java.util.LinkedList;

import org.junit.Test;

import com.jt.qianxun.core.RawHtmlProducer;
import com.jt.qianxun.entity.Urls;

/**
 * 从这里开始
 * @author Administrator
 *
 */
public class PageUtilTest {
	
	private static String url = "http://blog.fens.me/";
	private static String path = "d:/data/page/parse/";
	private static String rpath = "d:/data/page/";
	
	@Test
	public void test01() {
		PageUtil p = new PageUtil();
		String f = p.getHtml(url,rpath);
		p.createPage(f);
		p.parsePage(p.getPage());
		p.writePage(path);
	}
	
	@Test
	public void test02() throws InterruptedException {
		Urls urls = new Urls();
		
		LinkedList<String> list =  urls.getList();
		list.add("http://blog.fens.me/");
		list.add("http://blog.fens.me/series-hadoop-family/");
		list.add("http://blog.fens.me/series/");
		list.add("http://blog.fens.me/series-mongodb/");
		list.add("http://blog.fens.me/series-r/");
		int p = 3;
		RawHtmlProducer rh1= new RawHtmlProducer(urls, rpath, 3);
		rh1.startJob();
		
	}
}
