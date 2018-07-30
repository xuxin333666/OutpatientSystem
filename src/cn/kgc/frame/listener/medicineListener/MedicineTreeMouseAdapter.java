package cn.kgc.frame.listener.medicineListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import cn.kgc.frame.MedicineFrame;
import cn.kgc.frame.PrescriptionFrame;
import cn.kgc.model.Medicine;
import cn.kgc.model.MedicineType;
import cn.kgc.model.PrescriptionMedicine;
import cn.kgc.service.impl.PrescriptionMedicineServiceImpl;
import cn.kgc.service.intf.PrescriptionMedicineService;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.MyMedicineTypeTreeFrame;
import cn.kgc.utils.StringUtils;



public class MedicineTreeMouseAdapter implements MouseListener {
	private MedicineFrame medicineFrame;
	private PrescriptionFrame prescriptionFrame;
	private JTree tree;
	private MyMedicineTypeTreeFrame treeFrame;
	
	public MedicineTreeMouseAdapter(MedicineFrame medicineFrame) {
		this.medicineFrame = medicineFrame;
		this.treeFrame = medicineFrame.getMedicineTypeTreeFrame();
		try {
			this.tree = treeFrame.getTree();
		} catch (Exception e) {
			 FrameUtils.DialogErorr(e.getMessage());
			e.printStackTrace();
		}
	}

	public MedicineTreeMouseAdapter(PrescriptionFrame prescriptionFrame) {
		this.prescriptionFrame = prescriptionFrame;
		this.treeFrame = prescriptionFrame.getMedicineTypeTreeFrame();
		try {
			this.tree = treeFrame.getTree();
		} catch (Exception e) {
			 FrameUtils.DialogErorr(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		TreePath path = tree.getPathForLocation(e.getX(), e.getY()); // 关键是这个方法的使用  
        if (path == null) {  
            return;  
        }  
        tree.setSelectionPath(path);
        if (e.getButton() == 3) {//点击的是右键
        	DefaultMutableTreeNode selectedTree = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
			Object type = selectedTree.getUserObject();
			if(type instanceof MedicineType) {
				createPopMenu().show(tree, e.getX(), e.getY());  				
			}
        } 
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		DefaultMutableTreeNode selectedTree = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		Object obj = selectedTree.getUserObject();
		if(medicineFrame != null) {
			MedicineType type = (MedicineType)obj;
			String typeId = type.getId();
			medicineFrame.getMedicineTableFrame().getDataAndRefreshTable(typeId);			
		} else if(prescriptionFrame != null) {
			if(obj instanceof Medicine) {
				Medicine type = (Medicine)obj;
				prescriptionFrame.getPrescriptionMedicineTableFrame().getDataAndRefreshTable(type.getId());
				if(e.getClickCount() == 2) {
					List<JComponent> medicineFields = prescriptionFrame.getMedicineFields();
					List<PrescriptionMedicine> prescriptionMedicines = prescriptionFrame.getBufferPrescriptionMedicine();
					JTextField medicineField = (JTextField)medicineFields.get(prescriptionMedicines.size());
					medicineField.setText(type.toString());
					JTextField idField = (JTextField)prescriptionFrame.getPrescriptionFields().get(0);
					PrescriptionMedicineService prescriptionMedicineService = new PrescriptionMedicineServiceImpl();
					String id = null;
					try {
						id = prescriptionMedicineService.getMinEmptyId();
						int idInt = Integer.parseInt(id) + prescriptionMedicines.size();
						PrescriptionMedicine pm = new PrescriptionMedicine(String.valueOf(idInt),idField.getText(), type.getId(), null);
						prescriptionMedicines.add(pm);
						medicineField.setEnabled(false);
					} catch (Exception e1) {
						FrameUtils.DialogErorr("错误，" + e1.getMessage());
						e1.printStackTrace();
					}
					
					List<JComponent> prescriptionFields = prescriptionFrame.getPrescriptionFields();
					JTextField priceField = (JTextField)prescriptionFields.get(prescriptionFields.size() - 1);
					if(StringUtils.isEmpty(priceField.getText())) {
						priceField.setText(type.getPrice().toString());
					} else {
						Double sumPrice = Double.parseDouble(priceField.getText()) + type.getPrice();
						priceField.setText(sumPrice.toString());
					}				
				}					
			}
		}
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
		addItem.setName("add");
		addItem.addActionListener(new MedicineTypeDMLMenuListener(treeFrame));
		
		JMenuItem delItem = new JMenuItem("删除");  
		delItem.setName("delete");
		delItem.addActionListener(new MedicineTypeDMLMenuListener(treeFrame));
		
		JMenuItem modifyItem = new JMenuItem("修改");  
		modifyItem.setName("modify");
		modifyItem.addActionListener(new MedicineTypeDMLMenuListener(treeFrame));
		
		
		popMenu.add(addItem);  
		popMenu.add(delItem);  
		popMenu.add(modifyItem); 
		return popMenu;
	 } 
	
	 

}
