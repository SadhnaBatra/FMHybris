<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- Custom FederalMogul Tags -->
<%@ taglib prefix="fmproduct" tagdir="/WEB-INF/tags/desktop/fmproduct" %>

<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="galleryImages" required="true" type="java.util.List" %>





	<div class="prodMainImg">
		<div class="prodDetailBrandLogo">
	<img class="" src="${product.brands.image.url}" title="" alt="">
		</div>
		<c:forEach items="${galleryImages}" var="container"
			varStatus="varStatus">

			<c:if test="${varStatus.index  == 0 }">
				<img id="prodMainImg" src="${container.zoom.url}"
					data-zoom-image="${container.zoom.url}" class="img-responsive" />
			</c:if>
		</c:forEach>
		<c:if test="${empty galleryImages }" >
			<img id="noProdMainImg" src="/fmstorefront/_ui/desktop/theme-green/images/missing-product-1200x1200.jpg"
			data-zoom-image="/fmstorefront/_ui/desktop/theme-green/images/missing-product-1200x1200.jpg"					 
			class="img-responsive" />
		</c:if>
	</div>
	<div class="row">
		<div class="col-md-10">
			<div id="Carousel" class="carousel slide prdDetailThumbCarousel">
				<!-- Carousel items -->
				<div class="carousel-inner  prdDetailThumbnail"
					id="prdDetail_thumbnail">
					
					<c:set var="galleryLength" value="${fn:length(galleryImages)}" />
					<c:if test="${galleryLength gt  1}">
					<c:forEach items="${galleryImages}" var="container" varStatus="varStatus">
							
								<c:if test="${(varStatus.index)%3 == 0}">
								
									<div class="item ${(varStatus.index==0)? "active":""}">
										<div class="row">
								</c:if>
								<div class="col-md-4 col-xs-4">
									<a href="#" class="thumbnail ${(varStatus.index==0)? "active":""}" 
										data-image="${container.zoom.url}"
										data-zoom-image="${container.zoom.url}"><img
										src="${container.thumbnail.url}" alt="Image"></a>
								</div>
								<c:if test="${(varStatus.index+1)%3 == 0 || galleryLength == varStatus.index+1 }">
								
									</div>
								 </div>	
								
								</c:if>  
								
								
						</c:forEach>
						<%-- <c:if test="${empty galleryImages }">
							<a href="#" class="thumbnail active"><img
								src="/fmstorefront/_ui/desktop/theme-green/images/missing-product-300x300.jpg"
								alt="Image"></a>
				
						</c:if> --%>
					</c:if>



				</div>
				<!--.carousel-inner-->
				<c:if test="${(galleryLength)>3}">
				<a data-slide="prev" href="#Carousel" class="left carousel-control"><span
					class="fa fa-angle-left"></span></a> <a data-slide="next"
					href="#Carousel" class="right carousel-control"><span
					class="fa fa-angle-right"></span></a>
					</c:if>
			</div>
		</div>
	</div>



<!--STARTS: Modal for displaying large image when click on magnify glass -->
 
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog prodDetailDialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true" class="fa fa-close"></span><span
						class="sr-only">Close</span>
				</button>
			</div>
			<div class="modal-body">
				<div id="thumbnail-preview-indicators" class="carousel slide"
					data-ride="carousel">
					<div class="carousel-inner">
						<c:forEach items="${galleryImages}" var="container"
							varStatus="varStatus">
							
							<div class="item slides ${(varStatus.index==0)? "active":""}">
								<div class="slide-${varStatus.index}">
									<img class="img-responsive" src="${container.zoom.url}">
								</div>
							</div>
						</c:forEach>
					</div>
					<!-- Indicators -->
					<ol class="carousel-indicators">
						<c:forEach items="${galleryImages}" var="container"
							varStatus="varStatus">
							
							<li data-target="#thumbnail-preview-indicators"
								data-slide-to="${varStatus.index}"
								class="${(varStatus.index==0)? "active":""}">
								<div class="thumbnail" style="padding:0;" >
									<img class="img-responsive" src="${container.thumbnail.url}" style="width:68px; width:6.8rem; height:68px; height:6.8rem; !important;">
								</div>
							</li>
						</c:forEach>
					</ol>
					<c:if test="${(galleryLength)>3}">
					<a class="left carousel-control"
						href="#thumbnail-preview-indicators" role="button"
						data-slide="prev"><span class="fa fa-angle-left"></span></a> <a
						class="right carousel-control"
						href="#thumbnail-preview-indicators" role="button"
						data-slide="next"><span class="fa fa-angle-right" style="margin-left:3rem !important; margin-left:30px !important;"></span></a>
						</c:if>
				</div>
			</div>
		</div>
	</div>
</div>




