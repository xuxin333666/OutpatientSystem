package cn.kgc.frame.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.kgc.frame.ConsultFrame;
import cn.kgc.model.Patient;
import cn.kgc.service.impl.PatientServiceImpl;
import cn.kgc.service.intf.PatientService;
import cn.kgc.utils.DateUtils;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.PatientUtils;
import cn.kgc.utils.RegistDMLButtonUtils;
import cn.kgc.utils.StringUtils;

public class RegistDMLButtonListener implements ActionListener {
	private static final int COMMAND_ADD = 1;
	private static final int COMMAND_MODIFY = 2;
	private static final int COMMAND_DELETE = 3;
	private static final int COMMAND_SAVE = 4;
	private static final int COMMAND_UNDO = 5;
	private static final int DEFAULT_INDEX = 0;
	private static final String OTHER_TO_DO = "暂未开发该功能";
	private static final String DEFAULT_CONTENT = "";
	
	private ConsultFrame consultFrame;
	private static int command;
	
	public RegistDMLButtonListener(ConsultFrame consultFrame) {
		this.consultFrame = consultFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton)e.getSource();
		try {
			String methodName = RegistDMLButtonUtils.getMethodName(button.getName());
			Method method = RegistDMLButtonListener.class.getMethod(methodName,JButton.class);
			method.invoke(this,button);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void addPatient(JButton button) {
		command = COMMAND_ADD;
		controlButtonEnable();
		List<JComponent> fields = consultFrame.getRegistContentFields();
		emptyRegistContentFields(fields);
		JTextField field = (JTextField)fields.get(0);
		field.setEditable(false);
		PatientService patientService = new PatientServiceImpl();			
		try {
			String id = patientService.getMinEmptyId();
			field.setText(id);
		} catch (Exception e) {
			FrameUtils.DialogErorr("错误，" + e.getMessage());
		}
	}
	
	private void emptyRegistContentFields(List<JComponent> fields) {
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

	public void modifyPatient(JButton button) {
		command = COMMAND_MODIFY;
		controlButtonEnable();
	}
	
	public void deletePatient(JButton button) {
		command = COMMAND_DELETE;
		controlButtonEnable();
	}
	
	public void savePatient(JButton button) {
		switch(command) {
		case COMMAND_ADD:
			List<JComponent> fields = consultFrame.getRegistContentFields();
			Patient patient = RegistContentFieldsValue2Patient(fields);
			break;
		case COMMAND_MODIFY:
			break;
		case COMMAND_DELETE:
			break;
		}
		command = COMMAND_SAVE;
		controlButtonEnable();
	}
	
	private Patient RegistContentFieldsValue2Patient(List<JComponent> fields) {
		Patient patient = new Patient();
		Field[] attributes = patient.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < fields.size(); i++) {
				attributes[i].setAccessible(true);
				String str;
				if(fields.get(i) instanceof JTextField) {
					JTextField field = (JTextField)fields.get(i);
					str = field.getText();
					if(StringUtils.isEmpty(str)) {
						if(i==1) {
							throw new Exception("姓名不能为空！");
						}
					}
					if(i == 3) {
						str = DateUtils.calculateAgeByStr(str);					
					}
				} else if(fields.get(i) instanceof JTextArea) {
					JTextArea area = (JTextArea)fields.get(i);
					str = area.getText();
				} else {
					JComboBox<?> combo = (JComboBox<?>)fields.get(i);
					str = (String)combo.getSelectedItem();
					if(i == 2) {
						str = PatientUtils.Str2status(str, PatientUtils.sexRule);
					} else if(i == 4) {
						str = PatientUtils.Str2status(str, PatientUtils.marriedRule);
					} else if(i == 5) {
						str = PatientUtils.Str2status(str, PatientUtils.jobRule);
					} else if(i == 7) {
						str = PatientUtils.Str2status(str, PatientUtils.bloodRule);
					}
				
				}
				patientSetAttribute(attributes[i],patient,str);
			}
		} catch (Exception e) {
			FrameUtils.DialogErorr("错误，" + e.getMessage());
		}
		return patient;
	}

	private void patientSetAttribute(Field field, Patient patient, String str) throws IllegalArgumentException, IllegalAccessException {
		Class<?> type = field.getType();
		if(type.equals(String.class)) {
			field.set(patient, str);
		} else if(type.equals(Integer.class)) {
			field.set(patient, Integer.valueOf(str));
		} else if(type.equals(Double.class)) {
			field.set(patient, Double.valueOf(str));
		} else if(type.equals(Date.class)) {
			field.set(patient, DateUtils.String2Date(str));
		}
		
	}

	public void undo(JButton button) {
		command = COMMAND_UNDO;
		controlButtonEnable();
	}
	
	public void otherToDo(JButton button) {
		FrameUtils.DialogErorr(OTHER_TO_DO);
	}
	
	private void controlButtonEnable() {
		List<JButton> buttons = consultFrame.getRegistDMLButtons();
		for(int i=0;i<buttons.size();i++) {	
			try {
				JButton button = buttons.get(i);
				String buttonName = button.getName();
				switch(command) {
				case COMMAND_ADD:			
						
				case COMMAND_MODIFY:
		
				case COMMAND_DELETE:
					if(buttonName.equals(RegistDMLButtonUtils.getButtonNameByMethodName("addPatient"))) {
						button.setEnabled(false);
					} else if(buttonName.equals(RegistDMLButtonUtils.getButtonNameByMethodName("modifyPatient"))) {
						button.setEnabled(false);
					} else if(buttonName.equals(RegistDMLButtonUtils.getButtonNameByMethodName("deletePatient"))) {
						button.setEnabled(false);
					} else if(buttonName.equals(RegistDMLButtonUtils.getButtonNameByMethodName("savePatient"))) {
						button.setEnabled(true);
					} else if(buttonName.equals(RegistDMLButtonUtils.getButtonNameByMethodName("undo"))) {
						button.setEnabled(true);
					}
					ConsultFrame.patientTable.setEnabled(false);
					break;
				case COMMAND_SAVE:
					
				case COMMAND_UNDO:
					if(buttonName.equals(RegistDMLButtonUtils.getButtonNameByMethodName("addPatient"))) {
						button.setEnabled(true);
					} else if(buttonName.equals(RegistDMLButtonUtils.getButtonNameByMethodName("modifyPatient"))) {
						button.setEnabled(true);
					} else if(buttonName.equals(RegistDMLButtonUtils.getButtonNameByMethodName("deletePatient"))) {
						button.setEnabled(true);
					} else if(buttonName.equals(RegistDMLButtonUtils.getButtonNameByMethodName("savePatient"))) {
						button.setEnabled(false);
					} else if(buttonName.equals(RegistDMLButtonUtils.getButtonNameByMethodName("undo"))) {
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

}
