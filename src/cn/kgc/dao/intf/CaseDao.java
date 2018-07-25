package cn.kgc.dao.intf;

import java.util.List;

import cn.kgc.model.Case;

public interface CaseDao {

	List<Case> queryByPatientId(String patientId) throws Exception;

	Case queryByPatientIdAndId(String caseId, String patientId) throws Exception;

	int insert(Case $case) throws Exception;

	String queryMinEmptyId() throws Exception;

	int Update(Case $case) throws Exception;

	int delete(String cid) throws Exception;

}
