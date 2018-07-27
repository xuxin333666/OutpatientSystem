package cn.kgc.dao.impl;

import cn.kgc.dao.intf.PrescriptionDao;

public class PrescriptionDaoImpl extends BaseDaoImpl implements PrescriptionDao {

	@Override
	public String queryMinEmptyId() throws Exception {
		String sql = "SELECT minid FROM (SELECT id,ROWNUM+100000 minid FROM t_prescription ORDER BY id) WHERE minid <> id AND ROWNUM = 1";
		String sql2 = "SELECT nvl(max(id),100000)+1 minid FROM t_prescription";
		return queryMinEmptyId(sql,sql2,"minid");
	}

}
