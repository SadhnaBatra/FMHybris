/**
 * 
 */
package com.federalmogul.core.uploadorder.dao;

import de.hybris.platform.servicelayer.internal.dao.Dao;

import java.util.List;

import com.federalmogul.falcon.core.model.UploadOrderModel;


/**
 * @author mamud
 * 
 */
public interface UploadOrderDAO extends Dao
{
	public void createUploadorder();

	public void updateUploadorder();

	public void deleteUploadorder();

	List<UploadOrderModel> viewUploadorder(String uid);

	List<UploadOrderModel> viewCSRUploadorder();

	List<UploadOrderModel> viewCSRUploadorder(String accCode);

	List<UploadOrderModel> searchUploadorder(String uid, String ponumber, String sapordernumber, String sdate, String edate,
			String status);

	List<UploadOrderModel> searchCSRUploadorder(String accCode, String ponumber, String sapordernumber, String sdate,
			String edate, String status);

	UploadOrderModel viewUploadOrderEntry(String code);

	UploadOrderModel viewUploadOrderHistory(String code);

	public void createUploadorderEntry();

	public void updateUploadorderEntry();

	public void deleteUploadorderEntry();

	public List<UploadOrderModel> viewGlobalCSRUploadorder(String ponumber, String sapordernumber, String sdate, String edate,
			String status);
}
