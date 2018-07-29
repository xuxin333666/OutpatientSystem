package cn.kgc.frame.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import cn.kgc.frame.PrescriptionFrame;
import cn.kgc.model.PrescriptionMedicine;
import cn.kgc.utils.StringUtils;

public class PrescriptionFieldMouseListener implements MouseListener {
	private List<JComponent> medicineFields;
	private  List<JComponent> usageFields;
	private List<PrescriptionMedicine> prescriptionMedicines;
	
	public PrescriptionFieldMouseListener(PrescriptionFrame prescriptionFrame) {
		medicineFields = prescriptionFrame.getMedicineFields();
		usageFields = prescriptionFrame.getUasgeFields();
		prescriptionMedicines = prescriptionFrame.getBufferPrescriptionMedicine();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2) {
			JTextField SourceMedicineField = (JTextField)e.getSource();
			if(StringUtils.isEmpty(SourceMedicineField.getText())) {
				return;
			}
			int clickedRow = 0;
			for (;clickedRow<medicineFields.size();clickedRow++) {
				JTextField medicineField = (JTextField)medicineFields.get(clickedRow);
				if(medicineField.equals(SourceMedicineField)) {
					break;
				}
			}
			if(clickedRow < medicineFields.size()) {
				for (int i = clickedRow; i < medicineFields.size(); i++) {
					if(i == medicineFields.size()-1) {
						JTextField finalField = (JTextField)medicineFields.get(i);
						JComboBox<String> finalCombo = (JComboBox<String>)usageFields.get(i);
						finalField.setText("");
						finalCombo.setSelectedIndex(0);
						break;
					}
					
					JTextField preField = (JTextField)medicineFields.get(i);
					JTextField field = (JTextField)medicineFields.get(i+1);
					preField.setText(field.getText());
					
					JComboBox<String> preCombo = (JComboBox<String>)usageFields.get(i);
					JComboBox<String> combo = (JComboBox<String>)usageFields.get(i+1);
					preCombo.setSelectedItem(combo.getSelectedItem());
					
				}
				
				prescriptionMedicines.remove(clickedRow);
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
