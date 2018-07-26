package cn.kgc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import cn.kgc.dao.intf.MedicineDao;
import cn.kgc.dto.MedicineDto;
import cn.kgc.model.Medicine;
import cn.kgc.model.MedicineType;


public class MedicineDaoImpl extends BaseDaoImpl implements MedicineDao {
	private static final String[] COLUMN_NAMES = {"mid","mname","norms","unit","price","uasge","needing_attention","tid","tname","parent_id"};
	private final String SELECT_MEDICINE_AND_TYPE_TABLE_SQL =  "SELECT m.id mid,m.name mname,norms,unit,price,uasge,"
			+ "needing_attention,t.id tid,t.name tname,parent_id"
			+ " FROM t_medicine m JOIN t_medicine_type t ON medicine_type_id = t.id";

	@Override
	public List<Medicine> query() throws Exception {
		String sql = SELECT_MEDICINE_AND_TYPE_TABLE_SQL + " ORDER BY mid";
		List<Object> objs = query(sql,Medicine.class,MedicineType.class,COLUMN_NAMES);
		return obj2Medicine(objs);
	}
	
	@Override
	public List<Medicine> queryByTypeId(String typeId) throws Exception {
		String sql = SELECT_MEDICINE_AND_TYPE_TABLE_SQL + " WHERE medicine_type_id = ANY "
				+ "(SELECT id FROM t_medicine_type START WITH id = ? CONNECT BY PRIOR id = parent_id)";
		List<Object> objs = queryById(sql, Medicine.class, MedicineType.class, COLUMN_NAMES, typeId);
		return obj2Medicine(objs);
	}
	
	@Override
	public List<Medicine> queryBySearch(MedicineDto medicineDto) throws Exception {
		String sql = SELECT_MEDICINE_AND_TYPE_TABLE_SQL + " WHERE m.id LIKE ? OR m.name LIKE ? OR m.price LIKE ? OR t.name LIKE ?";
		List<Object> objs = queryBySearch(sql, medicineDto, Medicine.class, MedicineType.class, COLUMN_NAMES, 0, 3);
		return obj2Medicine(objs);
	}
	
	private List<Medicine> obj2Medicine(List<Object> objs) {
		List<Medicine> medicines = new ArrayList<>();
		for (Object object : objs) {
			Medicine medicine = (Medicine)object;
			medicines.add(medicine);
		}
		return medicines;
	}



}
