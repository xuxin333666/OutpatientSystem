package cn.kgc.frame.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;

import cn.kgc.frame.PrescriptionFrame;
import cn.kgc.frame.listener.BaseDMLButtonListener;
import cn.kgc.model.Prescription;
import cn.kgc.service.impl.PrescriptionServiceImpl;
import cn.kgc.service.intf.PrescriptionService;
import cn.kgc.utils.FrameUtils;

public class PrescriptionDMLButtonListener extends BaseDMLButtonListener implements ActionListener {
	private static final String SAVE_SUCCUSS = "保存成功";
	private static final String SAVE_ERORR = "保存失败";
	
	private PrescriptionFrame prescriptionFrame;
	private static int command;
	private static List<JButton> buttons;
	private List<JComponent> medicineFields;
	private List<JComponent> uasgeFields;
	private List<JComponent> prescriptionFields;
	
	private PrescriptionService prescriptionService = new PrescriptionServiceImpl();


	public PrescriptionDMLButtonListener(PrescriptionFrame prescriptionFrame) {
		this.prescriptionFrame = prescriptionFrame;
		buttons = prescriptionFrame.getPrescriptionDMLButtons();
		this.medicineFields = prescriptionFrame.getMedicineFields();
		this.uasgeFields = prescriptionFrame.getUasgeFields();
		prescriptionFields = prescriptionFrame.getPrescriptionFields();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton)e.getSource();
		execute(button);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void add(JButton button) {
		emptyFields(medicineFields,uasgeFields,prescriptionFields);
		command = COMMAND_ADD;
		controlComponentsEnable(command,medicineFields,uasgeFields,prescriptionFields);
		controlButtonEnable(buttons,command);
	}

	@Override
	public void modify(JButton button) {}

	@Override
	public void delete(JButton button) {}

	@SuppressWarnings("unchecked")
	@Override
	public void save(JButton button) {
		int status = 0;
		try {
			switch(command) {
			case COMMAND_ADD:
				Prescription prescription = prescriptionContentFieldsValue2Patient(prescriptionFields);
				prescription.set$case(prescriptionFrame.get$case());
				prescription.setPatient(prescriptionFrame.get$case().getPatient());
				status = prescriptionService.add(prescription);				
			break;
			case COMMAND_MODIFY:
				break;
			case COMMAND_DELETE:
				break;
			}
			FrameUtils.statusInfo(status, SAVE_SUCCUSS, SAVE_ERORR);
			command = COMMAND_SAVE;
			controlButtonEnable(buttons,command);
			controlComponentsEnable(command,medicineFields,uasgeFields,prescriptionFields);
		} catch (Exception e) {
			FrameUtils.DialogErorr("错误，" + e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void undo(JButton button) {
		emptyFields(medicineFields,uasgeFields,prescriptionFields);
		command = COMMAND_UNDO;
		controlButtonEnable(buttons,command);
		controlComponentsEnable(command,medicineFields,uasgeFields,prescriptionFields);
	}

	@Override
	public void exit(JButton button) {
		prescriptionFrame.getPrescriptionFrame().dispose();	
	}
	
	
	private Prescription prescriptionContentFieldsValue2Patient(List<JComponent> fields) throws Exception {	
		Object obj = fieldsValue2Patient(fields, Prescription.class, 0);
		Prescription prescription = (Prescription)obj;
		
		return prescription;
	}
	

}
