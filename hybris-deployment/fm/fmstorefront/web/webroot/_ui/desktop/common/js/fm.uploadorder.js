ACC.uploadorder = {
	currentPath: window.location.href,
	pathName: "",
	currentCurrency: "USD",
	ajaxUrl: "USD/uploadOrder/",
	ajaxBOUrl: "USD/backOrder/",
	ajaxIOUrl: "USD/invoice/",
	ajaxOHUrl: "USD/order-status/",
	url: "",
	currentPath: window.location.href,
	pathName: "",
	$divUploadOrderStatus: $('#uploadOrderStatus'),
	$divBackOrders: $('#backOrdersBlock'),
	$divInvoiceHeader: $('#invoiceBlock'),
	$divOrderHeader: $('#orderStatusHeader'),
	$tabUploadorder: $('#uploadorder'),
	$tabBackOrders: $('#backOrders'),
	$tabInvoiceHeader: $('#invoices'),
	$tabOrderStatus: $('#orderStatus'),
	$uploadOrderFile: $('#uploadOrderFile'),
	$csrOrderUpload: $('#csrOrderUpload'),
	$formID: $('#upload'),
	$buttonUploadOrderSearch: $('#uploadOrderSearch_uo'),
	setCurrentPath: function() {
		try {
			if (ACC.uploadorder.currentPath.indexOf("iframe") != -1) {
				ACC.uploadorder.currentPath = ACC.uploadorder.currentPath.replace("iframe","");
			}

			if (ACC.uploadorder.currentPath.indexOf("/USD") != -1) {
				ACC.uploadorder.pathName = ACC.uploadorder.currentPath.substring(0, ACC.uploadorder.currentPath.lastIndexOf("/USD") + 1);
			} else if (ACC.uploadorder.currentPath.indexOf("?site") != -1) {
				ACC.uploadorder.pathName = ACC.uploadorder.currentPath.substring(0, ACC.uploadorder.currentPath.lastIndexOf("/?site") + 1)+ACC.uploadorder.currentPath.substring(ACC.uploadorder.currentPath.lastIndexOf("site=")+5,ACC.uploadorder.currentPath.length)+"/en/";
			} else {
				ACC.uploadorder.pathName = ACC.uploadorder.currentPath+"federalmogul/en/";
			}
		} catch (e){
			alert(e);
		}
	},

	bindAll: function() {
		with (ACC.uploadorder) {
			bindToUploadOrder($tabUploadorder);
			bindToBackOrders($tabBackOrders);
			bindToInvoiceHeader($tabInvoiceHeader);
			bindToOrderStatusHeader($tabOrderStatus);
			bindToCSRUploadOrder($csrOrderUpload);
		}
	},

	bindToUploadOrder: function(uploadorder) {
		uploadorder.click(function(event) {
			ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl+"orderstatus";
			ACC.uploadorder.postAJAX(ACC.uploadorder.$divUploadOrderStatus);
		});
	},
	bindToCSRUploadOrder: function(csruploadorder) {
		csruploadorder.click(function(event) {
			ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl+"csrorderstatus";
			ACC.uploadorder.postAJAX(ACC.uploadorder.$divUploadOrderStatus);
		});
	},
	bindToBackOrders: function(backorder) {
		backorder.click(function(event) {
			ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxBOUrl+"back-order";
			ACC.uploadorder.postAJAX(ACC.uploadorder.$divBackOrders);
		});
	},
	bindToInvoiceHeader: function(invoiceHeader) {
		invoiceHeader.click(function(event) {
			ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxIOUrl+"invoice-header";
			ACC.uploadorder.postAJAX(ACC.uploadorder.$divInvoiceHeader);
		});
	},
	bindToOrderStatusHeader: function(orderHeader) {
		orderHeader.click(function(event) {
			ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxOHUrl+"order-header";
			ACC.uploadorder.postAJAX(ACC.uploadorder.$divOrderHeader);
		});
	},
	postAJAX:function(div){
		openUploadOrderModal();
		//ACC.uploadorder.pathName
		$.ajax({
			url: ACC.uploadorder.Url,
			async: true,
			cache: false,
			type: 'POST',
			success: function (result) {
				closeUploadOrderModal();
				div.html(result);
				setTimeout(function () {
					ACC.uploadorder.bootstrapFn();
				}, 0);
			},
			error: function (err) {
				closeUploadOrderModal();
			}
		});
	},
};

var bootstrapFn = function() {
	ACC.uploadorder.setCurrentPath();
	ACC.uploadorder.bindAll();

	var nowTemp = new Date();
	var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate()+8, 0, 0, 0, 0);
	$('#date-picker-6, #date-picker-7').datepicker({
		flat: true,
		autoclose: true
	});

	$('#datepicker').datepicker({
		onRender: function(date) {
			return date.valueOf() < now.valueOf() ? 'disabled' : '';
		},
		flat: true,
		format:'mm-dd-yyyy',
		setValue: now,
		autoclose: true,
	}).on('changeDate', function(ev){
		$(this).datepicker('hide');
		var deliveryDate = $(this).val();
		if (deliveryDate != ''){
			var ajaxURL = ACC.uploadorder.pathName + "USD/cart/futuredelivery/"+deliveryDate;
			$.ajax({
				url: ajaxURL,
				async: true,
				cache: false,
				success: function (xmlDoc) {

				}
			});
		}
	});

	$('#datepicker').on('keypress',function(evt){
		evt.preventDefault();
	});
	$('#datepicker').change(
		function(event) {
			var rgexpDate = /^((0?[1-9]|1[012])[- /.](0?[1-9]|[12][0-9]|3[01])[- /.](19|20)?[0-9]{2})*$/;
			var deliveryDate = $(this).val();

			var dd = now.getDate();
			var mm = now.getMonth()+1;

			dd = dd < 10 ? '0' + dd : dd;

			mm = mm < 10 ? '0' + mm : mm;

			var formatedDate = mm  + "-"+ dd  + "-" + now.getFullYear();

			var isValidDate = rgexpDate.test(deliveryDate);
			if (!isValidDate) {
				$('#datepicker').val(formatedDate);
			}

			deliveryDate = $(this).val();

			if (deliveryDate != '') {
				var ajaxURL = ACC.uploadorder.pathName + "USD/cart/futuredelivery/"+deliveryDate;
				$.ajax({
					url: ajaxURL,
					async: true,
					cache: false,
					success: function (xmlDoc) {

					}
				});
			}
		}
	);
};

ACC.uploadorder.bootstrapFn = bootstrapFn;

$(document).ready(bootstrapFn);

function isNumber(evt) {
	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;

	if ((charCode > 31 && (charCode < 48 || charCode > 57) ) || charCode == 13) {
		return false;
	}
	return true;
}


function openUploadOrderModal() {
	document.getElementById('quick_modal').style.display = 'block';
	document.getElementById('quick_fade').style.display = 'block';
}

function closeUploadOrderModal() {
	document.getElementById('quick_modal').style.display = 'none';
	document.getElementById('quick_fade').style.display = 'none';
}

function editUploadOrderEntry(code){
	var trurl = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl +"uploadOrderEntry/"+code;
	var trOrderEntry = $("#EditOrderTableRow_"+code);
	$.ajax({
		url: trurl,
		async: true,
		cache: false,
		type: 'POST',
		success: function (result) {
			trOrderEntry.html(result);
		}
	});
}

function closeUploadOrder(code){
	document.getElementById('EditOrderTableRow_'+code).style.display = 'none';
}

function saveUploadOrder(code){
	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl+"uploadOrderSave/"+code;
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divUploadOrderStatus);
}
function deleteUploadOrderEntry(entryno){
	var code = entryno.split("_") ;
	var trurl = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl +"uploadOrderEntryDelete/"+code[0]+"/"+entryno;
	var trOrderEntry = $("#EditOrderTableRow_"+code[0]);
	$.ajax({
		url: trurl,
		async: true,
		cache: false,
		type: 'POST',
		success: function (result) {
			trOrderEntry.html(result);
		}
	});
}

