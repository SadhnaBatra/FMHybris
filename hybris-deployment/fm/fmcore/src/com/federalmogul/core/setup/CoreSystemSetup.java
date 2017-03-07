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
package com.federalmogul.core.setup;

import de.hybris.platform.commerceservices.setup.AbstractSystemSetup;
import de.hybris.platform.commerceservices.setup.data.ImportData;
import de.hybris.platform.commerceservices.setup.events.CoreDataImportedEvent;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.initialization.SystemSetup;
import de.hybris.platform.core.initialization.SystemSetup.Process;
import de.hybris.platform.core.initialization.SystemSetup.Type;
import de.hybris.platform.core.initialization.SystemSetupContext;
import de.hybris.platform.core.initialization.SystemSetupParameter;
import de.hybris.platform.core.initialization.SystemSetupParameterMethod;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.validation.services.ValidationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.federalmogul.core.constants.FmCoreConstants;


/**
 * This class provides hooks into the system's initialization and update processes.
 * 
 * @see "https://wiki.hybris.com/display/release4/Hooks+for+Initialization+and+Update+Process"
 */
@SystemSetup(extension = FmCoreConstants.EXTENSIONNAME)
public class CoreSystemSetup extends AbstractSystemSetup
{
	public static final String IMPORT_SITES = "importSites";
	public static final String IMPORT_SYNC_CATALOGS = "syncProducts&ContentCatalogs";
	public static final String IMPORT_ACCESS_RIGHTS = "accessRights";
	public static final String ACTIVATE_SOLR_CRON_JOBS = "activateSolrCronJobs";

	public static final String FEDERALMOGUL = "federalmogul";



	/**
	 * This method will be called by system creator during initialization and system update. Be sure that this method can
	 * be called repeatedly.
	 * 
	 * @param context
	 *           the context provides the selected parameters and values
	 */
	@SystemSetup(type = Type.ESSENTIAL, process = Process.ALL)
	public void createEssentialData(final SystemSetupContext context)
	{
		importImpexFile(context, "/fmcore/import/essential-data.impex");
		importImpexFile(context, "/fmcore/import/countries.impex");
		importImpexFile(context, "/fmcore/import/delivery-modes.impex");

		importImpexFile(context, "/fmcore/import/themes.impex");
	}

	/**
	 * Generates the Dropdown and Multi-select boxes for the project data import
	 */
	@Override
	@SystemSetupParameterMethod
	public List<SystemSetupParameter> getInitializationOptions()
	{
		final List<SystemSetupParameter> params = new ArrayList<SystemSetupParameter>();

		params.add(createBooleanSystemSetupParameter(IMPORT_SITES, "Import Sites", true));
		params.add(createBooleanSystemSetupParameter(IMPORT_SYNC_CATALOGS, "Sync Products & Content Catalogs", false));
		params.add(createBooleanSystemSetupParameter(IMPORT_ACCESS_RIGHTS, "Import Users & Groups", true));
		params.add(createBooleanSystemSetupParameter(ACTIVATE_SOLR_CRON_JOBS, "Activate Solr Cron Jobs", false));

		return params;
	}

	/**
	 * This method will be called during the system initialization.
	 * 
	 * @param context
	 *           the context provides the selected parameters and values
	 */
	@SystemSetup(type = Type.PROJECT, process = Process.ALL)
	public void createProjectData(final SystemSetupContext context)
	{
		final boolean importSites = getBooleanSystemSetupParameter(context, IMPORT_SITES);
		//final boolean syncProducts = getBooleanSystemSetupParameter(context, IMPORT_SYNC_PRODUCTS);

		final boolean importAccessRights = getBooleanSystemSetupParameter(context, IMPORT_ACCESS_RIGHTS);

		if (importSites)
		{
			importProductCatalog(context, FEDERALMOGUL);

			importContentCatalog(context, FEDERALMOGUL);

			executeCatalogSyncJob(context, FEDERALMOGUL);

			importStore(context, FEDERALMOGUL);

			createAndActivateSolrIndex(context, FEDERALMOGUL);

			((ValidationService) Registry.getApplicationContext().getBean("validationService")).reloadValidationEngine();
		}

		final List<String> extensionNames = Registry.getCurrentTenant().getTenantSpecificExtensionNames();

		if (importAccessRights && extensionNames.contains("cmscockpit"))
		{
			importImpexFile(context, "/fmcore/import/cockpits/cmscockpit/cmscockpit-users.impex");
			importImpexFile(context, "/fmcore/import/cockpits/cmscockpit/cmscockpit-access-rights.impex");
		}

		if (importAccessRights && extensionNames.contains("btgcockpit"))
		{
			importImpexFile(context, "/fmcore/import/cockpits/cmscockpit/btgcockpit-users.impex");
			importImpexFile(context, "/fmcore/import/cockpits/cmscockpit/btgcockpit-access-rights.impex");
		}

		if (importAccessRights && extensionNames.contains("productcockpit"))
		{
			importImpexFile(context, "/fmcore/import/cockpits/productcockpit/productcockpit-users.impex");
			importImpexFile(context, "/fmcore/import/cockpits/productcockpit/productcockpit-access-rights.impex");
			importImpexFile(context, "/fmcore/import/cockpits/productcockpit/productcockpit-constraints.impex");
		}

		if (importAccessRights && extensionNames.contains("cscockpit"))
		{
			importImpexFile(context, "/fmcore/import/cockpits/cscockpit/cscockpit-users.impex");
			importImpexFile(context, "/fmcore/import/cockpits/cscockpit/cscockpit-access-rights.impex");
		}

		if (extensionNames.contains("mcc"))
		{
			importImpexFile(context, "/fmcore/import/mcc-sites-links.impex");
		}

		final ImportData federalMogulImportData = new ImportData();
		federalMogulImportData.setProductCatalogName(FEDERALMOGUL);
		federalMogulImportData.setContentCatalogNames(Arrays.asList(FEDERALMOGUL));
		federalMogulImportData.setStoreNames(Arrays.asList(FEDERALMOGUL));
		getEventService().publishEvent(new CoreDataImportedEvent(context, Arrays.asList(federalMogulImportData)));

	}

