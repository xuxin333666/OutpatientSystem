package cn.kgc.frame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import cn.kgc.frame.listener.MedicineDMLButtonListener;
import cn.kgc.frame.model.MedicineTableModel;
import cn.kgc.model.Medicine;
import cn.kgc.model.MedicineType;
import cn.kgc.service.impl.MedicineServiceImpl;
import cn.kgc.service.impl.MedicineTypeServiceImpl;
import cn.kgc.service.intf.MedicineService;
import cn.kgc.service.intf.MedicineTypeService;
import cn.kgc.utils.FrameUtils;

public class MedicineDMLFrame {
	private static final String INPUT_CONTENT_BUTTON_SAVE = "±£´æ";
	private static final String INPUT_CONTENT_BUTTON_CLOSE = "·µ»Ø";
	
	private static final int DML_MEDICINE_FRAME_WIDTH = 450;
	private static final int DML_MEDICINE_FRAME_HEIGHT = 500;
	
	private static final int INPUT_CONTENT_POSITON_X = 20;
	private static final int INPUT_CONTENT_POSITON_Y = 25;
	private static final int INPUT_CONTENT_LABEL_WIDTH = 90;
	private static final int INPUT_CONTENT_LABEL_HEIGHT = 25;
	private static final int INPUT_CONTENT_AREA_WIDTH = DML_MEDICINE_FRAME_WIDTH - INPUT_CONTENT_LABEL_WIDTH - INPUT_CONTENT_POSITON_X*4;
	private static final int INPUT_CONTENT_AREA_HEIGHT = INPUT_CONTENT_LABEL_HEIGHT;
	private static final int INPUT_CONTENT_BUTTON_DIV = 70;
	private static final int INPUT_CONTENT_BUTTON_WIDTH = 70;
	private static final int INPUT_CONTENT_BUTTON_HEIGHT = 25;
	private static final int INPUT_CONTENT_BUTTON_POSITION_X = DML_MEDICINE_FRAME_WIDTH/2 - (INPUT_CONTENT_BUTTON_WIDTH + INPUT_CONTENT_BUTTON_DIV/2);

	private MedicineFrame medicineFrame;
	private MedicineType type;
	private Medicine medicine;
	
	private JFrame medicineDMLFrame = new JFrame();
	private JButton saveButton = new JButton(INPUT_CONTENT_BUTTON_SAVE);
	private JButton closeButton = new JButton(INPUT_CONTENT_BUTTON_CLOSE);
	
	
	private List<JComponent> medicineDMLFields = new ArrayList<>();
	private MedicineService medicineService = new MedicineServiceImpl();
	
	public MedicineDMLFrame(Medicine medicine,MedicineFrame medicineFrame) {
		this.type = medicine.getMedicineType();
		this.medicineFrame = medicineFrame;
		this.medicine = medicine;
	}
	
	public MedicineDMLFrame() {}

	public void add() {		
		createInputContentPanel();
		try {
			String id = medicineService.getMinEmptyId();
			JTextField idField= (JTextField)medicineDMLFields.get(0);
			idField.setText(id);
			idField.setEditable(false);
		} catch (Exception e) {
			FrameUtils.DialogErorr("´íÎó£¬" + e.getMessage());
			e.printStackTrace();
		}
		
		@SuppressWarnings("unchecked")
		JComboBox<MedicineType> typeNameField = (JComboBox<MedicineType>)medicineDMLFields.get(medicineDMLFields.size()-1);
		typeNameField.setSelectedItem(type);
		typeNameField.setEnabled(false);
		
	}
	
