package cn.kgc.utils;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;




public class FrameUtils {
	
	
	public static JButton addButton(String imgUrl,int positionX ,int width,int height,JPanel parentPanel) {
		ImageIcon img = new ImageIcon(imgUrl);
		img.setImage(img.getImage().getScaledInstance(width-10,height-10,Image.SCALE_DEFAULT ));
		JButton button = new JButton(img);
		button.setBounds(positionX, 0,width, height);
		button.setBackground(Color.WHITE);
		button.setBorderPainted(false);

		parentPanel.add(button);
		
		button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	button.setBorderPainted(true);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	button.setBorderPainted(false);
            }
        });
		
		return button;		
	}	
	
	public static void statusInfo(int status,String succuss,String erorr) {
		if(status <= 0) {
			JOptionPane.showMessageDialog(null, erorr, "´íÎó", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, succuss, "³É¹¦", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public static void DialogErorr(String msg) {
		JOptionPane.showMessageDialog(null, msg, "´íÎó", JOptionPane.ERROR_MESSAGE);
	}
}
