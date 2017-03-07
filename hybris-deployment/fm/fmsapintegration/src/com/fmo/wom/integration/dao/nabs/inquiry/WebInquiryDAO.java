package com.fmo.wom.integration.dao.nabs.inquiry;

import com.fmo.wom.common.dao.BaseDAO;
import com.fmo.wom.domain.nabs.inquiry.WebInquiryBO;
import com.fmo.wom.domain.nabs.inquiry.WebInquiryPK;

public interface WebInquiryDAO extends BaseDAO<WebInquiryPK, WebInquiryBO> {

    public WebInquiryBO findByInquiryKey(String inquiryKey);    

}
