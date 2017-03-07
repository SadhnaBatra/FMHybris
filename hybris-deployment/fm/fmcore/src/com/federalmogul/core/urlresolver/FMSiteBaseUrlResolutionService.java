/**
 * 
 */
package com.federalmogul.core.urlresolver;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;

import java.net.UnknownHostException;


/**
 * @author su244261
 * 
 */
public interface FMSiteBaseUrlResolutionService
{

	String getWebsiteUrlForSite(BaseSiteModel site, boolean secure, String path) throws UnknownHostException;

	String getMediaUrlForSite(BaseSiteModel site, boolean secure);


	String getMediaUrlForSite(BaseSiteModel site, boolean secure, String path);

	String getWebsiteUrlForSite(final BaseSiteModel site, final boolean secure, final String path, final String queryParams)
			throws UnknownHostException;


	String getWebsiteUrlForSite(BaseSiteModel site, String encodingAttributes, boolean secure, String path)
			throws UnknownHostException;

	String getWebsiteUrlForSite(BaseSiteModel site, String encodingAtrributes, boolean secure, String path, String queryParams)
			throws UnknownHostException;
}
