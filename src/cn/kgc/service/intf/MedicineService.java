package cn.kgc.service.intf;

public interface MedicineService {
	
	Object[][] getAllInfo() throws Exception;
	
	Object[][] getAllInfoBySearch(String patientId) throws Exception;
}
