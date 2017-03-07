
<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
	<div class="manageUserPanel rightHandPanel viewUnit clearfix">
		<h2 class="text-uppercase">VIN Lookup</h2>
		<div class="row">
			<div class="col-md-12">
				<p>Enter the Vehicle identification Number of a vehicle to find
					the Federal-Mogul Motorparts available.</p>
			</div>
			<form class="" method="POST" id="vinLookupFormData"
				commandName="vinLookupFormData"
				action="${request.contextPath}/catalog/vin"
				enctype="multipart/form-data">
				<div class="clearfix">
					<div class="col-lg-4 col-md-4 col-sm-4">

						<div class="form-group">
							<div>
								<label for="descFile">Vehicle Identification Number<span
									class="required fm_fntRed">*</span></label>
							</div>
							<input type="text" value="" required="" placeholder="VIN"
								name="vin" class="form-control"
								id="vin">
							<div style="display: none;" id="vinInput" class="errorMsg fm_fntRed">Minimum 17
								charaters required</div>
						</div>

					</div>

					<div class="col-lg-8 col-md-8 col-sm-8 topMargn_30">
						<div class="form-group">
							<!-- 	<a class="btn  btn-sm btn-fmDefault " id="upload_order"
												href="#">Look It Up</a> -->
							<button type="submit" onclick="return validateVIN();"
								class="btn vinform">Look it Up</button>
						</div>
					</div>

				</div>
			</form>
			
		</div>
		<c:if test="${VPNError ne null }">
			<div id="vinNoData" class="clearfix championLeftImagePanelNew1">
				There is no data available for the searched VIN.</div>
		</c:if>
		<!-- Ends: Manage Account Right hand side panel -->
	</div>
</div>

