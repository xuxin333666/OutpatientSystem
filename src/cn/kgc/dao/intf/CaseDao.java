package cn.kgc.dao.intf;

import java.util.List;

import cn.kgc.model.Case;

public interface CaseDao {

	List<Case> queryByPatientId(String patientId) throws Exception;

}
