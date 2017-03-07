package com.fmo.wom.business.as;

import java.util.List;

import com.fmo.wom.business.as.config.ConfigurableAS;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.InvoiceBO;
import com.fmo.wom.domain.InvoiceSearchCriteriaBO;

public interface InvoiceAS extends ConfigurableAS {

	public List<InvoiceBO> searchInvoices(InvoiceSearchCriteriaBO searchCriteria)
			throws WOMExternalSystemException, WOMValidationException, WOMTransactionException;

}
