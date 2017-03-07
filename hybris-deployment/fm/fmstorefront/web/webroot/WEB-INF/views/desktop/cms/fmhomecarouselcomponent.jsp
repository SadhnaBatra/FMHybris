<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>



<c:if test="${fmComponentName == 'msgAnounceOffer'}">
	<div class="slider4">
		
				<c:forEach items="${fmbanners}" var="banner" varStatus="status" step="2" >
					<c:if test="${ycommerce:evaluateRestrictions(banner)}">
						<c:choose>
							<c:when test="${banner.sequenceId == 1}">
							
							<c:forEach items="${fmbanners}" var="banner" begin="0" end="${status.count+2}" varStatus="status" >
									 
									<div class="slide">
									
										<div class="well well-qualityCarousel qualityImg1 clearfix ">
											<c:if  test="${banner.uid == 'FMMsgAnounceBanner1'}">
												
													<a ${banner.external =='true'? 'target="_blank"' :''} href="${banner.urlLink}"><img  class="img-responsive fm-announce-banner" src="${banner.media.url}"
													 alt="" title="" /> </a>
												
											</c:if>
											<c:if  test="${banner.uid != 'FMMsgAnounceBanner1'}">
												<div class="col-sm-5 col-lg-5 col-md-5 col-xs-5 rgtPad_0">
													<div class=" ">
														<h3 class="qualityContentTitle topMargn_8">
															<span>${banner.headline}</span>
														</h3>
														<p class="qualityContent">${banner.content}</p><a  href="${banner.urlLink}">
														<button class="btn btn-sm btn-fmDefault text-uppercase" >${banner.carouselcontainer}</button></a>
													</div>
												</div>
												<div class="holder col-sm-7 col-lg-7 col-md-7 col-xs-7">
													<img class="img-responsive fm-announce-banner" src="${banner.media.url}"
														 alt="" title="" />
												</div>
											</c:if>
										</div>
									</div>
									</c:forEach>
							
							</c:when>
							<c:otherwise>
							<c:forEach items="${fmbanners}" var="banner" begin="0" end="${status.count+2}" varStatus="status" >
			
									<div class="slide">
										
										<div class="well well-qualityCarousel qualityImg1 clearfix ">
											<c:if  test="${banner.uid == 'FMMsgAnounceBanner1'}">
												
													<a ${banner.external =='true'? 'target="_blank"' :''} href="${banner.urlLink}"><img class="img-responsive fm-announce-banner" src="${banner.media.url}"
													 alt="" title="" /> </a>
												
											</c:if>
											<c:if  test="${banner.uid != 'FMMsgAnounceBanner1'}">
												<div class="col-sm-5 col-lg-5 col-md-5 col-xs-5 rgtPad_0">
													<div class=" ">
														<h3 class="qualityContentTitle topMargn_8">
															${banner.headline}
														</h3>
														<p class="qualityContent"><span>${banner.content}</span></p>
														<button class="btn btn-sm btn-fmDefault text-uppercase" >${banner.carouselcontainer}</button>
													</div>
												</div>
												<div class="holder col-sm-7 col-lg-7 col-md-7 col-xs-7">
													<img class="img-responsive fm-announce-banner" src="${banner.media.url}"
														 alt="" title="" />
											
												</div>
											</c:if>
										</div>
									</div>
									</c:forEach>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
			</div>
			
</c:if>




<c:if test="${fmComponentName == 'spotLite'}">

	<ol class="carousel-indicators">
		<c:forEach items="${fmbanners}" var="banner" varStatus="status">
			<li data-target="#carousel-example-generic"
				data-slide-to="${status.count - 1}" class=""></li>
		</c:forEach>
	</ol>
	<div class="carousel-inner">
		<c:forEach items="${fmbanners}" var="banner" varStatus="status">

			<c:choose>
				<c:when test="${banner.sequenceId == 1}">
					<div class="item active">
						<a href="${banner.urlLink}" target="_blank"> <img
							class="img-responsive" src="${banner.media.url}" alt="" title="" />
						</a>

					</div>
				</c:when>
				<c:otherwise>
					<div class="item ">
						<a href="${banner.urlLink}" target="_blank"> <img
							class="img-responsive" src="${banner.media.url}" alt="" title="" />
						</a>

					</div>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</div>
	<a class="left carousel-control" href="#carousel-example-generic"
		data-slide="prev"> <span class="fa fa-chevron-left"></span></a>
	<a class="right carousel-control" href="#carousel-example-generic"
		data-slide="next"> <span class="fa fa-chevron-right"> </span></a>





</c:if>

<c:if test="${fmComponentName == 'careerSpotLite'}">
	<ol class="carousel-indicators">
		<c:forEach items="${fmbanners}" var="banner" varStatus="status">
			<li data-target="#carousel-example-generic"
				data-slide-to="${status.count - 1}" class=""></li>
		</c:forEach>
	</ol>

	<div class="carousel-inner" style="width: 100%;">
		<c:forEach items="${fmbanners}" var="banner" varStatus="status">
			<div class="${banner.sequenceId eq  1 ? 'item active' : 'item' }">
				<div class="col-xs-12 customBgBlock">
					<div class="col-lg-5 col-xs-12">
						<h3 style="margin-top: 3rem;" class="colWhite">
							${banner.headline}
						</h3>
						<p class="colWhite"></p>
						  ${banner.content}
						
					</div>
					<div class="col-lg-7 col-xs-12">
						<a href="${banner.urlLink}"
							${fn:contains(banner.urlLink,'guru') ? 'target="_blank"' : ''}>
							<img class="img-responsive lftMrgn_31" src="${banner.media.url}" alt=""
							title="" />
						</a>
					</div>

					
				</div>
			</div>
		</c:forEach>
	</div>
</c:if>



<c:set var="fmComponentName" value="spotLite" scope="session" />




<c:if test="${fmComponentName == 'HomeCarousel'}">

			<div class="bx-wrapper" style="max-width: 666px;">
				<div class="bx-viewport"
					style="width: 100%; overflow: hidden; position: relative; height: 327px;">
					<div class="slider4 carousel"
						style="width: 515%; position: relative; transition-duration: 0s; transform: translate3d(-696px, 0px, 0px);">
						<c:forEach items="${fmbanners}" var="banner" varStatus="status">
							<c:if test="${ycommerce:evaluateRestrictions(banner)}">
							<div class="slide bx-clone"
								style="float: left; list-style: outside none none; position: relative; width: 202px; margin-right: 30px;">
								<a class="thumbnail" href="#"><img
									alt="Image" src="${banner.media.url}"></a>
								<div class="caption">
									<p class="title text-capitalize">${banner.headline}</p>
								
									<p>${banner.content}</p>
								</div>
							</div>
							</c:if>
						</c:forEach>
					</div>

				</div>
				<div class="bx-controls bx-has-pager bx-has-controls-direction">
					<c:forEach items="${fmbanners}" var="banner" varStatus="status">
						<div class="bx-pager bx-default-pager">
							<div class="bx-pager-item">
								<a class="bx-pager-link active"
									data-slide-index="${status.index}" href="">${status.index}</a>
							</div>
						</div>
					</c:forEach>
					<div class="bx-controls-direction"> 
						<a href="" class="bx-prev"><span class="fa fa-chevron-left"></span></a><a
							href="" class="bx-next"><span class="fa fa-chevron-right"></span></a>
					</div>
				</div>
			</div>
		
</c:if>



