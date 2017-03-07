<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="blockId" value="${promotype}Block" />
<c:set var="inputFieldId" value="${promotype}id" />
<c:set var="buttonId" value="${promotype}Btn" />
<tr class="${promotype}" style=" " id="${blockId}">
    <td colspan="4">
		<h2 class="text-uppercase DINWebBold">Success!</h2>
		<p class="topMargn_15">${errortype}</p>		
	</td>
</tr>