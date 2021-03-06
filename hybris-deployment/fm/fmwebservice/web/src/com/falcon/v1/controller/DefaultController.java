/**
 *
 */
package com.falcon.v1.controller;

import com.falcon.exceptions.UnknownResourceException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DefaultController
{
	@RequestMapping
	public void defaultRequest(final HttpServletRequest request)
	{
		throw new UnknownResourceException("There is no resource for path " + request.getRequestURI());
	}
}
