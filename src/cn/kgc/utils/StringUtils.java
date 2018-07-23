package cn.kgc.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class StringUtils {
	public static boolean isEmpty(String str) {
		if(str == null || "".equals(str)) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	public static String[] map2StringArr(Map<String, String> map) {
		if(map != null) {
			String[] strs = new String[map.size()];
			Set<String> key = map.keySet();
			Iterator<String> iterator = key.iterator();
			int index = 0;
			while(iterator.hasNext()) {
				strs[index++] = map.get(iterator.next());
			}
			return strs;
		}
		return null;
	}
}
