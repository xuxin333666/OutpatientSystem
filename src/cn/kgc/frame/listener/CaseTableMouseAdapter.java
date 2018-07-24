package cn.kgc.frame.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;

import cn.kgc.frame.ConsultFrame;
import cn.kgc.model.Patient;
import cn.kgc.service.impl.PatientServiceImpl;
import cn.kgc.service.intf.PatientService;
import cn.kgc.utils.FrameUtils;

public class CaseTableMouseAdapter implements MouseListener {
	
	private ConsultFrame consultFrame;
	private List<JComponent> fields;
	private String id;
	
	public CaseTableMouseAdapter(ConsultFrame consultFrame) {
		this.consultFrame = consultFrame;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JTable table = ConsultFrame.caseTable;
		if(table.getSelectedRowCount() == 1) {
			int rowNo = table.getSelectedRow();
			id = table.getValueAt(rowNo, 0).toString();
			PatientService patientService = new PatientServiceImpl();
			try {
				Patient patient = patientService.getPatientInfoById(id);
				fields = consultFrame.getRegistContentFields();
				FrameUtils.object2Component(patient,fields);
			} catch (Exception e1) {
				FrameUtils.DialogErorr("´íÎó£¬" + e1.getMessage());
			}
		}
		
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
