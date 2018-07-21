package cn.kgc.model;

public class User {
	private String id;
	private String name;
	private String pwd;
	private String status;
	
	
	public User(String name, String pwd) {
		this.name = name;
		this.pwd = pwd;
	}
	public User(String id, String name, String pwd, String status) {
		this(name,pwd);
		this.id = id;
		this.status = status;
	}
	public User() {}
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
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