	@Override
	public PerformResult executeCatalogSyncJob(final SystemSetupContext context, final String catalogName)
	{
		final boolean syncCatalogs = getBooleanSystemSetupParameter(context, IMPORT_SYNC_CATALOGS);
		logInfo(context, "Begin preparing catalogs sync job  [" + catalogName + "]");
		importImpexFile(context, "/fmcore/import/catalogs-sync.impex", true);

		getSetupSyncJobService().assignDependentSyncJobs(catalogName + "ProductCatalog",
				Collections.singleton(catalogName + "ContentCatalog"));

		logInfo(context, "Done preparing catalogs sync job  [" + catalogName + "]");
		PerformResult syncCronJobResult = null;
		if (syncCatalogs)
		{
			logInfo(context, "Executing catalogs sync job  [" + catalogName + "]");
			syncCronJobResult = super.executeCatalogSyncJob(context, FEDERALMOGUL + "Catalog");
			logInfo(context, "Executed catalogs sync job  [" + catalogName + "]");
		}
		return syncCronJobResult;
	}

	protected void importProductCatalog(final SystemSetupContext context, final String catalogName)
	{
		logInfo(context, "Begin importing catalog [" + catalogName + "]");

		importImpexFile(context, "/fmcore/import/productCatalogs/" + catalogName + "ProductCatalog/catalog.impex", true);

		createProductCatalogSyncJob(context, catalogName + "ProductCatalog");

	}

	protected void createAndActivateSolrIndex(final SystemSetupContext context, final String storeName)
	{
		logInfo(context, "Begin SOLR index setup [" + storeName + "]");

		importImpexFile(context, "/fmcore/import/stores/" + storeName + "/solr.impex");

		createSolrIndexerCronJobs(storeName + "Index");

		importImpexFile(context, "/fmcore/import/stores/" + storeName + "/solrtrigger.impex");

		if (getBooleanSystemSetupParameter(context, ACTIVATE_SOLR_CRON_JOBS))
		{
			executeSolrIndexerCronJob(storeName + "Index", true);
			activateSolrIndexerCronJobs(storeName + "Index");
		}

		logInfo(context, "Done SOLR index setup [" + storeName + "]");
	}

	protected void importContentCatalog(final SystemSetupContext context, final String catalogName)
	{
		logInfo(context, "Begin importing catalog [" + catalogName + "]");

		importImpexFile(context, "/fmcore/import/contentCatalogs/" + catalogName + "ContentCatalog/catalog.impex", false);
		//importImpexFile(context, "/fmcore/import/contentCatalogs/" + catalogName + "ContentCatalog/cms-content.impex", false);
		importImpexFile(context, "/fmcore/import/contentCatalogs/" + catalogName + "ContentCatalog/cms-mobile-content.impex", false);

		importImpexFile(context, "/fmcore/import/contentCatalogs/" + catalogName + "ContentCatalog/email-content.impex", false);

		createContentCatalogSyncJob(context, catalogName + "ContentCatalog");



		logInfo(context, "Done importing catalog [" + catalogName + "]");
	}

	protected void importStore(final SystemSetupContext context, final String storeName)
	{
		logInfo(context, "Begin importing store [" + storeName + "]");

		//importImpexFile(context, "/fmcore/import/stores/" + storeName + "/store.impex");
		//importImpexFile(context, "/fmcore/import/stores/" + storeName + "/site.impex");

		logInfo(context, "Done importing store [" + storeName + "]");
	}
}