function updateUploadOrderEntry(index,entryno){
	var code = entryno.split("_") ;
	var part = $('#part_'+index).val();
	var qty = $('#qty_'+index).val();
	var flag = $('#productFlag_'+index).val();
	var trurl = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl +"uploadOrderEntryEdit/"+code[0]+"/"+entryno+"/"+part+"/"+qty+"/"+flag;
	var trOrderEntry = $("#EditOrderTableRow_"+code[0]);
	$.ajax({
		url: trurl,
		async: true,
		cache: false,
		type: 'POST',
		success: function (result) {
			trOrderEntry.html(result);
		}
	});
}

function showUploadOrderHistory(code){
	var trurl = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl +"uploadOrderHistory/"+code;
	var trOrderHistory = $("#EditOrderHistory_"+code);
	$.ajax({
		url: trurl,
		async: true,
		cache: false,
		type: 'POST',
		success: function (result) {
			trOrderHistory.html(result);
		}
	});
}

function deleteUploadOrder(code) {
	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl+"uploadOrderDelete/"+code;
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divUploadOrderStatus);
}

function OrderSearch() {
	var ponumber = $('#pruchaseOrder_uo').val();
	var confirmationOrder = $('#confirmationOrder_uo').val();
	var sdate = $('#date-picker-6').val();
	var edate = $('#date-picker-7').val();
	var status = $('#uploadOrrderStatus').val();
	var urlString ="?ponumber="+ponumber+"&sapordernumber="+confirmationOrder+"&sdate="+sdate+"&edate="+edate+"&status="+status;
	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl+"search"+urlString;
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divUploadOrderStatus);
}

function categoryAddToCart (partnumber, name, price){
	// /category/{partNumber}
	var errMSG = $("#error_"+partnumber);
	var qty = $("#qty_"+partnumber).val();
	var isQtyNumber = true;
	ACC.track.trackAddToCart(partnumber,qty,name,price);

	if (isNaN(qty)){
		errMSG.html("Please enter the proper quantity");
		errMSG.removeClass("poerror").addClass("poerror_show");
		$("#error_"+partnumber).css('display', 'block');
		isQtyNumber = false;
	}
	if (partnumber != '' && qty != '' && isQtyNumber && qty >=1){
		var ajaxURL = ACC.uploadorder.pathName + "USD/orderupload/category/"+partnumber+"/"+qty;
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			contentType: 'text/xml',
			success: function (xmlDoc) {
				var respDoc = $(xmlDoc).find("msg").text() ;
				if (respDoc != null && respDoc.length > 1){
					if (respDoc.indexOf("multiple") != -1){
						var message = respDoc.split("@");
						respDoc = message[0];
					}
					if (respDoc.indexOf("~") != -1){
						var errMSGS = respDoc.split("~");
						errMSG.html(errMSGS[0]+"</br>"+errMSGS[1]);
					} else {
						errMSG.html(respDoc);
					}
					$("#err_"+partnumber).css('display', 'block');
					errMSG.removeClass("poerror").addClass("poerror_show");
					if ((respDoc.indexOf("multiple") != -1) || (respDoc.indexOf("superseded") != -1) || (respDoc.indexOf("matching") != -1)||((respDoc.indexOf("order quantity") != -1) && !(respDoc.indexOf("~") != -1))){
						location.href = ACC.ymmsearch.pathName +"USD/cart";
						ACC.minicart.refreshMiniCartCount();
					} else {
						var ajaxURL = ACC.uploadorder.pathName + "USD/orderupload/remove/"+partnumber;
						$.ajax({
							url: ajaxURL,
							async: true,
							cache: false,
							type: 'GET',
							success: function (xmlDoc) {
								var respDoc = $(xmlDoc).find("removePart").text().replace(/^\s+|\s+$/g, '');
								if (respDoc.indexOf("Sucess") != -1 ){
									ACC.minicart.refreshMiniCartCount();
								}
							}
						});
					}
				} else {
					location.href = ACC.ymmsearch.pathName +"USD/cart";
					ACC.minicart.refreshMiniCartCount();
				}
			},
			error: function (err) {
				errMSG.html("This part cannot be ordered. For additional information, please contact FMMP Customer Service at 800-334-3210.");
				errMSG.removeClass("poerror").addClass("poerror_show");
				$("#err_"+partnumber).css('display', 'block');
			}
		});
	}
}

