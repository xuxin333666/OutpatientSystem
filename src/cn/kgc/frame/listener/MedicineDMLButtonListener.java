package cn.kgc.frame.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import cn.kgc.frame.MedicineFrame;
import cn.kgc.frame.listener.BaseDMLButtonListener;


public class MedicineDMLButtonListener extends BaseDMLButtonListener implements ActionListener {

	
	
	public MedicineDMLButtonListener(MedicineFrame medicineFrame) {
		super("./MedicineDMLButton.properties");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(JButton button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modify(JButton button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(JButton button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(JButton button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo(JButton button) {
		// TODO Auto-generated method stub
		
	}

}
