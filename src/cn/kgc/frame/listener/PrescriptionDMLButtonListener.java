package cn.kgc.frame.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
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

public class PrescriptionDMLButtonListener extends BaseDMLButtonListener implements ActionListener {
	private static final String SAVE_SUCCUSS = "保存成功";
	private static final String SAVE_ERORR = "保存失败";
	
	private PrescriptionFrame prescriptionFrame;
	private static int command;
	private List<JComponent> fields;


	public PrescriptionDMLButtonListener(PrescriptionFrame prescriptionFrame) {
		this.prescriptionFrame = prescriptionFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public void add(JButton button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modify(JButton button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(JButton button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(JButton button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo(JButton button) {
		// TODO Auto-generated method stub
		
	}
	
	

}
