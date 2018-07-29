package cn.kgc.dao.intf;

import cn.kgc.model.PrescriptionMedicine;

public interface PrescriptionMedicineDao {

	String queryMinEmptyId() throws Exception;

	int insert(PrescriptionMedicine prescriptionMedicine) throws Exception;

}
