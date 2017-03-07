
<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:if test="${empty displayIFrame}">
	<c:set var="displayIFrame" value="${false}" />
</c:if>


<div id="fade"></div>
<div id="modal">
    <img id="loader" src="/fmstorefront/_ui/desktop/common/images/spinner.gif" />
</div>
<!-- begin tabs going in wide content -->
              
<ul class="nav nav-tabs content-tabs appLkpTabContent" id="appLkpTabContent" role="tablist">
    <li class="ymmTabHeading visible-lg visible-md" >
      <div class="panel-heading">
        <h3 class="panel-title"><spring:theme code="B2BHomepage.partSearch.title"/></h3>
      </div>
    </li>
	<c:if test="${VPNError ne null or LicenseError ne null}">
 		<li><a href="#vehicle" role="tab" data-toggle="tab"><spring:theme code="B2BHomepage.partSearch.Vehicle"/></a></li>
	</c:if>
	<c:if test="${VPNError eq null and LicenseError eq null}">
	   <li class="active"><a href="#vehicle" role="tab" data-toggle="tab"><spring:theme code="B2BHomepage.partSearch.Vehicle"/></a></li>
	</c:if>
    <c:if test="${LicenseError ne null}">
        <li class="active"> <a href="#licensePlate" data-toggle="tab" role="tab"><spring:theme code="B2BHomepage.partSearch.licensePlate"/> </a></li>
	</c:if>
	<c:if test="${LicenseError eq null}">
        <li> <a href="#licensePlate" data-toggle="tab" role="tab"><spring:theme code="B2BHomepage.partSearch.licensePlate"/></a></li>
	</c:if>
	<c:if test="${VPNError ne null}">
        <li class="active"><a href="#VIN" role="tab" data-toggle="tab" class="text-uppercase"><spring:theme code="B2BHomepage.partSearch.vin"/></a></li>
	</c:if>
	<c:if test="${VPNError eq null}">
		<li><a href="#VIN" role="tab" data-toggle="tab" class="text-uppercase"><spring:theme code="B2BHomepage.partSearch.vin"/></a></li>
    </c:if>
	<li><a href="#competitor" role="tab" data-toggle="tab"><spring:theme code="B2BHomepage.partSearch.competitor"/></a></li>
  </ul>
  <!--/.nav-tabs.content-tabs -->

