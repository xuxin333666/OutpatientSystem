package cn.kgc.service.intf;

import java.util.List;

import cn.kgc.model.PrescriptionMedicine;

public interface PrescriptionMedicineService {

	String getMinEmptyId() throws Exception;

	int add(List<PrescriptionMedicine> prescriptionMedicines) throws Exception;

}
