package cn.kgc.dao.intf;

import java.util.List;

import cn.kgc.model.MedicineType;

public interface MedicineTypeDao {

	/**
	 * 查询上级为给定参数的药品分类对象，返回list
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	List<MedicineType> query(String parentId) throws Exception;

	/**
	 * 查询病例表数据库中最小的未被使用的id号，返回字符串的该值
	 * @return
	 * @throws Exception
	 */
	String queryMinEmptyId() throws Exception;

	/**
	 * 查询没有下级的所有药品分类，返回list
	 * @return
	 * @throws Exception
	 */
	List<MedicineType> queryByNoChildId() throws Exception;
	
	/**
	 * 将给定的药品类型添加到数据库，返回影响行数
	 * @param type
	 * @return
	 * @throws Exception
	 */
	int insert(MedicineType type) throws Exception;

	/**
	 * 根据给定的药品分类id和数据修改数据库，返回影响行数
	 * @param selectedType
	 * @return
	 * @throws Exception
	 */
	int update(MedicineType selectedType) throws Exception;

	/**
	 * 根据给定的药品分类对象删除数据库数据，返回影响行数
	 * @param selectedType
	 * @return
	 * @throws Exception
	 */
	int delete(MedicineType selectedType) throws Exception;


}
