package cn.kgc.frame.listener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.kgc.frame.ConsultFrame;
import cn.kgc.model.Patient;
import cn.kgc.service.impl.PatientServiceImpl;
import cn.kgc.service.intf.PatientService;
import cn.kgc.utils.DateUtils;
import cn.kgc.utils.FrameUtils;

public class PatientTableFocusListener implements FocusListener {
	private ConsultFrame consultFrame;
	
	public PatientTableFocusListener(ConsultFrame consultFrame) {
		this.consultFrame = consultFrame;
	}

	@Override
	public void focusGained(FocusEvent e) {
		JTable table = ConsultFrame.patientTable;
		if(table.getSelectedRowCount() == 1) {
			int rowNo = table.getSelectedRow();
			Object id = table.getValueAt(rowNo, 0);
			PatientService patientService = new PatientServiceImpl();
			try {
				Patient patient = patientService.getPatientInfoById(id.toString());
				List<JComponent> fields = consultFrame.getRegistContentFields();
				Field[] attributes = patient.getClass().getDeclaredFields();
				for (int i = 0; i < fields.size(); i++) {
					attributes[i].setAccessible(true);
					Object value = attributes[i].get(patient);
					if(value == null) {
						continue;
					}
					if(fields.get(i) instanceof JTextField && i != 3) {
						JTextField field = (JTextField)fields.get(i);
						field.setText(value.toString());
					} else if(i == 3) {
						JTextField field = (JTextField)fields.get(i);
						field.setText(DateUtils.age2Date(Double.parseDouble(value.toString())));
					} else if(fields.get(i) instanceof JTextArea) {
						JTextArea area = (JTextArea)fields.get(i);
						area.setText(value.toString());
					} else {
						JComboBox<?> combo = (JComboBox<?>)fields.get(i);
						combo.setSelectedIndex(Integer.parseInt(value.toString()));
					}
				}
				
			} catch (Exception e1) {
				FrameUtils.DialogErorr("����" + e1.getMessage());
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {}

}
