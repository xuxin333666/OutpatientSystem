package cn.kgc.frame;


import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import cn.kgc.frame.listener.BusinessButtonListener;
import cn.kgc.frame.listener.loginListener.ModifyPswButtonListener;
import cn.kgc.model.User;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.ScreenSizeUtils;

public class MainFrame {
	private final String TITLE = "门 诊 管 理 系 统";
	private final String BACKGROUND_URL = "./img/1700.jpg";
	private final String STATUS_EXCEPTION = "账号状态异常，请联系管理员";
	private final String MODIFY_PASSWORD_LABEL = "请修改密码后使用：";
	private final String MODIFY_PASSWORD_BUTTON = "确认修改";
	private final String ENABLE = "00";
	private final String DISABLE = "01";
	private final int MODIFYPWD_LABEL_POSITION_X = 0;
	private final int MODIFYPWD_LABEL_POSITION_Y = 100;
	private final int MODIFYPWD_LABEL_WIDTH = 120;
	private final int MODIFYPWD_LABEL_HEIGHT = 25;
	private final int MODIFYPWD_FIELD_WIDTH = 150;
	private final int MODIFYPWD_FIELD_HEIGHT = MODIFYPWD_LABEL_HEIGHT;
	private final int MODIFYPWD_BUTTON_WIDTH = 100;
	private final int MODIFYPWD_BUTTON_HEIGHT = MODIFYPWD_LABEL_HEIGHT;
	private final int DEFAULT_DISTANCE = 10;
	private final int MODIFYPWD_PANEL_WIDTH = MODIFYPWD_LABEL_WIDTH+MODIFYPWD_FIELD_WIDTH+MODIFYPWD_BUTTON_WIDTH + 2*DEFAULT_DISTANCE;
	private final int MODIFYPWD_PANEL_HEIGHT = ScreenSizeUtils.screenHeight;
	
	private final int MENUBAR_HEIGHT = 23;
	
	private static MainFrame mainFrame;
	private User user;
	private JFrame jFrame = new JFrame(TITLE);
	private JLabel imgLabel;
	private MainMenuBarFrame mainMenuBarFrame;
	private BusinessButtonFrame businessButtonFrame;
	private JPanel modifyPwdPanel = new JPanel();
	private JPanel servicePanel = new JPanel();

	private MainFrame() {};
	
	public static MainFrame getInstance() {
		if(mainFrame == null) {
			mainFrame = new MainFrame();
		}		
		return mainFrame;
	}
	
	public void execute() {
		execute(user);
	}
	