function addtocart(obj, refreshParent){
	refreshParent = refreshParent || false;
	var sequenceId =obj.id.split("_") ;
	var id = "partNumber_"+sequenceId[1];
	var partnumber =$("#partNumber_"+sequenceId[1]).val();
	var hPartnumber =$("#hiddenPartNumber_"+sequenceId[1]).val();
	var qty = $("#qty_"+sequenceId[1]).val();
	var hQty = $("#hiddenQty_"+sequenceId[1]).val();
	var hPartFlag=$("#hiddenPartFlag_"+sequenceId[1]).val();
	var errMSG = $("#error_"+sequenceId[1]);
	var div = $("#div_"+sequenceId[1]);
	var isQtyNumber = true;
	if (isNaN(qty)) {
		errMSG.html("Please enter the proper quantity");
		errMSG.removeClass("poerror").addClass("poerror_show");
		$("#err_"+sequenceId[1]).css('display', 'block');
		isQtyNumber = false;
	}
	if (partnumber != '' && qty != '' && isQtyNumber && qty >=1) {
		document.getElementById('quick_modal').style.display = 'block';
		document.getElementById('quick_fade').style.display = 'block';
		if (hPartnumber === '') {
			hPartnumber = null;
		}
		if (hPartFlag === '') {
			hPartFlag = null;
		}
		var ajaxURL = ACC.uploadorder.pathName + "USD/orderupload/add/"+partnumber+"/"+qty+"/"+hPartnumber+"/"+hQty+"/"+hPartFlag;
		if (ajaxURL.indexOf("iframe") != -1) {
			ajaxURL = ajaxURL
		      .replace("iframe","");
		}
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			contentType: 'text/xml',
			success: function (xmlDoc) {
				//var respDoc = $(xmlDoc).find("msg").html();
				var respDoc = $(xmlDoc).find("msg").text() ;
				//var rawPartNumber = $(xmlDoc).find("rawPartNumber").html().trim();
				var rawPartNumber = $(xmlDoc).find("rawPartNumber").text().replace(/^\s+|\s+$/g, '') ;
				var desc = $(xmlDoc).find("itemDescription").text() ;
				var itemPrice = $(xmlDoc).find("itemPrice").text() ;
				var partFlag = $(xmlDoc).find("partFlag").text().replace(/^\s+|\s+$/g, '');
				if (rawPartNumber != ''){
					$("#partNumber_"+sequenceId[1]).val(rawPartNumber);
					$("#hiddenPartNumber_"+sequenceId[1]).val(rawPartNumber);
				} else {
					$("#hiddenPartNumber_"+sequenceId[1]).val(partnumber);
				}
				if (desc != ''){
					$("#desc_"+sequenceId[1]).html(desc);
				}
				if (itemPrice !=''){
					$("#price_"+sequenceId[1]).html(itemPrice);
				} else {
					$("#price_"+sequenceId[1]).html("N/A");
				}
				$("#hiddenQty_"+sequenceId[1]).val(qty);
				$("#hiddenPartFlag_"+sequenceId[1]).val(partFlag);
				if (respDoc != null && respDoc.length > 1){
					if (respDoc.indexOf("multiple") != -1){
						var message = respDoc.split("@");
						respDoc = message[0];
						if (message[1].indexOf("~") != -1){
							var qtymsg = message[1].split("~");
							$("#qty_"+sequenceId[1]).val(qtymsg[0]);
							$("#hiddenQty_"+sequenceId[1]).val(qtymsg[0]);
							respDoc = respDoc + "~"+qtymsg[1];
						} else {
							$("#qty_"+sequenceId[1]).val(message[1]);
							$("#hiddenQty_"+sequenceId[1]).val(message[1]);
						}
					}
					if (respDoc.indexOf("matching") != -1){
						//var multiSel = $(xmlDoc).find("multiselect").children("div") ;
						var multiSize =$(xmlDoc).find("multipartsize").text();
						$("#multiMatch_"+sequenceId[1]).empty();
						var defaultopt = '<option value="choose">Choose</option>';
						if (multiSize>0)
						{
							for ( var int = 0; int < multiSize; int++) {
								var option = $(xmlDoc).find("multiopt"+int).text().split("|");
								defaultopt += '<option value="'+option[0]+'">'+option[1]+ ' </option>';
							}
							$("#multiMatch_"+sequenceId[1]).append(defaultopt);
							$("#multi_"+sequenceId[1]).css('display', 'block');
							$("#multiMatch_"+sequenceId[1]).css('display', 'block');
						}
					} else {
						$("#multi_"+sequenceId[1]).css('display', 'none');
						$("#multiMatch_"+sequenceId[1]).css('display', 'none');
					}
					if (respDoc.indexOf("~") != -1){
						var errMSGS = respDoc.split("~");
						if (errMSGS[0] == errMSGS[1]){
							errMSG.html(errMSGS[0]);
						} else {
							errMSG.html(errMSGS[0]+"</br>"+errMSGS[1]);
						}
					} else {
						errMSG.html(respDoc);
					}
					$("#err_"+sequenceId[1]).css('display', 'block');
					//div.after(multiSel);
					errMSG.removeClass("poerror").addClass("poerror_show");
					if ((respDoc.indexOf("multiple") != -1) || (respDoc.indexOf("superseded") != -1) || (respDoc.indexOf("vintage") != -1)||(respDoc.indexOf("Part not returnable") != -1)|| (respDoc.indexOf("matching") != -1)||((respDoc.indexOf("order quantity") != -1) && !(respDoc.indexOf("~") != -1))){
						$("#partNumber_"+sequenceId[1]).css('border', '2px solid green');
						$("#QuickOrderButton0").removeClass('disabled');
						$("#QuickOrderButton1").removeClass('disabled');
						ACC.minicart.refreshMiniCartCount();
					} else {
						$("#partNumber_"+sequenceId[1]).css('border', '2px solid red');
						//Added as part of FAL-58 Fix that is disabled the proceed to check out button if none of the part is resolved correct.
						for (i=0 ;i<=sequenceId[1];i++){
							var partNumber = '';
							var errorText = '';
							var styleClassIndex=-1;
							try {
								partNumber = $("#partNumber_" + i).val();
								errorText = $('#error_' + i).text();
								styleClassIndex = $("#partNumber_" + i)[0].style.border.indexOf("green");
							} catch (err){
								var partNumber = '';
								var errorText = '';
								var styleClassIndex=-1;
							}


							if ((!(partNumber  == null || partNumber  == "") && (errorText  == ''))
								|| styleClassIndex >= 0){
								$("#QuickOrderButton0").removeClass('disabled');
								$("#QuickOrderButton1").removeClass('disabled');
								break;
							} else {
								$("#QuickOrderButton0").addClass('disabled');
								$("#QuickOrderButton1").addClass('disabled');

							}
						}
					}
					if (respDoc.indexOf("discontinued") != -1){
						$("#partNumber_"+sequenceId[1]).css('border', '2px solid red');
						//Added as part of FAL-58 Fix that is disabled the proceed to check out button if none of the part is resolved correct.
						for (i=0 ;i<=sequenceId[1];i++){
							var partNumber = '';
							var errorText = '';
							var styleClassIndex=-1;
							try {
								partNumber = $("#partNumber_" + i).val();
								errorText = $('#error_' + i).text();
								styleClassIndex = $("#partNumber_" + i)[0].style.border.indexOf("green");
							} catch (err){
								var partNumber = '';
								var errorText = '';
								var styleClassIndex=-1;
							}


							if ((!(partNumber  == null || partNumber  == "") && (errorText  == ''))
								|| styleClassIndex >= 0){
								$("#QuickOrderButton0").removeClass('disabled');
								$("#QuickOrderButton1").removeClass('disabled');
								break;
							} else {
								$("#QuickOrderButton0").addClass('disabled');
								$("#QuickOrderButton1").addClass('disabled');
							}
						}
					}

				} else {
					$("#partNumber_"+sequenceId[1]).css('border', '2px solid green');
					ACC.minicart.refreshMiniCartCount();
					errMSG.removeClass("poerror_show").addClass("poerror");
					//errMSG.css('display', 'none');
					//$("#err_"+sequenceId[1]).css('display', 'none');
					$("#QuickOrderButton0").removeClass('disabled');
					$("#QuickOrderButton1").removeClass('disabled');

				}
				document.getElementById('quick_modal').style.display = 'none';
				document.getElementById('quick_fade').style.display = 'none';

				// Send a nav refresh message to the parent window (should be triggered if we're in an iframe) so that cart on AEM nav can update...
				if (refreshParent) {
					window.parent.postMessage("hybrisCartRefresh" + new Date().getTime(), '*');
				}

			},
			error: function (err) {
				$("#partNumber_"+sequenceId[1]).css('border', '2px solid red');
				errMSG.html("This part cannot be ordered. For additional information, please contact FMMP Customer Service at 800-334-3210.");
				errMSG.removeClass("poerror").addClass("poerror_show");
				$("#err_"+sequenceId[1]).css('display', 'block');
				document.getElementById('quick_modal').style.display = 'none';
				document.getElementById('quick_fade').style.display = 'none';

				//Added as part of FAL-58 Fix that is disabled the proceed to check out button if none of the part is resolved correct.

				for (i=0 ;i<=sequenceId[1];i++){

					var partNumber = '';
					var errorText = '';
					var styleClassIndex=-1;
					try {
						partNumber = $("#partNumber_" + i).val();
						errorText = $('#error_' + i).text();
						styleClassIndex = $("#partNumber_" + i)[0].style.border.indexOf("green");
					} catch (err){
						var partNumber = '';
						var errorText = '';
						var styleClassIndex=-1;
					}


					if ((!(partNumber  == null || partNumber  == "") && (errorText  == ''))
						|| styleClassIndex >= 0){
						$("#QuickOrderButton0").removeClass('disabled');
						$("#QuickOrderButton1").removeClass('disabled');
						break;
					} else {
						$("#QuickOrderButton0").addClass('disabled');
						$("#QuickOrderButton1").addClass('disabled');

					}
				}
				//alert(err.message);
			}
		});
	} else {
		if (partnumber == '' && hPartnumber != '' ){
			var dPartNumber = hPartnumber;
			if (hPartFlag != ''){
				dPartNumber = hPartFlag.trim()+hPartnumber;
			}
			var ajaxURL = ACC.uploadorder.pathName + "USD/orderupload/remove/"+dPartNumber;
			$.ajax({
				url: ajaxURL,
				async: true,
				cache: false,
				type: 'GET',
				success: function (xmlDoc) {
					var respDoc = $(xmlDoc).find("removePart").text().replace(/^\s+|\s+$/g, '');
					if (respDoc.indexOf("Sucess") != -1 ){
						ACC.minicart.refreshMiniCartCount();
						errMSG.removeClass("poerror_show").addClass("poerror");
						$("#partNumber_"+sequenceId[1]).css('border', '1px solid #ccc');
						$("#partNumber_"+sequenceId[1]).val("");
						$("#qty_"+sequenceId[1]).val("");
						$("#hiddenQty_"+sequenceId[1]).val("0");
						$("#hiddenPartNumber_"+sequenceId[1]).val("");
						$("#hiddenPartFlag_"+sequenceId[1]).val("");

						// Send a nav refresh message to the parent window (should be triggered if we're in an iframe) so that cart on AEM nav can update...
						if (refreshParent) {
							window.parent.postMessage("hybrisCartRefresh" + new Date().getTime(), '*');
						}
					}
				}
			});
		}
	}
}

