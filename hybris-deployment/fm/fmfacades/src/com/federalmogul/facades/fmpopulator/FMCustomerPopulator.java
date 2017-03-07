/**
 * 
 */
package com.federalmogul.facades.fmpopulator;

import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.TitleModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import com.federalmogul.core.model.FMBrandCampaignModel;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.model.FMTaxDocumentModel;
import com.federalmogul.facades.address.data.FMB2bAddressData;
import com.federalmogul.facades.user.data.FMBrandCampaignData;
import com.federalmogul.facades.user.data.FMCustomerAccountData;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.facades.user.data.FMTaxDocumentData;


/**
 * @author SA297584
 * 
 */
public class FMCustomerPopulator implements Populator<FMCustomerModel, FMCustomerData>
{
	private static final Logger LOG = Logger.getLogger(FMCustomerPopulator.class);

	private static final String FM_JOBBER_GROUP_UID = "FMJobberGroup";
	private static final String FM_INSTALLER_GROUP_UID = "FMInstallerGroup";
			
	private Converter<AddressModel, AddressData> addressConverter;
	private Converter<RegionModel, RegionData> regionConverter;
	private Converter<FMCustomerModel, FMCustomerData> fmCustomerConverter;
	private Converter<AddressModel, FMB2bAddressData> fmB2bAddressConverter;

