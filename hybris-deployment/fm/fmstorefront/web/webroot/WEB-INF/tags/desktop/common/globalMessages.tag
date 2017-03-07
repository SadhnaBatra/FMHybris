<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="homePageURI"><spring:eval expression="@propertyConfigurer.getProperty('homePageURL')" /></c:set>

<%-- Information (confirmation) messages --%>
<c:if test="${not empty accConfMsgs}">
	<c:forEach items="${accConfMsgs}" var="msg">
		<div class="alert positive">
			<spring:theme code="${fn:escapeXml(msg.code)}" arguments="${fn:escapeXml(msg.attributes)}"/>
		</div>
	</c:forEach>
</c:if>

<%-- Warning messages --%>
<c:if test="${not empty accInfoMsgs}">
	<c:forEach items="${accInfoMsgs}" var="msg">
		<c:if  test="${msg.code eq 'You have successfully registered for a Federal-Mogul Motorparts account.'}">
			<div class="alert neutral">
				<h4 class="registrationHeading" style="text-align:center">			
					${msg.code}&nbsp;<a href="${homePageURI}" class=""><spring:theme code="registration.confirmation.message.signin" /></a>
				</h4>
			</div>
		</c:if>
			
		<c:if  test="${msg.code ne 'You have successfully registered for a Federal-Mogul Motorparts account.'}">
			<div class="alert neutral fm_fntGreen DIMbold">
				<spring:theme code="${fn:escapeXml(msg.code)}" arguments="${fn:escapeXml(msg.attributes)}"/>
			</div>
		</c:if>
	</c:forEach>
</c:if>

<%-- Error messages (includes spring validation messages)--%>
<c:if test="${not empty accErrorMsgs}" >
	<c:choose>
	 	<c:when test="${globalmsg_forgotPage eq 'alignToRight' }">
	 		<c:forEach items="${accErrorMsgs}" var="msg">
				<div>
					<spring:theme code="${fn:escapeXml(msg.code)}" arguments="${fn:escapeXml(msg.attributes)}"/>
				</div>	
			</c:forEach>
	 	</c:when>
	 	<c:otherwise>		
			<c:if test="${not empty accErrorMsgs}">
				<c:forEach items="${accErrorMsgs}" var="msg">
					<div style="margin: 15px 0;">
						<h5 class="fm_fntRed">
							<spring:theme code="${fn:escapeXml(msg.code)}" arguments="${fn:escapeXml(msg.attributes)}"/>
						</h5>	
					</div>	
				</c:forEach>
			</c:if>
	 	</c:otherwise>
	</c:choose>		
</c:if>
