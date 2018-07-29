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
	private JTree tree;		//���ɵ����ؼ�
	private DefaultMutableTreeNode root;		//���ɵ������ڵ�
	private MedicineType rootType;		//���ڵ�����ҩƷ�������
	private DefaultMutableTreeNode selectedTree;		//ѡ�е����ڵ�
	private MedicineType selectedtype;		//ѡ�е����ڵ�����ҩƷ�������
	private boolean flag;		//�Ƿ����Ҷ�ӽڵ��ϵ�ҩƷ����
	MedicineTypeService medicineTypeService = new MedicineTypeServiceImpl();		//ҩƷ����service����
	MedicineService medicineService = new MedicineServiceImpl();		//ҩƷservice����
	
	/**
	 * ���췽������ȡ���ڵ����ݣ�����jtree
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
			FrameUtils.DialogErorr("����" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * ע���ڸ�����scrollpane�ϣ��Ҳ�����Ҷ�ӽڵ��ϵ�ҩƷ����
	 * @param scrollPane
	 */
	public void regist(JScrollPane scrollPane) {
		flag = false;
		registBoth(scrollPane);
	}
	
	/**
	 * ע���ڸ�����scrollpane�ϣ�����Ҷ�ӽڵ��ϵ�ҩƷ����
	 * @param scrollPane
	 */
	public void registWithMedicine(JScrollPane scrollPane) {
		flag = true;
		registBoth(scrollPane);
	}
	
	/**
	 * ע���ڸ�����scrollpane�ϣ��ж��Ƿ����Ҷ�ӽڵ��ϵ�ҩƷ����
	 * @param scrollPane
	 */	
	private void registBoth(JScrollPane scrollPane) {
		//��tree
		try {
			if(rootType != null) {
				List<MedicineType> listArrChild = medicineTypeService.getAllInfoByParentId(rootType.getId());
				addTreeNode(root,listArrChild);
				
				scrollPane.getViewport().add(tree); 
			}				 
		} catch (Exception e) {
			FrameUtils.DialogErorr("����" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * ˢ�����ؼ�
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
	 * �ݹ鷽����������ӽڵ�
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
	 * ��jtree�����������ķ���
	 * @param mouseAdapter
	 */
	public void addMouseListener(MouseListener mouseAdapter) {
		tree.addMouseListener(mouseAdapter);	
	}

	/**
	 * �������ɵ�jtree
	 * @return
	 */
	public JTree getTree() {
		return tree;
	}
	
}
