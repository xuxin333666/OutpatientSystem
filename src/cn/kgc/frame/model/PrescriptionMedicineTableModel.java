package cn.kgc.frame.model;


import javax.swing.table.AbstractTableModel;

public class PrescriptionMedicineTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2768971685554043464L;
	private static PrescriptionMedicineTableModel patientTableModel;
	private Object[][] datas;
	private final String[] columnName = {"编号","名称","规格","单位"};


	
	private PrescriptionMedicineTableModel() {}
	
	public static PrescriptionMedicineTableModel getInstance() {
		if(patientTableModel == null) {
			patientTableModel = new PrescriptionMedicineTableModel();
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
