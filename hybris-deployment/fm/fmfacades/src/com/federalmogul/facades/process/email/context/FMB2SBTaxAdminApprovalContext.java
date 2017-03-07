/**
 * 
 */
package com.federalmogul.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.util.Config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.federalmogul.core.model.FMB2SBTaxAdminApprovalProcessModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.network.FMNetworkService;


/**
 * @author su244261
 * 
 */
public class FMB2SBTaxAdminApprovalContext extends AbstractEmailContext<FMB2SBTaxAdminApprovalProcessModel>
{

	private Converter<UserModel, CustomerData> customerConverter;
	private CustomerData customerData;
	@Resource
	private FMNetworkService fmNetworkService;
	private static final Logger LOG = Logger.getLogger(FMB2SBTaxAdminApprovalContext.class);
	String customerName = "CUSTOMER_NAME";
	String accountNumber = "ACCOUNT_NUMBER";
	String companyName = "COMPANY_NAME";
	String emailAddress = "EMAIL_ADDRESS";
	String dateAndTime = "DATE_AND_TIME";
	String fmHomePage = "FMHOME_PAGE";


	/**
	 * @return the fmHomePage
	 */
	public String getFmHomePage()
	{
		return fmHomePage;
	}

	/**
	 * @param fmHomePage
	 *           the fmHomePage to set
	 */
	public void setFmHomePage(final String fmHomePage)
	{
		this.fmHomePage = fmHomePage;
	}

	/**
	 * @return the dateAndTime
	 */
	public String getDateAndTime()
	{
		return dateAndTime;
	}

	/**
	 * @param dateAndTime
	 *           the dateAndTime to set
	 */
	public void setDateAndTime(final String dateAndTime)
	{
		this.dateAndTime = dateAndTime;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName()
	{
		return customerName;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber()
	{
		return accountNumber;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName()
	{
		return companyName;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress()
	{
		return emailAddress;
	}

	/**
	 * @param customerName
	 *           the customerName to set
	 */
	public void setCustomerName(final String customerName)
	{
		this.customerName = customerName;
	}

	/**
	 * @param accountNumber
	 *           the accountNumber to set
	 */
	public void setAccountNumber(final String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	/**
	 * @param companyName
	 *           the companyName to set
	 */
	public void setCompanyName(final String companyName)
	{
		this.companyName = companyName;
	}

	/**
	 * @param emailAddress
	 *           the emailAddress to set
	 */
	public void setEmailAddress(final String emailAddress)
	{
		this.emailAddress = emailAddress;
	}





	@Override
	public void init(final FMB2SBTaxAdminApprovalProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		super.init(businessProcessModel, emailPageModel);


		final CustomerModel customerModel = getCustomer(businessProcessModel);
		LOG.info("-----aaaaaaaaaa--------customerModel::" + customerModel);
		final String customerEmail = getCustomerEmailResolutionService().getEmailForCustomer(customerModel);
		LOG.info("-----bbbbbbbbbbbbbb--------customerEmail::" + customerEmail);



		final Date tempDate = Calendar.getInstance().getTime();
		dateAndTime = tempDate.toString();


		/*try
		{
			//final InetAddress localHost = InetAddress.getLocalHost();
			//final String ipAddress = localHost.getHostAddress();

			fmHomePage = Config.getParameter("email.host.httpaddress");
		}
		catch (final UnknownHostException e)
		{
			LOG.error(e.getMessage());
		}*/
		fmHomePage = Config.getParameter("email.host.httpaddress");
		final FMCustomerModel fmCustomerModel = fmNetworkService.getFMCustomerForUid(businessProcessModel.getEmailId());


		put(EMAIL, businessProcessModel.getEmailId());
		if (fmCustomerModel.getName() != null)
		{
			put(DISPLAY_NAME, fmCustomerModel.getName());
		}
		if (fmCustomerModel.getTitle() != null)
		{
			put(TITLE, fmCustomerModel.getTitle());
		}
		customerData = getCustomerConverter().convert(getCustomer(businessProcessModel));

	}

	protected Converter<UserModel, CustomerData> getCustomerConverter()
	{
		return customerConverter;
	}

	@Required
	public void setCustomerConverter(final Converter<UserModel, CustomerData> customerConverter)
	{
		this.customerConverter = customerConverter;
	}

	public CustomerData getCustomer()
	{
		return customerData;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getSite(de.hybris.platform.
	 * processengine.model.BusinessProcessModel)
	 */
	@Override
	protected BaseSiteModel getSite(final FMB2SBTaxAdminApprovalProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getSite();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getCustomer(de.hybris.platform
	 * .processengine.model.BusinessProcessModel)
	 */
	@Override
	protected CustomerModel getCustomer(final FMB2SBTaxAdminApprovalProcessModel businessProcessModel)
	{
		return businessProcessModel.getCustomer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getEmailLanguage(de.hybris.platform
	 * .processengine.model.BusinessProcessModel)
	 */
	@Override
	protected LanguageModel getEmailLanguage(final FMB2SBTaxAdminApprovalProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}

	protected <T extends FMNetworkService> T getFMNetworkService()
	{
		return (T) fmNetworkService;
	}


}
