package com.fmo.wom.integration.nabs.action;

import java.util.List;

public class NabsStoredProcOutput {

	private static String RETURN_CODE_SUCCESS = "000";
	
	// Attribute to store Stored Procedure Result Set data.
	private List<?> resultData;
	// Attribute to store Return Code.
	private String returnCode;
	
	public List<?> getResultData() {
		return resultData;
	}
	
	public void setResultData(List<?> resultData) {
		this.resultData = resultData;
	}
	
	public String getReturnCode() {
		return returnCode;
	}
	
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public boolean isReturnStatusSuccessful() {
		
		if (this.returnCode.trim().equals(RETURN_CODE_SUCCESS)) {
			return true;
		}
		
		return false;
	}
}
