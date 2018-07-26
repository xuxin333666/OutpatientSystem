package cn.kgc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import cn.kgc.dao.intf.MedicineTypeDao;
import cn.kgc.model.MedicineType;
import cn.kgc.utils.StringUtils;

public class MedicineTypeDaoImpl extends BaseDaoImpl implements MedicineTypeDao {
	private String[] columnName = {"cid","cname","pid"};
	private String[] columnName_update = {"id","name"};
	@Override
	public List<MedicineType> query(String parentId) throws Exception {
		StringBuilder sql = new StringBuilder("SELECT c.id cid,c.name cname,p.id pid "
				+ "FROM t_medicine_type c LEFT OUTER JOIN t_medicine_type p "
				+ "ON c.parent_id = p.id");
		if(StringUtils.isEmpty(parentId)) {
			sql.append(" WHERE c.parent_id iS NULL");
		} else {
			sql.append(" WHERE c.parent_id IN ?");
		}
		List<Object> objs = queryById(sql.toString(), MedicineType.class, MedicineType.class, columnName, parentId);
		return obj2MedicineType(objs);
	}

	@Override
	public String queryMinEmptyId() throws Exception {
		String sql = "SELECT minid FROM (SELECT id,ROWNUM+99 minid FROM t_medicine_type ORDER BY id) WHERE minid <> id AND ROWNUM = 1";
		String sql2 = "SELECT nvl(max(id),99)+1 minid FROM t_medicine_type";
		return queryMinEmptyId(sql,sql2,"minid");
	}

	@Override
	public int insert(MedicineType type) throws Exception {
		String sql =  "insert into t_medicine_type(id,name,parent_id) values (?,?,?)";
		return insert(sql, type, 0, 1,type.getParentMedicineType().getId());
	}


	@Override
	public int update(MedicineType selectedType) throws Exception {
		StringBuilder sql = new StringBuilder("UPDATE t_medicine_type SET");
		return updateById(sql, selectedType, columnName_update, selectedType.getId());
	}

	@Override
	public int delete(MedicineType selectedType) throws Exception {
		String sql = "DELETE FROM t_medicine_type WHERE id = ?";
		return deleteById(sql, selectedType.getId());
	}

	@Override
	public List<MedicineType> queryByNoChildId() throws Exception {
		String sql = "SELECT id cid,name cname,parent_id pid FROM  t_medicine_type "
				+ "WHERE id != ALL (SELECT  NVL(parent_id,0) FROM t_medicine_type)";
		List<Object> objs = query(sql, MedicineType.class, MedicineType.class, columnName);
		return obj2MedicineType(objs);
	}

	
	private List<MedicineType> obj2MedicineType(List<Object> objs) {
		List<MedicineType> medicineTypes = new ArrayList<>();
		for (Object object : objs) {
			MedicineType medicineType = (MedicineType)object;
			medicineTypes.add(medicineType);
		}
		return medicineTypes;
	}
}
