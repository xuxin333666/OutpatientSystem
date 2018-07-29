package cn.kgc.dao.intf;

import java.util.List;

import cn.kgc.model.Case;

public interface CaseDao {

	/**
	 * 根据病人的id查询所有病例，组成list返回
	 * @param patientId
	 * @return
	 * @throws Exception
	 */
	List<Case> queryByPatientId(String patientId) throws Exception;

	/**
	 * 根据病例的id查询病例，返回唯一的病例对象
	 * @param caseId
	 * @param patientId
	 * @return
	 * @throws Exception
	 */
	Case queryByPatientIdAndId(String caseId, String patientId) throws Exception;

	/**
	 * 查询病例表数据库中最小的未被使用的id号，返回字符串的该值
	 * @return
	 * @throws Exception
	 */
	String queryMinEmptyId() throws Exception;
	
	/**
	 * 将给定的病例加入病例表数据库，返回该操作所影响的行数
	 * @param $case
	 * @return
	 * @throws Exception
	 */
	int insert(Case $case) throws Exception;

	/**
	 * 根据给定的病例的id修改数据库的病例，返回该操作所影响的行数
	 * @param $case
	 * @return
	 * @throws Exception
	 */
	int Update(Case $case) throws Exception;

	/**
	 * 根据给定的病例id删除相应的病例，返回该操作所影响的行数
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	int delete(String cid) throws Exception;

}
