package cn.kgc.frame;

import cn.kgc.frame.intf.BusinessButtonFrameIntf;

public class ExitSystemFrame implements BusinessButtonFrameIntf {

	@Override
	public void execute(BusinessButtonFrame businessButtonFrame) {
		System.exit(0);
		
	}

}
