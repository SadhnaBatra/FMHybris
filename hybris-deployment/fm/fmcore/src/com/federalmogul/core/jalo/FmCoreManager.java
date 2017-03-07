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
package com.federalmogul.core.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.federalmogul.core.constants.FmCoreConstants;
import com.federalmogul.core.setup.CoreSystemSetup;

import org.apache.log4j.Logger;


/**
 * Don't use. User {@link CoreSystemSetup} instead.
 */
@SuppressWarnings("PMD")
public class FmCoreManager extends GeneratedFmCoreManager
{
	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(FmCoreManager.class.getName());

	public static final FmCoreManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (FmCoreManager) em.getExtension(FmCoreConstants.EXTENSIONNAME);
	}
}
