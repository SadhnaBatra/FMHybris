package com.fmo.wom.integration.util;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.fmo.wom.common.util.PropertiesUtil;
import com.fmo.wom.domain.ExternalSystemStatusBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.enums.ExternalSystem;

public class ProblemBOFactory {

    public static ProblemBO createSupercededPartProblem(String newPartNumber, String oldPartNumber) {
        
        ProblemBO supercededPartProblem = ProblemBOFactory.createProblem(MessageResourceUtil.PART_SUPERCEDED);
        // superceded parts can be processed
        supercededPartProblem.setAllowedToProcess(true);
        supercededPartProblem.addMessageParam(0, oldPartNumber);
        supercededPartProblem.addMessageParam(1, newPartNumber);
        
        return supercededPartProblem;
    }
    
    public static ProblemBO createProblem(String statusKey, ExternalSystem anExternalSystem) {
        ExternalSystemStatusBO anExternalSystemStatus = MessageResourceUtil.getExtSystemStatusViaStatusKey(statusKey, anExternalSystem);
        return createProblem(statusKey, anExternalSystemStatus);
    }

    public static ProblemBO createProblem(String statusKey) {
        ExternalSystemStatusBO anExternalSystemStatus = MessageResourceUtil.getExtSystemStatusViaStatusKey(statusKey);
        return createProblem(statusKey, anExternalSystemStatus);
    }

    private static ProblemBO createProblem(String statusKey, final ExternalSystemStatusBO anExternalSystemStatus) {
        
        ProblemBO aProblemBO = new ProblemBO(){
        	@Override
        	@XmlElement(name="ipoMessage")
        	public String getIPOMessage() {
				final String msgKey = this.getMessageKey();
				final List<Object> parms = this.getMessageParams();
				if (null != msgKey)
				{
					if (null != parms)
					{
						if (MessageResourceUtil.SOLD_IN_MULTIPLES.equals(msgKey) || MessageResourceUtil.PART_SUPERCEDED.equals(msgKey))
						{
							final String msg = PropertiesUtil.getIPOMessageProperty(msgKey);
							return msg.replace("{0}", parms.get(0).toString()).replace("{1}", parms.get(1).toString());
						}
						if (MessageResourceUtil.NO_INVENTORY.equals(msgKey))
						{
							final String msg = PropertiesUtil.getIPOMessageProperty(msgKey);
							return msg.replace("{0}", parms.get(0).toString());
						}
					}
					else
					{
						return PropertiesUtil.getIPOMessageProperty(msgKey);
					}
				}
				else
				{
					return PropertiesUtil.getIPOMessageProperty(this.getMessageKey());
				}
				return PropertiesUtil.getIPOMessageProperty(this.getMessageKey());
				//MessageResourceUtil.getIPOMessage(this.getMessageKey(), this.getMessageParams());
        	}
        	@Override
            @XmlElement(name="ipoCode")
            public String getIPOCode() {
                return anExternalSystemStatus.getIpoItemStatusCode();
            }
        	
        };
        aProblemBO.setStatusCategory(anExternalSystemStatus.getStatusCategory());
        aProblemBO.setMessageKey(statusKey);
        return aProblemBO;
    }
}
