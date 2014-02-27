/**
 * @author Administrator
 *
 * Feb 27, 2014
 */
package com.jt.qianxun.util;

import org.junit.Test;

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
}
