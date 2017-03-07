/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package com.federalmogul.storefront.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Strategy for setting and removing an auth token cookie for AEM use
 */
public interface AEMCookieStrategy
{
	/**
	 * Retrieves an auth token and sets it on a cookie
	 * 
	 * @param request
	 * @param response
	 */
	void setCookie(String username, String password, HttpServletRequest request, HttpServletResponse response);

	/**
	 * Removes the auth token cookie
	 * 
	 * @param request
	 * @param response
	 */
	void deleteCookie(HttpServletRequest request, HttpServletResponse response);
}
