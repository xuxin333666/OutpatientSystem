package cn.kgc.model;

import java.util.Date;

public class Prescription {
 private String id;
 private Date time;
 private String advice;
 private Double price;
 private Case $case;
 private Patient patient;
 
public Prescription(String id, Date time, Case $case, Patient patient,String advice,Double price) {
	this.id = id;
	this.time = time;
	this.advice = advice;
	this.price = price;
	this.$case = $case;
	this.patient = patient;
}
public Prescription() {}

public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public Date getTime() {
	return time;
}
public void setTime(Date time) {
	this.time = time;
}
public Case get$case() {
	return $case;
}
public void set$case(Case $case) {
	this.$case = $case;
}
public Patient getPatient() {
	return patient;
}
public void setPatient(Patient patient) {
	this.patient = patient;
}
public String getAdvice() {
	return advice;
}
public void setAdvice(String advice) {
	this.advice = advice;
}
public Double getPrice() {
	return price;
}
public void setPrice(Double price) {
	this.price = price;
}
@Override
public String toString() {
	return "Prescription [id=" + id + ", time=" + time + ", advice=" + advice + ", price=" + price + ", $case=" + $case
			+ ", patient=" + patient + "]";
}
}
