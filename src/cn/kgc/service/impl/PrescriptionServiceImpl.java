package cn.kgc.service.impl;

import cn.kgc.dao.impl.PrescriptionDaoImpl;
import cn.kgc.dao.intf.PrescriptionDao;
import cn.kgc.model.Prescription;
import cn.kgc.service.intf.PrescriptionService;

public class PrescriptionServiceImpl implements PrescriptionService {
	PrescriptionDao prescriptionDao = new PrescriptionDaoImpl();
	@Override
	public String getMinEmptyId() throws Exception {
		return prescriptionDao.queryMinEmptyId();
	}
	@Override
	public int add(Prescription prescription) throws Exception {
		return prescriptionDao.insert(prescription);
	}

}
