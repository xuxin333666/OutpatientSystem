package cn.kgc.frame.model;

import javax.swing.table.AbstractTableModel;

public class PatientTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2768971685554043463L;
	private static PatientTableModel patientTableModel;
	private Object[][] datas;
	private final String[] columnName = {"医疗证号","姓名","性别","年龄","婚姻状态","职业","体重",
									"血型","联系电话","登记日期","地址","过敏史","备注"};

	
	
	private PatientTableModel() {}
	
	public static PatientTableModel getInstance() {
		if(patientTableModel == null) {
			patientTableModel = new PatientTableModel();
		}
		return patientTableModel;
	}

	@Override
	public int getRowCount() {
		return datas.length;
	}

	@Override
	public int getColumnCount() {
		return columnName.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return datas[rowIndex][columnIndex];
	}

	@Override
	public String getColumnName(int column) {
		return columnName[column];
	}
	

	public void setDatas(Object[][] datas) {
		this.datas = datas;
	}

	public String[] getColumnName() {
		return columnName;
	}

}
