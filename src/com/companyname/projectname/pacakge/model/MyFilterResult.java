package com.companyname.projectname.pacakge.model;

import java.io.Serializable;
import java.util.ArrayList;
@SuppressWarnings("rawtypes")
public class MyFilterResult implements Serializable{
	
    /**
	 * This is the result object sent back to the Flex Grid.
	 * Need to send back the number of records for the pager 
	 * to work
	 */
	private static final long serialVersionUID = 1841222755681953556L;
	public MyFilterResult(ArrayList results, int recordCount) {
		this.records = results;
		this.totalRecords = recordCount;
	}
	public ArrayList records;
    public int totalRecords;
}
