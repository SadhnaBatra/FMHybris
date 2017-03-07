/*
 * Created on Jun 1, 2011
 */
package com.fmo.wom.domain;

/**
 * Business object to hold Ship-To Account information.
 * 
 * @author vishws74
 */
public class ShipToAcctBO extends AccountBO {
	private static final long serialVersionUID = 1L;

	public ShipToAcctBO() {
		setBillToCustomer(false);
	}
}
