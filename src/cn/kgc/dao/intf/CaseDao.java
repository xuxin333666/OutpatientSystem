package cn.kgc.dao.intf;

import java.util.List;

import cn.kgc.model.Case;

public interface CaseDao {

	/**
	 * ���ݲ��˵�id��ѯ���в��������list����
	 * @param patientId
	 * @return
	 * @throws Exception
	 */
	List<Case> queryByPatientId(String patientId) throws Exception;

	/**
	 * ���ݲ�����id��ѯ����������Ψһ�Ĳ�������
	 * @param caseId
	 * @param patientId
	 * @return
	 * @throws Exception
	 */
	Case queryByPatientIdAndId(String caseId, String patientId) throws Exception;

	/**
	 * ��ѯ���������ݿ�����С��δ��ʹ�õ�id�ţ������ַ����ĸ�ֵ
	 * @return
	 * @throws Exception
	 */
	String queryMinEmptyId() throws Exception;
	
	/**
	 * �������Ĳ������벡�������ݿ⣬���ظò�����Ӱ�������
	 * @param $case
	 * @return
	 * @throws Exception
	 */
	int insert(Case $case) throws Exception;

	/**
	 * ���ݸ����Ĳ�����id�޸����ݿ�Ĳ��������ظò�����Ӱ�������
	 * @param $case
	 * @return
	 * @throws Exception
	 */
	int Update(Case $case) throws Exception;

	/**
	 * ���ݸ����Ĳ���idɾ����Ӧ�Ĳ��������ظò�����Ӱ�������
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	int delete(String cid) throws Exception;

}
