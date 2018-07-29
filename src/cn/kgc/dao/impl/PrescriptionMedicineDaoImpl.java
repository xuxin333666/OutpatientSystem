package cn.kgc.dao.impl;

import cn.kgc.dao.intf.PrescriptionMedicineDao;
import cn.kgc.model.PrescriptionMedicine;

public class PrescriptionMedicineDaoImpl extends BaseDaoImpl implements PrescriptionMedicineDao {
	
	public PrescriptionMedicineDaoImpl() {
		super("PrescriptionMedicineDao");
	}

	/**
	 * ��ѯ����ҩƷ��ϵ�����ݿ�����С��δ��ʹ�õ�id�ţ������ַ����ĸ�ֵ
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryMinEmptyId() throws Exception {
		return queryMinEmptyId(sqlMap.get("QUERY_MIN_EMPTY_ID_SQL1"),sqlMap.get("QUERY_MIN_EMPTY_ID_SQL2"),"minid");
	}

	/**
	 * �������Ĵ���ҩƷ��ϵ����������ݿ⣬����Ӱ������
	 * @param prescriptionMedicine
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insert(PrescriptionMedicine prescriptionMedicine) throws Exception {
		return insert(sqlMap.get("INSERT_SQL"), prescriptionMedicine, 0, 3);
	}

}
