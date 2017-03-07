package com.fmo.wom.transformation.service;

import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.transformation.util.TransformationConstants;

public class ConfirmBODTransformer  extends GenericTransformer{
//	private String type = null;
//	public ConfirmBODTransformer(String type){		
//		super();
//		this.type = type;
//
//	}

	@Override
	public String getXslFile(TransformationConstants.IpoVersion version, String clientName) {
		return TransformationConstants.XSL_ROOT_FOLDER.CONFIRM.toString().toLowerCase()+TransformationConstants.FILE_PATH_SEPERATOR+
				version.getValue()+TransformationConstants.FILE_PATH_SEPERATOR+"ConfirmResponse.xsl";
	}
//	@Override
//	public String getResponseTemplateXml(String ipoVersion, String clientName)
//			throws WOMResourceException {
//		TransformationConstants.IpoVersion version = GenericTransformer.getIpoVersion(ipoVersion);
//		return  StringUtil.readFileFromClasspath(XSLTransformer.class, TransformationConstants.FILE_PATH_SEPERATOR+TransformationConstants.XSL_ROOT_FOLDER.CONFIRM.toString().toLowerCase()+TransformationConstants.FILE_PATH_SEPERATOR+
//					  version.getValue()+TransformationConstants.FILE_PATH_SEPERATOR+"ConfirmResponseTemplate.xml");	}
//	

	@Override
	public String getSchema(String version, String clientName)
			throws WOMResourceException {
		return TransformationConstants.XSL_ROOT_FOLDER.SCHEMAS.toString().toLowerCase()+TransformationConstants.FILE_PATH_SEPERATOR+
		   version+TransformationConstants.FILE_PATH_SEPERATOR+
		   "BODs/Confirmation.xsd";
	}
	
}
