<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="product" required="true"
	type="de.hybris.platform.commercefacades.product.data.ProductData"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/desktop/formElement"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fmproduct" tagdir="/WEB-INF/tags/desktop/fmproduct"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:url value="${product.url}/reviewhtml/2" var="getPageOfReviewsUrl" />
<c:url value="${product.url}/reviewhtml/all" var="getAllReviewsUrl" />

<div id="reviews" class="reviews" data-reviews="${getPageOfReviewsUrl}"
	data-allreviews="${getAllReviewsUrl}"></div>
<div class="reviewsDetails">
	<sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
		<c:url var="signin" value='/sign-in' />
		<a href="${signin}" class=" fm_fnt_Blue"> <spring:theme
				code="review.signin" text="Sign In or Register" />&nbsp;<spring:theme
				code="review.signin.text" text="to write a Review" /><span class="linkarow fa fa-angle-right ">&nbsp;</span> </a>	
</sec:authorize>
	<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
		<button data-target="#writeReview" data-toggle="modal"
			class="btn btn-fmDefault text-uppercase">
			<spring:theme code="review.write.title" text="Write a Review" />
		</button>
	</sec:authorize>
	<c:if test="${fn:length(product.reviews) gt 0}">
		<div class="clearfix">
			<h3 class="pull-left">
				<spring:theme code="review.number.reviews"/>
			</h3>
			<div class="prodShowAll visible-lg pull-right topMargn_25">
			<c:if test="${display ne 'all'}">
				<span class="rghtMrgn_20"> <label class="text-capitalize">
						<c:if test="${fn:length(product.reviews) gt '1'}">
  							2&nbsp;
  						</c:if> <c:if test="${fn:length(product.reviews) lt '2'}">
  							1&nbsp;
  						</c:if> <spring:theme code="review.number.of" />&nbsp;
						${fn:length(product.reviews)} &nbsp;<spring:theme
							code="review.number.reviews" />
				</label>
				</span>
			</c:if> 
            <a href="${getAllReviewsUrl}"><spring:theme code="review.show.all" /> &gt;</a>
		</div>

		</div>
		<c:set var="totalReviewCount" value="${product.reviews.size()}" />
		<c:if test="${display ne 'all'}">
		  <c:set var="totalReviewCount" value="2" />
		</c:if>
		
		<div class="reviewsDetailsContent">	
				<c:forEach items="${product.reviews}" begin="0" end="${totalReviewCount-1}" var="review"
					varStatus="status">
                    <div class="reviewBlock">	
						<label class="text-capitalize">${review.headline}</label>
						<div class="userreview">
							<spring:theme code="review.submitted.by"/>
							&nbsp;${review.principal.name}&nbsp;
							<spring:theme code="review.on"/>
							&nbsp;
							<c:set var="reviewDate" value="${review.date}" />
							<fmt:formatDate value="${reviewDate}" pattern="MMM dd yyyy" />
						</div>
						<div class="userreviewcomment">${review.comment}</div>
						<c:if test="${not empty review.fmAdminResponse}">
						  <div class="fmresponse">
							 <label class="text-capitalize"><spring:theme code="customer.product.review.fm.admin.response"/></label>&nbsp;&nbsp;
							 <c:set var="fmResponseDate" value="${review.fmAdminResponseDate}" />
							 <fmt:formatDate value="${fmResponseDate}" pattern="MMM dd yyyy" />
						  </div>
						  <div class="fmadminresponsecomment">${review.fmAdminResponse}</div>
						</c:if>
					</div>
     			</c:forEach>
		</div>
	</c:if>

</div>
<fmproduct:reviewPopup />
