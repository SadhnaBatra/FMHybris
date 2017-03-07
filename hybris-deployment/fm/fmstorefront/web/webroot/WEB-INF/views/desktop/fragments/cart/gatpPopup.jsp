<%@ page trimDirectiveWhitespaces="true"%>

<%@ taglib prefix="c"        uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="spring"   uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms"      uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"       uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="theme"    tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="product"  tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="format"   tagdir="/WEB-INF/tags/shared/format"%>

<spring:theme code="text.addToCart"      var="addToCartText"/>
<spring:theme code="text.popupCartTitle" var="popupCartTitleText"/>

<c:url value="/cart" var="cartUrl"/>
<c:url value="/checkout/multi/deliverymethod" var="checkoutUrl"><c:param name="isEmergency" value="true"/></c:url>
<c:url value="/gatp" var="gatpUrl"/>

<script type="text/javascript" src="${commonResourcePath}/js/fm.common.js"></script>
<script type="text/javascript" src="${commonResourcePath}/js/fm.uploadorder.js"></script>

<button type="button" class="close" onclick="inventoryClose()"><span aria-hidden="true" class="fa fa-close"></span><span class="sr-only">Close</span></button>
    
<h2 class="panel-title text-uppercase quickOrderTitle"><span class="fa fa-exchange"></span> <span class="text-uppercase">Inventory check</span></h2>

<c:set var="partCount" value="${fn:length(cartData.entries)}" />
<%-- Part Count: <c:out value="${partCount}" /><br>--%>
<c:choose>
	<c:when test="${partCount == 1}">
		<c:set var="inventoryCountForTheOnlyPart" value="${fn:length(inventory[0].inventory)}" />
		<%-- Inventory Count for the only Part: <c:out value="${inventoryCountForTheOnlyPart}" /><br>--%>
		<c:set var="availQtyForTheOnlyPartStr" value="${inventory[0].inventory[0].availableQty}" />
		<%-- Available Qty for the only Part (String): <c:out value="${availQtyForTheOnlyPartStr}" /><br>--%>
		<fmt:parseNumber value="${availQtyForTheOnlyPartStr}" integerOnly="true" var="availQtyForTheOnlyPartInt" />
		<%-- Available Qty for the only Part (int): <c:out value="${availQtyForTheOnlyPartInt}" /><br>--%>
		<c:choose>
			<c:when test="${inventoryCountForTheOnlyPart == 1 && availQtyForTheOnlyPartInt == 0}">
				<c:set var="disableEmergencyCheckoutButton" value="true" />
			</c:when>
			<c:otherwise>
				<c:set var="disableEmergencyCheckoutButton" value="false" />
			</c:otherwise>
		</c:choose>
	</c:when>

	<c:otherwise>
		<c:set var="disableEmergencyCheckoutButton" value="false" />
	</c:otherwise>
</c:choose>
<%-- Disable Emergency Checkout button? <c:out value="${disableEmergencyCheckoutButton}" /><br>--%>
<c:choose>
	<c:when test="${disableEmergencyCheckoutButton == true}">
		<c:set var="disableClassToUse" value="disabled" />
	</c:when>

	<c:otherwise>
		<c:set var="disableClassToUse" value="" />
	</c:otherwise>
</c:choose>

<div style="margin: 15px 0">
	<a target="_parent" href="${cartUrl}" class="a-fm-cart"><button id="bth-Regular" class="btn btn-fmDefault pull-right shoppingCartCheckOut"><spring:theme code="checkout.regular" /></button></a>
	<a target="_parent" href="${checkoutUrl}" class="a-fm-cart"><button id="bth-Emergency" class="btn btn-fmDefault pull-right shoppingCartCheckOut ${disableClassToUse}"><spring:theme code="checkout.emergency" /></button></a>
	<br>
</div>

<c:if test="${empty inventory && not empty ErrorMessage}">
	<div class="cart_modal_popup empty-popup-cart">
		<span style="color:red">${ErrorMessage} </span>
	</div>
</c:if>

