package com.fmo.wom.integration.dao.nabs.inquiry;




import com.fmo.wom.common.dao.JpaDAODB2;
import com.fmo.wom.domain.nabs.inquiry.WebInquiryBO;
import com.fmo.wom.domain.nabs.inquiry.WebInquiryPK;

public class WebInquiryDAOImpl extends JpaDAODB2<WebInquiryPK, WebInquiryBO>  implements WebInquiryDAO {


    public WebInquiryDAOImpl() {
		super();
		//setEntityManager(DB2ConnectionUtil.getEntityManager());
	}

	@Override
    public WebInquiryBO findByInquiryKey(String inquiryKey) {
    	WebInquiryPK id = new WebInquiryPK();
    	id.setInquiryKey(inquiryKey);
    	WebInquiryBO webInquiryBO = findById(id);
    	webInquiryBO.getOrderHeaderList();
        return webInquiryBO;
    }
   
}
