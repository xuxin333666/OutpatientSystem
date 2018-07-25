package cn.kgc.frame;


import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.tree.DefaultMutableTreeNode;

import cn.kgc.model.MedicineType;
import cn.kgc.frame.intf.BaseBusinessButtonFrame;
import cn.kgc.frame.listener.MedicineDMLButtonListener;
import cn.kgc.frame.listener.MedicineTreeMouseAdapter;
import cn.kgc.frame.model.MedicineTableModel;
import cn.kgc.service.impl.MedicineServiceImpl;
import cn.kgc.service.impl.MedicineTypeServiceImpl;
import cn.kgc.service.intf.MedicineTypeService;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.ScreenSizeUtils;

public class MedicineFrame implements BaseBusinessButtonFrame {
	private static final String TITLE = "药 品 设 置";
	private static final String TABED_DRUG_LIST_TITLE = "药品列表";
	private static final int WIDTH = 800;
	private static final int HEIGHT = 500;
	
	private static final int TOOL_PANEL_WIDTH = WIDTH;
	private static final int TOOL_PANEL_HEIGHT = 60;
	private static final int SPLIT_DIVIDER_LOCATION = 250;
	
	
	private static final int MEDICINE_DML_BUTTON_WIDTH = 60;
	
	private JFrame medicineFrame = new JFrame(TITLE);
	private JPanel medicineToolPanel = new JPanel();
	private JTabbedPane medicineTabbedPane = new JTabbedPane();
	private JScrollPane medicineTreePanel = new JScrollPane();
	private JSplitPane medicineSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, medicineTreePanel, medicineTabbedPane);
	
	private static JTable medicineTable;
	private static  JTree tree;
	
	
	private List<String> medicineButtonImgUrl;
	private List<JButton> medicineDMLButtons = new ArrayList<>();
	private MedicineTypeService medicineTypeService = new MedicineTypeServiceImpl();
	
	@Override
	public void execute() {
		
		medicineFrame.setBounds(ScreenSizeUtils.HorizontalCenterPosition(WIDTH), ScreenSizeUtils.VerticalCenter(HEIGHT), WIDTH, HEIGHT);		
		medicineFrame.setLayout(null);
		
		createLayout();
		createDrugToolPanel();
		createDrugTabbedPane();
		createDrugTreePanel();
		
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



	private void createDrugToolPanel() {
		medicineToolPanel.setLayout(null);
		MedicineDMLButtonListener medicineDMLButtonListener = new MedicineDMLButtonListener(this);
		medicineButtonImgUrl = medicineDMLButtonListener.getList();
		for (int i = 0,positionX=0; i < medicineButtonImgUrl.size(); i++) {
			JButton button = FrameUtils.addButton(medicineButtonImgUrl.get(i), positionX, MEDICINE_DML_BUTTON_WIDTH, TOOL_PANEL_HEIGHT, medicineToolPanel);
			button.setName(medicineButtonImgUrl.get(i));
			medicineDMLButtons.add(button);
			button.addActionListener(medicineDMLButtonListener);
			positionX += MEDICINE_DML_BUTTON_WIDTH;
		} 

	}





	private void createDrugTreePanel() {		
			//建tree
		List<MedicineType> listArr = medicineTypeService.getAllInfoByParentId(null);
		if(listArr.size() != 0) {
			DefaultMutableTreeNode root = new DefaultMutableTreeNode(listArr.get(0));  
			tree = new JTree(root);	
			List<MedicineType> listArrChild = medicineTypeService.getAllInfoByParentId(listArr.get(0).getId());
			addTreeNode(root,listArrChild);
			
			tree.addMouseListener(new MedicineTreeMouseAdapter(tree));
			medicineTreePanel.getViewport().add(tree); 		
		}				 
	}



	
	
	private void addTreeNode(DefaultMutableTreeNode treeNode, List<MedicineType> listArr) {
		if(listArr.size() != 0) {			
			for (MedicineType medicineType : listArr) {
				DefaultMutableTreeNode treeNodeChild = new DefaultMutableTreeNode(medicineType);  
				treeNode.add(treeNodeChild);
				List<MedicineType> listArrChild = medicineTypeService.getAllInfoByParentId(medicineType.getId());
				addTreeNode(treeNodeChild,listArrChild);
			}
		}	
	}






	public static void main(String[] args) {
		MedicineFrame c = new MedicineFrame();
		c.execute();
	}

}
