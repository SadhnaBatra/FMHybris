<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<ul class="filter-option">
	<c:forEach items="${productCodeList}" var="productCode" begin="0" varStatus="status">
		<c:forEach items="${productDescList}" var="productDesc"	begin="${status.count-1}" varStatus="status_desc" end="${status.count-1}">
			<li><c:choose>
					<c:when test="${fn:contains(productDesc, '/')}">
						<c:set value="${fn:replace(productDesc,'/',' ') }" var="prodDesc_changed" />
						<c:url value="/online-tools/getGraph/${productCode}/${prodDesc_changed}" var="encodedUrl" />
					</c:when>
					
					<c:when test="${fn:contains(productDesc, 'AF Bearings')}">
						<c:set value="${fn:replace(productDesc,'AF Bearings','Bearings') }" var="prodDescName_changed" />
						<c:url value="/online-tools/getGraph/${productCode}/${prodDescName_changed}" var="encodedUrl" />
					</c:when>					

					<c:otherwise>
						<c:url value="/online-tools/getGraph/${productCode}/${productDesc}" var="encodedUrl" />
					</c:otherwise>
				</c:choose> &nbsp;&nbsp;
				<c:if test="${fn:contains(productDesc, 'AF Bearings')}">
					<a href="${encodedUrl}"	class="${desc eq prodDescName_changed ? 'selected ' : ''}">${prodDescName_changed}</a>
					<span class="linkarow fa fa-angle-right "></span></li>
				</c:if> 
				<c:if test = "${not fn:contains(productDesc, 'AF Bearings')}">
					<a href="${encodedUrl}"	class="${desc eq productDesc ? 'selected ' : ''}">${productDesc}</a>
					<span class="linkarow fa fa-angle-right "></span></li>
				</c:if>
		</c:forEach>
	</c:forEach>

	<c:forEach items="${productCodeList_na}" var="productCodeNA" begin="0" varStatus="status">
		<c:forEach items="${productDescList_na}" var="productDescNA" begin="${status.count-1}" varStatus="status_desc" end="${status.count-1}">
			<li>&nbsp;&nbsp; <!-- <a href="#" onclick="return false;"> -->
				<span class="productNA_grey">${productDescNA}</span><span class="linkarow fa fa-angle-right "></span> <!-- </a> -->
			</li>
		</c:forEach>
	</c:forEach>
</ul>



