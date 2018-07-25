package cn.kgc.frame.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JComponent;

import cn.kgc.frame.ConsultFrame;
import cn.kgc.model.Case;
import cn.kgc.service.impl.CaseServiceImpl;
import cn.kgc.service.intf.CaseService;
import cn.kgc.utils.FrameUtils;

public class CaseTableMouseAdapter implements MouseListener {
	
	private ConsultFrame consultFrame;
	private List<JComponent> fields;
	private Object caseId;
	private Object patientId;
	
	public CaseTableMouseAdapter(ConsultFrame consultFrame) {
		this.consultFrame = consultFrame;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			caseId = FrameUtils.getTableSelectedRowInfo(ConsultFrame.caseTable, 0);
			patientId = FrameUtils.getTableSelectedRowInfo(ConsultFrame.patientTable, 0);
			CaseService caseService = new CaseServiceImpl();
			Case $case = caseService.getCaseInfoById(caseId.toString(),patientId.toString());
			fields = consultFrame.getCaseDescriptionFields();
			FrameUtils.object2Component($case,fields,1);
		} catch (Exception e1) {
			FrameUtils.DialogErorr("´íÎó£¬" + e1.getMessage());
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
