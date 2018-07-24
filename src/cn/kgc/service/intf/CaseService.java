package cn.kgc.service.intf;

public interface CaseService {

	Object[][] getAllCaseInfoByPatient(String patientId) throws Exception;

	Object[][] getAllCaseInfo() throws IllegalArgumentException, IllegalAccessException;


}
