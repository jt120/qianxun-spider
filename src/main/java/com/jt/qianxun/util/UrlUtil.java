/**
 * @author Administrator
 *
 * Feb 27, 2014
 */
package com.jt.qianxun.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class UrlUtil {
	
	private List<String> urls = new ArrayList<>();
	private int lines = 0;
	
	public int inject(String path) {
		File file = new File(path);
		FileInputStream fis = null;
		InputStreamReader isr = null;
		LineNumberReader reader = null;
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			reader = new LineNumberReader(isr);
			String url = "";
			while((url=reader.readLine())!=null) {
				urls.add(url);
				lines++;
			}
			System.out.println("total read: "+lines);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(fis!=null) fis.close();
				if(isr!=null) isr.close();
				if(reader!=null) reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lines;
	}
	
	public void printUrls(List<String> urls) {
		for(String url:urls) {
			System.out.println("url: "+url);
		}
	}
	
	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	@Test
	public void test01() {
		UrlUtil u = new UrlUtil();
		String path = "d:/data/url.txt";
		u.inject(path );
		u.printUrls(urls);
	}

}
