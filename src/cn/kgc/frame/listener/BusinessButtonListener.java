package cn.kgc.frame.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import cn.kgc.frame.intf.BaseBusinessButtonFrame;
import cn.kgc.utils.BusinessButtonUtils;
import cn.kgc.utils.FrameUtils;

public class BusinessButtonListener implements ActionListener {
	private static final String BUSINESS_STATUS_EXCEPTION = "系统异常，请联系管理员,";
	private static final String BUSINESS_NOTFOUND_EXCEPTION = "该功能正在建设中";


	@Override
	public void actionPerformed(ActionEvent e) {		
		try {
			JButton button = (JButton)e.getSource();
			BaseBusinessButtonFrame businessButtonFrame = BusinessButtonUtils.getBusinessButtonFrame(button.getName());
			businessButtonFrame.execute();
		} catch(ClassNotFoundException e1) {
			FrameUtils.DialogErorr(BUSINESS_NOTFOUND_EXCEPTION);
		} catch (Exception e1) {
			FrameUtils.DialogErorr(BUSINESS_STATUS_EXCEPTION + e1.getMessage());
		}
	}

}
