package cn.kgc.frame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.kgc.frame.listener.PrescriptionDMLButtonListener;
import cn.kgc.model.Case;
import cn.kgc.model.Patient;
import cn.kgc.model.Prescription;
import cn.kgc.service.impl.PrescriptionServiceImpl;
import cn.kgc.service.intf.PrescriptionService;
import cn.kgc.utils.DateChooser;
import cn.kgc.utils.DateUtils;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.PatientUtils;


public class PrescriptionFrame {
	private static final String TITLE = "�� �� �� ��";
	private static final String PRESCRIPTION_PANEL_TITLE = "��ҽ����";
	private static final String PRESCRIPTION_MEDICINE_PANEL_TITLE = "���鷽/�÷�";
	private static final String PRESCRIOTION_ID_LABEL_TITLE = "������ţ�";
	private static final String PRESCRIOTION_TIME_LABEL_TITLE = "�������ڣ�";
	private static final String PRESCRIOTION_TITLE_LABEL_CONTENT = "��ҩ������";
	private static final String TABLE_ID_LABEL_CONTENT = "���";
	private static final String TABLE_MEDICINE_LABEL_CONTENT = "������ҩ";
	private static final String TABLE_UASGE_LABEL_CONTENT = "�÷�";
	private static final String ADVICE_LABEL_CONTENT = "ҽ����";
	private static final String PRICE_LABEL_CONTENT = "�۸�";
	private static final String[] PETINENT_INFO_LABEL_CONTENTS = {"������","�Ա�","���䣺","סַ��","��ϵ�绰��","����ʷ��","��ϣ�","����˵����"};
	
	private static final String[] PRESCRIPTION_DML_BUTTON_IMGURL = {"./img/1new.PNG","./img/2save.PNG","./img/3shoufei.PNG",
			"./img/4qingchu.PNG","./img/5daying.PNG","./img/6cyanfang.PNG","./img/7lishi.PNG","./img/8close.PNG"};
	private static final String[] PRESCRIPTION_DML_BUTTON_NAMES = {"add","save","otherToDo","undo","otherToDo","otherToDo","otherToDo","otherToDo"};
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 700;
	private static final int SPLIT_DIVIDER_LOCATION = 200;
	private static final int COMPONENT_DISTANCE = 10;
	
	private static final int PRESCRIPTION_TOOL_PANEL_HEIGHT = 60;
	
	private static final int PRESCRIOTION_ID_LABEL_HEIGHT = 20;
	private static final int PRESCRIOTION_ID_LABEL_WIDTH = 70;
	private static final int PRESCRIOTION_ID_FIELD_WIDTH = 150;
	
	private static final int PRESCRIOTION_TITLE_LABEL_HEIGHT = 25;
	private static final int PETINENT_INFO_LABEL_WIDTH = 190;

	private static final int TABLE_ID_LABEL_WIDTH = 35;
	private static final int TABLE_MEDICINE_LABEL_WIDTH = 260;
	private static final int TABLE_UASGE_LABEL_WIDTH = 245;
	
	private static final int ADVICE_LABEL_WIDTH = 40;
	private static final int ADVICE_AREA_WIDTH = 300;
	private static final int ADVICE_AREA_HEIGHT = 80;
	private static final int PRICE_FIELD_WIDTH = 150;




	
	