function onMultiSelect(obj){
	var partDetails = obj.value;
	var partDetails =obj.value.split("~") ;
	var id ;
	var partnumber =partDetails[0];
	var productFlag =partDetails[1];
	var brandState = partDetails[2];
	$(".partNumber").each(function () {
		if ($(this).val() == partnumber ){
			id = $(this).attr('id');
		}
	});
	if (brandState != ''){
		brandState='a';
	}
	var sequenceId =obj.id.split("_") ;
	var errMSG = $("#error_"+sequenceId[1]);
	var div = $("#div_"+sequenceId[1]);
	var qty =$("#qty_"+sequenceId[1]).val();
	var isQtyNumber = true;
	if (isNaN(qty)){
		errMSG.html("Please enter the proper quantity");
		errMSG.removeClass("poerror").addClass("poerror_show");
		$("#err_"+sequenceId[1]).css('display', 'block');
		isQtyNumber = false;
	}
	if (partnumber != '' && qty != '' && isQtyNumber && qty >=1){
		var ajaxURL = ACC.uploadorder.pathName + "USD/orderupload/addmultimatch/"+partnumber+"/"+qty+"/"+productFlag+"/"+brandState;
		document.getElementById('quick_modal').style.display = 'block';
		document.getElementById('quick_fade').style.display = 'block';
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			type: 'GET',
			success: function (xmlDoc) {
				//var respDoc = $(xmlDoc).find("msg").html();
				var respDoc = $(xmlDoc).find("msg").text() ;
				//var rawPartNumber = $(xmlDoc).find("rawPartNumber").html().trim();
				var rawPartNumber = $(xmlDoc).find("rawPartNumber").text().replace(/^\s+|\s+$/g, '') ;
				var desc = $(xmlDoc).find("itemDescription").text() ;
				var itemPrice = $(xmlDoc).find("itemPrice").text() ;
				var partFlag = $(xmlDoc).find("partFlag").text().replace(/^\s+|\s+$/g, '');
				if (rawPartNumber != ''){
					$("#partNumber_"+sequenceId[1]).val(rawPartNumber);
					$("#hiddenPartNumber_"+sequenceId[1]).val(rawPartNumber);
				} else {
					$("#hiddenPartNumber_"+sequenceId[1]).val(partnumber);
				}
				$("#hiddenPartFlag_"+sequenceId[1]).val(partFlag);
				if (desc != ''){
					$("#desc_"+sequenceId[1]).html(desc);
				}
				if (itemPrice !=''){
					$("#price_"+sequenceId[1]).html(itemPrice);
				} else {
					$("#price_"+sequenceId[1]).html("N/A");
				}
				$("#hiddenQty_"+sequenceId[1]).val(qty);
				if (respDoc != null && respDoc.length > 1){
					if (respDoc.indexOf("multiple") != -1){
						var message = respDoc.split("@");
						respDoc = message[0];
						if (message[1].indexOf("~") != -1){
							var qtymsg = message[1].split("~");
							$("#qty_"+sequenceId[1]).val(qtymsg[0]);
							$("#hiddenQty_"+sequenceId[1]).val(qtymsg[0]);
							respDoc = respDoc + "~"+qtymsg[1];
						} else {
							$("#qty_"+sequenceId[1]).val(message[1]);
							$("#hiddenQty_"+sequenceId[1]).val(message[1]);
						}
					}
					if (respDoc.indexOf("matching") != -1){
						//var multiSel = $(xmlDoc).find("multiselect").children("div") ;
						var multiSize =$(xmlDoc).find("multipartsize").text();
						var defaultopt = '<option value="choose">Choose</option>';
						if (multiSize>0)
						{
							for ( var int = 0; int < multiSize; int++) {
								var option = $(xmlDoc).find("multiopt"+int).text().split("|");
								defaultopt += '<option value="'+option[0]+'">'+option[1]+ ' </option>';
							}
							$("#multiMatch_"+sequenceId[1]).append(defaultopt);
							$("#multi_"+sequenceId[1]).css('display', 'block');
						}
					} else {
						$("#multi_"+sequenceId[1]).css('display', 'none');
						$("#multiMatch_"+sequenceId[1]).css('display', 'none');
					}
					if (respDoc.indexOf("~") != -1){
						var errMSGS = respDoc.split("~");
						if (errMSGS[0] == errMSGS[1]){
							errMSG.html(errMSGS[0]);
						} else {
							errMSG.html(errMSGS[0]+"</br>"+errMSGS[1]);
						}
					} else {
						errMSG.html(respDoc);
					}
					//div.after(multiSel);
					errMSG.removeClass("poerror").addClass("poerror_show");
					if ((respDoc.indexOf("multiple") != -1) || (respDoc.indexOf("superseded") != -1) ||(respDoc.indexOf("vintage") != -1) || (respDoc.indexOf("matching") != -1)||((respDoc.indexOf("order quantity") != -1) && !(respDoc.indexOf("~") != -1))){
						$("#partNumber_"+sequenceId[1]).css('border', '2px solid green');
						ACC.minicart.refreshMiniCartCount();
					} else {
						$("#partNumber_"+sequenceId[1]).css('border', '2px solid red');
					}
					if (respDoc.indexOf("discontinued") != -1){
						$("#partNumber_"+sequenceId[1]).css('border', '2px solid red');
					}
				} else {
					$("#partNumber_"+sequenceId[1]).css('border', '2px solid green');
					errMSG.removeClass("poerror_show").addClass("poerror");
					errMSG.css('display', 'none');
					ACC.minicart.refreshMiniCartCount();
				}
				document.getElementById('quick_modal').style.display = 'none';
				document.getElementById('quick_fade').style.display = 'none';
				$(obj).css('display', 'none');
			}
		});
	}
}


function showInvoiceDetail(ioNumber){
	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxIOUrl+"invoice-details/"+ioNumber;
	ACC.uploadorder.postAJAX($('#orderHistory'));
}

var _validFileExtensions = [".txt", ".xls",".xlsx"];

function ValidateFileExtension(oInput) {
	if (oInput.type == "file") {
		var sFileName = oInput.value;
		if (sFileName.length > 0) {
			var blnValid = false;
			for (var j = 0; j < _validFileExtensions.length; j++) {
				var sCurExtension = _validFileExtensions[j];
				if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
					blnValid = true;
					break;
				}
			}
			if (!blnValid) {
				alert("Sorry, " + sFileName + " is invalid, allowed extensions are: " + _validFileExtensions.join(", "));
				oInput.value = "";
				return false;
			}
		}
	}
	return true;
}

