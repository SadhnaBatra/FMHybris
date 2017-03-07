ACC.track = {
	trackAddToCart: function (productCode,quantity,name,price)
	{
		window.mediator.publish('trackAddToCart',{
			productCode: productCode,
			quantity: quantity,
			productName:name,
                        productPrice:price
		});
	},
	trackRemoveFromCart: function(productCode, initialCartQuantity,name,price)
	{
		window.mediator.publish('trackRemoveFromCart',{
			productCode: productCode,
			initialCartQuantity: initialCartQuantity,
                        productName:name,
                        productPrice:price
		});
	},

	trackUpdateCart: function(productCode, initialCartQuantity, newCartQuantity,name,price)
	{
		window.mediator.publish('trackUpdateCart',{
			productCode: productCode,
			initialCartQuantity: initialCartQuantity,
			newCartQuantity: newCartQuantity,
			productName:name,
                        productPrice:price

		});
	}
	

};
