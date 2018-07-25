package cn.kgc.model;

public class MedicineType {
	private String id;
	private String name;
	private String parentMedicineTypeId;
	
	
	public MedicineType() {}
	
	public MedicineType(String id, String name, String parentMedicineTypeId) {
		this.id = id;
		this.name = name;
		this.parentMedicineTypeId = parentMedicineTypeId;
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

	public String getParentMedicineTypeId() {
		return parentMedicineTypeId;
	}

	public void setParentMedicineTypeId(String parentMedicineTypeId) {
		this.parentMedicineTypeId = parentMedicineTypeId;
	}
	
	
	
	
}
