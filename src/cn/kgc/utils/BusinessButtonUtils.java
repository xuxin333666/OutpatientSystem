package cn.kgc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cn.kgc.frame.BaseBusinessButtonFrame;


public class BusinessButtonUtils {
	private static final String PROPERTIES_URL = "./BusinessButton.properties";
	
	public static Properties p;
	
	static {
		InputStream is = BusinessButtonUtils.class.getClassLoader().getResourceAsStream(PROPERTIES_URL);
		p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static BaseBusinessButtonFrame getBusinessButtonFrame(String Name) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String businessButtonFrameStr = p.getProperty(Name);
		BaseBusinessButtonFrame businessButtonFrame = (BaseBusinessButtonFrame) Class.forName(businessButtonFrameStr).newInstance();
		return businessButtonFrame;
	}

}
