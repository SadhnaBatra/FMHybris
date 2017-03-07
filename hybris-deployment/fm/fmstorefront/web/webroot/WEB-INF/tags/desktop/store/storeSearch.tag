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

<c:url value="/where-to-buy/findStores" var="storeFinderFormAction" />
<c:url value="/where-to-buy/position" var="nearMeStorefinderFormAction" />

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

<section class="accountDetailPage pageContet whereToBuy">
	<div class="container cultureDiversity">
		<div class="row">
			<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
				<div class="panel panel-default manageAccountLinks clearfix">
					<div class="panel-body">
						<h3 class="text-uppercase"><spring:theme code="storeFinder.link" text="Where To Buy" /> </h3>

						<form:form action="${storeFinderFormAction}" method="POST"
							commandName="storeFinderForm">
							<div class="form-group  regFormFieldGroup ">
								<label for="country" class="">Country</label> <form:select
									class="form-control width237" name="country" id="country" path="country"
									onchange="getComboA(this)">
									<c:choose>
									<c:when test="${storeFinderForm.country eq 'us'}">
									<option value="us" id="us" selected="selected"><spring:theme code="country.us"/></option>
									<option value="ca" id="canada"><spring:theme code="country.canada"/></option>
									</c:when>
									<c:when test="${storeFinderForm.country eq 'ca'}">
									<option value="ca" id="canada" selected="selected"><spring:theme code="country.canada"/></option>
									<option value="us" id="us"><spring:theme code="country.us"/></option>
									</c:when>
									<c:otherwise>
									<option value="us" id="us"><spring:theme code="country.us"/></option>
									<option value="ca" id="canada"><spring:theme code="country.canada"/></option>
									</c:otherwise>
									</c:choose>
								</form:select>
							</div>

							<div class="form-group  regFormFieldGroup ">
								<label for="brand" class="">Brand <span
									class="fm_fntRed">*</span></label>
								<form:select class="form-control width237" path="brand"
									id="brand" name="brand" required="required">									
										<option value="" id="brand" selected="selected"><spring:theme code="brand.select"/></option>
										<option value="ANCO" ${storeFinderForm.brand == 'ANCO' or storeFinderForm.brand == 'WAN'? 'selected="selected"' : ''} id="brand"><spring:theme code="wheretobuy.anco"/></option>
										<option value="Champion" ${storeFinderForm.brand == 'Champion' or storeFinderForm.brand == 'CCH' or storeFinderForm.brand == 'CHFLF' or storeFinderForm.brand == 'KCH' or storeFinderForm.brand == 'WCH'? 'selected="selected"' : ''} id="brand"><spring:theme code="wheretobuy.champion"/></option>
										<option value="Fel-Pro" ${storeFinderForm.brand == 'Fel-Pro' or storeFinderForm.brand == 'FELPF'? 'selected="selected"' : ''} id="brand"><spring:theme code="wheretobuy.felpro"/></option>
										<option value="MOOG Driveline" ${storeFinderForm.brand == 'MOOG Driveline' or storeFinderForm.brand == 'HMG' or storeFinderForm.brand == 'AMG' or storeFinderForm.brand == 'NMG'? 'selected="selected"' : ''} id="brand"><spring:theme code="wheretobuy.moogDriveline"/></option>
										<option value="MOOG Chassis" ${storeFinderForm.brand == 'MOOG Chassis' ? 'selected="selected"' : ''} id="brand"><spring:theme code="wheretobuy.moogChassis"/></option>
										<option value="National Seals" ${storeFinderForm.brand == 'National Seals' or storeFinderForm.brand == 'NAT'? 'selected="selected"' : ''}  id="brand" ><spring:theme code="wheretobuy.national"/></option>
										<option value="Sealed Power" ${storeFinderForm.brand == 'Sealed Power' or storeFinderForm.brand == 'SLDPF/SPDF/FPDF'? 'selected="selected"' : ''} id="brand" ><spring:theme code="wheretobuy.sealedPowerPRODiesel"/></option>
										<option value="Wagner Brake" ${storeFinderForm.brand == 'Wagner Brake' or storeFinderForm.brand == 'WAG' or storeFinderForm.brand == 'MWG' or storeFinderForm.brand == 'MQS' or storeFinderForm.brand == 'MSV'? 'selected="selected"' : ''} id="brand" ><spring:theme code="wheretobuy.wagnerBrake"/></option>
										<option value="Wagner Lighting" ${storeFinderForm.brand == 'Wagner Lighting' or storeFinderForm.brand == 'WGLF' or storeFinderForm.brand == 'LWL'? 'selected="selected"' : ''} id="brand" ><spring:theme code="wheretobuy.wagnerLighting"/> </option>															
								</form:select>
							</div>


							<div class="form-group  regFormFieldGroup ">
								<label for="billZipPostalCode" class=""><span
									class="text-uppercase">Zip</span> / Postal Code<span
									class="fm_fntRed">*</span></label>
								<form:input type="text" path="q" class="form-control width237"
									placeholder="ZIP/Postal Code" name="q" id="postal_zip_code"
									onchange="javascript:validatezipcode(this)" required="required"/>


							</div>
							<div class="form-group  regFormFieldGroup ">
								<label for="Radius" class="">Radius</label>
								<form:select class="form-control width237" path="radius"
									name="radius" id="radius" required="required">									
									<option value="5" ${storeFinderForm.radius == '5' ? 'selected="selected"' : ''} ><spring:theme code="wheretobuy.r5"/></option>
									<option value="10" ${storeFinderForm.radius == '10' ? 'selected="selected"' : ''}><spring:theme code="wheretobuy.r10"/></option>
									<option value="15" ${storeFinderForm.radius == '15' ? 'selected="selected"' : ''}><spring:theme code="wheretobuy.r15"/></option>
									<option value="20" ${storeFinderForm.radius == '20' ? 'selected="selected"' : ''}><spring:theme code="wheretobuy.r20"/></option>
									<option value="50" ${storeFinderForm.radius == '50' ? 'selected="selected"' : ''}><spring:theme code="wheretobuy.r50"/></option>
									<option value="100" ${storeFinderForm.radius == '100' ? 'selected="selected"' : ''}><spring:theme code="wheretobuy.r100"/></option>		
								</form:select>
							</div>
							<div class="form-group  regFormFieldGroup ">
								<label for="showNearest" class="">Show Nearest</label>
								<form:select class="form-control width237" path="showNearest"
									name="showNearest" id="showNearest" required="required">								
									<option value="5" ${storeFinderForm.showNearest == '5' ? 'selected="selected"' : ''} >5</option>
									<option value="10" ${storeFinderForm.showNearest == '10' ? 'selected="selected"' : ''} >10</option>
									<option value="15" ${storeFinderForm.showNearest == '15' ? 'selected="selected"' : ''}>15</option>
									<option value="20" ${storeFinderForm.showNearest == '20' ? 'selected="selected"' : ''}>20</option>
									<option value="40" ${storeFinderForm.showNearest == '40' ? 'selected="selected"' : ''}>40</option>									
								</form:select>
							</div>
							<div class="form-group  regFormFieldGroup ">
								<label for="shopType" class="">Store/Shop Type(s)</label>
								<form:select class="form-control width237" path="shopType"
									name="shopType" id="shopType" required="required">
									<c:choose>
									<c:when test="${storeFinderForm.shopType eq '' || storeFinderForm.shopType eq 'null'}">
									<option value="Both" selected><spring:theme code="shoptype.both"/></option>
									<option value="Jobber"><spring:theme code="shoptype.jobber"/></option>
									<option value="Installer"><spring:theme code="shoptype.installer"/></option>
									</c:when>	
									<c:when test="${storeFinderForm.shopType eq 'Jobber'}">
									<option value="Both"><spring:theme code="shoptype.both"/></option>
									<option value="Jobber" selected><spring:theme code="shoptype.jobber"/></option>
									<option value="Installer"><spring:theme code="shoptype.installer"/></option>
									</c:when>	
									<c:when test="${storeFinderForm.shopType eq 'Installer'}">
									<option value="Both"><spring:theme code="shoptype.both"/></option>
									<option value="Jobber"><spring:theme code="shoptype.jobber"/></option>
									<option value="Installer" selected><spring:theme code="shoptype.installer"/></option>
									</c:when>						
									<c:otherwise>
									<option value="Both"><spring:theme code="shoptype.both"/></option>
									<option value="Jobber"><spring:theme code="shoptype.jobber"/></option>
									<option value="Installer"><spring:theme code="shoptype.installer"/></option> 
									</c:otherwise>
									</c:choose>	
								</form:select>
							</div>
							<br>
							<!-- <button class="btn btn-fmDefault storefindersearchbutton"
								type="submit" onclick="validateBrands(),validatezipcode()">Search</button> -->

