<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<section class="accountDetailPage pageContet onlineTools">
	<div class="container manageAccount contentPage">
		<div class="row">
			<!-- Starts: Manage Account Left hand side panel -->

			<!-- Ends: Manage Account Left hand side panel -->

			<!-- Starts: Manage Account Right hand side panel -->
			<div
				class="col-lg-9 col-md-9 col-sm-9 col-xs-12 learningCenter contentRHS">
				
				<c:set var="fmComponentName" value="onlineToolOverView" scope="session" />
				<sec:authorize ifAnyGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMADMINGROUP,ROLE_B2BADMINGROUP">
				<sec:authorize ifNotGranted="ROLE_FMB2T">
				<div class="row">
					<div class="col-lg-6 col-md-6 col-xs-12 ">
						<div class="bgwhite orderPop">
							<cms:pageSlot position="OTOverViewFileDownloads" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
						</div>
					</div>

					<div class="col-lg-6 col-md-6 col-xs-12 ">
						<div class="bgwhite orderPop">
							<cms:pageSlot position="OTOverViewResearch" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
						</div>
					</div>

					<div class="col-lg-6 col-md-6 col-xs-12 topMargn_25">
						<div class="bgwhite orderPop">
							<input type="hidden" id="nabscode" value="${nabsAccCode}" />
							<cms:pageSlot position="OTOverViewOrderPOPMaterials"
								var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
						</div>
					</div>


					<div class="col-lg-6 col-md-6 col-xs-12 topMargn_25">
						<div class="bgwhite orderPop">
							<cms:pageSlot position="OTOverViewDigitalAssets" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
						</div>
					</div>
				</div>
				</sec:authorize>
				</sec:authorize>
				
				<sec:authorize ifAnyGranted="ROLE_FMB2T">
				<div class="row">
					<div class="col-lg-6 col-md-6 col-xs-12 ">
						<div class="bgwhite orderPop">
							<cms:pageSlot position="OTOverViewResearch" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
						</div>
					</div>
					<div class="col-lg-6 col-md-6 col-xs-12">
						<div class="bgwhite orderPop">
							<cms:pageSlot position="OTOverViewDigitalAssets" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
						</div>
					</div>
				</div>
				</sec:authorize> 
			</div>
			<!-- Ends: Manage Account Right hand side panel -->
		</div>
	</div>
</section>