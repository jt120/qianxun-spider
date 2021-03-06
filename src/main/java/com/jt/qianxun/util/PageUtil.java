/**
 * @author Administrator
 *
 * Feb 27, 2014
 */
package com.jt.qianxun.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import com.jt.qianxun.entity.Page;

public class PageUtil {
	
	private static String encoding = "UTF-8";
	private static String type = "";
	private static Page page = new Page();
	private static Random random = new Random();
	
	/**
	 * 
	 * @param url: 抓取的url地址
	 * @param spath：存放抓取文件的位置，文件名为当前时间yyyyMMddHHmmss
	 * @return
	 */
	public static String getHtml(String url, String spath) {
		StringBuilder path = new StringBuilder(spath);
		HttpURLConnection conn = null;
		InputStream is = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		String fileName = "";
		try {
			URL u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			String en = conn.getContentEncoding();
			if(en!=null) {
				encoding = en;
			}
			type = conn.getContentType();
			page.setType(type);
			page.setUrl(url);
//			System.out.println("encoding: "+encoding);
//			System.out.println("type: "+type);
			
			is = (InputStream) conn.getContent();
			br = new BufferedReader(new InputStreamReader(is));
			String line = "";
			fileName = path.append(DateUtil.getCurrentTimestamp()).append("_").append(random.nextInt(100)).toString();
//			System.out.println("the new filename is " + fileName);
			File file = new File(fileName);
			if(!file.exists()) {
				File f = new File(file.getParent());
				f.mkdirs();
				file.createNewFile();
			}
			pw = new PrintWriter(file);
			while((line=br.readLine())!=null) {
				pw.write(line+"\n");
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
			try {
				if(is!=null) is.close();
				if(br!=null) br.close();
				if(pw!=null) pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileName;
	}
	
	/**
	 * create page object
	 * @param path
	 */
	public void createPage(String path) {
		File file = new File(path);
		StringBuilder sb = new StringBuilder();
		String line = "";
		FileInputStream fis = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(fis));
			while((line=br.readLine())!=null) {
				sb.append(line);
			}
			page.setRaw(sb.toString());
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fis!=null) fis.close();
				if(br!=null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Page parsePage(Page page) {
		String raw = page.getRaw();
		
		page.setTitle(HtmlPaser.getTitle(raw)) ;
		
		List<String> urls = HtmlPaser.paserUrls(raw,page.getUrl());
		page.setUrls(urls);
		
		page.setContent(HtmlPaser.getExtractedText(raw));
		
		return page;
	}
	
	/**
	 * write page to a file
	 * @param path
	 */
	public void writePage(String path) {
		try {
			String fileName = path + DateUtil.getCurrentTimestamp();
			File file = createFile(fileName);
			IOUtil.write(file, page.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * create file if there is no parent folder
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private File createFile(String fileName) throws IOException {
		File file = new File(fileName);
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			file.createNewFile();
		}
		return file;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	
}
