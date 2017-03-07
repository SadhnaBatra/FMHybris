<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="product" required="true"
	type="de.hybris.platform.commercefacades.product.data.ProductData"%>
<%@ attribute name="galleryImages" required="true" type="java.util.List"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<!-- Custom tags for FederalMogul -->
<%@ taglib prefix="fmproduct" tagdir="/WEB-INF/tags/desktop/fmproduct"%>

<div class="row">
	<div class="learningPanel col-lg-12">
		<div class="panel-heading clearfix learningPanelTitle">
			<h3 class="panel-title">
				<span class="text-uppercase">You might also be interested in:</span>
			</h3>
		</div>
		<div class="">
			<div class="row">
				<div class=" col-sm-3 col-md-3 col-xs-6">
					<div class="lms_intro_hover ">
						<p>
							<img src="images/suggested_prod_1.jpg" alt="learning" />
						</p>
						<div class="caption">
							<div class="blur"></div>
							<div class="caption-text">
								<h5>Wagner Disc Brake Rotor (BD126212)</h5>
								<p class="lms_desc">Lorem ipsum dolor sit amet, Consectetur
									adipisicing elit, sed do eiusmod tempor incididunt ut labore et
									dolore magna aliqua.</p>
								<div class="lms_btm_link">
									<span class="pull-left  text-capitalize"><a href="#">
											Read more <span class="fa fa-angle-right "></span>
									</a></span> <span class="pull-right  text-capitalize"><a href="#"><span
											class="fa fa-share-alt"> </span></a></span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class=" col-sm-3 col-md-3 col-xs-6">
					<div class="lms_intro_hover ">
						<p>
							<img src="images/suggested_prod_2.jpg" alt="learning" />
						</p>
						<div class="caption">
							<div class="blur"></div>
							<div class="caption-text">
								<h5>Wagner Disc Brake Caliper Guide Pin Boot Kit (H18052)</h5>
								<p class="lms_desc">Lorem ipsum dolor sit amet, consectetur
									adipiscing elit, sed do eiusmod tempor incididunt ut labore et
									dolore magna aliqua.</p>
								<div class="lms_btm_link">
									<span class="pull-left  text-capitalize"><a href="#">
											Read more <span class="fa fa-angle-right "></span>
									</a></span> <span class="pull-right  text-capitalize"><a href="#"><span
											class="fa fa-share-alt"> </span></a></span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class=" col-sm-3 col-md-3 col-xs-6">
					<div class="lms_intro_hover ">
						<p>
							<img src="images/suggested_prod_3.jpg" alt="learning" />
						</p>
						<div class="caption">
							<div class="blur"></div>
							<div class="caption-text">
								<h5>Wagner Disc Brake Caliper Bolt (H5091)</h5>
								<p class="lms_desc">Lorem ipsum dolor sit amet, consectetur
									adipiscing elit, sed do eiusmod tempor incididunt ut labore et
									dolore magna aliqua. y</p>
								<div class="lms_btm_link">
									<span class="pull-left  text-capitalize"><a href="#">
											Read more <span class="fa fa-angle-right "></span>
									</a></span> <span class="pull-right  text-capitalize"><a href="#"><span
											class="fa fa-share-alt"> </span></a></span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class=" col-sm-3 col-md-3 col-xs-6">
					<div class="lms_intro_hover ">
						<p>
							<img src="images/suggested_prod_4.jpg" alt="learning" />
						</p>
						<div class="caption">
							<div class="blur"></div>
							<div class="caption-text">
								<h5>Wagner Disc Brake Caliper Guide Pin Boot Kit (H18052)</h5>
								<p class="lms_desc">Lorem ipsum dolor sit amet, consectetur
									adipiscing elit, sed do eiusmod tempor incididunt ut labore et
									dolore magna aliqua.</p>
								<div class="lms_btm_link">
									<span class="pull-left  text-capitalize"><a href="#">
											Read More <span class="fa fa-angle-right "></span>
									</a></span> <span class="pull-right  text-capitalize"><a href="#"><span
											class="fa fa-share-alt"> </span></a></span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>