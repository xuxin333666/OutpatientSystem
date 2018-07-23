package cn.kgc.frame.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.kgc.frame.intf.BusinessButtonFrameIntf;
import cn.kgc.utils.BusinessButtonUtils;
import cn.kgc.utils.FrameUtils;

public class BusinessButtonListener implements ActionListener {
	private final String BUSINESS_STATUS_EXCEPTION = "系统异常，请联系管理员,";
	
	private String imgUrl;
	
	
	public BusinessButtonListener(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BusinessButtonFrameIntf businessButtonFrameIntf;
		try {
			businessButtonFrameIntf = BusinessButtonUtils.getBusinessButtonFrame(imgUrl);
			businessButtonFrameIntf.execute();
		} catch (Exception e1) {
			FrameUtils.DialogErorr(BUSINESS_STATUS_EXCEPTION + e1.getMessage());
		}

	}

}
