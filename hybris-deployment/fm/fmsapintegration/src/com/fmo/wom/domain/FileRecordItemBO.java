package com.fmo.wom.domain;

import java.util.HashMap;

public class FileRecordItemBO {

	private int lineNumber = -1;
	private String recordStatus = null;
	private HashMap<Integer, String> recordMap = new HashMap<Integer, String>();
	
	
	public FileRecordItemBO() {}
	
	public void addRecordField(int fieldPositionID, String fieldValue) {
		this.recordMap.put(fieldPositionID, fieldValue);
	}
	
	public String getRecordFieldValue(int fieldPositionID) {
		return this.recordMap.get(fieldPositionID);
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	
}
