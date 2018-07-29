package cn.kgc.dao.intf;

import java.util.List;

import cn.kgc.dto.PatientDto;
import cn.kgc.model.Patient;

public interface PatientDao {
	
	/**
	 * 全条件查询病人数据，返回list
	 * @return
	 * @throws Exception
	 */
	List<Patient> query() throws Exception;

	/**
	 * 根据dto查询满足条件的病人数据，返回list
	 * @param patientDto
	 * @return
	 * @throws Exception
	 */
	List<Patient> query(PatientDto patientDto) throws Exception;

	/**
	 * 根据病人id查询病人数据。返回盖病人对象
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Patient query(String id) throws Exception;

	/**
	 * 查询病人表数据库中最小的未被使用的id号，返回字符串的该值
	 * @return
	 * @throws Exception
	 */
	String queryMinEmptyId() throws Exception;

	/**
	 * 将病人对象添加到数据库，返回影响行数
	 * @param patient
	 * @return
	 * @throws Exception
	 */
	int insert(Patient patient) throws Exception;

	/**
	 * 根据给定的病人id和数据修改数据库数据。返回影响行数
	 * @param patient
	 * @return
	 * @throws Exception
	 */
	int update(Patient patient) throws Exception;

	/**
	 * 根据给定的病人id删除病人数据，返回影响行数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int delete(String id) throws Exception;
	
}