function addRows(count){
	//var count =$('#addrowtext').val();
	var lastInuptEle = $(".table tr:last");
	var len = $(".table tr:last ").index() + 1;
	var addcount= parseInt(len)+1;
	for (var j = 0; j <count; j++) {
		var myRow = '<tr id="tr_'+parseInt(addcount+j)+'"> <td class="nameCol itemsCol"><div class=""><input id="hiddenPartNumber_'+parseInt(addcount+j)+'" class="hiddenPartNumber form-control" type="hidden" value=""><input id="hiddenPartFlag_'+parseInt(addcount+j)+'" class="hiddenPartFlag form-control" type="hidden" value=""><input type="text" onchange="addtocart(this, true);" id="partNumber_'+parseInt(addcount+j)+'" placeholder="Enter Part Number" class="partNumber form-control visible-lg-inline visible-md-inline visible-sm-inline width165"></div><div class="searchStatus hide"><span class="fa fa-check-circle"></span></div></td><td class="text-center"><input id="hiddenQty_'+parseInt(addcount+j)+'" class="hiddenQty form-control" type="hidden" value="0"><input type="text" id="qty_'+parseInt(addcount+j)+'" onchange="addtocart(this, true);" placeholder="0"  class="form-control visible-lg-inline visible-md-inline visible-sm-inline width60"></td> <td class="descCol"><div id="desc_'+parseInt(addcount+j)+'"></div></td> <td class="text-right"><h4 class="DINWebBold"><div id="price_'+parseInt(addcount+j)+'"></div></h4></td> <td class="text-right"><div><a id="inventory_'+parseInt(addcount+j)+'" class="inventoryRow deleteRow" href="#" onclick="CheckInventory(this);"><span class="fa fa-exchange fa-fw fm_fntRed"></span></a><a id="delete_'+parseInt(addcount+j)+'" class="deleteRow" href="#" onclick="RemoveProduct(this);"><span class="fa fa-trash-o fa-fw fm_fntRed"></span></a></div></td></tr>'+'<tr><td colspan="5" class="quicOrderPoerrorTd"><div style="display: none;" id="err_'+parseInt(addcount+j)+'" class="topMargn_20 "><div class="" id="div_'+parseInt(addcount+j)+'"><div class="poerror_show topMargn_25 quicOrderPoerror_show" id="error_'+parseInt(addcount+j)+'"></div><div style="display: none;" id="multi_'+parseInt(addcount+j)+'" class="topMargn_10"><select id="multiMatch_'+parseInt(addcount+j)+'" class="form-control width165" style="display: none;"	onchange="javascript:onMultiSelect(this)"></select></div></div></div></td></tr>';
		$(".table tr:last").after(myRow);
	}
}

function addTopRows(){
	var count =$('#addrowtoptext').val();
	addRows(count);
}

function addButtomRows(){
	var count =$('#addrowbuttomtext').val();
	addRows(count);
}

function RemoveProduct(obj){
	$('#quickload').trigger('reset');
	$('#quickOrderFile').val("");

	var input = $("#quickOrderFile");
	input.replaceWith(input.val('').clone(true));

	var sequenceId =obj.id.split("_") ;
	var id = "partNumber_"+sequenceId[1];
	var partnumber =$("#partNumber_"+sequenceId[1]).val();
	var errMSG = $("#error_"+sequenceId[1]);
	var qty =  $("#qty_"+sequenceId[1]);
	var div = $("#div_"+sequenceId[1]);
	if (partnumber != ''  ){
		$("#partNumber_"+sequenceId[1]).val("");
		$("#qty_"+sequenceId[1]).val("");
		document.getElementById('quick_modal').style.display = 'block';
		document.getElementById('quick_fade').style.display = 'block';
		var ajaxURL = ACC.uploadorder.pathName + "USD/orderupload/remove/"+partnumber;
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			type: 'GET',
			success: function (xmlDoc) {
				var respDoc = $(xmlDoc).find("removePart").text().replace(/^\s+|\s+$/g, '');
				document.getElementById('quick_modal').style.display = 'none';
				document.getElementById('quick_fade').style.display = 'none';
				if (respDoc.indexOf("Sucess") != -1 ){
					ACC.minicart.refreshMiniCartCount();
					$("#tr_"+sequenceId[1]).remove();
					$("#err_"+sequenceId[1]).remove();
					//top.location.href=top.location.href;
				}
			}
		});

	}
}

function CheckInventory(obj){
	var sequenceId =obj.id.split("_") ;
	var id = "partNumber_"+sequenceId[1];
	var partnumber =$("#partNumber_"+sequenceId[1]).val();
	var partFlag=$("#hiddenPartFlag_"+sequenceId[1]).val().replace(/^\s+|\s+$/g, '');
	var qty =  $("#qty_"+sequenceId[1]).val();
	if (partnumber != ''  ){
		if (partFlag == ''){
			partFlag ='NA';
		}
		document.getElementById('quick_modal').style.display = 'block';
		document.getElementById('quick_fade').style.display = 'block';
		var ajaxURL = ACC.uploadorder.pathName + "USD/inventory/quick/"+partnumber+"/"+qty+"/"+partFlag.trim();
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			type: 'GET',
			success: function (xmlDoc) {
				$('#inventoryPopup').html(xmlDoc);
				$('#inventoryPopup').css('display', 'block');
				document.getElementById('quick_modal').style.display = 'none';
			}
		});
	}
}

function CheckPickupInventory(storeId,chkInvOnly){
	if (storeId != ''  ){
		document.getElementById('quick_modal').style.display = 'block';
		document.getElementById('quick_fade').style.display = 'block';
		var ajaxURL = ACC.uploadorder.pathName + "USD/inventory/pickup/"+storeId+"/"+chkInvOnly+"/pickup";
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			type: 'GET',
			success: function (xmlDoc) {
				if (chkInvOnly =='true'){
					$('#inventoryPopup').html(xmlDoc);
					$('#inventoryPopup').css('display', 'block');
					document.getElementById('quick_modal').style.display = 'none';
				} else {
					setTimeout(function() {
						location.href = ACC.ymmsearch.pathName+"USD/cart/checkout";
					}, 200);
				}
			}
		});
	}
}

function submitTSCPaymentForm(){
	var error_free=true;
	var element=$('input[name=p_poNumber]');
	var valid=element.hasClass("valid");
	var error_element=$("span", element.parent());
	if (!valid){error_element.removeClass("poerror").addClass("poerror_show"); error_free=false;}
	else {error_element.removeClass("poerror_show").addClass("poerror");}
	if (valid){element.removeClass("invalid").addClass("valid");}
	else {element.removeClass("valid").addClass("invalid");}

	if (!error_free){
		element.focus();
		event.preventDefault();
	} else {
		$("#paymentDetailsForm").submit();
	}

}

function setstores(storeId) {
	if (storeId != null ) {
		var ajaxURL = ACC.ymmsearch.pathName + "USD/cart-store-finder/setStore/"+storeId;
		$.ajax({
			url : ajaxURL,
			success : function(xmlDoc) {
				CheckPickupInventory(storeId,'false');
			},
			error : function(e) {
				alert("Error" + e);
			}
		});
	}
}

function CheckHomeInventory(obj,isTSCPickup){
	var partnumberList = '' ;
	for ( var int = 0; int < 3; int++) {
		var partnumber =$("#partNumber_"+int).val();
		var partFlag=$("#hiddenPartFlag_"+int).val().replace(/^\s+|\s+$/g, '');
		var qty =  $("#qty_"+int).val();
		if (partFlag == ''){
			partFlag ='NA';
		}
		if (partnumber != '' && qty !=''){
			if (int == 0){
				partnumberList = partnumber.trim()+"|"+qty.trim()+"|"+partFlag.trim()+"~";
			} else {
				partnumberList += partnumber.trim()+"|"+qty.trim()+"|"+partFlag.trim()+"~";
			}
		}
	}

	if (partnumberList != ''  ){
		document.getElementById('quick_modal').style.display = 'block';
		document.getElementById('quick_fade').style.display = 'block';
		var ajaxURL = ACC.uploadorder.pathName + "USD/inventory/home/"+partnumberList+"?TSCPickup="+isTSCPickup;
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			type: 'GET',
			success: function (xmlDoc) {
				$('#inventoryPopup').html(xmlDoc);
				$('#inventoryPopup').css('display', 'block');
				document.getElementById('quick_modal').style.display = 'none';
			}
		});
	}
}
function inventoryClose(){
	$('#inventoryPopup').css('display', 'none');
	document.getElementById('quick_fade').style.display = 'none';
}

