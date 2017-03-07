<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="breadcrumb"
	tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<%@ taglib prefix="productloyalty" tagdir="/WEB-INF/tags/desktop/product/loyalty" %>

<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>



<template:page pageTitle="${pageTitle}">
	<!-- InstanceBeginEditable name="Breadcrumb" -->
	<section class="breadcrumbPanel visible-lg visible-md visible-sm"
		itemscope itemtype="http://schema.org/Product">
		<div class="container">
			<div class="row">
				<ul class="breadcrumb">
					<li><a href="#"><span class="fa fa-home"></span> Home</a> <span
						class="fa fa-angle-right"></span></li>
					<li><a href="#">My Rewards</a> <span class="fa fa-angle-right"></span>
					</li>
					<li><a href="#" class="selected" itemprop="url">Redeem
							Rewards</a></li>
				</ul>
			</div>
		</div>
	</section>

	<!-- InstanceEndEditable -->


	<!-- MAIN CONTAINER-->
	<!-- InstanceBeginEditable name="Page Content Section" -->
	<section class="myRewardDetail productDetailPage">
		<section class="productDetailContent">
			<div class="container">
				<div class="clearfix bgwhite">
					<div class="col-lg-6 topMargn_20">
						<div class="prodMainImg">
							<div class="">
								<a href="rewardsSearchResult.html"
									class="text-capitalize addNewAddLink"><span
									class="linkarow fa fa-angle-left"></span> Back to redeem list</a>
							</div>
						
						<div class="col-lg-12">
	<productloyalty:loyaltyProductImage product="${product}"
		galleryImages="${galleryImages}" />
