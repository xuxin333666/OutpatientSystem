package cn.kgc.frame.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import cn.kgc.frame.PrescriptionFrame;
import cn.kgc.frame.listener.BaseDMLButtonListener;
import cn.kgc.model.Prescription;
import cn.kgc.model.PrescriptionMedicine;
import cn.kgc.service.impl.PrescriptionMedicineServiceImpl;
import cn.kgc.service.impl.PrescriptionServiceImpl;
import cn.kgc.service.intf.PrescriptionMedicineService;
import cn.kgc.service.intf.PrescriptionService;
import cn.kgc.utils.FrameUtils;

public class PrescriptionDMLButtonListener extends BaseDMLButtonListener implements ActionListener {
	private static final String SAVE_SUCCUSS = "±£¥Ê≥…π¶";
	private static final String SAVE_ERORR = "±£¥Ê ß∞‹";
	
	private PrescriptionFrame prescriptionFrame;
	private static int command;
	private static List<JButton> buttons;
	private List<JComponent> medicineFields;
	private List<JComponent> uasgeFields;
	private List<JComponent> prescriptionFields;
	
	private PrescriptionService prescriptionService = new PrescriptionServiceImpl();
	private PrescriptionMedicineService prescriptionMedicineService = new PrescriptionMedicineServiceImpl();


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
		JTextField idField = (JTextField)prescriptionFields.get(0);
		try {
			String id = prescriptionService.getMinEmptyId();
			idField.setText(id);
			idField.setEditable(false);
			List<PrescriptionMedicine> prescriptionMedicines = prescriptionFrame.getBufferPrescriptionMedicine();
			prescriptionMedicines.clear();
			command = COMMAND_ADD;
			controlComponentsEnable(command,medicineFields,uasgeFields,prescriptionFields);
			controlButtonEnable(buttons,command);
		} catch (Exception e) {
			FrameUtils.DialogErorr("¥ÌŒÛ£¨" + e.getMessage());
			e.printStackTrace();
		}
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
				int pStatus = prescriptionService.add(prescription);		
				
				List<PrescriptionMedicine> prescriptionMedicines = prescriptionFrame.getBufferPrescriptionMedicine();
				for (int i = 0; i < prescriptionMedicines.size(); i++) {
					JComboBox<String> usageCombo = (JComboBox<String>)uasgeFields.get(i);
					String uasge = usageCombo.getSelectedItem().toString();
					prescriptionMedicines.get(i).setUasge(uasge);				
				}
				int pmStatus = prescriptionMedicineService.add(prescriptionMedicines);
				if(pStatus > 0 && pmStatus == prescriptionMedicines.size()) {
					status = 1;
				}
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
			FrameUtils.DialogErorr("¥ÌŒÛ£¨" + e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void undo(JButton button) {
		List<PrescriptionMedicine> prescriptionMedicines = prescriptionFrame.getBufferPrescriptionMedicine();
		prescriptionMedicines.clear();
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
