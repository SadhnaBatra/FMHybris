<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>


<!--- Order in Progress PANEL -->
<div class="col-lg-4">
	<div class="panel panel-default orderProgressPanel clearfix">
		<div class="panel-body">
			<h2 class="panel-title text-uppercase orderInProgressTitle">
				<span class="fa fa-shopping-cart"></span> <strong
					class="text-uppercase">Orders in Progress</strong>
			</h2>
			<div class="orderInfoText" id="orderProgressPanel">
				<h4 class="panel-title text-uppercase ">
					<strong class="text-uppercase">Most Recently Added</strong>
				</h4>
				<p>
					As of <span>3:40PM</span> on <span>09/26/14</span>
				<p>
			</div>
		</div>

		<div id="productDescPanel" class="productDescPanel clearfix scroll-area" data-spy="scroll" 
		data-target="#orderProgressPanel" data-offset="0" style="height:100px; ">
			<div class="product clearfix">
				<div class="productImg col-xs-3">

					<img
						src="${commonResourcePath}/images/abexDiscBrake.png"
						class="img-responsive" alt="Abex Disc Brakes" />
				</div>
				<div class="prodDetail col-xs-9">
					<div class="prodName">
						<strong>Abex<sup>&reg;</sup> Commercial-Grade Disc Brake
							Pads
						</strong>
					</div>
					<div class="prodQty">
						Qty:<span>40</span>
					</div>
				</div>
			</div>
			<div class="product clearfix">
				<div class="productImg col-xs-3">
					<img
						src="${commonResourcePath}/images//nationalBearings.png"
						class="img-responsive" alt="Abex Disc Brakes" />
				</div>
				<div class="prodDetail col-xs-9">
					<div class="prodName">
						<strong>National<sup>&reg;</sup> Bearings
						</strong>
					</div>
					<div class="prodQty">
						Qty:<span>40</span>
					</div>
				</div>
			</div>
			<div class="product clearfix">
				<div class="productImg col-xs-3">
					<img
						src="${commonResourcePath}/images//abexDiscBrake.png"
						class="img-responsive" alt="Abex Disc Brakes" />
				</div>
				<div class="prodDetail col-xs-9">
					<div class="prodName">
						<strong>Abex<sup>&reg;</sup> Commercial-Grade Disc Brake
							Pads
						</strong>
					</div>
					<div class="prodQty">
						Qty:<span>40</span>
					</div>
				</div>
			</div>
			<div class="product clearfix">
				<div class="productImg col-xs-3">
					<img
						src="${commonResourcePath}/images//nationalBearings.png"
						class="img-responsive" alt="Abex Disc Brakes" />
				</div>
				<div class="prodDetail col-xs-9">
					<div class="prodName">
						<strong>National<sup>&reg;</sup> Bearings
						</strong>
					</div>
					<div class="prodQty">
						Qty:<span>40</span>
					</div>
				</div>
			</div>

		</div>

		<div class="panel-body">
			<div class="">
				<div class="reviewLnk pull-left">
					<a href="#">View my Cart [7] <span class="fa fa-chevron-right">
					</span></a>
				</div>
				<div class="pull-right">
					<a href="productlist.html" class="btn  btn-sm btn-fmDefault ">Checkout</a>
				</div>
			</div>
		</div>
	</div>
</div>