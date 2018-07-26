package cn.kgc.service.intf;

import java.util.List;

import cn.kgc.model.MedicineType;

public interface MedicineTypeService {


	List<MedicineType> getAllInfoByParentId(String parentId) throws Exception;

	int addTypeNode(MedicineType type) throws Exception;

	int modifyTypeNode(MedicineType selectedType) throws Exception;

	int deleteTypeNode(MedicineType selectedType) throws Exception;

}
