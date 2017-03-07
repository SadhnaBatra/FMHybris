ACC.cartremoveitem = {

	bindAll: function ()
	{
		this.bindCartRemoveProduct();
	},

	bindCartRemoveProduct: function ()
	{
		$('.submitRemoveProduct').on("click", function ()
		{		
			var entryNum = $(this).attr('id').split("_")[1];
		
			var $form = $('#updateCartForm' + entryNum);
			var initialCartQuantity = $form.find('input[name=initialQuantity]');
			var cartQuantity = $form.find('input[name=quantity]');
			var productCode = $form.find('input[name=productCode]').val(); 
			var productName = $form.find('input[name=productName]').val();
			var productPrice = $form.find('input[name=productPrice]').val(); 

			document.getElementById('quick_modal').style.display = 'block';
		    document.getElementById('quick_fade').style.display = 'block';
			ACC.track.trackRemoveFromCart(productCode, initialCartQuantity.val(),productName,productPrice);
			
			cartQuantity.val(0);
			initialCartQuantity.val(0);
			var method = $form.attr("method") ? $form.attr("method").toUpperCase() : "GET";

			$.ajax({
				url: $form.attr("action"),
				data: $form.serialize(),
				type: method,
				success: function(data) 
				{
					location.reload();
					document.getElementById('quick_modal').style.display = 'none';
					document.getElementById('quick_fade').style.display = 'none';
				},
				error: function() 
				{
					document.getElementById('quick_modal').style.display = 'none';
					document.getElementById('quick_fade').style.display = 'none';
					alert("Failed to remove quantity. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
				}
			});
		});
		
		$('.updateQuantityProduct').on("click", function (event)
		{
			var entryNum = $(this).attr('id').split("_")[1];
			
			var $form = $('#updateCartForm' + entryNum);
			var initialCartQuantity = $form.find('input[name=initialQuantity]').val();
			var newCartQuantity = $form.find('input[name=quantity]').val();
			var productCode = $form.find('input[name=productCode]').val(); 
			var productName = $form.find('input[name=productName]').val();
            var productPrice = $form.find('input[name=productPrice]').val(); 		

            if (initialCartQuantity != newCartQuantity)
			{ 
				document.getElementById('quick_modal').style.display = 'block';
			    document.getElementById('quick_fade').style.display = 'block';
				ACC.track.trackUpdateCart(productCode, initialCartQuantity, newCartQuantity,productName,productPrice);

				var method = $form.attr("method") ? $form.attr("method").toUpperCase() : "GET";
				$.ajax({
					url: $form.attr("action"),
					data: $form.serialize(),
					type: method,
					success: function(data) 
					{
						location.reload();
						document.getElementById('quick_modal').style.display = 'none';
						document.getElementById('quick_fade').style.display = 'none';
					},
					error: function() 
					{
						document.getElementById('quick_modal').style.display = 'none';
						document.getElementById('quick_fade').style.display = 'none';
						alert("Failed to update quantity. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
					}
				});
			}
		});
	},
	
	getProductQuantity: function(gridContainer, mapData) 
	{
		var skus          = jQuery.map(gridContainer.find("input[type='hidden'].sku"), function(o) {return o.value});
		var quantities    = jQuery.map(gridContainer.find("input[type='textbox'].sku-quantity"), function(o) {return o});
		
		var totalPrice = 0.0;
		var totalQuantity = 0.0;
	
		$.each(skus, function(index, skuId) 
		{ 
			var quantity = mapData[skuId];
			if (quantity != undefined)
			{
				quantities[index].value = quantity;
				totalQuantity += parseFloat(quantity);
				
				var indexPattern = "[0-9]+";
				var currentIndex = parseInt(quantities[index].id.match(indexPattern));
				
				var currentPrice = $("input[id='productPrice["+currentIndex+"]']").val();
				totalPrice += parseFloat(currentPrice) * parseInt(quantity);
			}
		});
		
		var subTotalValue = Currency.formatMoney(Number(totalPrice).toFixed(2), Currency.money_format[ACC.common.currentCurrency]);
		var avgPriceValue = 0.0;
		if (totalQuantity > 0)
		{
			avgPriceValue = Currency.formatMoney(Number(totalPrice/totalQuantity).toFixed(2), Currency.money_format[ACC.common.currentCurrency]);
		}

		gridContainer.parent().find('#quantity').html(totalQuantity);
		gridContainer.parent().find("#avgPrice").html(avgPriceValue)
		gridContainer.parent().find("#subtotal").html(subTotalValue);
		
		var $inputQuantityValue = gridContainer.parent().find('#quantityValue');
		var $inputAvgPriceValue = gridContainer.parent().find('#avgPriceValue');
		var $inputSubtotalValue = gridContainer.parent().find('#subtotalValue');

		$inputQuantityValue.val(totalQuantity);
		$inputAvgPriceValue.val(Number(totalPrice/totalQuantity).toFixed(2));
		$inputSubtotalValue.val(Number(totalPrice).toFixed(2));
	}, 
	
	coreTableActions: function(productCode, mapCodeQuantity)  
	{	
        var skuQuantityClass = '.sku-quantity';

		var quantityBefore = 0;
		var quantityAfter = 0;

		var grid = $('#grid_' + productCode);
		
		grid.on('click', skuQuantityClass, function(event) {
            $(this).select();
        });

        grid.on('focusin', skuQuantityClass, function(event) {
            quantityBefore = jQuery.trim(this.value);
            if (quantityBefore == "") {
                quantityBefore = 0;
                this.value = 0;
            }
        });

        grid.on('focusout', skuQuantityClass, function(event) {
            var indexPattern           = "[0-9]+";
            var currentIndex           = parseInt($(this).attr("id").match(indexPattern));
            var $gridGroup             = $(this).parents('.orderForm_grid_group');
            var $closestQuantityValue  = $gridGroup.find('#quantityValue');
            var $closestAvgPriceValue  = $gridGroup.find('#avgPriceValue');
            var $closestSubtotalValue  = $gridGroup.find('#subtotalValue');
            
            var currentQuantityValue   = $closestQuantityValue.val();
            var currentSubtotalValue   = $closestSubtotalValue.val();

            var currentPrice = $("input[id='productPrice["+currentIndex+"]']").val();
            var variantCode = $("input[id='cartEntries["+currentIndex+"].sku']").val();

            quantityAfter = jQuery.trim(this.value);

            if (isNaN(jQuery.trim(this.value))) {
                this.value = 0;
            }

            if (quantityAfter == "") {
                quantityAfter = 0;
                this.value = 0;
            }

            if (quantityBefore == 0) {
                $closestQuantityValue.val(parseInt(currentQuantityValue) + parseInt(quantityAfter));
                $closestSubtotalValue.val(parseFloat(currentSubtotalValue) + parseFloat(currentPrice) * parseInt(quantityAfter));
            } else {
                $closestQuantityValue.val(parseInt(currentQuantityValue) + (parseInt(quantityAfter) - parseInt(quantityBefore)));
                $closestSubtotalValue.val(parseFloat(currentSubtotalValue) + parseFloat(currentPrice) * (parseInt(quantityAfter) - parseInt(quantityBefore)));
            }

            if (parseInt($closestQuantityValue.val()) > 0) {
                $closestAvgPriceValue.val(parseFloat($closestSubtotalValue.val()) / parseInt($closestQuantityValue.val()));
            } else {
                $closestAvgPriceValue.val(0);
            }

            $closestQuantityValue.parent().find('#quantity').html($closestQuantityValue.val());
            $closestAvgPriceValue.parent().find('#avgPrice').html(ACC.productorderform.formatTotalsCurrency($closestAvgPriceValue.val()));
            $closestSubtotalValue.parent().find('#subtotal').html(ACC.productorderform.formatTotalsCurrency($closestSubtotalValue.val()));
            
            if (quantityBefore != quantityAfter)
            {
            	var method = "POST";

            	$.ajax({
            		url: ACC.config.contextPath + '/cart/update',
            		data: {productCode: variantCode, quantity: quantityAfter, entryNumber: -1},
					type: method,
					success: function(data) 
					{
						ACC.cartremoveitem.refreshCartData(data, -1, productCode, null);
						mapCodeQuantity[variantCode] = quantityAfter;
					},
					error: function(xht, textStatus, ex) 
					{
						alert("Failed to get variant matrix. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
					}
				});
            }
        });
	},
	
	refreshCartData: function(cartData, entryNum, productCode, quantity) 
	{
		// if cart is empty, we need to reload the whole page
		if (cartData.entries.length == 0)
		{
			location.reload();
		}
		else
		{
			var form;	
			var removeItem = false;
		
			if (entryNum == -1) // grouped item
			{
				var editLink = $('#QuantityProduct_' + productCode);
				form = editLink.parent();
			
				var quantity = 0;
				var entryPrice = 0;
				for (var i = 0; i < cartData.entries.length; i++)
				{
					var entry = cartData.entries[i];
					if (entry.product.code == productCode)
					{			
						quantity = entry.quantity;
						entryPrice = entry.totalPrice;
						break;
					}
				}

				if (quantity == 0)
				{
					removeItem = true;
					form.parent().parent().remove();
				}
				else
				{
					form.find(".qty").html(quantity);
					form.parent().parent().find(".total").html(entryPrice.formattedValue);
				}
			
			}
			else //ungrouped item
			{
				form = $('#updateCartForm' + entryNum);
		
				if (quantity == 0)
				{
					removeItem = true;
					form.parent().parent().remove();
				}
				else
				{
					for (var i = 0; i < cartData.entries.length; i++)
					{
						var entry = cartData.entries[i];
						if (entry.entryNumber == entryNum)
						{				
							form.find('input[name=quantity]').val(entry.quantity);
							form.parent().parent().find(".total").html(entry.totalPrice.formattedValue);
						}
					}
				}
			}
			
			// remove item, need to update other items' entry numbers
			if (removeItem === true)
			{
				$('.cartItem').each(function(index)
				{
					form = $(this).find('.quantity').children().first();
					var productCode = form.find('input[name=productCode]').val(); 

					for (var i = 0; i < cartData.entries.length; i++)
					{
						var entry = cartData.entries[i];
						if (entry.product.code == productCode)
						{				
							form.find('input[name=entryNumber]').val(entry.entryNumber);
							break;
						}
					}
				});
			}
			
			// refresh mini cart 	
			ACC.minicart.refreshMiniCartCount();
		
			$('#orderTotals').next().remove();
			$('#orderTotals').remove();
			$("#ajaxCart").html($("#cartTotalsTemplate").tmpl({data: cartData}));		
		}
	}
}


$(document).ready(function ()
{
	ACC.cartremoveitem.bindAll();
});
