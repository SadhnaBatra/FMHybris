
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="formTarget" value="" />
<c:if test="${displayIFrame}">
	<c:set var="formTarget" value="_parent" />
</c:if>

<c:if test="${fmComponentName == 'troubleShoot'}">

	<c:url value="${not empty page ? page.label : urlLink}"
		var="encodedUrl" />
	<c:choose>
		<c:when test="${empty encodedUrl || encodedUrl eq '#'}">

			<div class="thumbnail">
				<div class="overlay"></div>
				<img class="img-responsive" title="${headline}"
					alt="${media.altText}" src="${media.url}">
			</div>
			<div class="panel-body">
				<h3 class="panel-title">
					<span class="fa fa-gears"></span> <strong>${headline}</strong>
				</h3>
				<div class="thmbDesc">${content}</div>
				<div>
					<a href="${urlLink}" target="${formTarget}">
						<button type="submit"
							class="btn  btn-sm btn-fmDefault text-uppercase">${carouselcontainer}</button>
					</a>
				</div>
			</div>

		</c:when>
		<c:otherwise>

			<div class="thumbnail">
				<div class="overlay"></div>
				<img class="img-responsive" title="${headline}"
					alt="${media.altText}" src="${media.url}">
			</div>
			<div class="panel-body">
				<h3 class="panel-title">
					<span class="fa fa-gears"></span> <strong>${headline}</strong>
				</h3>
				<div class="thmbDesc">${content}</div>
				<div>
					<a href="${urlLink}" target="${formTarget}">
						<button type="submit"
							class="btn  btn-sm btn-fmDefault text-uppercase">${carouselcontainer}</button>
					</a>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
	<c:set var="fmComponentName" value="msgAnounceOffer" scope="session" />
</c:if>

<c:if test="${fmComponentName == 'downloadB2b'}">
	<c:url value="${not empty page ? page.label : urlLink}"
		var="encodedUrl" />
	<c:choose>
		<c:when test="${empty encodedUrl || encodedUrl eq '#'}">

			<div class="thumbnail">
				<div class="overlay"></div>
			<img class="img-responsive" title="${headline}"
				alt="${media.altText}" src="${media.url}">
			</div>
			<div class="panel-body">
				<h3 class="panel-title">
					</span> <strong class="text-uppercase">${headline}</strong>
				</h3>
				${content}
			</div>
		</c:when>
		<c:otherwise>
			
			<div class="thumbnail">
				<div class="overlay"></div>
			<img title="${headline}" alt="${media.altText}" src="${media.url}">
			</div>
			<div class="panel-body">
				<h3 class="panel-title">
					<span class="fa fa-gears"></span> <strong class="text-uppercase">${headline}</strong>
				</h3>
				${content}
			</div>
		</c:otherwise>
	</c:choose>
	<c:set var="fmComponentName" value="msgAnounceOffer" scope="session" />
</c:if>
<c:if test="${fmComponentName == 'learn'}">
   <div class="panel-body">
   <h3 class="panel-title text-uppercase"><span class="text-uppercase">${headline}</span></h3>
   <div class="btmMrgn_30 topMargn_20"> 
				<img class="img-responsive" title="${headline}"
					alt="${media.altText}" src="${media.url}">
			</div>
		${content}	
 </div>
  	
</c:if>

<c:if test="${fmComponentName == 'leftnavgaragereward'}">
 <div class="panel-body">
  <div class="btmMrgn_30 topMargn_20"> 
				<a href="${urlLink}"><img class="img-responsive" title="${headline}"
					alt="${media.altText}" src="${media.url}"></a>
	</div>
	${content}	
 </div>
  	
</c:if>



<c:if test="${fmComponentName == 'success'}">

	<c:url value="${not empty page ? page.label : urlLink}"
		var="encodedUrl" />
	<c:choose>
		<c:when test="${empty encodedUrl || encodedUrl eq '#'}">

			<div class="thumbnail">
				<div class="overlay"></div>
				<img class="img-responsive" title="${headline}"
					alt="${media.altText}" src="${media.url}">
			</div>
			<div class="panel-body">
				<h3 class="panel-title">
					<span class="fa fa-lightbulb-o "></span> <strong>${headline}</strong>
				</h3>
				<div class="thmbDesc">${content}</div>
				<div>
					<button type="submit"
						class="btn  btn-sm btn-fmDefault text-uppercase">${carouselcontainer}</button>
				</div>
			</div>

		</c:when>
		<c:otherwise>

			<div class="thumbnail">
				<div class="overlay"></div>
				<img class="img-responsive" title="${headline}"
					alt="${media.altText}" src="${media.url}">
			</div>
			<div class="panel-body">
				<h3 class="panel-title">
					<span class="fa fa-gears"></span> <strong>${headline}</strong>
				</h3>
				<div class="thmbDesc">${content}</div>
				<div>
					<button type="submit"
						class="btn  btn-sm btn-fmDefault text-uppercase">${carouselcontainer}</button>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
	<c:set var="fmComponentName" value="msgAnounceOffer" scope="session" />