	public void execute(User user) {
		this.user = user;
		jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		jFrame.setLayout(null);
		if(ENABLE.equals(user.getStatus())) {
			servicePanel();
		} else if(DISABLE.equals(user.getStatus())) {
			modifyPwdPanel();		
		} else {
			FrameUtils.DialogErorr(STATUS_EXCEPTION);
		}
		jFrame.setVisible(true);	
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void modifyPwdPanel() {
		modifyPwdPanel.setLayout(null);
		modifyPwdPanel.setBounds(ScreenSizeUtils.HorizontalCenterPosition(MODIFYPWD_PANEL_WIDTH), 0, MODIFYPWD_PANEL_WIDTH, MODIFYPWD_PANEL_HEIGHT);
		
		JLabel label = new JLabel(MODIFY_PASSWORD_LABEL);
		label.setBounds(MODIFYPWD_LABEL_POSITION_X, MODIFYPWD_LABEL_POSITION_Y, MODIFYPWD_LABEL_WIDTH, MODIFYPWD_LABEL_HEIGHT);
		
		JPasswordField newPwdField = new JPasswordField();
		newPwdField.setBounds(label.getX()+label.getWidth()+DEFAULT_DISTANCE, label.getY(), MODIFYPWD_FIELD_WIDTH, MODIFYPWD_FIELD_HEIGHT);
		
		JButton confirmButton = new JButton(MODIFY_PASSWORD_BUTTON);
		confirmButton.setBounds(newPwdField.getX()+newPwdField.getWidth()+DEFAULT_DISTANCE, newPwdField.getY(), MODIFYPWD_BUTTON_WIDTH, MODIFYPWD_BUTTON_HEIGHT);
		
		confirmButton.addActionListener(new ModifyPswButtonListener(newPwdField));
		
		modifyPwdPanel.add(label);
		modifyPwdPanel.add(newPwdField);
		modifyPwdPanel.add(confirmButton);
		jFrame.add(modifyPwdPanel);
	}
	
	private void servicePanel() {
		modifyPwdPanel.setVisible(false);
		settingServicePanel();
		insertBackgroundImg();
		createMenuBar();	
		createBusinessButton();
	}
	

	private void settingServicePanel() {
		servicePanel.setLayout(null);
		servicePanel.setOpaque(false);
		servicePanel.setBounds(0, 0,ScreenSizeUtils.screenWidth, ScreenSizeUtils.screenHeight);
		jFrame.add(servicePanel);
	}
	
	private void insertBackgroundImg() {
		ImageIcon mainBackgroundImg = new ImageIcon(BACKGROUND_URL);
		mainBackgroundImg.setImage(mainBackgroundImg.getImage().getScaledInstance(ScreenSizeUtils.screenWidth, ScreenSizeUtils.screenHeight,Image.SCALE_DEFAULT ));
		imgLabel = new JLabel(mainBackgroundImg);
		imgLabel.setLayout(null);
		imgLabel.setBounds(0, 0,ScreenSizeUtils.screenWidth, ScreenSizeUtils.screenHeight);
		jFrame.add(imgLabel);
	}

	private void createMenuBar() {
		mainMenuBarFrame = new MainMenuBarFrame(servicePanel);
		mainMenuBarFrame.execute();	
	}
	
	private void createBusinessButton() {
		businessButtonFrame = new BusinessButtonFrame(servicePanel);
		businessButtonFrame.execute();	
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	public class MainMenuBarFrame {
		private final String[] MENU_LIST = {"划价收费管理（S）","处方病例管理（T）","药房管理（U）","人事管理（V）","财务管理（W）","查询报表（X）","信息维护管理（Y）","帮助（Z）"};
		
		private JPanel servicePanel;
		private JMenuBar menuBar = new JMenuBar();
		
		public MainMenuBarFrame(JPanel servicePanel) {
			this.servicePanel = servicePanel;
		}
		
		public void execute() {	
			menuBar.setBounds(0, 0, ScreenSizeUtils.screenWidth, MENUBAR_HEIGHT);
			addMenu();
			servicePanel.add(menuBar);
			
		}
		
		private void addMenu() {
			for (String string : MENU_LIST) {
				JMenu menu = new JMenu(string);
				menuBar.add(menu);
			}
		}

		public JMenuBar getMenuBar() {
			return menuBar;
		}

	}
	
	
	public class BusinessButtonFrame {
		private final int BUSINESS_BUTTON_PANEL_HEIGHT = 70;
		private final int BUSINESS_BUTTON_WIDTH = 70;
		
		
		
		private String[] BusinessButtonUrlList = {"./img/tool_img01.PNG","./img/tool_img02.PNG","./img/tool_img03.PNG"
				,"./img/tool_img04.PNG","./img/tool_img05.PNG","./img/tool_img06.PNG","./img/tool_img07.PNG","./img/tool_img08.PNG"
				,"./img/tool_img09.PNG","./img/tool_img10.PNG","./img/tool_img11.PNG"};
		private String[] BusinessButtonNameList = {"charge","consult","case","health","analysis","remind",
				"medicine","financial","shift","lock","exit"};
		private JPanel servicePanel;
		private JPanel buttonPanel = new JPanel();
		
		public BusinessButtonFrame(JPanel servicePanel) {
			this.servicePanel = servicePanel;
		}

		public void execute() {	
			buttonPanel.setOpaque(false);
			buttonPanel.setLayout(null);
			buttonPanel.setBounds(0, MENUBAR_HEIGHT, ScreenSizeUtils.screenWidth, BUSINESS_BUTTON_PANEL_HEIGHT);		
			servicePanel.add(buttonPanel);
			addButton();
		}

		private void addButton() {
			int positionX = 0;
			for (int i=0;i<BusinessButtonUrlList.length;i++) {
				JButton button = FrameUtils.addButton(BusinessButtonUrlList[i], positionX, BUSINESS_BUTTON_WIDTH, BUSINESS_BUTTON_PANEL_HEIGHT, buttonPanel);
				button.setName(BusinessButtonNameList[i]);
				button.addActionListener(new BusinessButtonListener());
				positionX += BUSINESS_BUTTON_WIDTH;
			}
		}
		
		
	}

}
