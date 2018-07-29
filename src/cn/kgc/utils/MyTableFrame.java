package cn.kgc.utils;

import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * jtable的包装类
 * @author xuxin
 *
 */
public class MyTableFrame {
	private JTable table;	//table控件对象
	private JScrollPane scrollPane;		//用来装table的scrollpane控件
	private JPanel panel;	//用来装scrollpane的jpanel控件
	private Class<?> clazz;		//获取表格数据需要调用的Service类的Class对象
	private Object dto;		//获取表格数据需要增加的搜索条件dto
	private String methodName;	//全条件查询方法名称
	private String searchMethodName;	//条件查询方法名称

	
	/**
	 * 构造方法，创建一个jtable实例，并装入scrollpane中。
	 * @param obj
	 * @param clazz
	 */
	public MyTableFrame(Object obj,Class<?> clazz) {
		this.clazz = clazz;
		AbstractTableModel tableModel = (AbstractTableModel)obj;
		table = new JTable(tableModel);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(table);
	}
	
	/**
	 * 构造方法，创建一个jtable实例，给定搜索方法的名称。并装入scrollpane中。
	 * @param obj
	 * @param clazz
	 * @param methodName
	 * @param searchMethodName
	 */
	public MyTableFrame(Object obj,Class<?> clazz,String methodName, String searchMethodName) {
		this.methodName = methodName;
		this.searchMethodName = searchMethodName;
		this.clazz = clazz;
		AbstractTableModel tableModel = (AbstractTableModel)obj;
		table = new JTable(tableModel);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(table);
	}
	

	
	/**
	 * 用户传入一个装table的scrollpane的jpanel控件实例，将创建好的jtable装进去。并调用全条件刷新表格方法
	 * @param panel
	 */
	public void regist(JPanel panel) {	
		this.panel = panel;
		panel.add(scrollPane);
		getDataAndRefreshTable();
	}
	
	
	/**
	 * 用户传入一个装table的scrollpane的tabbed控件实例，将创建好的jtable装进去。并调用全条件刷新表格方法
	 * @param title
	 * @param tabedPane
	 */
	public void regist(String title,JTabbedPane tabedPane) {	
		tabedPane.addTab(title, scrollPane);
		getDataAndRefreshTable();
	}
	
	/**
	 * 根据用户传入的Bounds参数，设置scrollpane，并将上层的jpanel设置为无布局管理器
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void setScrollPaneBounds(int x, int y, int width, int height) {
		if(panel != null) {
			panel.setLayout(null);
			scrollPane.setBounds(x, y,width, height);			
		}
	}
	
	
	/**
	 * 将用户传入的鼠标监听添加至jtable中
	 * @param mouseListener
	 */
	public void addMouseListener(MouseListener mouseListener) {
		table.addMouseListener(mouseListener);
	}

	/**
	 * 通过反射方式，调用相应的Service类的获取表格数据的方法，获得二维数组后传入刷新表格方法
	 */
	public void getDataAndRefreshTable() {
		try {
			Object service = clazz.newInstance();
			Object[][] datas;
			String mName = "getAllInfo";
			String sMName = "getAllInfoBySearch";
			if(methodName != null) {
				mName = methodName;
			}
			if(searchMethodName != null) {
				sMName = searchMethodName;
			}
			if(dto == null) {
				Method method = clazz.getMethod(mName);
				datas = (Object[][])method.invoke(service);			
			} else {
				Method method = clazz.getMethod(sMName,dto.getClass());
				datas = (Object[][])method.invoke(service,dto);
			}
			refreshTable(datas);			
		} catch (Exception e) {
			e.printStackTrace();
			FrameUtils.DialogErorr(e.getMessage());
		} finally {
			dto = null;
		}
	}
	
	/**
	 * 通过反射方式，调用相应的Service类给定的方法，获得二维数组后传入刷新表格方法
	 */
	public void getDataAndRefreshTableWithMethodName(String methodName) {
		this.methodName = methodName;
		getDataAndRefreshTable();
	}
		
	/**
	 * 通过反射方式，调用相应的Service类的有条件获取表格数据的方法，获得二维数组后传入刷新表格方法
	 * @param dto
	 */
	public void getDataAndRefreshTable(Object dto) {
		this.dto = dto;
		getDataAndRefreshTable();
	}
	
	/**
	 * 通过反射方式，调用相应的Service类的给定的方法，获得二维数组后传入刷新表格方法
	 * @param dto
	 */
	public void getDataAndRefreshTableWithMethodName(String searchMethodName,Object dto) {
		this.searchMethodName = searchMethodName;
		this.dto = dto;
		getDataAndRefreshTable();
	}
	
	/**
	 * 刷新表格的方法，通过反射调用不同的tablemodel，重新设置datas，刷新表格
	 * @param datas
	 * @throws Exception
	 */
	public void refreshTable(Object[][] datas) throws Exception {
		TableModel model = table.getModel();
		Field field = model.getClass().getDeclaredField("datas");
		field.setAccessible(true);
		field.set(model, datas);
		table.updateUI();
	}
	
	/**
	 * 获取用户选择的行数相应列数的数据，并要求用户只能选择一行数据。
	 * @param columnNum
	 * @return
	 * @throws Exception
	 */
	public Object getTableSelectedRowInfo(int columnNum) throws Exception {
		if(table.getSelectedRowCount() == 1) {
			int rowNo = table.getSelectedRow();
			return table.getValueAt(rowNo, columnNum);
		}
		throw new Exception("请选择一行");
	}

	public JTable getTable() {
		return table;
	}

	/**
	 * 设置表格的激活状态
	 * @param b
	 */
	public void enable(boolean b) {
		table.setEnabled(b);
		
	}
	
	
	
}
