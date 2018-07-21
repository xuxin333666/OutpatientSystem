package cn.kgc.service.impl;

import java.util.List;

import cn.kgc.dao.impl.PatientDaoImpl;
import cn.kgc.dao.intf.PatientDao;
import cn.kgc.dto.PatientDto;
import cn.kgc.model.Patient;
import cn.kgc.service.intf.PatientService;
import cn.kgc.utils.PatientUtils;

public class PatientServiceImpl implements PatientService {

	@Override
	public Object[][] getAllPatientInfo() throws Exception {
		PatientDao patientDao = new PatientDaoImpl();
		List<Patient> patients = patientDao.query();
		return PatientUtils.list2Array(patients);
	}

	@Override
	public Object[][] getAllPatientInfoBySearch(PatientDto patientDto) throws Exception {
		PatientDao patientDao = new PatientDaoImpl();
		List<Patient> patients = patientDao.query(patientDto);
		return PatientUtils.list2Array(patients);
	}

}
