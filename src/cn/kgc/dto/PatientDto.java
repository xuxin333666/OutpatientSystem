package cn.kgc.dto;

public class PatientDto {

	private String startTimeStr;
	private String endTimeStr;
	private String queryColumnNameStr;
	private String keyStr;
	
	
	public PatientDto(String startTimeStr, String endTimeStr, String queryColumnNameStr, String keyStr) {
		this.startTimeStr = startTimeStr;
		this.endTimeStr = endTimeStr;
		this.queryColumnNameStr = queryColumnNameStr;
		this.keyStr = keyStr;
	}
	
	public PatientDto() {}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getQueryColumnNameStr() {
		return queryColumnNameStr;
	}

	public void setQueryColumnNameStr(String queryColumnNameStr) {
		this.queryColumnNameStr = queryColumnNameStr;
	}

	public String getKeyStr() {
		return keyStr;
	}

	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}
	
	
}
