/**
 * 
 */
package com.federalmogul.facades.account.impl;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

import java.io.IOException;

import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.facades.account.LoyaltySurveyFacade;
import com.federalmogul.facades.user.data.FMSurveyData;
import com.fm.falcon.webservices.dto.LoyaltySurveyRequestDTO;
import com.fm.falcon.webservices.dto.LoyaltySurveyResponseDTO;
import com.fm.falcon.webservices.soap.helper.LoyaltySurveyHelper;


/**
 * @author JA324889
 * 
 */
public class LoyaltySurveyFacadeImpl implements LoyaltySurveyFacade
{
	private static final Logger LOG = Logger.getLogger(LoyaltySurveyFacadeImpl.class);
	@Autowired
	protected UserService userService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.federalmogul.facades.account.LoyaltySurveyFacade#populatebrandSurveyData(com.federalmogul.facades.user.data
	 * .FMSurveyData)
	 */
	@Override
	public LoyaltySurveyResponseDTO populatebrandSurveyData(final FMSurveyData brandsurveyData)
			throws UnsupportedOperationException, ClassNotFoundException, SOAPException, IOException
	{
		String prospectId = null;
		String contactPersonId = null;
		final UserModel currentUserModel = userService.getCurrentUser();
		if (currentUserModel instanceof FMCustomerModel)
		{
			if (((FMCustomerModel) currentUserModel).getFmUnit() != null)
			{
				final FMCustomerAccountModel unitModel = ((FMCustomerModel) currentUserModel).getFmUnit();
				prospectId = unitModel.getProspectuid();
				LOG.info("contact id" + unitModel.getUid());
				contactPersonId = unitModel.getUid();				
				brandsurveyData.setContactPersonID(contactPersonId);
				brandsurveyData.setProspectID(prospectId);
			}
		}
		final LoyaltySurveyHelper surveyHelper = new LoyaltySurveyHelper();
		LoyaltySurveyResponseDTO surveyResponse = null;

		return surveyResponse = (LoyaltySurveyResponseDTO) surveyHelper.sendSOAPMessage(convertsurveyDataToDTO((brandsurveyData)));


	}

	private LoyaltySurveyRequestDTO convertsurveyDataToDTO(final FMSurveyData survey)
	{
		final LoyaltySurveyRequestDTO reqDTO = new LoyaltySurveyRequestDTO();
		reqDTO.setServiceName("MT_HYBRIS_SURVEY");
		LOG.info("contact id for request" + survey.getContactPersonID());
		reqDTO.setContactPersonID(survey.getContactPersonID());
		reqDTO.setProspectID(survey.getProspectID());
		//FAL - 90 - Social Media changes
		if (survey.getUniqueID()!=null && survey.getUniqueID().size() > 0)
		{
			reqDTO.setUniqueID(survey.getUniqueID());
		}
		if (survey.getProductReviewFlag() != null)
		{
			reqDTO.setProductReviewFlag(survey.getProductReviewFlag());
		}
		if (survey.getSocialMediaFlag() != null)
		{
			reqDTO.setSocialMediaFlag(survey.getSocialMediaFlag());
		}
		if (survey.getMembershipId() != null)
		{
			reqDTO.setMembershipID(survey.getMembershipId());
		}
		return reqDTO;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.federalmogul.facades.account.LoyaltySurveyFacade#populateHobbiesSurveyData(com.federalmogul.facades.user.data
	 * .FMSurveyData)
	 */
	@Override
	public LoyaltySurveyResponseDTO populateHobbiesSurveyData(final FMSurveyData hobbiesSurveyData)
			throws UnsupportedOperationException, ClassNotFoundException, SOAPException, IOException
	{
		String prospectId = null;
		String contactPersonId = null;
		final UserModel currentUserModel = userService.getCurrentUser();
		if (currentUserModel instanceof FMCustomerModel)
		{
			if (((FMCustomerModel) currentUserModel).getFmUnit() != null)
			{
				final FMCustomerAccountModel unitModel = ((FMCustomerModel) currentUserModel).getFmUnit();
				prospectId = unitModel.getProspectuid();
				LOG.info("contact id" + unitModel.getUid());
				contactPersonId = unitModel.getUid();				
				hobbiesSurveyData.setContactPersonID(contactPersonId);
				hobbiesSurveyData.setProspectID(prospectId);
			}
		}
		final LoyaltySurveyHelper surveyHelper = new LoyaltySurveyHelper();
		LoyaltySurveyResponseDTO surveyResponse = null;

		return surveyResponse = (LoyaltySurveyResponseDTO) surveyHelper
				.sendSOAPMessage(convertsurveyDataToDTO((hobbiesSurveyData)));

	}

	@Override
	public LoyaltySurveyResponseDTO populatePartsBuyingSurveyData(final FMSurveyData partsBuyingSurveyData)
			throws UnsupportedOperationException, ClassNotFoundException, SOAPException, IOException
	{
		String prospectId = null;
		String contactPersonId = null;
		final UserModel currentUserModel = userService.getCurrentUser();
		if (currentUserModel instanceof FMCustomerModel)
		{
			if (((FMCustomerModel) currentUserModel).getFmUnit() != null)
			{
				final FMCustomerAccountModel unitModel = ((FMCustomerModel) currentUserModel).getFmUnit();
				prospectId = unitModel.getProspectuid();
				LOG.info("contact id" + unitModel.getUid());
				contactPersonId = unitModel.getUid();				
				partsBuyingSurveyData.setContactPersonID(contactPersonId);
				partsBuyingSurveyData.setProspectID(prospectId);
			}
		}
		final LoyaltySurveyHelper surveyHelper = new LoyaltySurveyHelper();
		LoyaltySurveyResponseDTO surveyResponse = null;

		return surveyResponse = (LoyaltySurveyResponseDTO) surveyHelper
				.sendSOAPMessage(convertsurveyDataToDTO((partsBuyingSurveyData)));


	}
	
	//FAL - 90 - Social Media changes
	@Override
	public LoyaltySurveyResponseDTO populateSocialMediaData(final FMSurveyData socialMediaData)
			throws UnsupportedOperationException, ClassNotFoundException, SOAPException, IOException
	{
		String prospectId = null;
		String contactPersonId = null;
		final UserModel currentUserModel = userService.getCurrentUser();
		if (currentUserModel instanceof FMCustomerModel && ((FMCustomerModel) currentUserModel).getIsGarageRewardMember())
		{
			if (((FMCustomerModel) currentUserModel).getFmUnit() != null)
			{
				final FMCustomerAccountModel unitModel = ((FMCustomerModel) currentUserModel).getFmUnit();
				prospectId = unitModel.getProspectuid();
				LOG.info("contact id" + unitModel.getUid());
				contactPersonId = unitModel.getUid();
				socialMediaData.setContactPersonID(contactPersonId);
				socialMediaData.setProspectID(prospectId);
				socialMediaData.setSocialMediaFlag("X");
				socialMediaData.setMembershipId(((FMCustomerModel) currentUserModel).getB2cLoyaltyMembershipId());
			}
		}
		final LoyaltySurveyHelper surveyHelper = new LoyaltySurveyHelper();
		LoyaltySurveyResponseDTO surveyResponse = null;

		return surveyResponse = (LoyaltySurveyResponseDTO) surveyHelper.sendSOAPMessage(convertsurveyDataToDTO((socialMediaData)));
	}
