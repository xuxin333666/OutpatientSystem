package cn.kgc.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.kgc.dao.impl.CaseDaoImpl;
import cn.kgc.dao.intf.CaseDao;
import cn.kgc.model.Case;
import cn.kgc.service.intf.CaseService;
import cn.kgc.utils.ListUtils;

public class CaseServiceImpl implements CaseService {
	public static final int CASE_ATTRIBUTE_COUNT = 11;
	
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


	@Override
	public Case getCaseInfoById(String caseId, String patientId) throws Exception {
		return caseDao.queryByPatientIdAndId(caseId,patientId);
	}


	@Override
	public int add(Case $case) throws Exception {
		return caseDao.insert($case);
	}


	@Override
	public String getMinEmptyId() throws Exception {
		return caseDao.queryMinEmptyId();
	}


	@Override
	public int modify(Case $case) throws Exception {
		return caseDao.Update($case);
	}


	@Override
	public int delete(String cid) throws Exception {
		return caseDao.delete(cid);
	}

}
