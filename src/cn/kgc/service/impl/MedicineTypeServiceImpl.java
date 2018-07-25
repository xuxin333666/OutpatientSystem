package cn.kgc.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.kgc.dao.impl.MedicineTypeDaoImpl;
import cn.kgc.dao.intf.MedicineTypeDao;
import cn.kgc.model.MedicineType;
import cn.kgc.service.intf.MedicineTypeService;

public class MedicineTypeServiceImpl implements MedicineTypeService {
	MedicineTypeDao medicineTypeDao = new MedicineTypeDaoImpl();

	@Override
	public List<MedicineType> getAllInfoByParentId(String parentId) {
		 List<MedicineType> medicineTypes = new ArrayList<>();
		return medicineTypes;
	}

}
