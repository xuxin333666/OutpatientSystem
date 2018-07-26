package cn.kgc.service.impl;

import java.util.List;
import java.util.Vector;

import cn.kgc.dao.impl.MedicineTypeDaoImpl;
import cn.kgc.dao.intf.MedicineTypeDao;
import cn.kgc.model.MedicineType;
import cn.kgc.service.intf.MedicineTypeService;

public class MedicineTypeServiceImpl implements MedicineTypeService {
	MedicineTypeDao medicineTypeDao = new MedicineTypeDaoImpl();

	@Override
	public List<MedicineType> getAllInfoByParentId(String parentId) throws Exception {
		return medicineTypeDao.query(parentId);
	}

	@Override
	public int addTypeNode(MedicineType type) throws Exception {
		String id = medicineTypeDao.queryMinEmptyId();
		type.setId(id);
		return medicineTypeDao.insert(type);
	}

	@Override
	public int modifyTypeNode(MedicineType selectedType) throws Exception {
		return medicineTypeDao.update(selectedType);
	}

	@Override
	public int deleteTypeNode(MedicineType selectedType) throws Exception {
		return medicineTypeDao.delete(selectedType);
	}

	@Override
	public Vector<MedicineType> getAllInfoByNoChildId() throws Exception {
		List<MedicineType> types = medicineTypeDao.queryByNoChildId();
		Vector<MedicineType> typesVetor = new Vector<>();
		typesVetor.addAll(types);
		return typesVetor;
	}


}
