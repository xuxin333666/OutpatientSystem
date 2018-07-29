package cn.kgc.dao.impl;

import cn.kgc.dao.intf.PrescriptionMedicineDao;
import cn.kgc.model.PrescriptionMedicine;

public class PrescriptionMedicineDaoImpl extends BaseDaoImpl implements PrescriptionMedicineDao {

	@Override
	public String queryMinEmptyId() throws Exception {
		String sql = "SELECT minid FROM (SELECT id,ROWNUM minid FROM t_prescription_medicine ORDER BY id) WHERE minid <> id AND ROWNUM = 1";
		String sql2 = "SELECT nvl(max(id),0)+1 minid FROM t_prescription_medicine";
		return queryMinEmptyId(sql,sql2,"minid");
	}

	@Override
	public int insert(PrescriptionMedicine prescriptionMedicine) throws Exception {
		String sql =  "insert into t_prescription_medicine values (?,?,?,?)";
		return insert(sql, prescriptionMedicine, 0, 3);
	}

}
