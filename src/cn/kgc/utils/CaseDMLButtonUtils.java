package cn.kgc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import cn.kgc.frame.intf.BusinessButtonFrameIntf;


public class CaseDMLButtonUtils {
	private static final String PROPERTIES_URL = "./CaseDMLButton.properties";
	
	public static Properties p;
	
	static {
		InputStream is = RegistDMLButtonUtils.class.getClassLoader().getResourceAsStream(PROPERTIES_URL);
		p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
//	public static BusinessButtonFrameIntf getBusinessButtonFrame(String imgUrl) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
//		String businessButtonFrameStr = p.getProperty(imgUrl);
//		BusinessButtonFrameIntf businessButtonFrame = (BusinessButtonFrameIntf) Class.forName(businessButtonFrameStr).newInstance();
//		return businessButtonFrame;
//	}
	
	public static List<String> getList() {
		Set<Object> imgUrlSet = p.keySet();
		List<String> imgUrlList = new ArrayList<>();
		for (Object object : imgUrlSet) {
			String imgUrl = object.toString();
			imgUrlList.add(imgUrl);
		}
		Collections.sort(imgUrlList);
		return imgUrlList;
	} 
}