<div class="tab-content appLkpTabContentBlock">
    <c:if test="${VPNError ne null or LicenseError ne null}">
	   <div class="tab-pane ymm-tab-pane fade in clearfix" id="vehicle">
            <form id="normalYMMForm" class="vehicle-lookup ymmForm topMargn_35 pull-left col-sm-4" action="#" method="get">
    			<input type="hidden" id="ymmquery" name="q" value="" /> 
                <input type="hidden" name="text" value="#" />
                <h3>Vehicle</h3>
    			<div class="form-group select-box-holder">
    				<select id="vehiclesegment" class="form-control" style="width: 127% !important;">
    					<option value="vehiclesegment PaddingTopBtn"><spring:theme code="partsearch.VehicleType"/></option>
    					<option value="Passenger Car Light Truck PaddingTopBtn"><spring:theme code="partsearch.PassengerCarLightTruck"/></option>
    					<option value="Commercial, Industrial, Ag. PaddingTopBtn"><spring:theme code="partsearch.commercialVehicle"/></option>
    					<option value="Performance PaddingTopBtn"><spring:theme code="partsearch.performance"/></option>
    					<option value="Marine PaddingTopBtn"><spring:theme code="partsearch.marine"/></option>
    					<option value="Powersport PaddingTopBtn"><spring:theme code="partsearch.Powersport"/></option>
    					<option value="Small Engine PaddingTopBtn"><spring:theme code="partsearch.smallEngine"/></option>
    				</select>
                    <a class="select-box" title="" style=""><i class="fa fa-angle-down select-box-arrow"></i></a>
    			</div>
    			<div class="form-group">
    				<select id="year" class="form-control" style="width: 127%; !important" disabled>
    					<option value="year"><spring:theme code="partsearch.year"/></option>
    				</select>
    			</div>
    			<div class="form-group">
    				<select id="make" class="form-control" style="width: 127%; !important" disabled>
    					<option><spring:theme code="partsearch.make"/></option>
    				</select>
    			</div>
    			<div class="checkbox">
    				<select id="modelhome" class="form-control" style="width: 127%; !important" disabled>
    					<option><spring:theme code="partsearch.model"/></option>
    				</select>
    			</div>
    			<div class="form-group topMargn_20">
    				<div id="lookitup" style="padding-bottom:�5px;� padding-bottom:�0.5rem;">
    					<a id="ymmSearch" href="javascript:SearchQuery(${displayIFrame})"
    						class="btn btn-sm btn-fmDefault"><spring:theme code="partsearch.lookItUp"/></a>
    				</div>
    			</div>				
    		</form>
    		<div class="pull-right visible-lg visible-md visible-sm vehicleBg">
    			<img src="${commonResourcePath}/images/partner-img.png" class="img-responsive">
    		</div>
    	</div>
	</c:if>
	<c:if test="${VPNError eq null and LicenseError eq null}">
        <div class="tab-pane ymm-tab-pane fade in active clearfix" id="vehicle">
    		<form id="normalYMMForm" class="vehicle-lookup ymmForm topMargn_35 pull-left col-sm-4" action="#" method="get">
    			<input type="hidden" id="ymmquery" name="q" value="" /> 
                <input type="hidden" name="text" value="#" />
                <h3>Vehicle</h3>
    			<div class="form-group select-box-holder">
    				<select id="vehiclesegment" class="form-control" style="width: 127% !important;">
    					<option value="vehiclesegment"><spring:theme code="partsearch.VehicleType"/></option>
    					<option value="Passenger Car Light Truck"><spring:theme code="partsearch.PassengerCarLightTruck"/></option>
    					<option value="Commercial,Industrial Ag."><spring:theme code="partsearch.commercialVehicle"/></option>
    					<option value="Performance"><spring:theme code="partsearch.performance"/></option>
    					<option value="Marine"><spring:theme code="partsearch.marine"/></option>
    					<option value="Powersport"><spring:theme code="partsearch.Powersport"/></option>
    					<option value="Small Engine"><spring:theme code="partsearch.smallEngine"/></option>
    				</select>
                    <a class="select-box" title="" style=""><i class="fa fa-angle-down select-box-arrow"></i></a>
    			</div>
    			<div class="form-group select-box-holder">
    				<select id="year" class="form-control" style="width: 127%; !important" disabled>
    					<option value="year"><spring:theme code="partsearch.year"/></option>
    				</select>
                    <a class="select-box" title="" style=""><i class="fa fa-angle-down select-box-arrow"></i></a>
    			</div>
    			<div class="form-group select-box-holder">
    				<select id="make" class="form-control" style="width: 127%; !important" disabled>
    					<option><spring:theme code="partsearch.make"/></option>
    				</select>
                    <a class="select-box" title="" style=""><i class="fa fa-angle-down select-box-arrow"></i></a>
    			</div>
    			<div class="form-group select-box-holder">
    				<select id="modelhome" class="form-control" style="width: 127%; !important" disabled>
    					<option><spring:theme code="partsearch.model"/></option>
    				</select>
                    <a class="select-box" title="" style=""><i class="fa fa-angle-down select-box-arrow"></i></a>
    			</div>
    			<div class="form-group topMargn_20">
    				<div id="lookitup" style="padding-bottom:5px;padding-bottom:0.5rem;">
    					<a id="ymmSearch" href="javascript:SearchQuery(${displayIFrame})"
    						class="btn btn-sm btn-fmDefault"><spring:theme code="partsearch.lookItUp"/></a>
    				</div>
    			</div>
    		</form>
    		<div class="pull-right visible-lg visible-md visible-sm vehicleBg">
    			<img src="${commonResourcePath}/images/partner-img.png" class="img-responsive">
    		</div>
    	</div>
	</c:if>
    <c:set var="formTarget" value="" />
    <c:if test="${displayIFrame}">
    	<c:set var="formTarget" value="_parent" />
    </c:if>
    <c:if test="${LicenseError ne null}">
	   <div class="tab-pane fade ymm-tab-pane active in clearfix" id="licensePlate">
            <form:form target="${formTarget}" class="ymmForm topMargn_35 pull-left col-sm-5" method="POST" id="LicensePlateRequestForm" commandName="LicensePlateRequestForm"  action="${request.contextPath}/catalog/license-plate" enctype="multipart/form-data">
                <input type="hidden" id="state" name="state" />
                <h3>License Plate</h3>
                <div class="form-group regFormFieldGroup"> 
                    <c:if test="${noLicensePlate ne null}">
                        <input id="descFile" required="required" maxlength="8" name="licensePlate" class="form-control" type="text" placeholder="License Plate" value="${noLicensePlate}">
                    </c:if> 
                    <c:if test="${noLicensePlate eq null}">
                        <input id="descFile" required="required" maxlength="8" name="licensePlate" class="form-control" type="text" placeholder="License Plate">
                    </c:if> 
                    <div style="display: none;" id="licensePlateInput" class="errorMsg fm_fntRed">Only one space required</div>
                </div>
                <div class="form-group select-box-holder regFormFieldGroup"> 
                    <select id="state" name="state" required="required" class="form-control">
                        <option value=""><spring:theme code="partsearch.state"/></option>
                        <c:forEach items="${regionData}" var="region">		
                            <c:choose>
            					<c:when test="${selectedStateIsoCode eq region.isocodeShort}">
            						<option value="${region.isocodeShort}" selected="selected" >${region.name}</option>
            					</c:when>
            					<c:otherwise>
            						<option value="${region.isocodeShort}" >${region.name}</option>
            					</c:otherwise>
                            </c:choose>
            		    </c:forEach>
                    </select>
                    <a class="select-box" title="" style=""><i class="fa fa-angle-down select-box-arrow"></i></a>
                </div>
                <c:if test="${LicenseError ne null}">
                    <div id="noDataLicensePlate"><spring:theme code="partsearch.license.plate.error"/> </div>
                </c:if>  
                <div class="form-group topMargn_25">
                    <input type="hidden" name="sourceRequestURL" id="sourceRequestURL" value="fromHome" /> 
                    <button class="btn btn-sm btn-fmDefault"><spring:theme code="partsearch.lookItUp"/></button> 
                </div>
            </form:form>
            <div class="pull-right visible-lg visible-md visible-sm vehicleBg topMargn_10">
                <img src="${commonResourcePath}/images/partner-img.png" class="img-responsive">
            </div>
        </div>
    </c:if>
    <c:if test="${LicenseError eq null}">
	   <div class="tab-pane fade ymm-tab-pane clearfix" id="licensePlate">
            <form:form target="${formTarget}" class="ymmForm topMargn_35 pull-left col-sm-5" method="POST" id="LicensePlateRequestForm" commandName="LicensePlateRequestForm"  action="${request.contextPath}/catalog/license-plate" enctype="multipart/form-data">
                <input type="hidden" id="state" name="state" />
                <h3>License Plate</h3>
                <div class="form-group regFormFieldGroup"> 
                    <input id="descFile" maxlength="8" required="required" name="licensePlate" class="form-control" type="text" placeholder="License Plate">
                    <input type="hidden" name="sourceRequestURL" id="sourceRequestURL" value="fromHome" />
                    <div style="display: none;" id="licensePlateInput" class="errorMsg fm_fntRed">Only one space required</div>
                </div>
                <div class="form-group select-box-holder regFormFieldGroup"> 
                    <select id="state" name="state" required="required" class="form-control">
                        <option value=""><spring:theme code="partsearch.state"/></option>
                        <c:forEach items="${regionData}" var="region">		
                            <option value="${region.isocodeShort}">${region.name}</option>
            		    </c:forEach>
                    </select>
                    <a class="select-box" title="" style=""><i class="fa fa-angle-down select-box-arrow"></i></a>
                </div>
                <div class="form-group topMargn_25"> 
                    <button onclick="return validateLicensePlate();" class="btn btn-sm btn-fmDefault"><spring:theme code="partsearch.lookItUp"/></button> 
                </div>
            </form:form>
            <div class="pull-right visible-lg visible-md visible-sm vehicleBg topMargn_10">
                <img src="${commonResourcePath}/images/partner-img.png" class="img-responsive">
            </div>
        </div>
    </c:if>
    <c:if test="${VPNError ne null}">
        <div class="tab-pane fade ymm-tab-pane active in clearfix" id="VIN">
            <form:form target="${formTarget}" class="ymmForm topMargn_35 pull-left col-sm-5" method="POST" id="vinLookupFormData" commandName="vinLookupFormData"  action="${request.contextPath}/catalog/vin" enctype="multipart/form-data">
                <h3>VIN</h3>
                <div class="form-group regFormFieldGroup"> 
                    <c:if test="${notFoundVPN ne null}">
                        <input id="vin" class="form-control" required="" type="text" placeholder="VIN" name="vin" value="${notFoundVPN}">
				    </c:if> 
				    <c:if test="${notFoundVPN eq null}">
                        <input id="vin" class="form-control" required="" type="text" placeholder="VIN" name="vin">
                    </c:if>
				    <div style="display: none;" id="vinInput" class="errorMsg fm_fntRed">Minimum 17	characters required</div>
                </div> 
                <c:if test="${VPNError ne null}">
                    <div id="vinNoData"><spring:theme code="partsearch.vin.error"/></div>
                </c:if>              
                <div class="form-group topMargn_25"> 
                    <input type="hidden" name="sourceRequestURL" id="sourceRequestURL" value="fromHome" />
                    <button type="submit" onclick="return validateVIN();" class="btn btn-sm btn-fmDefault">Look it Up</button>
                </div>   
            </form:form>
            <div class="pull-right visible-lg visible-md visible-sm vehicleBg topMargn_10">
                <img src="${commonResourcePath}/images/partner-img.png" class="img-responsive">
            </div>
        </div>
    </c:if>
    <c:if test="${VPNError eq null}">
        <div class="tab-pane fade ymm-tab-pane clearfix" id="VIN">
            <form:form target="${formTarget}" class="ymmForm topMargn_35 pull-left col-sm-5" method="POST" id="vinLookupFormData" commandName="vinLookupFormData"  action="${request.contextPath}/catalog/vin" enctype="multipart/form-data">
                <h3>VIN</h3>
                <div class="form-group regFormFieldGroup"> 
                    <input id="vin" class="form-control" required="required" type="text" placeholder="VIN" name="vin">
                    <input type="hidden" name="sourceRequestURL" id="sourceRequestURL" value="fromHome" />
                    <div style="display: none;" id="vinInput" class="errorMsg fm_fntRed">Minimum 17 characters required</div>
                </div>            
                <div class="form-group topMargn_25"> 
                    <button type="submit" onclick="return validateVIN();" class="btn btn-sm btn-fmDefault"><spring:theme code="partsearch.lookItUp"/></button>
                </div>      
            </form:form>
            <div class="pull-right visible-lg visible-md visible-sm vehicleBg topMargn_10">
                <img src="${commonResourcePath}/images/partner-img.png" class="img-responsive">
            </div>
        </div>
    </c:if>
	
	<div class="tab-pane fade ymm-tab-pane clearfix" id="competitor">
	    <form:form target="${formTarget}" class="pull-left col-sm-5 ymmForm topMargn_35" method="POST" id="partInterchangeForm" commandName="partInterchangeForm"  action="${request.contextPath}/catalog/competitor-interchange" enctype="multipart/form-data">
            <h3>Competitor</h3>
            <div class="form-group regFormFieldGroup"> 
                <input id="externalPart" required="required" class="form-control" type="text" placeholder="Part Interchange#" name="externalPart">
            </div>               
            <div class="form-group topMargn_25">
                <button type="submit" class="btn btn-sm btn-fmDefault"><spring:theme code="partsearch.lookItUp"/></button>
            </div>      
        </form:form>
        <div class="pull-right visible-lg visible-md visible-sm vehicleBg topMargn_10">
            <img src="${commonResourcePath}/images/partner-img.png" class="img-responsive">
        </div>
	</div>
