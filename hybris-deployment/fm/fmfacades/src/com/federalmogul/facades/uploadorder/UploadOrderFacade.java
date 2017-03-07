/**
 * 
 */
package com.federalmogul.facades.uploadorder;

import java.util.List;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;


import com.federalmogul.facades.upload.order.data.UploadOrderHistoryData;
import com.federalmogul.facades.upload.order.data.uploadOrderData;
import com.federalmogul.facades.upload.order.data.uploadOrderEntryData;
import com.fmo.wom.domain.WeightBO;
import com.federalmogul.falcon.core.model.FMPartModel;


/**
 * @author mamud
 * 
 */
public interface UploadOrderFacade
{
	public void createUploadorder(uploadOrderData orderData);

	List<uploadOrderData> viewUploadorder();

	List<uploadOrderData> viewCSRUploadorder();

	List<uploadOrderData> viewCSRUploadorder(String code);

	boolean saveUploadorder(String code);

	List<uploadOrderData> searchUploadorder(String ponumber, String sapordernumber, String sdate, String edate, String status);

	List<uploadOrderData> searchCSRUploadorder(String code, String poNumber, String sapOrderNumber, String sDate, String eDate,
			String status);

	List<uploadOrderEntryData> viewUploadorderEntry(String code);

	List<UploadOrderHistoryData> viewUploadorderHistory(String code);

	boolean editUploadorderEntry(String code, String partNumber, String quantity, String entryCode, String productFlag);

	boolean deleteUploadorderEntry(String code, String entryCode);

	boolean deleteUploadorder(String code);

	/**
	 * @param ponumber
	 * @param sapordernumber
	 * @param sdate
	 * @param edate
	 * @param status
	 * @return
	 */
	public List<uploadOrderData> getGlobalCSRSearchUploadOrderData(String ponumber, String sapordernumber, String sdate,
			String edate, String status);

	public void createPriceRowForUser(Double price, FMPartModel part, String currency, WeightBO weight);

	public void createReportLog(FMCustomerAccountModel accModel,FMCustomerModel custModel,String eventType);

}
