<%@ attribute name="selected" required="false" type="java.lang.String" %>

<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="product"   tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="spring"    uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms"       uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme"     tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn"        uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="form"      uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"         uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="order"     tagdir="/WEB-INF/tags/desktop/order"%>
<%@ taglib prefix="format"    tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fmt"       uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<c:if test="${customerType eq 'b2bCustomer' or customerType eq 'b2BCustomer' or customerType eq 'CSRCustomer'}">
	<div class="clearfix bgwhite setup-content" id="step-1">
</c:if>

<c:if test="${customerType eq 'b2cCustomer'}">
	<div class="clearfix bgwhite setup-content" id="step-2">
</c:if>
	
	<div class="clearfix">
		<div class="shipmentMethodB2C">
			<div class="clearfix">
				<div class="col-lg-8 col-md-8 col-sm-8 col-xs-12 shipmentMethodPanelFirst">
					<h2 class="text-uppercase"><spring:theme code="checkout.page.deliverymethod.headline"/></h2>
	            
					<!-- <h3 class="text-uppercase"><spring:theme code="checkout.page.deliverymethod.paymentdetails"/></h3> -->
					<!-- <p>Enter your payments details below</p>
					<h4 class="text-capitalize">account payment</h4> -->
					<div class="clearfix">
						<div class="col-sm-6 lftPad_0">
							<form class="">
								<div class="form-group regFormFieldGroup">
									<label for="poNumber" class=""><spring:theme code="checkout.page.deliverymethod.poNumber"/><span class="fm_fntRed"><spring:theme code="required" text="*"/></span></label>
									<input onkeypress="return validatePO(event);" type="text" class="form-control width270 ${ cartData.custponumber != null ? 'valid' : ''}" 
											placeholder="" maxlength="30" name="p_poNumber" id="poNumber" value="${ cartData.custponumber != null ? cartData.custponumber : ''}">
									<span id="err_poNumber"class="poerror"><spring:theme code="checkout.page.deliverymethod.fieldRequired"/></span>
								</div>
	
								<div class="form-group regFormFieldGroup">
									<label for="orderNote" class=""><spring:theme code="checkout.page.deliverymethod.orderNote"/></label>
									<textarea class="form-control width270 height200" name="p_orderNote" id="orderNote" maxlength="60" minheight="140">
										<c:if test="${cartData.ordercomments != null }">${ cartData.ordercomments}</c:if>
									</textarea>
									<span class="char-count"></span>
								</div>
							</form>
						</div>
					</div>
				</div>
	 
	            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 shipmentMethodPanelSecond">
					<div class="orderSummary">
						<h3 class="text-uppercase"><spring:theme code="checkout.page.deliverymethod.orderSummary"/></h3>
						<order:orderhistory/>
					</div>
					<%--
					<h5 class="">Shipping Information</h5>
					<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed elit felis, scelerisque a lorem quis, varius lobortis arcu. Phasellus ultricies erat nunc, non tristique sem placerat eu. Cras et magna ornare, ornare justo sodales, faucibus sapien. Maecenas fringilla tellus sit amet malesuada viverra. Praesent condimentum mi a dui porttitor scelerisque. Sed at arcu in sem vestibulum finibus. Nulla a feugiat augue. </p> 
					--%>
				</div>
			</div>
	
			<div class="clearfix">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 shipmentMethodPanelFirst">
					<!-- <h3 class="text-uppercase"><spring:theme code="checkout.page.deliverymethod.headline"/></h3> -->
					<!--<h4 class="text-uppercase"><span class="myCartLabel">My cart</span> <span class="myCartId">ID: 123456789</span></h4>-->
					<form>
						<c:if test="${empty pickUpStore}">          	
							<c:forEach items="${dc}" var="distrubtioncenter" varStatus="status">
								<c:if test="${not empty distrubtioncenter}" >
									<order:shippment dc="${distrubtioncenter}" statusIndex="${status.index}" pageType="deliveryMethod"/>
								</c:if>
							</c:forEach>
						</c:if>
	
						<c:if test="${not empty pickUpStore}">
							<div class="panel panel-primary panel-frm panel-frm-filled">
								<div class="panel-heading clickable">
									<h3 class="panel-title text-uppercase"><spring:theme code="checkout.page.deliverymethod.${shippingOption}"/> ${storePickupAddress.firstName}</h3>
									<span class="pull-right shipmentMethodPanelSpan"><i class="fa fa-minus"></i></span>
								</div>
	
								<div class="panel-body">                            
									<div class="shipmentMethodInfo">
										<label class="text-capitalize  shoppingCartLabel lftPad_10" for="location"><spring:theme code="checkout.page.deliverymethod.location"/></label>
										<span class="text-capitalize lftPad_20">${storePickupAddress.firstName},${storePickupAddress.line1},${storePickupAddress.town},${storePickupAddress.region.name},${storePickupAddress.postalCode}</span> 
									</div>
	
									<c:set var="overallWeight" value="0"/>
									<c:set var="itemPrices" value="0" />
									<c:set var="itemQty" value="0" />
									<c:set var="itemQtyPrices" value="0" />
									<c:set var="stockAvailable" value="false" />					   
									<c:set var="invQty" value="0" />
	
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
													<c:set var="isTSCPartFound" value="false" />
													<c:forEach items="${storeInvItemBO}" var="item">
														<c:set var="dispPartNumber" value="${item.displayPartNumber }"/>
														<c:if test="${not empty item.productFlag}">
															<c:set var="dispPartNumber" value="${item.productFlag}${item.displayPartNumber}"/>
														</c:if>
														<c:if test="${dispPartNumber == entry.product.code}">
															<c:set var="isTSCPartFound" value="true" />
															<c:if test="${empty entry.distrubtionCenter }" >
																<c:set var="qty" value="${entry.quantity}"/>
																<c:forEach items="${item.inventory}" var="inventory">
																	<c:set var="invQty" value="${ inventory.availableQty}" />
																	<c:choose>
																		<c:when test="${ inventory.availableQty > item.itemQty.requestedQuantity}">
																			<c:set var="qty" value="${item.itemQty.requestedQuantity}"/>
																		</c:when>
																		<c:otherwise>
																			<c:set var="qty" value="${inventory.availableQty}"/>
																		</c:otherwise>
																	</c:choose>
																</c:forEach>
																<c:url value="${entry.product.url}" var="productUrl"/>
																<tr>                     
																	<td><product:productPrimaryImage product="${entry.product}" format="thumbnail"/></td>
																	<td>
																		<div class="prodDetail">
																			<h5><c:out value="${entry.product.name == '' ? entry.product.name : item.description}" /></h5>
																			<p>Part No:${item.displayPartNumber}</p>
																			<p>QTY:  ${entry.quantity}</p>
																			<c:choose>
																				<c:when test="${ invQty > item.itemQty.requestedQuantity}">
																					<p id="cutoffClockgreen"><strong><spring:theme code="checkout.page.deliverymethod.InStock"/></strong></p>
																					<c:set var="stockAvailable" value="true" />
																				</c:when>
																				<c:when test="${ qty <= 0 }">
																					<p id="cutoffClockred"><strong><spring:theme code="checkout.page.deliverymethod.outofStock"/></strong></p>
																				</c:when>
																				<c:otherwise>
																					<p id="cutoffClockyellow"><strong><spring:theme code="checkout.page.deliverymethod.limitedStock"/></strong></p>
																					<c:set var="stockAvailable" value="true" />
																				</c:otherwise>
																			</c:choose>			
																		</div>
																	</td>
	
																	<td class="text-center">
																		<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.itemWeight.weight * entry.quantity}" />lbs
																	</td>
																	<td class="text-right">
																		<!-- $<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.itemPrice.displayPrice  }" /> -->
																		<c:choose>
																			<c:when test="${logedUserType ne 'ShipTo'}">
																				<format:fmprice price="${item.itemPrice.displayPrice}"/>
																			</c:when>
																			<c:otherwise>
																				<format:fmprice price="0.0"/>
																			</c:otherwise>
																		</c:choose>
																	</td>
																	<td class="text-center"><%-- ${dcentry.availableQTY} --%> ${qty}</td>
																	<td class="text-right">
																		<%-- $<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.itemPrice.displayPrice * dcentry.availableQTY } " /> --%>
																		<c:choose>
																			<c:when test="${logedUserType ne 'ShipTo'}">
																				<format:fmprice price="${item.itemPrice.displayPrice * qty}"/>
																			</c:when>
																			<c:otherwise>
																				<format:fmprice price="0.0"/>
																			</c:otherwise>
																		</c:choose>
																	</td>
																	<c:choose>
																		<c:when test="${logedUserType ne 'ShipTo'}">
																			<c:set var="overallWeight" value="${overallWeight + ( item.itemWeight.weight * qty )}" />
																			<c:set var="itemQtyPrices" value="${itemQtyPrices + (item.itemPrice.displayPrice * qty )}" />
																			<c:set var="itemQty" value="${itemQty + qty }" />
																			<c:set var="itemPrices" value="${itemPrices + (item.itemPrice.displayPrice)}" />
																		</c:when>
																		<c:otherwise>
																			<c:set var="overallWeight" value="${overallWeight + ( item.itemWeight.weight * qty )}" />
																			<c:set var="itemQtyPrices" value="0.0" />
																			<c:set var="itemQty" value="${itemQty + qty}" />
																			<c:set var="itemPrices" value="0.0" />
																		</c:otherwise>
																	</c:choose>
																</tr>
															</c:if>
														</c:if>
													</c:forEach>
	
													<c:if test="${isTSCPartFound =='false'}">
														<c:forEach items="${itemBO}" var="item">
															<c:set var="dispPartNumber" value="${item.displayPartNumber}"/>
															<c:if test="${not empty item.productFlag}" >
																<c:set var="dispPartNumber" value="${item.productFlag}${item.displayPartNumber}"/>
															</c:if>
															
															<c:if test="${dispPartNumber == entry.product.code}">
																<c:if test="${empty entry.distrubtionCenter}">
																	<c:set var="qty" value="0"/>
																	<c:url value="${entry.product.url}" var="productUrl"/>
																	<tr>                     
																		<td><product:productPrimaryImage product="${entry.product}" format="thumbnail"/></td>
																		<td>
																			<div class="prodDetail">
																				<h5> <c:out value="${entry.product.name == '' ? entry.product.name : item.description}" /></h5>
																				<p>Part No:${item.displayPartNumber}</p>
																				<p>QTY:  ${entry.quantity}</p>
																				<c:choose>
																					<c:when test="${ qty <= 0}">
																						<p id="cutoffClockred"><strong>Not Available for Pick Up</strong></p>
																					</c:when>
																					<c:otherwise>
																						<p id="cutoffClockyellow"><strong>Limited Stock Available</strong></p>
																					</c:otherwise>
																				</c:choose>			
																			</div>
																		</td>
	
																		<td class="text-center"> 
																			<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.itemWeight.weight * entry.quantity }" />lbs
																		</td>
																		<td class="text-right">
																			<!-- $<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.itemPrice.displayPrice  }" /> -->
																			<c:choose>
																				<c:when test="${logedUserType ne 'ShipTo'}">
																					<format:fmprice price="${item.itemPrice.displayPrice}"/>
																				</c:when>
																				<c:otherwise>
																					<format:fmprice price="0.0"/>
																				</c:otherwise>
																			</c:choose>
																		</td>
																		<td class="text-center"><%-- ${dcentry.availableQTY} --%> ${qty}</td>
																		<td class="text-right">
																			<%-- $<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.itemPrice.displayPrice * dcentry.availableQTY } " /> --%>
																			<c:choose>
																				<c:when test="${logedUserType ne 'ShipTo'}">
																					<format:fmprice price="${item.itemPrice.displayPrice * qty}"/>
																				</c:when>
																				<c:otherwise>
																					<format:fmprice price="0.0"/>
																				</c:otherwise>
																			</c:choose>
																		</td>
																		<c:choose>
																			<c:when test="${logedUserType ne 'ShipTo'}">
																				<c:set var="overallWeight" value="${overallWeight + ( item.itemWeight.weight * qty )}" />
																				<c:set var="itemQtyPrices" value="${itemQtyPrices + (item.itemPrice.displayPrice * qty )}" />
																				<c:set var="itemQty" value="${itemQty + qty}" />
																				<c:set var="itemPrices" value="${itemPrices + (item.itemPrice.displayPrice)}" />
																			</c:when>
																			<c:otherwise>
																				<c:set var="overallWeight" value="${overallWeight + ( item.itemWeight.weight * qty )}" />
																				<c:set var="itemQtyPrices" value="0.0" />
																				<c:set var="itemQty" value="${itemQty + qty}" />
																				<c:set var="itemPrices" value="0.0" />
																			</c:otherwise>
																		</c:choose>
																	</tr>
																</c:if>
															</c:if>
														</c:forEach>
													</c:if>
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
					</form>
				</div>
			</div>
	
			<div class="rghtMrgn_15 btmMrgn_40">
				<c:url value="https://testsecureacceptance.cybersource.com/pay" var="paymentFormUrl" />
				<form:form id="hostOrderPostForm" name="hostOrderPostForm" commandName="hopPaymentDetailsForm" class="create_update_payment_form" action="${paymentFormUrl}" method="POST">
					<c:forEach items="${hopPaymentDetailsForm.unSignedParams}" var="entry" varStatus="status">
						<input type="hidden" id="${entry.key}" name="${entry.key}" value="${entry.value}"/>
					</c:forEach>
					<c:forEach items="${hopPaymentDetailsForm.parameters}" var="entry" varStatus="status">
						<input type="hidden" id="${entry.key}" name="${entry.key}" value="${entry.value}"/>
					</c:forEach>
				</form:form>
	
				<c:if test="${customerType eq 'b2bCustomer' or customerType eq 'b2cCustomer'}">
					<button id="activate-step-2" type="submit" onClick='submitPaymentForm()' class="btn btn-sm btn-fmDefault pull-right text-uppercase">
						<spring:theme code="checkout.page.deliverymethod.continueToPaymentDetails"/>
					</button>
				</c:if>
	
				<c:if test="${customerType eq 'b2BCustomer' or customerType eq 'CSRCustomer'}">
					<c:url value="/checkout/multi/orderConfirmation" var="encodedUrl"/>
					<form id="paymentDetailsForm" class="" action="${encodedUrl}" method="post">
						<c:set var="buttonType">submit</c:set>
						<input type="hidden" id="poNumber" name="poNumber" value="${cartData.custponumber != null ? cartData.custponumber : ''}" />
						<input type="hidden" name="orderInstruction" id="h_orderIns" value="${cartData.ordercomments != null ? cartData.ordercomments : ''}"/>
					</form>				
	
					<%--<c:url value="/checkout/multi/paymentDetails" var="encodedUrl"/>--%>
					<%--
					<a href="${encodedUrl }" class="a-fm-deliverytag">
						<button id="activate-step-2" class="btn btn-sm btn-fmDefault text-uppercase" data-dismiss="modal">Continue to Review & Place Order</button>
					</a> 
					--%>
					
					<c:if test="${not empty pickUpStore && stockAvailable eq 'true'}">
						<button id="activate-step-2" data-dismiss="modal" type="${buttonType}" onclick="submitTSCPaymentForm()" class="btn btn-sm btn-fmDefault pull-right text-uppercase">
							<spring:theme code="checkout.page.deliverymethod.continueToReview"/> &amp; <spring:theme code="checkout.page.deliverymethod.placeorder"/>
						</button> 
					</c:if> 
					
					<c:if test="${empty pickUpStore}">
						<!-- 
						<button id="activate-step-2" data-dismiss="modal" type="${buttonType}" onclick="submitB2BPaymentForm()" class="btn btn-sm btn-fmDefault pull-right text-uppercase">
							<spring:theme code="checkout.page.deliverymethod.continueToReview"/> &amp; <spring:theme code="checkout.page.deliverymethod.placeorder"/>
						</button>
						 --> 
						<button id="activate-step-2" data-dismiss="modal" type="${buttonType}" onclick="submitB2BPaymentForm()" class="btn btn-sm btn-fmDefault pull-right text-uppercase">
							<spring:theme code="checkout.page.deliverymethod.placeorder"/>
						</button>
	
						<!-- 
						<c:url value="/checkout/multi/orderConfirmation" var="encodedUrl"/>
						<a href="${encodedUrl}" id="btn-fm-rp-placeorder" class="btn btn-sm btn-fmDefault pull-right text-uppercase">
							<spring:theme code="checkout.page.deliverymethod.placeorder"/>
						</a> 
						 -->
					</c:if>         
				</c:if>
				
				<c:if test="${customerType eq 'b2bCustomer' or customerType eq 'b2BCustomer' or customerType eq 'CSRCustomer'}">
					<c:url value="/cart" var="encodedUrl"/>
					<a href="${encodedUrl}">
						<button class="btn btn-sm btn-fmDefault pull-right btn-fm-Grey text-uppercase">
							<!--<i class="fa fa-angle-left"></i> -->
							<spring:theme code="checkout.page.return.cart"/>
						</button>
					</a>           
				</c:if>
	            
				<c:if test="${customerType eq 'b2cCustomer'}">
					<c:url value="/checkout/multi" var="encodedUrl"/>
					<a href="${encodedUrl}" class="btn btn-sm btn-fmDefault btn-fm-Grey text-uppercase">
						<spring:theme code="checkout.page.deliverymethod.returnToBilling"/> &amp; <spring:theme code="checkout.page.deliverymethod.returnToShipping"/>
					</a>           
				</c:if>
			</div>
		</div>
	</div>
</div>
