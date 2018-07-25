package cn.kgc.frame.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;



public class MedicineTreeMouseAdapter implements MouseListener {
	private JTree tree;
	
	public MedicineTreeMouseAdapter(JTree tree) {
		this.tree = tree;
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
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();//ѡ�еĽڵ�
		selectedNode.getUserObject();				
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
		addItem.addActionListener(new MedicineTypeDMLMenuListener(tree));
		JMenuItem delItem = new JMenuItem("ɾ��");  
		JMenuItem modifyItem = new JMenuItem("�޸�");  
		popMenu.add(addItem);  
		popMenu.add(delItem);  
		popMenu.add(modifyItem); 
		return popMenu;
	 } 
	
	 

}
