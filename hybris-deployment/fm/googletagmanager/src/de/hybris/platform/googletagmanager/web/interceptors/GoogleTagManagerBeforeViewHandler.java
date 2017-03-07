/**
 * 
 */
package de.hybris.platform.googletagmanager.web.interceptors;

import de.hybris.platform.acceleratorservices.config.HostConfigService;
import de.hybris.platform.core.Registry;
import de.hybris.platform.util.config.ConfigIntf;


import java.util.List;
import java.util.ArrayList;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import de.hybris.platform.addonsupport.interceptors.BeforeViewHandlerAdaptee;


public class GoogleTagManagerBeforeViewHandler implements BeforeViewHandlerAdaptee
{
	@Resource(name = "hostConfigService")
	private HostConfigService hostConfigService;

	private static final Logger LOG = Logger.getLogger(GoogleTagManagerBeforeViewHandler.class);

	// Change listener
	private ConfigIntf.ConfigChangeListener configChangeListener;

	// Google Tag Manager container id class attr
	private static String ecommerce;
	private static String containerId;
	private static List<String> checkoutStepsList;

	// properties' keys
	private static final String GOOGLE_TAGMANAGER_PREFIX = "googletagmanager";
	private static final String PROPERTIES_GTM_CONTAINER_ID = "containerId";
	private static final String PROPERTIES_ECOMMERCE = "ecommerce";

	// GTM ID variable for JSP
	private static final String GTM_CONTAINER_ID = "ContainerId";
	private static final String CHECKOUT_STEP = "checkoutStep";
	private static final String CHECKOUT_STEPS_LIST = "checkoutStepsList";

	private static final String TAG_NAMESPACE = "gtm";
	private static String serverName;

	@Override
	public String beforeView(final HttpServletRequest request, final HttpServletResponse response, final ModelMap model,
			final String viewName) throws Exception
	{
		// Create the change listener and register it to listen when the config properties are changed in the platform
		if (configChangeListener == null)
		{
			registerConfigChangeLister();
		}
		serverName = request.getServerName();

		if(containerId == null){
			//Get the value of GTM container id from settings
			containerId = getHostConfigService().getProperty(GOOGLE_TAGMANAGER_PREFIX + "." + PROPERTIES_GTM_CONTAINER_ID, serverName);
		}

		//Send container ID to the template
		model.addAttribute(TAG_NAMESPACE + GTM_CONTAINER_ID, containerId);



		//do checkout steps exist 
		if(checkoutStepsList == null){
			updateCheckoutStepsList(serverName);
		}
		
		model.addAttribute(TAG_NAMESPACE + CHECKOUT_STEPS_LIST, checkoutStepsList);

		//Ecommerce setting (enhanced vs legacy)
		if(ecommerce == null){
			ecommerce = getHostConfigService().getProperty(GOOGLE_TAGMANAGER_PREFIX + "." + PROPERTIES_ECOMMERCE, serverName);
		}
		model.addAttribute(TAG_NAMESPACE + PROPERTIES_ECOMMERCE, ecommerce);

		return viewName;
	}

	protected class ConfigChangeListener implements ConfigIntf.ConfigChangeListener
	{
		@Override
		public void configChanged(final String key, final String newValue)
		{
			// If the platform settings change update the containerId
			if (key.equals(GOOGLE_TAGMANAGER_PREFIX + "." + PROPERTIES_GTM_CONTAINER_ID))
			{
				containerId = newValue;
			}
			// If any of the checkout step changes update checkout steps list
			else if(key.startsWith(GOOGLE_TAGMANAGER_PREFIX + "." + CHECKOUT_STEP)){
				updateCheckoutStepsList(serverName);
			}

			else if(key.equals(GOOGLE_TAGMANAGER_PREFIX + "." + PROPERTIES_ECOMMERCE)){
				ecommerce = newValue;
			}
		}
	}

	protected void updateCheckoutStepsList(String serverName){
		String checkoutStep;
		if(checkoutStepsList==null){
			checkoutStepsList = new ArrayList<String>();
		}
		else{
			checkoutStepsList.clear();
		}

		//Allow maximum of 20 steps
		for(int i=2; i<=20; i++){
			checkoutStep = getHostConfigService().getProperty(GOOGLE_TAGMANAGER_PREFIX + "." + CHECKOUT_STEP + i, serverName);
			if(checkoutStep == null)
				break;

			checkoutStepsList.add(checkoutStep);
		}
	}

	protected void registerConfigChangeLister()
	{
		final ConfigIntf config = Registry.getMasterTenant().getConfig();
		configChangeListener = new ConfigChangeListener();
		config.registerConfigChangeListener(configChangeListener);
	}


	public void setHostConfigService(final HostConfigService hostConfigService)
	{
		this.hostConfigService = hostConfigService;
	}

	public HostConfigService getHostConfigService()
	{
		return hostConfigService;
	}

}