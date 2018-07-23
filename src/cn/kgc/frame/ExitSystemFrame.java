package cn.kgc.frame;

import cn.kgc.frame.intf.BusinessButtonFrameIntf;

public class ExitSystemFrame implements BusinessButtonFrameIntf {

	@Override
	public void execute() {
		System.exit(0);
		
	}

}
