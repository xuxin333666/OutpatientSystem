package cn.kgc.frame.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.kgc.dto.PatientDto;
import cn.kgc.frame.ConsultFrame;
import cn.kgc.service.impl.PatientServiceImpl;
import cn.kgc.service.intf.PatientService;
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
		PatientService patientService = new PatientServiceImpl();
		try {
			Object[][] datas = patientService.getAllPatientInfoBySearch(new PatientDto(startTimeStr,endTimeStr,queryColumnNameStr,keyStr));
			ConsultFrame.refreshTable(datas);
		} catch (Exception e1) {
			FrameUtils.DialogErorr(e1.getMessage());
		}
	}

}
