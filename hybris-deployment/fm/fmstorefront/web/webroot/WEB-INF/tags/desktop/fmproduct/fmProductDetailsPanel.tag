<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="galleryImages" required="true" type="java.util.List" %>
<%@ attribute name="categoryCode" required="false" type="java.lang.String" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<!-- Custom tags for FederalMogul -->
<%@ taglib prefix="fmproduct" tagdir="/WEB-INF/tags/desktop/fmproduct" %>

<spring:theme code="text.addToCart" var="addToCartText"/>

<div class="col-lg-5">
	<fmproduct:fmProductImagePanel product="${product}"
		galleryImages="${galleryImages}" />
</div>

<div class="col-lg-7">
	<div class="desNDetails">
		<h2 class="text-capitalize">${fn:escapeXml(product.name)}</h2>
		 <div class="partNoReviewLink clearfix">
              <div class="partNoNWarnty pull-left"> <span class="partNoLabel">Part Number:</span>
              <c:choose>
              		<c:when test="${not empty product.partNumber && !isDummyPart  }" >
              			<span class="partNo" itemprop="productID"> ${product.partNumber}</span>
              		</c:when>
              		<c:when test="${not empty product.rawPartNumber }" >
              			<span class="partNo" itemprop="productID"> ${product.rawPartNumber}</span>
              		</c:when>
              		<c:otherwise>
              			<span class="partNo" itemprop="productID">${product.code}</span>
              		</c:otherwise>
              </c:choose>
           </div>
          
              <div class="reviewLnk" itemprop="reviewCount"><span class="divider">|</span> <a href="#tab2" onclick="javascript:goToReview()">
		${fn:length(product.reviews)}&nbsp; <spring:theme code="review.number.reviews" text="reviews"/> <span class="fa fa-angle-right"></span></a> </div>

		<!-- <c:choose>
			<c:when test="${fn:length(product.reviews) eq 0}">
				<spring:theme code="review.write.title" text="Write a Review" /> <span class="fa fa-angle-right"></span></a> </div>
			</c:when>
			<c:otherwise>
				<spring:theme code="review.view.all" text="View all"/>&nbsp;${fn:length(product.reviews)}&nbsp; <spring:theme code="review.all.reviews" text="reviews"/> <span class="fa fa-angle-right"></span></a> </div>
			</c:otherwise>
		</c:choose> -->
          
         </div>
		<%-- <div class="reviewLnk">
			<a href="#">View all ${fn:length(product.reviews)} reviews <span
				class="glyphicon glyphicon-chevron-right"></span></a> <br>
		</div>
		<div class="partNoNWarnty">
			<div class="">
				<span>Part Number:</span><span class="partNo">${product.code}</span>
			</div>
			<div class="">
				<span>Warranty:</span><span class="partWarnty">${fn:escapeXml(product.warranty)}</span>
			</div>
		</div>
		<div class="descDetails">${product.summary}</div>
		<!--<div class="well well-sm clearfix">
			<div class="pull-left fitmentCheck">
				Exact fit for your <span class="vehicleDetail">2011 Toyota
					Fortuner SL</span>
			</div>
			<div class="pull-right">
				<div class="checkAnotnerLnk">
					<a href="#">Check for another vehicle <span
						class="glyphicon glyphicon-chevron-right"></span></a>
				</div>
			</div>

		</div>-->
		<%-- <product:productPricePanel product="${product}"  table="false" /> --%>
		
		<fmproduct:fmProductPricePanel product="${product}"  table="false" categoryCode="${fn:trim(categoryCode)}"/>
		
		<%--<c:if test="${years != null && make != null && model != null }">
			<fmproduct:fmProductFitmentPanel product="${product}"  table="false" />
		</c:if> --%>
	</div>
</div>
