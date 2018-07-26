package cn.kgc.frame.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.tree.DefaultMutableTreeNode;

import cn.kgc.dto.MedicineDto;
import cn.kgc.frame.MedicineFrame;
import cn.kgc.model.MedicineType;
import cn.kgc.service.impl.MedicineServiceImpl;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.StringUtils;

public class MedicineSearchKeyListener implements KeyListener {
	private MedicineFrame medicineFrame;
	private String key = "";
	
	public MedicineSearchKeyListener(MedicineFrame medicineFrame) {
		this.medicineFrame = medicineFrame;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		String args = medicineFrame.getMedicineSearchField().getText();
		if(args.equals(key)) {
			return;
		}
		key = args;
		
		Object dto = null;
		if(StringUtils.isEmpty(key)) {
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)medicineFrame.getTree().getLastSelectedPathComponent();
			if(treeNode == null) {
				FrameUtils.getDataAndRefreshTable(medicineFrame.getMedicineTable(), MedicineServiceImpl.class);
				return;
			} else {
				dto = ((MedicineType)treeNode.getUserObject()).getId();
			}
		} else {
			dto = new MedicineDto("%" + key + "%");				
		}
		FrameUtils.getDataAndRefreshTableBySearch(medicineFrame.getMedicineTable(), MedicineServiceImpl.class,dto);
	}

}