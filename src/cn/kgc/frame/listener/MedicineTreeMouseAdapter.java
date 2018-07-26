package cn.kgc.frame.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import cn.kgc.frame.MedicineFrame;
import cn.kgc.model.MedicineType;
import cn.kgc.service.impl.MedicineServiceImpl;
import cn.kgc.utils.FrameUtils;



public class MedicineTreeMouseAdapter implements MouseListener {
	private MedicineFrame medicineFrame;
	private JTree tree;
	
	public MedicineTreeMouseAdapter(MedicineFrame medicineFrame) {
		this.medicineFrame = medicineFrame;
		this.tree = medicineFrame.getTree();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		TreePath path = tree.getPathForLocation(e.getX(), e.getY()); // �ؼ������������ʹ��  
        if (path == null) {  
            return;  
        }  
        tree.setSelectionPath(path);
        if (e.getButton() == 3) {//��������Ҽ�
        	
        	createPopMenu().show(tree, e.getX(), e.getY());  
        } 
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		DefaultMutableTreeNode selectedTree = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		MedicineType type = (MedicineType)selectedTree.getUserObject();
		String typeId = type.getId();
		FrameUtils.getDataAndRefreshTableBySearch(medicineFrame.getMedicineTable(), MedicineServiceImpl.class, typeId);
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	private JPopupMenu createPopMenu() {  
		JPopupMenu popMenu = new JPopupMenu(); 
		
		JMenuItem addItem = new JMenuItem("���"); 
		addItem.setName("add");
		addItem.addActionListener(new MedicineTypeDMLMenuListener(tree));
		
		JMenuItem delItem = new JMenuItem("ɾ��");  
		delItem.setName("delete");
		delItem.addActionListener(new MedicineTypeDMLMenuListener(tree));
		
		JMenuItem modifyItem = new JMenuItem("�޸�");  
		modifyItem.setName("modify");
		modifyItem.addActionListener(new MedicineTypeDMLMenuListener(tree));
		
		
		popMenu.add(addItem);  
		popMenu.add(delItem);  
		popMenu.add(modifyItem); 
		return popMenu;
	 } 
	
	 

}
