package cn.kgc.frame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import cn.kgc.utils.ScreenSizeUtils;

public class MainMenuBarFrame {
	private final int MENUBAR_HEIGHT = 23;
	private final String[] MENU_LIST = {"�����շѹ���S��","������������T��","ҩ������U��","���¹���V��","�������W��","��ѯ����X��","��Ϣά������Y��","������Z��"};
	
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