<c:if test="${not empty inventory}">

	<div class="table-responsive col-lg-12 userTable">
		<table id="myTable" class="table tablesorter" >
			<thead>
			  <tr>
			    <th class="text-capitalize">Part Number</th>
			    <th class="text-capitalize"> <span style="width: 50%">Location</span><!-- <span style="width: 25%">Availability</span> --></th>
			  </tr>
			</thead>

			<tbody">
				<%-- 
				<c:forEach items="${inventory}" var="tempItem">
					Part #: <c:out value="${tempItem.displayPartNumber}" /><br><br>
				    <c:set var="dcWithQtyGTZeroPresnt" value="false" />
				    
				    <c:forEach items="${tempItem.inventory}" var="tempInventory">
				    	DC Name: <c:out value="${tempInventory.distributionCenter.name}" /><br>
				    	Available Qty: <c:out value="${tempInventory.availableQty}" /><br>
						<fmt:parseNumber value="${tempInventory.availableQty}" integerOnly="true" var="tempAvailQtyInt" />
						tempAvailQtyInt: <c:out value="${tempAvailQtyInt}" /><br>
				    	<c:if test="${tempAvailQtyInt > 0}">
			    			<c:set var="dcWithQtyGTZeroPresnt" value="true" />
				    	</c:if>
				    	<c:out value="----------------" /><br>
				    </c:forEach>
				    DC with Qty > 0 present? <c:out value="${dcWithQtyGTZeroPresnt}" /><br>
				    	<c:out value="================" /><br>
				</c:forEach>
				--%>

				<c:set var="entryNumber" value="0" />

				<c:forEach items="${inventory}" var="item">
				    <c:set var="dccount" value="0" />

				    <c:set var="dcWithQtyGTZeroPresent" value="false" />
				    <c:forEach items="${item.inventory}" var="tempInvntry">
						<fmt:parseNumber value="${tempInvntry.availableQty}" integerOnly="true" var="availQtyInt" />
				    	<c:if test="${availQtyInt > 0}">
			    			<c:set var="dcWithQtyGTZeroPresent" value="true" />
				    	</c:if>
			    	</c:forEach>

				    <tr>
						<td>${item.displayPartNumber}</td>
				   
						<c:forEach items="${cartData.entries}" var="entry">
							<c:if test="${item.displayPartNumber == entry.product.code || item.displayPartNumber == entry.product.rawPartNumber}">
								<c:set var="entryNumber" value="${entry.entryNumber}"/>
							</c:if>
						</c:forEach>

						<td>
							<c:choose>
								<c:when test="${dcWithQtyGTZeroPresent == true}">
									<select class="form-control" id="sel-fm-stock-dc-${entryNumber}" onchange="onSelectDC(this);" style="width: 100%; !important">
										<option value="dc_choose">Select</option>
		
										<c:forEach items="${item.inventory}" var="inventory">
											<c:choose>
												<c:when test="${inventory.availableQty > item.itemQty.requestedQuantity}">
													<option value="available_DS-${cartData.code}-${entryNumber}-${dccount}" class="text-capitalize">ship from ${inventory.distributionCenter.name} (${item.itemQty.requestedQuantity} available) ${inventory.distributionCenter.distance} Miles </option>
												</c:when> 

												<c:otherwise>
													<fmt:parseNumber value="${inventory.availableQty}" integerOnly="true" var="availableQtyInt" />
													<c:choose>
														<c:when test="${availableQtyInt > 0}">
															<option value="available_DS-${cartData.code}-${entryNumber}-${dccount}" class="text-capitalize">ship from ${inventory.distributionCenter.name}   (${inventory.availableQty}           )            ${inventory.distributionCenter.distance} Miles </option>
														</c:when>

														<c:otherwise>
															<%-- DO NOT display the Inventory with zero Available Quantity!! --%>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose> 
		
											<c:set var="dccount" value="${dccount + 1}" /> 
		
										</c:forEach>	      	       					
									</select> 
								</c:when>

								<c:otherwise>
									<div class="errorMSG">
										<span class="" style="color:red">No inventory available!</span>
									</div>
								</c:otherwise>
							</c:choose>
						</td> 
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</c:if>

 
