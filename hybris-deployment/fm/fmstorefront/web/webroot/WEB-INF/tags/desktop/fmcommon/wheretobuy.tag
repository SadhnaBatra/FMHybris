<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>

<!-- Where to buy panel -->

	<section class="whereToBuyMap">
		<img src="http://localhost:9001/fmstorefront/_ui/desktop/common/images/map_bg.jpg" alt=""
			class="img-responsive visible-lg visible-md" /> <img
			src="http://localhost:9001/fmstorefront/_ui/desktop/common/images/mobile_map_bg.jpg" alt=""
			class="img-responsive visible-xs" />
	</section>
	<section class="well well-whereToBuy">
		<div class="container">
			<div class="row">
				<form class="navbar-form text-center clearfix visible-lg"
					role="search">
					<div class="form-group visible-lg-inline-block wtb-control">
						<h2>
							<span class="fa fa-map-marker"></span><span
								class="wheretoBuyCustomTitle"> WHERE TO BUY?</span>
						</h2>
					</div>
					<div class="form-group visible-lg-inline-block wtb-control">
						<select class="form-control">
							<option>Brand</option>
							<option class="text-uppercase" selected>FEL-PRO</option>
						</select>
					</div>
					<div class="form-group visible-lg-inline-block wtb-control ">
						<input type="text" class="form-control width75" name="zip"
							placeholder="ZIP/Postal Code" value="48084" maxlength="6">
					</div>
					<div class="form-group visible-lg-inline-block wtb-control">
						<select class="form-control">
							<option>Shop Type</option>
							<option class="text-uppercase" selected>PARTS STORE</option>
						</select>
					</div>
					<div class="form-group visible-lg-inline-block wtb-control">
						<button type="submit"
							class="btn  btn-sm btn-fmDefault text-uppercase">Search</button>
					</div>
				</form>
				<form class="navbar-form text-center visible-xs clearfix"
					role="search">
					<div class="form-group col-xs-12">
						<h2>
							<span class="fa fa-map-marker"></span><span
								class="wheretoBuyCustomTitle"> WHERE TO BUY?</span>
						</h2>
					</div>
					<div class="form-group col-xs-4">
						<select class="form-control">
							<option>Brand</option>
							<option selected class="text-uppercase">FEL-PRO</option>
						</select>
					</div>
					<div class="form-group col-xs-3">
						<input type="text" class="form-control width75" name="zip"
							placeholder="ZIP/Postal Code" value="48084" maxlength="6">
					</div>
					<div class="form-group col-xs-5">
						<select class="form-control">
							<option>Shop Type</option>
							<option selected class="text-uppercase">PARTS STORE</option>
						</select>
					</div>
					<div class="form-group col-xs-12">
						<button type="submit"
							class="btn  btn-sm btn-fmDefault text-uppercase">Search</button>
					</div>
				</form>
			</div>
		</div>
	</section>
