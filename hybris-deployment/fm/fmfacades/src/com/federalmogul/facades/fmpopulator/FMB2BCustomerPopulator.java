/**
 * 
 */
package com.federalmogul.facades.fmpopulator;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BPermissionModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BPermissionData;
import de.hybris.platform.b2bacceleratorfacades.user.populators.B2BCustomerPopulator;
import de.hybris.platform.b2bacceleratorservices.strategies.B2BUserGroupsLookUpStrategy;
import de.hybris.platform.commercefacades.storesession.data.CurrencyData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.PredicateUtils;
import org.springframework.beans.factory.annotation.Required;


/**
 * @author JA324889
 * 
 */
public class FMB2BCustomerPopulator extends B2BCustomerPopulator
{

	private CommonI18NService commonI18NService;
	private Converter<CurrencyModel, CurrencyData> currencyConverter;
	private B2BUnitService<B2BUnitModel, UserModel> b2bUnitService;
	private Converter<B2BPermissionModel, B2BPermissionData> b2BPermissionConverter;
	private B2BUserGroupsLookUpStrategy b2BUserGroupsLookUpStrategy;



	@Override
	public void populate(final CustomerModel source, final CustomerData target) throws ConversionException
	{
		super.populate(source, target);
	}

	@Override
	protected void populateRoles(final B2BCustomerModel source, final CustomerData target)
	{

		final List<String> roles = new ArrayList<String>();
		final Set<PrincipalGroupModel> roleModels = new HashSet<PrincipalGroupModel>(source.getGroups());
		CollectionUtils.filter(roleModels, PredicateUtils.notPredicate(PredicateUtils.instanceofPredicate(B2BUnitModel.class)));

		for (final PrincipalGroupModel role : roleModels)
		{
			// only display allowed usergroups

			roles.add(role.getUid());
			

		}
		
		target.setRoles(roles);
	}

	@Override
	protected B2BUnitService<B2BUnitModel, UserModel> getB2bUnitService()
	{
		return b2bUnitService;
	}

	@Override
	@Required
	public void setB2bUnitService(final B2BUnitService<B2BUnitModel, UserModel> b2bUnitService)
	{
		this.b2bUnitService = b2bUnitService;
	}

	@Override
	protected CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	@Override
	@Required
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	@Override
	protected Converter<CurrencyModel, CurrencyData> getCurrencyConverter()
	{
		return currencyConverter;
	}

	@Override
	@Required
	public void setCurrencyConverter(final Converter<CurrencyModel, CurrencyData> currencyConverter)
	{
		this.currencyConverter = currencyConverter;
	}

	@Override
	protected Converter<B2BPermissionModel, B2BPermissionData> getB2BPermissionConverter()
	{
		return b2BPermissionConverter;
	}

	@Override
	@Required
	public void setB2BPermissionConverter(final Converter<B2BPermissionModel, B2BPermissionData> b2BPermissionConverter)
	{
		this.b2BPermissionConverter = b2BPermissionConverter;
	}

	@Override
	protected B2BUserGroupsLookUpStrategy getB2BUserGroupsLookUpStrategy()
	{
		return b2BUserGroupsLookUpStrategy;
	}

	@Override
	@Required
	public void setB2BUserGroupsLookUpStrategy(final B2BUserGroupsLookUpStrategy b2BUserGroupsLookUpStrategy)
	{
		this.b2BUserGroupsLookUpStrategy = b2BUserGroupsLookUpStrategy;
	}
}
