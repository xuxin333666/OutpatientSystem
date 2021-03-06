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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicineType other = (MedicineType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}




	
	
	
	
}
