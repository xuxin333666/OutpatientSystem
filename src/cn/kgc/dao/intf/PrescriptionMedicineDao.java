package cn.kgc.dao.intf;

import cn.kgc.model.PrescriptionMedicine;

public interface PrescriptionMedicineDao {
	
	/**
	 * ��ѯ����ҩƷ��ϵ�����ݿ�����С��δ��ʹ�õ�id�ţ������ַ����ĸ�ֵ
	 * @return
	 * @throws Exception
	 */
	String queryMinEmptyId() throws Exception;

	/**
	 * �������Ĵ���ҩƷ��ϵ����������ݿ⣬����Ӱ������
	 * @param prescriptionMedicine
	 * @return
	 * @throws Exception
	 */
	int insert(PrescriptionMedicine prescriptionMedicine) throws Exception;

}
