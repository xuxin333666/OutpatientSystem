package cn.kgc.model;

public class PrescriptionMedicine {
	private String id;
	private String prescriptionId;
	private String medicineId;
	private String uasge;
	
	
	public PrescriptionMedicine(String id, String prescriptionId, String medicineId, String uasge) {
		this.id = id;
		this.prescriptionId = prescriptionId;
		this.medicineId = medicineId;
		this.uasge = uasge;
	}
	public PrescriptionMedicine() {}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPrescriptionId() {
		return prescriptionId;
	}
	public void setPrescriptionId(String prescriptionId) {
		this.prescriptionId = prescriptionId;
	}
	public String getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(String medicineId) {
		this.medicineId = medicineId;
	}
	public String getUasge() {
		return uasge;
	}
	public void setUasge(String uasge) {
		this.uasge = uasge;
	}
}
