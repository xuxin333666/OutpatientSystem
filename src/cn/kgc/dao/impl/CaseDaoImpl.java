package cn.kgc.dao.impl;

import java.sql.Connection;
import java.sql.Date;
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
	private final String[] COLUMN_NAME = {"examination_time","main_symptom","now_symptom","past_symptom","personal_symptom","body_test",
			"lab_test","examination","advice","other_explain",
			"cid","pid","name","sex","age","married","job",
			"weight","blood","phone_number","register_time",
			"address","allergy","handling_sug","remark"};
	private final String[] COLUMN_NAME_UPDATE = {"id","examination_time","main_symptom","now_symptom","past_symptom","personal_symptom","body_test",
			"lab_test","examination","advice","other_explain"};
	private final String SELECT_CASE_AND_PATINT_TABLE_SQL =  "SELECT examination_time,main_symptom,now_symptom,past_symptom,personal_symptom,body_test,"
			+ "lab_test,examination,advice,other_explain,"
			+ "c.id cid,p.id pid,name,sex,age,married,job,"
			+ "weight,blood,phone_number,register_time,"
			+ "address,allergy,handling_sug,remark FROM t_case c JOIN t_patient p ON patient_id = p.id";

	@Override
	public List<Case> queryByPatientId(String patientId) throws Exception {
		DBPoolConnection dBP = DBPoolConnection.getInstance();
		List<Case> cases =  new ArrayList<>();
		String sql = SELECT_CASE_AND_PATINT_TABLE_SQL + " WHERE patient_id = ?";
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet result = null;
		try {
			cn = dBP.getConnection();
			psm = cn.prepareStatement(sql);
			psm.setString(1, patientId);
			result = psm.executeQuery();
			result2List(result,cases);		
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			if(cn != null) {
				cn.close();
			}	
		}
		return cases;
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
			return result2Case(result);		
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
		DBPoolConnection dBP = DBPoolConnection.getInstance();
		String sql =  "insert into t_case(id,examination_time,main_symptom,now_symptom,past_symptom,personal_symptom,body_test,"
				+ "lab_test,examination,advice,other_explain,patient_id) "
				+ "values (?,nvl(?,sysdate),?,?,?,?,?,?,?,?,?,?)";
		Connection cn = null;
		PreparedStatement psm = null;
		try {
			cn = dBP.getConnection();		
			psm = cn.prepareStatement(sql);
			prepareStatementSetValue(psm, $case, 0, 10);
			psm.setString(12, $case.getPatient().getId());
			return psm.executeUpdate();	
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			if(cn != null) {
				cn.close();
			}	
		}
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
	
	
	
	private Case result2Case(ResultSet result) throws SQLException {
		List<Case> cases =  new ArrayList<>();
		result2List(result, cases);
		if(cases.size() != 0) {
			return cases.get(0);
		}
		return null;
	}

	
	


	private void result2List(ResultSet result, List<Case> cases) throws SQLException {
		Patient patient = null;
		int count = 0;
		while(result.next()) {
			int index = 0;
			Date examinationTime = result.getDate(COLUMN_NAME[index++]);
			String mainSymptom = result.getString(COLUMN_NAME[index++]);
			String nowSymptom = result.getString(COLUMN_NAME[index++]);
			String pastSymptom = result.getString(COLUMN_NAME[index++]);
			String personalSymptom = result.getString(COLUMN_NAME[index++]);
			String bodyTest = result.getString(COLUMN_NAME[index++]);
			String labTest = result.getString(COLUMN_NAME[index++]);
			String examination = result.getString(COLUMN_NAME[index++]);
			String advice = result.getString(COLUMN_NAME[index++]);
			String otherExplain = result.getString(COLUMN_NAME[index++]);
			String id = result.getString(COLUMN_NAME[index++]);
			
			if(count == 0) {			
				String patientId = result.getString(COLUMN_NAME[index++]);
				String name = result.getString(COLUMN_NAME[index++]);
				String sex = result.getString(COLUMN_NAME[index++]);
				Double age = result.getDouble(COLUMN_NAME[index++]);
				String married = result.getString(COLUMN_NAME[index++]);
				String job = result.getString(COLUMN_NAME[index++]);
				Double weight = result.getDouble(COLUMN_NAME[index++]);
				String blood = result.getString(COLUMN_NAME[index++]);
				String phoneNumber = result.getString(COLUMN_NAME[index++]);
				Date registerTime = result.getDate(COLUMN_NAME[index++]);
				String address = result.getString(COLUMN_NAME[index++]);
				String allergy = result.getString(COLUMN_NAME[index++]);
				String handlingSug = result.getString(COLUMN_NAME[index++]);
				String remark = result.getString(COLUMN_NAME[index++]);
				patient = new Patient(patientId, name, sex, age, married, job, weight, blood, 
						phoneNumber, registerTime, address, allergy,handlingSug, remark);
			}
			Case $case = new Case(id, mainSymptom, nowSymptom, pastSymptom, personalSymptom,
					bodyTest, labTest, examination, advice, otherExplain, examinationTime, patient);
			cases.add($case);
			count++;
		}
	}












}
