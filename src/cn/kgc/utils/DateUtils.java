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

}
