package com.fmo.wom.business.as;

import java.util.List;

import com.fmo.wom.business.as.config.ConfigurableAS;
import com.fmo.wom.common.exception.WOMProcessException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.PossibleOrderTypeBO;

public interface OrderAS extends ConfigurableAS {
	
    /**
     * Get a new master order number.  Should be similar format for all systems, but may come
     * from different places.
     * @return a newly created unique master order number
     */
    public String getMasterOrderNumber();
    
	/**
	 * Process the order. Determine the shipping methods, reserve and book order
	 * @param order
	 * @return
	 * @throws WOMResourceException
	 * @throws WOMValidationException
	 * @throws WOMProcessException
	 */
	public OrderBO processOrder(OrderBO order)throws WOMResourceException,
												WOMValidationException, WOMProcessException;
	
	/**
	 * Process an uploaded order.  Similar in some aspects of workflow but different in back end
	 * ERP impl as well as some inventory and rules checks. 
	 * @param order
	 * @return
	 */
	public OrderBO processUploadOrder(OrderBO order) throws WOMValidationException, WOMResourceException;
	
	
	/**
	 * From the given order, calculate the possible order types.  This is a business layer 
	 * call to allow for market specific data-driven reasons for allowing/disallowing certain
	 * order types.
	 * @param anOrder
	 * @return
	 */
	public List<PossibleOrderTypeBO> calculatePossibleOrderTypes(OrderBO anOrder);
	
	
}
