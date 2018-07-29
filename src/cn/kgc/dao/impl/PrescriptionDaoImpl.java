package cn.kgc.dao.impl;

import cn.kgc.dao.intf.PrescriptionDao;
import cn.kgc.model.Prescription;

public class PrescriptionDaoImpl extends BaseDaoImpl implements PrescriptionDao {
	
	public PrescriptionDaoImpl() {
		super("PrescriptionDao");
	}

	/**
	 * 查询处方表数据库中最小的未被使用的id号，返回字符串的该值
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryMinEmptyId() throws Exception {
		return queryMinEmptyId(sqlMap.get("QUERY_MIN_EMPTY_ID_SQL1"),sqlMap.get("QUERY_MIN_EMPTY_ID_SQL2"),"minid");
	}

	/**
	 * 将给定的处方添加至数据库，返回影响行数
	 * @param prescription
	 * @return
	 * @throws Exception
	 */
	@Override
	public int insert(Prescription prescription) throws Exception {
		return insert(sqlMap.get("INSERT_SQL"), prescription, 0, 3,new String[]{prescription.get$case().getId(),prescription.getPatient().getId()});
	}

}
