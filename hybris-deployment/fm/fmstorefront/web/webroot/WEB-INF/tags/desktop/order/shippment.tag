<%@ attribute name="dc"          required="true"  type="java.lang.String" %>
<%@ attribute name="pageType"    required="true"  type="java.lang.String" %>
<%@ attribute name="statusIndex" required="true"  type="java.lang.Integer" %>
<%@ attribute name="selected"    required="false" type="java.lang.String" %>

<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="fmt"          uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="federalmogul" uri="http://federalmogul.com/tld/federalmogultags" %>
<%@ taglib prefix="product"   tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="spring"       uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms"          uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme"     tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce"    uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn"           uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="form"         uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"            uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="order"     tagdir="/WEB-INF/tags/desktop/order" %>
<%@ taglib prefix="format"    tagdir="/WEB-INF/tags/shared/format" %>

<c:set var="dcCode"            value="${fn:substringBefore(dc,'-') }" />
<c:set var="dcdetails"         value="${fn:substringAfter(dc,'-') }" />
<c:set var="dcDate"            value="${fn:substringBefore(dcdetails,'_') }" />
<c:set var="dcNameAndDistance" value="${fn:substringAfter(dcdetails,'_') }" />
<c:set var="dcName"            value="${fn:substringBefore(dcNameAndDistance, '|')}" />
<c:set var="distanceStr"       value="${fn:substringAfter(dcNameAndDistance, '|')}" />
   
<%-- <c:if test="${pageType ne 'deliveryMethod' }" > --%>
	<c:forEach items="${cartData.entries}" var="entry">
		<c:forEach items="${entry.distrubtionCenter}" var="dcentry">
			<c:if test="${dcCode == dcentry.distrubtionCenterDataCode}">
				<c:set var="carrier" value="${dcentry.carrierName }" />
				<c:set var="shipmethod" value="${dcentry.shippingMethod }" />
				<c:set var="shippingMethodName" value="${dcentry.shippingMethodName }" />
				<c:set var="carrierDispName" value="${dcentry.carrierDispName }" />
				<c:set var="customcarrieracccode" value="${dcentry.carrierAccountCode }" />
				<c:choose>
					<c:when test="${fn:substring(dcCode,0,2) == '45'}">
						<c:set var="tscFlag" value="Y" />
					</c:when>
					<c:otherwise>
						<c:set var="tscFlag" value="N" />
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:forEach>
	</c:forEach>
 <%--  </c:if>  --%>
		
