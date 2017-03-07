<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="federalmogul" uri="http://federalmogul.com/tld/federalmogultags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fitment" tagdir="/WEB-INF/tags/desktop/federalmogul/product" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<spring:theme code="text.addToCart" var="addToCartText"/>
<c:url value="${product.url}" var="productUrl"/>

<c:set var="whereToBuyURI"><spring:eval expression="@propertyConfigurer.getProperty('wheretobuyurl')" /></c:set>

<c:set value="${not empty product.potentialPromotions}" var="hasPromotion"/>

<c:set var="displayFitmentInfo"	value="false" />
<c:set var="displayFitmentYear"	value="false" />
<c:set var="displayFitmentMake"	value="false" />
<c:set var="displayFitmentModel" value="false" />

<c:if test="${not empty searchPageData.breadcrumbs}">
	<c:forEach items="${searchPageData.breadcrumbs}" var="breadcrumb">
		<c:if test="${not empty breadcrumb.facetName && breadcrumb.facetName == 'year' }" >
        	<c:set var="displayFitmentYear"	value="${fn:substringAfter(breadcrumb.facetValueName,'|')}" />
        </c:if>
        <c:if test="${not empty breadcrumb.facetName && breadcrumb.facetName == 'make' }" >
        	<c:set var="displayFitmentMake"	value="${fn:substringAfter(breadcrumb.facetValueName,'|')}" />
        </c:if>
        <c:if test="${not empty breadcrumb.facetName && breadcrumb.facetName == 'model'}" >	
        	<c:set var="displayFitmentModel" value="${fn:substringAfter(breadcrumb.facetValueName,'|')}" />
        </c:if>
        <c:if test="${displayFitmentYear != 'false' && displayFitmentMake != 'false' && displayFitmentModel != 'false'}" >	
           	<c:set var="displayFitmentInfo"	value="true" />
        </c:if>
  </c:forEach>
</c:if>
<div class="row productlistblock newProductlistblock clearfix topMargn_10">
	<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
        <div class="recommendedLabel" >&nbsp;</div>
        <div class="thumbnail">
			<c:choose>
				<c:when test="${displayFitmentInfo =='true' }">
					<a
						href="${productUrl}?years=${displayFitmentYear}&make=${displayFitmentMake}&models=${displayFitmentModel}"
						title="${product.name}">
							<product:productPrimaryImage product="${product}" format="product" />
					</a>
				</c:when>
				<c:otherwise>
					<a href="${productUrl}">
						<product:productPrimaryImage product="${product}" format="product" />
					</a>
				</c:otherwise>
			</c:choose>

		</div>
    </div>
	<c:set var="productURL" value="${productUrl}" />
	<c:if test="${displayFitmentInfo =='true' }">
		<c:set var="productURL" value="${productUrl}?years=${displayFitmentYear}&make=${displayFitmentMake}&models=${displayFitmentModel}" />
	</c:if>
	<c:if test="${displayFitmentInfo =='false' }">
		<c:set var="productURL" value="${productUrl}?categoryCode=${categoryCode}" />
	</c:if>
	<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
		<div class="media-body">
			<p class="prodBrandName text-uppercase">${product.brands.corpname}</p>
			<c:if test="${fn:contains(product.name, product.brands.corpname)}">
				<c:set value="${fn:replace(product.name, product.brands.corpname,'')}" var="name"></c:set>				
			
			<h4 class="text-capitalize">
					 <c:choose>
							<c:when test="${fn:contains(name, '(')}">
							<c:set value="${name}" var="name"/>
							<c:set value="${fn:substring(name,fn:indexOf(name,'('),fn:indexOf(name,')'))}" var="name1"/>
							 <a href="${productURL}">${fn:replace(name, fn:substring(name,fn:indexOf(name,'('),fn:indexOf(name,')')), fn:toUpperCase(fn:trim(name1)))}</a>
					</c:when>
					<c:otherwise>
							<a href="${productURL}">${name}</a>
					</c:otherwise>                    												
						</c:choose>				   		
			</h4>

			</c:if>
			<p class="partNoReview">
				<span class="partNo"><spring:theme code="ProductSearchresultspage.partNo"/></span>
				<c:choose>
					<c:when test="${not empty  product.partNumber }" >
						<a href="${productURL}">${product.partNumber}</a>
					</c:when>
					<c:otherwise>
						<a href="${productURL}">${product.rawPartNumber}</a>
					</c:otherwise>
				</c:choose>
				<span class="review"><!--${fn:length(product.reviews)}--><a href="${productURL}#horizontalTab2"> ${product.numberOfReviews}&nbsp;
					<spring:theme code="ProductSearchresultspage.Reviews"/> <span class="linkarow fa fa-angle-right "></span></a>
				</span>
			</p>
			<c:choose>
			<c:when test="${displayFitmentInfo == 'true'}" >
				<div class="productDetailPageTab newProdListTab horizontalTab">
					<ul class="resp-tabs-list pull-left">
	                      <li><spring:theme code="ProductSearchresultspage.Features"/></li>
	                      <li><spring:theme code="ProductSearchresultspage.Description"/></li>
	                </ul>
	                <div class="resp-tabs-container">
	                	<div>
	                	<c:set value="0" var="apps"/>
	                	<c:if test="${product.partFitments != null && displayFitmentInfo == 'true'}" >
	                		<c:set value="${federalmogul:appFitmentmap(product.partFitments,searchPageData,displayFitmentYear,displayFitmentMake,displayFitmentModel)}" var="appFitmentmap" />
						</c:if>
						<c:forEach items="${appFitmentmap}" var="map">
							<c:if test='${map.key eq "fitmentcount"}'>
								<c:set value="${map.value}" var="appFitmentcount"/>
							</c:if>
							<c:if test='${map.key eq "fitmentlist"}'>
								<c:set value="${map.value}" var="partfitments"/>
							</c:if>
						</c:forEach>
						<c:if test="${appFitmentcount > 1 }" >
							<h5 class="tabTitle text-uppercase">multiple applications (${appFitmentcount })</h5>
						</c:if>
						<c:if test="${displayFitmentInfo == 'true'}" >
							<c:forEach items="${partfitments}" var="fitment" varStatus="status">
								<c:if test="${not empty fitment}" >
									<fitment:fitmentinfo fitment="${fitment}" statusIndex="${status.index}" fitmentCount="${appFitmentcount}"/>
								</c:if>
							</c:forEach>
						</c:if>
						</div>
	                	<div>
	                        <p>${product.summary}</p>
	                    </div>
	                </div>
				</div>
			</c:when>
			<c:otherwise>
				<div id="description" class="description btmMrgn_14">
					${product.summary} 
				</div>
				<!-- <div class="clearfix btmMrgn_14">
					<a class="btn btn-fmDefault pull-left" style="display: block">where
						to buy</a>
				</div> -->
			</c:otherwise>
			</c:choose>
		</div>
	</div>
   
	<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
		<div class="prodPrice">
			<p>&nbsp;</p>
			<p class="price-text-color">&nbsp;</p>
		</div>

