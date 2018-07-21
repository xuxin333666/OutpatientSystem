package cn.kgc.service.intf;

import cn.kgc.dto.PatientDto;

public interface PatientService {

	Object[][] getAllPatientInfo() throws Exception;

	Object[][] getAllPatientInfoBySearch(PatientDto patientDto) throws Exception;

}
