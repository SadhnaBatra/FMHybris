/**
 * 
 */
package com.federalmogul.facades.account;

import java.io.IOException;

import javax.xml.soap.SOAPException;

import com.federalmogul.facades.user.data.FMSurveyData;
import com.fm.falcon.webservices.dto.LoyaltySurveyResponseDTO;

/**
 * @author JA324889
 * 
 */
public interface LoyaltySurveyFacade
{

	public LoyaltySurveyResponseDTO populatebrandSurveyData(final FMSurveyData surveyData) throws UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException;

	public LoyaltySurveyResponseDTO populateHobbiesSurveyData(final FMSurveyData surveyData) throws UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException;

	public LoyaltySurveyResponseDTO populatePartsBuyingSurveyData(final FMSurveyData surveyData)
			throws UnsupportedOperationException, ClassNotFoundException, SOAPException, IOException;
	//FAL - 90 - Social Media changes
	public LoyaltySurveyResponseDTO populateSocialMediaData(final FMSurveyData surveyData) throws UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException;
			
			
	public LoyaltySurveyResponseDTO populateReviewData(final FMSurveyData surveyData) throws UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException;

}