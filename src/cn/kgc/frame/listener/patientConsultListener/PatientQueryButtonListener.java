package cn.kgc.frame.listener.patientConsultListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.kgc.dto.PatientDto;
import cn.kgc.frame.ConsultFrame;
import cn.kgc.service.impl.PatientServiceImpl;
import cn.kgc.utils.FrameUtils;

public class PatientQueryButtonListener implements ActionListener {
	
	
	private ConsultFrame consultFrame;
	
	public PatientQueryButtonListener(ConsultFrame consultFrame) {
		this.consultFrame = consultFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String startTimeStr = consultFrame.getStartTimeField().getText();
		String endTimeStr = consultFrame.getEndTimeField().getText();
		String queryColumnNameStr = consultFrame.getQueryColumnNameComboBox().getSelectedItem().toString();
		String keyStr = consultFrame.getKeyField().getText();
		PatientDto patientDto = new PatientDto(startTimeStr,endTimeStr,queryColumnNameStr,keyStr);
		FrameUtils.getDataAndRefreshTableBySearch(ConsultFrame.patientTable,PatientServiceImpl.class, patientDto);
	}

}
