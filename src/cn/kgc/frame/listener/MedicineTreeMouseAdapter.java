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
		TreePath path = tree.getPathForLocation(e.getX(), e.getY()); // 关键是这个方法的使用  
        if (path == null) {  
            return;  
        }  
        tree.setSelectionPath(path);
        if (e.getButton() == 3) {//点击的是右键
        	
        	createPopMenu().show(tree, e.getX(), e.getY());  
        } 
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();//选中的节点
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
		JMenuItem addItem = new JMenuItem("添加"); 
		addItem.addActionListener(new MedicineTypeDMLMenuListener(tree));
		JMenuItem delItem = new JMenuItem("删除");  
		JMenuItem modifyItem = new JMenuItem("修改");  
		popMenu.add(addItem);  
		popMenu.add(delItem);  
		popMenu.add(modifyItem); 
		return popMenu;
	 } 
	
	 

}
