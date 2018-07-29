package cn.kgc.dao.impl;

import cn.kgc.dao.intf.PrescriptionDao;
import cn.kgc.model.Prescription;

public class PrescriptionDaoImpl extends BaseDaoImpl implements PrescriptionDao {
	
	public PrescriptionDaoImpl() {
		super("PrescriptionDao");
	}

	/**
	 * ��ѯ���������ݿ�����С��δ��ʹ�õ�id�ţ������ַ����ĸ�ֵ
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryMinEmptyId() throws Exception {
		return queryMinEmptyId(sqlMap.get("QUERY_MIN_EMPTY_ID_SQL1"),sqlMap.get("QUERY_MIN_EMPTY_ID_SQL2"),"minid");
	}

	/**
	 * �������Ĵ�����������ݿ⣬����Ӱ������
	 * @param prescription
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insert(Prescription prescription) throws Exception {
		return insert(sqlMap.get("INSERT_SQL"), prescription, 0, 3,new String[]{prescription.get$case().getId(),prescription.getPatient().getId()});
	}

}
