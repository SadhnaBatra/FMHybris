package com.fmo.wom.domain;

import java.io.Serializable;

public class CreditCheckBO extends WOMBaseBO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String creditBlock = " ";
	
	private String orderBlock  = " ";
	
	
	public CreditCheckBO() {
		super();
	}
	
	public CreditCheckBO(String creditBlock, String orderBlock) {
		super();
		this.creditBlock = creditBlock;
		this.orderBlock = orderBlock;
	}

	public String getCreditBlock() {
		return creditBlock;
	}
	
	public void setCreditBlock(String creditBlock) {
		this.creditBlock = creditBlock;
	}
	
	public String getOrderBlock() {
		return orderBlock;
	}
	
	public void setOrderBlock(String orderBlock) {
		this.orderBlock = orderBlock;
	}
	
	@Override
	public String toString() {
		return "CreditCheckBO [creditBlock "+creditBlock+", orderBlock "+orderBlock+" ]";
	}
	
}
