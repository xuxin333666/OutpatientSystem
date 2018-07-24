package cn.kgc.utils;

import java.lang.reflect.Field;
import java.util.List;

public class ListUtils {
	
	
	public static Object[][] list2TableArray(List<?> list,int len) throws IllegalArgumentException, IllegalAccessException {
		Object[][] datas = new Object[list.size()][len];
		Field[] fields;
		int count = 0;
		for (Object obj : list) {
			fields = obj.getClass().getDeclaredFields();
			int  index = 0;
			for (Field field : fields) {
				field.setAccessible(true);
				Object value = field.get(obj);
				if(value == null) {
					datas[count][index++] = null;
				} else {
					datas[count][index++] = value.toString();					
				}
				if(index == len) {
					break;
				}
			}
			count++;
		}
		return datas;
	}
}
