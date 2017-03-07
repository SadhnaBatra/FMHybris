package com.fmo.wom.webservice.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fmo.wom.business.as.IPOOrderStatusASUSCanImpl;
import com.fmo.wom.common.exception.WOMProcessException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.helper.XmlListAdapter;
import com.fmo.wom.transformation.service.TransformationService;
import com.fmo.wom.transformation.util.TransformationServiceFactory;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
/**
 * Delegates the requests to other layers in sequential manner
 * @author chanac32
 *
 */
public class GSServiceHandler extends FMOBaseServiceHandler{
	
    //@Autowired
	//OrderStatusASUSCan orderStatusASUSCan;
	
	@Override
	public Object executeProcess(Object obj) throws WOMResourceException,
			WOMValidationException, WOMProcessException {
		Map<String,String> shipmentData = toData((ElementNSImpl)obj);
		//TODO. replace hard coded values with Constants
		IPOOrderStatusASUSCanImpl orderStatusASUSCan =  new IPOOrderStatusASUSCanImpl();
		List<ShippedOrderBO> showShipments =  orderStatusASUSCan.getOrderAndShipmentStatus(shipmentData.get("billToAccountCode")
				, shipmentData.get("mstrOrderNumber"), shipmentData.get("custPoNumber"));
		
		if(showShipments.size()>1){
			throw new WOMValidationException("More than one shipment found. Please provide unique combination of Master Order number and Purchase Order Number combination");
		}
		return new XmlListAdapter<ShippedOrderBO>(showShipments);		
	}

	@Override
	public TransformationService getRequestTransformationService() {
		return TransformationServiceFactory.getGsTransformationService();
	}
	@Override
	public TransformationService getResponseTransformationService() {
		return TransformationServiceFactory.getSsTransformationService();
	}
	private Map<String,String> toData(ElementNSImpl nsObj){
		NodeList nodes = nsObj.getChildNodes();
		int iIndex = 0;
		Map<String,String> elemenetValues = new HashMap<String,String>();
		while(iIndex < nodes.getLength()){
			Node node = (Node)nodes.item(iIndex);
			if(node!=null){
				elemenetValues.put(node.getLocalName(), node.getTextContent());
			}
			iIndex++;
		}
		return elemenetValues;
	}

	/*public OrderStatusAS getOrderStatusAS() {
		return orderStatusASUSCan;
	}

	public void setOrderStatusAS(OrderStatusASUSCan orderStatusAS) {
		this.orderStatusASUSCan = orderStatusASUSCan;
	}*/
}
