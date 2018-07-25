package cn.kgc.frame.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.kgc.frame.LoginFrame;
import cn.kgc.frame.MainFrame;
import cn.kgc.model.User;
import cn.kgc.service.impl.UserServiceImpl;
import cn.kgc.service.intf.UserService;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.MD5Utils;

public class LoginFrameLoginButtonListener implements ActionListener {
	private LoginFrame loginFrame;
	
	
	public LoginFrameLoginButtonListener(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		UserService userService = new UserServiceImpl();
		String username = loginFrame.getNameField().getText();
		char[] password = loginFrame.getPwdField().getPassword();
		try {
			String pwdByMD5 = MD5Utils.Encoder(new String(password));
			User user = userService.login(username, pwdByMD5);
			openMainFrame(user);
		} catch (Exception e1) {
			FrameUtils.DialogErorr("´íÎó£¬" + e1.getMessage());
			e1.printStackTrace();
		}
	}


	private void openMainFrame(User user) {
		MainFrame mainFrame = MainFrame.getInstance();
		mainFrame.execute(user);
	}

}
