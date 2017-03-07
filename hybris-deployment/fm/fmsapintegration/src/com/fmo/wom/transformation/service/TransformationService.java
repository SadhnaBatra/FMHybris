package com.fmo.wom.transformation.service;

import com.fmo.wom.common.exception.WOMIPOTransformationException;
import com.fmo.wom.common.exception.WOMResourceException;
/**
 * Transformation service for converting the IPO xml file
 * @author chanac32
 *
 */
public interface TransformationService {
	/**
	 * Transforms the incoming XML into Java objects
	 * @param requestedXML
	 * @return
	 * @throws WOMIPOTransformationException
	 * @throws WOMResourceException
	 */
	public Object transformRequest(String requestedXML,String ipoVersion,String clientName) 
		throws WOMIPOTransformationException,WOMResourceException;
	/**
	 * Transforms the java Object into output XML file
	 * @param referencedXml
	 * @param processedData
	 * @return
	 * @throws WOMIPOTransformationException
	 * @throws WOMResourceException
	 */
	public String transformResponse(String referencedXml,Object processedData,String ipoVersion,String clientName)
		throws WOMIPOTransformationException,WOMResourceException;
	public String getSchema(String version, String clientName) throws WOMResourceException;
}
