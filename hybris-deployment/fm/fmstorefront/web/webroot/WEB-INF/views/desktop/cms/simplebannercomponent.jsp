<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:url value="${urlLink}" var="encodedUrl" />


<c:choose>
	<c:when
		test="${component.uid eq 'SiteLogoComponent' and currentsiteUID eq 'loyalty'}">

		<a href="/${urlLink}"> <img itemprop="logo"
			title="${media.altText}" alt="${media.altText}" src="${media.url}"
			class="img-responsive" />
		</a>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="${empty encodedUrl || encodedUrl eq '#'}">
				<a itemprop="url" href="#" class="logo" title="Link to Home page">
					<img itemprop="logo" title="${media.altText}"
					alt="${media.altText}" src="${media.url}" class="img-responsive" />
				</a>
			</c:when>
			<c:otherwise>
				<a href="${encodedUrl}"> <img itemprop="logo"
					title="${media.altText}" alt="${media.altText}" src="${media.url}"
					class="img-responsive" />
				</a>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>








