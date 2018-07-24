package cn.kgc.frame;


import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.text.JTextComponent;

import cn.kgc.frame.intf.BusinessButtonFrameIntf;
import cn.kgc.frame.listener.PatientQueryButtonListener;
import cn.kgc.frame.listener.PatientTableMouseAdapter;
import cn.kgc.frame.listener.RegistDMLButtonListener;
import cn.kgc.frame.model.CaseTableModel;
import cn.kgc.frame.model.PatientTableModel;
import cn.kgc.utils.CaseDMLButtonUtils;
import cn.kgc.utils.DateChooser;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.PatientUtils;
import cn.kgc.utils.RegistDMLButtonUtils;
import cn.kgc.utils.ScreenSizeUtils;
import cn.kgc.utils.StringUtils;

public class ConsultFrame implements BusinessButtonFrameIntf {
	private static final String TITLE = "患 者 咨 询";
	private static final String REGIST_PANEL_TITLE = "初 诊 登 记";
	private static final String CASEMANAGER_PANEL_TITLE = "病 例 管 理";
	private static final String CASE_DESCRIPTION_TITLE = "病例描述";
	private static final String[] CASE_TOOL_LABEL_PATIENT_ID_TITLEs = {"医疗证号:","姓名:","性别:","年龄:"};
	private static final String START_TIME_LABEL_CONTENT = "起始时间：";
	private static final String END_TIME_LABEL_CONTENT = "截止时间：";
	private static final String QUERY_LOCATION_LABEL_CONTENT = "查询位置：";
	private static final String KEY_LABEL_CONTENT = "关键字：";
	private static final String QUERY_BUTTON_CONTENT = "搜索";
	private static final String REGIST_TITLE_LABEL_CONTENT = "患者初诊登记";
	private static final String REGIST_CONTENT_LINE_IMGURL = "./img/line.jpg";
	private static final String[] QUERY_KEY_LIST = {"证号/姓名","性别","婚姻状况","职业","联系地址","初诊处理意见","初诊备注"};
	private static final String REFRESH_PATIENT_TABLE_COMMAND = "patient";
	private static final String REFRESH_CASE_TABLE_COMMAND = "case";
	
	private static final int PATIENT_ATTRIBUTE_COUNT = PatientUtils.PATIENT_ATTRIBUTE_COUNT;
	private static final int CASE_ATTRIBUTE_COUNT = 10;
	private static final int QUERY_PANEL_HEIGHT = 35;
	private static final int FOOTER_HEIGHT = 80;
	private static final int SPLIT_PANE_DIVIDER_LOCATION = 300;
	private static final int PATIENT_SCROLL_PANE_WIDTH = 1000;
	private static final int START_TIME_LABEL_POSITION_X = 40;
	private static final int START_TIME_LABEL_POSITION_Y = 5;
	private static final int START_TIME_LABEL_WIDTH = 70;
	private static final int START_TIME_LABEL_HIGHT = 25;
	private static final int START_TIME_FIELD_WIDTH = 120;
	private static final int START_TIME_FIELD_HIGHT = 25;
	private static final int COMPONENT_DISTANSE = 10;
	
