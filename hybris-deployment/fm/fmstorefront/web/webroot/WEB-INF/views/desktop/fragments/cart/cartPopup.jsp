<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>

<spring:theme code="text.addToCart" var="addToCartText"/>
<spring:theme code="text.popupCartTitle" var="popupCartTitleText"/>
<c:url value="/cart" var="cartUrl"/>
<c:url value="/cart/checkout" var="checkoutUrl"/>


<c:if test="${numberShowing > 0 }">
	<div class="legend">
		<div class="clearfix">
              <div class="col-md-11">
                <h3 class=""> <span class="fa fa-shopping-cart fm_fntRed"></span> <span class="text-uppercase">SHOPPING CART</span> </h3>
              </div>
              <div class="col-md-1"> <a class="fa fa-times miniCartfaTimes pull-right"  onClick="$('.miniCartPopup').hide();"></a> </div>
            </div>
		<c:if test="${numberItemsInCart > numberShowing}">
			<div class="clearfix"><a href="${cartUrl}">Show All</a> </div>
		</c:if>
	</div>
</c:if>


<c:if test="${empty numberItemsInCart or numberItemsInCart eq 0}">
	<div class="cart_modal_popup empty-popup-cart">
		<spring:theme code="popup.cart.empty"/>
	</div>
</c:if>
<c:if test="${numberShowing > 0 }">
	<ul class="itemList itemListHeight">
	<c:forEach items="${entries}" var="entry" end="${numberShowing - 1}">
		<c:url value="${entry.product.url}" var="entryProductUrl"/>
		
		
		<div class="miniCartpart col-md-12">
                         </div>
            <!-- product 1 minicart -->
            <div class="miniCartProd clearfix">
              <div class="col-md-3  col-xs-3"> <product:productPrimaryImage product="${entry.product}" format="thumbnail"/> </div>
              <div class="col-md-6  col-xs-6 miniCartRow"> <span class="fm_fntRed miniCartProdName">${entry.product.name}</span>
                <p class="miniCartPaddingPart">Part No: ${entry.product.code}</p>
                <p class="miniCartQty">Quantity: ${entry.quantity}</p>
              </div>
<c:choose>
	<c:when test="${logedUserType ne 'ShipTo'}">
							                   
              <div class="col-md-3  col-xs-3">
              <p class="miniCartRate text-right"><format:price priceData="${entry.basePrice}"/></p>
              </div>
            </div>
</c:when>
<c:otherwise>
<div class="col-md-3  col-xs-3">	
<p class="miniCartRate text-right"><format:fmprice price="0.0"/></p>
</div>
</c:otherwise>
</c:choose>
		
		
		<%-- <li class="popupCartItem">
			<div class="itemThumb">
				<a href="${entryProductUrl}">
					<product:productPrimaryImage product="${entry.product}" format="cartIcon"/>
				</a>
			</div>
			<div class="itemDesc">
				<a class="itemName" href="${entryProductUrl}">${entry.product.name}</a>
				<div class="itemQuantity"><span class="label"><spring:theme code="popup.cart.quantity"/></span>${entry.quantity}</div>
				
				<c:forEach items="${entry.product.baseOptions}" var="baseOptions">
					<c:forEach items="${baseOptions.selected.variantOptionQualifiers}" var="baseOptionQualifier">
						<c:if test="${baseOptionQualifier.qualifier eq 'style' and not empty baseOptionQualifier.image.url}">
							<div class="itemColor">
								<span class="label"><spring:theme code="product.variants.colour"/></span>
								<img src="${baseOptionQualifier.image.url}" alt="${baseOptionQualifier.value}" title="${baseOptionQualifier.value}"/>
							</div>
						</c:if>
						<c:if test="${baseOptionQualifier.qualifier eq 'size'}">
							<div class="itemSize">
								<span class="label"><spring:theme code="product.variants.size"/></span>
								${baseOptionQualifier.value}
							</div>
						</c:if>
					</c:forEach>
				</c:forEach>
				
				<c:if test="${not empty entry.deliveryPointOfService.name}">
					<div class="itemPickup"><span class="itemPickupLabel"><spring:theme code="popup.cart.pickup"/></span>${entry.deliveryPointOfService.name}</div>
				</c:if>
				
				<c:choose>
					<c:when test="${not entry.product.multidimensional or (entry.product.priceRange.minPrice.value eq entry.product.priceRange.maxPrice.value)}" >
						<div class="itemPrice"><format:price priceData="${entry.basePrice}"/></div>
					</c:when>
					<c:otherwise>
						<div class="itemPrice">
							<format:price priceData="${entry.product.priceRange.minPrice}"/>
							-
							<format:price priceData="${entry.product.priceRange.maxPrice}"/>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</li> --%>
	</c:forEach>
	</ul>
</c:if>
<c:choose>
	<c:when test="${logedUserType ne 'ShipTo'}">

 <div class="miniCartTotal clearfix">
     <p class="pull-right">TOTAL <format:price priceData="${cartData.totalPrice}"/></p>
 </div>
</c:when>
<c:otherwise>
<div class="miniCartTotal clearfix">
     <p class="pull-right">TOTAL <format:fmprice price="0.0"/></p>
 </div>

</c:otherwise>
</c:choose>





  <!-- <button class="btn btn-sm btn-fmDefault text-uppercase pull-right miniCartBtn" onclick="/cart" type="submit">checkout</button>
<div class="btn btn-sm btn-fmDefault text-uppercase pull-right miniCartBtn">
	<a href="${cartUrl}" >checkout</a>
</div>  -->

<c:choose>
	<c:when test="${empty numberItemsInCart or numberItemsInCart eq 0}">
		<a href="${cartUrl}">
			<button
				class="btn btn-sm btn-fmDefault text-uppercase pull-right miniCartBtn"
				disabled="disabled">checkout</button>
		</a>
	</c:when>
	<c:otherwise>
		<a href="${cartUrl}">
			<button
				class="btn btn-sm btn-fmDefault text-uppercase pull-right miniCartBtn">
				checkout</button>
		</a>
	</c:otherwise>
</c:choose>
