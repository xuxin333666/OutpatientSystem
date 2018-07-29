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
 * jtable�İ�װ��
 * @author xuxin
 *
 */
public class MyTableFrame {
	private JTable table;	//table�ؼ�����
	private JScrollPane scrollPane;		//����װtable��scrollpane�ؼ�
	private JPanel panel;	//����װscrollpane��jpanel�ؼ�
	private Class<?> clazz;		//��ȡ���������Ҫ���õ�Service���Class����
	private Object dto;		//��ȡ���������Ҫ���ӵ���������dto
	private String methodName;	//ȫ������ѯ��������
	private String searchMethodName;	//������ѯ��������

	
	/**
	 * ���췽��������һ��jtableʵ������װ��scrollpane�С�
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
	 * ���췽��������һ��jtableʵ���������������������ơ���װ��scrollpane�С�
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
	 * �û�����һ��װtable��scrollpane��jpanel�ؼ�ʵ�����������õ�jtableװ��ȥ��������ȫ����ˢ�±�񷽷�
	 * @param panel
	 */
	public void regist(JPanel panel) {	
		this.panel = panel;
		panel.add(scrollPane);
		getDataAndRefreshTable();
	}
	
	
	/**
	 * �û�����һ��װtable��scrollpane��tabbed�ؼ�ʵ�����������õ�jtableװ��ȥ��������ȫ����ˢ�±�񷽷�
	 * @param title
	 * @param tabedPane
	 */
	public void regist(String title,JTabbedPane tabedPane) {	
		tabedPane.addTab(title, scrollPane);
		getDataAndRefreshTable();
	}
	
	/**
	 * �����û������Bounds����������scrollpane�������ϲ��jpanel����Ϊ�޲��ֹ�����
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
	 * ���û�����������������jtable��
	 * @param mouseListener
	 */
	public void addMouseListener(MouseListener mouseListener) {
		table.addMouseListener(mouseListener);
	}

	/**
	 * ͨ�����䷽ʽ��������Ӧ��Service��Ļ�ȡ������ݵķ�������ö�ά�������ˢ�±�񷽷�
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
	 * ͨ�����䷽ʽ��������Ӧ��Service������ķ�������ö�ά�������ˢ�±�񷽷�
	 */
	public void getDataAndRefreshTableWithMethodName(String methodName) {
		this.methodName = methodName;
		getDataAndRefreshTable();
	}
		
	/**
	 * ͨ�����䷽ʽ��������Ӧ��Service�����������ȡ������ݵķ�������ö�ά�������ˢ�±�񷽷�
	 * @param dto
	 */
	public void getDataAndRefreshTable(Object dto) {
		this.dto = dto;
		getDataAndRefreshTable();
	}
	
	/**
	 * ͨ�����䷽ʽ��������Ӧ��Service��ĸ����ķ�������ö�ά�������ˢ�±�񷽷�
	 * @param dto
	 */
	public void getDataAndRefreshTableWithMethodName(String searchMethodName,Object dto) {
		this.searchMethodName = searchMethodName;
		this.dto = dto;
		getDataAndRefreshTable();
	}
	
	/**
	 * ˢ�±��ķ�����ͨ��������ò�ͬ��tablemodel����������datas��ˢ�±��
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
	 * ��ȡ�û�ѡ���������Ӧ���������ݣ���Ҫ���û�ֻ��ѡ��һ�����ݡ�
	 * @param columnNum
	 * @return
	 * @throws Exception
	 */
	public Object getTableSelectedRowInfo(int columnNum) throws Exception {
		if(table.getSelectedRowCount() == 1) {
			int rowNo = table.getSelectedRow();
			return table.getValueAt(rowNo, columnNum);
		}
		throw new Exception("��ѡ��һ��");
	}

	public JTable getTable() {
		return table;
	}

	/**
	 * ���ñ��ļ���״̬
	 * @param b
	 */
	public void enable(boolean b) {
		table.setEnabled(b);
		
	}
	
	
	
}
