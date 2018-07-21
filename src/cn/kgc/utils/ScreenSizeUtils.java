package cn.kgc.utils;

public class ScreenSizeUtils {
    public static int screenWidth = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    public static int screenHeight = (int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

    
    public static int HorizontalCenterPosition(int width) {
    	return screenWidth/2 - width/2;
    }
    
    public static int VerticalCenter(int height) {
    	return screenHeight/2 - height/2;
    }
}
