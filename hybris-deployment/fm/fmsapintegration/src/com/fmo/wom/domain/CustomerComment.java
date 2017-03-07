package com.fmo.wom.domain;

import java.io.Serializable;



/**
 * The persistent class for the CUSTOMER_COMMENTS database table.
 * 
 */
public class CustomerComment extends WOMBaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	private CustomerCommentPK id;

	private String commentText;

	//bi-directional many-to-one association to Customer
	private Customer customer;

    public CustomerComment() {
    }

	public CustomerCommentPK getId() {
		return this.id;
	}

	public void setId(CustomerCommentPK id) {
		this.id = id;
	}
	
	public String getCommentText() {
		return this.commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}