<div class="shipmentMethodSubHeadingFirst" id="shipmentMethodSubHeading${statusIndex}">
	<div class="panel  panel-primary panel-frm  panel-frm-filled">
		<div class="panel-heading clickable">
			<c:set var="string1" value="${dcName}"/>
			<c:set var="string2" value="${fn:replace(string1,',',', ')}" />
			
			<h3 class="panel-title text-uppercase">Shipment From : ${string2 } 
				<c:if test="${tscFlag eq 'Y' and distanceStr ne 'DistanceNotAvailable'}">&nbsp;&nbsp;(<c:out value="${distanceStr}" /> miles)</c:if>
			</h3>
			<span class="pull-right shipmentMethodPanelSpan"><i class="fa fa-minus"></i></span>
		</div>
 
		<c:if test="${pageType == 'deliveryMethod'}">
			<c:if test="${tscFlag eq 'Y' and distanceStr ne 'DistanceNotAvailable'}">
				<%--*** Convert Distance from String to Number for comparison - BEGIN: *** --%>
				<c:set var="distanceNbr" value="${distanceStr}" />
				<%--*** Convert Distance from String to Number for comparison - END *** --%>
				<c:if test="${distanceNbr <= 100.00}">
					<div class="panel-body rgtPad_0">
						<label style="padding-top: 25px" class="text-capitalize">
							<input type="checkbox" class="customerPickup" id="CustPkupChkbx_${statusIndex}" name="customerPickup" value="" 
									onclick="enableOrDisableCarrierShipMthdSelectBoxes(this, ${statusIndex});saveShippingMethodOnPkupChkboxClick(this, ${statusIndex}, '${dcCode}');">
								Customer Pickup
						</label>
					</div>
				</c:if>
			</c:if>
		</c:if>
 
 		<div class="panel-body rgtPad_0" style="">
			<div class="chooseShipMethod clearfix">
				<c:if test="${customerType ne 'b2BCustomer' && customerType ne 'CSRCustomer'}">
					<div class="col-md-4"><span class="shipmentPrice ">Estimated Shipping Price:</span> ECC Service</div>
                     	
					<div class="col-md-4"><span class="shipmentPlace ">Shipped From:</span><c:if test="${ fn:contains(dc ,'pdc')}" >Primary DC</c:if> <c:if test="${!fn:contains(dc ,'pdc')}" >${dcName}</c:if> </div> 
					<div class="col-md-4"><label class="shipmentPlace ">Estimated Shipping Date:</label><div>${dcDate} </div></div>
 				</c:if>
			</div>

			<div class="form-group chooseShipMethod clearfix">
				<div class="col-md-3 lftPad_0 width175">
					<c:choose>
						<c:when test="${pageType == 'deliveryMethod'}">
							<label class="text-capitalize" for="chooseCarrier">Carrier<!--<span class="fm_fntRed">*</span>--></label>
							<c:if test="${cartData.fmordertype != 'Stock'}">
								<select id="chooseCarrier_${statusIndex}"  name="carrier" class="form-control" 
										onfocus="javascript:displayCarrier(this,${statusIndex},'${dcCode}','${cartData.deliveryAddress.country.isocode}');" 
										onChange="javascript:displayShippingMethod(this,${statusIndex},'${dcCode}','${cartData.deliveryAddress.country.isocode}');">
									<option value="Carrier">Select Carrier</option>
									<c:if test="${not empty carrierDispName}">
										<option value="${carrier}" selected="selected">${carrierDispName}</option>
									</c:if>
								</select>

								<div class="Carrier-error-msg topMargn_15" id="carrier-error-msg-${statusIndex}">
									<div class="poerror_show " id="chooseCarrier_error_${statusIndex}" style="display: none">Please select the carrier</div>	
								</div>
							</c:if>

							<c:if test="${cartData.fmordertype == 'Stock'}">
								<select id="chooseCarrier_${statusIndex}"  name="carrier" class="form-control" onfocus="javascript:displayStockCarrier(this,${statusIndex},'${dcCode}');" 
										onChange="javascript:displayShippingMethod(this,${statusIndex},'${dcCode}','${cartData.deliveryAddress.country.isocode}');">
									<%--<option selected="selected" value="FM">FM Specified Carrier</option>  --%>
									<option value="Carrier">Select Carrier</option>
									<c:if test="${not empty carrierDispName}">
										<option value="${carrier}" selected="selected">${carrierDispName}</option>
									</c:if>
								</select>

								<div class="Carrier-error-msg topMargn_15" id="carrier-error-msg-${statusIndex}">
									<div class="poerror_show " id="chooseCarrier_error_${statusIndex}" style="display: none">Please select the Carrier</div>	
								</div>
							</c:if>
						</c:when>

						<c:when test="${pageType == 'orderReview'}">
							<c:choose>
								<c:when test="${shippingMethodName == 'Pickup'}">
									<label class="text-capitalize">shipping method</label>
									<div>Customer Pickup</div>
								</c:when>
								<c:otherwise>
									<label class="text-capitalize">carrier</label>
									<div>${carrierDispName}</div>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<label class="text-capitalize">carrier</label>
							<div>${carrierDispName}</div>
						</c:otherwise>
					</c:choose>
				</div>

				<div class="col-md-5">
					<c:choose>
						<c:when test="${pageType == 'deliveryMethod'}" >
							<label class="text-capitalize" for="chooseShipMethod">Shipping method</label>
							<select id="sm_${statusIndex}" name="shippingmethod" class="form-control" 
									onfocus="javascript:displayShippingMethod(this,${statusIndex},'${dcCode }','${cartData.deliveryAddress.country.isocode }');" 
									onChange="javascript:saveShippingMethod(this,${statusIndex},'${dcCode}');">
								<option value="SM">Select Shipping Method</option>
								<c:if test="${not empty shippingMethodName}"> 
									<option value="${shipmethod}" selected="selected">${shippingMethodName}</option>
								</c:if>
							</select>

						    <div class="col-xs-12 topMargn_15 lftMrgn_12" id="div_1">
								<div class="poerror_show" id="shippingmethod_error_${statusIndex}" style="display: none">Please select the Shipping Method</div>
							</div>
						</c:when>

						<c:when test="${pageType == 'orderReview'}">
							<c:if test="${shippingMethodName != 'Pickup'}">
								<label class="text-capitalize">shipping method</label>
								<div>${shippingMethodName}</div>
							</c:if>
						</c:when>
						<c:otherwise>
							<label class="text-capitalize">shipping method</label>
							<div>${shippingMethodName}</div>
						</c:otherwise>
					</c:choose>
				</div>

				<div class="col-md-4 ">
					<c:choose>
						<c:when test="${pageType == 'deliveryMethod'}">
							<div id="carrierAccCode_${statusIndex }" style="${fn:contains(carrierDispName, 'Collect') ? 'display:block' : 'display:none'}">	                    			
								<label for="carrierAccCode" class="text-capitalize">Carrier Account Code</label>
								<c:if test="${not empty carrierAccCode}" >	                    			
									<input type="text" id="carrierAccountChange_${statusIndex}" disabled="disabled" onChange="javascript:saveCarrierAccCode(this,${statusIndex},'${dcCode}');" 
											value="${carrierAccCode}" class="carrierAccountChange form-control">	                    			
								</c:if>

								<c:if test="${empty carrierAccCode}">	   
									<input type="text" id="carrierAccountChange_${statusIndex}" value="NA" disabled="disabled" 
										onChange="javascript:saveCarrierAccCode(this,${statusIndex},'${dcCode}');" class="carrierAccountChange form-control">	                    			
								</c:if>
							</div>
						</c:when>

						<c:when test="${pageType == 'orderReview'}">
							<c:if test="${fn:contains(carrierDispName, 'Collect')}">
								<label class="text-capitalize ">Carrier Account Code</label>
								<c:if test="${not empty customcarrieracccode}">
									<div>${customcarrieracccode}</div>
								</c:if>
								<c:if test="${empty customcarrieracccode}">
	                    			<div>NA</div>                  			
	                    		</c:if>
							</c:if>
						</c:when>
						<c:otherwise>
							<c:if test="${fn:contains(carrierDispName, 'Collect')}">
								<label class="text-capitalize ">Carrier Account Code</label>
								<c:if test="${not empty customcarrieracccode}">
									<div>${customcarrieracccode}</div>
								</c:if>
								<c:if test="${empty customcarrieracccode}">
									<div>NA</div>                  			
								</c:if>
							</c:if>
						</c:otherwise>
					</c:choose>
				</div>
			</div>                    

			<div class="table-responsive userTable"><!-- id="shipmentTable"-->
				<table class="table tablesorter shipmentTable">
					<thead>
						<tr class="">
							<th class="shipmentMethodTr width55p text-capitalize" colspan="2">Item details</th>
							<th class="shipmentMethodTr text-center text-capitalize">Weight</th>
							<th class="shipmentMethodTr text-right text-capitalize">Price</th>
							<th class="shipmentMethodTr text-center text-capitalize">Quantity</th>
							<th class="shipmentMethodTr text-center text-capitalize">Subotal</th>
						</tr>
					</thead>

					<tbody>
						<c:set var="overallWeight" value="0"/>
						<c:set var="itemPrices" value="0" />
						<c:set var="itemQty" value="0" />
						<c:set var="itemQtyPrices" value="0" />
						<c:set var="freightCostForDC" value="0" />
	                   
						<c:forEach items="${cartData.entries}" var="entry">
							<!--  >>>>>><p>in shipment tag 1...</p> -->
							<c:forEach items="${itemBO}" var="item">
								<c:set var="dispPartNumber" value="${item.displayPartNumber }"/>
								<c:if test="${not empty item.productFlag }" >
									<c:set var="dispPartNumber" value="${item.productFlag}${item.displayPartNumber}"/>
								</c:if>

	                    		<c:if test="${dispPartNumber == entry.product.code}" >
									<c:if test="${not empty entry.distrubtionCenter && !fn:contains(dc ,'pdc') }" >
										<c:forEach items="${entry.distrubtionCenter}" var="dcentry">
											<fmt:formatDate value="${dcentry.availableDate }" pattern="MM/dd/yyyy" var="dcItemDate"/>
											<c:if test="${dcCode == dcentry.distrubtionCenterDataCode}">
												<c:set var="qty" value="${entry.quantity}"/>
												<c:set var="freightCostForDC" value="${dcentry.freightCost}" />
												<c:choose>
													<c:when test="${dcentry.backorderFlag =='nothing'}">
														<c:set var="qty" value="${dcentry.backorderQTYAll}"/>
													</c:when>
													<c:when test="${dcentry.backorderFlag == 'partial'}">
														<c:set var="qty" value="${(dcentry.availableQTY + dcentry.backorderQTY)}"/>
													</c:when>
													<c:otherwise>
														<c:set var="qty" value="${entry.quantity}"/>
													</c:otherwise>
												</c:choose>

												<tr>                     
													<td><product:productPrimaryImage product="${entry.product}" format="thumbnail"/></td>
													<td>
														<div class="prodDetail">
															<h5><c:out value="${entry.product.name != '' ? entry.product.name : item.description}" /></h5>
															<p>Part No:&nbsp;${item.displayPartNumber}</p>
															<p>QTY:&nbsp;${entry.quantity}</p>
														</div>
													</td>
													<td class="text-center"> <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.itemWeight.weight * qty}" />lbs</td>
													<td class="text-right">
														<c:choose>
															<c:when test="${logedUserType ne 'ShipTo'}">
																<format:fmprice price="${item.itemPrice.displayPrice}"/>
															</c:when>
															<c:otherwise>
																<format:fmprice price="0.0"/>
															</c:otherwise>
														</c:choose>
													</td>
							                        <td class="text-center">${qty}</td>
													<td class="text-center">
														<c:choose>
															<c:when test="${logedUserType ne 'ShipTo'}">
																<format:fmprice price="${item.itemPrice.displayPrice * qty}"/>
															</c:when>
															<c:otherwise>
																<format:fmprice price="0.0"/>
															</c:otherwise>
														</c:choose>
													</td>
													<c:set var="overallWeight" value="${overallWeight +( item.itemWeight.weight * qty )}" />
													<c:choose>
														<c:when test="${logedUserType eq 'ShipTo'}">
															<c:set var="itemQtyPrices" value="0.0" />				                    				
														</c:when>
														<c:otherwise>
			                    							<c:set var="itemQtyPrices" value="${itemQtyPrices + (item.itemPrice.displayPrice * qty )}" />
														</c:otherwise>
													</c:choose>
													<c:set var="itemQty" value="${itemQty + qty }" />
													<c:choose>
														<c:when test="${logedUserType eq 'ShipTo'}">
															<c:set var="itemPrices" value="0.0" />				                    				
														</c:when>
														<c:otherwise>
															<c:set var="itemPrices" value="${itemPrices + (item.itemPrice.displayPrice)}" />
														</c:otherwise>
													</c:choose>
												</tr>
											</c:if>
										</c:forEach>
									</c:if>

			                      	<c:if test="${empty entry.distrubtionCenter }" >
										<c:if test="${fn:contains(dc ,'pdc')}">
											<c:url value="${entry.product.url}" var="productUrl"/>
											<tr>                     
												<td><product:productPrimaryImage product="${entry.product}" format="thumbnail"/></td>
												<td>
													<div class="prodDetail">
														<h5> <c:out value="${entry.product.name == '' ? entry.product.name : item.description}" /></h5>
														<p>Part No:&nbsp;${item.displayPartNumber}</p>
														<p>QTY:&nbsp;${entry.quantity}</p>
													</div>
												</td>
												<td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.itemWeight.weight * entry.quantity }" /> lbs</td>
												<c:choose>
													<c:when test="${logedUserType ne 'ShipTo'}">
														<td class="text-right">$<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${item.itemPrice.displayPrice }" /></td>
													</c:when>
													<c:otherwise>
														<td class="text-right"><spring:theme code="text.free" text="N/A"/></td>
													</c:otherwise>
												</c:choose>
												<td class="text-center"><%-- ${dcentry.availableQTY} --%> ${entry.quantity}</td>
												<c:choose>
													<c:when test="${logedUserType ne 'ShipTo'}">
														<td class="text-right">$<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${entry.totalPrice}" /></td>
													</c:when>
													<c:otherwise>
														<td class="text-right"><spring:theme code="text.free" text="N/A"/></td>
													</c:otherwise>
												</c:choose>
												<c:set var="overallWeight" value="${overallWeight +( item.itemWeight.weight * entry.quantity )}" />
												<c:choose>
													<c:when test="${logedUserType ne 'ShipTo'}">
														<c:set var="itemQtyPrices" value="${itemQtyPrices + (item.itemPrice.displayPrice * entry.quantity )}" />
													</c:when>
													<c:otherwise>
														<c:set var="itemQtyPrices" value="0.0"/>
													</c:otherwise>
												</c:choose>
												<c:set var="itemQty" value="${itemQty + entry.quantity }" />
												<c:choose>
													<c:when test="${logedUserType ne 'ShipTo'}">
														<c:set var="itemPrices" value="${itemPrices + (item.itemPrice.displayPrice)}" />
													</c:when>
													<c:otherwise>
														<c:set var="itemPrices" value="0.0" />
													</c:otherwise>
												</c:choose>
											</tr>
										</c:if>
									</c:if>
								</c:if>
							</c:forEach>
						</c:forEach>

						<tr class="tableBottomTitle">
							<th class="shipmentMethodTr width55p text-capitalize" colspan="2">Item Summary</th>
							<th class="shipmentMethodTr text-center text-capitalize">Weight</th>
							<th class="shipmentMethodTr text-right text-capitalize">Price</th>
							<th class="shipmentMethodTr text-center text-capitalize">Quantity</th>
							<th class="shipmentMethodTr text-center text-capitalize">Subtotal</th>
						</tr>                      

						<tr class="orderSubTotalRow">
							<td ></td>
							<td ></td>
							<td class="text-center"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${overallWeight}" />lbs</td>
							<td class="text-right"><format:fmprice price="${itemPrices}"/></td>
							<td class="text-center">${itemQty}</td>
							<td class="text-center"><format:fmprice price="${itemQtyPrices}"/></td>
						</tr>
						
						<tr class="orderSubTotalRow">
							<td colspan="5" class="text-right text-capitalize"><div><h5>Shipping Cost:</h5></div></td>
							<td class="text-center"><div id="shipCostForDC_${dcCode}"><format:fmprice price="${freightCostForDC}"/></div></td>
						</tr>

						<tr class="orderSubTotalRow">
							<th colspan="5" class="shipmentMethodTr text-right"><div><h5>Subtotal with Shipping Cost:</h5></div></th>
							<th class="shipmentMethodTr text-center"><div id="subtotalWithShipCostForDC_${dcCode}"><h5><format:fmprice price="${itemQtyPrices + freightCostForDC}"/></h5></div></th>
						</tr>

					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
