<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>

<section class="accountDetailPage pageContet">
	<div class="container companyOverview">
		<div class="row">

			<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 contentLHS">
				<!--- Order in Progress PANEL -->
				<div class="panel panel-default manageAccountLinks clearfix">
					<div class="panel-body">
						<h3 class="text-uppercase">Media</h3>

						<div
							class="panel-group sidebar visible-lg visible-md visible-sm productlist-lhs"
							id="accordion">
							<!-- accordian starts -->
							<div class="panel-default">
								<div class="acc-heading1"></div>
								<div class="panel-default">
									<c:forEach var="component"
										items="${slots.CompanySection.cmsComponents}"
										varStatus="status">

										<div class="acc-heading1">
											<c:url
												value="/about-us/media/${component.name}?complnkname=${component.name}"
												var="aboutusURL" />

											<c:choose>
												<c:when
													test="${componentUid eq null and  component.name eq 'Overview'}">
													<h4 class="panel-title">
														<a href="${ aboutusURL}" class="selected toggle"> <span
															class="">${component.name}</span></a>
													</h4>

												</c:when>
												<c:otherwise>

													<h4 class="panel-title">
														<a href="${ aboutusURL}" class="selected toggle"> <span
															class="">${component.name}</span></a>
													</h4>

												</c:otherwise>
											</c:choose>


										</div>
									</c:forEach>

								</div>
							</div>
						</div>

					</div>
				</div>




			</div>
			<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 internalLanding">
				<c:if test="${componentUid eq 'Overview' or componentUid eq null }">
					<div class="brandingFlyr">
						<!-- <div class="container-fluid"> -->
						<div class="clearfix">

							<c:set var="fmComponentName" value="aboutusBrandBanner"
								scope="session" />
							<cms:pageSlot position="companyOverviewBrandSection"
								var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>

						</div>
						<!-- </div> -->
					</div>
				</c:if>


				<div class="internalPage rightHandPanel clearfix">
					<div id=defaultshow>
						<c:forEach begin="0"
							end="${slots.CompanySection.cmsComponents.size()}"
							var="component" items="${slots.CompanySection.cmsComponents}">
							<c:if test="${componentUid eq 'Overview'}">
								<p>${component.content}</p>
							</c:if>

						</c:forEach>
						<c:if test="${ componentUid eq null }">
							<c:set var="component"
								value="${slots.CompanySection.cmsComponents.get(0)}">
							</c:set>
							<p>${component.content}</p>


						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
