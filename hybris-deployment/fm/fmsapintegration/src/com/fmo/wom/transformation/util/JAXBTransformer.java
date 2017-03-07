package com.fmo.wom.transformation.util;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMBaseUncheckedException;
import com.fmo.wom.common.exception.WOMIPOTransformationException;
import com.fmo.wom.common.util.StringUtil;

public class JAXBTransformer {
	public static final Logger logger = Logger.getLogger(JAXBTransformer.class);
	static JAXBContext context = null;
	static byte[] ILLEGAL_XML_1_0_CHARS;	
	static{
        final StringBuffer buff = new StringBuffer();
        for (char i = 0x0000; i < 0x0020; i++) {
            if (i != 0x0009 &&
                    i != 0x000A &&
                    i != 0x000D) {
                buff.append(i);
            }
        }
        ILLEGAL_XML_1_0_CHARS = buff.toString().getBytes();
        Arrays.sort(ILLEGAL_XML_1_0_CHARS);
		
		try {
			context = JAXBContext.newInstance(TransformationConstants.DOMAIN_ROOT_PACKAGE);
		} catch (JAXBException e) {
			logger.error("Error while creating the JAXBContext:: ", e);
			throw new WOMBaseUncheckedException("Error while creating the JAXBContext");
		}
	}
	/**
	 * converts the XML string into correponding java Object
	 * @param xmlData	String xml representation of object data
	 * @return			Object 
	 * @throws WOMIPOTransformationException 
	 * @throws Exception
	 */
	public static Object unMarshall(String xmlData) throws WOMIPOTransformationException {
		
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return unmarshaller.unmarshal(StringUtil.getStringSource(xmlData));
		} catch (Exception e) {
			logger.error("Error while unmarshalling::error ", e);
			throw new WOMIPOTransformationException("Error while unmarshalling."+e.toString(),e);
		}
	}
	/**
	 * Marshalls the java Object i to XML string
	 * @param obj	Object
	 * @return		String xml representation of java Object
	 * @throws WOMIPOTransformationException
	 */
	public static String marshall(Object obj) throws WOMIPOTransformationException {
		Writer out = new StringWriter();
		try{
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(obj, out);
		} catch (Exception e) {
			logger.error("Error while marshalling::error ",e);
			throw new WOMIPOTransformationException("Error while marshalling."+e.toString(),e);
		}
		return cleanStringForXml(out.toString(),' ');
	}
    /**
     * Cleans a given String, so that it can be safely used in XML.
     * All Invalid characters, will be replaced with the given replace
	 *	character.
     * Valid XML characters are described here:
     * {@link "http://www.w3c.org/TR/2000/REC-xml-20001006#dt-character"}
     *
     * @param pString      the string to clean
     * @param pReplacement the char to use to replace the invalid characters
     * @return the string, cleaned for XML.
     */
    public static String cleanStringForXml(String pString, char pReplacement) {
        final byte[] bytes = pString.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            byte aByte = bytes[i];
            if (Arrays.binarySearch(ILLEGAL_XML_1_0_CHARS, aByte) >= 0) {
                bytes[i] = (byte) pReplacement;
            }
        }
        return new String(bytes);
    }	
	
}
