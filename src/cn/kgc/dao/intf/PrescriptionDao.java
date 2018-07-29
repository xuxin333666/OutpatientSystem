package cn.kgc.dao.intf;

import cn.kgc.model.Prescription;

public interface PrescriptionDao {

	/**
	 * 查询处方表数据库中最小的未被使用的id号，返回字符串的该值
	 * @return
	 * @throws Exception
	 */
	String queryMinEmptyId() throws Exception;
	
	/**
	 * 将给定的处方添加至数据库，返回影响行数
	 * @param prescription
	 * @return
	 * @throws Exception
	 */
	int insert(Prescription prescription) throws Exception;

}
