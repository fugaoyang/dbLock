package com.dbLock.model;


import java.io.Serializable;
import java.util.Date;

public class TradeSyncLock implements Serializable {

	private static final long serialVersionUID = -6430633825913553154L;
	
	private Long id;

	private String outerSerialNo;

	private String custNo;

	private String sourceCode;
	
	private String threadNo;

	private String status;

	private String remark;

	private Date createdAt;

	private Date updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOuterSerialNo() {
		return outerSerialNo;
	}

	public void setOuterSerialNo(String outerSerialNo) {
		this.outerSerialNo = outerSerialNo == null ? null : outerSerialNo.trim();
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo == null ? null : custNo.trim();
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode == null ? null : sourceCode.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getThreadNo() {
		return threadNo;
	}

	public void setThreadNo(String threadNo) {
		this.threadNo = threadNo;
	}

}