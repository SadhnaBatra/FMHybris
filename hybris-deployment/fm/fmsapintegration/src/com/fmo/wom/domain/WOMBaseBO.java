package com.fmo.wom.domain;

import java.util.Date;

//import javax.persistence.*;

/**
 * MappedSuperclass for mapping commonly used fields in various entities.
 * All entity classes extending from this class will automatically inherit 
 * these mappings. 
 * 
 * NOTE: 
 * 1. If an entity's underlying database table does NOT contain any of these 
 * 		columns (even under a different name), DO NOT extend that entity class 
 * 		from this class.
 * 2. If an entity's underlying database table has one or more (but not all)
 * 		of these columns (with same or different names), map the fields that 
 * 		DO NOT have corresponding columns in the table to arbitrary columns of
 * 		similar datatypes (whether or not they have been mapped already) using
 * 		the '//AttributeOverride' (for one column) or '//AttributeOverrides'
 * 		(for more than one column).
 * 
 * //author vishws74
 */
//MappedSuperclass
public class WOMBaseBO {

    public static final String FLAG_YES = "Y";
    public static final String FLAG_NO = "N";
    
	//Column (name="CREATE_USERID")
	protected String createUserId;	

    //Temporal( TemporalType.TIMESTAMP)
	//Column(name="CREATE_TIMESTAMP")
	protected Date createTimestamp;

    //Column (name="UPDATE_USERID")
    protected String updateUserId;

    //Temporal( TemporalType.TIMESTAMP)
	//Column(name="UPDATE_TIMESTAMP")
    protected Date updateTimestamp;

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
	
	protected String getMaxString(String arg, int maxSize) {
	    if (arg != null && arg.length() > maxSize) {
	        return arg.substring(0, maxSize);
	    }
	    return arg;
	}

}
