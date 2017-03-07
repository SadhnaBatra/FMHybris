package com.fmo.wom.webservice.handler;

import de.hybris.platform.core.Registry;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.business.as.TradingPartnerAS;
import com.fmo.wom.common.dao.AuditInterceptor;
import com.fmo.wom.common.exception.WOMIPOTransformationException;
import com.fmo.wom.common.exception.WOMIPOValidationException;
import com.fmo.wom.common.exception.WOMProcessException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.domain.UserAccountBO;
import com.fmo.wom.domain.UserBO;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.helper.XmlListAdapter;
import com.fmo.wom.model.FMIPOTRACKINGModel;
import com.fmo.wom.transformation.util.TransformationConstants;
import com.fmo.wom.transformation.util.XSLTransformer;



/**
 * Delegates the requests to other layers in sequential manner
 * @author chanac32
 *
 */
public abstract class FMOBaseServiceHandler implements IBusinessProcessExecutor{
	//@Autowired 
	//private TradingPartnerAS tradingPartnerAs = null;
	private static Logger logger = Logger.getLogger(FMOBaseServiceHandler.class);
	private static String IPO_USER_ID = "IPOUSER";	
	
	public TradingPartnerAS getTradingPartnerAs() {
		TradingPartnerAS tradingPartnerAs = new TradingPartnerAS();
		return tradingPartnerAs;
	}
	/*public void setTradingPartnerAs(TradingPartnerAS tradingPartnerAs) {
		this.tradingPartnerAs = tradingPartnerAs;
	}*/
	public IpoVersion getVersion(String xmlBod) throws WOMValidationException, WOMResourceException{
		IpoVersion bodData = new IpoVersion();
		try {
			bodData.setIpoVersion(getIpoVersion(xmlBod));
		} catch (WOMResourceException e1) {
			// Defaulting version to 1.2
			bodData.setIpoVersion(TransformationConstants.VERSION1_2);
		}
		String secretKey = getSecretKey(xmlBod);
		String billToAccountCode = getBillToAccountCode(xmlBod);
		bodData.setClientName(getTradingPartnerAs().getTradingPartnerAccount(secretKey,billToAccountCode).getTradingPartner().getGroupCode());
		return bodData;
	}
	public static String getIpoVersion(String xmlBod) throws WOMIPOTransformationException, WOMResourceException{
		return XSLTransformer.transform(TransformationConstants.IPOVERSION_XSLFILE, xmlBod,null,null);
	}
	public String getSecretKey(String xmlBod) throws WOMIPOTransformationException, WOMResourceException{
		return XSLTransformer.transform(TransformationConstants.SECRET_KEY_XSLFILE, xmlBod,null,null);
	}
	public static String getBillToAccountCode(String xmlBod) throws WOMIPOTransformationException, WOMResourceException{
		return XSLTransformer.transform(TransformationConstants.BILL_TO_ACCOUNT_CODE_XSLFILE, xmlBod,null,null);
	}
	
	public String getUser() {
	    return IPO_USER_ID;
	}
	
	public static class IpoVersion{
		private String ipoVersion;
		private String clientName;
		public String getIpoVersion() {
			return ipoVersion;
		}
		public void setIpoVersion(String ipoVersion) {
			this.ipoVersion = ipoVersion;
		}
		public String getClientName() {
			return clientName;
		}
		public void setClientName(String clientName) {
			this.clientName = clientName;
		}
	}

	public boolean isValidXmlDocument(String version
			,String xmlDocument,String schemaFile) throws WOMIPOValidationException{
		boolean isValidationrequired = false;
		if(TransformationConstants.IpoVersion.VERSION2_0.getValue().equals(version)){
//			isValidationrequired = true;
		}
		return isValidXmlDocument(isValidationrequired,xmlDocument,schemaFile);		
	}

	public boolean isValidXmlDocument(String xmlDocument,String schemaFile) throws WOMIPOValidationException{
		return isValidXmlDocument(true,xmlDocument,schemaFile);
	}
	
	private boolean isValidXmlDocument(boolean isValidationrequired
			, String xmlDocument,String schemaFile) throws WOMIPOValidationException{
		if(!isValidationrequired){
			return true;
		}
		return (StringUtil.getDocument(xmlDocument, isValidationrequired
				, XSLTransformer.classLoader,schemaFile))!=null;
	}
	
