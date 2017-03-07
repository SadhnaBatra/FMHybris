<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>

<%@ taglib prefix="sec"
		   uri="http://www.springframework.org/security/tags"%>

<c:set value="${component.styleClass} ${dropDownLayout}"
	   var="bannerClasses" />
<c:if test="${fromb2t eq 'fromb2t'}">
	<c:set value="${currentUser.loyaltySignup}" var="loyaltystatus" scope="session"/>
</c:if>

<li class="dropdown">
	<c:choose>
		<c:when test="${empty component.navigationNode.children }">

			<c:choose>
				<c:when test="${component.link.linkName eq 'Online Tools'  }">
					<sec:authorize
							ifAnyGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMBUVOR,ROLE_FMB2T,ROLE_FMNOINVOICEGROUP,ROLE_FMVIEWONLYGROUP">
						<c:if test="${logedUserType ne 'ShipTo' }">
							<a href="${component.link.url}" >${component.link.linkName}</a>
						</c:if>
					</sec:authorize>
				</c:when>
				<c:when test="${component.link.linkName eq 'Learning Center'  }">
					<sec:authorize
							ifNotGranted="ROLE_ANONYMOUS,ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMBUVOR,ROLE_FMB2C,ROLE_FMB2SB,ROLE_FMB2T,ROLE_FMNOINVOICEGROUP,ROLE_FMVIEWONLYGROUP">
						<a href="${component.link.url}">${component.link.linkName}</a>
					</sec:authorize>
				</c:when>
                <c:when test="${component.link.linkName eq 'Buy Gear'  }">
                   <sec:authorize
					ifNotGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMBUVOR,ROLE_FMNOINVOICEGROUP,ROLE_FMVIEWONLYGROUP">
				     <a href="${component.link.url}" target="_blank">${component.link.linkName}</a>
			       </sec:authorize>
		        </c:when>

				<c:when test="${component.link.linkName eq 'Garage Rewards'  }">
					<sec:authorize ifAnyGranted="ROLE_FMB2T,ROLE_ANONYMOUS">
						<c:if test="${loyaltystatus eq true}">
							<a href="${component.link.url}">${component.link.linkName}</a>
						</c:if> 
					</sec:authorize>
				</c:when>
			
				<c:otherwise>
					<a href="${component.link.url}">${component.link.linkName}</a>
				</c:otherwise>
			</c:choose>
		</c:when>

		<c:when test="${component.link.linkName ne 'Online Tools' and component.link.linkName ne 'Learning Center'
			and component.link.linkName ne 'Garage Gurus' and component.link.linkName ne 'Support'
			and  component.link.linkName ne 'About Us' and component.link.linkName ne 'Garage Rewards'}">
			<a href="#" class="dropdown-toggle ${childlink.styleAttributes}" data-toggle="dropdown">
					${component.link.linkName}<b class="caret"></b>
			</a>
		</c:when>

		<c:when test="${component.link.name eq 'Garage Rewards B2T'}">
			<sec:authorize ifAnyGranted="ROLE_FMB2T">
				<c:if test="${loyaltystatus eq true}">
					<a href="#" class="dropdown-toggle ${childlink.styleAttributes}" data-toggle="dropdown">
							${component.link.linkName} <b class="caret"></b>
					</a>
				</c:if>
			</sec:authorize>
		</c:when>
		<c:when test="${component.link.name eq 'Garage Rewards Non Member'}">
			<sec:authorize ifAnyGranted="ROLE_FMB2T">
				<c:if test="${loyaltystatus eq false}">
					<a href="#" class="dropdown-toggle ${childlink.styleAttributes}" data-toggle="dropdown">
							${component.link.linkName} <b class="caret"></b>
					</a>
				</c:if>
			</sec:authorize>
		</c:when>
			
		
		<c:when test="${component.link.linkName eq 'Online Tools'  }">
			<sec:authorize
					ifAnyGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMBUVOR,ROLE_FMNOINVOICEGROUP,ROLE_FMVIEWONLYGROUP">
					<a href="#" class="dropdown-toggle ${childlink.styleAttributes}" data-toggle="dropdown">
							${component.link.linkName}<b class="caret"></b>
					</a>
			</sec:authorize>
		</c:when>
		<c:when test="${component.link.linkName eq 'Learning Center'  }">
			<sec:authorize
					ifNotGranted="ROLE_ANONYMOUS,ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMBUVOR,ROLE_FMB2C,ROLE_FMB2SB,ROLE_FMB2T,ROLE_FMNOINVOICEGROUP,ROLE_FMVIEWONLYGROUP">
				<a href="#" class="dropdown-toggle ${childlink.styleAttributes}" data-toggle="dropdown">
						${component.link.linkName}<b class="caret"></b>
				</a>
			</sec:authorize>
		</c:when>
		<c:when test="${component.link.linkName eq 'Garage Gurus'  }">
			<sec:authorize ifAnyGranted="ROLE_FMB2T,ROLE_ANONYMOUS,ROLE_FMB2C">
				<a href="#" class="dropdown-toggle ${childlink.styleAttributes}" data-toggle="dropdown">
						${component.link.linkName}<b class="caret"></b>
				</a>
			</sec:authorize>
		</c:when>	
		<c:when test="${component.link.linkName eq 'Support'}">
		  <sec:authorize
					ifNotGranted="ROLE_ANONYMOUS,ROLE_FMB2C,ROLE_FMB2SB,ROLE_FMB2T">
				<a href="#" class="dropdown-toggle ${childlink.styleAttributes}" data-toggle="dropdown">
						${component.link.linkName}<b class="caret"></b>
				</a>
			</sec:authorize>
		</c:when>

		<c:when test="${component.link.linkName eq 'About Us'
					  or  component.link.linkName eq 'Brands'}">
			<%--<sec:authorize
					ifAnyGranted="ROLE_FMB2T">
				<c:if test="${loyaltystatus eq false}">
					<a href="#" class="dropdown-toggle ${childlink.styleAttributes}" data-toggle="dropdown">
							${component.link.linkName}<b class="caret"></b>
					</a>
				</c:if>
			</sec:authorize>--%>
			<sec:authorize
					ifAnyGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMBUVOR,ROLE_FMB2C,ROLE_FMB2SB">

				<a href="#" class="dropdown-toggle ${childlink.styleAttributes}" data-toggle="dropdown">
						${component.link.linkName}<b class="caret"></b>
				</a>

			</sec:authorize>
			<sec:authorize
					ifNotGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMBUVOR,ROLE_FMB2C,ROLE_FMB2SB,ROLE_FMB2T">

				<a href="#" class="dropdown-toggle ${childlink.styleAttributes}" data-toggle="dropdown">
						${component.link.linkName}<b class="caret"></b>
				</a>

			</sec:authorize> 
		</c:when>
		<c:otherwise>
			<cms:component component="${component.link}"
						   evaluateRestriction="true" />
		</c:otherwise>
	</c:choose>

	<c:if test="${not empty component.navigationNode.children}">
		<ul class="dropdown-menu mega-menu">
			<c:forEach items="${component.navigationNode.children}" var="child"
					   varStatus="status">
				<c:if test="${child.visible}">

					<c:forEach items="${child.links}" step="${component.wrapAfter}"
							   varStatus="i">
						<li class="mega-menu-column">
							<ul>
								<c:forEach items="${child.links}" var="childlink"
										   begin="${i.index}" end="${i.index + component.wrapAfter - 1}">
									<%-- <cms:component component="${childlink}" evaluateRestriction="true" element="li" /> --%>
									<c:choose>
										<c:when test="${childlink.linkName eq 'Order Sales Materials'}">
												<li><a class="${childlink.styleAttributes}"
													   href="${childlink.url}&qqid01=262&qqid02=${nabsAccCode}" target="_blank">${childlink.linkName}</a></li>
											<sec:authorize ifAnyGranted="ROLE_FMCSR,ROLE_FMBUVOR">
												<c:if test="${csrAccountEmulation eq 'true'}">
													<li><a class="${childlink.styleAttributes}"
														   href="${childlink.url}${nabsAccCode}" target="_blank">${childlink.linkName}</a></li>
												</c:if>
											</sec:authorize>

										</c:when>
										<c:when test="${childlink.linkName eq 'File Downloads'}">
											<sec:authorize
													ifAnyGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMBUVOR">
												<li><a class="${childlink.styleAttributes}" href="${childlink.url}">${childlink.linkName}</a></li>
											</sec:authorize>


										</c:when>
										<c:when test="${childlink.linkName eq 'Download Digital Assets'}">
											<li><a class="${childlink.styleAttributes}" href="${childlink.url}" target="_blank">${childlink.linkName}</a></li>
										</c:when>

										<c:when test="${childlink.linkName eq 'Training Options'}">
											<li><a class="${childlink.styleAttributes}" href="${childlink.url}" target="_blank">${childlink.linkName}</a></li>
										</c:when>

										<c:when test="${childlink.linkName eq 'Courses'}">
											<li><a class="${childlink.styleAttributes}" href="${childlink.url}" target="_blank">${childlink.linkName}</a></li>
										</c:when>

										<c:when test="${childlink.linkName eq 'Tech Tips'}">
											<li><a class="${childlink.styleAttributes}" href="${childlink.url}" target="_blank">${childlink.linkName}</a></li>
										</c:when>

										<c:when test="${childlink.linkName eq 'Supplier Collaboration'}">
											<li><a class="${childlink.styleAttributes}" href="${childlink.url}" target="_blank">${childlink.linkName}</a></li>
										</c:when>
										<c:when test="${childlink.linkName eq 'Help Center'}">
											<sec:authorize ifAnyGranted="ROLE_FMB2BB,ROLE_FMCSR">
												<li><a class="${childlink.styleAttributes}"
													   href="${childlink.url}" >${childlink.linkName}</a></li>
											</sec:authorize>
										</c:when>
										<%--
										<c:when test="${childlink.linkName eq 'Customer Service'}">
											<sec:authorize ifAnyGranted="ROLE_CUSTOMERGROUP">
												<c:if test="${loyaltystatus eq true}">
													<li><a
													href="${childlink.url}" >${childlink.linkName}</a></li>
												</c:if>
											</sec:authorize>
										</c:when>
										--%>

										<c:when test="${childlink.linkName eq 'Customer Service'}">


											<sec:authorize ifAnyGranted="ROLE_FMB2T,ROLE_FMB2BB,ROLE_CUSTOMERGROUP">

												<li><a class="${childlink.styleAttributes}"
													   href="${childlink.url}" >${childlink.linkName}</a></li>

											</sec:authorize>
											<sec:authorize ifNotGranted="ROLE_FMB2T,ROLE_FMB2BB,ROLE_CUSTOMERGROUP">

												<li><a class="${childlink.styleAttributes}"
													   href="${childlink.url}" >${childlink.linkName}</a></li>

											</sec:authorize>
										</c:when>

										<c:when test="${childlink.linkName eq 'Garage Rewards'}">
											<sec:authorize ifAnyGranted="ROLE_FMB2T,ROLE_ANONYMOUS">
											    <c:if test="${loyaltystatus eq true}">
													<li><a class="${childlink.styleAttributes}" href="${childlink.url}">${childlink.linkName}</a></li>
												</c:if>
											</sec:authorize>

										</c:when>
										<c:when test="${component.link.linkName eq 'Learning Center' and  childlink.linkName eq 'About Garage Rewards'}">

											<li><a class="${childlink.styleAttributes}" href="${childlink.url}">${childlink.linkName}</a></li>

										</c:when>
										<c:when test="${childlink.linkName eq 'About Garage Rewards' }">
											<sec:authorize ifAnyGranted="ROLE_FMB2T,ROLE_ANONYMOUS">
												<c:if test="${loyaltystatus eq false}">
													<li><a class="${childlink.styleAttributes}" href="${childlink.url}">${childlink.linkName}</a></li>
												</c:if>
											</sec:authorize>

										</c:when>
										<c:when test="${childlink.linkName eq 'Motorparts Monitor'}">
											<li><a href="${childlink.url}">${childlink.linkName}</a></li> 
											<sec:authorize ifAnyGranted="ROLE_FMCSR,ROLE_FMBUVOR">
												<c:if test="${csrAccountEmulation eq 'true'}">
													<li><a class="${childlink.styleAttributes}" href="${childlink.url}">${childlink.linkName}</a></li>
												</c:if>
											</sec:authorize>

										</c:when>
										<c:when	test="${childlink.linkName eq 'Market Your Shop'}">
											<li><a class="${childlink.styleAttributes}" href="${childlink.url}">${childlink.linkName}</a></li>
											<%-- <sec:authorize ifNotGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMADMINGROUP,ROLE_B2BADMINGROUP">
												<li><a href="${childlink.url}">${childlink.linkName}</a></li>
											</sec:authorize> --%>
										</c:when>
										<c:otherwise>
											<li><a class="${childlink.styleAttributes}" href="${childlink.url}">${childlink.linkName}</a></li>

										</c:otherwise>

									</c:choose>

									<%-- <li><a href="${childlink.url}">${childlink.linkName}</a></li> --%>
								</c:forEach>
							</ul>
						</li>
					</c:forEach>
				</c:if>
			</c:forEach>
		</ul>
	</c:if></li>
