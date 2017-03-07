<%@ taglib prefix="c"           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="product"     tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="form"        uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/desktop/form" %>
<%@ taglib prefix="order"       tagdir="/WEB-INF/tags/desktop/order" %>
<%@ taglib prefix="format"      tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fmt"         uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"          uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring"      uri="http://www.springframework.org/tags" %>

<div class="clearfix bgwhite setup-content" id="step-5">
	<div class="col-lg-12 ">
		<ul class="printShareSaveLink pull-right">
			<spring:url value="/checkout/multi/orderConfirmationExport" var="exportExcel" />
			<!-- <li><a href="#" onclick="printDiv('printableArea')"><span class="fa fa-print"></span> Print</a></li> -->
			<li><a href="${exportExcel}" target="_BLANK"><span class="fa fa-print"></span> <spring:theme code="checkout.orderconfirmation.print"/> </a></li>
			<li><a href="${exportExcel}" target="_BLANK"><span class="fa fa-file-pdf-o orderPdf"></span> <spring:theme code="checkout.orderconfirmation.saveAsPdf"/></a></li>
			<!--   <li><a href="#" onclick="savePDF('printableArea')"><span class="fa fa-file-pdf-o orderPdf"></span> Save PDF</a></li> -->
		</ul>
	</div>

	<div class="confirmationB2C" id="printableArea">
		<div class="clearfix">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<c:if test="${orderSavedButNoDeliveryCreatedFlag eq true}">
					<div class="ConfirmationHeadingRedPanel lftPad_10 clearfix">
						<span class="shipmentMethodBold"><spring:theme code="checkout.orderConfirmation.orderSavedNoDeliveryCreatedMsg"/></span>
					</div>
				</c:if>

				<c:if test="${orderSavedButNoDeliveryCreatedFlag eq false}">
	 				<h2 class="text-uppercase"><spring:theme code="checkout.orderconfirmation.description"/></h2>
	 				<p><spring:theme code="checkout.orderconfirmation.description1"/> <strong>${orderData.user.uid}</strong></p>
					<div class="ConfirmationHeadingRedPanel lftPad_10 clearfix">
						<span>
							<span class="shipmentMethodBold"><spring:theme code="checkout.orderconfirmation.number"/> #:</span>
							${orderData.purchaseOrderNumber}
						</span>
					</div>
 				</c:if>
 
 				<div class="row">
 					<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 rgtPad_0">
 						<div class="subTitle text-uppercase">
							<div class="clearfix">
								<spring:theme code="checkout.orderconfirmation.InvoiceDetails"/>
							</div>
						</div>

						<div class="reviewFirstPanelMargin lftPad_10">
							<p class="text-uppercase">${soldToUnit.name}</p>
							<p>${billToAddress.line1}</p>
							<p>${billToAddress.line2}</p> 
							<p>${billToAddress.town},&nbsp;${billToAddress.region.isocodeShort}&nbsp;${billToAddress.postalCode}&nbsp;${billToAddress.country.isocode}</p> 
						</div>

						<div class="reviewFirstPanelMargin lftPad_10 btmMrgn_30">
 							<p class="reviewPlaceOrderBold text-capitalize"><spring:theme code="checkout.page.reviewOrder.customerCode"/></p>
							<p>${soldToUnit.uid}</p>
						</div>
					</div>
 
 					<c:if test="${cartData.fmordertype != 'pickup' || (cartData.fmordertype == 'pickup'  && shippingOption !='pickup') }">
						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 rgtPad_0 lftPad_1">
							<div class="subTitle text-uppercase">
								<div class="clearfix"><spring:theme code="checkout.orderconfirmation.shppingDetails"/><!--<a href="#" class="pull-right fm_fnt_Blue text-capitalize">Edit</a>--></div>
							</div>

							<div class="reviewFirstPanelMargin lftPad_10">
								<!-- <p class="reviewPlaceOrderBold text-capitalize">Ship to</p> -->
								<p class="text-uppercase">${shipToUnit.name}</p>
								<p>${shipToAddress.line1}</p>
								<p>${shipToAddress.line2}</p>
								<p>${shipToAddress.town},&nbsp;${shipToAddress.region.isocodeShort}&nbsp;${shipToAddress.postalCode}&nbsp;${shipToAddress.country.isocode}</p>                  
							</div>

							<c:if test="${shipToUnit.uid  !=  null}">
								<div class="reviewFirstPanelMargin lftPad_10 btmMrgn_30">
									<p class="reviewPlaceOrderBold text-capitalize"><spring:theme code="checkout.page.reviewOrder.customerCode"/></p>
									<p>${shipToUnit.uid}</p>
								</div>
							</c:if>
						</div>
 					</c:if>

					<c:if test="${cartData.fmordertype == 'pickup' && shippingOption == 'pickup'}" >
						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 rgtPad_0 lftPad_1">
							<div class="subTitle text-uppercase">
								<div class="clearfix"><spring:theme code="checkout.page.reviewOrder.pickUp.From"/></div>
							</div>

							<div class="reviewFirstPanelMargin lftPad_10">	
								<p class="text-uppercase">${storePickupAddress.firstName}</p>
								<p>${storePickupAddress.line1}</p>
								<p>${storePickupAddress.line2}</p>
								<p>${storePickupAddress.town},&nbsp;${storePickupAddress.region.isocodeShort}&nbsp;${storePickupAddress.country.isocode}&nbsp;${storePickupAddress.postalCode}</p> 
							</div>
						</div>
					</c:if>

					<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 lftPad_1">
						<div class="subTitle text-uppercase">
 							<div class="clearfix"><spring:theme code="checkout.page.deliverymethod.paymentdetails"/> <!--<a href="#" class="pull-right fm_fnt_Blue text-capitalize">Edit</a>--></div>
						</div>

						<div class="reviewFirstPanelMargin lftPad_10">
							<p class="reviewPlaceOrderBold  text-capitalize"><spring:theme code="checkout.page.deliverymethod.billingAddress"/></p>
							<p class="text-uppercase">${soldToUnit.name}</p>
							<p>${billToAddress.line1}</p>
							<p>${billToAddress.line2}</p> 
							<p>${billToAddress.town},&nbsp;${billToAddress.region.isocodeShort}&nbsp;${billToAddress.postalCode}&nbsp;${billToAddress.country.isocode}</p>
						</div>

						<c:if test="${customerType eq 'b2bCustomer' or customerType eq 'b2cCustomer'}">
							<div class="reviewFirstPanelMargin  lftPad_10">
								<p class="reviewPlaceOrderBold text-capitalize">card payment</span></p>
								<p class=" text-capitalize">${cartData.paymentInfo.cardNumber} </p>
								<p class=" text-capitalize">${cartData.paymentInfo.expiryYear}</p>
							</div>
						</c:if>

						<c:if test="${customerType eq 'b2BCustomer' or customerType eq 'CSRCustomer'}">
							<div class="reviewFirstPanelMargin lftPad_10">
								<p class="reviewPlaceOrderBold text-capitalize"><spring:theme code="checkout.page.deliverymethod.poNumber"/></p>
								<p class=" text-capitalize">${cartData.custponumber}</p>
							</div>

							<div class="reviewFirstPanelMargin lftPad_10 btmMrgn_30">
								<p class="reviewPlaceOrderBold text-capitalize"><span><spring:theme code="checkout.page.deliverymethod.orderNote"/></span></p>
								<p class="textWrap">${cartData.ordercomments}</p>
							</div>
						</c:if>
					</div>
				</div>

				<div class="clearfix">
 					<div class="reviewPlaceOrderFirstPanel userTable lftPad_0 rgtPad_0">
						<c:if test="${cartData.fmordertype != 'pickup' }">
							<c:forEach items="${dc}" var="distrubtioncenter" varStatus="status">
								<c:if test="${not empty distrubtioncenter}" >
									<order:shippment dc="${distrubtioncenter}" statusIndex="${status.index}" pageType="orderReview"/>
								</c:if>
							</c:forEach>
						</c:if>

						<c:if test="${cartData.fmordertype == 'pickup' }">
							<div class="panel  panel-primary panel-frm  panel-frm-filled">
								<div class="panel-heading clickable">
 									<h3 class="panel-title text-uppercase"><spring:theme code="checkout.page.deliverymethod.${shippingOption}"/> ${storePickupAddress.firstName }</h3>
 									<span class="pull-right shipmentMethodPanelSpan"><i class="fa fa-minus"></i></span>
 								</div>

								<div class="panel-body">                            
									<div class="shipmentMethodInfo">
 										<label class="text-capitalize  shoppingCartLabel lftPad_10" for="location"><spring:theme code="checkout.page.deliverymethod.location"/></label>
										<span class="text-capitalize lftPad_20">${storePickupAddress.firstName } ,${storePickupAddress.line1},${storePickupAddress.town},${storePickupAddress.region.name},${storePickupAddress.postalCode}  </span> 
                          	
 									</div>

									<c:set var="overallWeight" value="0"/>
									<c:set var="itemPrices" value="0" />
									<c:set var="itemQty" value="0" />
									<c:set var="itemQtyPrices" value="0" />

									<div class="table-responsive userTable">
 										<table class="table tablesorter shipmentTable">
 											<thead>
												<tr class="">
													<th class="shipmentMethodTr width55p text-capitalize" colspan="2"><spring:theme code="checkout.page.deliverymethod.itemDetails"/></th>
													<th class="shipmentMethodTr text-center text-capitalize"><spring:theme code="checkout.page.deliverymethod.weight"/></th>
													<th class="shipmentMethodTr text-center width12p text-capitalize"><spring:theme code="checkout.page.deliverymethod.price"/></th>
 													<th class="shipmentMethodTr text-center text-capitalize"><spring:theme code="checkout.page.deliverymethod.quantity"/></th>
													<th class="shipmentMethodTr text-center text-capitalize"><spring:theme code="checkout.page.deliverymethod.Subtotal"/></th>
												</tr>
 											</thead>

											<tbody>
												<c:forEach items="${cartData.entries}" var="entry">
													<c:forEach items="${itemBO}" var="item">
														<c:set var="dispPartNumber" value="${item.displayPartNumber }"/>
														<c:if test="${not empty item.productFlag }">
															<c:set var="dispPartNumber" value="${item.productFlag}${item.displayPartNumber}"/>
														</c:if>

														<c:if test="${dispPartNumber == entry.product.code}">
															<c:if test="${empty entry.distrubtionCenter }" >
																<c:set var="qty" value="${entry.quantity}"/>
																<c:url value="${entry.product.url}" var="productUrl"/>
																<tr>                     
																	<td><product:productPrimaryImage product="${entry.product}" format="thumbnail"/></td>
																	<td>
																		<div class="prodDetail">
																			<h5> <c:out value="${entry.product.name == '' ? entry.product.name : item.description}" /></h5>
																			<p>Part No:${item.displayPartNumber}</p>
																			<p>QTY:  ${entry.quantity}</p>
																		</div>
																	</td>
																	<td class="text-center"> <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.itemWeight.weight * qty }" />lbs</td>
																	<td class="text-right">
																		<format:fmprice price="${item.itemPrice.displayPrice}"/>
																	</td>
																	<td class="text-center">${qty}</td>
																	<td class="text-right">
																		<format:fmprice price="${item.itemPrice.displayPrice * qty}"/>
																	</td>

																	<c:set var="overallWeight" value="${overallWeight +( item.itemWeight.weight * qty )}" />
																	<c:set var="itemQtyPrices" value="${itemQtyPrices + (item.itemPrice.displayPrice * qty )}" />
																	<c:set var="itemQty" value="${itemQty + qty }" />
																	<c:set var="itemPrices" value="${itemPrices + (item.itemPrice.displayPrice)}" />
																</tr>
															</c:if>
														</c:if>
													</c:forEach>
												</c:forEach>

												<tr class="tableBottomTitle">
													<th class="shipmentMethodTr width55p text-capitalize" colspan="2"><spring:theme code="checkout.page.deliverymethod.itemSummary"/></th>
													<th class="shipmentMethodTr text-center text-capitalize"><spring:theme code="checkout.page.deliverymethod.weight"/></th>
													<th class="shipmentMethodTr text-right text-capitalize"><spring:theme code="checkout.page.deliverymethod.price"/></th>
													<th class="shipmentMethodTr text-center text-capitalize"><spring:theme code="checkout.page.deliverymethod.quantity"/></th>
													<th class="shipmentMethodTr text-center text-capitalize"><spring:theme code="checkout.page.deliverymethod.Subtotal"/></th>
												</tr>                      

												<tr class="orderSubTotalRow">
													<td ></td>
													<td ></td>
													<td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${overallWeight}" />lbs</td>
													<td class="text-right"><format:fmprice price="${itemPrices}"/></td>
													<td class="text-center">${itemQty}</td>
													<td class="text-right"><format:fmprice price="${itemQtyPrices}"/></td>
												</tr> 
											</tbody>
										</table>
									</div>
								</div>
 							</div>				  
						</c:if>

						<div class="table-responsive userTable">
							<table id="myTable" class="table tablesorter">
 								<tbody>
 									<tr class="reviewPlaceOrderTableTwo orderSubTotalRow">
 										<td >
 											<div class="infoDiv clearfix">
												<div class="pull-left"><span class="fa  fa-info-circle fm_fnt_Blue"></span></div>
												<div class="pull-left lftMrgn_20"><%-- <p>Youâ??ve received a Something for Something</p>
													<p>Youâ??ve received a free item for Qualifying Criteria</p> --%>
												</div>
											</div>
										</td>

										<td colspan="3">
											<order:orderhistory/>
										</td>
									</tr>
								</tbody>
 							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	function printDiv(divName) {
	     var printContents = document.getElementById(divName).innerHTML;
	     var originalContents = document.body.innerHTML;
	
	     document.body.innerHTML = printContents;
	
	     window.print();
	
	     document.body.innerHTML = originalContents;
	}
	
	var doc = new jsPDF();
	var specialElementHandlers = {
	    '#editor': function (element, renderer) {
	        return true;
	    }
	};
	
	function savePDF(divName) {
	    doc.fromHTML($('#printableArea').html(), 15, 15, {
	        'width': 170,
	            'elementHandlers': specialElementHandlers
	    });
	    doc.save('sample-file.pdf');
	}
</script>
