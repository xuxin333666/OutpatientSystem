package cn.kgc.service.intf;

import cn.kgc.model.Case;

public interface CaseService {

	Object[][] getAllCaseInfoByPatient(String patientId) throws Exception;

	Object[][] getAllCaseInfo() throws IllegalArgumentException, IllegalAccessException;

	Case getCaseInfoById(String caseId, String patientId) throws Exception;

	int add(Case $case) throws Exception;

	String getMinEmptyId() throws Exception;

	int modify(Case $case) throws Exception;

	int delete(String cid) throws Exception;


}
