package cn.kgc.frame.listener.medicineListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import cn.kgc.dao.impl.MedicineTypeDaoImpl;
import cn.kgc.dao.intf.MedicineTypeDao;
import cn.kgc.model.Medicine;
import cn.kgc.model.MedicineType;
import cn.kgc.service.impl.MedicineServiceImpl;
import cn.kgc.service.impl.MedicineTypeServiceImpl;
import cn.kgc.service.intf.MedicineService;
import cn.kgc.service.intf.MedicineTypeService;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.MyMedicineTypeTreeFrame;

public class MedicineTypeDMLMenuListener implements ActionListener {

	private static final String ADD_TREE_NODE_ERORR = "保存失败";
	private static final String NODE_NAME_IS_EMPTY = "药品类目名不能为空";
	private static final String ADD_IS_NOT_EMPTY = "不能增加，该节点含有药品";
	private static final Object CONFIRM_DELETE_MESSAGE = "是否确认删除？";
	private static final String CONFIRM_DELETE_TITLE = "删除";
	private static final String DELETE_IS_NOT_EMPTY = "不能删除，该节点含有其他子节点或药品";
	
	private MedicineTypeService medicineTypeService = new MedicineTypeServiceImpl();
	
	private MyMedicineTypeTreeFrame treeFrame;  
	private MedicineType selectedType;
	  
     public MedicineTypeDMLMenuListener(MyMedicineTypeTreeFrame treeFrame) {  
         this.treeFrame = treeFrame;  
     }  

     public void actionPerformed(ActionEvent e) {  
    	 JMenuItem item = (JMenuItem)e.getSource();
    	 int status = 1;
		 try {
			 DefaultMutableTreeNode selectedTree = (DefaultMutableTreeNode) treeFrame.getTree().getLastSelectedPathComponent();
			 selectedType = (MedicineType)selectedTree.getUserObject();
	    	 if("add".equals(item.getName())) {
	    		 status = add(selectedTree);
	    	 } else if("modify".equals(item.getName())) {
	    		 status = modify(selectedTree);
	    	 } else if("delete".equals(item.getName())) {
	    		 status = delete(selectedTree);
	    	 } 
	         FrameUtils.statusInfo(status, null, ADD_TREE_NODE_ERORR); 
	        treeFrame.refreshTree(selectedTree);
		 } catch (Exception e1) {
			 FrameUtils.DialogErorr(e1.getMessage());
			 e1.printStackTrace();
		}
     }


	private int add(DefaultMutableTreeNode parentTree) throws Exception {
		if(isMedicineExist()) {
			throw new Exception(ADD_IS_NOT_EMPTY);
		}
        String name = JOptionPane.showInputDialog("请输入分类节点名称："); 
        if(name == null) {
        	return 1;      	
        } else if(name.equals("")) {
        	throw new Exception(NODE_NAME_IS_EMPTY);
        }    
        MedicineTypeDao medicineTypeDao = new MedicineTypeDaoImpl();
        MedicineType type = new MedicineType(medicineTypeDao.queryMinEmptyId(),name,selectedType);
        int status = medicineTypeService.addTypeNode(type);
        if(status > 0) {
        	DefaultMutableTreeNode node = new DefaultMutableTreeNode(type);
        	parentTree.add(node);
        }
        return status;
	}  
	
	private int modify(DefaultMutableTreeNode selectedTree) throws Exception {
		String name = JOptionPane.showInputDialog("请输入修改后的名称："); 
        if(name == null) {
        	return 1;      	
        } else if(name.equals("")) {
        	throw new Exception(NODE_NAME_IS_EMPTY);
        }		
		
        selectedType.setName(name);   
        return medicineTypeService.modifyTypeNode(selectedType);

		
	}
	
	private int delete(DefaultMutableTreeNode selectedTree) throws Exception {
		if(selectedTree.getChildCount() != 0 || isMedicineExist()) {
			throw new Exception(DELETE_IS_NOT_EMPTY);
		};
		int result = JOptionPane.showConfirmDialog(null, CONFIRM_DELETE_MESSAGE, CONFIRM_DELETE_TITLE, JOptionPane.WARNING_MESSAGE);
		if(result != 0) {
			return 1;
		}
		int status = medicineTypeService.deleteTypeNode(selectedType);
		if(status > 0) {
			DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)selectedTree.getParent();
			for(int i=0;i<parentNode.getLeafCount();i++) {
				DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)parentNode.getChildAt(i);
				if(selectedTree.equals(childNode)) {
					parentNode.remove(i);
					break;
				}
			}
			
		}
		return status;
	}
	
	private boolean isMedicineExist() throws Exception {
		MedicineService medicineDao = new MedicineServiceImpl();
		List<Medicine> medicine = medicineDao.getAllInfoByTypeId(selectedType.getId());
		if(medicine.size() == 0) {
			return false;
		}
		return true;
	}

}
