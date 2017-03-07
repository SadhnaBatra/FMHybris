/**
 *
 * Copyright 2014 Wipro Technologies All rights reserved.
 * 
 * Customer specific copyright notice     :Federal Mogul
 *
 * File Name       : SoapMessageCreator.java
 *
 * Version         : 0.0.0.1
 *
 * Created Date    :26-DEC-2014
 * 
 * Modification History:Modified by, on date.
 */
package com.fm.falcon.webservices.soap.common;

import de.hybris.platform.util.Config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import sun.misc.BASE64Encoder;

import com.fm.falcon.webservices.soap.AbstractSoapMessage;


/**
 * Abstract class for SOAP message creation.
 * 
 */
public abstract class SoapMessageCreator implements AbstractSoapMessage
{

	/**
	 * Logger class to maintain the data related to SoapMessageCreator class
	 */
	private static final Logger LOG = Logger.getLogger(SoapMessageCreator.class);


	/**
	 * Method for creating SOAP Message structure.
	 * 
	 * @return soapMessage
	 * @throws UnsupportedOperationException
	 * @throws SOAPException
	 * 
	 */
	public SOAPMessage createSoapMessage() throws UnsupportedOperationException, SOAPException
	{

		final MessageFactory messageFactory = MessageFactory.newInstance();
		final SOAPMessage soapMessage = messageFactory.createMessage();
		return soapMessage;
	}

	/**
	 * Method for creating SOAP part in existing SOAP message with the request message name space
	 * 
	 * @param message
	 * 
	 * @return soapBodyElement
	 * @throws UnsupportedOperationException
	 * @throws SOAPException
	 * 
	 */
	public SOAPBodyElement createSoapBody(final SOAPMessage message, final String urn, final String nameSpace,
			final String serviceName) throws UnsupportedOperationException, SOAPException
	{
		final SOAPPart soapPart = message.getSOAPPart();
		final SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		final Name header = soapEnvelope.createName(serviceName, urn, nameSpace);
		soapEnvelope.addNamespaceDeclaration(urn, nameSpace);
		final SOAPBody soapBody = soapEnvelope.getBody();
		//final SOAPBodyElement soapBodyElement = soapBody.addBodyElement(header);
		final SOAPBodyElement soapBodyElement = soapBody.addBodyElement(header);
		return soapBodyElement;
	}

	public SOAPBodyElement createLoyaltyEarnPointsSoapBody(final SOAPMessage message, final String urn, final String nameSpace,
			final String serviceName) throws UnsupportedOperationException, SOAPException
	{
		final SOAPPart soapPart = message.getSOAPPart();
		final SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		final Name header = soapEnvelope.createName(serviceName, urn, nameSpace);
		soapEnvelope.addNamespaceDeclaration(urn, nameSpace);
		final SOAPBody soapBody = soapEnvelope.getBody();

		//final SOAPBodyElement soapBodyElement = soapBody.addBodyElement(header);
		//final SOAPBodyElement soapBodyElement = soapBody.addBodyElement(header);
		final SOAPBodyElement soapBodyElement = (SOAPBodyElement) soapBody.addChildElement("MT_LOYALTY_EARNPOINTS_SEND");

		return soapBodyElement;
	}



	public SOAPBodyElement createLoyaltyHistorySoapBody(final SOAPMessage message, final String urn, final String nameSpace,
			final String serviceName) throws UnsupportedOperationException, SOAPException
	{
		final SOAPPart soapPart = message.getSOAPPart();
		final SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		final Name header = soapEnvelope.createName(serviceName, urn, nameSpace);
		soapEnvelope.addNamespaceDeclaration(urn, nameSpace);
		final SOAPBody soapBody = soapEnvelope.getBody();

		//final SOAPBodyElement soapBodyElement = soapBody.addBodyElement(header);
		//	final SOAPBodyElement soapBodyElement = soapBody.addBodyElement(header);
		final SOAPBodyElement soapBodyElement = (SOAPBodyElement) soapBody.addChildElement(
				"MT_Hybris_LoyaltyPointsHistory_Request", "loy");

		return soapBodyElement;
	}

