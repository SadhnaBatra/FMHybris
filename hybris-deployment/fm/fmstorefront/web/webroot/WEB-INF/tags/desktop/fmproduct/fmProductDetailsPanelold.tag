<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="galleryImages" required="true" type="java.util.List" %>

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

 <%-- <div class="productDetailsPanel">

	<product:productImagePanel product="${product}" galleryImages="${galleryImages}"/>

	<div class="span-10 productDescription last">
		<ycommerce:testId code="productDetails_productNamePrice_label_${product.code}">
			<product:productPricePanel product="${product}"  table="false" />
		</ycommerce:testId>
		<ycommerce:testId code="productDetails_productNamePrice_label_${product.code}">
			<h1>
					${fn:escapeXml(product.name)} hiiiii
			</h1>
		</ycommerce:testId>

		<product:productReviewSummary product="${product}"/>


		<div class="summary">
			${fn:escapeXml(product.summary)}
		</div>
		
		<product:productPricePanel product="${product}" table="true" />

		<product:productPromotionSection product="${product}"/>

		<cms:pageSlot position="VariantSelector" var="component" element="div">
			<cms:component component="${component}"/>
		</cms:pageSlot>

		<cms:pageSlot position="AddToCart" var="component" element="div">
			<cms:component component="${component}"/>
		</cms:pageSlot>

		<product:productShareTag/>
	</div>

	<cms:pageSlot position="Section2" var="feature" element="div" class="span-8 section2 cms_disp-img_slot last">
		<cms:component component="${feature}"/>
	</cms:pageSlot>
</div>   --%>


		<%-- <product:productImagePanel product="${product}" galleryImages="${galleryImages}" /> --%>
			
			<fmproduct:fmProductImagePanel product="${product}" galleryImages="${galleryImages}" />

<div class="col-lg-6">
	<div class="desNDetails">
		<h2>${fn:escapeXml(product.name)}</h2>
		<div class="reviewLnk">
			<a href="#">View all ${fn:length(product.reviews)} reviews <span
				class="glyphicon glyphicon-chevron-right"></span></a> <br>
		</div>
		<div class="partNoNWarnty">
			<div class="">
				<span>Part Number:</span><span class="partNo">${product.code}</span>
			</div>
			<div class="">
				<span>Warranty:</span><span class="partWarnty">${fn:escapeXml(product.summary)}</span>
			</div>
		</div>
		<div class="descDetails">${product.description}</div>
		<div class="well well-sm clearfix">
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

		</div>
		<%-- <product:productPricePanel product="${product}"  table="false" /> --%>
		
		<fmproduct:fmProductPricePanel product="${product}"  table="false" />
	</div>
</div>
