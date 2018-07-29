package cn.kgc.frame.listener.medicineListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import cn.kgc.frame.MedicineDMLFrame;
import cn.kgc.frame.MedicineFrame;
import cn.kgc.frame.listener.BaseDMLButtonListener;
import cn.kgc.model.Medicine;
import cn.kgc.model.MedicineType;
import cn.kgc.service.impl.MedicineServiceImpl;
import cn.kgc.service.intf.MedicineService;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.StringUtils;


public class MedicineDMLButtonListener extends BaseDMLButtonListener implements ActionListener {
	private static final String SAVE_SUCCUSS = "保存成功";
	private static final String SAVE_ERORR = "保存失败";
	private static final String USER_CHOOSE_TREE_ERORR = "请选择一个底层节点添加药品";
	private static final String NAME_IS_EMPTY = "药品名字不能为空";
	private static final String DELETE_MESSAGE = "是否删除？";
	private static final String DELETE_MESSAGE_TITLE = "删除确认";



	private MedicineFrame medicineFrame;
	private MedicineDMLFrame medicineDMLFrame;
	private static int command;
	
	private MedicineService medicineService = new MedicineServiceImpl();
	
	
	public MedicineDMLButtonListener(MedicineFrame medicineFrame) {
		
		this.medicineFrame = medicineFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {		
			JButton button = (JButton)e.getSource();
			execute(button);
		} catch (Exception e1) {
			FrameUtils.DialogErorr("错误，" + e1.getMessage());
			e1.printStackTrace();
		}
	}

	@Override
	public void add(JButton button) {
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)medicineFrame.getMedicineTypeTreeFrame().getTree().getLastSelectedPathComponent();	
		if(treeNode != null && treeNode.getChildCount() == 0) {
			command = COMMAND_ADD;
			MedicineType type = (MedicineType)treeNode.getUserObject();
			Medicine medicine = new Medicine();
			medicine.setMedicineType(type);
			MedicineDMLFrame medicineDMLFrame = new MedicineDMLFrame(medicine, this.medicineFrame);
			medicineDMLFrame.add();
			
		} else {
			FrameUtils.DialogErorr(USER_CHOOSE_TREE_ERORR);
		}
	}

	@Override
	public void modify(JButton button) {
		try {
			String medicineId = (String)medicineFrame.getMedicineTableFrame().getTableSelectedRowInfo(0);
			Medicine medicine = medicineService.getInfoById(medicineId);
			command = COMMAND_MODIFY;
			MedicineDMLFrame medicineDMLFrame = new MedicineDMLFrame(medicine, this.medicineFrame);
			medicineDMLFrame.modify();
		} catch (Exception e) {
			FrameUtils.DialogErorr("错误，" + e.getMessage());
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(JButton button) {
		String medicineId;
		try {
			medicineId = (String)medicineFrame.getMedicineTableFrame().getTableSelectedRowInfo(0);
			int result = JOptionPane.showConfirmDialog(null, DELETE_MESSAGE,DELETE_MESSAGE_TITLE, JOptionPane.WARNING_MESSAGE);
			if(result == 0) {
				int status = medicineService.delete(medicineId);
				FrameUtils.statusInfo(status, SAVE_SUCCUSS, SAVE_ERORR);
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode)medicineFrame.getMedicineTypeTreeFrame().getTree().getLastSelectedPathComponent();	
				if(treeNode != null) {
					MedicineType type = (MedicineType)treeNode.getUserObject();
					medicineFrame.getMedicineTableFrame().getDataAndRefreshTable(type.getId());				
				}else {
					medicineFrame.getMedicineTableFrame().getDataAndRefreshTable();				
				}	
				command = COMMAND_SAVE;			
			}
		} catch (Exception e) {
			FrameUtils.DialogErorr("错误，" + e.getMessage());
			e.printStackTrace();
		}
		
	}

	@Override
	public void save(JButton button) {
		int status = 0;
		try {
			switch(command) {
			case COMMAND_ADD:
				Medicine medicine = medicineContentFieldsValue2Patient();
				medicine.setMedicineType(medicineDMLFrame.getType());
				status = medicineService.add(medicine);				
			break;
			case COMMAND_MODIFY:
				medicine = medicineContentFieldsValue2Patient();
				List<JComponent> types = medicineDMLFrame.getMedicineDMLFields();
				@SuppressWarnings("unchecked") 
				JComboBox<MedicineType> combo = (JComboBox<MedicineType>)types.get(types.size()-1);
				medicine.setMedicineType((MedicineType)combo.getSelectedItem());
				status = medicineService.modify(medicine);				
				break;
			}
			FrameUtils.statusInfo(status, SAVE_SUCCUSS, SAVE_ERORR);
			medicineFrame.getMedicineTableFrame().getDataAndRefreshTable(medicineDMLFrame.getType().getId());
			command = COMMAND_SAVE;
			medicineDMLFrame.getMedicineDMLFrame().dispose();	
		} catch (Exception e) {
			FrameUtils.DialogErorr("错误，" + e.getMessage());
			e.printStackTrace();
		}
	}



	@Override
	public void undo(JButton button) {
		exit(button);
	}
	

	@Override
	public void exit(JButton button) {
		medicineDMLFrame.getMedicineDMLFrame().dispose();	
	}
	
	private Medicine medicineContentFieldsValue2Patient() throws Exception {
		if(StringUtils.isEmpty(((JTextField)medicineDMLFrame.getMedicineDMLFields().get(1)).getText())) {
			throw new Exception(NAME_IS_EMPTY);						
		}
		Object obj = fieldsValue2Patient(medicineDMLFrame.getMedicineDMLFields(), Medicine.class, 0);
		Medicine medicine = (Medicine)obj;
		
		return medicine;
	}

	public void setMedicineDMLFrame(MedicineDMLFrame medicineDMLFrame) {
		this.medicineDMLFrame = medicineDMLFrame;		
	}



}
