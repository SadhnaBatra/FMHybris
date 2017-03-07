/**
 * 
 */
package com.federalmogul.core.account.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bacceleratorservices.customer.impl.DefaultB2BCustomerAccountService;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.customer.PasswordMismatchException;
import de.hybris.platform.commerceservices.customer.TokenInvalidatedException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.security.SecureToken;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import de.hybris.platform.util.Config;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.federalmogul.core.account.FMCustomerAccountService;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.network.dao.FMUserSearchDAO;



/**
 * @author SR279690
 * 
 */
public class FMCustomerAccountServiceImpl extends DefaultB2BCustomerAccountService implements FMCustomerAccountService
{

	@Resource
	protected SessionService sessionService;

	@Resource
	FMUserSearchDAO fmUserSearchDAO;


	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2BUnitService;

	/**
	 * @return the b2BUnitService
	 */
	public B2BUnitService<B2BUnitModel, B2BCustomerModel> getB2BUnitService()
	{
		return b2BUnitService;
	}

	/**
	 * @param b2bUnitService
	 *           the b2BUnitService to set
	 */
	public void setB2BUnitService(final B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService)
	{
		b2BUnitService = b2bUnitService;
	}

	/**
	 * @return the sessionService
	 */
	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @param sessionService
	 *           the sessionService to set
	 */

	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	private static final Logger LOG = Logger.getLogger(FMCustomerAccountServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.account.FMCustomerAccountService#updateFMProfile()
	 */

	@Override
	public void updateFMProfile(final FMCustomerModel fmcustomerModel, final String titleCode, final String name,
			final String login) throws DuplicateUidException
	{
		ServicesUtil.validateParameterNotNullStandardMessage("customerModel", fmcustomerModel);

		fmcustomerModel.setUid(login);
		fmcustomerModel.setName(name);
		LOG.info("NAME IN service" + name);
		if (StringUtils.isNotBlank(titleCode))
		{
			fmcustomerModel.setTitle(getUserService().getTitleForCode(titleCode));
		}
		else
		{
			fmcustomerModel.setTitle(null);
		}
		internalSaveCustomer(fmcustomerModel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.account.FMCustomerAccountService#getFMAddressBookEntries(com.federalmogul.core.model.
	 * FMCustomerModel)
	 */
	@Override
	public List<AddressModel> getFMAddressBookEntries(final FMCustomerModel currentCustomer)
	{
		LOG.info("In my service - getaddressbookentries method");
		ServicesUtil.validateParameterNotNull(currentCustomer, "Customer model cannot be null");
		final List<AddressModel> addressModels = new ArrayList<AddressModel>();

		for (final AddressModel address : currentCustomer.getAddresses())
		{
			if (!(Boolean.TRUE.equals(address.getVisibleInAddressBook())))
			{
				continue;
			}
			addressModels.add(address);

		}

		return addressModels;
	}

	@Override
	public SearchPageData<B2BUnitModel> getAdminAddressBookEntries(final PageableData pageableData)
	{
		return fmUserSearchDAO.getAllFMAccounts(pageableData);
	}

	@Override
	public B2BUnitModel getUnitForUid(final String unitUid)
	{
		return getB2BUnitService().getUnitForUid(unitUid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.federalmogul.core.account.FMCustomerAccountService#getFMDefaultAddress(com.federalmogul.core.model.FMCustomerModel
	 * )
	 */
	@Override
	public AddressModel getFMDefaultAddress(final FMCustomerModel currentCustomer)
	{
		LOG.info("In my service" + getUserService().getCurrentUser().getDefaultShipmentAddress());
		return getUserService().getCurrentUser().getDefaultShipmentAddress();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.federalmogul.core.account.FMCustomerAccountService#changeFMPassword(com.federalmogul.core.model.FMCustomerModel
	 * , java.lang.String, java.lang.String)
	 */
	@Override
	public void changeFMPassword(final FMCustomerModel fmCustomerModel, final String oldPassword, final String newPassword)
			throws PasswordMismatchException
	{
		ServicesUtil.validateParameterNotNullStandardMessage("fmCustomerModel", fmCustomerModel);
		if (getUserService().isAnonymousUser(fmCustomerModel))
		{
			return;
		}
		final String encodedCurrentPassword = getPasswordEncoderService().encode(fmCustomerModel, oldPassword,
				fmCustomerModel.getPasswordEncoding());
		final String defaultencodedpwd = getPasswordEncoderService().encode(fmCustomerModel, oldPassword, getPasswordEncoding());
		if (encodedCurrentPassword.equals(fmCustomerModel.getEncodedPassword()) || encodedCurrentPassword.equals(defaultencodedpwd))
		{
			getUserService().setPassword(fmCustomerModel, newPassword, fmCustomerModel.getPasswordEncoding());
			/* For FAL-219 - BEGIN: */
			fmCustomerModel.setPwdMeetsLatestStandards(true);
			fmCustomerModel.setLogindate(new Date());
			fmCustomerModel.setLoginDisabled(false);
			/* For FAL-219 - END */
			getModelService().save(fmCustomerModel);
		}
		else
		{
			throw new PasswordMismatchException(fmCustomerModel.getUid());
		}

	}

	@Override
	public void fmUpdatePassword(final String token, final String newPassword) throws TokenInvalidatedException
	{
		Assert.hasText(token, "The field [token] cannot be empty");
		Assert.hasText(newPassword, "The field [newPassword] cannot be empty");

		final SecureToken data = getSecureTokenService().decryptData(token);
		if (getTokenValiditySeconds() > 0L)
		{
			final long delta = new Date().getTime() - data.getTimeStamp();
			if (delta / 1000L > getTokenValiditySeconds())
			{
				throw new IllegalArgumentException("token expired");
			}
		}

		final CustomerModel customer = getUserService().getUserForUID(data.getData(), CustomerModel.class);
		if (customer == null)
		{
			throw new IllegalArgumentException("user for token not found");
		}
		if (!(token.equals(customer.getToken())))
		{
			throw new TokenInvalidatedException();
		}
		customer.setToken(null);
		customer.setLoginDisabled(false);
		//forgot password code
		updateLDAPPassword(customer, newPassword);

		getModelService().save(customer);

		getUserService().setPassword(data.getData(), newPassword, getPasswordEncoding());
		getUserService().getCurrentUser().setPwdMeetsLatestStandards(true); // Post Sprint 9 - FAL-219 (since it was missed in that sprint!!)
	}

	public static void updateLDAPPassword(final CustomerModel currentUser, final String newPassword)
	{

		updateFMPassword(currentUser.getLdaplogin(), newPassword);
	}

	private static void updateFMPassword(final String customerCN, final String newPassword)
	{
		final String quotedPassword = "\"" + newPassword + "\"";
		final char[] unicodePwd = quotedPassword.toCharArray();
		final byte[] pwdArray = new byte[unicodePwd.length * 2];

		for (int i = 0; i < unicodePwd.length; i++)
		{
			pwdArray[i * 2 + 1] = (byte) (unicodePwd[i] >>> 8);
			pwdArray[i * 2 + 0] = (byte) (unicodePwd[i] & 0xff);
		}
		try
		{
			final ModificationItem[] mods = new ModificationItem[1];
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("UnicodePwd", pwdArray));
			getLDAPDirContext().modifyAttributes(customerCN, mods);
		}
		catch (final Exception e)
		{
			LOG.info("update password error: " + e);
		}

	}

	private static DirContext getLDAPDirContext() throws NamingException
	{
		
		final String userDn = Config.getParameter("ldap.jndi.principals");//"cn=hybris-svc,o=fmoweb";
		final String ldapPassword = Config.getParameter("ldap.jndi.credentials");//"Passw0rd1";
		final String ldapAdServer = "ldap://" + Config.getParameter("ldap.server.url");//"ldap://sfldmims107:389";

		final Hashtable<String, Object> env = new Hashtable<String, Object>();
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, userDn);
		env.put(Context.SECURITY_CREDENTIALS, ldapPassword);

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapAdServer);

		// ensures that objectSID attribute values
		// will be returned as a byte[] instead of a String
		env.put("java.naming.ldap.attributes.binary", "objectSID");

		return new InitialDirContext(env);
	}

