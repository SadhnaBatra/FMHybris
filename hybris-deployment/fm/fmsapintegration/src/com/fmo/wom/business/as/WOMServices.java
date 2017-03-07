package com.fmo.wom.business.as;

import com.fmo.wom.integration.NabsService;

public class WOMServices {
	
	public static InventoryASUSCanImpl getCheckInventoryService(){
		return new  InventoryASUSCanImpl();
	}
	
	public static ItemASUSCanImpl getPartResolutionService(){
		return new  ItemASUSCanImpl();
	}
	
	public static OrderASUSCanImpl getProcessOrderService(){
		return new  OrderASUSCanImpl();
	}
	
	public static NabsService getNabsService(){
		return new NabsService();
	}

}
