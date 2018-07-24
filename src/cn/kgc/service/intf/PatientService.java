package cn.kgc.service.intf;

import cn.kgc.dto.PatientDto;
import cn.kgc.model.Patient;

public interface PatientService {

	Object[][] getAllPatientInfo() throws Exception;

	Object[][] getAllPatientInfoBySearch(PatientDto patientDto) throws Exception;

	Patient getPatientInfoById(String id) throws Exception;

	String getMinEmptyId() throws Exception;

	int add(Patient patient) throws Exception;

	int modify(Patient patient) throws Exception;

	int delete(String id) throws Exception;

}
