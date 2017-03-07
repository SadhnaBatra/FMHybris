<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="federalmogul"
	tagdir="/WEB-INF/tags/desktop/federalmogul"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
	<div class="manageUserPanel rightHandPanel clearfix">
<div id="globalMessages">
		<common:globalMessages />
	</div>
		<h2 class="text-uppercase">
			<spring:theme code="text.account.profile" text="Profile" />
		</h2>
		<div class="profileTable">
			<c:if test="${not empty fn:escapeXml(title.name)}">
				<p><strong><spring:theme code="profile.title" text="Title" />: </strong>${fn:escapeXml(title.name)}</p>
			</c:if>
			<p><strong><spring:theme code="profile.firstName" text="First name" />: </strong>${fn:escapeXml(fmCustomerData.firstName)}</p>
			<p><strong><spring:theme code="profile.lastName" text="Last name" />: </strong>${fn:escapeXml(fmCustomerData.lastName)}</p>
			<p><strong><spring:theme code="profile.email" text="E-mail" />: </strong>${fn:escapeXml(fmCustomerData.email)}</p>
		</div>
		<div class="topMargn_20">
			<c:choose>
				<c:when test="${isFMAdminHomePage eq 'true'}">
					<a href="my-fmaccount/update-password"><button class="btn btn-fmDefault">
							<spring:theme code="text.account.profile.changePassword"
								text="Update password" />
						</button></a>
					<a href="my-fmaccount/update-profile"><button
							class="btn btn-fmDefault lftMrgn_20">
							<spring:theme code="text.account.profile.updatePersonalDetails"
								text="Update details" /></button></a>
				</c:when>
				<c:otherwise>
					<a href="update-password"><button class="btn btn-fmDefault">
							<spring:theme code="text.account.profile.changePassword"
								text="Change password" />
						</button></a>
					<a href="update-profile"><button
							class="btn btn-fmDefault lftMrgn_20">
							<spring:theme code="text.account.profile.updatePersonalDetails"
								text="Update personal details" />
						</button></a>
				</c:otherwise>

			</c:choose>

		</div>
	</div>
</div>
