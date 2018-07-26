package cn.kgc.dao.intf;

import java.util.List;

import cn.kgc.dto.MedicineDto;
import cn.kgc.model.Medicine;

public interface MedicineDao {

	List<Medicine> query() throws Exception;

	List<Medicine> queryByTypeId(String typeId) throws Exception;

	List<Medicine> queryBySearch(MedicineDto medicineDto) throws Exception;

}
