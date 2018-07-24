package cn.kgc.frame.model;


import javax.swing.table.AbstractTableModel;

public class CaseTableModel extends AbstractTableModel implements TableModelSetDate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2768971685554043464L;
	private static CaseTableModel patientTableModel;
	private Object[][] datas;
	private final String[] columnName = {"诊断日期","主述","现病史","既往史","个人史","体格检查","实验室检测",
									"诊断","意见","其他说明"};


	
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
