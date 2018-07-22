package cn.kgc.frame;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import cn.kgc.frame.intf.BusinessButtonFrameIntf;
import cn.kgc.frame.listener.PatientQueryButtonListener;
import cn.kgc.frame.model.PatientTableModel;
import cn.kgc.service.impl.PatientServiceImpl;
import cn.kgc.service.intf.PatientService;
import cn.kgc.utils.DateChooser;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.ScreenSizeUtils;

public class ConsultFrame implements BusinessButtonFrameIntf {
	private static final String TITLE = "患 者 咨 询";
	private static final String REGIST_PANEL_TITLE = "初 诊 登 记";
	private static final String CASEMANAGER_PANEL_TITLE = "病 例 管 理";
	private static final String START_TIME_LABEL_CONTENT = "起始时间：";
	private static final String END_TIME_LABEL_CONTENT = "截止时间：";
	private static final String QUERY_LOCATION_LABEL_CONTENT = "查询位置：";
	private static final String KEY_LABEL_CONTENT = "关键字：";
	private static final String QUERY_BUTTON_CONTENT = "搜索";
	private static final String[] QUERY_KEY_LIST = {"证号/姓名","性别","婚姻状况","职业","联系地址","初诊处理意见","初诊备注"};   
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
	
	
	private JFrame jFrame = new JFrame(TITLE);
	private JPanel patientTablePanel = new JPanel();
	private JTabbedPane patientRegistTabbedPane = new JTabbedPane();
	private JSplitPane contentPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, patientTablePanel, patientRegistTabbedPane);
	private static JTable patientTable;
	
	private JPanel queryPanel = new JPanel();
	private JTextField startTimeField  = new JTextField();
	private JTextField endTimeField  = new JTextField();
	private JComboBox<String> queryColumnNameComboBox;
	private JTextField keyField  = new JTextField();
	
	
	
	@Override
	public void execute(BusinessButtonFrame businessButtonFrame) {
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
		patientTablePanel.setLayout(null);
		JScrollPane scrollPane = new JScrollPane(patientTable);
		scrollPane.setBounds(0, 0, PATIENT_SCROLL_PANE_WIDTH, ScreenSizeUtils.screenHeight-QUERY_PANEL_HEIGHT - FOOTER_HEIGHT);
		patientTablePanel.add(scrollPane);
		getDataAndRefreshTable();
	}
	
	private void createPatientRegistTabbedPane() {
		JPanel registPanel = new JPanel();
		JPanel caseManagerPanel = new JPanel();
		patientRegistTabbedPane.add(REGIST_PANEL_TITLE, registPanel);
		patientRegistTabbedPane.add(CASEMANAGER_PANEL_TITLE, caseManagerPanel);
	}
	
	private void getDataAndRefreshTable() {
		PatientService patientService = new PatientServiceImpl();
		Object[][] datas;
		try {		
			datas = patientService.getAllPatientInfo();
			refreshTable(datas);
		} catch (Exception e) {
			FrameUtils.DialogErorr(e.getMessage());
		}
	}
	
	public static void refreshTable(Object[][] datas) {
		PatientTableModel patientTableModel = PatientTableModel.getInstance();
		patientTableModel.setDatas(datas);
		patientTable.updateUI();
	}
	
	public static void main(String[] args) {
		BusinessButtonFrame businessButtonFrame = new BusinessButtonFrame(new JPanel());
		ConsultFrame c = new ConsultFrame();
		c.execute(businessButtonFrame);
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
	
}