	public String execute(String requestedXML) 
		throws WOMResourceException, WOMValidationException, WOMProcessException {
		logger.debug("execute process stated" + (new Date().getTime()));
		long startTime = new Date().getTime();
		IpoVersion version = getVersion(requestedXML);
		logger.debug("Time taken to complete getVersion " + (new Date().getTime() - startTime));
		startTime = new Date().getTime();
		
		AuditInterceptor.setUserAccount(getIpoUserAccountBO());

		logger.debug("Time taken to complete AuditInterceptor " + (new Date().getTime() - startTime));
		startTime = new Date().getTime();
		
		if(!isValidXmlDocument(version.getIpoVersion(),requestedXML
				,getRequestTransformationService().getSchema(version.getIpoVersion(),version.getClientName()))){
			logger.error("Incoming BOD not well formed as per schema. Error: "+requestedXML);
			throw new WOMIPOValidationException("Incoming BOD not well formed as per schema");
		}
	
		logger.debug("Time taken to complete Validation " + (new Date().getTime() - startTime));
		startTime = new Date().getTime();
		
		logger.debug("execute(...): requestedXML: [>>--:  " + requestedXML + "  :--<<]");
		Object transformationData = getRequestTransformationService().transformRequest(
					  requestedXML,version.getIpoVersion(),version.getClientName());
		logger.debug("After request transformation and before AS calling: quoteOrder:" + transformationData);
		logger.debug("Time taken to complete Inbound transformation " + (new Date().getTime() - startTime));
		startTime = new Date().getTime();
        
		Object processedData = executeProcess(transformationData);

		logger.debug("After AS calling: quoteOrder:" + processedData);
		logger.debug("Time taken to complete process RFQ Request by AS" + (new Date().getTime() - startTime));
		startTime = new Date().getTime();
		String response = getResponseTransformationService().
			  transformResponse(requestedXML,processedData,version.getIpoVersion(),version.getClientName());

		logger.debug("Time taken to complete Outbound transformation " + (new Date().getTime() - startTime));
		startTime = new Date().getTime();
		
		logger.debug("execute(...): response: [>>--: \n" + response + "  :--<<]");
		if(!isValidXmlDocument(version.getIpoVersion(),response
				,getResponseTransformationService().getSchema(version.getIpoVersion(),version.getClientName()))){
			logger.error("Incoming BOD not well formed as per schema. Error: "+requestedXML);
			throw new WOMIPOValidationException("Response BOD not well formed as per schema");
		}

		logger.debug("Time taken to complete Outbound Validation " + (new Date().getTime() - startTime));
		startTime = new Date().getTime();
		logger.debug("After Response transformation: addquote :" + response);
		try
		{
			createIPOTracking(processedData, requestedXML, response, version);
		}
		catch (final Exception e)
		{
			logger.error("Error Saving respone into Hybris");
		}
		return response;
	}

	public static UserAccountBO getIpoUserAccountBO(){
		UserAccountBO ipoUserAccount = new UserAccountBO();
		ipoUserAccount.setLdapUser(getIpoUser());
		ipoUserAccount.setUserID(IPO_USER_ID);
		ipoUserAccount.setMarketCode(Market.US_CANADA.getCode());
		ipoUserAccount.setLoggedInAsBillto(true);
		ipoUserAccount.setAccountCode(IPO_USER_ID);
		return ipoUserAccount;
	}
	
	public static UserBO getIpoUser(){
		UserBO ipouser = new UserBO();
		ipouser.setUserID(IPO_USER_ID);
		return ipouser;
	}
	
	private FlexibleSearchService getFlexibleSearchService()
	{
		final FlexibleSearchService flexibleSearchService = (FlexibleSearchService) Registry.getApplicationContext().getBean(
				"flexibleSearchService");
		return flexibleSearchService;
	}

	private ModelService getmodelService()
	{
		final ModelService modelService = (ModelService) Registry.getApplicationContext().getBean("modelService");
		return modelService;
	}

	private void createIPOTracking(final Object processedData, final String req, final String resp, final IpoVersion version)
	{
		final FMIPOTRACKINGModel track = getmodelService().create(FMIPOTRACKINGModel.class);
		String mstrOrderNumber = null;
		if (processedData instanceof OrderBO)
		{
			final OrderBO order = (OrderBO) processedData;
			mstrOrderNumber = order.getMstrOrdNbr();
		}
		else if (processedData instanceof XmlListAdapter)
		{
			final List<ShippedOrderBO> showShipments = ((XmlListAdapter) processedData).getValue();
			mstrOrderNumber = showShipments.get(0).getMasterOrderNumber();
		}

		final Calendar cal = Calendar.getInstance();

		final String code = mstrOrderNumber + "_" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH)
				+ cal.get(Calendar.DAY_OF_MONTH) + cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE)
				+ cal.get(Calendar.SECOND) + cal.getTimeInMillis();
		track.setCode(code);
		track.setMasterOrderNumber(mstrOrderNumber);
		track.setIPORequest(req);
		track.setIPOResponse(resp);
		track.setIpoVersion(version.getIpoVersion());
		track.setCustomer(version.getClientName());
		track.setCreateDate(new Date());
		getmodelService().save(track);
	}
}