</div>
						
							<!-- <img id="prodMainImg"
								src="/fmstorefront/_ui/desktop/common/images/LG_8250_515_300.jpg"
								data-zoom-image="/fmstorefront/_ui/desktop/common/images/LG_8250_1500.jpg"
								class="img-responsive" />
 -->							<!--<img class="" src="${product.brands.image.url}" title="" alt="">
		
		<c:forEach items="${galleryImages}" var="container"
			varStatus="varStatus">

			<c:if test="${varStatus.index  == 0 }">
				<img id="prodMainImg" src="${container.zoom.url}"
					data-zoom-image="${container.zoom.url}" class="img-responsive" />
			</c:if>
		</c:forEach>
		<c:if test="${empty galleryImages }" >
			<img id="prodMainImg" src="/fmstorefront/_ui/desktop/theme-green/images/missing-product-1200x1200.jpg"
			data-zoom-image="/fmstorefront/_ui/desktop/theme-green/images/missing-product-1200x1200.jpg"					 
			class="img-responsive" />
		</c:if>-->

						</div>
		<!-- 				<div class="row btmMrgn_25">
							<div class="col-md-10">
								<div id="Carousel" class="carousel slide prdDetailThumbCarousel">
									Carousel items
									<div class="carousel-inner  prdDetailThumbnail"
										id="prdDetail_thumbnail">
										<div class="item active">
											<div class="row">
												<div class="col-md-4 col-xs-4">
													<a href="#" class="thumbnail active"
														data-image="/fmstorefront/_ui/desktop/common/images/LG_8250_515_72.jpg"
														data-zoom-image="/fmstorefront/_ui/desktop/common/images/large/LG_8250_1500.jpg"><img
														src="/fmstorefront/_ui/desktop/common/images/LG_8250_86.jpg"
														alt="Image"></a>
												</div>
												<div class="col-md-4 col-xs-4">
													<a href="#" class="thumbnail"
														data-image="/fmstorefront/_ui/desktop/common/images/LG_8250_515_72.jpg"
														data-zoom-image="/fmstorefront/_ui/desktop/common/images/large/LG_8250_1500.jpg"><img
														src="/fmstorefront/_ui/desktop/common/images/LG_8250_86.jpg"
														alt="Image"></a>
												</div>
												<div class="col-md-4 col-xs-4">
													<a href="#" class="thumbnail"
														data-image="/fmstorefront/_ui/desktop/common/images/LG_8250_515_72.jpg"
														data-zoom-image="/fmstorefront/_ui/desktop/common/images/large/LG_8250_1500.jpg"><img
														src="/fmstorefront/_ui/desktop/common/images/LG_8250_86.jpg"
														alt="Image"></a>
												</div>
											</div>
											.row
										</div>

										.item

									</div>
									.carousel-inner
									<a data-slide="prev" href="#Carousel"
										class="left carousel-control"><span
										class="fa fa-angle-left"></span></a> <a data-slide="next"
										href="#Carousel" class="right carousel-control"><span
										class="fa fa-angle-right"></span></a>
								</div>
							</div>
						</div> -->
					</div>
					<div class="col-lg-6 topMargn_20">
						<div class="desNDetails">
							<div class="fitmentCheck rewardsPanel clearfix btmMrgn_30">
								<h3 class="panel-title col-lg-7 topMargn_10">
									<span class="fa fa-certificate"></span> <span
										class=" text-uppercase">My rewards</span>
								</h3>
								<div class="rewardsPoints col-lg-5">
									<span class="pull-right"><i class="fa fa-wrench"></i><strong>3,254<sub>pts</sub></strong></span>
								</div>

							</div>
							<h2 class="text-capitalize">${fn:escapeXml(product.name)}</h2>
							<div class="partNoReviewLink clearfix">
								<div class="partNoNWarnty pull-left">
									<span class="partNoLabel">Item Number:</span> <span
										class="partNo" itemprop="productID">${product.code}</span>
								</div>
								<div class="partNoNWarnty pull-right">
									<span class="partNoLabel mainPrice">${product.loyaltyPoints}</span> <span
										class="partNo">Pts</span>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-12 text-capitalize topMargn_20">
									<p>${product.description}</p>
									<!-- <ul class="topMargn_10">
                <li>Solid black with distressed logo</li>
                <li>8.5 oz, 70% cotton/30%polyester</li>
              </ul> -->
								</div>
								<div class="clearfix">
									<div class="col-lg-12 topMargn_20">
										<div class="col-sm-3">
 <!--   <product:productVariantSelector product="${product}"/> -->
											<form class="form-horizontal">
												<div class="form-group">
													<label class="control-label DINWebBold prodQtyLabel "
														for="qty">Color</label>
													<div
														class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block btmMrgn_5 width115">
														<select id="sortby" class="form-control">
															<option>Black</option>
															<option>White</option>
															<option>Red</option>
														</select>
													</div>
												</div>
											</form>
										</div>
										<div class="col-sm-3 hideAddedtoCart">
											<form class="form-horizontal">
												<div class="form-group">
													<label class="control-label DINWebBold prodQtyLabel "
														for="qty">Size</label>
													<div
														class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block btmMrgn_5 width115">
														<select id="sortby" class="form-control">
															<option>30</option>
															<option>32</option>
															<option>34</option>
														</select>
													</div>

													<a
														onClick="$('.sizeChart').show();$('.hideSizeLink').show();$('.viewSizeLink').hide();"
														class="text-capitalize addNewAddLink viewSizeLink">View
														size chart <span class="linkarow fa fa-angle-right "></span>
													</a> <a
														onClick="$('.sizeChart').hide();$('.hideSizeLink').hide();$('.viewSizeLink').show();"
														class="text-capitalize addNewAddLink hideSizeLink"
														style="display: none">Hide size chart <span
														class="linkarow fa fa-angle-right "></span></a>
												</div>
											</form>
										</div>
										<div class="col-sm-2 hideAddedtoCart">
											<form class="form-horizontal">
												<div class="form-group">

													<label for="qty"
														class="control-label DINWebBold prodQtyLabel ">Quantity</label>
													<input type="text" maxlength="5" size="1" id="qtyInput" 
														name="qtyInput" class="form-control width58" value="1"
														width="30%">

												</div>
											</form>
										</div>
										<div class="col-lg-3 myRewardProDetailAddToCart">

											<form id="AddToCartForm" class="form-horizontal"  action="<c:url value="/loyaltycart/add"/>" method="POST" >
												<c:set var="buttonType">submit</c:set>
												<input type="hidden" maxlength="5" size="1" id="qty" name="qty" class="qty" value="1" />
												 <input type="hidden" name="productCodePost" value="${product.code}" />
												<div class="form-group topMargn_25">

													<button type="${buttonType}" class="btn btn-fmDefault prodDetAddtoCart">ADD TO CART</button>

													<!-- This is link to open size chart in popup
                      <a href="#" class="text-capitalize addNewAddLink"  data-target="#sizeChart" data-toggle="modal">View size chart <span class="linkarow fa fa-angle-right "></span></a>-->
												</div>
											</form>
										</div>
									</div>
								</div>
								<div class="sizeChart clearfix" style="display: none;">
									<div class="col-sm-12">
										<h4 class="text-uppercase DINWebBold">Sizing Chart for:</h4>
										<h5 class="text-capitalize">Brand name here</h5>
									</div>
									<div class="col-sm-12">
										<div class="table-responsive userTable btmMrgn_25">
											<table class="table" id="myTable">
												<thead>
													<tr class="text-capitalize">
														<th>Men Champion Sizing</th>
														<th class="text-center">S</th>
														<th class="text-center">M</th>
														<th class="text-center">L</th>
														<th class="text-center">XL</th>
														<th class="text-center">2XL</th>
														<th class="text-center">3XL</th>
													</tr>
												</thead>
												<tbody>
													<tr class="">
														<td>Chest</td>
														<td>34-36</td>
														<td>38-40</td>
														<td>42-44</td>
														<td>46-48</td>
														<td>50-52</td>
														<td>54-56</td>
													</tr>
													<tr>
														<td>Waist</td>
														<td>28-30</td>
														<td>32-34</td>
														<td>36-38</td>
														<td>40-42</td>
														<td>44-46</td>
														<td>48-50</td>
													</tr>
													<tr>
														<td>Inseam</td>
														<td>30</td>
														<td>31</td>
														<td>32</td>
														<td>32.5</td>
														<td>33</td>
														<td>33.5</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
		</section>
		<section class="suggestedProduct pageContet">
			<div class="container">
				<div class="row">
					<div class="learningPanel col-lg-12">
						<div class="panel-heading clearfix learningPanelTitle">
							<h3 class="panel-title">
								<span class="text-uppercase">You might also be interested
									in:</span>
							</h3>
						</div>
					</div>
				</div>
				<div class="row">
					<div
						class="col-lg-12 col-md-12 col-sm-12 col-xs-12 internalLanding">
						<div class="internalPage rightHandPanel clearfix">
							<div class="clearfix">
								<div class="slider4 carousel">
									<div class="slide">
										<a href="#" class="thumbnail"><img
											src="/fmstorefront/_ui/desktop/common/images/rewards-home-prod-img1.jpg"
											alt="Image"></a>
										<div class="caption">
											<p class="title text-capitalize">Champion&reg; Classic
												logo Tee</p>
											<p>
												iItem#: <span class="itemNum">8250-LG</span>
											</p>
											<p>Lorem ipsum dolor sit amet, sed do eiusmod tempor.</p>
										</div>
									</div>
									<div class="slide">
										<a href="#" class="thumbnail"><img
											src="/fmstorefront/_ui/desktop/common/images/rewards-home-prod-img2.jpg"
											alt="Image"></a>
										<div class="caption">
											<p class="title text-capitalize">Champion&reg; trucker
												hat</p>
											<p>
												iItem#: <span class="itemNum">8460</span>
											</p>
											<p>Lorem ipsum dolor sit amet, sed do eiusmod tempor.</p>
										</div>
									</div>
									<div class="slide">
										<a href="#" class="thumbnail"><img
											src="/fmstorefront/_ui/desktop/common/images/rewards-home-prod-img3.jpg"
											alt="Image"></a>
										<div class="caption">
											<p class="title text-capitalize">Champion&reg; $100 gift
												certificate</p>
											<p>
												iItem#: <span class="itemNum">G3</span>
											</p>
											<p>Lorem ipsum dolor sit amet, sed do eiusmod tempor.</p>
										</div>
									</div>
								</div>
							</div>
							<!--.container-->

						</div>
					</div>
					<!-- Starts: Manage Account Left hand side panel -->

				</div>
			</div>
		</section>


		<!-- Brandstrip -->

		<div class="well well-sm well-brandstrip clearfix">
			<ul class="nav ">
				<!-- <c:set var="fmComponentName" value="brandStripB2B" scope="session" />-->
				<c:set var="fmComponentName" value="brandStrip" scope="session" />

				<cms:pageSlot position="FMBrandstrip" var="feature">
					<cms:component component="${feature}" />
				</cms:pageSlot>
			</ul>
		</div>
	</section>
	<c:set var="fmComponentName" value="footerImageLinks" scope="session" />
