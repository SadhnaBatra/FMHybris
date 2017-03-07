package com.fmo.wom.domain;

public class CustomerBO extends WOMBaseBO {

	private UserBO user = null;
	private CartBO cart = null;
	
	public UserBO getUser() {
		return user;
	}

	public void setUser(UserBO user) {
		this.user = user;
	}

	public CartBO getCart() {
		return cart;
	}

	public void setCart(CartBO cart) {
		this.cart = cart;
	}
}
