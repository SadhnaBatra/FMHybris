package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The persistent class for the CUSTOMER_RELATIONSHIP database table.
 * 
 * NOTE: Do NOT extend this class from either WOMBaseBO or WOMCodeDescBO since
 * 			the underlying 'QUANTITY' table does not contain any audit column.
 * 			Else, the audit columns will be included in SQL statements sent to
 * 			the database, resulting in errors.
 * 
 * @author vishws74
 */

public class AccountRelationshipBO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private AccountRelationshipPK id;

	private Date createdDate;

	//bi-directional many-to-one association to AccountBO
    @XmlTransient
	@ManyToOne
    private AccountBO primaryCustomer;

	//bi-directional many-to-one association to AccountBO
    @XmlTransient
	private AccountBO secondaryCustomer;

    public AccountRelationshipBO() {
    }

	public AccountRelationshipPK getId() {
		return this.id;
	}

	public void setId(AccountRelationshipPK id) {
		this.id = id;
	}
	
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	public AccountBO getPrimaryCustomer() {
		return this.primaryCustomer;
	}


	public void setPrimaryCustomer(AccountBO customer) {
		this.primaryCustomer = customer;
	}
	

	public AccountBO getSecondaryCustomer() {
		return this.secondaryCustomer;
	}

	public void setSecondaryCustomer(AccountBO customer) {
		this.secondaryCustomer = customer;
	}
	
}