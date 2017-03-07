/**
 * 
 */
package com.federalmogul.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.model.B2BUserGroupModel;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.security.PrincipalModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.federalmogul.core.model.FMCustomerAdminProcessModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.network.FMNetworkService;
import com.federalmogul.core.network.dao.FMUserSearchDAO;


/**
 * @author su244261
 * 
 */
public class FMCustomerAdminContext extends AbstractEmailContext<FMCustomerAdminProcessModel>
{

	private Converter<UserModel, CustomerData> customerConverter;
	private CustomerData customerData;
	@Resource
	private FMNetworkService fmNetworkService;
	private static final Logger LOG = Logger.getLogger(FMCustomerAdminContext.class);
	String customerName = "CUSTOMER_NAME";
	String accountNumber = "ACCOUNT_NUMBER";
	String companyName = "COMPANY_NAME";
	String emailAddress = "EMAIL_ADDRESS";
	String dateAndTime = "DATE_AND_TIME";
	String approveLink = "APPROVE_LINK";
	AddressModel companyAddress = null;


	@Autowired
	FMUserSearchDAO fmUserSearchDAO;

	/**
	 * @return the approveLink
	 */
	public String getApproveLink()
	{
		return approveLink;
	}

	/**
	 * @param approveLink
	 *           the approveLink to set
	 */
	public void setApproveLink(final String approveLink)
	{
		this.approveLink = approveLink;
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
	public void init(final FMCustomerAdminProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		super.init(businessProcessModel, emailPageModel);


		final CustomerModel customerModel = getCustomer(businessProcessModel);
		final String customerEmail = getCustomerEmailResolutionService().getEmailForCustomer(customerModel);

		final FMCustomerModel fmCustomerModel = getFMNetworkService().getCustomerForUid(customerEmail);
		final B2BUnitModel b2bUnitModel = fmCustomerModel.getDefaultB2BUnit();
		LOG.info("******************b2bUnitModel.getUid()::" + b2bUnitModel.getUid() + "**************b2bUnitModel.getName()::"
				+ b2bUnitModel.getName() + "*************Name::" + fmCustomerModel.getName() + "**********Email"
				+ fmCustomerModel.getEmail());

		customerName = fmCustomerModel.getName();
		accountNumber = b2bUnitModel.getUid();
		companyName = b2bUnitModel.getLocName();
		if (b2bUnitModel.getAddresses().size() > 0)
		{
			companyAddress = b2bUnitModel.getAddresses().iterator().next();
		}
		emailAddress = fmCustomerModel.getEmail();

		final Date tempDate = Calendar.getInstance().getTime();
		dateAndTime = tempDate.toString();



		approveLink = Config.getParameter("email.host.httpaddress");


		/*final List<String> uidList = new ArrayList<String>();

		final B2BUserGroupModel userGroupModel = fmUserSearchDAO.getGroupByID("FMB2BB");

		LOG.info("*************groupUID::" + userGroupModel.getUid());
		final Set<PrincipalModel> memberList = userGroupModel.getMembers();
		for (final PrincipalModel memeber : memberList)
		{
			final Set<PrincipalGroupModel> memeberGroups = memeber.getGroups();

			for (final PrincipalGroupModel memberGroup : memeberGroups)
			{

				if ("FMAdminGroup".equals(memberGroup.getUid()))
				{
					uidList.add(memeber.getUid());
					break;
				}
			}

		}

		String mailList = "";



		for (final String uid : uidList)
		{
			mailList = mailList.concat(uid);
		}*/



		customerData = getCustomerConverter().convert(getCustomer(businessProcessModel));
		put(EMAIL, Config.getParameter("fmAdminEmail"));
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
	protected BaseSiteModel getSite(final FMCustomerAdminProcessModel businessProcessModel)
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
	protected CustomerModel getCustomer(final FMCustomerAdminProcessModel businessProcessModel)
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
	protected LanguageModel getEmailLanguage(final FMCustomerAdminProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}

	protected <T extends FMNetworkService> T getFMNetworkService()
	{
		return (T) fmNetworkService;
	}

	/**
	 * @return the companyAddress
	 */
	public AddressModel getCompanyAddress()
	{
		return companyAddress;
	}

	/**
	 * @param companyAddress
	 *           the companyAddress to set
	 */
	public void setCompanyAddress(final AddressModel companyAddress)
	{
		this.companyAddress = companyAddress;
	}

}
