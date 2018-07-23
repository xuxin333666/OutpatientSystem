package cn.kgc.model;

import java.util.Date;

public class Patient {
	private String id;
	private String name;
	private String sex;
	private Double age;
	private String married;
	private String job;
	private Double weight;
	private String blood;
	private String phoneNumber;
	private Date registerTime;
	private String address;
	private String allergy;
	private String handlingSug;
	private String remark;
	
	
	public Patient() {}
	
	
	public Patient(String id, String name, String sex, 
			Double age, String married, String job, Double weight,
			String blood, String phoneNumber, Date registerTime, 
			String address, String allergy,String handlingSug, String remark) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.married = married;
		this.job = job;
		this.weight = weight;
		this.blood = blood;
		this.phoneNumber = phoneNumber;
		this.registerTime = registerTime;
		this.address = address;
		this.allergy = allergy;
		this.handlingSug = handlingSug;
		this.remark = remark;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public Double getAge() {
		return age;
	}


	public void setAge(Double age) {
		this.age = age;
	}


	public String getMarried() {
		return married;
	}


	public void setMarried(String married) {
		this.married = married;
	}


	public String getJob() {
		return job;
	}


	public void setJob(String job) {
		this.job = job;
	}


	public Double getWeight() {
		return weight;
	}


	public void setWeight(Double weight) {
		this.weight = weight;
	}


	public String getBlood() {
		return blood;
	}


	public void setBlood(String blood) {
		this.blood = blood;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public Date getRegisterTime() {
		return registerTime;
	}


	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getAllergy() {
		return allergy;
	}


	public void setAllergy(String allergy) {
		this.allergy = allergy;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getHandlingSug() {
		return handlingSug;
	}


	public void setHandlingSug(String handlingSug) {
		this.handlingSug = handlingSug;
	}


	@Override
	public String toString() {
		return "Patient [id=" + id + ", name=" + name + ", sex=" + sex + ", age=" + age + ", married=" + married
				+ ", job=" + job + ", weight=" + weight + ", blood=" + blood + ", phoneNumber=" + phoneNumber
				+ ", registerTime=" + registerTime + ", address=" + address + ", allergy=" + allergy + ", handlingSug="
				+ handlingSug + ", remark=" + remark + "]";
	}
	
	
}
