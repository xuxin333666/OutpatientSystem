package cn.kgc.frame.listener.patientConsultListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import cn.kgc.frame.ConsultFrame;
import cn.kgc.frame.PrescriptionFrame;
import cn.kgc.frame.listener.BaseDMLButtonListener;
import cn.kgc.model.Case;
import cn.kgc.model.Patient;
import cn.kgc.service.impl.CaseServiceImpl;
import cn.kgc.service.impl.PatientServiceImpl;
import cn.kgc.service.intf.CaseService;
import cn.kgc.service.intf.PatientService;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.StringUtils;

public class CaseDMLButtonListener extends BaseDMLButtonListener implements ActionListener {
	private static final String SAVE_SUCCUSS = "����ɹ�";
	private static final String SAVE_ERORR = "����ʧ��";
	private static final String MAIN_SYMPTOM_IS_EMPTY = "��������Ϊ��";
	private static final String EXAMINATION_IS_EMPTY = "��ϲ���Ϊ��";
	
	private ConsultFrame consultFrame;
	private static int command;
	private static String cid;
	private List<JComponent> fields;
	private Patient patient;
	private JTable table;
	private CaseService caseService = new CaseServiceImpl();	
	private PatientService patientService = new PatientServiceImpl();	
	
	public CaseDMLButtonListener(ConsultFrame consultFrame) {
		this.consultFrame = consultFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		fields = consultFrame.getCaseDescriptionFields();
		table = consultFrame.getCaseTableFrame().getTable();
		try {
			String pid = (String)consultFrame.getPatientTableFrame().getTableSelectedRowInfo(0);
			patient = patientService.getPatientInfoById(pid);	
			JButton button = (JButton)e.getSource();
			execute(button);
		} catch (Exception e1) {
			FrameUtils.DialogErorr("����" + e1.getMessage());
			e1.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void add(JButton button) {
		try {
			cid = caseService.getMinEmptyId();
		} catch (Exception e) {
			FrameUtils.DialogErorr("����" + e.getMessage());
			e.printStackTrace();
		}
		command = COMMAND_ADD;
		controlButtonEnable(consultFrame.getCaseDMLButtons(),table,command);
		consultFrame.getPatientTableFrame().enable(false);
		FrameUtils.fieldsEnable(fields);
		emptyFields(fields);	
	}
	

	@Override
	public void modify(JButton button) {
		try {
			cid = (String)consultFrame.getCaseTableFrame().getTableSelectedRowInfo(0);
			FrameUtils.fieldsEnable(fields);
			JTextField field = (JTextField)fields.get(0);
			field.setEditable(false);
			command = COMMAND_MODIFY;
			controlButtonEnable(consultFrame.getCaseDMLButtons(),table,command);
			consultFrame.getPatientTableFrame().enable(false);
		} catch (Exception e) {
			FrameUtils.DialogErorr("����" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(JButton button) {
		try {
			cid = (String)consultFrame.getCaseTableFrame().getTableSelectedRowInfo(0);
			command = COMMAND_DELETE;
			controlButtonEnable(consultFrame.getCaseDMLButtons(),table,command);
			consultFrame.getPatientTableFrame().enable(false);
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
				Case $case = caseContentFieldsValue2Patient();
				$case.setPatient(patient);
				$case.setId(cid);
				status = caseService.add($case);				
			break;
			case COMMAND_MODIFY:
				$case = caseContentFieldsValue2Patient();
				$case.setPatient(patient);
				$case.setId(cid);
				status = caseService.modify($case);
				break;
			case COMMAND_DELETE:
				status = caseService.delete(cid);
				if(status != 0) {
					emptyFields(fields);
				}
				break;
			}
			FrameUtils.statusInfo(status, SAVE_SUCCUSS, SAVE_ERORR);
			consultFrame.getCaseTableFrame().getDataAndRefreshTable(patient.getId());
			command = COMMAND_SAVE;
			controlButtonEnable(consultFrame.getCaseDMLButtons(),table,command);
			consultFrame.getPatientTableFrame().enable(true);
			FrameUtils.fieldsDisable(fields);
		} catch (Exception e) {
			FrameUtils.DialogErorr("����" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void prescription(JButton button) {
		try {			
			String cid = (String)consultFrame.getCaseTableFrame().getTableSelectedRowInfo(0);
			Case $case = caseService.getCaseInfoById(cid,null);
			PrescriptionFrame prescriptionFrame = new PrescriptionFrame($case);
			prescriptionFrame.execute();
		} catch (Exception e) {
			FrameUtils.DialogErorr("����" + e.getMessage());
			e.printStackTrace();
		}
	};
	
	@SuppressWarnings("unchecked")
	public void undo(JButton button) {
		emptyFields(fields);
		command = COMMAND_UNDO;
		controlButtonEnable(consultFrame.getCaseDMLButtons(),table,command);
		consultFrame.getPatientTableFrame().enable(true);
		FrameUtils.fieldsDisable(fields);
	}
	
	
	private Case caseContentFieldsValue2Patient() throws Exception {
		if(StringUtils.isEmpty(((JTextArea)fields.get(1)).getText())) {
			throw new Exception(MAIN_SYMPTOM_IS_EMPTY);						
		}
		if(StringUtils.isEmpty(((JTextArea)fields.get(7)).getText())) {
			throw new Exception(EXAMINATION_IS_EMPTY);						
		}
		Object obj = fieldsValue2Patient(fields, Case.class, 1);
		Case $case = (Case)obj;
		
		return $case;
	}
	
	



}
