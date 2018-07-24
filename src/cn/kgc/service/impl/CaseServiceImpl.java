package cn.kgc.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.kgc.dao.impl.CaseDao;
import cn.kgc.dao.impl.CaseDaoImpl;
import cn.kgc.model.Case;
import cn.kgc.service.intf.CaseService;
import cn.kgc.utils.ListUtils;

public class CaseServiceImpl implements CaseService {
	public static final int CASE_ATTRIBUTE_COUNT = 10;
	
	private CaseDao caseDao = new CaseDaoImpl();
	


	@Override
	public Object[][] getAllCaseInfoByPatient(String patientId) throws Exception {
		List<Case> cases = caseDao.queryByPatientId(patientId);
		return ListUtils.list2TableArray(cases,CASE_ATTRIBUTE_COUNT);
	}


	@Override
	public Object[][] getAllCaseInfo() throws IllegalArgumentException, IllegalAccessException {
		List<Case> cases = new ArrayList<>();
		return ListUtils.list2TableArray(cases,CASE_ATTRIBUTE_COUNT);
	}

}
