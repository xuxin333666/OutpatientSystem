package cn.kgc.frame;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cn.kgc.frame.listener.LoginFrameCancelButtonListener;
import cn.kgc.frame.listener.LoginFrameLoginButtonListener;
import cn.kgc.utils.ScreenSizeUtils;

public class LoginFrame {
	private final String TITLE = "登 陆 窗 口";
	private final String USERNAME_LABEL_CONTENT = "用户名：";
	private final String PASSWORD_LABEL_CONTENT = "密码：";
	private final String LOGIN_BUTTON_CONTENT = "确认";
	private final String CANCEL_BUTTON_CONTENT = "取消";
	private final String BACKGROUND_URL = "./img/login.png";
	
	
	private final int WIDTH = 625;
	private final int HEIGHT = 335;
	
	private final int INPUT_PANEL_POSITION_X = 365;
	private final int INPUT_PANEL_POSITION_Y = 225;
	private final int INPUT_PANEL_WIDTH = 300;
	private final int INPUT_PANEL_HEIGHT = HEIGHT - INPUT_PANEL_POSITION_Y;
	
	private final int USERNAME_LABEL_POSITION_X = 0;
	private final int USERNAME_LABEL_WIDTH = 55;
	private final int USERNAME_POSITION_Y = 0;
	private final int USERNAME_FIELD_POSITION_X = USERNAME_LABEL_POSITION_X + USERNAME_LABEL_WIDTH;
	private final int USERNAME_FEILD_WIDTH = 100;
	private final int USERNAME_HEIGHT = 20;
	
	private final int PASSWORD_LABEL_POSITION_X = 0;
	private final int PASSWORD_LABEL_WIDTH = USERNAME_LABEL_WIDTH;
	private final int PASSWORD_POSITION_Y = USERNAME_HEIGHT + 10;
	private final int PASSWORD_FIELD_POSITION_X = PASSWORD_LABEL_POSITION_X + PASSWORD_LABEL_WIDTH;
	private final int PASSWORD_FEILD_WIDTH = USERNAME_FEILD_WIDTH;
	private final int PASSWORD_HEIGHT = USERNAME_HEIGHT;
	
	private final int LOGIN_BUTTON_POSITION_X = USERNAME_FIELD_POSITION_X + USERNAME_FEILD_WIDTH + 10;
	private final int LOGIN_BUTTON_POSITION_Y = USERNAME_POSITION_Y;
	private final int LOGIN_BUTTON_WIDTH = 60;
	private final int LOGIN_BUTTON_HEIGHT = USERNAME_HEIGHT;
	
	private final int CANCEL_BUTTON_POSITION_X = PASSWORD_FIELD_POSITION_X + PASSWORD_FEILD_WIDTH + 10;
	private final int CANCEL_BUTTON_POSITION_Y = PASSWORD_POSITION_Y;
	private final int CANCEL_BUTTON_WIDTH = LOGIN_BUTTON_WIDTH;
	private final int CANCEL_BUTTON_HEIGHT = PASSWORD_HEIGHT;

	private JFrame jFrame;
	private JLabel imgLabel;
	private JPanel inputPanel;
	private JTextField nameField;
	private JPasswordField pwdField;
	
	public void execute() {
		jFrame = new JFrame(TITLE);
		jFrame.setLayout(null);		
		jFrame.setBounds(ScreenSizeUtils.HorizontalCenterPosition(WIDTH), ScreenSizeUtils.VerticalCenter(HEIGHT), WIDTH, HEIGHT);		
		insertBackgroundImg();
		createInputArea();
		createLoginButton();
		createCancelButton();
		jFrame.setVisible(true);	
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	private void insertBackgroundImg() {
		ImageIcon loginImg = new ImageIcon(BACKGROUND_URL);
		imgLabel = new JLabel(loginImg);
		imgLabel.setBounds(0, 0, loginImg.getIconWidth(), loginImg.getIconHeight());
		jFrame.add(imgLabel);
	}
	
	private void createInputArea() {
		inputPanel = new JPanel();
		inputPanel.setLayout(null);
		inputPanel.setOpaque(false);
		inputPanel.setBounds(INPUT_PANEL_POSITION_X, INPUT_PANEL_POSITION_Y, INPUT_PANEL_WIDTH, INPUT_PANEL_HEIGHT);
		
		
		JLabel nameLabel = new JLabel(USERNAME_LABEL_CONTENT);
		nameLabel.setBounds(USERNAME_LABEL_POSITION_X, USERNAME_POSITION_Y, USERNAME_LABEL_WIDTH, USERNAME_HEIGHT);
		nameField = new JTextField();
		nameField.setBounds(USERNAME_FIELD_POSITION_X, USERNAME_POSITION_Y, USERNAME_FEILD_WIDTH, USERNAME_HEIGHT);
		
		JLabel pwdLabel = new JLabel(PASSWORD_LABEL_CONTENT);
		pwdLabel.setBounds(PASSWORD_LABEL_POSITION_X,PASSWORD_POSITION_Y, PASSWORD_LABEL_WIDTH, PASSWORD_HEIGHT);
		pwdField = new JPasswordField();
		pwdField.setBounds(PASSWORD_FIELD_POSITION_X, PASSWORD_POSITION_Y, PASSWORD_FEILD_WIDTH, PASSWORD_HEIGHT);
		
		
		inputPanel.add(nameLabel);
		inputPanel.add(nameField);
		inputPanel.add(pwdLabel);
		inputPanel.add(pwdField);
		imgLabel.add(inputPanel);
	}
	
	
	private void createLoginButton() {
		JButton loginButton = new JButton(LOGIN_BUTTON_CONTENT);
		loginButton.setBounds(LOGIN_BUTTON_POSITION_X, LOGIN_BUTTON_POSITION_Y, LOGIN_BUTTON_WIDTH, LOGIN_BUTTON_HEIGHT);
		inputPanel.add(loginButton);
		loginButton.addActionListener(new LoginFrameLoginButtonListener(this));
	}
	
	private void createCancelButton() {
		JButton cancelButton = new JButton(CANCEL_BUTTON_CONTENT);
		cancelButton.setBounds(CANCEL_BUTTON_POSITION_X, CANCEL_BUTTON_POSITION_Y, CANCEL_BUTTON_WIDTH, CANCEL_BUTTON_HEIGHT);
		inputPanel.add(cancelButton);
		cancelButton.addActionListener(new LoginFrameCancelButtonListener());
	}


	public JTextField getNameField() {
		return nameField;
	}


	public JPasswordField getPwdField() {
		return pwdField;
	}
}
