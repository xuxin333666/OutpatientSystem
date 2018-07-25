package cn.kgc.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import cn.kgc.frame.intf.BaseBusinessButtonFrame;
import cn.kgc.frame.model.MedicineTableModel;
import cn.kgc.service.impl.MedicineServiceImpl;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.ScreenSizeUtils;

public class MedicineFrame implements BaseBusinessButtonFrame {
	private static final String TITLE = "药 品 设 置";
	private static final String TABED_DRUG_LIST_TITLE = "药品列表";
	private static final int WIDTH = 800;
	private static final int HEIGHT = 500;
	
	private static final int TOOL_PANEL_WIDTH = WIDTH;
	private static final int TOOL_PANEL_HEIGHT = 90;
	private static final int SPLIT_DIVIDER_LOCATION = 250;
	
	private JFrame medicineFrame = new JFrame(TITLE);
	private JPanel medicineToolPanel = new JPanel();
	private JTabbedPane medicineTabbedPane = new JTabbedPane();
	private JPanel medicineTreePanel = new JPanel();
	private JSplitPane medicineSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, medicineTreePanel, medicineTabbedPane);
	
	private static JTable medicineTable;
	
	@Override
	public void execute() {
		
		medicineFrame.setBounds(ScreenSizeUtils.HorizontalCenterPosition(WIDTH), ScreenSizeUtils.VerticalCenter(HEIGHT), WIDTH, HEIGHT);		
		medicineFrame.setLayout(null);
		
		createLayout();
//		createDrugToolPanel();
		createDrugTabbedPane();
//		createDrugTreePanel();
		
		medicineFrame.setVisible(true);
		
	}
	




	private void createLayout() {
		medicineToolPanel.setBounds(0, 0, TOOL_PANEL_WIDTH, TOOL_PANEL_HEIGHT);
		medicineSplitPane.setBounds(0, TOOL_PANEL_HEIGHT, TOOL_PANEL_WIDTH, HEIGHT - TOOL_PANEL_HEIGHT);
		medicineSplitPane.setDividerLocation(SPLIT_DIVIDER_LOCATION);
			
		medicineFrame.add(medicineToolPanel);
		medicineFrame.add(medicineSplitPane);

		
	}
	
	

	private void createDrugTabbedPane() {
		MedicineTableModel drugTableModel = MedicineTableModel.getInstance();
		medicineTable = new JTable(drugTableModel);
		JScrollPane drugTableListPane = new JScrollPane(medicineTable);
		FrameUtils.getDataAndRefreshTable(medicineTable,MedicineServiceImpl.class);

		medicineTabbedPane.add(TABED_DRUG_LIST_TITLE, drugTableListPane);
		
		medicineTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
	}



	public static void main(String[] args) {
		MedicineFrame c = new MedicineFrame();
		c.execute();
	}

}
