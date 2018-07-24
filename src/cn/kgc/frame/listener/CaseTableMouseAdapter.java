package cn.kgc.frame.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.kgc.frame.ConsultFrame;
import cn.kgc.model.Patient;
import cn.kgc.service.impl.PatientServiceImpl;
import cn.kgc.service.intf.PatientService;
import cn.kgc.utils.DateUtils;
import cn.kgc.utils.FrameUtils;

public class CaseTableMouseAdapter implements MouseListener {
	private static final String REFRESH_CASE_TABLE_COMMAND = "case";
	private static final String[] CASE_TOOL_LABEL_PATIENT_ID_TITLEs = {"医疗证号:","姓名:","性别:","年龄:"};
	
	private ConsultFrame consultFrame;
	private List<JComponent> fields;
	private String id;
	
	public CaseTableMouseAdapter(ConsultFrame consultFrame) {
		this.consultFrame = consultFrame;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JTable table = ConsultFrame.patientTable;
		if(table.getSelectedRowCount() == 1) {
			int rowNo = table.getSelectedRow();
			id = table.getValueAt(rowNo, 0).toString();
			PatientService patientService = new PatientServiceImpl();
			try {
				Patient patient = patientService.getPatientInfoById(id);
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
				refreshCaseTool();
				
			} catch (Exception e1) {
				FrameUtils.DialogErorr("错误，" + e1.getMessage());
			}
		}
		
	}

	private void refreshCaseTool() {
		List<JLabel> caseToolLabels = consultFrame.getCaseToolLabels();

		for(int i=0;i<caseToolLabels.size();i++) {

			if(fields.get(i) instanceof JTextField) {
				JTextField idField = (JTextField)fields.get(i);
				String str = null;
				if(i == 3) {
					str = DateUtils.calculateAgeByStr(idField.getText());
				} else {
					str =idField.getText();
				}
				caseToolLabels.get(i).setText(CASE_TOOL_LABEL_PATIENT_ID_TITLEs[i] + "   " + str);
			} else if(fields.get(i) instanceof JComboBox) {
				JComboBox<?> combo = (JComboBox<?>)fields.get(i);
				caseToolLabels.get(i).setText(CASE_TOOL_LABEL_PATIENT_ID_TITLEs[i] + "   " + combo.getSelectedItem().toString());
			}
		}
	}

	private void refreshCaseTable() {
		FrameUtils.getDataAndRefreshTableBySearch(REFRESH_CASE_TABLE_COMMAND,id);
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
