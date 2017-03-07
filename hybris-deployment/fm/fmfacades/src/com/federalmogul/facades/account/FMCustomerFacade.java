/**
 * 
 */
package com.federalmogul.facades.account;

import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commercefacades.user.data.TitleData;
import de.hybris.platform.commercefacades.user.exceptions.PasswordMismatchException;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.customer.TokenInvalidatedException;

import java.io.IOException;
import java.util.List;

import javax.xml.soap.SOAPException;

import org.springframework.web.multipart.MultipartFile;

import com.federalmogul.core.enums.FmTaxValidationType;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMTaxDocumentModel;
import com.federalmogul.facades.user.data.FMCustomerAccountData;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.facades.user.data.FMTaxDocumentData;
import com.federalmogul.falcon.core.model.FMCsrAccountListModel;


/**
 * @author SR279690
 * 
 */
public interface FMCustomerFacade extends CustomerFacade
{
	public void createCustomerAccount(FMCustomerData fmcustomerdata) throws UnsupportedOperationException, ClassNotFoundException,
			SOAPException, IOException;

	public void updateFMProfile(FMCustomerData fmcustomerData) throws DuplicateUidException, UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException;

	public FMCustomerData getfmCustomerDetails();


	public FMCustomerData getCurrentFMCustomer();


	public List<TitleData> getTitles();

	/**
	 * @param fmcustomerdata
	 */
	public void taxexemption(FMCustomerData fmcustomerdata);


	/**
	 * @param taxdocname
	 * @return
	 */
	public boolean downloadtaxdoc(String taxdocname);

	/**
	 * @param taxdocname
	 * @return
	 */
	public boolean deletetaxdoc(String taxdocname);

	/**
	 * @return
	 */
	public List<AddressData> getFMAddressBook();

	/**
	 * @return
	 */
	AddressData getFMDefaultAddress();


	/**
	 * 
	 * @param taxDocPK
	 * @return
	 */
	public boolean updateTaxExemptionApprovalForPK(String taxDocPK, FmTaxValidationType adminTaxDocApproval);

	/**
	 * 
	 * @return
	 */
	public List<FMTaxDocumentModel> getAllTaxDocuments(String searchTaxDocName, String sortOption);


	/**
	 * 
	 * @param taxDocPK
	 * @return
	 */
	public FMTaxDocumentModel getFMTaxDocumentsForPK(String taxDocPK);

	/**
	 * 
	 * @param taxDocumentPK
	 * @return
	 */
	public boolean fmTaxDocumentFileDownload(final String taxDocumentPK);


	/**
	 * 
	 * @param file
	 * @param cust_Upload_File_Path
	 * @param dateFormate
	 * @return
	 */
	public FMTaxDocumentData convertFileToFMTaxDocumentModel(final MultipartFile file, final String uploadFilePath,
			final String dateFormate);

	/**
	 * @param oldPassword
	 * @param newPassword
	 */
	public void changeFMPassword(String oldPassword, String newPassword) throws PasswordMismatchException;

	public List<RegionData> getTaxApprovedRegions(final FMCustomerAccountData fmunit);

	/**
	 * @param email
	 * @return
	 */
	public boolean checkUidExists(String email);


	public void fmUpdatePassword(final String token, final String newPassword) throws TokenInvalidatedException;

	B2BUnitData getSoldToUnit(final String uid);

	/**
	 * @param addressID
	 */
	void removeAdminAddress(String addressID);


	AddressData getAddressByID(String addressID);

	/**
	 * @param addressData
	 */
	void editAdminAddress(AddressData addressData);


	public boolean checkTaxIdExists(String taxId);

	/**
	 * @return
	 */
	public List<FMCustomerAccountData> getPartnerAddress();

	/**
	 * @param uid
	 * @return
	 */
	public List<AddressData> getSoldToUnitAddress(String uid);

	/**
	 * @param uid
	 * @return
	 */
	public List<FMCustomerAccountData> getSoldToShipToUnitUid(String uid);

	/**
	 * @param fmcustomerdata
	 * @param state
	 * @return
	 */
	public boolean validateStateWiseTaxDocument(FMCustomerData fmcustomerdata, String state);

	public boolean createAdminAddress(final AddressData newAddress);


	/**
	 * @return
	 */
	List<FMCsrAccountListModel> getFMCSRAccountList();

	/**
	 * @return
	 */
	public List<FMCustomerAccountData> getMultipleSoldTos(String unitID);

	/**
	 * @param accountData
	 * @return
	 */
	public List<FMCustomerAccountData> getMultipleSoldTosCSR(FMCustomerAccountData accountData);

	/**
	 * @param accountData
	 * @return
	 */
	public List<FMCustomerAccountData> getPartnerAddressCSR(FMCustomerAccountData accountData);

	/**
	 * @param searchAddressText
	 * @return
	 */
	public List<FMCustomerAccountData> getB2BShipToAddressForSoldTo(String searchAddressText, String string);

	/**
	 * @param searchAddressText
	 * @return
	 */
	public List<FMCustomerAccountData> getB2BSoldToAddressForSoldTo(String searchAddressText, String string);

	/**
	 * @param searchAddressText
	 * @param string
	 * @return
	 */
	public List<FMCustomerAccountData> getB2BSoldToAddressForShipTo(String searchAddressText, String string);

	/**
	 * @param searchAddressText
	 * @param string
	 * @return
	 */
	public List<FMCustomerAccountData> getB2BShipToAddressForShipTo(String searchAddressText, String string);

	public FMCustomerAccountModel getFMB2BUnit(String uid);
	public List<FMCustomerAccountData> getB2BShipToAddressForSoldTo(String searchAddressText, String accountID, String searchType,
			String loggedUserType);
	/**
	 * @param uid
	 * @return
	 */
	public String checkSoldToShipto(String uid);
}
