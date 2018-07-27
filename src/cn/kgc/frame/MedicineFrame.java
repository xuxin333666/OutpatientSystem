package cn.kgc.frame;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.tree.DefaultMutableTreeNode;

import cn.kgc.model.MedicineType;
import cn.kgc.frame.intf.BaseBusinessButtonFrame;
import cn.kgc.frame.listener.medicineListener.MedicineDMLButtonListener;
import cn.kgc.frame.listener.medicineListener.MedicineSearchKeyListener;
import cn.kgc.frame.listener.medicineListener.MedicineTreeMouseAdapter;
import cn.kgc.frame.model.MedicineTableModel;
import cn.kgc.service.impl.MedicineServiceImpl;
import cn.kgc.service.impl.MedicineTypeServiceImpl;
import cn.kgc.service.intf.MedicineTypeService;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.ScreenSizeUtils;

public class MedicineFrame implements BaseBusinessButtonFrame {
	private static final String TITLE = "药 品 设 置";
	private static final String TABED_DRUG_LIST_TITLE = "药品列表";
	private static final String MEDICINE_SEARCH_LABEL_CONTENT = "药品检索：";
	
	private static final String[] MEDICINE_DML_BUTTON_IMGURL = {"./img/yp_tool_001.PNG","./img/yp_tool_002.PNG","./img/yp_tool_003.PNG","./img/yp_tool_004.PNG","./img/yp_tool_007.PNG",
			"./img/yp_tool_008.PNG","./img/yp_tool_009.PNG"};
	private static final String[] MEDICINE_DML_BUTTON_NAMES = {"add","modify","delete","otherToDo","otherToDo","otherToDo","otherToDo"};
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 500;
	
	private static final int TOOL_PANEL_WIDTH = WIDTH;
	private static final int TOOL_PANEL_HEIGHT = 60;
	private static final int SPLIT_DIVIDER_LOCATION = 150;
	
	
	private static final int MEDICINE_DML_BUTTON_WIDTH = 60;
	private static final int MEDICINE_SEARCH_LABEL_POSITION_X = 100;
	private static final int MEDICINE_SEARCH_LABEL_WIDTH = 70;
	private static final int MEDICINE_SEARCH_LABEL_HEIGHT = 25;
	private static final int MEDICINE_SEARCH_FIELD_WIDTH = 150;
	
	private JFrame medicineFrame = new JFrame(TITLE);
	private JPanel medicineToolPanel = new JPanel();
	private JTabbedPane medicineTabbedPane = new JTabbedPane();
	private JScrollPane medicineTreePanel = new JScrollPane();
	private JSplitPane medicineSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, medicineTreePanel, medicineTabbedPane);
	
	private JTable medicineTable;
	private JTree tree;
	
	private JTextField medicineSearchField = new JTextField();
	

	
	private List<JButton> medicineDMLButtons = new ArrayList<>();
	private MedicineTypeService medicineTypeService = new MedicineTypeServiceImpl();
	
	@Override
	public void execute() {
		
		medicineFrame.setBounds(ScreenSizeUtils.HorizontalCenterPosition(WIDTH), ScreenSizeUtils.VerticalCenter(HEIGHT), WIDTH, HEIGHT);		
		medicineFrame.setLayout(null);
		
		createLayout();
		createDrugToolPanel();
		createDrugTabbedPane();
		createMedicineTreePanel();
		
		medicineFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		int positionX = 0;
		for (int i = 0; i < MEDICINE_DML_BUTTON_IMGURL.length; i++) {
			JButton button = FrameUtils.addButton(MEDICINE_DML_BUTTON_IMGURL[i], positionX, MEDICINE_DML_BUTTON_WIDTH, TOOL_PANEL_HEIGHT, medicineToolPanel);
			button.setName(MEDICINE_DML_BUTTON_NAMES[i]);
			medicineDMLButtons.add(button);
			button.addActionListener(new MedicineDMLButtonListener(this));
			positionX += MEDICINE_DML_BUTTON_WIDTH;
		} 
		
		
		JLabel medicineSearchLabel = new  JLabel(MEDICINE_SEARCH_LABEL_CONTENT);
		medicineSearchLabel.setBounds(positionX + MEDICINE_SEARCH_LABEL_POSITION_X, TOOL_PANEL_HEIGHT/2 - MEDICINE_SEARCH_LABEL_HEIGHT/2, MEDICINE_SEARCH_LABEL_WIDTH, MEDICINE_SEARCH_LABEL_HEIGHT);
	
		medicineSearchField.setBounds(medicineSearchLabel.getX() + medicineSearchLabel.getWidth(), medicineSearchLabel.getY(), MEDICINE_SEARCH_FIELD_WIDTH, MEDICINE_SEARCH_LABEL_HEIGHT);
		medicineSearchField.addKeyListener(new MedicineSearchKeyListener(this));
		
		medicineToolPanel.add(medicineSearchLabel);
		medicineToolPanel.add(medicineSearchField);

	}





	private void createMedicineTreePanel() {		
			//建tree
		List<MedicineType> listArr;
		try {
			listArr = medicineTypeService.getAllInfoByParentId(null);
			if(listArr.size() != 0) {
				DefaultMutableTreeNode root = new DefaultMutableTreeNode(listArr.get(0));  
				tree = new JTree(root);	
				List<MedicineType> listArrChild = medicineTypeService.getAllInfoByParentId(listArr.get(0).getId());
				addTreeNode(root,listArrChild);
				
				tree.addMouseListener(new MedicineTreeMouseAdapter(this));
				medicineTreePanel.getViewport().add(tree); 		
			}				 
		} catch (Exception e) {
			FrameUtils.DialogErorr("错误，" + e.getMessage());
			e.printStackTrace();
		}
	}



	
	
	private void addTreeNode(DefaultMutableTreeNode treeNode, List<MedicineType> listArr) throws Exception {
		if(listArr.size() != 0) {			
			for (MedicineType medicineType : listArr) {
				DefaultMutableTreeNode treeNodeChild = new DefaultMutableTreeNode(medicineType);  
				treeNode.add(treeNodeChild);
				List<MedicineType> listArrChild = medicineTypeService.getAllInfoByParentId(medicineType.getId());
				addTreeNode(treeNodeChild,listArrChild);
			}
		}	
	}


	
	public JTable getMedicineTable() {
		return medicineTable;
	}



	public JTree getTree() {
		return tree;
	}




	public JTextField getMedicineSearchField() {
		return medicineSearchField;
	}






	public static void main(String[] args) {
		MedicineFrame c = new MedicineFrame();
		c.execute();
	}

}
