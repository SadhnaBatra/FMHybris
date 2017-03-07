<%@ taglib prefix="c"           uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="product"     tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="form"        uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/desktop/form" %>
<%@ taglib prefix="order"       tagdir="/WEB-INF/tags/desktop/order"%>
<%@ taglib prefix="format"      tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fmt"         uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring"      uri="http://www.springframework.org/tags" %>
 
<div class="clearfix bgwhite setup-content" id="step-4">
	<div class="clearfix">
		<div class="reviewPlaceOrderB2C">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="lftMrgn_10">
					<h2 class="text-uppercase"><spring:theme code="checkout.page.reviewOrder.review"/> &amp; <spring:theme code="checkout.page.deliverymethod.returnToBilling"/></h2>
				</div>

				<div class="clearfix">
					<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 rgtPad_0  lftPad_0">
						<div class="subTitle text-uppercase">
							<div class="clearfix"><spring:theme code="checkout.page.reviewOrder.soldTo"/><!--<a href="#" class="pull-right fm_fnt_Blue text-capitalize">Edit</a>--></div>
						</div>
                    
						<div class="reviewFirstPanelMargin lftPad_10">
							<!-- <p class="reviewPlaceOrderBold  text-capitalize">Sold To</span></p> -->
							<p class="text-uppercase">${soldToUnit.name}</p>
							<p>${billToAddress.line1}</p>
							<p>${billToAddress.line2}</p>
							<p>${billToAddress.town},&nbsp;${billToAddress.region.isocodeShort}&nbsp;${billToAddress.postalCode}&nbsp;${billToAddress.country.isocode}</p>                
							<p>${soldToUnit.uid}</p>
						</div>

						<div class="reviewFirstPanelMargin lftPad_10 btmMrgn_30">
							<p class="reviewPlaceOrderBold text-capitalize"><spring:theme code="checkout.page.reviewOrder.custCode" text="customer code"/></p>
							<p>${soldToUnit.uid}</p>
						</div>
					</div>

					<c:if test="${cartData.fmordertype != 'pickup' || (cartData.fmordertype == 'pickup' && shippingOption !='pickup') }" >
						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 rgtPad_0 lftPad_1">
							<div class="subTitle text-uppercase">
								<div class="clearfix"><spring:theme code="checkout.page.reviewOrder.shipTo"/><!--<a href="#" class="pull-right fm_fnt_Blue text-capitalize">Edit</a>--></div>
							</div>

							<div class="reviewFirstPanelMargin lftPad_10">
								<!-- <p class="reviewPlaceOrderBold text-capitalize">Ship to</p> -->
								<p class="text-uppercase">${shipToUnit.name}</p>
								<p>${shipToAddress.line1}</p>
								<p>${shipToAddress.line2}</p>
								<p>${shipToAddress.town},&nbsp;${shipToAddress.region.isocodeShort}&nbsp;${shipToAddress.postalCode}&nbsp;${shipToAddress.country.isocode}</p>                
								<p>${shipToUnit.uid}</p>
							</div>
	                    
							<c:if test="${shipToUnit.uid != null}">
								<div class="reviewFirstPanelMargin lftPad_10 btmMrgn_30"> 
									<p class="reviewPlaceOrderBold text-capitalize"><spring:theme code="checkout.page.reviewOrder.customerCode"/></p>
									<p>${shipToUnit.uid}</p>
								</div>
							</c:if>
						</div>
					</c:if>

					<c:if test="${cartData.fmordertype == 'pickup' && shippingOption =='pickup'}">
						<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 lftPad_1">
							<div class="subTitle text-uppercase">
								<div class="clearfix"><spring:theme code="checkout.page.reviewOrder.pickUp.From"/></div>
							</div>

							<div class="reviewFirstPanelMargin lftPad_10">	
								<p class="text-uppercase">${storePickupAddress.firstName}</p>
								<p>${storePickupAddress.line1}</p>
								<p>${storePickupAddress.line2}</p>
								<p>${storePickupAddress.town}</p> 
								<p>${storePickupAddress.region.name}</p>                   
								<p>${storePickupAddress.country.name}</p>
								<p>${storePickupAddress.postalCode}</p> 
							</div>
						</div>
					</c:if>

					<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 lftPad_1  rgtPad_0">
						<div class="subTitle text-uppercase">
							<div class="clearfix"><spring:theme code="checkout.page.deliverymethod.paymentdetails"/><!--<a href="#" class="pull-right fm_fnt_Blue text-capitalize">Edit</a>--></div>
						</div>

						<div class="reviewFirstPanelMargin lftPad_10">
							<p class="reviewPlaceOrderBold  text-capitalize"><spring:theme code="checkout.page.deliverymethod.billingAddress"/></p>
							<p class="text-uppercase">${soldToUnit.name}</p>
							<p>${billToAddress.line1}</p>
							<p>${billToAddress.town},&nbsp;${billToAddress.region.name}&nbsp;${billToAddress.postalCode}&nbsp;${billToAddress.country.isocode}</p>                  
							<p>${soldToUnit.uid}</p>
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
					<div class="reviewPlaceOrderFirstPanel userTable rgtPad_0 lftPad_0">
						<c:if test="${cartData.fmordertype != 'pickup'}">
							<c:forEach items="${dc}" var="distrubtioncenter" varStatus="status">
								<c:if test="${not empty distrubtioncenter}">
									<order:shippment dc="${distrubtioncenter}" statusIndex="${status.index}" pageType="orderReview"/>
								</c:if>
							</c:forEach>
						</c:if>

						<c:if test="${cartData.fmordertype == 'pickup'}">
							<div class="panel  panel-primary panel-frm  panel-frm-filled">
								<div class="panel-heading clickable">
									<h3 class="panel-title text-uppercase"><spring:theme code="checkout.page.deliverymethod.${shippingOption}"/> ${storePickupAddress.firstName}</h3>
									<span class="pull-right shipmentMethodPanelSpan"><i class="fa fa-minus"></i></span>
								</div>

								<div class="panel-body rgtPad_0">                            
									<div class="shipmentMethodInfo">
										<label class="text-capitalize  shoppingCartLabel lftPad_10" for="location"><spring:theme code="checkout.page.deliverymethod.location"/> </label>
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
													<th class="shipmentMethodTr text-center text-capitalize"><spring:theme code="checkout.page.deliverymethod.Total"/></th>
												</tr>
											</thead>

											<tbody>
												<c:forEach items="${cartData.entries}" var="entry">
													<c:forEach items="${itemBO}" var="item">
														<c:set var="dispPartNumber" value="${item.displayPartNumber}"/>
														<c:if test="${not empty item.productFlag}" >
															<c:set var="dispPartNumber" value="${item.productFlag}${item.displayPartNumber}"/>
														</c:if>

														<c:if test="${dispPartNumber == entry.product.code}">
															<c:if test="${empty entry.distrubtionCenter}">
																<c:set var="qty" value="${entry.quantity}"/>
																<c:url value="${entry.product.url}" var="productUrl"/>
																<tr>
																	<td><product:productPrimaryImage product="${entry.product}" format="thumbnail"/></td>
																	<td>
																		<div class="prodDetail">
																			<h5><c:out value="${entry.product.name == '' ? entry.product.name : item.description}" /></h5>
																			<p>Part No:${item.displayPartNumber}</p>
																			<p>QTY:  ${entry.quantity}</p>
																		</div>
																	</td>

																	<td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.itemWeight.weight * qty}" />lbs</td>
																	<td class="text-right">
																		<!-- $<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.itemPrice.displayPrice  }" /> -->
																		<format:fmprice price="${item.itemPrice.displayPrice}"/>
																	</td>
																	<td class="text-center"><%-- ${dcentry.availableQTY} --%> ${qty}</td>
																	<td class="text-right">
																		<%-- $<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.itemPrice.displayPrice * dcentry.availableQTY } " /> --%>
																		<format:fmprice price="${item.itemPrice.displayPrice * qty}"/>
																	</td>

																	<c:set var="overallWeight" value="${overallWeight +( item.itemWeight.weight * qty)}" />
																	<c:set var="itemQtyPrices" value="${itemQtyPrices + (item.itemPrice.displayPrice * qty)}" />
																	<c:set var="itemQty" value="${itemQty + qty}" />
																	<c:set var="itemPrices" value="${itemPrices + (item.itemPrice.displayPrice)}" />
																</tr>
															</c:if>
														</c:if>
													</c:forEach>
												</c:forEach>

												<tr class="tableBottomTitle">
													<th class="shipmentMethodTr width55p text-capitalize" colspan="2"><spring:theme code="checkout.page.deliverymethod.shipmentSummary"/></th>
													<th class="shipmentMethodTr text-center text-capitalize"><spring:theme code="checkout.page.deliverymethod.weight"/></th>
													<th class="shipmentMethodTr text-right text-capitalize"><spring:theme code="checkout.page.deliverymethod.price"/></th>
													<th class="shipmentMethodTr text-center text-capitalize"><spring:theme code="checkout.page.deliverymethod.quantity"/></th>
													<th class="shipmentMethodTr text-center text-capitalize"><spring:theme code="checkout.page.deliverymethod.Total"/></th>
												</tr>

												<tr class="orderSubTotalRow">
													<td ></td>
													<td ></td>
													<td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${overallWeight}" />lbs</td>
													<td class="text-right"><format:fmprice price="${itemPrices}"/><%--$<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${itemPrices}" />  --%></td>
													<td class="text-center">${itemQty}</td>
													<td class="text-right"><format:fmprice price="${itemQtyPrices}"/> <%--$<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${itemQtyPrices}" />--%></td>
												</tr> 
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</c:if>
					</div>

					<div class="reviewPlaceOrderBtn col-lg-12">
						<!-- <button id="activate-step-5" class="btn btn-sm btn-fmDefault text-uppercase pull-right rghtMrgn_20">Place order</button> -->

						<c:url value="/checkout/multi/orderConfirmation" var="encodedUrl"/>
						<a href="${encodedUrl}" id="btn-fm-rp-placeorder" class="btn btn-sm btn-fmDefault pull-right text-uppercase checkoutPlaceorder">
							<spring:theme code="checkout.page.deliverymethod.placeorder"/>
						</a> 

						<!--<c:url value="/checkout/multi/paymentDetails" var="encodedUrl"/>-->

						<c:url value="/checkout/multi/deliverymethod" var="encodedUrl"/>
						<a href="${encodedUrl}" class="btn-fm-rp-cart btn btn-sm btn-fmDefault btn-fm-Grey text-uppercase pull-right">
							<spring:theme code="checkout.page.reviewOrder.returntoPayment"/>
						</a>  

						<!--  <a href="shoppingCart_B2C.html">
						<button id="prev-step-3" class="btn-fm-rp-cart btn btn-sm btn-fmDefault btn-fm-Grey text-uppercase pull-right"><i class="fa fa-angle-left"></i> Return to Payment Details</button> -->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
