package com.companyname.projectname.pacakge.model;

public class MyResponse {
	public Boolean success;
	public String message;
	public Object data;
	public Details details;

	public MyResponse() {
		details = new Details();
	}
	
	public static class Details{
		public Integer totalRecords;
	}
}
