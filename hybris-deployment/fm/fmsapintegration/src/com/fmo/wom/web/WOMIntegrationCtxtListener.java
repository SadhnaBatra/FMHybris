package com.fmo.wom.web;

import de.hybris.platform.spring.HybrisContextLoaderListener;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.fmo.wom.common.util.PropertiesUtil;
import com.fmo.wom.integration.nabs.util.DB2ConnectionUtil;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.fmo.wom.transformation.util.XSLTransformer;


public class WOMIntegrationCtxtListener extends HybrisContextLoaderListener
{
	Logger logger = Logger.getLogger(WOMIntegrationCtxtListener.class);


	protected static final String ACCELERATORSTOREFRONT = "acceleratorstorefront";

	@Override
	protected void fillConfigLocations(final String appName, final List<String> locations)
	{
		// Get the default config
		super.fillConfigLocations(appName, locations);

		/* lookup the Db2 connection pool */
		EntityManager entityManager = DB2ConnectionUtil.getEntityManager();
		if (entityManager != null)
		{
			logger.info("WOMIntegration Context .. Db2 connected ");
			if (entityManager.isOpen())
			{
				entityManager.close();
				entityManager = null;
			}
		}
		new PropertiesUtil();
		new MessageResourceUtil();
		new XSLTransformer();
		logger.info("WOMIntegration Context Initilized .. ");

		// Load the 'name independent' storefront config
		fillConfigLocationsFromExtensions(ACCELERATORSTOREFRONT, locations);
	}


}
