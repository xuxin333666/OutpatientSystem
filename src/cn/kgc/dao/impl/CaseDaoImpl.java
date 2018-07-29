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
	 * 根据病人的id查询所有病例，组成list返回
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
	 * 根据病例的id查询病例，返回唯一的病例对象
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
	 * 查询病例表数据库中最小的未被使用的id号，返回字符串的该值
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryMinEmptyId() throws Exception {
		return queryMinEmptyId(sqlMap.get("QUERY_MIN_EMPTY_ID_SQL1"),sqlMap.get("QUERY_MIN_EMPTY_ID_SQL2"),"minid");
	}
	
	/**
	 * 将给定的病例加入病例表数据库，返回该操作所影响的行数
	 * @param $case
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insert(Case $case) throws Exception {	
		return insert(sqlMap.get("INSERT_SQL"), $case, 0, 10,$case.getPatient().getId());
		
	}
	

	/**
	 * 根据给定的病例的id修改数据库的病例，返回该操作所影响的行数
	 * @param $case
	 * @return
	 * @throws Exception
	 */
	@Override
	public int Update(Case $case) throws Exception {
		return updateById(sqlMap.get("UPDATE_SQL"), $case, COLUMN_NAME_UPDATE, $case.getId());
	}
	
	/**
	 * 根据给定的病例id删除相应的病例，返回该操作所影响的行数
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	@Override
	public int delete(String cid) throws Exception {
		return deleteById(sqlMap.get("DELETE_SQL"), cid);
	}
	
	/**
	 * 将List<Object>转换为List<Case>返回
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