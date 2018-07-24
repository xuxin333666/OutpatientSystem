package cn.kgc.frame.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

public class PatientTableMouseAdapter implements MouseListener {
	private static final String REFRESH_CASE_TABLE_COMMAND = "case";
	
	private ConsultFrame consultFrame;
	private List<JComponent> fields;
	
	public PatientTableMouseAdapter(ConsultFrame consultFrame) {
		this.consultFrame = consultFrame;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JTable table = ConsultFrame.patientTable;
		if(table.getSelectedRowCount() == 1) {
			int rowNo = table.getSelectedRow();
			Object id = table.getValueAt(rowNo, 0);
			PatientService patientService = new PatientServiceImpl();
			try {
				Patient patient = patientService.getPatientInfoById(id.toString());
				fields = consultFrame.getRegistContentFields();
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
				
				
				
				refreshCaseTable();
				
			} catch (Exception e1) {
				FrameUtils.DialogErorr("´íÎó£¬" + e1.getMessage());
			}
		}
		
	}

	private void refreshCaseTable() {
		JTextField idField = (JTextField)fields.get(0);
		String patientId = idField.getText();
		FrameUtils.getDataAndRefreshTableBySearch(REFRESH_CASE_TABLE_COMMAND, patientId);
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	

}
