package cn.kgc.dao.intf;

import java.util.List;

import cn.kgc.model.Medicine;

public interface MedicineDao {

	List<Medicine> query() throws Exception;

}
