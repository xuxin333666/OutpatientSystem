package cn.kgc.dao.intf;

import cn.kgc.model.PrescriptionMedicine;

public interface PrescriptionMedicineDao {
	
	/**
	 * 查询处方药品关系表数据库中最小的未被使用的id号，返回字符串的该值
	 * @return
	 * @throws Exception
	 */
	String queryMinEmptyId() throws Exception;

	/**
	 * 将给定的处方药品关系表添加至数据库，返回影响行数
	 * @param prescriptionMedicine
	 * @return
	 * @throws Exception
	 */
	int insert(PrescriptionMedicine prescriptionMedicine) throws Exception;

}