	private static final int DML_BUTTON_PANEL_WIDTH = ScreenSizeUtils.screenWidth;
	private static final int DML_BUTTON_PANEL_HEIGHT = 60;
	private static final int DML_BUTTON_WIDTH = 60;
	
	
	private static final int REGIST_TITLE_LABEL_HEIGHT = 20;
	private static final int REGIST_TITLE_LABEL_WIDTH = 100;
	private static final int LINE_LABEL_HEIGHT = 1;
	private static final int REGIST_INPUT_CONTENT_POSITON_X = COMPONENT_DISTANSE;
	private static final int REGIST_INPUT_CONTENT_POSITON_Y = COMPONENT_DISTANSE;
	private static final int REGUST_INPUT_CONTENT_LABEL_WIDTH = 70;
	private static final int REGUST_INPUT_CONTENT_LABEL_HEIGHT = 25;
	private static final int REGUST_INPUT_CONTENT_FIELD_SMALL_WIDTH = 150;
	private static final int REGUST_INPUT_CONTENT_FIELD_HEIGHT = REGUST_INPUT_CONTENT_LABEL_HEIGHT;
	private static final int REGUST_INPUT_CONTENT_FIELD_BIG_WIDTH = REGUST_INPUT_CONTENT_FIELD_SMALL_WIDTH*2 + COMPONENT_DISTANSE + REGUST_INPUT_CONTENT_LABEL_WIDTH;
	private static final int REGUST_INPUT_CONTENT_AREA_HEIGHT = 70;
	
	
	private static final int CASE_TABLE_HEIGHT = 130;
	private static final int CASE_DESCRIPTION_TOOL_PANEL_HEIGHT = 100;
	private static final int CASE_TOOL_LABEL_PATIENT_ID_HEIGHT = 30;
	private static final int CASE_TOOL_LABEL_COUNT = 4;
	private static final int CASE_INPUT_CONTENT_AREA_HEIGHT = 40;
	private static final int CASE_INPUT_CONTENT_AREA_WIDTH = 700;
	
	
	private JFrame jFrame = new JFrame(TITLE);
	private JPanel patientTablePanel = new JPanel();
	private JTabbedPane patientRegistTabbedPane = new JTabbedPane();
	private JSplitPane contentPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, patientTablePanel, patientRegistTabbedPane);
	public static JTable patientTable;
	
	private JPanel queryPanel = new JPanel();
	private JTextField startTimeField  = new JTextField();
	private JTextField endTimeField  = new JTextField();
	private JComboBox<String> queryColumnNameComboBox;
	private JTextField keyField  = new JTextField();
	
	private JPanel registPanel = new JPanel();
	private JPanel DMLButtonPanel = new JPanel();
	private JPanel registContentPanel = new JPanel();
	private List<JComponent> registContentFields = new ArrayList<>();
	private List<JButton> registDMLButtons = new ArrayList<>();
	
	private JPanel caseManagerPanel = new JPanel();
	private JTabbedPane caseManagerChildpanel = new JTabbedPane();
	public static JTable caseTable;
	private List<JButton> caseDMLButtons = new ArrayList<>();
	private List<JLabel> caseToolLabels = new ArrayList<>();
	private List<JTextComponent> caseDescriptionLabels = new ArrayList<>();
	
	
	private List<String> registButtonImgUrl = RegistDMLButtonUtils.getList();
	private List<String> caseButtonImgUrl = CaseDMLButtonUtils.getList();
	
	
	
	@Override
	public void execute() {
		jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		jFrame.setLayout(null);
		createQueryPanel();
		createLayout();
		createPatientRegistTabbedPane();
		createPatientTablePanel();
		jFrame.setVisible(true);		
	}
	
	private void createLayout() {
		queryPanel.setBounds(0, 0, ScreenSizeUtils.screenWidth, QUERY_PANEL_HEIGHT);
		contentPane.setBounds(0, QUERY_PANEL_HEIGHT, ScreenSizeUtils.screenWidth, ScreenSizeUtils.screenHeight-QUERY_PANEL_HEIGHT - FOOTER_HEIGHT);
		contentPane.setDividerLocation(SPLIT_PANE_DIVIDER_LOCATION);
		jFrame.add(queryPanel);
		jFrame.add(contentPane);
	}
	
	private void createQueryPanel() {
		DateChooser startTimeChooser = DateChooser.getInstance("yyyy-MM-dd");
		DateChooser endTimeChooser = DateChooser.getInstance("yyyy-MM-dd");
		queryPanel.setLayout(null);
		
		JLabel startTimeLabel = new JLabel(START_TIME_LABEL_CONTENT);
		startTimeLabel.setBounds(START_TIME_LABEL_POSITION_X, START_TIME_LABEL_POSITION_Y, START_TIME_LABEL_WIDTH, START_TIME_LABEL_HIGHT);
		
		startTimeChooser.register(startTimeField); 
		startTimeField.setBounds(startTimeLabel.getX() + startTimeLabel.getWidth() + COMPONENT_DISTANSE, START_TIME_LABEL_POSITION_Y, START_TIME_FIELD_WIDTH, START_TIME_FIELD_HIGHT);
		
		JLabel endTimeLabel = new JLabel(END_TIME_LABEL_CONTENT);
		endTimeLabel.setBounds(startTimeField.getX() + startTimeField.getWidth() + COMPONENT_DISTANSE, START_TIME_LABEL_POSITION_Y, START_TIME_LABEL_WIDTH, START_TIME_LABEL_HIGHT);
		
		endTimeChooser.register(endTimeField);
		endTimeField.setBounds(endTimeLabel.getX() + endTimeLabel.getWidth() + COMPONENT_DISTANSE, START_TIME_LABEL_POSITION_Y, START_TIME_FIELD_WIDTH, START_TIME_FIELD_HIGHT);
		
		JLabel queryLocationLabel = new JLabel(QUERY_LOCATION_LABEL_CONTENT);
		queryLocationLabel.setBounds(endTimeField.getX() + endTimeField.getWidth() + COMPONENT_DISTANSE, START_TIME_LABEL_POSITION_Y, START_TIME_LABEL_WIDTH, START_TIME_LABEL_HIGHT);
		
		queryColumnNameComboBox = new JComboBox<>(QUERY_KEY_LIST);
		queryColumnNameComboBox.setBounds(queryLocationLabel.getX() + queryLocationLabel.getWidth() + COMPONENT_DISTANSE, START_TIME_LABEL_POSITION_Y, START_TIME_FIELD_WIDTH, START_TIME_FIELD_HIGHT);
		
		JLabel keyLabel = new JLabel(KEY_LABEL_CONTENT);
		keyLabel.setBounds(queryColumnNameComboBox.getX() + queryColumnNameComboBox.getWidth() + COMPONENT_DISTANSE, START_TIME_LABEL_POSITION_Y, START_TIME_LABEL_WIDTH, START_TIME_LABEL_HIGHT);
		
		keyField.setBounds(keyLabel.getX() + keyLabel.getWidth() + COMPONENT_DISTANSE, START_TIME_LABEL_POSITION_Y, START_TIME_FIELD_WIDTH, START_TIME_FIELD_HIGHT);
		
		JButton queryButton = new JButton(QUERY_BUTTON_CONTENT);
		queryButton.setBounds(keyField.getX() + keyField.getWidth() + COMPONENT_DISTANSE, START_TIME_LABEL_POSITION_Y, START_TIME_LABEL_WIDTH, START_TIME_LABEL_HIGHT);
		
		
		queryPanel.add(startTimeLabel);
		queryPanel.add(startTimeField);
		queryPanel.add(endTimeLabel);
		queryPanel.add(endTimeField);
		queryPanel.add(queryLocationLabel);
		queryPanel.add(queryColumnNameComboBox);
		queryPanel.add(keyLabel);
		queryPanel.add(keyField);
		queryPanel.add(queryButton);
		
		queryButton.addActionListener(new PatientQueryButtonListener(this));
	}
	
	private void createPatientTablePanel() {
		PatientTableModel patientTableModel = PatientTableModel.getInstance();
		patientTable = new JTable(patientTableModel);
		patientTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		patientTablePanel.setLayout(null);
		JScrollPane scrollPane = new JScrollPane(patientTable);
		scrollPane.setBounds(0, 0, PATIENT_SCROLL_PANE_WIDTH, ScreenSizeUtils.screenHeight-QUERY_PANEL_HEIGHT - FOOTER_HEIGHT);
		patientTablePanel.add(scrollPane);
		patientTable.addMouseListener(new PatientTableMouseAdapter(this));
		FrameUtils.getDataAndRefreshTable(REFRESH_PATIENT_TABLE_COMMAND);
	}
	
	private void createPatientRegistTabbedPane() {
		registPanel.setLayout(null);
		caseManagerPanel.setLayout(null);
		createRegistPanel();
		createCaseManagerPanel();
		patientRegistTabbedPane.add(REGIST_PANEL_TITLE, registPanel);
		patientRegistTabbedPane.add(CASEMANAGER_PANEL_TITLE, caseManagerPanel);
	}
	

	private void createRegistPanel() {
		DMLButtonPanel.setBounds(0, 0, DML_BUTTON_PANEL_WIDTH, DML_BUTTON_PANEL_HEIGHT);
		DMLButtonPanel.setLayout(null);
		createDMLButtonPanel();
		
		registContentPanel.setBounds(0, DML_BUTTON_PANEL_HEIGHT, DML_BUTTON_PANEL_WIDTH, ScreenSizeUtils.screenHeight - DML_BUTTON_PANEL_HEIGHT);
		registContentPanel.setLayout(null);
		createRegistContentPanel();
		
		
		registPanel.add(registContentPanel);
		registPanel.add(DMLButtonPanel);
	}

	private void createRegistContentPanel() {
		ImageIcon lineImg = new ImageIcon(REGIST_CONTENT_LINE_IMGURL);
		lineImg.setImage(lineImg.getImage().getScaledInstance(DML_BUTTON_PANEL_WIDTH,5,Image.SCALE_DEFAULT ));
		JLabel lineLabel = new JLabel(lineImg);
		lineLabel.setBounds(REGIST_TITLE_LABEL_WIDTH, REGIST_TITLE_LABEL_HEIGHT/2,DML_BUTTON_PANEL_WIDTH,LINE_LABEL_HEIGHT);
		registContentPanel.add(lineLabel);
		
		JLabel registTitleLabel = new JLabel(REGIST_TITLE_LABEL_CONTENT);
		registTitleLabel.setBounds(0, 0, REGIST_TITLE_LABEL_WIDTH, REGIST_TITLE_LABEL_HEIGHT);
		registContentPanel.add(registTitleLabel);
		
		JPanel registInputContentPanel = new JPanel();
		registInputContentPanel.setLayout(null);
		registInputContentPanel.setBounds(0, REGIST_TITLE_LABEL_HEIGHT, DML_BUTTON_PANEL_WIDTH, ScreenSizeUtils.screenHeight - DML_BUTTON_PANEL_HEIGHT - REGIST_TITLE_LABEL_HEIGHT);
		createRegistInputContentPanel(registInputContentPanel);
		registContentPanel.add(registInputContentPanel);
		
	}

	private void createRegistInputContentPanel(JPanel registInputContentPanel) {
		int X = REGIST_INPUT_CONTENT_POSITON_X;
		int Y = REGIST_INPUT_CONTENT_POSITON_Y;
		PatientTableModel patientTableModel = PatientTableModel.getInstance();
		for(int i=0;i<PATIENT_ATTRIBUTE_COUNT;i++) {
			String colName = patientTableModel.getColumnName(i);
			JLabel idLabel = new JLabel(colName + ":");
			idLabel.setBounds(X, Y, REGUST_INPUT_CONTENT_LABEL_WIDTH, REGUST_INPUT_CONTENT_LABEL_HEIGHT);
			X += REGUST_INPUT_CONTENT_LABEL_WIDTH;
			
			JComponent field = new JTextField();
			if(colName.equals("地址") || colName.equals("过敏史")) {
				field.setBounds(X, Y, REGUST_INPUT_CONTENT_FIELD_BIG_WIDTH, REGUST_INPUT_CONTENT_FIELD_HEIGHT);
			} else if(colName.equals("初诊意见") || colName.equals("备注")) {
				field = new JTextArea();
				field.setBorder (BorderFactory.createLineBorder(Color.GRAY,1));
				field.setBounds(X, Y, REGUST_INPUT_CONTENT_FIELD_BIG_WIDTH, REGUST_INPUT_CONTENT_AREA_HEIGHT);	
			} else {
				if(colName.equals("性别")) {
					String[] contents = StringUtils.map2StringArr(PatientUtils.sexRule);
					field = new JComboBox<>(contents);
				} else if(colName.equals("婚姻状态")) {
					String[] contents = StringUtils.map2StringArr(PatientUtils.marriedRule);
					field = new JComboBox<>(contents);
				} else if(colName.equals("职业")) {
					String[] contents = StringUtils.map2StringArr(PatientUtils.jobRule);
					field = new JComboBox<>(contents);
				} else if(colName.equals("血型")) {
					String[] contents = StringUtils.map2StringArr(PatientUtils.bloodRule);
					field = new JComboBox<>(contents);
				} else if (colName.equals("年龄")) {
					DateChooser ageDateChooser = DateChooser.getInstance("yyyy-MM-dd");
					idLabel.setText("出生日期:");
					ageDateChooser.register(field);
				} else if (colName.equals("登记日期")) {
					DateChooser registDateChooser = DateChooser.getInstance("yyyy-MM-dd");
					registDateChooser.register(field);
				}
				field.setBounds(X, Y, REGUST_INPUT_CONTENT_FIELD_SMALL_WIDTH, REGUST_INPUT_CONTENT_FIELD_HEIGHT);				
				X += REGUST_INPUT_CONTENT_FIELD_SMALL_WIDTH + COMPONENT_DISTANSE;
			}
			if((i-1)%2==0 || field.getWidth() != REGUST_INPUT_CONTENT_FIELD_SMALL_WIDTH) {
				X = REGIST_INPUT_CONTENT_POSITON_X;
				Y += field.getHeight() + COMPONENT_DISTANSE;			
			}
			registContentFields.add(field);
			registInputContentPanel.add(field);
			registInputContentPanel.add(idLabel);
		}	
	}

	private void createDMLButtonPanel() {
		for(int i=0,positionX=0;i<registButtonImgUrl.size();i++) {
			JButton button = FrameUtils.addButton(registButtonImgUrl.get(i), positionX, DML_BUTTON_WIDTH, DML_BUTTON_PANEL_HEIGHT, DMLButtonPanel);
			button.setName(registButtonImgUrl.get(i));
			if("./img/004.PNG".equals(registButtonImgUrl.get(i)) || "./img/005.PNG".equals(registButtonImgUrl.get(i))) {
				button.setEnabled(false);
			}
			registDMLButtons.add(button);
			button.addActionListener(new RegistDMLButtonListener(this));
			positionX += DML_BUTTON_WIDTH;
		}
		
	}
	
	private void createCaseManagerPanel() {
		caseManagerChildpanel.setBounds(0, 0, ScreenSizeUtils.screenWidth - SPLIT_PANE_DIVIDER_LOCATION, contentPane.getHeight() - CASE_TABLE_HEIGHT);
		createCaseDescriptionPanel();
		
		createCaseTablePane();
		JScrollPane caseTablePane = new JScrollPane(caseTable);
		caseTablePane.setBounds(0, caseManagerChildpanel.getHeight(), caseManagerChildpanel.getWidth(), CASE_TABLE_HEIGHT);
		caseManagerPanel.add(caseManagerChildpanel);
		caseManagerPanel.add(caseTablePane);
		FrameUtils.getDataAndRefreshTable(REFRESH_CASE_TABLE_COMMAND);		
		caseTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}


	private void createCaseTablePane() {
		CaseTableModel caseTableModel = CaseTableModel.getInstance();
		
		caseTable = new JTable(caseTableModel);	
		caseTable.addMouseListener(new PatientTableMouseAdapter(this));
	}

	private void createCaseDescriptionPanel() {
		JPanel caseDescriptionPanel = new JPanel();
		caseDescriptionPanel.setLayout(null);
		
		JPanel caseDescriptionToolPanel = new JPanel();
		caseDescriptionToolPanel.setBounds(0, 0, caseManagerChildpanel.getWidth(), CASE_DESCRIPTION_TOOL_PANEL_HEIGHT );
		caseDescriptionToolPanel.setLayout(null);
		createCaseDescriptionToolPanel(caseDescriptionToolPanel);
		
		JPanel caseDescriptionInputContentPanel = new JPanel();
		caseDescriptionInputContentPanel.setBounds(0, caseDescriptionToolPanel.getHeight(), caseManagerChildpanel.getWidth(), caseManagerChildpanel.getHeight() - CASE_DESCRIPTION_TOOL_PANEL_HEIGHT );
		caseDescriptionInputContentPanel.setLayout(null);
		createCaseDescriptionInputContentPanel(caseDescriptionInputContentPanel);

		caseDescriptionPanel.add(caseDescriptionToolPanel);
		caseDescriptionPanel.add(caseDescriptionInputContentPanel);
		caseManagerChildpanel.add(CASE_DESCRIPTION_TITLE, caseDescriptionPanel);	
	}

	

	private void createCaseDescriptionToolPanel(JPanel caseDescriptionPanel) {
		for(int i=0,positionX=0;i<caseButtonImgUrl.size();i++) {
			JButton button = FrameUtils.addButton(caseButtonImgUrl.get(i), positionX, DML_BUTTON_WIDTH, DML_BUTTON_PANEL_HEIGHT, caseDescriptionPanel);
			button.setName(caseButtonImgUrl.get(i));
			if("./img/004.PNG".equals(caseButtonImgUrl.get(i)) || "./img/005.PNG".equals(caseButtonImgUrl.get(i))) {
				button.setEnabled(false);
			}
			caseDMLButtons.add(button);
//			button.addActionListener(new RegistDMLButtonListener(this));
			positionX += DML_BUTTON_WIDTH;
		}
		
		for(int i=0;i<CASE_TOOL_LABEL_COUNT;i++) {
			JLabel label = new  JLabel(CASE_TOOL_LABEL_PATIENT_ID_TITLEs[i]);
			label.setBounds(COMPONENT_DISTANSE + START_TIME_FIELD_WIDTH*i, DML_BUTTON_PANEL_HEIGHT + COMPONENT_DISTANSE, START_TIME_FIELD_WIDTH, CASE_TOOL_LABEL_PATIENT_ID_HEIGHT);
			caseDescriptionPanel.add(label);
			caseToolLabels.add(label);
		}
		
		CaseTableModel caseTableModel = CaseTableModel.getInstance();
		JLabel examinationTimeLabel = new JLabel(caseTableModel.getColumnName(0) + ":");
		examinationTimeLabel.setBounds(caseToolLabels.get(CASE_TOOL_LABEL_COUNT-1).getX() + caseToolLabels.get(CASE_TOOL_LABEL_COUNT-1).getWidth(), DML_BUTTON_PANEL_HEIGHT + COMPONENT_DISTANSE, REGUST_INPUT_CONTENT_LABEL_WIDTH, CASE_TOOL_LABEL_PATIENT_ID_HEIGHT);
		
		DateChooser examinationTimeChooser = DateChooser.getInstance("yyyy-MM-dd");
		JTextField examinationTimeField = new JTextField();
		examinationTimeField.setBounds(examinationTimeLabel.getX() + examinationTimeLabel.getWidth(), DML_BUTTON_PANEL_HEIGHT + COMPONENT_DISTANSE, REGUST_INPUT_CONTENT_FIELD_SMALL_WIDTH, CASE_TOOL_LABEL_PATIENT_ID_HEIGHT);
		examinationTimeChooser.register(examinationTimeField);
		
		
		caseDescriptionPanel.add(examinationTimeField);
		caseDescriptionPanel.add(examinationTimeLabel);
		
		caseDescriptionLabels.add(examinationTimeField);
		
	}
	
	private void createCaseDescriptionInputContentPanel(JPanel caseDescriptionInputContentPanel) {
		int X = REGIST_INPUT_CONTENT_POSITON_X;
		int Y = REGIST_INPUT_CONTENT_POSITON_Y;
		CaseTableModel caseTableModel = CaseTableModel.getInstance();
		for(int i=1;i<CASE_ATTRIBUTE_COUNT;i++) {
			String colName = caseTableModel.getColumnName(i);
			JLabel idLabel = new JLabel(colName + ":");
			idLabel.setBounds(X, Y, REGUST_INPUT_CONTENT_LABEL_WIDTH, REGUST_INPUT_CONTENT_LABEL_HEIGHT);
			X += REGUST_INPUT_CONTENT_LABEL_WIDTH;
			
			JTextComponent field = new JTextArea();
			field.setBorder (BorderFactory.createLineBorder(Color.GRAY,1));
			field.setBounds(X, Y, CASE_INPUT_CONTENT_AREA_WIDTH, CASE_INPUT_CONTENT_AREA_HEIGHT);	
			
			X = REGIST_INPUT_CONTENT_POSITON_X;
			Y += field.getHeight();	
			
			caseDescriptionLabels.add(field);
			caseDescriptionInputContentPanel.add(field);
			caseDescriptionInputContentPanel.add(idLabel);
		}	
	}

	public static void main(String[] args) {
		ConsultFrame c = new ConsultFrame();
		c.execute();
	}
	
	
	public JTextField getStartTimeField() {
		return startTimeField;
	}


	public JTextField getEndTimeField() {
		return endTimeField;
	}


	public JComboBox<String> getQueryColumnNameComboBox() {
		return queryColumnNameComboBox;
	}


	public JTextField getKeyField() {
		return keyField;
	}

	public static int getDmlButtonPanelWidth() {
		return DML_BUTTON_PANEL_WIDTH;
	}

	public List<JComponent> getRegistContentFields() {
		return registContentFields;
	}

	public List<JButton> getRegistDMLButtons() {
		return registDMLButtons;
	}

	public List<JLabel> getCaseToolLabels() {
		return caseToolLabels;
	}
	
}
