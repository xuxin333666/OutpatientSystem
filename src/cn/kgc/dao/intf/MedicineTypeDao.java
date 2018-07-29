package cn.kgc.dao.intf;

import java.util.List;

import cn.kgc.model.MedicineType;

public interface MedicineTypeDao {

	/**
	 * ��ѯ�ϼ�Ϊ����������ҩƷ������󣬷���list
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	List<MedicineType> query(String parentId) throws Exception;

	/**
	 * ��ѯ���������ݿ�����С��δ��ʹ�õ�id�ţ������ַ����ĸ�ֵ
	 * @return
	 * @throws Exception
	 */
	String queryMinEmptyId() throws Exception;

	/**
	 * ��ѯû���¼�������ҩƷ���࣬����list
	 * @return
	 * @throws Exception
	 */
	List<MedicineType> queryByNoChildId() throws Exception;
	
	/**
	 * ��������ҩƷ������ӵ����ݿ⣬����Ӱ������
	 * @param type
	 * @return
	 * @throws Exception
	 */
	int insert(MedicineType type) throws Exception;

	/**
	 * ���ݸ�����ҩƷ����id�������޸����ݿ⣬����Ӱ������
	 * @param selectedType
	 * @return
	 * @throws Exception
	 */
	int update(MedicineType selectedType) throws Exception;

	/**
	 * ���ݸ�����ҩƷ�������ɾ�����ݿ����ݣ�����Ӱ������
	 * @param selectedType
	 * @return
	 * @throws Exception
	 */
	int delete(MedicineType selectedType) throws Exception;


}
