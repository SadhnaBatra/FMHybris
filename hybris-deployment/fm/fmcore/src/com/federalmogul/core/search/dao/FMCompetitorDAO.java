/**
 * 
 */
package com.federalmogul.core.search.dao;

import java.util.List;

import com.federalmogul.falcon.core.model.FMPartModel;
import com.federalmogul.falcon.core.model.PartInterchangeModel;


/**
 * @author SU276498
 * 
 */
public interface FMCompetitorDAO
{

	public List<PartInterchangeModel> getExternalPartInfo(final String externalPart);

	public List<FMPartModel> getPartNumberInfo(final String partNumber);

	public List<String> getAppilcationUsageReports(String accountNumber, String userId, 	String sDate, String eDate);
	

}