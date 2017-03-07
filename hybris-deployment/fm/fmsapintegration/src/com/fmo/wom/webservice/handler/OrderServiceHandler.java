package com.fmo.wom.webservice.handler;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMProcessException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.OrderBO;
/**
 * Delegates the requests to other layers in sequential manner
 * @author chanac32
 *
 */
public abstract class OrderServiceHandler extends FMOBaseServiceHandler{
	private static Logger logger = Logger.getLogger(OrderServiceHandler.class);
	public abstract OrderBO executeOrder(OrderBO order)throws WOMResourceException, WOMValidationException, WOMProcessException;
	@Override
	public Object executeProcess(Object obj) 
		throws WOMResourceException, WOMValidationException, WOMProcessException {
		
		OrderBO order = (OrderBO)obj;
		order.setUserAccount(getIpoUserAccountBO());
		return executeOrder(order);
	}
	
}
