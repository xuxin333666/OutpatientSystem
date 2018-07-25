package cn.kgc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.kgc.dao.intf.CaseDao;
import cn.kgc.model.Case;
import cn.kgc.model.Patient;
import cn.kgc.utils.DBPoolConnection;

public class CaseDaoImpl extends BaseDaoImpl implements CaseDao {
	private final String[] COLUMN_NAME = {"cid","examination_time","main_symptom","now_symptom","past_symptom","personal_symptom","body_test",
			"lab_test","examination","advice","other_explain",
			"pid","name","sex","age","married","job",
			"weight","blood","phone_number","register_time",
			"address","allergy","handling_sug","remark"};
	private final String[] COLUMN_NAME_UPDATE = {"id","examination_time","main_symptom","now_symptom","past_symptom","personal_symptom","body_test",
			"lab_test","examination","advice","other_explain"};
	private final String SELECT_CASE_AND_PATINT_TABLE_SQL =  "SELECT c.id cid,examination_time,main_symptom,now_symptom,past_symptom,personal_symptom,body_test,"
			+ "lab_test,examination,advice,other_explain,"
			+ "p.id pid,name,sex,age,married,job,"
			+ "weight,blood,phone_number,register_time,"
			+ "address,allergy,handling_sug,remark FROM t_case c JOIN t_patient p ON patient_id = p.id";

	@Override
	public List<Case> queryByPatientId(String patientId) throws Exception {
		String sql = SELECT_CASE_AND_PATINT_TABLE_SQL + " WHERE patient_id = ?";
		List<Object> objs = queryById(sql, Case.class, Patient.class, COLUMN_NAME, patientId);
		return obj2Case(objs);
	}
	

	@Override
	public Case queryByPatientIdAndId(String caseId, String patientId) throws Exception {
		DBPoolConnection dBP = DBPoolConnection.getInstance();
		String sql =  SELECT_CASE_AND_PATINT_TABLE_SQL + " WHERE patient_id = ? AND c.id = ?";
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet result = null;
		try {
			cn = dBP.getConnection();
			psm = cn.prepareStatement(sql);
			psm.setString(1, patientId);
			psm.setString(2, caseId);
			result = psm.executeQuery();
			List<Object> objs = new ArrayList<>();
			result2List(result, objs, Case.class, Patient.class, COLUMN_NAME);
			return (Case)objs.get(0);
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			if(cn != null) {
				cn.close();
			}	
		}
	}
	

	@Override
	public int insert(Case $case) throws Exception {
		String sql =  "insert into t_case(id,examination_time,main_symptom,now_symptom,past_symptom,personal_symptom,body_test,"
				+ "lab_test,examination,advice,other_explain,patient_id) "
				+ "values (?,nvl(?,sysdate),?,?,?,?,?,?,?,?,?,?)";
		return insert(sql, $case, 0, 10,$case.getPatient().getId());
		
	}
	



	@Override
	public String queryMinEmptyId() throws Exception {
		String sql = "SELECT minid FROM (SELECT id,ROWNUM minid FROM t_case ORDER BY id) WHERE minid <> id AND ROWNUM = 1";
		String sql2 = "SELECT nvl(max(id),0)+1 minid FROM t_case";
		return queryMinEmptyId(sql,sql2,"minid");
	}
	

	@Override
	public int Update(Case $case) throws Exception {
		StringBuilder sql = new StringBuilder("UPDATE t_case SET");
		return updateById(sql, $case, COLUMN_NAME_UPDATE, $case.getId());
	}
	

	@Override
	public int delete(String cid) throws Exception {
		String sql = "DELETE FROM t_case WHERE id = ?";
		return deleteById(sql, cid);
	}
	
	
	private List<Case> obj2Case(List<Object> objs) {
		List<Case> cases = new ArrayList<>();
		for (Object object : objs) {
			Case $case = (Case)object;
			cases.add($case);
		}
		return cases;
	}












}