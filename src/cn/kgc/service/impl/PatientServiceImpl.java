package cn.kgc.service.impl;

import java.util.List;

import cn.kgc.dao.impl.PatientDaoImpl;
import cn.kgc.dao.intf.PatientDao;
import cn.kgc.dto.PatientDto;
import cn.kgc.model.Patient;
import cn.kgc.service.intf.PatientService;
import cn.kgc.utils.PatientUtils;

public class PatientServiceImpl implements PatientService {
	private PatientDao patientDao = new PatientDaoImpl();

	@Override
	public Object[][] getAllPatientInfo() throws Exception {
		List<Patient> patients = patientDao.query();
		return PatientUtils.list2Array(patients);
	}

	@Override
	public Object[][] getAllPatientInfoBySearch(PatientDto patientDto) throws Exception {
		List<Patient> patients = patientDao.query(patientDto);
		return PatientUtils.list2Array(patients);
	}
	
	@Override
	public Patient getPatientInfoById(String id) throws Exception {
		return patientDao.query(id);
	}

	@Override
	public String getMinEmptyId() throws Exception {
		return patientDao.queryMinEmptyId();
	}

	@Override
	public int add(Patient patient) throws Exception {
		return patientDao.insert(patient);
	}

	@Override
	public int modify(Patient patient) throws Exception {
		return patientDao.update(patient);
	}

	@Override
	public int delete(String id) throws Exception {
		return patientDao.delete(id);
	}

}