	private JFrame prescriptionFrame = new JFrame(TITLE);
	private JTabbedPane prescriptionMedicineTabbedPane = new JTabbedPane();
	private JTabbedPane prescriptionTabbedPane = new JTabbedPane();
	private JSplitPane prescriptionSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, prescriptionMedicineTabbedPane, prescriptionTabbedPane);
	
	private JPanel prescriptionMedicinePanel = new JPanel();	
	private JPanel prescriptionPanel = new JPanel();
	
	
	private Case $case;
	private Prescription prescription;
	private List<JButton> prescriptionDMLButtons = new ArrayList<>();
	private List<JLabel> patientInfoLabels = new ArrayList<>();
	private List<JComponent> medicineFields = new ArrayList<>();
	private List<JComponent> uasgeFields = new ArrayList<>();
	
	
	private PrescriptionService prescriptionService = new PrescriptionServiceImpl();
	
	public PrescriptionFrame(Case $case) {
		this.$case = $case;
		prescription = new Prescription();
	}

	public void execute() {
		
		prescriptionFrame.setSize(WIDTH, HEIGHT);
		prescriptionFrame.setLocationRelativeTo(null);
		prescriptionFrame.setLayout(null);
		
	
		try {
			String id = prescriptionService.getMinEmptyId();
			prescription.setId(id);
		} catch (Exception e) {
			FrameUtils.DialogErorr("����," + e.getMessage());
			e.printStackTrace();
		}
		
		createLayout();
		createPrescriptionPanel();
//		createDrugTabbedPane();
//		createMedicineTreePanel();
		
		prescriptionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		prescriptionFrame.setVisible(true);
		
	}



	private void createLayout() {
		prescriptionSplitPane.setDividerLocation(SPLIT_DIVIDER_LOCATION);
		prescriptionSplitPane.setBounds(0, 0, WIDTH, HEIGHT);
				
		prescriptionFrame.add(prescriptionSplitPane);
		
		prescriptionMedicineTabbedPane.addTab(PRESCRIPTION_MEDICINE_PANEL_TITLE,prescriptionMedicinePanel);
		prescriptionTabbedPane.addTab(PRESCRIPTION_PANEL_TITLE, prescriptionPanel);
		
		prescriptionMedicinePanel.setLayout(null);
		prescriptionPanel.setLayout(null);

		
	}
	
	private void createPrescriptionPanel() {
		JPanel prescriptionToolPanel = new JPanel();
		prescriptionToolPanel.setBounds(0, 0, WIDTH-SPLIT_DIVIDER_LOCATION, PRESCRIPTION_TOOL_PANEL_HEIGHT);
		createPrescriptionToolPanel(prescriptionToolPanel);
		
		JPanel prescriptionInfoPanel = new JPanel();
		prescriptionInfoPanel.setBounds(0, prescriptionToolPanel.getHeight(), prescriptionToolPanel.getWidth(), HEIGHT-prescriptionToolPanel.getHeight());
		createPrescriptionInfoPanel(prescriptionInfoPanel);
		
		prescriptionPanel.add(prescriptionInfoPanel);
		prescriptionPanel.add(prescriptionToolPanel);

	}


	private void createPrescriptionToolPanel(JPanel prescriptionToolPanel) {
		prescriptionToolPanel.setLayout(null);

		int positionX = 0;
		for (int i = 0; i < PRESCRIPTION_DML_BUTTON_IMGURL.length; i++) {
			JButton button = FrameUtils.addButton(PRESCRIPTION_DML_BUTTON_IMGURL[i], positionX, PRESCRIPTION_TOOL_PANEL_HEIGHT, PRESCRIPTION_TOOL_PANEL_HEIGHT, prescriptionToolPanel);
			button.setName(PRESCRIPTION_DML_BUTTON_NAMES[i]);
			prescriptionDMLButtons.add(button);
			button.addActionListener(new PrescriptionDMLButtonListener(this));
			positionX += PRESCRIPTION_TOOL_PANEL_HEIGHT;
			if(PRESCRIPTION_DML_BUTTON_NAMES[i].equals("save") || PRESCRIPTION_DML_BUTTON_NAMES[i].equals("undo") ) {
				button.setEnabled(false);
			}
		}
	}
	
	
	private void createPrescriptionInfoPanel(JPanel prescriptionInfoPanel) {
		prescriptionInfoPanel.setLayout(null);
		
		JLabel prescriptionIdLabel = new JLabel(PRESCRIOTION_ID_LABEL_TITLE);
		prescriptionIdLabel.setBounds(COMPONENT_DISTANCE, COMPONENT_DISTANCE, PRESCRIOTION_ID_LABEL_WIDTH, PRESCRIOTION_ID_LABEL_HEIGHT);
		prescriptionInfoPanel.add(prescriptionIdLabel);
		
		JTextField prescriptionIdField = new JTextField();
		prescriptionIdField.setBounds(prescriptionIdLabel.getX() + prescriptionIdLabel.getWidth(), COMPONENT_DISTANCE, PRESCRIOTION_ID_FIELD_WIDTH, PRESCRIOTION_ID_LABEL_HEIGHT);
		prescriptionIdField.setText(prescription.getId());
		prescriptionIdField.setEditable(false);
		prescriptionInfoPanel.add(prescriptionIdField);
		
		JLabel prescriptionTimeLabel = new JLabel(PRESCRIOTION_TIME_LABEL_TITLE);
		prescriptionTimeLabel.setBounds(prescriptionIdField.getX() + prescriptionIdField.getWidth() + COMPONENT_DISTANCE, COMPONENT_DISTANCE, PRESCRIOTION_ID_LABEL_WIDTH, PRESCRIOTION_ID_LABEL_HEIGHT);
		prescriptionInfoPanel.add(prescriptionTimeLabel);
		
		JTextField prescriptionTimeField = new JTextField();
		prescriptionTimeField.setBounds(prescriptionTimeLabel.getX() + prescriptionTimeLabel.getWidth(), COMPONENT_DISTANCE, PRESCRIOTION_ID_FIELD_WIDTH, PRESCRIOTION_ID_LABEL_HEIGHT);
		Date date = new Date();
		prescriptionTimeField.setText(DateUtils.date2String(date));
		DateChooser dateChooser = DateChooser.getInstance();
		dateChooser.register(prescriptionTimeField);
		prescriptionInfoPanel.add(prescriptionTimeField);
		
		JLabel titleLable = new JLabel(PRESCRIOTION_TITLE_LABEL_CONTENT,JLabel.CENTER);
		titleLable.setBounds(0, prescriptionTimeField.getHeight() + COMPONENT_DISTANCE*2, WIDTH-SPLIT_DIVIDER_LOCATION, PRESCRIOTION_TITLE_LABEL_HEIGHT);
		titleLable.setFont(new java.awt.Font("Dialog", 1, 25));
		prescriptionInfoPanel.add(titleLable);
		
		JPanel inputContentPanel = new JPanel();
		inputContentPanel.setBounds(0, titleLable.getY()+titleLable.getHeight(), titleLable.getWidth(), prescriptionInfoPanel.getHeight() - titleLable.getY()- titleLable.getHeight());
		prescriptionInfoPanel.add(inputContentPanel);
		createInputContentPanel(inputContentPanel);
	}

	private void createInputContentPanel(JPanel inputContentPanel) {
		inputContentPanel.setLayout(null);
		int X = COMPONENT_DISTANCE;
		int Y = 0;
		for (int i = 0; i < PETINENT_INFO_LABEL_CONTENTS.length; i++) {
			JLabel label = new JLabel();
			label.setBounds(X, Y, PETINENT_INFO_LABEL_WIDTH, PRESCRIOTION_TITLE_LABEL_HEIGHT);
			patientInfoLabels.add(label);
			inputContentPanel.add(label);
			if((label.getX()+label.getWidth()*2) > inputContentPanel.getWidth()) {
				X = COMPONENT_DISTANCE;
				Y = label.getY() + label.getHeight();
			}  else {
				X += PETINENT_INFO_LABEL_WIDTH;
			}
			if(i == PETINENT_INFO_LABEL_CONTENTS.length-1) {
				Y = label.getY() + label.getHeight();
			}
		}
		patientInfo2Lables();
		
		X = 0;
		JLabel tableIdLabel = new JLabel(TABLE_ID_LABEL_CONTENT,JLabel.CENTER);
		tableIdLabel.setBounds(X, Y, TABLE_ID_LABEL_WIDTH, PRESCRIOTION_TITLE_LABEL_HEIGHT);
		tableIdLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
		inputContentPanel.add(tableIdLabel);
		X += TABLE_ID_LABEL_WIDTH;
		
		JLabel tableMedicineLabel = new JLabel(TABLE_MEDICINE_LABEL_CONTENT,JLabel.CENTER);
		tableMedicineLabel.setBounds(X, Y, TABLE_MEDICINE_LABEL_WIDTH, PRESCRIOTION_TITLE_LABEL_HEIGHT);
		tableMedicineLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
		inputContentPanel.add(tableMedicineLabel);
		X += TABLE_MEDICINE_LABEL_WIDTH;
		
		JLabel tableUasgeLabel = new JLabel(TABLE_UASGE_LABEL_CONTENT,JLabel.CENTER);
		tableUasgeLabel.setBounds(X, Y, TABLE_UASGE_LABEL_WIDTH, PRESCRIOTION_TITLE_LABEL_HEIGHT);
		tableUasgeLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
		inputContentPanel.add(tableUasgeLabel);
		X += TABLE_UASGE_LABEL_WIDTH;
		
		X = 0;
		Y += PRESCRIOTION_TITLE_LABEL_HEIGHT;
		for (int i = 1; i < 13; i++) {
			JLabel label = new JLabel(i + "��",JLabel.CENTER);
			label.setBounds(X, Y, TABLE_ID_LABEL_WIDTH, PRESCRIOTION_TITLE_LABEL_HEIGHT);
			label.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
			inputContentPanel.add(label);
			X += TABLE_ID_LABEL_WIDTH;
			
			JTextField field = new JTextField();
			field.setBounds(X, Y, TABLE_MEDICINE_LABEL_WIDTH, PRESCRIOTION_TITLE_LABEL_HEIGHT);
			field.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
			inputContentPanel.add(field);
			medicineFields.add(field);
			X += TABLE_MEDICINE_LABEL_WIDTH;
			
			JTextField field2 = new JTextField();
			field2.setBounds(X, Y, TABLE_UASGE_LABEL_WIDTH, PRESCRIOTION_TITLE_LABEL_HEIGHT);
			field2.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
			inputContentPanel.add(field2);
			uasgeFields.add(field2);
			X = 0;
			Y += PRESCRIOTION_TITLE_LABEL_HEIGHT;
			
		}
		FrameUtils.fieldsDisable(medicineFields);
		FrameUtils.fieldsDisable(uasgeFields);
		
		Y += COMPONENT_DISTANCE;
		JLabel adviceLabel = new JLabel(ADVICE_LABEL_CONTENT);
		adviceLabel.setBounds(X, Y, ADVICE_LABEL_WIDTH, PRESCRIOTION_TITLE_LABEL_HEIGHT);
		inputContentPanel.add(adviceLabel);
		X += ADVICE_LABEL_WIDTH;
		
		JTextArea adviceArea = new JTextArea();
		adviceArea.setBounds(X, Y, ADVICE_AREA_WIDTH, ADVICE_AREA_HEIGHT);
		inputContentPanel.add(adviceArea);
		adviceArea.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
		X += ADVICE_AREA_WIDTH + COMPONENT_DISTANCE;
		Y += PRESCRIOTION_TITLE_LABEL_HEIGHT;
		
		JLabel priceLabel = new JLabel(PRICE_LABEL_CONTENT);
		priceLabel.setBounds(X, Y, ADVICE_LABEL_WIDTH, PRESCRIOTION_TITLE_LABEL_HEIGHT);
		inputContentPanel.add(priceLabel);
		X += ADVICE_LABEL_WIDTH;
		
		JTextField priceField = new JTextField();
		priceField.setBounds(X, Y, PRICE_FIELD_WIDTH, PRESCRIOTION_TITLE_LABEL_HEIGHT);
		inputContentPanel.add(priceField);
	}

	private void patientInfo2Lables() {
		int index = 0;
		Patient patient = $case.getPatient();
		patientInfoLabels.get(index).setText(PETINENT_INFO_LABEL_CONTENTS[index++] + "  " + patient.getName());
		patientInfoLabels.get(index).setText(PETINENT_INFO_LABEL_CONTENTS[index++] + "  " + PatientUtils.status2Str(patient.getSex(), PatientUtils.sexRule));
		patientInfoLabels.get(index).setText(PETINENT_INFO_LABEL_CONTENTS[index++] + "  " + patient.getAge());
		patientInfoLabels.get(index).setText(PETINENT_INFO_LABEL_CONTENTS[index++] + "  " + patient.getAddress());
		patientInfoLabels.get(index).setText(PETINENT_INFO_LABEL_CONTENTS[index++] + "  " + patient.getPhoneNumber());
		patientInfoLabels.get(index).setText(PETINENT_INFO_LABEL_CONTENTS[index++] + "  " + patient.getAllergy());
		patientInfoLabels.get(index).setText(PETINENT_INFO_LABEL_CONTENTS[index++] + "  " + $case.getExamination());
		patientInfoLabels.get(index).setText(PETINENT_INFO_LABEL_CONTENTS[index++] + "  " + $case.getOtherExplain());

	}

	public static void main(String[] args) {
		PrescriptionFrame PrescriptionFrame = new PrescriptionFrame(new Case(null, null, null, null, null, null, null, null, null, null, null, new Patient(null, null, null, null, null, null, null, null, null, null, null, null, null, null)));
		PrescriptionFrame.execute();
	}

}