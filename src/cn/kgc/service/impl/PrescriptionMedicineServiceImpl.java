package cn.kgc.service.impl;

import java.util.List;

import cn.kgc.dao.impl.PrescriptionMedicineDaoImpl;
import cn.kgc.dao.intf.PrescriptionMedicineDao;
import cn.kgc.model.PrescriptionMedicine;
import cn.kgc.service.intf.PrescriptionMedicineService;

public class PrescriptionMedicineServiceImpl implements PrescriptionMedicineService {
	PrescriptionMedicineDao prescriptionMedicineDao = new PrescriptionMedicineDaoImpl();
	@Override
	public String getMinEmptyId() throws Exception {
		return prescriptionMedicineDao.queryMinEmptyId();
	}
	@Override
	public int add(List<PrescriptionMedicine> prescriptionMedicines) throws Exception {
		int status = 0;
		for (PrescriptionMedicine prescriptionMedicine : prescriptionMedicines) {
			status += prescriptionMedicineDao.insert(prescriptionMedicine);
		}
		return status;
	}

}
