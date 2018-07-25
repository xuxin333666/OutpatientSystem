package cn.kgc.frame.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import cn.kgc.model.MedicineType;

public class MedicineTypeDMLMenuListener implements ActionListener {

	 private JTree tree;  
	  
     public MedicineTypeDMLMenuListener(JTree tree) {  
         this.tree = tree;  
     }  

     public void actionPerformed(ActionEvent e) {  
         String name = JOptionPane.showInputDialog("请输入分类节点名称："); 
         
         DefaultMutableTreeNode parentTree = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
         MedicineType parentType = (MedicineType)parentTree.getUserObject();
         MedicineType type111 = new MedicineType("102",name,parentType);

         
         
         DefaultMutableTreeNode treenode = new DefaultMutableTreeNode(type111);  
         parentTree.add(treenode);  
         tree.expandPath(new TreePath(parentTree.getPath()));  
         tree.updateUI();  
     }  

}
