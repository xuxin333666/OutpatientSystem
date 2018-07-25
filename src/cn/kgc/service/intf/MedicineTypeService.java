package cn.kgc.service.intf;

import java.util.List;

import cn.kgc.model.MedicineType;

public interface MedicineTypeService {


	List<MedicineType> getAllInfoByParentId(String parentId);

}