<button class="btn btn-fmDefault storefindersearchbutton"
								type="submit" onclick="validatezipcode()">Search</button>

						</form:form>

					</div>
				</div>
			</div>
			<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 internalContent">
				<div class="internalPage rightHandPanel clearfix">
					<div class="wheretoBuyMap" id="">
						<store:storesMap storeSearchPageData="${searchPageData}"
							locationQuery="${storeFinderForm.q}"
							numberPagesShown="${numberPagesShown}" geoPoint="${geoPoint}" />
					</div>
				</div>
			</div>
		</div>
	</div>

</section>

<script type="text/javascript">
	var regexObj = {
		canada : /^[ABCEGHJKLMNPRSTVXY]\d[ABCEGHJKLMNPRSTVWXYZ]( )?\d[ABCEGHJKLMNPRSTVWXYZ]\d$/i, //i for case-insensitive
		usa : /^\d{5}(-\d{4})?$/
	}
	//$('#'+obj.id+' option:Selected').val();   
	
	
	
	function validatezipcode() {
		var countrySelect = $('#country').val();   
		//alert(countrySelect);
		var userInput = document.getElementById('postal_zip_code').value;
	
		
		var canadavar = document.getElementById('canada').value;
		var usavar = document.getElementById('us').value;
	

		var regexp = new RegExp(regexObj.canada);
		var resultStr = "";
		// check for canada at first
	
		if (regexp.test(userInput) && countrySelect=='ca') {

			//alert('Its a Valid Canadian Postal Code');

		}

		else {
			// now check for USA
			regexp = null;
			regexp = new RegExp(regexObj.usa);
			if (regexp.test(userInput) && countrySelect =='us') {

				//alert('Its a Valid USA Postal Code');

			}

			else {
					alert('Not a valid Postal Code or Zip Code');
					var elem = document.getElementById('postal_zip_code');
					elem.value="";
				
					}
		}

	}
	
	function getComboA(sel) {
		var value = sel.value;
				
	}
	
	function valid() {
		validatezipcode();	
	}
	
</script>


