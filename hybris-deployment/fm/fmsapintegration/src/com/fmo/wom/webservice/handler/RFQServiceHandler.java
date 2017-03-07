package com.fmo.wom.webservice.handler;

import org.apache.log4j.Logger;

import com.fmo.wom.business.as.IPOOrderASUSCanImpl;
import com.fmo.wom.business.as.OrderASUSCan;
import com.fmo.wom.common.exception.WOMProcessException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.enums.OrderSource;
import com.fmo.wom.domain.enums.UsageType;
import com.fmo.wom.transformation.service.TransformationService;
import com.fmo.wom.transformation.util.TransformationServiceFactory;
/**
 * Delegates the requests to other layers in sequential manner
 * @author chanac32
 *
 */
public class RFQServiceHandler extends OrderServiceHandler{
	private static Logger logger = Logger.getLogger(RFQServiceHandler.class);
	// inject service using the spring configuration
	//@Autowired 
	//OrderASUSCan orderASUSCan;
	
	public OrderASUSCan getOrderService() {
		return new IPOOrderASUSCanImpl();
	}
	
	public void setOrderService(OrderASUSCan orderService) {
		//this.orderASUSCan = orderService;
	}
	
	@Override
	public OrderBO executeOrder(OrderBO order) 
		throws WOMResourceException, WOMValidationException, WOMProcessException {
		order.setOrderSource(OrderSource.IPO);
		order.setUsageType(UsageType.QUOTE);
		return this.getOrderService().processOrder(order);
	}
	@Override
	public TransformationService getRequestTransformationService() {
		return TransformationServiceFactory.getRfqTransformationService();
	}
	@Override
	public TransformationService getResponseTransformationService() {
		return TransformationServiceFactory.getAqTransformationService();
	}
	
}
