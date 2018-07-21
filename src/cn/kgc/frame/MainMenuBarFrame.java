package cn.kgc.frame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import cn.kgc.utils.ScreenSizeUtils;

public class MainMenuBarFrame {
	private final int MENUBAR_HEIGHT = 23;
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
