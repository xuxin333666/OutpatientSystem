package cn.kgc.dao.intf;

import java.util.List;

import cn.kgc.dto.PatientDto;
import cn.kgc.model.Patient;

public interface PatientDao {

	List<Patient> query() throws Exception;

	List<Patient> query(PatientDto patientDto) throws Exception;

	Patient query(String id) throws Exception;

	String queryMinEmptyId() throws Exception;
	
}
