<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions" %>   

<!-- SubTotal calculation - BEGIN: -->
<c:if test="${customerType eq 'b2BCustomer' or customerType eq 'CSRCustomer'}">
	<c:set var="B2BSubtotal" value="0"/>

	<c:forEach items="${cartData.entries}" var="entry">
		<c:if test="${entry.basePrice.value > 0}">
		<%--	<c:set var="dcentries" value="${entry.distrubtionCenter}" />
			<!-- DO NOT add Item Price to the Order Subtotal if no inventory is available for the part -->
			<c:if test="${not empty dcentries}">  --%>
				<c:forEach items="${entry.distrubtionCenter}" var="dcentry">
					<c:choose>
						<c:when test="${dcentry.backorderFlag =='nothing'}">
							<c:set var="qty" value="${dcentry.backorderQTYAll}"/>
						</c:when>
						<c:when test="${dcentry.backorderFlag == 'partial'}">
							<c:set var="qty" value="${(dcentry.availableQTY + dcentry.backorderQTY)}"/>
						</c:when>
						<c:otherwise>
							<c:set var="qty" value="${dcentry.availableQTY}"/>
						</c:otherwise>
					</c:choose>
					<c:set var="B2BSubtotal" value="${B2BSubtotal + (entry.basePrice.value * qty)}" />
				</c:forEach>
		<%--	</c:if>  --%>
		</c:if>
	</c:forEach>
</c:if>
<!-- SubTotal calculation - END -->

<!-- Order Summary table - BEGIN: -->
<table  class="orderSummaryTable">
	<tbody>
		<!-- SubTotal row - BEGIN: -->
		<tr>
			<td class="text-left text-capitalize">subtotal</td>
			<format:price priceData="${order.totalPriceWithTax}"/>

			<!-- SubTotal value - For B2B Customer Type: -->
			<c:if test="${customerType eq 'b2BCustomer' or customerType eq 'CSRCustomer'}">
				<td class="text-right"> 
					<c:if test="${empty pickUpStore}">
						<format:fmprice price="${B2BSubtotal}"/>
					</c:if>

					<c:if test="${not empty pickUpStore}">
						<format:price priceData="${cartData.totalPrice}"/>
					</c:if>
				</td>
			</c:if>

			<!-- SubTotal value - For non-B2B Customer Types: -->
			<c:if test="${customerType ne 'b2BCustomer' and customerType ne 'CSRCustomer'}">
				<td class="text-right"><format:price priceData="${cartData.subTotal}"/></td>
			</c:if>
		</tr>
		<!-- SubTotal row - END -->
 
        <!-- Shipping Cost row - BEGIN: -->
		<tr>
			<td class="text-left text-capitalize">shipping</td>
			<!-- <td class="text-right"><format:price priceData="${cartData.deliveryCost}" displayFreeForZero="true"/></td> -->
			<td class="text-right">
				<div id="shipCostSummary">
					<c:choose>
						<c:when test="${cartData.deliveryCost != null && cartData.deliveryCost.value > 0}">
							<fmt:formatNumber value="${cartData.deliveryCost.value}" type="currency" currencySymbol="$"/>
						</c:when>
						<c:otherwise>
							<a href="/fmstorefront/federalmogul/en/USD/support/contact-us" target="_blank"><span class="fa fa-phone"></span> Call</a>
						</c:otherwise>
					</c:choose>
				</div>
			</td>
		</tr>
		<!-- Shipping Cost row - END -->

		<!-- For B2b and B2c Customer Types - BEGIN: -->
		<c:if test="${customerType eq 'b2bCustomer' or customerType eq 'b2cCustomer'}">
			<!-- Savings row - BEGIN: -->
			<tr class="fm_fntRed">
				<td class="text-left text-capitalize">savings</td>
				<td class="text-right"><format:price priceData="${cartData.totalDiscounts}"/></td>
			</tr>
			<!-- Savings row - END -->

			<!-- Shipping Cost row - BEGIN: -->
			<tr>
				<td class="text-left text-capitalize">shipping</td>
				<td class="text-right"><format:price priceData="${cartData.deliveryCost}" displayFreeForZero="true"/></td>
			</tr>
			<!-- Shipping Cost row - END -->
		</c:if>
		<!-- For B2b and B2c Customer Types - END -->

		<!-- For B2c Customer Type - BEGIN: -->
		<c:if test="${customerType eq 'b2cCustomer'}">
			<!-- Taxes row - BEGIN: -->
			<tr>
				<td class="text-left text-capitalize">taxes</td>
				<td class="text-right"><format:price priceData="${cartData.totalTax}"/></td>
			</tr>
			<!-- Taxes row - END -->
		</c:if>
		<!-- For B2c Customer Type - END -->

		<!-- Estimated Total row - BEGIN: -->
		<tr class="estTotal">
			<td class="text-left text-capitalize">estimated total</td>

			<!-- For B2B Customer Type - BEGIN: -->
			<c:if test="${customerType eq 'b2BCustomer' or customerType eq 'CSRCustomer'}">
				<td class="text-right"> 
					<div id="estTotalSummary">
						<c:if test="${empty pickUpStore }">
							<c:choose>
								<c:when test="${cartData.deliveryCost != null && cartData.deliveryCost.value > 0}">
									<format:fmprice price="${B2BSubtotal + cartData.deliveryCost.value}"/>**
								</c:when>
								<c:otherwise>
									<format:fmprice price="${B2BSubtotal}"/>**
								</c:otherwise>
							</c:choose>
						</c:if>
	
						<c:if test="${not empty pickUpStore }">
							<format:price priceData="${cartData.totalPrice}"/>
						</c:if>
					</div>
				</td>
			</c:if>
			<!-- For B2B Customer Type - END -->

			<!-- For non-B2B Customer Types - BEGIN: -->
			<c:if test="${customerType ne 'b2BCustomer' and customerType ne 'CSRCustomer'}">
				<!-- <td class="text-right"><format:price priceData="${cartData.totalPrice}"/></td> -->
				<c:choose>
					<c:when test="${cartData.deliveryCost.value > 0}">
						<td class="text-right"><fmt:formatNumber value="${(cartData.subTotal.value + cartData.deliveryCost.value)}" type="currency" currencySymbol="$"/></td>
					</c:when>
					<c:otherwise>
						<td class="text-right"><format:price priceData="${cartData.totalPrice}"/></td>
					</c:otherwise>
				</c:choose>
			</c:if>
			<!-- For non-B2B Customer Types - END -->
		</tr>
		<!-- Estimated Total row - END -->
	</tbody>
</table>
<!-- Order Summary table - END -->