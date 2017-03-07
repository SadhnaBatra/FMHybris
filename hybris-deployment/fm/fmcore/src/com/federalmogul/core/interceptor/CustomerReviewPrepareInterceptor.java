package com.federalmogul.core.interceptor;

import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import de.hybris.platform.servicelayer.model.ItemModelContextImpl;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;
import java.util.Date;

/*
 The interceptor is coded to set the FM Admin Response Date on save of the object. This interceptor
 will make sure the FM admin response date is always the date on which the FM Admin enters response to customer response/
 updates response comments for any reason.This is also kind of a date validation check, preventing the FM Admin person
 from entering any date.
*/
public class CustomerReviewPrepareInterceptor implements PrepareInterceptor {
	private static final Logger LOG = Logger.getLogger(CustomerReviewPrepareInterceptor.class);
	
	@Override
	public void onPrepare(final Object model, final InterceptorContext ctx) throws InterceptorException
	{
	  String oldFmAdminResponse=null;
	  String newFmAdminResponse=null;
	  if(!ctx.isNew(model))
	  {	
		if (model instanceof CustomerReviewModel)
		{
		  CustomerReviewModel customerReview= (CustomerReviewModel)model;
		  final ItemModelContextImpl modelContext = (ItemModelContextImpl) customerReview.getItemModelContext();
		  if (modelContext.isLoaded(CustomerReviewModel.FMADMINRESPONSE))
		  {
			 oldFmAdminResponse=(String)modelContext.getOriginalValue(CustomerReviewModel.FMADMINRESPONSE);	
		  }
		  else
		  {
			 oldFmAdminResponse=(String)modelContext.getAttributeProvider().getAttribute(CustomerReviewModel.FMADMINRESPONSE);
		  }
		  newFmAdminResponse=customerReview.getFmAdminResponse();	
		  if(StringUtils.isNotBlank(newFmAdminResponse)&&!newFmAdminResponse.equalsIgnoreCase(oldFmAdminResponse))
		  {
			customerReview.setFmAdminResponseDate(new Date());
		  }
		}
	  }
	}
}
