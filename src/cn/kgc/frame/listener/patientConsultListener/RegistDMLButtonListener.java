package cn.kgc.frame.listener.patientConsultListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;

import cn.kgc.dto.PatientDto;
import cn.kgc.frame.ConsultFrame;
import cn.kgc.frame.listener.BaseDMLButtonListener;
import cn.kgc.model.Patient;
import cn.kgc.service.impl.PatientServiceImpl;
import cn.kgc.service.intf.PatientService;
import cn.kgc.utils.DateUtils;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.PatientUtils;
import cn.kgc.utils.StringUtils;

public class RegistDMLButtonListener extends BaseDMLButtonListener implements ActionListener {
	private static final String SAVE_SUCCUSS = "����ɹ�";
	private static final String SAVE_ERORR = "����ʧ��";
	
	private ConsultFrame consultFrame;
	private static int command;
	private List<JComponent> fields;
	private JTable table;
	private PatientService patientService = new PatientServiceImpl();	
	
	public RegistDMLButtonListener(ConsultFrame consultFrame) {
		this.consultFrame = consultFrame;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		fields = consultFrame.getRegistContentFields();
		table = consultFrame.getPatientTableFrame().getTable();
		
		JTextField field = (JTextField)fields.get(0);
		field.setEditable(false);
		
		JButton button = (JButton)e.getSource();
		execute(button);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void add(JButton button) {
		command = COMMAND_ADD;
		controlButtonEnable(consultFrame.getRegistDMLButtons(),table,command);
		FrameUtils.fieldsEnable(fields);
		emptyFields(fields);
		JTextField field = (JTextField)fields.get(0);		
		try {
			String id = patientService.getMinEmptyId();
			field.setText(id);
		} catch (Exception e) {
			FrameUtils.DialogErorr("����" + e.getMessage());
			e.printStackTrace();
		}
	}
	

	@Override
	public void modify(JButton button) {
		try {
			consultFrame.getPatientTableFrame().getTableSelectedRowInfo(0);
			command = COMMAND_MODIFY;
			controlButtonEnable(consultFrame.getRegistDMLButtons(),table,command);
			FrameUtils.fieldsEnable(fields);
		} catch (Exception e) {
			FrameUtils.DialogErorr("����" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(JButton button) {
		try {
			consultFrame.getPatientTableFrame().getTableSelectedRowInfo(0);
			command = COMMAND_DELETE;
			controlButtonEnable(consultFrame.getRegistDMLButtons(),table,command);
		} catch (Exception e) {
			FrameUtils.DialogErorr("����" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void save(JButton button) {
		int status = 0;
		try {
			switch(command) {
			case COMMAND_ADD:
				Patient patient = RegistContentFieldsValue2Patient(fields);
				status = patientService.add(patient);				
			break;
			case COMMAND_MODIFY:
				patient = RegistContentFieldsValue2Patient(fields);
				status = patientService.modify(patient);
				break;
			case COMMAND_DELETE:
				JTextField idField = (JTextField)fields.get(0);
				String id = idField.getText();
				status = patientService.delete(id);
				if(status != 0) {
					emptyFields(fields);
				}
				break;
			}
			FrameUtils.statusInfo(status, SAVE_SUCCUSS, SAVE_ERORR);
			
			String startTimeStr = consultFrame.getStartTimeField().getText();
			String endTimeStr = consultFrame.getEndTimeField().getText();
			String queryColumnNameStr = consultFrame.getQueryColumnNameComboBox().getSelectedItem().toString();
			String keyStr = consultFrame.getKeyField().getText();
			PatientDto patientDto = new PatientDto(startTimeStr,endTimeStr,queryColumnNameStr,keyStr);
			consultFrame.getPatientTableFrame().getDataAndRefreshTable(patientDto);
			command = COMMAND_SAVE;
			controlButtonEnable(consultFrame.getRegistDMLButtons(),table,command);
			FrameUtils.fieldsDisable(fields);
		} catch (Exception e) {
			FrameUtils.DialogErorr("����" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void undo(JButton button) {
		emptyFields(fields);
		command = COMMAND_UNDO;
		controlButtonEnable(consultFrame.getRegistDMLButtons(),table,command);
		FrameUtils.fieldsDisable(fields);
	}
	
	
	private Patient RegistContentFieldsValue2Patient(List<JComponent> fields) throws Exception {
		String str = null;
		JComboBox<?> combo;
		if(StringUtils.isEmpty(((JTextField)fields.get(1)).getText())) {
			throw new Exception("��������Ϊ�գ�");						
		}
					
		str = DateUtils.calculateAgeByStr(((JTextField)fields.get(3)).getText());
		((JTextField)fields.get(3)).setText(str);
			
		Object obj = fieldsValue2Patient(fields, Patient.class, 0);
		Patient patient = (Patient)obj;
		
		for (int i = 0; i < fields.size(); i++) {
			if(fields.get(i) instanceof JTextField) {
				JTextField field = (JTextField)fields.get(i);
				str = field.getText();
			} else if (fields.get(i) instanceof JComboBox) {
				combo = (JComboBox<?>)fields.get(i);
				str = (String)combo.getSelectedItem();
			}
		
			if(i == 2) {	
				str = PatientUtils.Str2status(str, PatientUtils.sexRule);
				patient.setSex(str);
			} else if(i == 4) {
				str = PatientUtils.Str2status(str, PatientUtils.marriedRule);
				patient.setMarried(str);
			} else if(i == 5) {
				str = PatientUtils.Str2status(str, PatientUtils.jobRule);
				patient.setJob(str);
			} else if(i == 7) {
				str = PatientUtils.Str2status(str, PatientUtils.bloodRule);
				patient.setBlood(str);	
			}
			
		}
		
		return patient;
	}
	



}
