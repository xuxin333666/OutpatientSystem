package cn.kgc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import cn.kgc.dao.intf.MedicineDao;
import cn.kgc.dto.MedicineDto;
import cn.kgc.model.Medicine;
import cn.kgc.model.MedicineType;


public class MedicineDaoImpl extends BaseDaoImpl implements MedicineDao {
	private static final String[] COLUMN_NAMES = {"mid","mname","norms","unit","price","uasge","needing_attention","tid","tname","parent_id"};
	private static final String[] COLUMN_NAMES_UPDATE = {"id","name","norms","unit","price","uasge","needing_attention","medicine_type_id"};

	
	public MedicineDaoImpl() {
		super("MedicineDao");
	}
	

	/**
	 * ȫ������ѯҩƷ������list
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Medicine> query() throws Exception {
		List<Object> objs = query(sqlMap.get("SELECT_MEDICINE_AND_TYPE_TABLE_SQL") + sqlMap.get("QUERY_SQL"),Medicine.class,MedicineType.class,COLUMN_NAMES);
		return obj2Medicine(objs);
	}
	
	/**
	 * ����ҩƷ����id��ѯҩƷ������list
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Medicine> queryByTypeId(String typeId) throws Exception {
		List<Object> objs = queryById(sqlMap.get("SELECT_MEDICINE_AND_TYPE_TABLE_SQL") + sqlMap.get("QUERY_BY_TYPE_ID_SQL"), Medicine.class, MedicineType.class, COLUMN_NAMES, typeId);
		return obj2Medicine(objs);
	}
	
	/**
	 * ����dto��ѯҩƷ����������������ҩƷlist
	 * @param medicineDto
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Medicine> queryBySearch(MedicineDto medicineDto) throws Exception {
		List<Object> objs = queryBySearch(sqlMap.get("SELECT_MEDICINE_AND_TYPE_TABLE_SQL") + sqlMap.get("QUERY_BY_SEARCH_SQL"), medicineDto, Medicine.class, MedicineType.class, COLUMN_NAMES, 0, 3);
		return obj2Medicine(objs);
	}
	
	/**
	 * ��ѯ���������ݿ�����С��δ��ʹ�õ�id�ţ������ַ����ĸ�ֵ
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getMinEmptyId() throws Exception {
		return queryMinEmptyId(sqlMap.get("QUERY_MIN_EMPTY_ID_SQL1"),sqlMap.get("QUERY_MIN_EMPTY_ID_SQL2"),"minid");
	}

	/**
	 * ����ҩƷid��ѯ�����ظ�ҩƷ����
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public Medicine queryById(String medicineId) throws Exception {
		List<Object> objs = queryById(sqlMap.get("SELECT_MEDICINE_AND_TYPE_TABLE_SQL") + sqlMap.get("QUERY_BY_ID_SQL"), Medicine.class, MedicineType.class, COLUMN_NAMES, medicineId);
		return (Medicine)objs.get(0);
	}


	/**
	 * ����ҩƷid��ѯ����������������list
	 * @param medicineId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Medicine> queryByMedicineId(String medicineId) throws Exception {
		List<Object> objs = queryById(sqlMap.get("SELECT_MEDICINE_AND_TYPE_TABLE_SQL") + sqlMap.get("QUERY_BY_MEDICINE_ID_SQL"), Medicine.class, MedicineType.class, COLUMN_NAMES, medicineId);
		return obj2Medicine(objs);
	}
	
	/**
	 * ������ҩƷ������ӵ����ݿ⣬����Ӱ������
	 * @param medicine
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insert(Medicine medicine) throws Exception {
		return insert(sqlMap.get("INSERT_SQL"), medicine, 0, 6,medicine.getMedicineType().getId());
	}

	/**
	 * ����ҩƷidɾ��ҩƷ���ݣ�����Ӱ������
	 * @param medicineId
	 * @return
	 * @throws Exception
	 */
	@Override
	public int delete(String medicineId) throws Exception {
		return deleteById(sqlMap.get("DELETE_SQL"), medicineId);
	}

	/**
	 * ���ݸ�����ҩƷid�������޸����ݿ����ݣ�����Ӱ������
	 * @param medicine
	 * @return
	 * @throws Exception
	 */
	@Override
	public int update(Medicine medicine) throws Exception {
		return updateById(sqlMap.get("UPDATE_SQL"), medicine, COLUMN_NAMES_UPDATE, medicine.getId(),medicine.getMedicineType().getId());
	}
	
	/**
	 * ��List<Object>ת��ΪList<Medicine>����
	 * @param objs
	 * @return
	 */
	private List<Medicine> obj2Medicine(List<Object> objs) {
		List<Medicine> medicines = new ArrayList<>();
		for (Object object : objs) {
			Medicine medicine = (Medicine)object;
			medicines.add(medicine);
		}
		return medicines;
	}



}
