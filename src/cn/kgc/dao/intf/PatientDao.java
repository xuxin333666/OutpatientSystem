package cn.kgc.dao.intf;

import java.util.List;

import cn.kgc.dto.PatientDto;
import cn.kgc.model.Patient;

public interface PatientDao {
	
	/**
	 * ȫ������ѯ�������ݣ�����list
	 * @return
	 * @throws Exception
	 */
	List<Patient> query() throws Exception;

	/**
	 * ����dto��ѯ���������Ĳ������ݣ�����list
	 * @param patientDto
	 * @return
	 * @throws Exception
	 */
	List<Patient> query(PatientDto patientDto) throws Exception;

	/**
	 * ���ݲ���id��ѯ�������ݡ����ظǲ��˶���
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Patient query(String id) throws Exception;

	/**
	 * ��ѯ���˱����ݿ�����С��δ��ʹ�õ�id�ţ������ַ����ĸ�ֵ
	 * @return
	 * @throws Exception
	 */
	String queryMinEmptyId() throws Exception;

	/**
	 * �����˶�����ӵ����ݿ⣬����Ӱ������
	 * @param patient
	 * @return
	 * @throws Exception
	 */
	int insert(Patient patient) throws Exception;

	/**
	 * ���ݸ����Ĳ���id�������޸����ݿ����ݡ�����Ӱ������
	 * @param patient
	 * @return
	 * @throws Exception
	 */
	int update(Patient patient) throws Exception;

	/**
	 * ���ݸ����Ĳ���idɾ���������ݣ�����Ӱ������
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int delete(String id) throws Exception;
	
}
