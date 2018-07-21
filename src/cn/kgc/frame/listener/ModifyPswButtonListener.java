package cn.kgc.frame.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;

import cn.kgc.frame.MainFrame;
import cn.kgc.model.User;
import cn.kgc.service.impl.UserServiceImpl;
import cn.kgc.service.intf.UserService;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.MD5Utils;

public class ModifyPswButtonListener implements ActionListener {
	private final String PWD_SAME = "密码不能与原密码相同";
	
	
	private JPasswordField newPwdField;
	
	public ModifyPswButtonListener(JPasswordField newPwdField) {
		this.newPwdField = newPwdField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MainFrame mainFrame = MainFrame.getInstance();
		User user = mainFrame.getUser();
		char[] newPwds = newPwdField.getPassword();
		String newPwd = new String(newPwds);
		UserService userService = new UserServiceImpl();
		try {
			String pwdMD5 = MD5Utils.Encoder(newPwd);
			if(user.getPwd().equals(pwdMD5)) {
				FrameUtils.DialogErorr(PWD_SAME);
				return;
			}
			user = userService.modifyPwd(user.getId(),user.getName(),pwdMD5);
			mainFrame.execute(user);
		} catch (Exception e1) {
			FrameUtils.DialogErorr("错误，" + e1.getMessage());
		}
	}

}
