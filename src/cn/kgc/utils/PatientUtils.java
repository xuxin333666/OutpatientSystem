package cn.kgc.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.kgc.model.Patient;

public class PatientUtils {
	private static final int PATIENT_ATTRIBUTE_COUNT = 13;
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
		Object[][] datas = new Object[patients.size()][PATIENT_ATTRIBUTE_COUNT];
		int count = 0;
		for (Patient patient : patients) {
			int  index = 0;
			datas[count][index++] = patient.getId();
			datas[count][index++] = patient.getName();
			datas[count][index++] = status2Str(patient.getSex(),sexRule);
			datas[count][index++] = patient.getAge();
			datas[count][index++] = status2Str(patient.getMarried(),marriedRule);
			datas[count][index++] = status2Str(patient.getJob(),jobRule);
			datas[count][index++] = patient.getWeight();
			datas[count][index++] = status2Str(patient.getBlood(),bloodRule);
			datas[count][index++] = patient.getPhoneNumber();
			datas[count][index++] = patient.getRegisterTime();
			datas[count][index++] = patient.getAddress();
			datas[count][index++] = patient.getAllergy();
			datas[count++][index++] = patient.getRemark();
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
			if(rule.get(string).equals(str)) {
				return string;
			}
		}
		return STATUS_ERORR;
	}

}
