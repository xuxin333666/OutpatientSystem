package cn.kgc.frame.listener;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import cn.kgc.frame.ConsultFrame;
import cn.kgc.utils.DateUtils;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.StringUtils;

public abstract class BaseDMLButtonListener {
	protected static final int COMMAND_ADD = 1;
	protected static final int COMMAND_MODIFY = 2;
	protected static final int COMMAND_DELETE = 3;
	protected static final int COMMAND_SAVE = 4;
	protected static final int COMMAND_UNDO = 5;
	protected static final int DEFAULT_INDEX = 0;
	protected static final String OTHER_TO_DO = "暂未开发该功能";
	protected static final String DEFAULT_CONTENT = "";
	
	
	public Properties p;	
	
	public BaseDMLButtonListener( String PROPERTIES_URL) {
		InputStream is = BaseDMLButtonListener.class.getClassLoader().getResourceAsStream(PROPERTIES_URL);
		p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void execute(JButton button) {
		try {
			String methodName = getMethodName(button.getName());
			Method method = BaseDMLButtonListener.class.getMethod(methodName,JButton.class);
			method.invoke(this,button);
		} catch (Exception e1) {
			e1.printStackTrace();
			FrameUtils.DialogErorr("错误，" + e1.getMessage());
		}
	}
	
	public abstract void add(JButton button);
	
	public void emptyFields(List<JComponent> fields) {
		for (int i = 0; i < fields.size(); i++) {
			if(fields.get(i) instanceof JTextField) {
				JTextField field = (JTextField)fields.get(i);
				field.setText(DEFAULT_CONTENT);
			} else if(fields.get(i) instanceof JTextArea) {
				JTextArea area = (JTextArea)fields.get(i);
				area.setText(DEFAULT_CONTENT);
			} else {
				JComboBox<?> combo = (JComboBox<?>)fields.get(i);
				combo.setSelectedIndex(DEFAULT_INDEX);
			}
		}
	}

	public abstract void modify(JButton button);
	
	public abstract void delete(JButton button);
	
	public abstract void save(JButton button);
	
	public abstract void undo(JButton button);
	
	public void otherToDo(JButton button) {
		FrameUtils.DialogErorr(OTHER_TO_DO);
	}
	
	public Object fieldsValue2Patient(List<JComponent> fields,Class<?> clazz,int attributeStart) throws Exception {
		Object object = clazz.newInstance();
		Field[] attributes = clazz.getDeclaredFields();
		for (int i = 0; i < fields.size(); i++,attributeStart++) {
			attributes[attributeStart].setAccessible(true);
			String str;
			if(fields.get(i) instanceof JTextField) {
				JTextField field = (JTextField)fields.get(i);
				str = field.getText();
			} else if(fields.get(i) instanceof JTextArea) {
				JTextArea area = (JTextArea)fields.get(i);
				str = area.getText();
			} else {
				JComboBox<?> combo = (JComboBox<?>)fields.get(i);
				str = combo.getSelectedItem().toString();			
			}
			objectSetAttribute(attributes[attributeStart],object,str);
			}
		
		return object;
	}

	private void objectSetAttribute(Field field, Object obj, String str) throws IllegalArgumentException, IllegalAccessException {
		if(StringUtils.isEmpty(str)) {
			field.set(obj, null);
			return;
		}
		Class<?> type = field.getType();
		if(type.equals(String.class)) {
			field.set(obj, str);
		} else if(type.equals(Integer.class)) {
			field.set(obj, Integer.valueOf(str));
		} else if(type.equals(Double.class)) {
			field.set(obj, Double.valueOf(str));
		} else if(type.equals(Date.class)) {
			field.set(obj, DateUtils.String2Date(str));
		}
		
	}

	
	public void controlButtonEnable(List<JButton> buttons,int command) {
		for(int i=0;i<buttons.size();i++) {	
			try {
				JButton button = buttons.get(i);
				String buttonName = button.getName();
				switch(command) {
				case COMMAND_ADD:			
						
				case COMMAND_MODIFY:
		
				case COMMAND_DELETE:
					if(buttonName.equals(getButtonNameByMethodName("add"))) {
						button.setEnabled(false);
					} else if(buttonName.equals(getButtonNameByMethodName("modify"))) {
						button.setEnabled(false);
					} else if(buttonName.equals(getButtonNameByMethodName("delete"))) {
						button.setEnabled(false);
					} else if(buttonName.equals(getButtonNameByMethodName("save"))) {
						button.setEnabled(true);
					} else if(buttonName.equals(getButtonNameByMethodName("undo"))) {
						button.setEnabled(true);
					}
					ConsultFrame.patientTable.setEnabled(false);
					break;
				case COMMAND_SAVE:
					
				case COMMAND_UNDO:
					if(buttonName.equals(getButtonNameByMethodName("add"))) {
						button.setEnabled(true);
					} else if(buttonName.equals(getButtonNameByMethodName("modify"))) {
						button.setEnabled(true);
					} else if(buttonName.equals(getButtonNameByMethodName("delete"))) {
						button.setEnabled(true);
					} else if(buttonName.equals(getButtonNameByMethodName("save"))) {
						button.setEnabled(false);
					} else if(buttonName.equals(getButtonNameByMethodName("undo"))) {
						button.setEnabled(false);
					}
					ConsultFrame.patientTable.setEnabled(true);
					break;
				}

			} catch (Exception e) {
				FrameUtils.DialogErorr("错误，" + e.getMessage());
			}
		};
	}
	
	public String getMethodName(String imgUrl) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return  p.getProperty(imgUrl);
	}
	
	public List<String> getList() {
		Set<Object> imgUrlSet = p.keySet();
		List<String> imgUrlList = new ArrayList<>();
		for (Object object : imgUrlSet) {
			String imgUrl = object.toString();
			imgUrlList.add(imgUrl);
		}
		Collections.sort(imgUrlList);
		return imgUrlList;
	} 
	
	public String getButtonNameByMethodName(String methodName) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Set<Object> keySet = p.keySet();
		for (Object key : keySet) {
			String value = p.getProperty(key.toString());
			if(value.equals(methodName)) {
				return key.toString();
			}
		}
		return null;
	}

}
