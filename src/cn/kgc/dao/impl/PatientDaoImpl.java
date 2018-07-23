package cn.kgc.dao.impl;

import java.sql.Connection;
import java.sql.Date;
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

public class PatientDaoImpl implements PatientDao {
	private final String SQL_ERORR = "后台数据错误，";
	private final String[] COLUMN_NAME = {"id","name","sex","age","married","job",
										"weight","blood","phone_number","register_time",
										"address","allergy","handling_sug","remark"};
	private static final String[] QUERY_KEY_LIST = {"证号/姓名","性别","婚姻状况","职业","联系地址","初诊处理意见","初诊备注"};  
	@Override
	public List<Patient> query() throws Exception {
		DBPoolConnection dbp = new DBPoolConnection();
		List<Patient> patients =  new ArrayList<>();
		String sql = "SELECT * FROM t_patient";
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet result = null;
		try {
			cn = dbp.getConnection();
			psm = cn.prepareStatement(sql); 
			result = psm.executeQuery();
			result2List(result,patients);
		} catch (SQLException e) {
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
		return patients;
	}
	@Override
	public List<Patient> query(PatientDto patientDto) throws Exception {
		DBPoolConnection dbp = new DBPoolConnection();
		List<Patient> patients =  new ArrayList<>();
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
				sql.append(" AND id LIKE ? OR id LIKE ?");
			}
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
			result2List(result,patients);
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
		return patients;
	}
	
	@Override
	public Patient query(String id) throws Exception {
		DBPoolConnection dBP = DBPoolConnection.getInstance();
		String sql = "SELECT * FROM t_patient WHERE id = ?";
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet result = null;
		try {
			cn = dBP.getConnection();
			psm = cn.prepareStatement(sql);
			psm.setString(1, id);
			result = psm.executeQuery();
			return result2User(result);		
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
		DBPoolConnection dBP = DBPoolConnection.getInstance();
		String sql = "SELECT minid FROM (SELECT id,ROWNUM+10000 minid FROM t_patient) WHERE minid <> id AND ROWNUM = 1";
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet result = null;
		try {
			cn = dBP.getConnection();
			psm = cn.prepareStatement(sql);
			result = psm.executeQuery();
			if(result.next()) {
				return result.getString("minid");
			}
			return queryMaxId();		
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			if(cn != null) {
				cn.close();
			}	
		}
	}
	
	
	private String queryMaxId() throws Exception {
		DBPoolConnection dBP = DBPoolConnection.getInstance();
		String sql = "SELECT nvl(max(id),10000)+1 minid FROM t_patient";
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet result = null;
		try {
			cn = dBP.getConnection();
			psm = cn.prepareStatement(sql);
			result = psm.executeQuery();
			if(result.next()) {
				return result.getString("minid");
			}
			return null;		
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			if(cn != null) {
				cn.close();
			}	
		}
	}
	private Patient result2User(ResultSet result) throws SQLException {
		List<Patient> patients =  new ArrayList<>();
		result2List(result, patients);
		if(patients.size() != 0) {
			return patients.get(0);
		}
		return null;
	}
	
	


	private void result2List(ResultSet result, List<Patient> patients) throws SQLException {
		while(result.next()) {
			int index = 0;
			String id = result.getString(COLUMN_NAME[index++]);
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
			Patient patient = new Patient(id, name, sex, age, married, job, weight, blood, 
					phoneNumber, registerTime, address, allergy,handlingSug, remark);
			patients.add(patient);
		}
	}

}
