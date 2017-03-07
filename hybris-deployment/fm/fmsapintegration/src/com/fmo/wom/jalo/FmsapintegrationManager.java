package com.fmo.wom.jalo;

import com.fmo.wom.constants.FmsapintegrationConstants;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import org.apache.log4j.Logger;

@SuppressWarnings("PMD")
public class FmsapintegrationManager extends GeneratedFmsapintegrationManager
{
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger( FmsapintegrationManager.class.getName() );
	
	public static final FmsapintegrationManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (FmsapintegrationManager) em.getExtension(FmsapintegrationConstants.EXTENSIONNAME);
	}
	
}
