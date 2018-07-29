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
	private static final String[] COLUMN_NAME = {"id","name","sex","age","married","job",
										"weight","blood","phone_number","register_time",
										"address","allergy","handling_sug","remark"};
	private static final String[] QUERY_KEY_LIST = {"证号/姓名","性别","婚姻状况","职业","联系地址","初诊处理意见","初诊备注"};  
	
	public PatientDaoImpl() {
		super("PatientDao");
	}
	
	
	/**
	 * 全条件查询病人数据，返回list
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Patient> query() throws Exception {
		List<Object> objs = query(sqlMap.get("QUERY_SQL"), Patient.class, null, COLUMN_NAME);
		return obj2Patient(objs);
	}
	
	
	/**
	 * 根据dto查询满足条件的病人数据，返回list
	 * @param patientDto
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Patient> query(PatientDto patientDto) throws Exception {
		DBPoolConnection dbp = new DBPoolConnection();
		StringBuilder sql = new StringBuilder(sqlMap.get("QUERY_SEARCH_SQL"));
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
	

	/**
	 * 根据病人id查询病人数据。返回盖病人对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public Patient query(String id) throws Exception {
		List<Object> objs = queryById(sqlMap.get("QUERY_BY_ID_SQL"), Patient.class, null, COLUMN_NAME, id);
		return (Patient)objs.get(0);
	}
	
	/**
	 * 查询病人表数据库中最小的未被使用的id号，返回字符串的该值
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryMinEmptyId() throws Exception {
		return queryMinEmptyId(sqlMap.get("QUERY_MIN_EMPTY_ID_SQL1"),sqlMap.get("QUERY_MIN_EMPTY_ID_SQL2"),"minid");
	}
	
	/**
	 * 将病人对象添加到数据库，返回影响行数
	 * @param patient
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insert(Patient patient) throws Exception {
		return insert(sqlMap.get("INSERT_SQL"), patient, 0, 13);
	}
	
	/**
	 * 根据给定的病人id和数据修改数据库数据。返回影响行数
	 * @param patient
	 * @return
	 * @throws Exception
	 */
	@Override
	public int update(Patient patient) throws Exception {
		return updateById(sqlMap.get("UPDATE_SQL"), patient, COLUMN_NAME, patient.getId());
	}
	
	/**
	 * 根据给定的病人id删除病人数据，返回影响行数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public int delete(String id) throws Exception {
		return deleteById(sqlMap.get("DELETE_SQL"), id);
	}
	
	/**
	 * 将List<Object>转换为List<Patient>返回
	 * @param objs
	 * @return
	 */
	private List<Patient> obj2Patient(List<Object> objs) {
		List<Patient> patients = new ArrayList<>();
		for (Object object : objs) {
			Patient patient = (Patient)object;
			patients.add(patient);
		}
		return patients;
	}
	

}