@Override
	public LoyaltySurveyResponseDTO populateReviewData(final FMSurveyData reviewData) throws UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException
	{
		String prospectId = null;
		String contactPersonId = null;
		final UserModel currentUserModel = userService.getCurrentUser();

		if (currentUserModel instanceof FMCustomerModel && ((FMCustomerModel) currentUserModel).getIsGarageRewardMember())				
		{
			LOG.info("in crm case");
			LOG.info("in GarageRewardMember" + ((FMCustomerModel) currentUserModel).getIsGarageRewardMember());
			if (((FMCustomerModel) currentUserModel).getFmUnit() != null)
			{
			LOG.info("in GarageRewardMember");
				final FMCustomerAccountModel unitModel = ((FMCustomerModel) currentUserModel).getFmUnit();
				prospectId = unitModel.getProspectuid();
				LOG.info("contact id" + unitModel.getUid());
				contactPersonId = unitModel.getUid();
				reviewData.setContactPersonID(contactPersonId);
				reviewData.setProspectID(prospectId);
				reviewData.setProductReviewFlag("X");
				reviewData.setMembershipId(((FMCustomerModel) currentUserModel).getB2cLoyaltyMembershipId());
			}
			final LoyaltySurveyHelper surveyHelper = new LoyaltySurveyHelper();
			LoyaltySurveyResponseDTO surveyResponse = null;

			return surveyResponse = (LoyaltySurveyResponseDTO) surveyHelper.sendSOAPMessage(convertsurveyDataToDTO((reviewData)));
		}
		return null;

	}

}
