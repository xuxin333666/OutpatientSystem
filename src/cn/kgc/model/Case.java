package cn.kgc.model;

import java.util.Date;

public class Case {
	private Date examinationTime;
	private String mainSymptom;
	private String nowSymptom;
	private String pastSymptom;
	private String personalSymptom;
	private String bodyTest;
	private String labTest;
	private String examination;
	private String advice;
	private String otherExplain;
	private Patient patient;
	private String id;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMainSymptom() {
		return mainSymptom;
	}
	public void setMainSymptom(String mainSymptom) {
		this.mainSymptom = mainSymptom;
	}
	public String getNowSymptom() {
		return nowSymptom;
	}
	public void setNowSymptom(String nowSymptom) {
		this.nowSymptom = nowSymptom;
	}
	public String getPastSymptom() {
		return pastSymptom;
	}
	public void setPastSymptom(String pastSymptom) {
		this.pastSymptom = pastSymptom;
	}
	public String getPersonalSymptom() {
		return personalSymptom;
	}
	public void setPersonalSymptom(String personalSymptom) {
		this.personalSymptom = personalSymptom;
	}
	public String getBodyTest() {
		return bodyTest;
	}
	public void setBodyTest(String bodyTest) {
		this.bodyTest = bodyTest;
	}
	public String getLabTest() {
		return labTest;
	}
	public void setLabTest(String labTest) {
		this.labTest = labTest;
	}
	public String getExamination() {
		return examination;
	}
	public void setExamination(String examination) {
		this.examination = examination;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	public String getOtherExplain() {
		return otherExplain;
	}
	public void setOtherExplain(String otherExplain) {
		this.otherExplain = otherExplain;
	}
	public Date getExaminationTime() {
		return examinationTime;
	}
	public void setExaminationTime(Date examinationTime) {
		this.examinationTime = examinationTime;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Case(String id, String mainSymptom, String nowSymptom, String pastSymptom, String personalSymptom,
			String bodyTest, String labTest, String examination, String advice, String otherExplain,
			Date examinationTime, Patient patient) {
		super();
		this.id = id;
		this.mainSymptom = mainSymptom;
		this.nowSymptom = nowSymptom;
		this.pastSymptom = pastSymptom;
		this.personalSymptom = personalSymptom;
		this.bodyTest = bodyTest;
		this.labTest = labTest;
		this.examination = examination;
		this.advice = advice;
		this.otherExplain = otherExplain;
		this.examinationTime = examinationTime;
		this.patient = patient;
	}
	public Case() {}
}
