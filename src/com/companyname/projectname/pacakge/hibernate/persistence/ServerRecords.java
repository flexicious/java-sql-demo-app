
package com.companyname.projectname.pacakge.hibernate.persistence;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="server_records")
public class ServerRecords {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="record_type")
	private String recordType;
	
	@Column(name="record_id")
	private int recordId;
	
	@Column
	private String record;
	
	@Column
	private String site;
	
	@Column
	private String system;
	
	@Column(name="start_time")
	private Timestamp startTime;
	
	@Column(name="run_time")
	private BigInteger runTime;
	
	@Column
	private String status;
	
	@Column
	private String jenkins;
	
	@Column
	private String result;

    @Transient
	private Number childCounts;

	@Transient
	private List<ServerRecords> children;
	
	public ServerRecords() {
		
	}

	public ServerRecords(int id, String recordType, int record_id,
			String record, String site, String system, Timestamp startTime,
            BigInteger runTime, String status, String jenkins, String result) {
		super();
		this.id = id;
		this.recordType = recordType;
		this.recordId = record_id;
		this.record = record;
		this.site = site;
		this.system = system;
		this.startTime = startTime;
		this.runTime = runTime;
		this.status = status;
		this.jenkins = jenkins;
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

    public String getRecord_type() {
        return recordType;
    }

    public void setRecord_type(String recordType) {
        this.recordType = recordType;
    }

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int record_id) {
		this.recordId = record_id;
	}

    public int getRecord_id() {
        return recordId;
    }

    public void setRecord_id(int record_id) {
        this.recordId = record_id;
    }

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

    public Timestamp getStart_time() {
        return startTime;
    }

    public void setStart_time(Timestamp startTime) {
        this.startTime = startTime;
    }

    public BigInteger getRunTime() {
		return runTime;
	}

	public void setRunTime(BigInteger runTime) {
		this.runTime = runTime;
	}

    public BigInteger getRun_time() {
        return runTime;
    }

    public void setRun_time(BigInteger runTime) {
        this.runTime = runTime;
    }



    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getJenkins() {
		return jenkins;
	}

	public void setJenkins(String jenkins) {
		this.jenkins = jenkins;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Number getChildCounts() {
		return childCounts;
	}

	public void setChildCounts(Number childCounts) {
        this.childCounts = childCounts;
	}

	public List<ServerRecords> getChildren() {
		return children;
	}

	public void setChildren(List<ServerRecords> children) {
		this.children = children;
	}

}