</template:page>




<!-- InstanceBeginEditable name="Page Related Script" -->
<script src="js/jquery.elevatezoom.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
$("#prodMainImg").elevateZoom({ gallery: 'prdDetail_thumbnail', cursor: 'pointer', galleryActiveClass: "active",
			zoomWindowWidth: 300,
            zoomWindowHeight: 300,
			zoomWindowWidth: 526,
			zoomWindowHeight: 500, 
			zoomWindowPosition: 1,
			easing: true,
            zoomWindowFadeIn: 500,
            zoomWindowFadeOut: 500,
            lensFadeIn: 500,
            lensFadeOut: 500,
zoomWindowOffety: -25,
            zoomWindowOffetx: 40
});
$("#prodMainImg").bind("click", function(e) {
var ez = $('#prodMainImg').data('elevateZoom');
ez.closeAll();
return false;
});
$('#prodMainImg').on('click', function(event){
    $('#myModal').modal('toggle');
});

});

</script>

<script type="text/javascript">
$(document).ready(function() {
	
  $('.slider4').bxSlider({
    slideWidth: 302,
    minSlides: 2,
    maxSlides: 4,
    moveSlides: 1,
    slideMargin: 30,
	nextText: '<span class="fa fa-chevron-right"></span>',
	prevText: '<span class="fa fa-chevron-left"></span>'
  });
});
</script>
