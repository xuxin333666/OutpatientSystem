package cn.kgc.dao.intf;

import java.util.List;

import cn.kgc.model.MedicineType;

public interface MedicineTypeDao {

	List<MedicineType> query(String parentId);

}
