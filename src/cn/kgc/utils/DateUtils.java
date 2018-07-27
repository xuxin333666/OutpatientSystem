package cn.kgc.utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {

	public static Date String2SqlDate(String dateStr) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = new Date(dateFormat.parse(dateStr).getTime());
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;		
	}
	
	
	public static Date date2SqlDate(java.util.Date date) {
		Date sqlDate = new Date(date.getTime());
		return sqlDate;	
	}
	
	public static java.util.Date String2Date(String dateStr) {
		if(StringUtils.isEmpty(dateStr)) {
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		try {
			date = dateFormat.parse(dateStr);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;		
	}
	
	
	public static String age2Date(Double age) {
		java.util.Date nowTime = new java.util.Date();
		long time = nowTime.getTime() - (long)(age*365*24*60*60*1000);
		java.util.Date bornTime = new java.util.Date(time);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(bornTime);
	}


	public static String calculateAgeByStr(String birthday) {
		if(StringUtils.isEmpty(birthday)) {
			return null;
		}
		java.util.Date nowDate = new java.util.Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date birthDate =dateFormat.parse(birthday);
			long time = nowDate.getTime() - birthDate.getTime();
			return (time/1000/60/60/24/365) + "";
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	public static String date2String(java.util.Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

}
