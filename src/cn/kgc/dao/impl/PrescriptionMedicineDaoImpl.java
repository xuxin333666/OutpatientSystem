package cn.kgc.dao.impl;

import cn.kgc.dao.intf.PrescriptionMedicineDao;
import cn.kgc.model.PrescriptionMedicine;

public class PrescriptionMedicineDaoImpl extends BaseDaoImpl implements PrescriptionMedicineDao {
	
	public PrescriptionMedicineDaoImpl() {
		super("PrescriptionMedicineDao");
	}

	/**
	 * 查询处方药品关系表数据库中最小的未被使用的id号，返回字符串的该值
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryMinEmptyId() throws Exception {
		return queryMinEmptyId(sqlMap.get("QUERY_MIN_EMPTY_ID_SQL1"),sqlMap.get("QUERY_MIN_EMPTY_ID_SQL2"),"minid");
	}

	/**
	 * 将给定的处方药品关系表添加至数据库，返回影响行数
	 * @param prescriptionMedicine
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insert(PrescriptionMedicine prescriptionMedicine) throws Exception {
		return insert(sqlMap.get("INSERT_SQL"), prescriptionMedicine, 0, 3);
	}

}
