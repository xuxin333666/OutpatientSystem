package cn.kgc.service.impl;

import java.util.List;

import cn.kgc.dao.impl.MedicineDaoImpl;
import cn.kgc.dao.intf.MedicineDao;
import cn.kgc.model.Medicine;
import cn.kgc.service.intf.MedicineService;
import cn.kgc.utils.ListUtils;

public class MedicineServiceImpl implements MedicineService {
	private MedicineDao medicineDao = new MedicineDaoImpl();
	@Override
	public Object[][] getAllInfo() throws Exception {
		List<Medicine> medicines = medicineDao.query();
		return ListUtils.list2TableArray(medicines, 7);
	}

	@Override
	public Object[][] getAllInfoBySearch(String patientId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	

}