	@Override
	public AddressModel getAddressByID(final String addressID)
	{
		return fmUserSearchDAO.getAddressByID(addressID);
	}


	/*
	 * @Override public void saveAdminAddressEntry(final B2BUnitModel b2bUnitModel, final AddressModel addressModel) {
	 * ServicesUtil.validateParameterNotNull(b2bUnitModel, "Unit model cannot be null");
	 * ServicesUtil.validateParameterNotNull(addressModel, "Address model cannot be null"); final List unitAddresses =
	 * new ArrayList(); unitAddresses.addAll(b2bUnitModel.getAddresses()); if
	 * (b2bUnitModel.getAddresses().contains(addressModel)) { getModelService().save(addressModel); } else {
	 * addressModel.setOwner(b2bUnitModel); getModelService().save(addressModel);
	 * getModelService().refresh(addressModel); unitAddresses.add(addressModel); }
	 * b2bUnitModel.setAddresses(unitAddresses);
	 * 
	 * getModelService().save(b2bUnitModel); getModelService().refresh(b2bUnitModel); }
	 */


	@Override
	public void saveAdminAddressEntry(final AddressModel addressModel)
	{
		getModelService().save(addressModel);
	}

	@Override
	public void removeAdminAddress(final AddressModel addressModel)
	{
		getModelService().remove(addressModel);
	}




}
