package com.fmo.wom.webservice.handler;

import com.fmo.wom.common.exception.WOMProcessException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.transformation.service.TransformationService;

public interface IBusinessProcessExecutor {
	public Object executeProcess(Object obj) throws WOMResourceException, WOMValidationException, WOMProcessException;
	public TransformationService getRequestTransformationService();
	public TransformationService getResponseTransformationService();
}
