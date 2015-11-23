package com.companyname.projectname.pacakge.model;

import java.io.Serializable;

public class DepartmentDTO implements Serializable {

	private static final long serialVersionUID = 5442035754249271337L;
	public String departmentName;
	public int departmentId;
	
	public DepartmentDTO(String departmentName, int departmentId)
	{
		this.departmentId = departmentId;
		this.departmentName = departmentName;
	}
	
}