	@SuppressWarnings("unchecked")
	public void modify() {
		createInputContentPanel();
		Field[] attributes = medicine.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < medicineDMLFields.size(); i++) {
				attributes[i].setAccessible(true);
				Object value = attributes[i].get(medicine);
				if(value == null) {
					value = "";
				}
				if(medicineDMLFields.get(i) instanceof JComboBox) {
					JComboBox<MedicineType> combo = (JComboBox<MedicineType>)medicineDMLFields.get(i);
					combo.setSelectedItem(medicine.getMedicineType());
				} else if(medicineDMLFields.get(i) instanceof JTextField) {
					JTextField field = (JTextField)medicineDMLFields.get(i);

					field.setText(value.toString());
					if(i == 0) {
						field.setEditable(false);
					}
				}
			}
		} catch (Exception e) {
			FrameUtils.DialogErorr("´íÎó£¬" + e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	

	private void createInputContentPanel() {
		medicineDMLFrame.setSize(DML_MEDICINE_FRAME_WIDTH, DML_MEDICINE_FRAME_HEIGHT);
		medicineDMLFrame.setLayout(null);
		medicineDMLFrame.setLocationRelativeTo(null);
		
		
		int X = INPUT_CONTENT_POSITON_X;
		int Y = INPUT_CONTENT_POSITON_Y;
		MedicineTableModel medicineTableModel = MedicineTableModel.getInstance();
		for(int i=0;i<=medicineTableModel.getColumnCount();i++) {
			JLabel Label = new JLabel();
			if(i == medicineTableModel.getColumnCount()) {
				Label.setText("Ò©Æ··ÖÀà£º");
			} else {
				String colName = medicineTableModel.getColumnName(i);
				Label.setText(colName + ":");			
			}
			Label.setBounds(X, Y, INPUT_CONTENT_LABEL_WIDTH, INPUT_CONTENT_LABEL_HEIGHT);
			X += INPUT_CONTENT_LABEL_WIDTH;
			
			JComponent field = new JTextField();
			if(i == medicineTableModel.getColumnCount()) {
				MedicineTypeService medicineTypeService = new MedicineTypeServiceImpl();
				Vector<MedicineType> types;
				try {
					types = medicineTypeService.getAllInfoByNoChildId();
					field = new JComboBox<>(types);
				} catch (Exception e) {
					FrameUtils.DialogErorr("´íÎó£¬" + e.getMessage());
					e.printStackTrace();
				}
			} else {
				field.setBorder (BorderFactory.createLineBorder(Color.GRAY,1));	
			}
			field.setBounds(X, Y, INPUT_CONTENT_AREA_WIDTH, INPUT_CONTENT_AREA_HEIGHT);	
			
			X = INPUT_CONTENT_POSITON_X;
			Y += field.getHeight() + INPUT_CONTENT_POSITON_Y;	
			
			medicineDMLFields.add(field);
			medicineDMLFrame.add(field);
			medicineDMLFrame.add(Label);
		}
		
		
				
		saveButton.setBounds(INPUT_CONTENT_BUTTON_POSITION_X, Y, INPUT_CONTENT_BUTTON_WIDTH, INPUT_CONTENT_BUTTON_HEIGHT);
		
		closeButton.setBounds(saveButton.getX()+saveButton.getWidth()+INPUT_CONTENT_BUTTON_DIV, saveButton.getY(), INPUT_CONTENT_BUTTON_WIDTH, INPUT_CONTENT_BUTTON_HEIGHT);
		
		medicineDMLFrame.add(saveButton);
		medicineDMLFrame.add(closeButton);
		
		saveButton.setName("save");
		MedicineDMLButtonListener medicineDMLButtonListener = new MedicineDMLButtonListener(medicineFrame);
		medicineDMLButtonListener.setMedicineDMLFrame(this);
		saveButton.addActionListener(medicineDMLButtonListener);
		
		
		closeButton.setName("undo");
		MedicineDMLButtonListener medicineDMLButtonListener2 = new MedicineDMLButtonListener(medicineFrame);
		medicineDMLButtonListener2.setMedicineDMLFrame(this);
		closeButton.addActionListener(medicineDMLButtonListener2);
		
		medicineDMLFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		medicineDMLFrame.setVisible(true);
	}

	public MedicineType getType() {
		return type;
	}

	public JFrame getMedicineDMLFrame() {
		return medicineDMLFrame;
	}

	public List<JComponent> getMedicineDMLFields() {
		return medicineDMLFields;
	}


}
