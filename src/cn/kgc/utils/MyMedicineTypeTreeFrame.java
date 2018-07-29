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
	private JTree tree;		//生成的树控件
	private DefaultMutableTreeNode root;		//生成的树根节点
	private MedicineType rootType;		//根节点放入的药品分类对象
	private DefaultMutableTreeNode selectedTree;		//选中的树节点
	private MedicineType selectedtype;		//选中的树节点放入的药品分类对象
	private boolean flag;		//是否加载叶子节点上的药品对象
	MedicineTypeService medicineTypeService = new MedicineTypeServiceImpl();		//药品分类service对象
	MedicineService medicineService = new MedicineServiceImpl();		//药品service对象
	
	/**
	 * 构造方法，获取根节点数据，生成jtree
	 */
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
			FrameUtils.DialogErorr("错误，" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 注册在给定的scrollpane上，且不加载叶子节点上的药品对象
	 * @param scrollPane
	 */
	public void regist(JScrollPane scrollPane) {
		flag = false;
		registBoth(scrollPane);
	}
	
	/**
	 * 注册在给定的scrollpane上，加载叶子节点上的药品对象
	 * @param scrollPane
	 */
	public void registWithMedicine(JScrollPane scrollPane) {
		flag = true;
		registBoth(scrollPane);
	}
	
	/**
	 * 注册在给定的scrollpane上，判断是否加载叶子节点上的药品对象
	 * @param scrollPane
	 */	
	private void registBoth(JScrollPane scrollPane) {
		//建tree
		try {
			if(rootType != null) {
				List<MedicineType> listArrChild = medicineTypeService.getAllInfoByParentId(rootType.getId());
				addTreeNode(root,listArrChild);
				
				scrollPane.getViewport().add(tree); 
			}				 
		} catch (Exception e) {
			FrameUtils.DialogErorr("错误，" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 刷新树控件
	 * @param selectedTree
	 * @throws Exception
	 */
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
	
	/**
	 * 递归方法，用来添加节点
	 * @param treeNode
	 * @param listArr
	 * @throws Exception
	 */
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

	/**
	 * 给jtree增加鼠标监听的方法
	 * @param mouseAdapter
	 */
	public void addMouseListener(MouseListener mouseAdapter) {
		tree.addMouseListener(mouseAdapter);	
	}

	/**
	 * 返回生成的jtree
	 * @return
	 */
	public JTree getTree() {
		return tree;
	}
	
}
