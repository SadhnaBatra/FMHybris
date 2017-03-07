<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<c:if test="${fmComponentName == '1FMLearningVideosCarousel1'}">
<div class="clearfix">
<div class="col-xs-12 bgwhite topMargn_20 techTips">
      <div class="col-lg-4 col-xs-12 padZero">
       <h3>${heading}</h3>
         <p>${description}</p>
      <button class="btn btn-fmDefault btmMrgn_14 topMargn_10">View all Tech Tips</button>
    
      </div>
 <div class="slider4 col-lg-8 col-xs-12 carousel">
				<c:forEach items="${fmbanners}" var="banner" varStatus="status" step="2" >
					<c:if test="${ycommerce:evaluateRestrictions(banner)}">
						<c:choose>
						<c:when test="${banner.sequenceId == 1}">
							<c:forEach items="${fmbanners}" var="banner" begin="0" end="${status.count+2}" varStatus="status" >
									<div class="slide">								
									<p><object  data="${banner.urlLink}" width="100%" height="100%" id="VideoPlayback">
  <param name="movie" value="${banner.urlLink}" />
  <param name="FlashVars" value="playerMode=embedded" />
  <param name="allowScriptAcess" value="sameDomain" />
  <param name="scale" value="noScale" />
  <param name="quality" value="best" />
  <param name="bgcolor" value="#FFF" />
  <param name="salign" value="TL" />
</object></p>			
									</div>
							</c:forEach>
						 </c:when>
					<c:otherwise>
							<c:forEach items="${fmbanners}" var="banner" begin="0" end="${status.count+2}" varStatus="status" >
							<div class="slide"> 
							<p ><object  data="${banner.urlLink}" width="100%" height="100%" id="VideoPlayback">
  <param name="movie" value="${banner.urlLink}" />
  <param name="FlashVars" value="playerMode=embedded" />
  <param name="allowScriptAcess" value="sameDomain" />
  <param name="scale" value="noScale" />
  <param name="quality" value="best" />
  <param name="bgcolor" value="#FFF" />
  <param name="salign" value="TL" />
</object></p>
							</div>
							</c:forEach>
											
										
					</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
			</div>
			</div>
			</div>

</c:if>

<c:if test="${fmComponentName == 'FMLearningVideosDisplay'}">
   <div class="clearfix">
      <div class="col-xs-12 bgwhite fm-padding-20 topMargn_20">
      <div class="col-lg-4 col-xs-12 padZero">
        <h3>${heading}</h3>
	${learingdescription} 
     	</div>
    <c:forEach items="${fmbanners}" var="banner">
      <div class=" col-sm-4 col-md-4 col-xs-12 topMargn_20">
                <div class="lms_intro_hover1">
                  <p class="col-xs-lms">         
			<a ${banner.external =='true'? 'target="_blank"' :''} href="${banner.urlLink}"><img  height="220" width="380" title="${banner.headline}" alt="${banner.media.altText}" src="${banner.media.url}"></a>

		   </p>
                </div>
              </div>
	<c:if test="${not empty banner.carouselcontainer}">
		<div class="col-lg-4 col-xs-12 padZero fm-learing-video-pad" >
			<a ${banner.external =='true'? 'target="_blank"' :''} href="${banner.urlLink}"> ${banner.carouselcontainer}</a>
		</div>
	</c:if>
     </c:forEach>  
      </div>
      </div>
</c:if>

<c:if test="${fmComponentName != 'FMLearningVideosDisplay' }"> 
	<div class="clearfix">
      <div class="col-xs-12 bgwhite fm-padding-20 topMargn_20">
      <div class="col-lg-4 col-xs-12 padZero">
        <h3>${heading}</h3>
		${learingdescription} 
     	</div>
    <c:forEach items="${fmbanners}" var="banner">
      <div class=" col-sm-4 col-md-4 col-xs-12 topMargn_20">
                <div class="lms_intro_hover">
                  <p class="col-xs-lms">         
					<a ${banner.external =='true'? 'target="_blank"' :''} href="${banner.urlLink}"><img  alt="${banner.media.altText}" src="${banner.media.url}"></a>
		   		   </p>
		   		   <div class="caption">
					<div class="blur"></div>
				
				 <div class="caption-text">
					<h5>
						${banner.headline}
					</h5>
					<p class="lms_desc">
						${banner.content}
					</p>
					  
				</div>
			</div>  
		   		   
		   		   
                </div>
              </div>
	<c:if test="${not empty banner.carouselcontainer}">
		<div class="col-lg-4 col-xs-12 padZero fm-learing-video-pad" >
			<a ${banner.external =='true'? 'target="_blank"' :''} href="${banner.urlLink}"> ${banner.carouselcontainer} </a>
		</div>
	</c:if>
     </c:forEach>  
      </div>
      </div>
 </c:if>






 
<script type="text/javascript">
$(document).ready(function() {

  $('.slider4').bxSlider({
    slideWidth: 202,
    minSlides: 2,
    maxSlides: 4,
    moveSlides: 1,
    slideMargin: 10,
	nextText: '<span class="fa fa-chevron-right"></span>',
	prevText: '<span class="fa fa-chevron-left"></span>'
  });
});
</script> 


