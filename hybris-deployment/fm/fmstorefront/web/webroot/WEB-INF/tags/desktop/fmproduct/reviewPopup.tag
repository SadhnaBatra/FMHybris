<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="modal fade shipToModel" id="writeReview" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog newAddressPopup">
		<button data-dismiss="modal" class="close" type="button">
			<span class="fa fa-close" aria-hidden="true"></span><span
				class="sr-only">Close</span>
		</button>
		<div class="modal-content">
			<div class="modal-header">
				<h2 class="text-uppercase DINWebBold"><spring:theme code="review.write.title" text="Write a Review" /></h2>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-2">
						<c:forEach items="${galleryImages}" var="container"
							varStatus="varStatus">
							<c:if test="${varStatus.index eq 0}">
								<img src="${container.thumbnail.url}" alt="Image">
							</c:if>
						</c:forEach>
					</div>
					<div class="col-lg-10 text-capitalize">
						<!-- <label>Wanger<sup>&reg;</sup> thermoQuiet<sup>&reg;</sup> ceramicNXT premium disc brake pads <span class="productNameNotes text-uppercase">front(set of two)</span></label> -->
						<label>${fn:escapeXml(product.name)}</label>
						<!-- <p><span class="partNoLabel">Part Number:</span><span itemprop="productID" class="partNo">123456</span></p> -->
					</div>
				</div>
				<c:url value="${product.url}/review" var="productReviewActionUrl" />
				<div class="row" id="post-review-box">
					<div class="col-md-12">
						<form:form method="POST" id="review" accept-charset="UTF-8"
							action="${productReviewActionUrl}" commandName="reviewForm">

							<div class="form-group">
								<label for="rateProduct"><spring:theme code="review.rate.product" text="Rate this product" /></label>
								<span class="errorMsg fm_fntRed" id="errorRating" > </span>
								
								<%-- <div id="stars-wrapper" class="col-lg-25">
									<c:forEach begin="1" end="5" varStatus="status">
									<label><img class="no_star" src="${commonResourcePath}/images/jquery.ui.stars.custom.gif" alt="<spring:theme code="review.rating.alt"/>" onmouseover="javascript:changeText(${status.index})" /><form:radiobutton path="rating" id="rateProduct" value="${status.index}" />${status.index}/${status.end}</label>
									</c:forEach>
								</div> --%>
								<div>
								<span class="starRating">
								 	<form:radiobutton id="rating5" path="rating" name="rating" value="5"/> 
								 		<label for="rating5" onmouseover="javascript:changeText(5)" onmouseout="javascript:changeEmpty()">5</label>
									<form:radiobutton id="rating4" path="rating" name="rating" value="4"/>
										<label for="rating4" onmouseover="javascript:changeText(4)" onmouseout="javascript:changeEmpty()">4</label> 
									<form:radiobutton id="rating3" path="rating" name="rating" value="3"/> 
										<label for="rating3" onmouseover="javascript:changeText(3)" onmouseout="javascript:changeEmpty()">3</label>
									<form:radiobutton id="rating2" path="rating" name="rating" value="2"/>
										<label for="rating2" onmouseover="javascript:changeText(2)" onmouseout="javascript:changeEmpty()">2</label>
									<form:radiobutton id="rating1" path="rating" name="rating" value="1"/>
										 <label for="rating1" onmouseover="javascript:changeText(1)" onmouseout="javascript:changeEmpty()">1</label>
								</span>
								</div> 
								<span id="displayDesc"></span>
								
								<!-- <div class="row">
									<div class="col-sm-2">
										<label> <form:radiobutton path="rating" id="rateProduct" value="1" />
											<spring:theme code="review.rating.verypoor" text="I hate it"/>
										</label>
										 
									</div>
									<div class="col-sm-3">
										<label> <form:radiobutton path="rating" id="rateProduct" value="2" />
											<spring:theme code="review.rating.poor" text="I don't like it"/>
										</label>
									</div>
									<div class="col-sm-3">
										<label> <form:radiobutton path="rating" id="rateProduct" value="3" />
											<spring:theme code="review.rating.ok" text="It's okay"/>
										</label>
									</div>
									<div class="col-sm-2">
										<label> <form:radiobutton path="rating" id="rateProduct" value="4" />
											<spring:theme code="review.rating.verygood" text="I like it"/>
										</label>
									</div>
									<div class="col-sm-2">
										<label> <form:radiobutton path="rating" id="rateProduct" value="5" />
											<spring:theme code="review.rating.excellent" text="I love it"/>
										</label>
									</div>
								</div> -->
							</div>

							<div class="form-group">
								<label for="reviewHeadline" onclick="$('#headline').focus();" ><spring:theme code="review.headline.text" text="Review Headline" /></label>
								<form:input name="headline" path="headline" type="text" id="headline"
									required="required" class="form-control width195" minlength="4" maxlength="50" />
							</div>

							<div class="form-group">
								<div class="clearfix">
									<label class="pull-left" onclick="$('#comment').focus();"><spring:theme code="review.review.text" text="Your review" /></label>
								</div>
								<form:textarea name="comment" path="comment" type="textarea" id="comment"
									required="required" class="form-control animated" cols="50"
									rows="5" minlength="120" maxlength="2000" />
							</div>
							<div class="form-group">
								<div class="clearfix">
									<button
										class="btn btn-fmDefault text-uppercase pull-left rghtMrgn_5"
										type="submit" onclick="return checkForm();"><spring:theme code="review.submit.text" text="Submit review" /></button>
									<span class="pull-left"><spring:theme code="review.posted.by" text="Posted publicly as" /> ${user.firstName} ${user.lastName} | <a
										href="javascript:document.getElementById('review').reset()"
										class="text-capitalize fm_fnt_Blue DINWebBold"><spring:theme code="review.clear" text="Clear"/></a></span>
									<!--<a href="#" class=" pull-right  text-capitalize fm_fnt_Blue DINWebBold" >Terms &amp; Conditions</a>-->
								</div>

							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
function checkForm()
{
	
	if (! $("input:radio[name=rating]").is(":checked"))
	{
		document.getElementById("errorRating").innerHTML="Please rate the product";
		return false;
	}
	else
	{
		document.getElementById("errorRating").innerHTML="";
	}
} 


	
</script>