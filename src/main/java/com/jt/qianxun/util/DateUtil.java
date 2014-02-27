package com.jt.qianxun.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static String getCurrentTimestamp() {
		return format.format(new Date(System.currentTimeMillis()));
	}
}
