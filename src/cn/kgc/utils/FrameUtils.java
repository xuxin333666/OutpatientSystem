package cn.kgc.utils;

import javax.swing.JOptionPane;


public class FrameUtils {
	
	
//	public static String[] getText(JTextField[] jTextFields) {
//		if(jTextFields == null) {
//			return null;
//		}
//		String[] datas = new String[jTextFields.length];
//		int count = 0;
//		for (JTextField jTextField : jTextFields) {
//			if(jTextField != null) {
//				datas[count++] = jTextField.getText();				
//			}
//		}
//		return datas;
//	}
//	
	
	public static void statusInfo(int status,String succuss,String erorr) {
		if(status <= 0) {
			JOptionPane.showMessageDialog(null, erorr, "´íÎó", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, succuss, "³É¹¦", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public static void DialogErorr(String msg) {
		JOptionPane.showMessageDialog(null, msg, "´íÎó", JOptionPane.ERROR_MESSAGE);
	}
}
