<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="typeHeader" tagdir="/WEB-INF/tags/desktop/pricing"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>

<typeHeader:headerForContentType></typeHeader:headerForContentType>

<%-- <row> 
	<autoSearchList>
	<ul class="autoSearch" id="autoUnitsSearchList">
		
		<c:forEach items="${unitsSearchList}" var="userUnitId" varStatus="loopStatus">
			
				<li >
					${userUnitId}
				</li>
			
		</c:forEach>
	</ul>
	</autoSearchList> 
</row>
 --%>
 
 <row> 
	<autoSearchList>
	<ul class="autoSearch" id="autoUnitsSearchList">
	
	<c:forEach items="${unitsSearchList}" var="userUnitId" varStatus="loopStatus">
			
				<li id="${userUnitId}" value="${userUnitId}" onclick="javascript:hideUnitsList(this);">
					${userUnitId}
				</li>
			
		</c:forEach>
		
		
	</ul>
	</autoSearchList> 
</row>
 