$('.inventoryClose').click( function() {
	$('#inventoryPopup').css('display', 'none');
	document.getElementById('quick_fade').style.display = 'none';
});

$('#quick_fade').click(function() {
	$('#inventoryPopup').css('display', 'none');
	$('#brandPrefix').css('display', 'none');
	document.getElementById('quick_modal').style.display = 'none';
	document.getElementById('quick_fade').style.display = 'none';
});


function displayCarrier(obj,ind,dc,country){
	document.getElementById('carrierAccCode_'+ind).style.display = 'none';
	var ajaxURL = ACC.uploadorder.pathName + "USD/checkout/multi/carrier/"+dc;
	$.ajax({
		url: ajaxURL,
		async: true,
		cache: false,
		type: 'POST',
		success: function (xmlDoc) {
			$(obj).empty();
					//var respDoc = $(xmlDoc).find("carrier").html();
			var carrierSize =$(xmlDoc).find("carrierSize").text();
			var defaultCarrierCollect=$(xmlDoc).find("defaultCarrierCollect").text().replace(/^\s+|\s+$/g, '');
			var defaultCACarrierCollect=$(xmlDoc).find("defaultCACarrierCollect").text().replace(/^\s+|\s+$/g, '');
			var defaultopt = '<option value="Carrier">Please Select Carrier</option>';
			if (carrierSize>0)
			{
				for ( var int = 0; int < carrierSize; int++) {
					var option = $(xmlDoc).find("carrieropt"+int).text().split("-");
					if (option[1] != "FM" && option[1] != "OTH") {
						if ((country =="US") || (country =="CA" && option[1] == defaultCACarrierCollect ) ){
							defaultopt += '<option value="'+option[1].replace(/^\s+|\s+$/g, '')+'">'+option[0].replace(/^\s+|\s+$/g, '')+ ' </option>';
						}

					}
				}
			}
			$(obj).append(defaultopt);
		}
	});
}


function displayStockCarrier(obj,ind,dc){
	document.getElementById('carrierAccCode_'+ind).style.display = 'none';
	var ajaxURL = ACC.uploadorder.pathName + "USD/checkout/multi/carrier/"+dc;
	$.ajax({
		url: ajaxURL,
		async: true,
		cache: false,
		type: 'POST',
		success: function (xmlDoc) {
			$(obj).empty();
					//var respDoc = $(xmlDoc).find("carrier").html();
			var carrierSize =$(xmlDoc).find("carrierSize").text();
			var defaultopt = '<option value="Carrier">Please Select Carrier</option>';
			if (carrierSize>0)
			{
				for ( var int = 0; int < carrierSize; int++) {
					var option = $(xmlDoc).find("carrieropt"+int).text().split("-");
					if (option[1] == "OTH" || option[1] == "FM"){
						defaultopt += '<option value="'+option[1].replace(/^\s+|\s+$/g, '')+'">'+option[0].replace(/^\s+|\s+$/g, '')+ ' </option>';
					}
				}
			}
			$(obj).append(defaultopt);
		}
	});
}

function displayShippingMethod(obj,id,dc,country){
	var usedNames = {};
	var smSelect = $('#sm_'+id);
	$("#chooseCarrier_error_"+id).css('display', 'none');
	$("#chooseCarrier_"+id).css('border', '1px solid #ccc');
	var cc = $('#chooseCarrier_'+id).val();
	var ccName = $('#chooseCarrier_'+id).find("option:selected").text();
	var isCarrierCollect = false;

	if (ccName.indexOf("Collect") != -1){
		isCarrierCollect = true;
	}

	if (cc != null){
		var ajaxURL = ACC.uploadorder.pathName + "USD/checkout/multi/shipmethod/"+dc+"/"+cc+"/"+country;
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			success: function (xmlDoc) {
				smSelect.empty();
				var smSize =$(xmlDoc).find("smSize").text();
				var defaultopt = '<option value="SM">Please Select Shipping Method</option>';
				if (smSize>0)
				{
					for ( var int = 0; int < smSize; int++) {
						var option = $(xmlDoc).find("smopt"+int).text().split("-");
						if (isCarrierCollect && option[0].indexOf("Collect") != -1){
							defaultopt += '<option value="'+option[1].replace(/^\s+|\s+$/g, '')+'">'+option[0].replace(/^\s+|\s+$/g, '')+ ' </option>';
						} else if (!isCarrierCollect && option[0].indexOf("Collect") == -1){
							defaultopt += '<option value="'+option[1].replace(/^\s+|\s+$/g, '')+'">'+option[0].replace(/^\s+|\s+$/g, '')+ ' </option>';
						}
					}
				}
				smSelect.append(defaultopt);
				removeDuplicates(id);
				smSelect.removeAttr('disabled');
			}
		});
	}
}

function removeDuplicates(id){
	var usedNames = {};
	$("select[id=sm_"+id+"] > option").each(function () {
		if (usedNames[this.text]) {
			$(this).remove();
		} else {
			usedNames[this.text] = this.value;
		}
	});
}

function saveShippingMethod(obj,id,dc) {

	$("#shippingmethod_error_"+id).css('display', 'none');
	$("#sm_"+id).css('border', '1px solid #ccc');
	var sm = obj.value;
	var cc = $('#chooseCarrier_'+id).val();
	var ccName= $('#chooseCarrier_'+id).find("option:selected").text();
	var ccAccount =$('#carrierAccountChange_'+id).val();
	var ccacc =  $(obj).find("option:selected").text();

	if (ccAccount == null || ccAccount == '') {
		ccAccount ='NA';
	} else {
		if (ccAccount == 'NA') {
			ccAccount ='NA';
		}
	}

	if (cc != null && sm != null) {
		var ajaxURL = ACC.uploadorder.pathName + "USD/checkout/multi/shippingmethod/"+dc+"/"+cc+"/"+sm+"/"+ccAccount+"/"+ccName+"/"+ccacc;
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			contentType: 'text/xml',
			success: function (xmlDoc) {
				var loggedInUserType = $(xmlDoc).find("loggedInUserType").text();
				var dcCode = $(xmlDoc).find("dcCode").text();
				var totalItemPriceForDC = $(xmlDoc).find("totalItemPriceForDC").text();
				var totalFreightCostForDC = $(xmlDoc).find("totalFreightCostForDC").text();
				var totalDCValue = $(xmlDoc).find("totalDCValue").text();
				var totalItemPriceForAllDCs = $(xmlDoc).find("totalItemPriceForAllDCs").text();
				var totalFreightCostForAllDCs = $(xmlDoc).find("totalFreightCostForAllDCs").text();
				var totalOrderValue = $(xmlDoc).find("totalOrderValue").text();

				if (totalFreightCostForDC > 0) {
					$("#shipCostForDC_" + dc).html("$" + totalFreightCostForDC);
				} else {
					$("#shipCostForDC_" + dc).html("N/A");
				}

				if (loggedInUserType == "ShipTo") {
					$("#subtotalWithShipCostForDC_" + dc).html("<h5>N/A</h5>");
				} else {
					$("#subtotalWithShipCostForDC_" + dc).html("<h5>$" + totalDCValue + "</h5>");
				}

				if (totalFreightCostForAllDCs > 0) {
					$("#shipCostSummary").html("$" + totalFreightCostForAllDCs);
				} else {
					$("#shipCostSummary").html("N/A");
				}

				if (loggedInUserType == "ShipTo") {
					$("#estTotalSummary").html("N/A");
				} else {
					$("#estTotalSummary").html("$" + totalOrderValue + "**");
				}
			}
		});
	}

	if (ccacc.indexOf("Collect") != -1) {
		document.getElementById('carrierAccCode_'+id).style.display = 'block';
	} else {
		document.getElementById('carrierAccCode_'+id).style.display = 'none';
	}

}

