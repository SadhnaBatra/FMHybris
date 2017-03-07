<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
	<div class="manageUserPanel rightHandPanel viewUnit clearfix">
		<h2 class="text-uppercase">License Plate Lookup</h2>
		<div class="row">
			<div class="col-md-12">
				<p>Select the state and enter the number for a license plate to
					find the Federal-Mogul Motorparts for a vehicle</p>
			</div>
		</div>
		<!-- Ends: Manage Account Right hand side panel -->
		<form class="" method="POST" id="LicensePlateRequestForm"
			commandName="LicensePlateRequestForm"
			action="${request.contextPath}/catalog/license-plate"
			enctype="multipart/form-data">
			<div class="clearfix">
				<div class="col-lg-2 col-md-2 col-sm-2 lftPad_0">
					<input type="hidden" id="state" name="state" />
					<div class="form-group">
						<label for="descFile">State</label> <select id="shipToSelect"
							required="required" class="form-control" name="state">
							   <option value="">State</option>
						<!-- 	<option value="default" selected="">State</option> -->
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
					</div>

				</div>
				<div class="col-lg-4 col-md-4 col-sm-4">

					<div class="form-group">
						<label for="descFile">License Plate</label> <input type="text"
							required="required" placeholder=""  maxlength="8" class="form-control" id="descFile" name="licensePlate" value="${noLicensePlate}">
						<div style="display: none;" id="licensePlateInput" class="errorMsg fm_fntRed">Only one space required</div>
					</div>

				</div>

				<div class="col-lg-6 col-md-6 col-sm-6 topMargn_30">
					<div class="form-group">
					    <div class="form-group topMargn_25"> <button onclick="return validateLicensePlate();" class="btn  btn-sm btn-fmDefault">Look it Up</button> </div>
						<%-- <a class="btn  btn-sm btn-fmDefault " id="upload_order" href="${request.contextPath}/catalog/license-plate">Look
							It Up</a> --%>
					</div>
				</div>

			</div>
			<c:if test="${LicenseError ne null }">
				<div class="clearfix championLeftImagePanelNew1" id="noDataLicensePlate"> There is no data available for the searched License Plate.  </div>
			</c:if>
		</form>
	</div>
</div>