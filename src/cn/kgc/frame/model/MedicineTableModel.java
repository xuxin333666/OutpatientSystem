package cn.kgc.frame.model;


import javax.swing.table.AbstractTableModel;

public class MedicineTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2768971685554043464L;
	private static MedicineTableModel patientTableModel;
	private Object[][] datas;
	private final String[] columnName = {"编号","名称","规格","单位","销售价","用法","注意事项"};


	
	private MedicineTableModel() {}
	
	public static MedicineTableModel getInstance() {
		if(patientTableModel == null) {
			patientTableModel = new MedicineTableModel();
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

	public void setDatas(Object[][] datas) {
		this.datas = datas;
	}


}
