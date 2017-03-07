/**
 * 
 */
package com.federalmogul.core.interceptor;

import de.hybris.platform.catalog.enums.ArticleApprovalStatus;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;

import com.federalmogul.core.enums.PartStatus;
import com.federalmogul.falcon.core.model.FMPartModel;


/**
 * @author mahaveera
 * 
 */
@SuppressWarnings("deprecation")
public class FMPartStatusUpdateInterceptor implements PrepareInterceptor
{
	@SuppressWarnings("deprecation")
	@Override
	public void onPrepare(final Object model, final InterceptorContext ctx) throws InterceptorException
	{

		if (ctx.isModified(model, FMPartModel.PARTSTATUS))
		{
			if (model instanceof FMPartModel)
			{
				final FMPartModel part = (FMPartModel) model;
				if (null != part.getPartstatus()
						&& (part.getPartstatus().equals(PartStatus.ACTIVEPART) || part.getPartstatus().equals(PartStatus.NEWPART)))
				{
					part.setApprovalStatus(ArticleApprovalStatus.APPROVED);
				}
				if (null != part.getPartstatus() && part.getPartstatus().equals(PartStatus.OBSOLETEPART))
				{
					part.setApprovalStatus(ArticleApprovalStatus.UNAPPROVED);
				}
			}
		}
		if (ctx.isModified(model, FMPartModel.APPROVALSTATUS))
		{
			if (model instanceof FMPartModel)
			{
				final FMPartModel part = (FMPartModel) model;
				if (null != part.getApprovalStatus() && (part.getApprovalStatus().equals(ArticleApprovalStatus.APPROVED)))
				{
					part.setPartstatus(PartStatus.ACTIVEPART);
				}
				if (null != part.getApprovalStatus() && (part.getApprovalStatus().equals(ArticleApprovalStatus.UNAPPROVED)))
				{
					part.setPartstatus(PartStatus.OBSOLETEPART);
				}
			}
		}
	}
}
