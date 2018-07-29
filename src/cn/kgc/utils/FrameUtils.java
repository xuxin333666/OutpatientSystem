package cn.kgc.utils;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;






public class FrameUtils {
	
	public static JButton addButton(String imgUrl,int positionX ,int width,int height,JPanel parentPanel) {
		ImageIcon img = new ImageIcon(imgUrl);
		img.setImage(img.getImage().getScaledInstance(width-5,height-5,Image.SCALE_DEFAULT ));
		JButton button = new JButton(img);
		button.setBounds(positionX, 0,width, height);
		button.setBackground(Color.WHITE);
		button.setBorderPainted(false);

		parentPanel.add(button);
		buttonAddMouseListener(button);
		
		
		return button;		
	}	
	
	public static void buttonAddMouseListener(JButton button) {
		button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	if(button.isEnabled()) {
            		button.setBorderPainted(true);          		
            	}
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	button.setBorderPainted(false);
            }
        });
	}
	
	public static void buttonRemoveMouseListener(JButton button) {
		MouseListener[] mListeners = button.getMouseListeners();
		for(int j=0;j<mListeners.length;j++) {
			button.removeMouseListener(mListeners[j]);					
		}
	}
	
	public static void statusInfo(int status,String succuss,String erorr) throws Exception {
		if(status <= 0) {
			throw new Exception(erorr);
		} else {
			if(StringUtils.isNotEmpty(succuss)) {
				JOptionPane.showMessageDialog(null, succuss, "³É¹¦", JOptionPane.INFORMATION_MESSAGE);			
			}
		}
	}
	
	public static void DialogErorr(String msg) {
		JOptionPane.showMessageDialog(null, msg, "´íÎó", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void object2Component(Object obj,List<JComponent> fields,int AttributeStart) throws IllegalArgumentException, IllegalAccessException {
		Field[] attributes = obj.getClass().getDeclaredFields();
		for (int i=0;i<fields.size();i++,AttributeStart++) {
			attributes[AttributeStart].setAccessible(true);
			Object value = attributes[AttributeStart].get(obj);
			if(value == null) {
				continue;
			}
			JComponent field = fields.get(i);
			if(field instanceof JTextField) {
				JTextField textField = (JTextField)field;
				textField.setText(value.toString());
			} else if(field instanceof JTextArea) {
				JTextArea textArea = (JTextArea)field;
				textArea.setText(value.toString());
			} else if(field instanceof JComboBox<?>) {
				JComboBox<?> combo = (JComboBox<?>)field;
				combo.setSelectedIndex(Integer.parseInt(value.toString()));
			}
		}
	}
	
	public static void object2Component(Object obj,List<JComponent> fields) throws IllegalArgumentException, IllegalAccessException {
		object2Component(obj,fields,0);
	}

	public static void fieldsEnable(List<JComponent> fields) {
		for (JComponent jComponent : fields) {
			jComponent.setEnabled(true);
		}
	}
	
	public static void fieldsDisable(List<JComponent> fields) {
		for (JComponent jComponent : fields) {
			jComponent.setEnabled(false);
		}
	}
	
}
