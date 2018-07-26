package cn.kgc.dto;

public class MedicineDto {

	private String id;
	private String name;
	private String price;
	private String typeName;
	
	
	
	public MedicineDto(String id, String name, String price, String typeName) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.typeName = typeName;
	}
	
	public MedicineDto(String args) {
		this.id = args;
		this.name = args;
		this.price = args;
		this.typeName = args;
	}
	
	public MedicineDto() {}
	
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	
}
