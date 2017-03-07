package com.fmo.wom.domain;

import java.util.List;

public class ProductFlagOption {
	int lineNumber;
	String diplayPartNum;
	List<SupplierCdProductFlgBO> scpfBOList;
	
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getDiplayPartNum() {
		return diplayPartNum;
	}
	public void setDiplayPartNum(String diplayPartNum) {
		this.diplayPartNum = diplayPartNum;
	}
	public List<SupplierCdProductFlgBO> getScpfBOList() {
		return scpfBOList;
	}
	public void setScpfBOList(List<SupplierCdProductFlgBO> scpfBOList) {
		this.scpfBOList = scpfBOList;
	}

}