function saveShippingMethodOnPkupChkboxClick(custPickupCheckbox, id, dc) {
	var carrierCd    = '';
	var carrierName  = '';
	var shipMthdCd   = '';
	var shipMthdName = '';

	if (custPickupCheckbox.checked) {
		carrierCd    = 'OTH';
		carrierName  = 'Other';
		shipMthdCd   = 'PKUP';
		shipMthdName = 'Pickup';
	} else {
		carrierCd    = 'UPS';
		carrierName  = 'UPS';
		shipMthdCd   = 'GRD';
		shipMthdName = 'Ground';
	}

	var carrierAcctCd = $('#carrierAccountChange_' +  id).val();

	if (carrierAcctCd == null || carrierAcctCd == '') {
		carrierAcctCd ='NA';
	} else {
		if (carrierAcctCd == 'NA') {
			carrierAcctCd ='NA';
		}
	}

	if (carrierCd != null && shipMthdCd != null) {
		var ajaxURL = ACC.uploadorder.pathName + "USD/checkout/multi/shippingmethod/" + dc + "/" + carrierCd + "/" + shipMthdCd + "/" + carrierAcctCd + "/" + carrierName + "/" + shipMthdName;
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			success: function (xmlDoc) {
				var loggedInUserType = $(xmlDoc).find("loggedInUserType").text();
				var dcCode = $(xmlDoc).find("dcCode").text();
				var totalItemPriceForDC = $(xmlDoc).find("totalItemPriceForDC").text();
				var totalFreightCostForDC = $(xmlDoc).find("totalFreightCostForDC").text();
				var totalDCValue = $(xmlDoc).find("totalDCValue").text();
				var totalItemPriceForAllDCs = $(xmlDoc).find("totalItemPriceForAllDCs").text();
				var totalFreightCostForAllDCs = $(xmlDoc).find("totalFreightCostForAllDCs").text();
				var totalOrderValue = $(xmlDoc).find("totalOrderValue").text();

				if (totalFreightCostForDC > 0) {
					$("#shipCostForDC_" + dc).html("$" + totalFreightCostForDC);
				} else {
					$("#shipCostForDC_" + dc).html("N/A");
				}

				if (loggedInUserType == "ShipTo") {
					$("#subtotalWithShipCostForDC_" + dc).html("<h5>N/A</h5>");
				} else {
					$("#subtotalWithShipCostForDC_" + dc).html("<h5>$" + totalDCValue + "</h5>");
				}


				if (totalFreightCostForAllDCs > 0) {
					$("#shipCostSummary").html("$" + totalFreightCostForAllDCs);
				} else {
					$("#shipCostSummary").html("N/A");
				}

				if (loggedInUserType == "ShipTo") {
					$("#estTotalSummary").html("N/A");
				} else {
					$("#estTotalSummary").html("$" + totalOrderValue + "**");
				}
			}
		});
	}
}

function saveCarrierAccCode(obj,id,dc){

	var cc = $('#chooseCarrier_'+id).val();
	var ccName= $('#chooseCarrier_'+id).find("option:selected").text();
	var ccAccount =obj.value;
	var sm =  $('#sm_'+id).val();
	var ccacc =  $('#sm_'+id).find("option:selected").text();
	if (ccAccount == null || ccAccount == ''){
		ccAccount ='dummy';
	} else {
		if (ccAccount == 'NA'){
			ccAccount ='dummy';
		}
	}
	if (cc != null && sm != null){
		var ajaxURL = ACC.uploadorder.pathName + "USD/checkout/multi/shippingmethod/"+dc+"/"+cc+"/"+sm+"/"+ccAccount+"/"+ccName+"/"+ccacc;
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			success: function (xmlDoc) {

			}
		});
	}
}

$('.qty').on('keypress',function(evt){
	var charCode = (evt.which) ? evt.which : event.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)){
		evt.preventDefault();     // Prevent character input
	}
});


$('.a-fm-deliverytag').on('click',function(e)
{
	var isCarrierSelected = false;
	var isShipMethodSelected = false;
	$('select[name=carrier]').each(function (a) {
		if ($(this).val() !="Carrier"){
			isCarrierSelected = true;
		} else {
			isCarrierSelected = false;
		}
	});

	$('select[name=shippingmethod]').each(function (b) {
		if ($(this).val() !="SM"){
			isShipMethodSelected = true;
		} else {
			isShipMethodSelected = false;
		}
	});

	if (!isCarrierSelected){
		e.preventDefault();
		alert("Select the carrier to continue ");
	}
	else if (!isShipMethodSelected){
		e.preventDefault();
		alert("Select the shipping method to continue ");
	}
});


function onSelectDC(obj)
{
	//nothing_DS-${cartData.code}-${entry.entryNumber}-${dccount}
	var optSelected = obj.value.split("_") ;
	var backorderPolicy = optSelected[0];
	var availDC = optSelected[1];
	if (backorderPolicy != "dc"){
		var dcDetails = availDC.split("-");
		var entrynumber = dcDetails[2];
		var dcCode= dcDetails[3];
		var dcInfo = $(obj).find("option:selected").text().split("ship from")[1];
		var dcName= dcInfo.substring(0, dcInfo.lastIndexOf("("));
		var quantity = dcInfo.split("(")[1].split("available")[0];
		var res = dcName.replace(',',', ');
		var info= '<p><b>Shipping location :</b>'+ res +' ,</p><p> <b>Available Quantity :</b> '+quantity +'</p>';
		var backOrderInfo = "</br><span class='fm_fntRed'><b>No inventory available. Only back order all available.</b></span>";
		if (backorderPolicy  == "nothing"){
			info += backOrderInfo;
		}
		//$('#s-fm-dc-ship-info-'+entrynumber).html(info);
		//$(obj).css('display', 'none');
		if (backorderPolicy  != "partial"){
			//$('#s-fm-dc-ship-info-'+entrynumber).css('display', 'block');
			addDistrubutionCenter(availDC,entrynumber,backorderPolicy);
		} else {
			//$("#sel-fm-stock-partial-"+entrynumber+"-"+dcCode).css('display', 'block');
		}
	}
}

function onPartialSelect(obj,entrynumber){
	var optSelected =obj.value.split("_") ;
	var backorderPolicy = optSelected[0];
	var availDC = optSelected[1];
	$(obj).css('display', 'none');
	$('#s-fm-dc-ship-info-'+entrynumber).css('display', 'block');
	addDistrubutionCenter(availDC,entrynumber,backorderPolicy);
}

function addDistrubutionCenter(availDC,entrynumber,backorderPolicy){
	var ajaxURL = ACC.ymmsearch.pathName + "USD/gatp/adddc/"+availDC+"/"+entrynumber+"/"+backorderPolicy;
	$.ajax({
		url: ajaxURL,
		async: true,
		cache: false,
		success: function (xmlDoc) {

		}
	});
}

function searchOrders(){

	var PO_Number = $('#pruchaseOrder_no').val();
	var confirma_Number = $('#confirmationOrder_no').val();
	var start_Date = $('#date-picker-1').val();
	var end_Date = $('#date-picker-2').val();
	var Status= $("select[id='orderStatus'] option:selected").val();



	var urlString ="?ponumber="+encodeURIComponent(PO_Number)+"&invoice="+encodeURIComponent(confirma_Number)+"&sdate="+start_Date+"&edate="+end_Date+"&status="+Status
			//alert("urlString :: "+urlString);

	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxOHUrl+"order-header"+urlString;
			//alert("ACC.uploadorder.Url :: "+ACC.uploadorder.Url);
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divOrderHeader);


}

