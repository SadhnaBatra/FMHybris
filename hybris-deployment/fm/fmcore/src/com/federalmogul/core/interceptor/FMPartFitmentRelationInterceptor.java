/**
 * 
 */
package com.federalmogul.core.interceptor;

import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.federalmogul.falcon.core.model.FMFitmentModel;
import com.federalmogul.falcon.core.model.FMPartModel;


/**
 * @author mahaveera
 * 
 */
@SuppressWarnings("deprecation")
public class FMPartFitmentRelationInterceptor implements PrepareInterceptor
{
	@Autowired
	private ProductService productService;

	@Autowired
	private ModelService modelService;

	@SuppressWarnings("deprecation")
	@Override
	public void onPrepare(final Object model, final InterceptorContext ctx) throws InterceptorException
	{
		final List<FMFitmentModel> parts = new ArrayList<FMFitmentModel>();
		if (ctx.isNew(model) || ctx.isModified(model, FMFitmentModel.PARTNUMBER))
		{
			if (model instanceof FMFitmentModel)
			{
				final FMFitmentModel fitment = (FMFitmentModel) model;
				if (null != fitment.getPartNumber())
				{
					final FMPartModel partModel = (FMPartModel) productService.getProductForCode(fitment.getPartNumber());
					if (null != partModel)
					{
						parts.add(fitment);
						partModel.setFitments(parts);
						modelService.save(partModel);
					}
				}
			}
		}
	}
}
