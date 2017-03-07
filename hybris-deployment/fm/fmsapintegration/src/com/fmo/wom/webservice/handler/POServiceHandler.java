package com.fmo.wom.webservice.handler;

//import org.springframework.beans.factory.annotation.Autowired;

import com.fmo.wom.business.as.IPOOrderASUSCanImpl;
import com.fmo.wom.business.as.OrderAS;
import com.fmo.wom.business.as.OrderASUSCan;
import com.fmo.wom.common.exception.WOMProcessException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.enums.OrderSource;
import com.fmo.wom.transformation.service.TransformationService;
import com.fmo.wom.transformation.util.TransformationServiceFactory;
/**
 * Delegates the requests to other layers in sequential manner
 * @author chanac32
 *
 */
public class POServiceHandler extends OrderServiceHandler{
//	private static Logger logger = LoggerFactory.getLogger(POServiceHandler.class);
	// inject service using the spring configuration
	//@Autowired 
	//OrderASUSCan orderASUSCan;
	
	public OrderAS getOrderService() {
		return new IPOOrderASUSCanImpl();
	}
	public void setOrderService(OrderASUSCan orderService) {
		//this.orderASUSCan = orderService;
	}

	@Override
	public OrderBO executeOrder(OrderBO order) 
		throws WOMResourceException, WOMValidationException, WOMProcessException {
		order.setOrderSource(OrderSource.IPO);
		return this.getOrderService().processOrder(order);
	}
	@Override
	public TransformationService getRequestTransformationService() {
		return TransformationServiceFactory.getPoTransformationService();
	}
	@Override
	public TransformationService getResponseTransformationService() {
		return TransformationServiceFactory.getApoTransformationService();
	}
	
}
