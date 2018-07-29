package cn.kgc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import cn.kgc.dao.intf.MedicineTypeDao;
import cn.kgc.model.MedicineType;
import cn.kgc.utils.StringUtils;

public class MedicineTypeDaoImpl extends BaseDaoImpl implements MedicineTypeDao {
	private static final String[] columnName = {"cid","cname","pid"};
	private static final String[] columnName_update = {"id","name"};
	
	public MedicineTypeDaoImpl() {
		super("MedicineTypeDao");
	}
	
	/**
	 * ��ѯ�ϼ�Ϊ����������ҩƷ������󣬷���list
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<MedicineType> query(String parentId) throws Exception {
		StringBuilder sql = new StringBuilder(sqlMap.get("QUERY_SQL"));
		if(StringUtils.isEmpty(parentId)) {
			sql.append(" WHERE c.parent_id iS NULL");
		} else {
			sql.append(" WHERE c.parent_id IN ?");
		}
		List<Object> objs = queryById(sql.toString(), MedicineType.class, MedicineType.class, columnName, parentId);
		return obj2MedicineType(objs);
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
	 * ��ѯû���¼�������ҩƷ���࣬����list
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<MedicineType> queryByNoChildId() throws Exception {
		List<Object> objs = query(sqlMap.get("QUERY_BY_NO_CHILD_ID_SQL"), MedicineType.class, MedicineType.class, columnName);
		return obj2MedicineType(objs);
	}


	/**
	 * ��������ҩƷ������ӵ����ݿ⣬����Ӱ������
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insert(MedicineType type) throws Exception {
		return insert(sqlMap.get("INSERT_SQL"), type, 0, 1,type.getParentMedicineType().getId());
	}

	/**
	 * ���ݸ�����ҩƷ����id�������޸����ݿ⣬����Ӱ������
	 * @param selectedType
	 * @return
	 * @throws Exception
	 */
	@Override
	public int update(MedicineType selectedType) throws Exception {
		return updateById(sqlMap.get("UPDATE_SQL"), selectedType, columnName_update, selectedType.getId());
	}

	/**
	 * ���ݸ�����ҩƷ�������ɾ�����ݿ����ݣ�����Ӱ������
	 * @param selectedType
	 * @return
	 * @throws Exception
	 */
	@Override
	public int delete(MedicineType selectedType) throws Exception {
		return deleteById(sqlMap.get("DELETE_SQL"), selectedType.getId());
	}

	/**
	 * ��List<Object>ת��ΪList<MedicineType>����
	 * @param objs
	 * @return
	 */
	private List<MedicineType> obj2MedicineType(List<Object> objs) {
		List<MedicineType> medicineTypes = new ArrayList<>();
		for (Object object : objs) {
			MedicineType medicineType = (MedicineType)object;
			medicineTypes.add(medicineType);
		}
		return medicineTypes;
	}
}