<%--
<sec:authentication property="principal" var="principal"/>
<c:out value="${principal}"/>
<sec:authentication property="authorities" var="authorities"/>
<c:out value="${authorities}"/>
 --%>
 
		<c:choose>
			<c:when test="${customerType ne 'b2bCustomer' and customerType ne 'b2BCustomer' and customerType ne 'b2cCustomer' and customerType ne 'CSRCustomer' and customerType ne 'FMAdmin'}">
	            <div class=" clearfix">
					<a href="${whereToBuyURI}#hybrisProductCode=${product.brands.corpcode}" class="btn btn-fmDefault pull-left" style="display: block">where to buy</a>
				</div>
			</c:when>
			<c:otherwise>
				<sec:authorize ifAnyGranted="ROLE_FMB2T">
					 <div class=" clearfix">
						<a href=${whereToBuyURI}#hybrisProductCode=${product.brands.corpcode}" class="btn btn-fmDefault pull-left" style="display: block">where to buy</a>
					</div>
				</sec:authorize>

				<sec:authorize ifNotGranted="ROLE_FMB2T,ROLE_FMB2C,ROLE_FMVIEWONLYGROUP">
				 	<div class=" clearfix">
						<input type="text" maxlength="5" id="qty_${product.code}" class="form-control qty visible-lg-inline visible-md-inline visible-sm-inline width45 pull-left" value="">
						<a href="#" onclick="javascript:categoryAddToCart('${product.code}','${product.name}','${product.price.formattedValue}');" 
							class="btn btn-fmDefault topMargn_5" style="display: block">add to cart</a>
					</div>
				</sec:authorize>	

				<div id="div_${product.code}" class="topMargn_15">
	             	  <div id="error_${product.code}" class="poerror"></div>
	            </div>
			</c:otherwise>
		</c:choose>
		
		<c:if test="${displayFitmentInfo == 'false'}" >
			<div class="">
 				<p class="vehiclefitmatch clearfix"><span class="fitLabel">Fits:</span><br/>
                    <i class="fa fa-exclamation-triangle fm_fntRed"></i> Enter vehicle information above to ensure this part fits your vehicle</p></br>
			</div>
		</c:if>

		<c:if test="${displayFitmentInfo == 'true'}" >
			<div class="">
				<p class="vehiclefitmatch clearfix">
					<span class="fitLabel"><spring:theme code="ProductSearchresultspage.fitment.tab.title"/></span><br /> 
					<i class="fa fa-check-circle fmFitsChecked"></i> <a href="#">${displayFitmentYear}&nbsp; ${displayFitmentMake }&nbsp;${displayFitmentModel }</a>
				</p>

				<c:if test="${displayFitmentInfo == 'true'}" >
					<c:set var="ctr" value="0"/>
					<c:forEach items="${partfitments}" var="fitment" varStatus="status">
						<c:if test="${not empty fitment}" >
							<fitment:additionalFitinfo fitment="${fitment}" statusIndex="${ctr}" fitmentCount="${appFitmentcount}"/>
							<c:set var="ctr" value="${ctr+1}"/>
						</c:if>
					</c:forEach>
				</c:if>
			
				<c:if test="${appFitmentcount > 1 }" >
					<p class="multiAppTitle text-capitalize">multiple applications (${appFitmentcount })</p>
				</c:if>
			</div>
		</c:if>	
	</div>
  
</div>
