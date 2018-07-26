package cn.kgc.dao.intf;

import java.util.List;

import cn.kgc.dto.MedicineDto;
import cn.kgc.model.Medicine;

public interface MedicineDao {

	List<Medicine> query() throws Exception;

	List<Medicine> queryByTypeId(String typeId) throws Exception;

	List<Medicine> queryBySearch(MedicineDto medicineDto) throws Exception;

	String getMinEmptyId() throws Exception;

	int insert(Medicine medicine) throws Exception;

	int delete(String medicineId) throws Exception;

	Medicine queryById(String id) throws Exception;

	int update(Medicine medicine) throws Exception;

}
