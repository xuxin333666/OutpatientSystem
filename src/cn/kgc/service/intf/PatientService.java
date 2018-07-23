package cn.kgc.service.intf;

import cn.kgc.dto.PatientDto;
import cn.kgc.model.Patient;

public interface PatientService {

	Object[][] getAllPatientInfo() throws Exception;

	Object[][] getAllPatientInfoBySearch(PatientDto patientDto) throws Exception;

	Patient getPatientInfoById(String id) throws Exception;

	String getMinEmptyId() throws Exception;

}
