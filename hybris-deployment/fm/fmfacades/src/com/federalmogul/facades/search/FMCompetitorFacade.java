/**
 * 
 */
package com.federalmogul.facades.search;

import java.util.List;

import com.federalmogul.falcon.core.model.FMPartModel;
import com.federalmogul.falcon.core.model.PartInterchangeModel;


/**
 * @author SU276498
 * 
 */
public interface FMCompetitorFacade
{

	public List<PartInterchangeModel> getExternalPartInfo(String externalPart);

	public List<FMPartModel> getPartNumberInfo(String partNumber);

	public List<String> getAppilcationUsageReports(String accountNumber, String userId, String sDate, String eDate);


}
