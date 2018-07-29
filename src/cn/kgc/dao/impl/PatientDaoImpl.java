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
	private static final String[] QUERY_KEY_LIST = {"֤��/����","�Ա�","����״��","ְҵ","��ϵ��ַ","���ﴦ�����","���ﱸע"};  
	
	public PatientDaoImpl() {
		super("PatientDao");
	}
	
	
	/**
	 * ȫ������ѯ�������ݣ�����list
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Patient> query() throws Exception {
		List<Object> objs = query(sqlMap.get("QUERY_SQL"), Patient.class, null, COLUMN_NAME);
		return obj2Patient(objs);
	}
	
	
	/**
	 * ����dto��ѯ���������Ĳ������ݣ�����list
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
	 * ���ݲ���id��ѯ�������ݡ����ظǲ��˶���
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
	 * ��ѯ���˱����ݿ�����С��δ��ʹ�õ�id�ţ������ַ����ĸ�ֵ
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryMinEmptyId() throws Exception {
		return queryMinEmptyId(sqlMap.get("QUERY_MIN_EMPTY_ID_SQL1"),sqlMap.get("QUERY_MIN_EMPTY_ID_SQL2"),"minid");
	}
	
	/**
	 * �����˶�����ӵ����ݿ⣬����Ӱ������
	 * @param patient
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insert(Patient patient) throws Exception {
		return insert(sqlMap.get("INSERT_SQL"), patient, 0, 13);
	}
	
	/**
	 * ���ݸ����Ĳ���id�������޸����ݿ����ݡ�����Ӱ������
	 * @param patient
	 * @return
	 * @throws Exception
	 */
	@Override
	public int update(Patient patient) throws Exception {
		return updateById(sqlMap.get("UPDATE_SQL"), patient, COLUMN_NAME, patient.getId());
	}
	
	/**
	 * ���ݸ����Ĳ���idɾ���������ݣ�����Ӱ������
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public int delete(String id) throws Exception {
		return deleteById(sqlMap.get("DELETE_SQL"), id);
	}
	
	/**
	 * ��List<Object>ת��ΪList<Patient>����
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
