package cn.kgc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.kgc.dao.intf.PatientDao;
import cn.kgc.dto.PatientDto;
import cn.kgc.model.Patient;
import cn.kgc.utils.DBPoolConnection;
import cn.kgc.utils.DateUtils;
import cn.kgc.utils.PatientUtils;
import cn.kgc.utils.StringUtils;

public class PatientDaoImpl extends BaseDaoImpl implements PatientDao {
	private final String[] COLUMN_NAME = {"id","name","sex","age","married","job",
										"weight","blood","phone_number","register_time",
										"address","allergy","handling_sug","remark"};
	private static final String[] QUERY_KEY_LIST = {"证号/姓名","性别","婚姻状况","职业","联系地址","初诊处理意见","初诊备注"};  
	@Override
	public List<Patient> query() throws Exception {
		String sql = "SELECT * FROM t_patient ORDER BY id";
		List<Object> objs = query(sql, Patient.class, null, COLUMN_NAME);
		return obj2Patient(objs);
	}

	@Override
	public List<Patient> query(PatientDto patientDto) throws Exception {
		DBPoolConnection dbp = new DBPoolConnection();
		StringBuilder sql = new StringBuilder("SELECT * FROM t_patient WHERE 1=1");
		String queryColumnName = patientDto.getQueryColumnNameStr();
		String key = patientDto.getKeyStr();
		if(StringUtils.isNotEmpty(patientDto.getStartTimeStr())) {
			sql.append(" AND register_time >= ?");
		}
		if(StringUtils.isNotEmpty(patientDto.getEndTimeStr())) {
			sql.append(" AND register_time <= ?");
		}
		if(StringUtils.isNotEmpty(key)) {
			int index = 1;
			if(QUERY_KEY_LIST[index++].equals(queryColumnName)) {
				sql.append(" AND sex LIKE ?");	
				key = PatientUtils.Str2status(key, PatientUtils.sexRule);
			} else if(QUERY_KEY_LIST[index++].equals(queryColumnName)) {
				sql.append(" AND married LIKE ?");
				key = PatientUtils.Str2status(key, PatientUtils.marriedRule);
			} else if(QUERY_KEY_LIST[index++].equals(queryColumnName)) {
				sql.append(" AND job LIKE ?");
				key = PatientUtils.Str2status(key, PatientUtils.jobRule);
			} else if(QUERY_KEY_LIST[index++].equals(queryColumnName)) {
				sql.append(" AND address LIKE ?");
			} else if(QUERY_KEY_LIST[index++].equals(queryColumnName)) {
				sql.append(" AND handling_sug LIKE ?");
			} else if(QUERY_KEY_LIST[index++].equals(queryColumnName)) {
				sql.append(" AND remark LIKE ?");
			} else if(QUERY_KEY_LIST[0].equals(queryColumnName)) {
				sql.append(" AND id LIKE ? OR name LIKE ?");
			}
			sql.append(" ORDER BY id");
		}
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet result = null;
		try {
			cn = dbp.getConnection();
			psm = cn.prepareStatement(sql.toString()); 
			int agrsIndex = 1;
			if(StringUtils.isNotEmpty(patientDto.getStartTimeStr())) {
				psm.setDate(agrsIndex++, DateUtils.String2SqlDate(patientDto.getStartTimeStr()));
			}
			if(StringUtils.isNotEmpty(patientDto.getEndTimeStr())) {
				psm.setDate(agrsIndex++, DateUtils.String2SqlDate(patientDto.getEndTimeStr()));
			}
			if(StringUtils.isNotEmpty(key)) {
				psm.setString(agrsIndex++, "%" + key + "%");
				if(QUERY_KEY_LIST[0].equals(queryColumnName)) {
					psm.setString(agrsIndex++, "%" + key + "%");
				}
			}
			result = psm.executeQuery();
			List<Object> objs = new ArrayList<>();
			result2List(result, objs, Patient.class, null, COLUMN_NAME);
			return obj2Patient(objs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(SQL_ERORR + e.getMessage());
		} finally {
			if(cn != null) {
				try {
					cn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public Patient query(String id) throws Exception {
		String sql = "SELECT * FROM t_patient WHERE id = ?";
		List<Object> objs = queryById(sql, Patient.class, null, COLUMN_NAME, id);
		return (Patient)objs.get(0);
	}
	
	@Override
	public String queryMinEmptyId() throws Exception {
		String sql = "SELECT minid FROM (SELECT id,ROWNUM+10000 minid FROM t_patient) WHERE minid <> id AND ROWNUM = 1";
		String sql2 = "SELECT nvl(max(id),10000)+1 minid FROM t_patient";
		return queryMinEmptyId(sql,sql2,"minid");
	}
	
	@Override
	public int insert(Patient patient) throws Exception {
		String sql = "INSERT INTO t_patient VALUES (?,?,?,?,?,?,?,?,?,nvl(?,SYSDATE),?,?,?,?)";
		return insert(sql, patient, 0, 13);
	}
	
	@Override
	public int update(Patient patient) throws Exception {
		StringBuilder sql = new StringBuilder("UPDATE t_patient SET");
		return updateById(sql, patient, COLUMN_NAME, patient.getId());
	}
	
	
	@Override
	public int delete(String id) throws Exception {
		String sql = "DELETE FROM t_patient WHERE id = ?";
		return deleteById(sql, id);
	}
	
	
	private List<Patient> obj2Patient(List<Object> objs) {
		List<Patient> patients = new ArrayList<>();
		for (Object object : objs) {
			Patient patient = (Patient)object;
			patients.add(patient);
		}
		return patients;
	}
	

}
