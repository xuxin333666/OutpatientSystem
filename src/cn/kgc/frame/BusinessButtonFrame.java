package cn.kgc.frame;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import cn.kgc.frame.listener.BusinessButtonListener;
import cn.kgc.utils.BusinessButtonUtils;
import cn.kgc.utils.ScreenSizeUtils;

public class BusinessButtonFrame {
	private final int MENUBAR_HEIGHT = 23;
	private final int BUTTONPANEL_HEIGHT = 55;
	private final int BUTTON_WIDTH = 55;
	
	
	
	private List<String> BusinessButtonUrlSet = BusinessButtonUtils.getList();
	private JPanel servicePanel;
	private JPanel buttonPanel = new JPanel();
	
	public BusinessButtonFrame(JPanel servicePanel) {
		this.servicePanel = servicePanel;
	}

	public void execute() {	
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(null);
		buttonPanel.setBounds(0, MENUBAR_HEIGHT, ScreenSizeUtils.screenWidth, BUTTONPANEL_HEIGHT);		
		servicePanel.add(buttonPanel);
		addButton();
	}

	private void addButton() {
		int positionX = 0;
		for (String string : BusinessButtonUrlSet) {
			ImageIcon img = new ImageIcon(string);
			img.setImage(img.getImage().getScaledInstance(BUTTON_WIDTH, BUTTONPANEL_HEIGHT,Image.SCALE_DEFAULT ));
			JButton button = new JButton(img);
			button.setBounds(positionX, 0, BUTTON_WIDTH, BUTTONPANEL_HEIGHT);
			button.setBorderPainted(false);
			positionX += BUTTON_WIDTH;
			buttonPanel.add(button);
			
			button.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseEntered(java.awt.event.MouseEvent evt) {
	            	button.setBorderPainted(true);
	            }
	            public void mouseExited(java.awt.event.MouseEvent evt) {
	            	button.setBorderPainted(false);
	            }
	        });
			
			button.addActionListener(new BusinessButtonListener(string,this));
		}
		
	}
	
	
}
