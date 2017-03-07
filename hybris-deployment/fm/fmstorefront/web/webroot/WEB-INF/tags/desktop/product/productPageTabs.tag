<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fmproduct" tagdir="/WEB-INF/tags/desktop/fmproduct" %>

<div id="tabsection" class="col-lg-12">
	<div id="horizontalTab" class="productDetailPageTab">
		<ul class="resp-tabs-list pull-left">			
              <li id="tab1"><spring:theme code="ProductDetailsPage.tag.Specifications"/></li>
              <li id="tab2"><spring:theme code="ProductDetailsPage.tag.Specifications.Reviews"/></li>
			   <c:if test="${product.faqQestion1 ne null && product.faqQestion2 ne null }">
              <li id="tab3"><spring:theme code="ProductDetailsPage.tag.Specifications.FAQs"/></li>
			  </c:if>  
              <%-- <li>Warranty</li>--%>
			  <c:if test="${not empty AlsoFits}">
              <li id="tab4"><spring:theme code="ProductDetailsPage.tag.Specifications.alsoFits"/></li>
			   </c:if>
			   <%-- 
              <li id="tab5"><spring:theme code="ProductDetailsPage.tag.Specifications.techTips"/></li>
			  --%>
		</ul>

		<div class="prodSuppLnk visible-lg pull-right">
			<a href="<%= request.getContextPath() %>/support/technical-line"><spring:theme code="ProductDetailsPage.tag.Specifications.productSupport"/><span
				class="glyphicon glyphicon-chevron-right"></span></a>
		</div>
		<div class="resp-tabs-container">
		<%-- 	<div>
				<section>
					<h3>Product Details</h3>

					<div class="descDetailsInsideTab">
						${product.description}
					</div>


				</section>
				<section>
					<h3>Warranty &amp; Support</h3>
					<p>${product.warrantydescription}</p>
				</section>
			</div> --%>
			<div id="prodSpec">
				<h3><spring:theme code="ProductDetailsPage.tag.Specifications.partSpecifications"/></h3>
				<product:productDetailsTab product="${product}" />
			</div>
			<div id="reviewTab">
				<%-- <h2 class="text-capitalize"><spring:theme code="ProductDetailsPage.tag.Specifications.ComingSoon" text="Coming Soon."/></h2>
				 <product:productPageReviewsTab product="${product}" /> --%>
 				<fmproduct:fmProductPageReviewsTab product="${product}" />
				
			</div>
			 <c:if test="${product.faqQestion1 ne null && product.faqQestion2 ne null }">
			<div id="FAQTab">
				<product:productFAQsTab product="${product}" />
			</div>
			</c:if>
			<%-- <div>
                <p>${product.warrantydescription}</p>
             </div> --%>
			<div id="AlsoFitsTab">
				<product:productAlsoFitsTab product="${product}" />
			</div>
			<div id="TechTipsTag">
               <h2 class="text-capitalize"><spring:theme code="ProductDetailsPage.tag.Specifications.ComingSoon" text="Coming Soon."/></h2>
             </div>

		</div>

		<div class="prodSuppLnk visible-xs pull-right">
			<a href="#"><spring:theme code="ProductDetailsPage.tag.Specifications.productSupport"/><span
				class="glyphicon glyphicon-chevron-right"></span></a>
		</div>
	</div>
</div>