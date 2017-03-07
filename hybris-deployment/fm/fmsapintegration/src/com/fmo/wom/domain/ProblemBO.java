package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

import com.fmo.wom.domain.ExternalSystemStatusBO.StatusCategory;

public class ProblemBO implements Serializable {
	
	private static final long serialVersionUID = 881791109205314717L;

	private StatusCategory statusCategory = null;
    private WOMCodeDescBO code = null;
    
    private boolean allowedToProcess = false;
    
    /**
	 * Flag indicating whether or not the part contained in this line item can
	 * be added for further processing.
	 */
	private boolean canBeAdded = false;

	/**
	 * Flag indicating whether or not this part should be redisplayed to the user.
	 */
	private boolean redisplayPart = false;

	/**
	 * Contains objects for any messages associated with this line item.
	 */
	private String message = null;
	private String messageKey = null;
	private List<Object> messageParams;
	
	/**
	 * Stores the options to be presented to the user for corrective action.
	 */
	private Map correctiveOptions = null;

	
	public boolean isAllowedToProcess() {
		return allowedToProcess;
	}

	public void setAllowedToProcess(boolean allowedToProcess) {
		this.allowedToProcess = allowedToProcess;
	}

	public boolean isCanBeAdded() {
		return canBeAdded;
	}

	public void setCanBeAdded(boolean canBeAdded) {
		this.canBeAdded = canBeAdded;
	}

	public boolean isRedisplayPart() {
		return redisplayPart;
	}

	public void setRedisplayPart(boolean redisplayPart) {
		this.redisplayPart = redisplayPart;
	}

	public String getMessageKey() {
		return messageKey;
	}

    public List<Object> getMessageParams() {
        return messageParams;
    }

    public void setMessageParams(List<Object> messageParams) {
        this.messageParams = messageParams;
    }
    
    public void addMessageParam(int index, Object aMessageParam) {
        
        if (messageParams == null) {
            messageParams = new ArrayList<Object>();
        }
        
        while (messageParams.size() - 1 < index) {
            messageParams.add("");
        }
        messageParams.set(index, aMessageParam);
    }

    public StatusCategory getStatusCategory() {
        return statusCategory;
    }

    public void setStatusCategory(StatusCategory statusCategory) {
        this.statusCategory = statusCategory;
    }

    public WOMCodeDescBO getCode() {
        return code;
    }

    public void setCode(WOMCodeDescBO code) {
        this.code = code;
    }

    public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public Map getCorrectiveOptions() {
		return correctiveOptions;
	}

	public void setCorrectiveOptions(Map correctiveOptions) {
		this.correctiveOptions = correctiveOptions;
	}

	/**
	 * Based on the message key in here, look up the appropriate real world message
	 * @return text description message for this problem
	 */
	public String getMessage() {
	    return message;
	}
	
	public void setMessage(String message) {
	    this.message = message;
	}
	
	/**
	 * Based on message key stored in this object, look up the appropriate IPO specific message.
	 * @return IPO specific message
	 */
	@XmlElement(name="ipoMessage")
	public String getIPOMessage() {
	    return messageKey;
	}

	/**
	 * Based on this problem status, get the associated IPO specific code
	 * @return IPO specific status code
	 */
	@XmlElement(name="ipoCode")
	public String getIPOCode() {
	    return null;
	}

  	@Override
    public String toString() {
        return "ProblemBO [statusCategory=" + statusCategory + ", code=" + code
                + ", canBeAdded=" + canBeAdded + ", redisplayPart="
                + redisplayPart + ", messages=" + messageKey
                + ", correctiveOptions=" + correctiveOptions + "]";
    }

    public enum ProblemStatus {
	    
	    PART_FOUND(StatusCategory.PART_FOUND),
	    PART_MIGRATED(StatusCategory.PART_MIGRATED),
	    PART_NOT_FOUND(StatusCategory.PART_NOT_FOUND),
	    MULTIPLE_PART_FOUND(StatusCategory.MULTIPLE_PART_FOUND),
	    PART_SETUP_ERROR(StatusCategory.PART_SETUP_ERROR),
	    NO_INVENTORY(StatusCategory.INVENTORY_ISSUE),
	    INSUFFICIENT_INVENTORY(StatusCategory.INVENTORY_ISSUE),
	    SOLD_IN_MULTIPLES(StatusCategory.INVENTORY_ISSUE);
	    
	    private StatusCategory statusCategory;
	    
	    private ProblemStatus(StatusCategory aStatusCategory) {
	        this.statusCategory = aStatusCategory;
	    }
	    
	    public StatusCategory getStatusCategory() {
	        return statusCategory;
	    }
	}
	
}
