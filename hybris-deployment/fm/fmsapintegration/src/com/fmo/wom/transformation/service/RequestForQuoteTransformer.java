package com.fmo.wom.transformation.service;

import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.transformation.util.TransformationConstants;
import com.fmo.wom.transformation.util.TransformationConstants.XSL_FILENAME;


public class RequestForQuoteTransformer extends GenericTransformer{
	
	@Override
	public String getXslFile(TransformationConstants.IpoVersion version, String clientName) {
		return TransformationConstants.XSL_ROOT_FOLDER.INCOMING.toString().toLowerCase()+TransformationConstants.FILE_PATH_SEPERATOR+
				version.getValue()+TransformationConstants.FILE_PATH_SEPERATOR+
				XSL_FILENAME.RFQRequest+(clientName!=null&&clientName.length() > 0?"_"+clientName.toLowerCase():"")+".xsl";
	}

	@Override
	public String getSchema(String version, String clientName)
			throws WOMResourceException {
		return TransformationConstants.XSL_ROOT_FOLDER.SCHEMAS.toString().toLowerCase()+TransformationConstants.FILE_PATH_SEPERATOR+
		   version+TransformationConstants.FILE_PATH_SEPERATOR+
		   "BODs/AddRequestForQuote.xsd";
	}
	

//	@Override
//	public String getResponseTemplateXml(String ipoVersion, String clientName) throws WOMResourceException {
//		return null;
//	}
}
