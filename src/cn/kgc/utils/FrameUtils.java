package cn.kgc.utils;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.kgc.dto.PatientDto;
import cn.kgc.frame.ConsultFrame;
import cn.kgc.frame.model.CaseTableModel;
import cn.kgc.frame.model.PatientTableModel;
import cn.kgc.frame.model.TableModelSetDate;
import cn.kgc.service.impl.CaseServiceImpl;
import cn.kgc.service.impl.PatientServiceImpl;
import cn.kgc.service.intf.CaseService;
import cn.kgc.service.intf.PatientService;




public class FrameUtils {
	private static final String REFRESH_PATIENT_TABLE_COMMAND = "patient";
	private static final String REFRESH_CASE_TABLE_COMMAND = "case";
	
	public static JButton addButton(String imgUrl,int positionX ,int width,int height,JPanel parentPanel) {
		ImageIcon img = new ImageIcon(imgUrl);
		img.setImage(img.getImage().getScaledInstance(width-10,height-10,Image.SCALE_DEFAULT ));
		JButton button = new JButton(img);
		button.setBounds(positionX, 0,width, height);
		button.setBackground(Color.WHITE);
		button.setBorderPainted(false);

		parentPanel.add(button);
		buttonAddMouseListener(button);
		
		
		return button;		
	}	
	
	public static void buttonAddMouseListener(JButton button) {
		button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	if(button.isEnabled()) {
            		button.setBorderPainted(true);          		
            	}
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	button.setBorderPainted(false);
            }
        });
	}
	
	public static void buttonRemoveMouseListener(JButton button) {
		MouseListener[] mListeners = button.getMouseListeners();
		for(int j=0;j<mListeners.length;j++) {
			button.removeMouseListener(mListeners[j]);					
		}
	}
	
	public static void statusInfo(int status,String succuss,String erorr) throws Exception {
		if(status <= 0) {
			throw new Exception(erorr);
		} else {
			JOptionPane.showMessageDialog(null, succuss, "³É¹¦", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public static void DialogErorr(String msg) {
		JOptionPane.showMessageDialog(null, msg, "´íÎó", JOptionPane.ERROR_MESSAGE);
	}
	
	
	public static void getDataAndRefreshTable(String type) {
		try {
			if(REFRESH_PATIENT_TABLE_COMMAND.equals(type)) {
				PatientService patientService = new PatientServiceImpl();
				Object[][] datas = patientService.getAllPatientInfo();
				refreshTable(datas,PatientTableModel.getInstance(),ConsultFrame.patientTable);			
			} else if(REFRESH_CASE_TABLE_COMMAND.equals(type)) {
				CaseService caseService = new CaseServiceImpl();
				Object[][] datas = caseService.getAllCaseInfo();
				refreshTable(datas,CaseTableModel.getInstance(),ConsultFrame.caseTable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FrameUtils.DialogErorr(e.getMessage());
		}
	}
	
	public static void getDataAndRefreshTableBySearch(String type,Object dto) {
		try {
			if(REFRESH_PATIENT_TABLE_COMMAND.equals(type)) {
				PatientService patientService = new PatientServiceImpl();
				Object[][] datas = patientService.getAllPatientInfoBySearch((PatientDto)dto);
				refreshTable(datas,PatientTableModel.getInstance(),ConsultFrame.patientTable);			
			} else if(REFRESH_CASE_TABLE_COMMAND.equals(type)) {
				CaseService caseService = new CaseServiceImpl();
				Object[][] datas = caseService.getAllCaseInfoByPatient(dto.toString());
				refreshTable(datas,CaseTableModel.getInstance(),ConsultFrame.caseTable);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FrameUtils.DialogErorr(e.getMessage());
		}
	}
	
	public static void refreshTable(Object[][] datas,TableModelSetDate t,JTable table) {
		t.setDatas(datas);
		table.updateUI();
	}
	
	public static void object2Component(Object obj,List<JComponent> fields) throws IllegalArgumentException, IllegalAccessException {
		Field[] attributes = obj.getClass().getDeclaredFields();
		for (int i=0;i<fields.size();i++) {
			attributes[i].setAccessible(true);
			Object value = attributes[i].get(obj);
			if(value == null) {
				continue;
			}
			JComponent field = fields.get(i);
			if(field instanceof JTextField) {
				JTextField textField = (JTextField)field;
				textField.setText(value.toString());
			} else if(field instanceof JTextArea) {
				JTextArea textArea = (JTextArea)field;
				textArea.setText(value.toString());
			} else if(field instanceof JComboBox<?>) {
				JComboBox<?> combo = (JComboBox<?>)field;
				combo.setSelectedIndex(Integer.parseInt(value.toString()));
			}
		}
	}
	
}