	public SOAPBodyElement createLoyaltyOrderConfirmSoapBody(final SOAPMessage message, final String urn, final String nameSpace,
			final String serviceName) throws UnsupportedOperationException, SOAPException
	{
		final SOAPPart soapPart = message.getSOAPPart();
		final SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		final Name header = soapEnvelope.createName(serviceName, urn, nameSpace);
		soapEnvelope.addNamespaceDeclaration(urn, nameSpace);
		final SOAPBody soapBody = soapEnvelope.getBody();

		//final SOAPBodyElement soapBodyElement = soapBody.addBodyElement(header);
		//	final SOAPBodyElement soapBodyElement = soapBody.addBodyElement(header);
		final SOAPBodyElement soapBodyElement = (SOAPBodyElement) soapBody.addChildElement("MT_Hybris_Loyalty_OrderRedemption",
				"loy");

		return soapBodyElement;
	}


public SOAPBodyElement createPromoCodeSoapBody(final SOAPMessage message, final String urn, final String nameSpace,
			final String serviceName) throws UnsupportedOperationException, SOAPException
	{
		final SOAPPart soapPart = message.getSOAPPart();
		final SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		final Name header = soapEnvelope.createName(serviceName, urn, nameSpace);
		soapEnvelope.addNamespaceDeclaration(urn, nameSpace);
		final SOAPBody soapBody = soapEnvelope.getBody();

		//final SOAPBodyElement soapBodyElement = soapBody.addBodyElement(header);
		//final SOAPBodyElement soapBodyElement = soapBody.addBodyElement(header);
		final SOAPBodyElement soapBodyElement = (SOAPBodyElement) soapBody.addChildElement("MT_HYBRIS_PROMOCODE_REQUEST", "loy");

		return soapBodyElement;
	}


