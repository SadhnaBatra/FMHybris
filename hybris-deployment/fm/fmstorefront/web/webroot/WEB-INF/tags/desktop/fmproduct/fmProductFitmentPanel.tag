<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="isOrderForm" required="false" type="java.lang.Boolean" %>
<%@ attribute name="table" required="false" type="java.lang.Boolean" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fitment" tagdir="/WEB-INF/tags/desktop/federalmogul/product" %>
<%@ taglib prefix="federalmogul" uri="http://federalmogul.com/tld/federalmogultags" %>

<div class="fitmentCheck clearfix">
	<div class="pull-left"> <i class="fa fa-check-circle fmFitsChecked"></i> <span class="fitLabel">Fits: </span>${years}&nbsp; ${make }&nbsp; ${model }</div>
	<div class="pull-right">
		<div class="checkAnotnerLnk"> <a href="/fmstorefront/federalmogul/en/USD/search?q=&text=#">Check for another vehicle <span class="fa fa-angle-right"></span></a> </div>
	</div>
</div>

<c:if test="${product.partFitments != null }" >
   <c:set value="${federalmogul:appProductFitmentmap(product.partFitments,years,make,model)}" var="appFitmentmap" />
</c:if>
<c:forEach items="${appFitmentmap}" var="map">
	<c:if test='${map.key eq "fitmentcount"}'>
		<c:set value="${map.value}" var="appFitmentcount"/>
	</c:if>
	<c:if test='${map.key eq "fitmentlist"}'>
		<c:set value="${map.value}" var="partfitments"/>
	</c:if>
</c:forEach>



<div class="descDetails">
	<div class="productSpecPanel">
    	<c:if test="${appFitmentcount > 1 }" >
			<h5 class="tabTitle text-uppercase">multiple applications (${appFitmentcount })</h5>
		</c:if>
			<c:forEach items="${partfitments}" var="fitment" varStatus="status">
					<fitment:fitmentinfo fitment="${fitment}" statusIndex="${counter}" fitmentCount="${appFitmentcount }"/>
			</c:forEach>
	 </div>        
            <!-- Product Detail Quality information -->
            <div class="prodDetailQuality">
              <div class="row">
                <div class="col-sm-3"><img src="images/wagner_thermoquiet.png" alt="" /></div>
                <div class="col-sm-9">
                  <h3 class="fm_fntRed text-capitalize">Quality that lasts</h3>
                  <p>Our brake materials are validated using the most stringent testing in the industry at Federal-Mogul’s global R&D facilities and through their close working relationships with leading automotive manufacturers.</p>
                </div>
              </div>
            </div>
          </div>
       
      
