<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>






<c:if test="${fmComponentName ne 'extendedfooterLinks'}">

	<c:forEach items="${navigationNodes}" var="node">
		<c:if test="${node.visible}">

			<c:choose>
				<c:when test="${node.title eq 'Online Tools'  }">
					<sec:authorize ifAnyGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMBUVOR,ROLE_FMB2T">
						<div class="col-xs-12 col-lg-2 link-container">
							<h5>${node.title}</h5>
							<ul class="unstyled mobslider">
								<c:forEach items="${node.links}" step="${component.wrapAfter}"
									varStatus="i">
									<c:forEach items="${node.links}" var="childlink"
										begin="${i.index}" end="${i.index + component.wrapAfter - 1}">
										<c:choose>
											<c:when test="${node.title eq 'Online Tools'  }">
												<c:choose>
													<c:when test="${childlink.linkName eq 'Order Sales Materials'}">
														<sec:authorize ifAnyGranted="ROLE_FMB2BB">
															<li><a
																href="${childlink.url}${nabsAccCode}" target="_blank">${childlink.linkName}</a></li>
														</sec:authorize>
														<sec:authorize ifAnyGranted="ROLE_FMCSR,ROLE_FMBUVOR">
															<c:if test="${csrAccountEmulation eq 'true'}">
																<li><a
																	href="${childlink.url}${nabsAccCode}" target="_blank">${childlink.linkName}</a></li>
															</c:if>
														</sec:authorize>
			
													</c:when>
													<c:when test="${childlink.linkName eq 'File Downloads'}">
														<sec:authorize ifNotGranted="ROLE_FMB2T">
															<li><cms:component component="${childlink}" evaluateRestriction="true" /></li>
														</sec:authorize>
													</c:when>
													<c:when test="${childlink.linkName eq 'Market Your Shop Link'}">	
														<sec:authorize ifAnyGranted="ROLE_FMB2T">
															<li><cms:component component="${childlink}" evaluateRestriction="true" /></li>
														</sec:authorize>
													</c:when>
													<c:otherwise>
														<li><cms:component component="${childlink}" evaluateRestriction="true" /></li>		
													</c:otherwise>
			
												</c:choose>
											</c:when>
											<c:when test="${node.title eq 'Learning Center'  }">
												<sec:authorize
													ifNotGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMBUVOR,ROLE_FMB2C,ROLE_FMB2SB">
													<li><cms:component component="${childlink}"
															evaluateRestriction="true" /></li>
												</sec:authorize>
											</c:when>
											<c:otherwise>
												<li><cms:component component="${childlink}"
														evaluateRestriction="true" /></li>
											</c:otherwise>
										</c:choose>

									</c:forEach>
								</c:forEach>
							</ul>
						</div>
					</sec:authorize>
				</c:when>
				<c:when test="${node.title eq 'Learning Center'  }">
					<sec:authorize ifNotGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMBUVOR,ROLE_FMB2T">
						<div class="col-xs-12 col-lg-2 link-container">
							<h5>${node.title}</h5>
							<ul class="unstyled mobslider">
								<c:forEach items="${node.links}" step="${component.wrapAfter}"
									varStatus="i">
									<c:forEach items="${node.links}" var="childlink"
										begin="${i.index}" end="${i.index + component.wrapAfter - 1}">
										<c:choose>
											<c:when test="${node.title eq 'Online Tools'  }">
												<sec:authorize
													ifAnyGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMBUVOR,ROLE_FMB2T">
													<li><cms:component component="${childlink}"
															evaluateRestriction="true" /></li>
												</sec:authorize>
											</c:when>
											<c:when test="${node.title eq 'Learning Center'  }">
											<c:if test="${fn:contains(childlink.linkName,'Garage')}" >
												<c:set var="garagelink" value="b2t" />
										</c:if>
										<c:if test="${childlink.linkName eq 'Garage Rewards'}">
										<c:set var="garagelink" value="B2B" />
										</c:if>
												<c:choose>
													<c:when test="${garagelink eq 'b2t'}">
													<sec:authorize
													ifAnyGranted="ROLE_FMB2T">
															<li><a href="${childlink.url}" target="_blank">${childlink.linkName}</a></li>
															</sec:authorize>
														</c:when>
													<c:otherwise>
												<li><cms:component component="${childlink}"
															evaluateRestriction="true" /></li>
															</c:otherwise>
															</c:choose>
											</c:when>
											<c:otherwise>
												<li><cms:component component="${childlink}"
														evaluateRestriction="true" /></li>
											</c:otherwise>
										</c:choose>

									</c:forEach>
								</c:forEach>
							</ul>
						</div>
					</sec:authorize>
				</c:when>
				<c:when test="${node.title eq 'Garage Gurus'}">
					<sec:authorize ifAnyGranted="ROLE_FMB2T">
						<div class="col-xs-12 col-lg-2 link-container">
							<h5>${node.title}</h5>
							<ul class="unstyled mobslider">
								<c:forEach items="${node.links}" step="${component.wrapAfter}"
									varStatus="i">
									<c:forEach items="${node.links}" var="childlink"
										begin="${i.index}" end="${i.index + component.wrapAfter - 1}">
										   <c:choose>
											<c:when test="${childlink.linkName eq 'Garage Rewards'  }">
												<c:if test="${loyaltystatus eq false}">
													<li><cms:component component="${childlink}"
															evaluateRestriction="true" /></li>
												</c:if>
											</c:when>
											<c:otherwise>
												<li><cms:component component="${childlink}"
														evaluateRestriction="true" /></li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:forEach>
							</ul>
						</div>

					</sec:authorize>
				</c:when>
				<c:when test="${node.title eq 'Support'  }">
						<div class="col-xs-12 col-lg-2 link-container">
							<h5>${node.title}</h5>
							<ul class="unstyled mobslider">
								<c:forEach items="${node.links}" step="${component.wrapAfter}"
									varStatus="i">
									<c:forEach items="${node.links}" var="childlink"
										begin="${i.index}" end="${i.index + component.wrapAfter - 1}">
										<c:choose>
												<c:when test="${childlink.linkName eq 'Help Center'}">
													<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
														<li><cms:component component="${childlink}"
																evaluateRestriction="true" /></li>
													</sec:authorize>
												</c:when>
												<c:otherwise>
													<li><cms:component component="${childlink}"
															evaluateRestriction="true" /></li>
												</c:otherwise>
											</c:choose>
									</c:forEach>
								</c:forEach>
								</br>
							</ul>
							<c:if test="${not empty node.children}">
							
							<c:forEach items="${node.children}" var="child">
							<h5>${child.title}</h5>
							<ul class="unstyled mobslider">
							
								<c:forEach items="${child.links}" step="${component.wrapAfter}"
									varStatus="i">
									<c:forEach items="${child.links}" var="childlink"
										begin="${i.index}" end="${i.index + component.wrapAfter - 1}">
										<li><cms:component component="${childlink}"
														evaluateRestriction="true" /></li>
									</c:forEach>
								</c:forEach>
							</ul>
							</c:forEach>
							</c:if>
						</div>
					
				</c:when>
				<c:when test="${node.title eq 'Garage Rewards'}">
					<sec:authorize ifAnyGranted="ROLE_FMB2T">
						<c:if test="${loyaltystatus eq true}">
						<div class="col-xs-12 col-lg-2 link-container">
							<h5>${node.title}</h5>
							<ul class="unstyled mobslider">
								<c:forEach items="${node.links}" step="${component.wrapAfter}"
									varStatus="i">
									<c:forEach items="${node.links}" var="childlink"
										begin="${i.index}" end="${i.index + component.wrapAfter - 1}">
											<%-- <li><cms:component component="${childlink}"
															evaluateRestriction="true" /></li> --%>
											<li><a href="${childlink.url}">${childlink.linkName}</a></li>
									</c:forEach>
								</c:forEach>
							</ul>
						</div>
						</c:if>
					</sec:authorize>
				</c:when>
				<c:otherwise>
					<div class="col-xs-12 col-lg-2 link-container">
						<h5>${node.title}</h5>
						<ul class="unstyled mobslider">
							<c:forEach items="${node.links}" step="${component.wrapAfter}"
								varStatus="i">
								<c:forEach items="${node.links}" var="childlink"
									begin="${i.index}" end="${i.index + component.wrapAfter - 1}">
									<c:choose>
										<c:when test="${node.title eq 'Online Tools'  }">
											<sec:authorize
												ifAnyGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMBUVOR">
												<li><cms:component component="${childlink}"
														evaluateRestriction="true" /></li>
											</sec:authorize>
										</c:when>
										<c:when test="${node.title eq 'Learning Center'  }">
											<sec:authorize
												ifNotGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMBUVOR,ROLE_FMB2C,ROLE_FMB2SB">
												<li><cms:component component="${childlink}"
														evaluateRestriction="true" /></li>
											</sec:authorize>
										</c:when>
										<c:when test="${childlink.linkName eq 'Help Center'  }">
											<sec:authorize
												ifAnyGranted="ROLE_FMB2BB">
												<li><cms:component component="${childlink}"
														evaluateRestriction="true" /></li>
											</sec:authorize>
										</c:when>
										<c:otherwise>
											<li>
												<%-- Don't use the hybris Link component for the Media link (to AEM) b/c it will always prepend the
													Content base (/fmstorefront/...) to the URI that is configured --%>
												<c:choose>
													<c:when test="${(childlink.uid eq 'AboutUsMediaFooterLink'||childlink.uid eq 'MediaSuppliersFooterLink'||childlink.uid eq 'CompanyFooterLink'||childlink.uid eq 'CareersFooterLink'||childlink.uid eq 'InvestorsFooterLink'||childlink.uid eq 'WhereToBuyLink')}">
														<a href="${childlink.url}" title="${childlink.linkName}">${childlink.linkName}</a>
													</c:when>
													<c:otherwise>
														<cms:component component="${childlink}" evaluateRestriction="true" />
													</c:otherwise>
												</c:choose>
											</li>
										</c:otherwise>
									</c:choose>

								</c:forEach>
							</c:forEach>
						</ul>
					</div>
				</c:otherwise>
			</c:choose>


			
		</c:if>
	</c:forEach>
	
</c:if>