	@Override
	public void populate(final FMCustomerModel source, final FMCustomerData target) throws ConversionException
	{
		LOG.info("Inside 'populate(...)' method");

		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");

		final TitleModel title = source.getTitle();
		if (title != null) {
			target.setTitleCode(title.getCode());
		}
		
		target.setUid(source.getUid());
		target.setEmail(source.getEmail());
		target.setLastName(source.getLastName());
		target.setName(source.getName());
		target.setFirstName(source.getName().substring(0, (source.getName().length() - source.getLastName().length()) - 1));
		target.setCustomerDataCustomerType(source.getFmCustomerType());
		LOG.info("populate(...): Target CustomerType: " + target.getCustomerDataCustomerType());

		if (source.getNewsLetterSubscription() != null) {
			target.setNewsLetterSubscription(source.getNewsLetterSubscription());
		}

		if (source.getEncodedPassword() != null) {
			target.setPassword(source.getEncodedPassword());
		}

		target.setActive(source.getActive().booleanValue());
		target.setIsLoginDisabled(Boolean.valueOf(source.isLoginDisabled()));
		target.setWorkContactNo(source.getWorkContactNo());

		if (source.getDefaultShipmentAddress() != null) {
			target.setDefaultShippingAddress(getAddressConverter().convert(source.getDefaultShipmentAddress()));
		} else if (source.getAddresses() != null && source.getAddresses().size() > 0) {
			LOG.info("populate(...): source.getDefaultShipmentAddress() is null. So, setting target to 'source.addresses.get(0)'");
			target.setDefaultShippingAddress(getAddressConverter().convert(((List<AddressModel>) source.getAddresses()).get(0)));
		}
		
		if (target.getDefaultShippingAddress() != null) {
			LOG.info("populate(...): target.getDefaultShippingAddress(): Address Line1: " + target.getDefaultShippingAddress().getLine1() +
									", Address Line2: " + target.getDefaultShippingAddress().getLine2() +
									", City: " + target.getDefaultShippingAddress().getTown());
		}

		final List<String> roleString = new ArrayList<String>();
		final Set<PrincipalGroupModel> userGroups = source.getGroups();

		for (final PrincipalGroupModel userGroup : userGroups) {
			roleString.add(userGroup.getUid());
		}

		target.setRoles(roleString);

		//to set channel code
		target.setUserTypeDescription(source.getChannelCode());

		final FMCustomerAccountModel sourceunit = (FMCustomerAccountModel) source.getDefaultB2BUnit();
		final FMCustomerAccountData targetunit = new FMCustomerAccountData();
		final List<FMB2bAddressData> unitAddressData = new ArrayList<FMB2bAddressData>();

		//changed here
		final Set<PrincipalGroupModel> groupss = source.getGroups();
		for (PrincipalGroupModel groupmodel : groupss) {

			if (sourceunit != null && (FM_JOBBER_GROUP_UID.equals(groupmodel.getUid())
					|| FM_INSTALLER_GROUP_UID.equals(groupmodel.getUid()))) {
				
				targetunit.setTaxID(sourceunit.getTaxID());
				LOG.info("populate(...): targetunit.getTaxID(): " + targetunit.getTaxID());

				final List<FMTaxDocumentData> taxdocsdata = new ArrayList<FMTaxDocumentData>();
				final List<FMTaxDocumentModel> newtaxdocsmodel = sourceunit.getFmtaxDocument();
				for (FMTaxDocumentModel newtaxdocmodel : newtaxdocsmodel) {				
					final FMTaxDocumentData fmtaxdoc = new FMTaxDocumentData();
					fmtaxdoc.setDocname(newtaxdocmodel.getDocname());
					LOG.info("populate(...): fmtaxdoc.getDocname(): " + fmtaxdoc.getDocname());

					if (newtaxdocmodel.getValidate() != null) {
						fmtaxdoc.setValidate(newtaxdocmodel.getValidate().toString());
					}

					fmtaxdoc.setState(getRegionConverter().convert(newtaxdocmodel.getState()));

					taxdocsdata.add(fmtaxdoc);
				}

				targetunit.setTaxDocumentList(taxdocsdata);
				targetunit.setProspectId(sourceunit.getProspectuid());
			}
		}
		
		if (sourceunit != null) {
			final List<AddressModel> unitaddresses = (List<AddressModel>) sourceunit.getAddresses();
			LOG.info("populate(...): sourceunit.getAddresses().size(): " + sourceunit.getAddresses().size());
			LOG.info("populate(...): sourceunit.getUid(): " + sourceunit.getUid());

			for (AddressModel unitaddress : unitaddresses) {
				LOG.info("populate(...): unitaddress.getLine1(): " + unitaddress.getLine1());
				FMB2bAddressData fmB2bAddressData = getFmB2bAddressConverter().convert(unitaddress);
				unitAddressData.add(fmB2bAddressData);
			}
			
			targetunit.setUnitAddress(unitAddressData);
			targetunit.setName(sourceunit.getLocName());
			targetunit.setUid(sourceunit.getUid());

			targetunit.setNabsAccountCode(sourceunit.getNabsAccountCode());
			target.setFmunit(targetunit);
			target.setUnit(targetunit);
		}

		target.setIsGarageRewardMember(source.getIsGarageRewardMember());
		target.setIsLoginDisabled(Boolean.valueOf(source.isLoginDisabled()));
		target.setLoyaltySignup(source.getLoyaltySubscription());
		target.setLmsSigninId(source.getLmsSigninID());
		target.setCrmContactId(source.getCrmCustomerID());
		target.setB2cLoyaltyMembershipId(source.getB2cLoyaltyMembershipId());
		target.setSurveyStatus(source.getSurveyStatus());
		target.setAboutShop(source.getAboutShop());
		target.setShopType(source.getShopType());
		target.setShopBanner(source.getShopBanner());
		target.setShopBays(source.getShopBays());
		target.setMostIntersted(source.getMostIntersted());
		target.setBrands(source.getBrands());
		if (source.getBrandCampaign() != null && !source.getBrandCampaign().isEmpty()) {
			List<FMBrandCampaignData> fmBrandCampaignDataList = new ArrayList<FMBrandCampaignData>();
			for (FMBrandCampaignModel fmBrandCampaignModel : source.getBrandCampaign()) {
					FMBrandCampaignData fmBrandCampaignData = new FMBrandCampaignData();
					fmBrandCampaignData.setFmBrandCampaignID(fmBrandCampaignModel.getBrandCampaignID());
					fmBrandCampaignData.setFmBrandCampaignInfo(fmBrandCampaignModel.getCampaignInfo());
					fmBrandCampaignData.setUserid(fmBrandCampaignModel.getUserID());
					LOG.info("FMCustomer populator - BrandCampaignID:"+fmBrandCampaignData.getFmBrandCampaignID()+" BrandCampaignInfo:"+fmBrandCampaignData.getFmBrandCampaignInfo());
					fmBrandCampaignDataList.add(fmBrandCampaignData);
			}
			target.setFmBrandCampaignList(fmBrandCampaignDataList);
		}
		if (target.getFmBrandCampaignList() != null) {
			LOG.info("Total number of BrandCampaigns User id:"+ source.getUid() +" participated in : "+target.getFmBrandCampaignList().size());
		}
	}

	protected Converter<AddressModel, AddressData> getAddressConverter() {
		return addressConverter;
	}

	@Required
	public void setAddressConverter(final Converter<AddressModel, AddressData> addressConverter) {
		this.addressConverter = addressConverter;
	}

	public Converter<RegionModel, RegionData> getRegionConverter() {
		return regionConverter;
	}

	public void setRegionConverter(Converter<RegionModel, RegionData> regionConverter) {
		this.regionConverter = regionConverter;
	}

	public Converter<FMCustomerModel, FMCustomerData> getFmCustomerConverter() {
		return fmCustomerConverter;
	}

	@Required
	public void setFmCustomerConverter(final Converter<FMCustomerModel, FMCustomerData> fmCustomerConverter) {
		this.fmCustomerConverter = fmCustomerConverter;
	}

	public Converter<AddressModel, FMB2bAddressData> getFmB2bAddressConverter() {
		return fmB2bAddressConverter;
	}

	public void setFmB2bAddressConverter(Converter<AddressModel, FMB2bAddressData> fmB2bAddressConverter) {
		this.fmB2bAddressConverter = fmB2bAddressConverter;
	}

}