	public SOAPBodyElement createLoyaltySurveySoapBody(final SOAPMessage message, final String urn, final String nameSpace,
			final String serviceName) throws UnsupportedOperationException, SOAPException
	{
		final SOAPPart soapPart = message.getSOAPPart();
		final SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		final Name header = soapEnvelope.createName(serviceName, urn, nameSpace);
		soapEnvelope.addNamespaceDeclaration(urn, nameSpace);
		final SOAPBody soapBody = soapEnvelope.getBody();

		//final SOAPBodyElement soapBodyElement = soapBody.addBodyElement(header);
		//	final SOAPBodyElement soapBodyElement = soapBody.addBodyElement(header);
		final SOAPBodyElement soapBodyElement = (SOAPBodyElement) soapBody.addChildElement("MT_HYBRIS_SURVEY_REQUEST", "sur");

		return soapBodyElement;
	}
	public SOAPBodyElement createSoapBodyLicensePlate(final SOAPMessage message, final String urn, final String nameSpace,
			final String serviceName) throws UnsupportedOperationException, SOAPException
	{
		final SOAPPart soapPart = message.getSOAPPart();

		final SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		final SOAPHeader header1 = message.getSOAPHeader();

		final SOAPElement security = header1.addChildElement("Security", "wsse",
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");

		final SOAPElement usernameToken = security.addChildElement("UsernameToken", "wsse");
		usernameToken.addAttribute(new QName("xmlns:wsu"),
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");

		final SOAPElement username = usernameToken.addChildElement("Username", "wsse");
		username.addTextNode("FEDERALMOGUL");

		final SOAPElement password = usernameToken.addChildElement("Password", "wsse");
		password.setAttribute("Type",
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
		password.addTextNode("mogulf4pv");

		LOG.info("header1" + header1);


		//final String authorization = new sun.misc.BASE64Encoder().encode(("FEDERALMOGUL:mogulf4pv").getBytes());
		final String authorization = new BASE64Encoder().encode(("FEDERALMOGUL:mogulf4pv").getBytes());
		final MimeHeaders hd = message.getMimeHeaders();
		hd.addHeader("Authorization", "Basic " + authorization);

		SOAPHeader header = message.getSOAPHeader();
		if (header == null)
		{
			header = soapEnvelope.addHeader();
		}

		final SOAPHeaderElement he = header.addHeaderElement(soapEnvelope.createName(serviceName, urn, nameSpace));

		LOG.info(":: ==>" + he.getActor());

		final Name nheader = soapEnvelope.createName(serviceName, urn, nameSpace);

		soapEnvelope.addNamespaceDeclaration(urn, nameSpace);
		final SOAPBody soapBody = soapEnvelope.getBody();
		//final SOAPBodyElement soapBodyElement = soapBody.addBodyElement(header);

		final SOAPBodyElement soapBodyElement = soapBody.addBodyElement(nheader);
		return soapBodyElement;
	}

	/**
	 * Method sends SOAP request and receives the response using the passed request and end point URL parameters.
	 * Will encode the username and password, and add an HTTP Authorization Header
	 *
	 * @param soapMessage
	 * @param endpointURL
	 * @param userId
	 * @param password
	 * @return SOAPBody
	 * @throws UnsupportedOperationException
	 * @throws SOAPException
	 * @throws IOException
	 */
	public SOAPBody sendSoapRequest(final SOAPMessage soapMessage, final String endpointURL, String userId, String password) throws UnsupportedOperationException,
			SOAPException, IOException
	{
		final String authorization = new BASE64Encoder().encode((userId + ":" + password).getBytes());

		soapMessage.getMimeHeaders().setHeader("Authorization", "Basic " + authorization);

		return sendSoapRequest(soapMessage, endpointURL);
	}


	/**
	 * Method sends SOAP request and receives the response using the passed request and end point URL parameters.
	 * 
	 * @param soapMessage
	 * @param endpointURL
	 * @return SOAPBody
	 * @throws UnsupportedOperationException
	 * @throws SOAPException
	 * @throws IOException
	 */
	public SOAPBody sendSoapRequest(final SOAPMessage soapMessage, final String endpointURL) throws UnsupportedOperationException,
			SOAPException, IOException
	{
		final SOAPConnectionFactory conFact = SOAPConnectionFactory.newInstance();
		final SOAPConnection con = conFact.createConnection();

		LOG.info("\n Request Message \n");
		soapMessage.writeTo(System.out);

		final SOAPMessage response = con.call(soapMessage, new URL(endpointURL));

		LOG.info("\n Response Message \n");
		response.writeTo(System.out);

		return response.getSOAPBody();
	}

	/**
	 * Method for authentication with the CRM credentials
	 * 
	 */

	public void authenticategCRMRequest() throws UnsupportedEncodingException
	{

		//LOG.info("authenticating.. " + Config.getParameter("userRegistrationServiceUID") + " "	+ Config.getParameter("userRegistrationServicePWD"));
		Authenticator.setDefault(new Authenticator()
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(Config.getParameter("userRegistrationServiceUID"), Config.getParameter(
						"userRegistrationServicePWD").toCharArray());

			}
		});

	}


	/**
	 * Method for authentication with the Return CRM credentials
	 * 
	 */

	public void authenticategReturnRequest() throws UnsupportedEncodingException
	{

		//LOG.info("authenticating.. " + Config.getParameter("returnsServiceUID") + " " + Config.getParameter("returnsServicePWD"));
		Authenticator.setDefault(new Authenticator()
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(Config.getParameter("returnsServiceUID"), Config.getParameter("returnsServicePWD")
						.toCharArray());

			}
		});

	}

	/**
	 * Method for authentication with the Return CallBack CRM credentials
	 * 
	 */

	public void authenticategCallBackRequest() throws UnsupportedEncodingException
	{

		Authenticator.setDefault(new Authenticator()
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(Config.getParameter("leadGenerationUID"), Config.getParameter(
						"leadGenerationServicePWD").toCharArray());

			}
		});

	}


	/**
	 * Method for authentication with the VIN Look up credentials
	 * 
	 */

	public void authenticateVinLookupRequest() throws UnsupportedEncodingException
	{

		//LOG.info("authenticating.. " + Config.getParameter("vinLookupServiceUID") + " "+ Config.getParameter("vinLookupServicePWD"));
		Authenticator.setDefault(new Authenticator()
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(Config.getParameter("vinLookupServiceUID"), Config.getParameter(
						"vinLookupServicePWD").toCharArray());

			}
		});

	}

	/**
	 * Method for authentication with the VIN Look up credentials
	 * 
	 */

	public void authenticateLicensePlateRequest() throws UnsupportedEncodingException
	{

		//LOG.info("authenticating.. " + Config.getParameter("licensePlateServiceUID") + " "+ Config.getParameter("licensePlateServicePWD"));
		Authenticator.setDefault(new Authenticator()
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(Config.getParameter("licensePlateServiceUID"), Config.getParameter(
						"licensePlateServicePWD").toCharArray());

			}
		});

	}

	/**
	 * Method for authentication with the Loyalty History request credentials
	 */
	public void authenticateLoyaltyHistoryRequest() throws UnsupportedEncodingException
	{
		//LOG.info("authenticating.. " + Config.getParameter("licensePlateServiceUID") + " "+ Config.getParameter("licensePlateServicePWD"));
		Authenticator.setDefault(new Authenticator()
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(Config.getParameter("loyaltyHistoryUID"), Config.getParameter("loyaltyHistoryPWD")
						.toCharArray());

			}
		});

	}


	/**
	 * Method for creating SOAP part in existing SOAP message with the request message name space
	 * 
	 * @param message
	 * 
	 * @return soapBodyElement
	 * @throws UnsupportedOperationException
	 * @throws SOAPException
	 * 
	 */
	public SOAPBodyElement createSoapBodyForReturnCallBack(final SOAPMessage message, final String serviceName)
			throws UnsupportedOperationException, SOAPException
	{
		final SOAPPart soapPart = message.getSOAPPart();
		LOG.info("\n soapPart \n" + soapPart);
		final SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		LOG.info("\n soapEnvelope \n" + soapEnvelope);
		final Name header = soapEnvelope.createName(serviceName);
		LOG.info("\n header \n" + header);
		final SOAPBody soapBody = soapEnvelope.getBody();
		LOG.info("\n soapBody \n" + soapBody);
		final SOAPBodyElement soapBodyElement = soapBody.addBodyElement(header);
		LOG.info("\n soapBodyElement \n" + soapBodyElement);
		return soapBodyElement;
	}

	/**
	 * Method for creating SOAP part in existing SOAP message with the request message name space
	 * 
	 * @param message
	 * 
	 * @return soapBodyElement
	 * @throws UnsupportedOperationException
	 * @throws SOAPException
	 * 
	 */
	public SOAPBodyElement createSoapBodyForVinLookup(final SOAPMessage message, final String serviceName)
			throws UnsupportedOperationException, SOAPException
	{
		final SOAPPart soapPart = message.getSOAPPart();
		LOG.info("\n soapPart \n" + soapPart);
		final SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
		LOG.info("\n soapEnvelope \n" + soapEnvelope);
		final Name header = soapEnvelope.createName(serviceName);
		LOG.info("\n header \n" + header);
		final SOAPBody soapBody = soapEnvelope.getBody();
		LOG.info("\n soapBody \n" + soapBody);
		final SOAPBodyElement soapBodyElement = soapBody.addBodyElement(header);
		LOG.info("\n soapBodyElement \n" + soapBodyElement);
		return soapBodyElement;
	}

	/**
	 * Method for creating QNAME
	 * 
	 * @param fieldName
	 *           .
	 * @return qName
	 */

	public QName createQname(final String fieldName)
	{
		//final QName qName = new QName(fieldName);
		//return qName;
		return new QName(fieldName);
	}

	/**
	 * Method to retrieve the node value.
	 * 
	 * @param item
	 *           xml node element
	 * @return String node value
	 */
	public String getNodeValue(final Node item)
	{

		return item != null ? item.getTextContent() : null;
	}

}
