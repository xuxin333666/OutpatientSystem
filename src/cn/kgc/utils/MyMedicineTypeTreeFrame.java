package cn.kgc.utils;

import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import cn.kgc.model.Medicine;
import cn.kgc.model.MedicineType;
import cn.kgc.service.impl.MedicineServiceImpl;
import cn.kgc.service.impl.MedicineTypeServiceImpl;
import cn.kgc.service.intf.MedicineService;
import cn.kgc.service.intf.MedicineTypeService;

public class MyMedicineTypeTreeFrame {
	private JTree tree;
	private DefaultMutableTreeNode root;
	private MedicineType rootType;
	private DefaultMutableTreeNode selectedTree;
	private MedicineType selectedtype;
	private boolean flag;
	MedicineTypeService medicineTypeService = new MedicineTypeServiceImpl();
	MedicineService medicineService = new MedicineServiceImpl();
	
	
	public MyMedicineTypeTreeFrame() {
		List<MedicineType> listArr;
		try {
			listArr = medicineTypeService.getAllInfoByParentId(null);
			if(listArr.size() != 0) {
				rootType = listArr.get(0);
				root = new DefaultMutableTreeNode(rootType);  
				tree = new JTree(root);
			}	
		} catch (Exception e) {
			FrameUtils.DialogErorr("´íÎó£¬" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void regist(JScrollPane scrollPane) {
		flag = false;
		registBoth(scrollPane);
	}
	
	public void registWithMedicine(JScrollPane scrollPane) {
		flag = true;
		registBoth(scrollPane);
	}
	
	
	
	private void registBoth(JScrollPane scrollPane) {
		//½¨tree
		try {
			if(rootType != null) {
				List<MedicineType> listArrChild = medicineTypeService.getAllInfoByParentId(rootType.getId());
				addTreeNode(root,listArrChild);
				
				scrollPane.getViewport().add(tree); 
			}				 
		} catch (Exception e) {
			FrameUtils.DialogErorr("´íÎó£¬" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void refreshTree(DefaultMutableTreeNode selectedTree) throws Exception {
		this.selectedTree = selectedTree;
		selectedtype = (MedicineType)selectedTree.getUserObject();
		root.removeAllChildren();
		List<MedicineType> listArrChild = medicineTypeService.getAllInfoByParentId(rootType.getId());
		addTreeNode(root,listArrChild);
		if(this.selectedTree.getParent() != null) {
			this.selectedTree = (DefaultMutableTreeNode)(this.selectedTree.getParent());
		}
		TreePath path = new TreePath(this.selectedTree.getPath());
		tree.expandPath(path);  
        tree.updateUI(); 
	}
	
	
	private void addTreeNode(DefaultMutableTreeNode treeNode, List<MedicineType> listArr) throws Exception {
		if(listArr.size() != 0) {			
			for (MedicineType medicineType : listArr) {
				DefaultMutableTreeNode treeNodeChild = new DefaultMutableTreeNode(medicineType);  
				treeNode.add(treeNodeChild);
				if(medicineType.equals(selectedtype)) {
					selectedTree = treeNodeChild;					
				}
				List<MedicineType> listArrChild = medicineTypeService.getAllInfoByParentId(medicineType.getId());
				addTreeNode(treeNodeChild,listArrChild);				

			}
		} else {
			if(flag) {
				MedicineType medicineType = (MedicineType)treeNode.getUserObject();
				String typeId = medicineType.getId();
				List<Medicine> medicines = medicineService.getAllInfoByTypeId(typeId);
				for (Medicine medicine : medicines) {
					DefaultMutableTreeNode treeNodeChild = new DefaultMutableTreeNode(medicine);  
					treeNode.add(treeNodeChild);
				}				
			}
			
		}
	}

	public void addMouseListener(MouseListener mouseAdapter) {
		tree.addMouseListener(mouseAdapter);	
	}

	public JTree getTree() {
		return tree;
	}
	
}
