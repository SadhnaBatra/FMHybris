<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="typeHeader" tagdir="/WEB-INF/tags/desktop/pricing"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>

<typeHeader:headerForContentType></typeHeader:headerForContentType>

<row> 
	<autoSearchList>
	<c:if test="${newSearch eq 'false'}">
		<ul class="autoSearch" id="autoSearchList${Type}">
			<c:set var="addressSelected" value="abc" scope="session" />
			<c:forEach items="${SearchList}" var="customerAccount" varStatus="loopStatus">
				<c:forEach items="${customerAccount.value}" var="cust_address">
					<li id="${customerAccount.key.uid}" onclick="javascript:hideList('${cust_address.companyName}<br>${cust_address.line1}<br>${cust_address.town},&nbsp;${cust_address.region.isocodeShort}&nbsp;${cust_address.postalCode}&nbsp;${cust_address.country.isocode}<br>${customerAccount.key.uid}/${customerAccount.key.nabsAccountCode }','${Type}','${customerAccount.key.uid}','${customerAccount.key.nabsAccountCode }');" value="${customerAccount}">
						${cust_address.companyName}&nbsp;${cust_address.line1}&nbsp;${cust_address.town}&nbsp;${cust_address.region.isocodeShort}&nbsp;${customerAccount.key.uid}/${customerAccount.key.nabsAccountCode}
					</li>
					<li>----X----</li>
				</c:forEach>
			</c:forEach>
		</ul>
	</c:if>
	<c:if test="${newSearch eq 'true'}">
		<ul class="autoSearch" id="autoSearchList${Type}">
			<c:set var="addressSelected" value="abc" scope="session" />
			<c:forEach items="${SearchList}" var="customerAccount" varStatus="loopStatus">
					<li id="${customerAccount.uid}" onclick="javascript:hideList('${customerAccount.FMB2baddress.companyName}<br>${customerAccount.FMB2baddress.line1}<br>${customerAccount.FMB2baddress.town},&nbsp;${customerAccount.FMB2baddress.region.isocodeShort}&nbsp;${customerAccount.FMB2baddress.postalCode}&nbsp;${customerAccount.FMB2baddress.country.isocode}<br>${customerAccount.uid}/${customerAccount.nabsAccountCode }','${Type}','${customerAccount.uid}','${customerAccount.nabsAccountCode }');" value="${customerAccount}">
						${customerAccount.FMB2baddress.companyName}&nbsp;${customerAccount.FMB2baddress.line1}&nbsp;${customerAccount.FMB2baddress.town}&nbsp;${customerAccount.FMB2baddress.region.isocodeShort}&nbsp;${customerAccount.uid}/${customerAccount.nabsAccountCode}
					</li>
					<li>----X----</li>
			</c:forEach>
		</ul>
	</c:if>
	</autoSearchList> 
</row>

