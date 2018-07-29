package cn.kgc.service.impl;

import java.util.List;

import cn.kgc.dao.impl.MedicineDaoImpl;
import cn.kgc.dao.intf.MedicineDao;
import cn.kgc.dto.MedicineDto;
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
	public Object[][] getAllInfoBySearch(MedicineDto dto) throws Exception {
		List<Medicine> medicines = medicineDao.queryBySearch(dto);	
		return ListUtils.list2TableArray(medicines, 7);
	}

	@Override
	public Object[][] getAllInfoBySearch(String typeId) throws Exception {
		List<Medicine> medicines = medicineDao.queryByTypeId(typeId);
		return ListUtils.list2TableArray(medicines, 7);
	}

	@Override
	public Medicine getInfoById(String id) throws Exception {
		return medicineDao.queryById(id);
	}

	@Override
	public String getMinEmptyId() throws Exception {
		return medicineDao.getMinEmptyId();
	}

	@Override
	public int add(Medicine medicine) throws Exception {
		return medicineDao.insert(medicine);
	}

	@Override
	public int modify(Medicine medicine) throws Exception {
		return medicineDao.update(medicine);
	}

	@Override
	public int delete(String medicineId) throws Exception {
		return medicineDao.delete(medicineId);
	}

	@Override
	public List<Medicine> getAllInfoByTypeId(String typeId) throws Exception {
		return medicineDao.queryByTypeId(typeId);
	}

	@Override
	public Object[][] getAllMedicine() {
		return new Object[0][4];
	}

	@Override
	public Object[][] getMedicineInPrescription(String medicineId) throws Exception {
		List<Medicine> medicines = medicineDao.queryByMedicineId(medicineId);
		return ListUtils.list2TableArray(medicines, 4);
	}
	
	

}
