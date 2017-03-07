package com.fmo.wom.transformation.service;

import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.transformation.util.TransformationConstants;


public class AcknowledgePurchaseOrderTransformer extends GenericTransformer{

	@Override
	public String getXslFile(TransformationConstants.IpoVersion version, String clientName){
		return TransformationConstants.XSL_ROOT_FOLDER.OUTGOING.toString().toLowerCase()+TransformationConstants.FILE_PATH_SEPERATOR+
			   version.getValue()+TransformationConstants.FILE_PATH_SEPERATOR+
			   TransformationConstants.XSL_FILENAME.APOResponse+
			   (clientName!=null&&clientName.length() > 0?TransformationConstants.UNDERSCORE_STR+clientName.toLowerCase():TransformationConstants.BLANK_STR)+
			   TransformationConstants.XSL_FILE_EXTENSION;
	}

	@Override
	public String getSchema(String version, String clientName)
			throws WOMResourceException {
		return TransformationConstants.XSL_ROOT_FOLDER.SCHEMAS.toString().toLowerCase()+TransformationConstants.FILE_PATH_SEPERATOR+
		   version+TransformationConstants.FILE_PATH_SEPERATOR+
		   "BODs/AcknowledgePurchaseOrder.xsd";
	}

//	@Override
//	public String getResponseTemplateXml(String ipoVersion, String clientName) throws WOMResourceException {
//		TransformationConstants.IpoVersion version = GenericTransformer.getIpoVersion(ipoVersion);
//		String templateFilePath =TransformationConstants.FILE_PATH_SEPERATOR+TransformationConstants.XSL_ROOT_FOLDER.AQ.toString().toLowerCase()+TransformationConstants.FILE_PATH_SEPERATOR+ 
//				version.getValue()+TransformationConstants.FILE_PATH_SEPERATOR+
//				TransformationConstants.XSL_FILENAME.AqTemplate+TransformationConstants.XML_FILE_EXTENSION;
//		// TODO Cache the xml file here to increase the performance
//		return  StringUtil.readFileFromClasspath(XSLTransformer.class, templateFilePath);
//	}
}
