package cn.kgc.frame;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import cn.kgc.model.Medicine;
import cn.kgc.model.MedicineType;
import cn.kgc.service.impl.MedicineServiceImpl;
import cn.kgc.service.impl.MedicineTypeServiceImpl;
import cn.kgc.service.intf.MedicineService;
import cn.kgc.service.intf.MedicineTypeService;
import cn.kgc.utils.FrameUtils;

public class MedicineTypeTreeFrame {
	private static MedicineTypeTreeFrame medicineTypeTreeFrame;
	MedicineTypeService medicineTypeService = new MedicineTypeServiceImpl();
	MedicineService medicineService = new MedicineServiceImpl();
	
	
	private MedicineTypeTreeFrame() {}
	
	public static MedicineTypeTreeFrame getInstance() {
		if(medicineTypeTreeFrame == null) {
			medicineTypeTreeFrame = new MedicineTypeTreeFrame ();
		}
		return medicineTypeTreeFrame;
	}


	public JTree regist(JScrollPane scrollPane) {
		return regist(scrollPane, false);
	}
	
	public JTree registWithMedicine(JScrollPane scrollPane) {
		return regist(scrollPane, true);
	}
	
	
	
	private JTree regist(JScrollPane scrollPane,boolean flag) {
		//½¨tree
		List<MedicineType> listArr;
		try {
			listArr = medicineTypeService.getAllInfoByParentId(null);
			if(listArr.size() != 0) {
				DefaultMutableTreeNode root = new DefaultMutableTreeNode(listArr.get(0));  
				JTree tree = new JTree(root);	
				List<MedicineType> listArrChild = medicineTypeService.getAllInfoByParentId(listArr.get(0).getId());
				addTreeNode(root,listArrChild,flag);
				
				scrollPane.getViewport().add(tree); 
				return tree;
			}				 
		} catch (Exception e) {
			FrameUtils.DialogErorr("´íÎó£¬" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	
	private void addTreeNode(DefaultMutableTreeNode treeNode, List<MedicineType> listArr,boolean flag) throws Exception {
		if(listArr.size() != 0) {			
			for (MedicineType medicineType : listArr) {
				DefaultMutableTreeNode treeNodeChild = new DefaultMutableTreeNode(medicineType);  
				treeNode.add(treeNodeChild);
				List<MedicineType> listArrChild = medicineTypeService.getAllInfoByParentId(medicineType.getId());
				addTreeNode(treeNodeChild,listArrChild,flag);
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
	
}
