/**
 * 
 */
package com.federalmogul.storefront.security.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.web.DefaultRedirectStrategy;


/**
 * @author SA297584
 * 
 */
public class DefaultFMRedirectStrategy extends DefaultRedirectStrategy
{

	private static final Logger LOG = Logger.getLogger(DefaultFMRedirectStrategy.class);

	@Override
	public void sendRedirect(final HttpServletRequest request, final HttpServletResponse response, final String url)
			throws IOException
	{

		String redirectUrl = url;
		LOG.info("sendRedirect url ===>" + url);
		if (redirectUrl.toString().indexOf("createcustomer") > -1 || redirectUrl.toString().indexOf("fmlogin") > -1)
		{


			redirectUrl = "/";

		}


		super.sendRedirect(request, response, redirectUrl);
	}
}
