package cn.kgc.dao.intf;

import cn.kgc.model.Prescription;

public interface PrescriptionDao {

	/**
	 * ��ѯ���������ݿ�����С��δ��ʹ�õ�id�ţ������ַ����ĸ�ֵ
	 * @return
	 * @throws Exception
	 */
	String queryMinEmptyId() throws Exception;
	
	/**
	 * �������Ĵ�����������ݿ⣬����Ӱ������
	 * @param prescription
	 * @return
	 * @throws Exception
	 */
	int insert(Prescription prescription) throws Exception;

}
