package com.companyname.projectname.pacakge.model;

import java.io.Serializable;

public class MyFilterExpression implements Serializable {

	private static final long serialVersionUID = -4011716566714855728L;
	public String columnName;
	public String filterOperation;
	public Object expression;
	public String filterComparisonType;
}
