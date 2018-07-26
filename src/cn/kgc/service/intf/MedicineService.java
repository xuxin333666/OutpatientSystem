package cn.kgc.service.intf;

import cn.kgc.dto.MedicineDto;
import cn.kgc.model.Medicine;

public interface MedicineService {
	
	Object[][] getAllInfo() throws Exception;
	
	Object[][] getAllInfoBySearch(MedicineDto dto) throws Exception;
	
	Object[][] getAllInfoBySearch(String typeId) throws Exception;

	Medicine getInfoById(String id) throws Exception;

	String getMinEmptyId() throws Exception;

	int add(Medicine medicine) throws Exception;

	int modify(Medicine medicine) throws Exception;

	int delete(String medicineId) throws Exception;
}
