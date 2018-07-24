package cn.kgc.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.kgc.model.Patient;

public class PatientUtils {
	public static final int PATIENT_ATTRIBUTE_COUNT = 14;
	private static final String STATUS_ERORR = "未知";
	public static HashMap<String, String> sexRule;
	public static HashMap<String, String> marriedRule;
	public static HashMap<String, String> jobRule;
	public static HashMap<String, String> bloodRule;
	
	
	static {
		sexRule = new HashMap<>();
		sexRule.put("00", "男");
		sexRule.put("01", "女");
		
		marriedRule = new HashMap<>();
		marriedRule.put("00", "已婚");
		marriedRule.put("01", "未婚");
		
		jobRule = new HashMap<>();
		jobRule.put("00", "工人");
		jobRule.put("01", "农民");
		jobRule.put("02", "IT 民工");
		jobRule.put("03", "化妆师");
		
		bloodRule = new HashMap<>();
		bloodRule.put("00", "A型");
		bloodRule.put("01", "B型");
		bloodRule.put("02", "O型");
		bloodRule.put("03", "AB型");
	}

	public static Object[][] list2Array(List<Patient> patients) {
		Object[][] datas = new Object[0][PATIENT_ATTRIBUTE_COUNT];
		try {
			datas = ListUtils.list2TableArray(patients,PATIENT_ATTRIBUTE_COUNT);
			for (int i = 0; i < datas.length; i++) {
				Object[] data = datas[i];
				Patient patient = patients.get(i);
				data[2] = status2Str(patient.getSex(),sexRule);
				data[4] = status2Str(patient.getMarried(),marriedRule);
				data[5] = status2Str(patient.getJob(),jobRule);
				data[7] = status2Str(patient.getBlood(),bloodRule);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return datas;
	}
	
	
	public static String status2Str(String status,Map<String, String> rule) {
		if(rule.containsKey(status)) {
			return rule.get(status);
		}
		return STATUS_ERORR;
	}
	
	public static String Str2status(String str,Map<String, String> rule) {
		Set<String> set = rule.keySet();
		for (String string : set) {
			if(rule.get(string).indexOf(str) != -1) {
				return string;
			}
		}
		return STATUS_ERORR;
	}

}