</c:if>

<!--sree  -->
<!--B2B Home Page  -->
<c:if test="${fmComponentName == 'announcement'}">
	<c:url value="${not empty page ? page.label : urlLink}"
		var="encodedUrl" />
	<c:choose>
		<c:when test="${empty encodedUrl || encodedUrl eq '#'}">
			<div class="panel-body">
			<h3 class="panel-title text-uppercase">
			<span class="fa fa-comment"></span>
				<strong class="text-uppercase">${headline}</strong>
			</h3>
			${content}

		</div>
		</c:when>
		<c:otherwise>
		<div class="panel-body">
			<h3 class="panel-title text-uppercase">
			<span class="fa fa-comment"></span>
				<strong class="text-uppercase">${headline}</strong>
			</h3>
			${content}
			<a href="${urlLink}" target="${formTarget}">
				<img title="${headline}" alt="${media.altText}" src="${media.url}">
			</a>
		</div>
		</c:otherwise>
	</c:choose>
	<c:set var="fmComponentName" value="msgAnounceOffer" scope="session" />
</c:if>


 <c:if test="${fmComponentName eq 'brandStrip'}">

		<li class="brandCol">
		
			<a href="${urlLink}" target="${formTarget}" class="${media.altText}"  >
				<img title="${headline}" alt="${media.altText}" src="${media.url}">
			</a>
		</li>
</c:if>

<c:if test="${fmComponentName == 'FMBannerSection'}">
     <div class="col-lg-5 col-xs-12 fm-padding-20">
<h3 class="colWhite">${headline}</h3>
      <p class="colWhite">${content}</p>
      <a href="${urlLink}" target="_blank">
      		<button class="btn btn-fmDefault topMargn_10">${carouselcontainer} </button>
	</a>   </div>
      <div class="col-lg-7 col-xs-12">
    <img class="img-responsive lftMrgn_18" title="${headline}" alt="${media.altText}" src="${media.url}">
        </div>
</c:if>

<c:if test="${fmComponentName == 'aboutusBrandBanner'}">
     <div class=""> <img class="img-responsive" title="${headline}" alt="${media.altText}" src="${media.url}">
	<div class="internalLandingImgContent text-uppercase"><p>${headline}</p> 
		 ${content}
	</div>
	
    </div>
</c:if>

<c:if test="${fmComponentName == 'insight'}">
	<div class=" col-sm-3 col-md-3 col-xs-6">
		<div class="lms_intro_hover ">
			<p>
				<img class="img-responsive" title="${headline}" alt="${media.altText}" src="${media.url}" />
			</p>
			 ${content}
		</div>
	</div>
</c:if>		

<c:if test="${fmComponentName == 'loyaltyBanner'}">
     <img class="img-responsive" title="${headline}" alt="${media.altText}" src="${media.url}"/>
        <div class="loyaltyBannerContent">
        	<h1>${headline}</h1>
            ${content}
        </div>
</c:if>
	
<c:if test="${fmComponentName == 'banner'}">
     <div class="rewardsBanner">
		<img class="img-responsive" title="${media.altText}"
				alt="${media.altText}" src="${media.url}" />
		<div class="rewardsAboutusBannerContent">
			${headline}			 
			${content}
		</div>
	</div></c:if>
	
<c:if test="${fmComponentName == 'waytolearn'}">
     <div class="rewardsBanner">
		<img class="img-responsive" title="${media.altText}"
				alt="${media.altText}" src="${media.url}" />
		<div class="rewardsAboutusBannerContent">
			${headline}			 
			${content}
		</div>
	</div>       
</c:if>


<c:if test="${fmComponentName == 'rewardbanner'}">
	<div class="rewardsBanner">
		<img class="img-responsive" title="${media.altText}"
				alt="${media.altText}" src="${media.url}" />
		<div class="rewardsAboutusBannerContent">
			${headline}			 
			${content}
		</div>
	</div>
</c:if>

<c:if test="${fmComponentName == 'b2tNotopted'}">
	<div class="panel panel-default panelTroubleShoot event">
							<div class="thumbnail">
				<div class="overlay"></div>
				<img class="img-responsive" title="${media.altText}"
				alt="${media.altText}" src="${media.url}" />
			</div>
			<div class="panel-body">
				${headline}
				${content}				
			</div>
		
	</div>
</c:if>
            
<c:if test="${fmComponentName == 'b2tlearning'}">
  

	<div class=" col-sm-3 col-md-3 col-xs-6">
		<div class="lms_intro_hover ">
			<p>
				<img class="img-responsive" title="${media.altText}"
					alt="${media.altText}" src="${media.url}" />
			</p>
			<div class="caption">
				${content}
			</div>
		</div>
	</div>


</c:if>
              
               
