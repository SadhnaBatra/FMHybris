<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="errorNoResults" required="false"
	type="java.lang.String"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/desktop/formElement"%>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/desktop/store"%>

<%-- <c:url value="/where-to-buy/homePageFindStores"	var="storeFinderFormAction" /> --%>
<c:url value="/where-to-buy/findStores"	var="storeFinderFormAction" />


<style type="text/css">
#postalzip {
	font-family: Arial, Verdana;
	font-size: 62.5%;
	padding: 10px;
}

#result_info {
	padding: 5px;
	color: #069;
	font-size: 1.2em;
}
</style>
<!-- Where to buy panel -->

<section class="whereToBuyMap">
	<img src="${commonResourcePath}/images/map_bg.jpg" alt=""
		class="img-responsive visible-lg visible-md" /> <img
		src="${commonResourcePath}/images/mobile_map_bg.jpg" alt=""
		class="img-responsive visible-xs" />
</section>
<section class="well well-whereToBuy">
	<div class="container">
		<div class="row">
			<form:form class="navbar-form text-center clearfix visible-lg" action="${storeFinderFormAction}" method="POST" commandName="storeFinderForm">
				<div class="form-group visible-lg-inline-block wtb-control">
					<h2>
						<span class="fa fa-map-marker"></span><span
							class="wheretoBuyCustomTitle"> <spring:theme code="anonymousHome.wheretobuy" text="WHERE TO BUY? " /></span>
					</h2>
				</div>
				<div class="form-group visible-lg-inline-block wtb-control">
					<select class="form-control width100" path="brand" id="brand"
						name="brand" required="required">
						<option class="text-uppercase" value="" id="brand"><spring:theme code="brand.select"/></option>
						<option class="text-uppercase" value="ANCO" id="brand"><spring:theme code="wheretobuy.anco"/></option>
						<option class="text-uppercase" value="Champion" id="brand"><spring:theme code="wheretobuy.champion"/></option>
						<option class="text-uppercase" value="Fel-Pro" id="brand"><spring:theme code="wheretobuy.felpro"/></option>
						<option class="text-uppercase" value="MOOG Driveline" id="brand"><spring:theme code="wheretobuy.moogDriveline"/></option>
						<option class="text-uppercase" value="MOOG Chassis" id="brand"><spring:theme code="wheretobuy.moogChassis"/></option>
						<option value="National Seals"><spring:theme code="wheretobuy.national"/></option>
						<option value="Sealed Power"><spring:theme code="wheretobuy.sealedPowerPRODiesel"/></option>
						<option value="Wagner Brake"><spring:theme code="wheretobuy.wagnerBrake"/></option>
						<option value="Wagner Lighting"><spring:theme code="wheretobuy.wagnerLighting"/></option>
					</select>
				</div>
			<div class="form-group visible-lg-inline-block wtb-control regFormFieldGroup">
						<input type="text" path="q" class="form-control width100"
									placeholder="ZIP/Postal Code" name="q" required="required"/>
			</div>	
								
			<input type="hidden" name="radius" path="radius" id="radius" value="" />	
								
			<input type="hidden" name="showNearest" path="showNearest" id="showNearest" value="40" />	

				<div class="form-group visible-lg-inline-block wtb-control">
					<select class="form-control width100" path="shopType"
						name="shopType" id="shopType">
						<option value="Both"><spring:theme code="shoptype.both"/></option>
						<option value="Jobber" id="shopType"><spring:theme code="shoptype.jobber"/></option>
						<option value="Installer" id="shopType"><spring:theme code="shoptype.installer"/></option>
					</select>
				</div>
				<div class="form-group visible-lg-inline-block wtb-control">
					<button class="btn btn-fmDefault storefindersearchbutton"
						type="submit">Search</button>

				</div>

			</form:form>
		</div>
	</div>
</section>