</div>
            

<c:set var="fmComponentName" value="troubleShoot" scope="session" />
<script>
	function SearchQuery(displayIFrame) {
		
		var sel_vehicleSegment = $('#vehiclesegment option:selected').text();
		var sel_year = $('#year option:selected').text();
		var sel_make = $('#make option:selected').text();
		var sel_model = $('#modelhome option:selected').text();

		var pathName = '';
		var win_url = window.location.href;
		if (win_url.indexOf("iframe") != -1) {
			win_url = win_url
		      .replace("iframe","");
		}
		try {
			if (win_url.indexOf("/USD") != -1) {
				pathName = win_url
						.substring(0, win_url.lastIndexOf("/USD") + 5);
			} else if (win_url.indexOf("?site") != -1) {
				pathName = win_url.substring(0,
						win_url.lastIndexOf("/?site") + 1)
						+ win_url.substring(win_url.lastIndexOf("site=") + 5,
								win_url.length) + "/en/USD/";

			} else {
				pathName = win_url+"federalmogul/en/USD/";
			}

		} catch (e) {
			alert(e);
		}
		if(sel_model == 'modelhome' ||  sel_model == ''){
			$('#ymmSearch').addClass('disabled');
			alert("Please select Model");
			return;
		}
		var ymmCode=sel_year.trim()+sel_make.trim()+sel_model.trim().replace(/&/g,'')+"|";
		var queryVal= pathName + "search?q=:name-asc:vehiclesegment:"+ymmCode+sel_vehicleSegment.trim()+":year:"+ymmCode+sel_year.trim()+":make:"+ymmCode+sel_make.trim()+":model:"+ymmCode+sel_model.trim().replace(/&/g,'')+ "&text=#";
		
		//var queryVal = pathName + "search?q=:name-desc:vehiclesegment:"
				//+ sel_vehicleSegment.trim() + ":year:" + sel_year.trim()
				//+ ":make:" + sel_make.trim() + ":model:" + sel_model.trim()
				//+ "&text=#";
		$("#ymmSearch").prop("href", queryVal);
		if (displayIFrame) {
			window.parent.location = queryVal;
		} else {
			location.href = queryVal;
		}
	}
</script>
