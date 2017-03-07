/*
 * Created on Jun 1, 2011
 */
package com.fmo.wom.domain;

/**
 * Business object to hold Bill-To Account information.
 * 
 * @author vishws74
 */
public class BillToAcctBO extends AccountBO {
	private static final long serialVersionUID = 1L;

	public BillToAcctBO() {
		setBillToCustomer(true);
	}
}
