package cn.kgc.service.intf;

import cn.kgc.dto.MedicineDto;

public interface MedicineService {
	
	Object[][] getAllInfo() throws Exception;
	
	Object[][] getAllInfoBySearch(MedicineDto dto) throws Exception;
	
	Object[][] getAllInfoBySearch(String typeId) throws Exception;
}
