package cn.kgc.model;

public class Medicine {
	private String id;
	private String name;
	private String norms;
	private String unit;
	private Double price;
	private String uasge;
	private String needingAttention;
	private MedicineType medicineType;
	
	
	public Medicine() {}
	public Medicine(String id, String name, String norms, String unit, Double price, String uasge,
			String needingAttention, MedicineType medicineType) {
		super();
		this.id = id;
		this.name = name;
		this.norms = norms;
		this.unit = unit;
		this.price = price;
		this.uasge = uasge;
		this.needingAttention = needingAttention;
		this.medicineType = medicineType;
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
	public String getNorms() {
		return norms;
	}
	public void setNorms(String norms) {
		this.norms = norms;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getUasge() {
		return uasge;
	}
	public void setUasge(String uasge) {
		this.uasge = uasge;
	}
	public String getNeedingAttention() {
		return needingAttention;
	}
	public void setNeedingAttention(String needingAttention) {
		this.needingAttention = needingAttention;
	}
	public MedicineType getMedicineType() {
		return medicineType;
	}
	public void setMedicineType(MedicineType medicineType) {
		this.medicineType = medicineType;
	}
	
	
	
	
	
	
	
}
