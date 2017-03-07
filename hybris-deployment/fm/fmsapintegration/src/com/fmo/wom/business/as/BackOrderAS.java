package com.fmo.wom.business.as;

import java.util.List;

import com.fmo.wom.business.as.config.ConfigurableAS;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.BackOrderBO;

public interface BackOrderAS extends ConfigurableAS {

    /**
     * Search for BackOrders for the given Account Code. Order Number and Part Number are optional.
     * @param account	Account to search for Back Orders. Required.
     * @param orderNumber	Order Number to filter on
     * @param partNumber	Part Number to filter on
     * @return List of BackOrders matching the search criteria.
     * @throws WOMExternalSystemException
     * @throws WOMValidationException
     */
    public List<BackOrderBO> searchBackOrders(AccountBO account, String orderNumber, String partNumber)
    		throws WOMExternalSystemException, WOMValidationException, WOMTransactionException;
    
    /**
     * Back order search for a ship to account with a list of bill tos
     * @param billToAccounts
     * @param shipToAccount
     * @param orderNumber
     * @param partNumber
     * @return
     * @throws WOMExternalSystemException
     * @throws WOMValidationException
     * @throws WOMTransactionException
     */
    public List<BackOrderBO> searchBackOrders(List<AccountBO> billToAccounts, AccountBO shipToAccount, String orderNumber, String partNumber)
            throws WOMExternalSystemException, WOMValidationException, WOMTransactionException;


 }
