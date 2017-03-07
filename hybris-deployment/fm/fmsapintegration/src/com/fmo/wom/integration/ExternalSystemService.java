package com.fmo.wom.integration;

import java.util.List;

import com.fmo.wom.common.exception.WOMConnectionException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.ItemBO;

public interface ExternalSystemService {
	
	public List<ItemBO> checkOrderable(List<ItemBO> itemList, AccountBO account)
	  throws WOMExternalSystemException;
	
	public List<ItemBO> checkInventory(List<ItemBO> itemList, AccountBO account, boolean isFutureOrder)
	  throws WOMExternalSystemException;

}
