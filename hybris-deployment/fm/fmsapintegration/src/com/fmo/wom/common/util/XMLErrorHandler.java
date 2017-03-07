package com.fmo.wom.common.util;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLErrorHandler extends DefaultHandler{
	private int errorCount=0;
	private List<String> errorMessages = new ArrayList<String>();
	
	public void warning(SAXParseException exception)
	throws SAXException {
		processException(exception);
	}

	public void error(SAXParseException exception)
	throws SAXException {
		processException(exception);
	}

	public void fatalError(SAXParseException exception)
	throws SAXException {
		processException(exception);
	}

	public void processException(SAXParseException exception) {
		System.err.println(exception);
		errorCount++;
		errorMessages.add(exception.toString());
	}

	public int getErrorCount() {
		return errorCount;
	}
	public String getAllErrorMessages(){
		StringBuffer errors = new StringBuffer();
		for(String errorMessage:errorMessages){
			errors.append("<validationError>");
			errors.append(errorMessages);
			errors.append("</validationError>");
		}
		return errors.toString();
	}

}



