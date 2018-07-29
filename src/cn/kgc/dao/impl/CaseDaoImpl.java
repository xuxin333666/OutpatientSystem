package cn.kgc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import cn.kgc.dao.intf.CaseDao;
import cn.kgc.model.Case;
import cn.kgc.model.Patient;

public class CaseDaoImpl extends BaseDaoImpl implements CaseDao {
	private static final String[] COLUMN_NAME = {"cid","examination_time","main_symptom","now_symptom","past_symptom","personal_symptom","body_test",
			"lab_test","examination","advice","other_explain",
			"pid","name","sex","age","married","job",
			"weight","blood","phone_number","register_time",
			"address","allergy","handling_sug","remark"};
	private static final String[] COLUMN_NAME_UPDATE = {"id","examination_time","main_symptom","now_symptom","past_symptom","personal_symptom","body_test",
			"lab_test","examination","advice","other_explain"};
	
	public CaseDaoImpl() {
		super("CaseDao");
	}
	
	
	/**
	 * ���ݲ��˵�id��ѯ���в��������list����
	 * @param patientId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Case> queryByPatientId(String patientId) throws Exception {
		List<Object> objs = queryById(sqlMap.get("SELECT_CASE_AND_PATINT_TABLE_SQL") + sqlMap.get("QUERY_BY_PATIENT_ID_SQL"), Case.class, Patient.class, COLUMN_NAME, patientId);
		return obj2Case(objs);
	}
	
	/**
	 * ���ݲ�����id��ѯ����������Ψһ�Ĳ�������
	 * @param caseId
	 * @param patientId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Case queryByPatientIdAndId(String caseId, String patientId) throws Exception {
		List<Object> objs = queryById(sqlMap.get("SELECT_CASE_AND_PATINT_TABLE_SQL") + sqlMap.get("QUERY_BY_CASE_ID_SQL"), Case.class, Patient.class, COLUMN_NAME, caseId);
		return (Case)objs.get(0);
	}
	

	/**
	 * ��ѯ���������ݿ�����С��δ��ʹ�õ�id�ţ������ַ����ĸ�ֵ
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryMinEmptyId() throws Exception {
		return queryMinEmptyId(sqlMap.get("QUERY_MIN_EMPTY_ID_SQL1"),sqlMap.get("QUERY_MIN_EMPTY_ID_SQL2"),"minid");
	}
	
	/**
	 * �������Ĳ������벡�������ݿ⣬���ظò�����Ӱ�������
	 * @param $case
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insert(Case $case) throws Exception {	
		return insert(sqlMap.get("INSERT_SQL"), $case, 0, 10,$case.getPatient().getId());
		
	}
	

	/**
	 * ���ݸ����Ĳ�����id�޸����ݿ�Ĳ��������ظò�����Ӱ�������
	 * @param $case
	 * @return
	 * @throws Exception
	 */
	@Override
	public int Update(Case $case) throws Exception {
		return updateById(sqlMap.get("UPDATE_SQL"), $case, COLUMN_NAME_UPDATE, $case.getId());
	}
	
	/**
	 * ���ݸ����Ĳ���idɾ����Ӧ�Ĳ��������ظò�����Ӱ�������
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	@Override
	public int delete(String cid) throws Exception {
		return deleteById(sqlMap.get("DELETE_SQL"), cid);
	}
	
	/**
	 * ��List<Object>ת��ΪList<Case>����
	 * @param objs
	 * @return
	 */
	private List<Case> obj2Case(List<Object> objs) {
		List<Case> cases = new ArrayList<>();
		for (Object object : objs) {
			Case $case = (Case)object;
			cases.add($case);
		}
		return cases;
	}












}