package cn.kgc.dao.intf;

import java.util.List;

import cn.kgc.model.MedicineType;

public interface MedicineTypeDao {

	List<MedicineType> query(String parentId) throws Exception;

	String queryMinEmptyId() throws Exception;

	int insert(MedicineType type) throws Exception;

	int update(MedicineType selectedType) throws Exception;

	int delete(MedicineType selectedType) throws Exception;

}
