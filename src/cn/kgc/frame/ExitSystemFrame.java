package cn.kgc.frame;

import cn.kgc.frame.intf.BaseBusinessButtonFrame;

public class ExitSystemFrame implements BaseBusinessButtonFrame {

	@Override
	public void execute() {
		System.exit(0);
		
	}

}
