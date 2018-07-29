package cn.kgc.dao.intf;

import java.util.List;

import cn.kgc.dto.MedicineDto;
import cn.kgc.model.Medicine;

public interface MedicineDao {

	/**
	 * ȫ������ѯҩƷ������list
	 * @return
	 * @throws Exception
	 */
	List<Medicine> query() throws Exception;

	/**
	 * ����ҩƷ����id��ѯҩƷ������list
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	List<Medicine> queryByTypeId(String typeId) throws Exception;

	/**
	 * ����dto��ѯҩƷ����������������ҩƷlist
	 * @param medicineDto
	 * @return
	 * @throws Exception
	 */
	List<Medicine> queryBySearch(MedicineDto medicineDto) throws Exception;

	/**
	 * ��ѯ���������ݿ�����С��δ��ʹ�õ�id�ţ������ַ����ĸ�ֵ
	 * @return
	 * @throws Exception
	 */
	String getMinEmptyId() throws Exception;
	
	/**
	 * ����ҩƷid��ѯ�����ظ�ҩƷ����
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Medicine queryById(String medicineId) throws Exception;
	
	/**
	 * ����ҩƷid��ѯ����������������list
	 * @param medicineId
	 * @return
	 * @throws Exception
	 */
	List<Medicine> queryByMedicineId(String medicineId) throws Exception;
	
	/**
	 * ������ҩƷ������ӵ����ݿ⣬����Ӱ������
	 * @param medicine
	 * @return
	 * @throws Exception
	 */
	int insert(Medicine medicine) throws Exception;

	/**
	 * ����ҩƷidɾ��ҩƷ���ݣ�����Ӱ������
	 * @param medicineId
	 * @return
	 * @throws Exception
	 */
	int delete(String medicineId) throws Exception;


	/**
	 * ���ݸ�����ҩƷid�������޸����ݿ����ݣ�����Ӱ������
	 * @param medicine
	 * @return
	 * @throws Exception
	 */
	int update(Medicine medicine) throws Exception;


}
