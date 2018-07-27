package cn.kgc.service.intf;

import cn.kgc.model.Prescription;

public interface PrescriptionService {

	String getMinEmptyId() throws Exception;

	int add(Prescription prescription) throws Exception;



}
