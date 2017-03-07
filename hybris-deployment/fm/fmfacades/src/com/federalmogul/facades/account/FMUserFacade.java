/**
 * 
 */
package com.federalmogul.facades.account;

import de.hybris.platform.commercefacades.user.data.AddressData;

import java.util.List;


/**
 * @author su244261
 * 
 */
public interface FMUserFacade
{
	public List<AddressData> getAddressBook(final String userID);

	public AddressData getFMDefaultAddress();

}
