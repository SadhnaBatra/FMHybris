ACC.product = {
	
	initQuickviewLightbox:function(){
		this.enableAddToCartButton();
	},
	
	
	enableAddToCartButton: function ()
	{
		$('#addToCartButton').removeAttr("disabled");
	},

	/**
	 * This is now disabled. Originally it was called in the initQuickviewLightbox and in the onReady callback.
	 * FM now wants to have the cart redirect to the cart page, so we are no longer wrapping the POST and displaying
	 * a pop-up. This code is being kept in place in case they decide to go back to the cart pop-up in the near future.
	 */
	bindToAddToCartForm: function ()
	{
		var addToCartForm = $('.add_to_cart_form');
		try{
		addToCartForm.ajaxForm({success: ACC.product.displayAddToCartPopup});
		}catch(e){}
	},

	displayAddToCartPopup: function (cartResult, statusText, xhr, formElement)
	{
		
		ACC.common.$globalMessages.html(cartResult.cartGlobalMessagesHtml);
		
		$('#addToCartLayer').remove();
		
		if (typeof ACC.minicart.refreshMiniCartCount == 'function')
		{
			ACC.minicart.refreshMiniCartCount();
		}
		
		$("#header").append(cartResult.addToCartLayer);
		

		$('#addToCartLayer').fadeIn(function(){
			$.colorbox.close();
			if (typeof timeoutId != 'undefined')
			{
				clearTimeout(timeoutId);
			}
			timeoutId = setTimeout(function ()
			{
				$('#addToCartLayer').fadeOut(function(){
			 	   $('#addToCartLayer').remove();
					
				});
			}, 5000);
			
		});
		
		var productCode = $('[name=productCodePost]', formElement).val();
		var quantityField = $('[name=qty]', formElement).val();

		var quantity = 1;
		if (quantityField != undefined)
		{
			quantity = quantityField;
		}

		ACC.track.trackAddToCart(productCode, quantity, cartResult.cartData);
		
		// if it is orderForm, disable add to cart button in the end
		if($('#orderFormAddToCart').length > 0) {
			$('#addToCartBtn').attr('disabled','disabled');
		}
	}

};

$(document).ready(function ()
{
	with(ACC.product)
	{
		enableAddToCartButton();
	}
});

