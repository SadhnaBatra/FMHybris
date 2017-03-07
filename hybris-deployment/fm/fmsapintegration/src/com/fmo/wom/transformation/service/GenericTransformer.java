package com.fmo.wom.transformation.service;

import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMIPOTransformationException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.transformation.util.JAXBTransformer;
import com.fmo.wom.transformation.util.TransformationConstants;
import com.fmo.wom.transformation.util.XSLTransformer;

public abstract class GenericTransformer implements TransformationService{
	private static Logger logger = Logger.getLogger(GenericTransformer.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public Object transformRequest(String requestedXML,String ipoVersion,String clientName) 
		  throws WOMIPOTransformationException,WOMResourceException{
		boolean isValidateXml = false;
		if(TransformationConstants.IpoVersion.VERSION2_0.getValue().equals(ipoVersion)){
			isValidateXml = true;
		}
		logger.debug("transformRequest(...): incoming XML :: requestedXML = " + requestedXML);
		String commonSchmeXML = XSLTransformer.transformRequest(
				getXslFile(ipoVersion,clientName), requestedXML);
		logger.debug("transformRequest(...): Incoming CommonSchemaXML =\n" + commonSchmeXML);
		Object boModelObj = JAXBTransformer.unMarshall(commonSchmeXML);
		return ((JAXBElement<Object>)boModelObj).getValue();
	}

	@Override
	public String transformResponse(String requestedXml, Object processedData,String ipoVersion,String clientName) 
		  throws WOMIPOTransformationException,WOMResourceException{

//		boolean isValidateXml = false;
//		if(TransformationConstants.IpoVersion.VERSION2_0.equals(ipoVersion)){
////			isValidateXml = true;
//		}

		String commonSchmeXML = null;
		if(processedData instanceof String){
			commonSchmeXML = (String)processedData;
		}else{
			commonSchmeXML = JAXBTransformer.marshall(processedData);
		}
		logger.debug("transformResponse(...): Outgoing CommonSchemaXML =\n" + commonSchmeXML);
		String responseXml = XSLTransformer.transform(getXslFile(ipoVersion,clientName),commonSchmeXML, requestedXml,null);
		logger.debug("transformResponse(...): Outgoing XML:: responseXml = " + responseXml);
		return responseXml;
	}

	/**
	 * Locate proper XSL file for a given ipoVersion and clientName
	 * @param ipoVersion	String ipoVersion 
	 * @param clientName	String clientName
	 * @return				String xsl file path
	 */
	public abstract String getXslFile(TransformationConstants.IpoVersion version, String clientName);	
	
	public static TransformationConstants.IpoVersion getIpoVersion(String versionNumber){
		TransformationConstants.IpoVersion ipoVersion = TransformationConstants.IpoVersion.VERSION1_2;
		if(versionNumber!=null && versionNumber.startsWith("2.")){
			ipoVersion = TransformationConstants.IpoVersion.VERSION2_0;
		}
		return ipoVersion;
		
	}

	public String getXslFile(final String ipoVersion, final String name) {
		String clientName = name;
		TransformationConstants.IpoVersion version = GenericTransformer.getIpoVersion(ipoVersion);
		if(TransformationConstants.IpoVersion.VERSION2_0.equals(version)){
			clientName = null;
		}
		String xslFile = getXslFile(version,clientName);
		if(clientName !=null && XSLTransformer.getXslTransformers().get(xslFile)==null && XSLTransformer.getXslTransformers().size()>0){
			xslFile =  getXslFile(ipoVersion,null);
		}
		return xslFile;
	}
	
}
