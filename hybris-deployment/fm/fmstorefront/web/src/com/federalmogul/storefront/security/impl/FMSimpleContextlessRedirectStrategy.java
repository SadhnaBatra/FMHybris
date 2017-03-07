package com.federalmogul.storefront.security.impl;

import org.apache.log4j.Logger;
import org.springframework.security.web.DefaultRedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Purpose of this class is to simply send the redirect without changing the config for the DefaultRedirectStrategy which could potentially
 * affect many redirects. Essentially, the context will never be taken into account here. This will be used to redirect logout
 * requests to AEM.
 */
public class FMSimpleContextlessRedirectStrategy extends DefaultRedirectStrategy {

	private static final Logger LOG = Logger.getLogger(FMSimpleContextlessRedirectStrategy.class);

	public FMSimpleContextlessRedirectStrategy() {
	}

	public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		String redirectUrl = response.encodeRedirectURL(url);
		LOG.debug("Redirecting to \'" + redirectUrl + "\'");

		response.sendRedirect(redirectUrl);
	}
}
