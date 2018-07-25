package cn.kgc.model;

public class MedicineType {
	private String id;
	private String name;
	private MedicineType parentMedicineType;
	
	
	public MedicineType() {}
	
	public MedicineType(String id, String name, MedicineType parentMedicineType) {
		this.id = id;
		this.name = name;
		this.parentMedicineType = parentMedicineType;
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

	public MedicineType getParentMedicineType() {
		return parentMedicineType;
	}

	public void setParentMedicineType(MedicineType parentMedicineType) {
		this.parentMedicineType = parentMedicineType;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
	
	
}
