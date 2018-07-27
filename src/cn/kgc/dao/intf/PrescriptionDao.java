package cn.kgc.dao.intf;

import cn.kgc.model.Prescription;

public interface PrescriptionDao {

	String queryMinEmptyId() throws Exception;

	int insert(Prescription prescription) throws Exception;

}
