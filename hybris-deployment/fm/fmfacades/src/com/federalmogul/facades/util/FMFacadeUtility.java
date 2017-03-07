/**
 * 
 */
package com.federalmogul.facades.util;

import de.hybris.platform.util.Config;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.facades.user.data.FMCustomerData;


/**
 * Utility class for facade interface connectivities.
 * 
 */
public class FMFacadeUtility
{

	private static final Logger LOG = Logger.getLogger(FMFacadeUtility.class);

	/**
	 * Method to create the users in LDAP
	 * 
	 * @param customerData
	 * @return Creation response
	 */
	public static String createNewCustomer(final FMCustomerData customerData)
	{
		return createNewCustomer(customerData, false);
	}

	/**
	 * Method to create the users in LDAP
	 * 
	 * @param customerData
	 * @param fmEmployeeFlag Indicates whether the user is an FM Employee
	 * 							true - FM Employee
	 * 						 	false - Non-FM Employee
	 * @return Creation response
	 */
	public static String createNewCustomer(final FMCustomerData customerData, boolean fmEmployeeFlag) {
		String rootDN = fmEmployeeFlag ? Config.getParameter("ldap.fmemployee.root.dn") : Config.getParameter("ldap.customer.root.dn");
		final String newCustomerDN = "cn=" + customerData.getEmail() + "," + rootDN;
		LOG.info("createNewCustomer(...): newCustomerDN: " + newCustomerDN);
		final Attributes newAttributes = new BasicAttributes(true);
		final Attribute oc = new BasicAttribute("objectclass");
		oc.add("top");
		oc.add("person");
		oc.add("organizationalperson");
		oc.add("user");
		newAttributes.put(oc);
		newAttributes.put(new BasicAttribute("mail", customerData.getEmail()));
		newAttributes.put(new BasicAttribute("uid", customerData.getEmail()));
		newAttributes.put(new BasicAttribute("cn", customerData.getEmail()));
		newAttributes.put(new BasicAttribute("sn", customerData.getLastName()));
		newAttributes.put(new BasicAttribute("givenName", customerData.getFirstName()));
		newAttributes.put(new BasicAttribute("displayName", customerData.getFirstName() + " " + customerData.getLastName()));
		newAttributes.put(new BasicAttribute("fmoSmPasswordData", customerData.getPassword()));
		newAttributes.put(new BasicAttribute("userPassword", customerData.getPassword()));

		try
		{
			getLDAPDirContext().createSubcontext(newCustomerDN, newAttributes);
		}
		catch (final NameAlreadyBoundException naexception)
		{
			updatePassword(newCustomerDN, customerData.getPassword());
			LOG.error("createNewCustomer(...): NameAlreadyBoundException Message: " + naexception.getMessage());
		}
		catch (final NamingException e)
		{
			// YTODO Auto-generated catch block
			LOG.error("createNewCustomer(...): NamingException Message: " + e.getMessage());
		}
		return newCustomerDN;		
	}

	/**
	 * Method to update the password in LDAP
	 * 
	 * @param newCustomer
	 */
	private static void updatePassword(final String customerCN, final String newPassword)
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
			LOG.info("updatePassword(...): Error: " + e);
		}
	}

	/**
	 * Method creates the LDAP context
	 * 
	 * @return DirContext LDAP context
	 * @throws NamingException
	 */
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

	/**
	 * Method updates the LDAP password for the user
	 */
	public static void updateLDAPPassword(final FMCustomerModel currentUser, final String newPassword)
	{
		updatePassword(currentUser.getLdaplogin(), newPassword);
	}

}
