package com.fmo.wom.domain;

import java.io.Serializable;

import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The persistent class for the CUSTOMER_COMMENTS database table.
 * 
 * NOTE: Do NOT extend this class from either WOMBaseBO or WOMCodeDescBO since
 * 			the underlying 'CUSTOMER_COMMENTS' table does not contain any audit 
 * 			column. Else, the audit columns will be included in SQL statements 
 * 			sent to the database, resulting in errors. Currently there is no way 
 * 			in JPA to suppress extra attributes passed on from the 
 * 			'@MappedSuperclass'.
 * 
 * @author vishws74
 */
public class AccountCommentBO implements Serializable {
	private static final long serialVersionUID = 1L;

	private AccountCommentPK id;

	private String commentText;

	//bi-directional many-to-one association to AccountBOTest
	@XmlTransient
    @ManyToOne
	private AccountBO customer;

    public AccountCommentBO() {
    }

	public AccountCommentPK getId() {
		return this.id;
	}

	public void setId(AccountCommentPK id) {
		this.id = id;
	}
	
	public String getCommentText() {
		return this.commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public AccountBO getCustomer() {
		return this.customer;
	}

	public void setCustomer(AccountBO customer) {
		this.customer = customer;
	}
	
}