function resetOrders(){


	$('#pruchaseOrder_no').val("");
	$('#confirmationOrder_no').val("");
	$('#date-picker-1').val("");
	$('#date-picker-2').val("");
	$('#orderStatus').prop('selectedIndex',0);


}

function searchInvoice(){

	var PO_Number = $('#invoicePruchaseOrder_iv').val();
	var confirma_Number = $('#invoiceConfirmationOrder_iv').val();
	var start_Date = $('#date-picker-4').val();
	var end_Date = $('#date-picker-5').val();
	var Status= $('#invoiceStatus').val();

	var urlString ="?ponumber="+encodeURIComponent(PO_Number)+"&invoice="+encodeURIComponent(confirma_Number)+"&sdate="+start_Date+"&edate="+end_Date+"&status="+Status
			//alert("urlString :: "+urlString);
	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxIOUrl+"invoice-header"+urlString;
			//alert("ACC.uploadorder.Url :: "+ACC.uploadorder.Url);
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divInvoiceHeader);
}

function resetInvoice(){
	$('#invoicePruchaseOrder_iv').val("");
	$('#invoiceConfirmationOrder_iv').val("");
	$('#date-picker-4').val("");
	$('#date-picker-5').val("");
	$('#invoiceStatus').prop('selectedIndex',0);
}

function searchBackOrder(){

	var PO_Number = $('#backOrderpo_no').val();
	var part_Number = $('#backOrderpart_no').val();

	var urlString ="?ponumber="+PO_Number+"&partnumber="+part_Number
			//alert("urlString :: "+urlString);

	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxBOUrl+"back-order"+urlString;
			//alert("ACC.uploadorder.Url :: "+ACC.uploadorder.Url);
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divBackOrders);
}

function resetBackOrder(){

	$('#backOrderpo_no').val("");
	$('#backOrderpart_no').val("");
}

function invoiceSortBy(sortOption){
	var urlString ="?sortBy="+sortOption;
	//alert("urlString :: "+urlString);
	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxIOUrl+"sortInvoice-Header"+urlString;
	//alert("ACC.uploadorder.Url :: "+ACC.uploadorder.Url);
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divInvoiceHeader);
}

function backOrderSortBy(sortOption){
	var urlString ="?sortBy="+sortOption;
	//alert("urlString :: "+urlString);
	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxBOUrl+"sort-Back-Order"+urlString;
	//alert("ACC.uploadorder.Url :: "+ACC.uploadorder.Url);
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divBackOrders);

}

function displayOrdersAs(pos){

	var itemsCount = 20;

	if (pos == 'up'){
		itemsCount  = $( "#orderStatusdisplayup option:selected" ).val();
	} else {
		itemsCount = $( "#orderStatusdisplaydown option:selected" ).val();
	}

	var urlString ="?itemsCount="+itemsCount
	//alert("urlString :: "+urlString);

	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxOHUrl+"orderHistory-Pagination"+urlString;
	//alert("ACC.uploadorder.Url :: "+ACC.uploadorder.Url);
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divOrderHeader);

}

function hasNext(currentPage,itemsCount,begin,end){


	var urlString ="?itemsCount="+itemsCount+"&begin="+begin+"&end="+end+"&page="+currentPage+"&moveFlag=1";
	//alert("urlString :: "+urlString);

	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxOHUrl+"orderHistory-Pagination"+urlString;
	//alert("ACC.uploadorder.Url :: "+ACC.uploadorder.Url);
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divOrderHeader);

}

function hasPrev(currentPage,itemsCount,begin,end){


	var urlString ="?itemsCount="+itemsCount+"&begin="+begin+"&end="+end+"&page="+currentPage+"&moveFlag=-1";
	//alert("urlString :: "+urlString);

	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxOHUrl+"orderHistory-Pagination"+urlString;
	//alert("ACC.uploadorder.Url :: "+ACC.uploadorder.Url);
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divOrderHeader);

}

function formateDate(id){

	if (id == 'date-picker-1'){
	 // $('#date-picker-1').attr("placeholder", "");
		$('#date-picker-1').val("");
		return false;
	} else {
		//$('#date-picker-2').attr("placeholder", "");
		$('#date-picker-2').val("");
		return false;
	}
}

function disableAltDcSelectBox(gatpDc, altDcToDisable) {
	if (gatpDc.checked) {
		document.getElementById(altDcToDisable).disabled = true;
		document.getElementById(altDcToDisable).value = "Select";
	} else {
		document.getElementById(altDcToDisable).disabled = false;
	}
}

function clearGatpDcCheckboxes(nonGatpSel, entryNumberToDisable) {
	var altDcValue = $('select#' + nonGatpSel + ' :selected').text();

	if (nonGatpSel != "Select") {
		document.getElementById(entryNumberToDisable).checked = false;
	}
}

function addGatpDc(entryNumber) {
	var ajaxURL = ACC.ymmsearch.pathName + "USD/gatp/adddc/"+$(this).val()+"/"+entryNumber+"/available";
	$.ajax({
		url: ajaxURL,
		async: false,
		cache: false,
		success: function (xmlDoc) {
			return true;
		}
	});
}

function removeGatpDc(altDcSelectTxt, gatpCheckboxId) {
	if (gatpCheckboxId.checked && altDcSelectTxt != "Select") {
		var ajaxURL = ACC.ymmsearch.pathName + "USD/gatp/removedc/"+$(this).val()+"/"+gatpCheckboxId;
		$.ajax({
			url: ajaxURL,
			async: false,
			cache: false,
			success: function (xmlDoc) {
				return true;
			}
		});
	}
}

function addAltDc(selectedAltDc, entryNumber) {
	if (selectedAltDc.indexOf("-") > -1) {
		var arr = selectedAltDc.split("-");
		var dcCode = "";
		if (arr.length > 0) {
			dcCode = arr[1];
		}
		var ajaxURL = ACC.ymmsearch.pathName + "USD/gatp/adddc/"+dcCode+"/"+entryNumber+"/available";
		$.ajax({
			url: ajaxURL,
			async: false,
			cache: false,
			success: function (xmlDoc) {
				return true;
			}
		});
	}
}

function removeAltDc(gatpCheckboxId, altDcSelect, altDcEntryNumber) {
	if (altDcSelect.value != "Select" && gatpCheckboxId.checked) {
		var ajaxURL = ACC.ymmsearch.pathName + "USD/gatp/removedc/"+$(this).val()+"/"+altDcEntryNumber;
		$.ajax({
			url: ajaxURL,
			async: false,
			cache: false,
			success: function (xmlDoc) {
				return true;
			}
		});
	}
}

function enableOrDisableCarrierShipMthdSelectBoxes(custPickupCheckbox, id) {
	if (custPickupCheckbox.checked) {
		document.getElementById('chooseCarrier_' + id).disabled = true;
		document.getElementById('chooseCarrier_' + id).value = "Carrier";
		document.getElementById('sm_' + id).disabled = true;
		document.getElementById('sm_' + id).value = "SM";
	} else {
		document.getElementById('chooseCarrier_' + id).disabled = false;
		document.getElementById('chooseCarrier_' + id).value = "UPS";
		document.getElementById('sm_' + id).disabled = false;
		document.getElementById('sm_' + id).value = "GRD";
	}
}

function validatePO(evt) {
	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if ((charCode > 47 && charCode < 58)
			|| (charCode > 64 && charCode < 91)
			|| (charCode > 96 && charCode < 123) || charCode == 0
			|| charCode == 95 || charCode == 45 || charCode == 32
			|| charCode == 8 || charCode == 9) {
		return true;
	}
	return false;
}

function GetXmlHttpObject(){var a=null;try {a=new XMLHttpRequest()} catch (b){try {a=new ActiveXObject("Msxml2.XMLHTTP")} catch (b){try {a=new ActiveXObject("Microsoft.XMLHTTP")} catch (b){alert("Your browser broke!");return false}}} return a};
