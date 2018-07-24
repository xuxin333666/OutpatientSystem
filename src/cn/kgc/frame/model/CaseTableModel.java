package cn.kgc.frame.model;


import javax.swing.table.AbstractTableModel;

public class CaseTableModel extends AbstractTableModel implements TableModelSetDate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2768971685554043464L;
	private static CaseTableModel patientTableModel;
	private Object[][] datas;
	private final String[] columnName = {"�������","����","�ֲ�ʷ","����ʷ","����ʷ","�����","ʵ���Ҽ��",
									"���","���","����˵��"};


	
	private CaseTableModel() {}
	
	public static CaseTableModel getInstance() {
		if(patientTableModel == null) {
			patientTableModel = new CaseTableModel();
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
	


	public String[] getColumnName() {
		return columnName;
	}

	@Override
	public void setDatas(Object[][] datas) {
		this.datas = datas;
	}


}
