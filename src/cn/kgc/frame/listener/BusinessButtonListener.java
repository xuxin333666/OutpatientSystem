package cn.kgc.frame.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.kgc.frame.BusinessButtonFrame;
import cn.kgc.frame.intf.BusinessButtonFrameIntf;
import cn.kgc.utils.BusinessButtonUtils;
import cn.kgc.utils.FrameUtils;

public class BusinessButtonListener implements ActionListener {
	private final String BUSINESS_STATUS_EXCEPTION = "系统异常，请联系管理员,";
	
	private String imgUrl;
	private BusinessButtonFrame businessButtonFrame;
	
	
	public BusinessButtonListener(String imgUrl, BusinessButtonFrame businessButtonFrame) {
		this.imgUrl = imgUrl;
		this.businessButtonFrame = businessButtonFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BusinessButtonFrameIntf businessButtonFrameIntf;
		try {
			businessButtonFrameIntf = BusinessButtonUtils.getBusinessButtonFrame(imgUrl);
			businessButtonFrameIntf.execute(businessButtonFrame);
		} catch (Exception e1) {
			FrameUtils.DialogErorr(BUSINESS_STATUS_EXCEPTION + e1.getMessage());
		}

	}

}
