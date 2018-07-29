package cn.kgc.dao.intf;

import java.util.List;

import cn.kgc.dto.MedicineDto;
import cn.kgc.model.Medicine;

public interface MedicineDao {

	/**
	 * 全条件查询药品，返回list
	 * @return
	 * @throws Exception
	 */
	List<Medicine> query() throws Exception;

	/**
	 * 根据药品分类id查询药品，返回list
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	List<Medicine> queryByTypeId(String typeId) throws Exception;

	/**
	 * 根据dto查询药品，返回满足条件的药品list
	 * @param medicineDto
	 * @return
	 * @throws Exception
	 */
	List<Medicine> queryBySearch(MedicineDto medicineDto) throws Exception;

	/**
	 * 查询病例表数据库中最小的未被使用的id号，返回字符串的该值
	 * @return
	 * @throws Exception
	 */
	String getMinEmptyId() throws Exception;
	
	/**
	 * 根据药品id查询。返回该药品对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Medicine queryById(String medicineId) throws Exception;
	
	/**
	 * 根据药品id查询，返回满足条件的list
	 * @param medicineId
	 * @return
	 * @throws Exception
	 */
	List<Medicine> queryByMedicineId(String medicineId) throws Exception;
	
	/**
	 * 给定的药品对象添加到数据库，返回影响行数
	 * @param medicine
	 * @return
	 * @throws Exception
	 */
	int insert(Medicine medicine) throws Exception;

	/**
	 * 根据药品id删除药品数据，返回影响行数
	 * @param medicineId
	 * @return
	 * @throws Exception
	 */
	int delete(String medicineId) throws Exception;


	/**
	 * 根据给定的药品id和数据修改数据库数据，返回影响行数
	 * @param medicine
	 * @return
	 * @throws Exception
	 */
	int update(Medicine medicine) throws Exception;


}
