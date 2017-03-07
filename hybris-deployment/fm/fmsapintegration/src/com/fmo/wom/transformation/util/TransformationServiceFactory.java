package com.fmo.wom.transformation.util;

import com.fmo.wom.transformation.service.AcknowledgePurchaseOrderTransformer;
import com.fmo.wom.transformation.service.AddQuoteTransformer;
import com.fmo.wom.transformation.service.GetShipmentTransformer;
import com.fmo.wom.transformation.service.PurchaseOrderTransformer;
import com.fmo.wom.transformation.service.RequestForQuoteTransformer;
import com.fmo.wom.transformation.service.ShowShipmentTransformer;
import com.fmo.wom.transformation.service.TransformationService;
/**
 * Returns the appropriate transformation service 
 * @author chanac32
 *
 */
public class TransformationServiceFactory {
	/**
	 * Returns the RFQ transformation service
	 * @return TransformationService
	 */
	public static TransformationService getRfqTransformationService() {
		return new RequestForQuoteTransformer();
	}
	public static TransformationService getPoTransformationService() {
		return new PurchaseOrderTransformer();
	}
	public static TransformationService getGsTransformationService() {
		return new GetShipmentTransformer();
	}
	/**
	 * Returns the AddQuote transformer
	 * @return
	 */
	public static TransformationService getAqTransformationService() {
		return new AddQuoteTransformer();
	}
	public static TransformationService getApoTransformationService() {
		return new AcknowledgePurchaseOrderTransformer();
	}
	public static TransformationService getSsTransformationService() {
		return new ShowShipmentTransformer();
	}
}
