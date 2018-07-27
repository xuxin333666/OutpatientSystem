package cn.kgc.dao.impl;

import cn.kgc.dao.intf.PrescriptionDao;
import cn.kgc.model.Prescription;

public class PrescriptionDaoImpl extends BaseDaoImpl implements PrescriptionDao {

	@Override
	public String queryMinEmptyId() throws Exception {
		String sql = "SELECT minid FROM (SELECT id,ROWNUM+100000 minid FROM t_prescription ORDER BY id) WHERE minid <> id AND ROWNUM = 1";
		String sql2 = "SELECT nvl(max(id),100000)+1 minid FROM t_prescription";
		return queryMinEmptyId(sql,sql2,"minid");
	}

	@Override
	public int insert(Prescription prescription) throws Exception {
		String sql =  "insert into t_prescription values (?,nvl(?,sysdate),?,?,?,?)";
		return insert(sql, prescription, 0, 3,new String[]{prescription.get$case().getId(),prescription.getPatient().getId()});
	}

}
