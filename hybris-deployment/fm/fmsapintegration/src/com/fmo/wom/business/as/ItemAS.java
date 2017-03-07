/**
 * 
 */
package com.fmo.wom.business.as;

import java.util.List;

import com.fmo.wom.business.as.config.ConfigurableAS;
import com.fmo.wom.common.exception.WOMBusinessDataException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.ItemBO;

/**
 * @author wiprouser
 *
 */
public interface ItemAS extends ConfigurableAS {
	
	/**
     * Resolve the given parts list and check the inventory for them if they are good parts.  The master order number
     * is not required, but can be submitted to allow for audit trails across check orderable calls.
     * @param masterOdrNum
     * @param itemList
     * @param account
     * @param shipTo
     * @return
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     * @throws WOMBusinessDataException
	 * @throws WOMValidationException 
     */
    public List<ItemBO> checkOrderableAndInventory(String masterOrderNumber, List<ItemBO> itemList,
    									           AccountBO billToAcount, AccountBO shipToAccount ) 
            			throws WOMExternalSystemException,WOMTransactionException, WOMBusinessDataException, WOMValidationException;
    
    /**
     * Toned down version of check orderable that does NOT check the inventory and determine distribution center data where applicable
     */
    public List<ItemBO> checkOrderable(String masterOrderNumber, List<ItemBO> itemList, AccountBO billToAcount, AccountBO shipToAccount ) 
        throws WOMExternalSystemException,WOMTransactionException, WOMBusinessDataException, WOMValidationException;

}
