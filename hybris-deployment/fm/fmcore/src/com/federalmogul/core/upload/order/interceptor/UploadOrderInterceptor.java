/**
 * 
 */
package com.federalmogul.core.upload.order.interceptor;

import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;

import org.apache.log4j.Logger;

import com.federalmogul.core.enums.UploadOrderStatus;
import com.federalmogul.falcon.core.model.UploadOrderModel;


/**
 * @author mamud
 * 
 */
public class UploadOrderInterceptor implements PrepareInterceptor
{

	private static final Logger LOG = Logger.getLogger(UploadOrderInterceptor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.servicelayer.interceptor.PrepareInterceptor#onPrepare(java.lang.Object,
	 * de.hybris.platform.servicelayer.interceptor.InterceptorContext)
	 */
	@Override
	public void onPrepare(final Object model, final InterceptorContext ctx) throws InterceptorException
	{

		// YTODO Auto-generated method stub

		if (ctx.isModified(model, UploadOrderModel.UPLOADORDERSTATUS))
		{
			if (model instanceof UploadOrderModel)
			{
				final UploadOrderModel order = (UploadOrderModel) model;
				if (null != order.getUploadOrderStatus() && (order.getUploadOrderStatus().equals(UploadOrderStatus.FILEPARSED)))
				{
					LOG.info("INside the method");
					order.setUploadOrderStatus(UploadOrderStatus.INPROGRESS);
				}

			}
		}

	}

}
