<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${fmComponentName == 'learning'}">
			<p>
				<a target="_blank" href="${component.imageText}" ><img title="${media.altText}"  src="${media.url}"
				alt="${media.altText}" /> </a>
			</p>
</c:if>

<c:if test="${fmComponentName == 'learningarticle'}">

			<p>
				<a href="${component.imageText}" ><img title="${media.altText}"  src="${media.url}"
				alt="${media.altText}" /> </a>
			</p>
</c:if>

<c:if test="${fmComponentName == 'learningvideos1'}">
			<p>
				<a target="_blank" href="${component.imageText}" ><img title="${media.altText}"  src="${media.url}"
				alt="${media.altText}" /> </a>
			</p>

</c:if>

<c:if test="${fmComponentName == 'learningvideos2'}">
			<p>
				<a target="_blank" href="${component.imageText}" ><img title="${media.altText}"  src="${media.url}"
				alt="${media.altText}" /> </a>
			</p>
</c:if>
<c:if test="${fmComponentName == 'productGridBanner'}">



	<img title="${media.altText}" src="${media.url}" alt="${media.altText}"
		class="img-responsive" />

</c:if>

<c:if test="${fmComponentName == 'insight'}">
	<img title="${media.altText}" src="${media.url}" alt="${media.altText}" />
</c:if>

<c:if test="${fmComponentName == 'b2clearning'}">
	<img title="${media.altText}" src="${media.url}" alt="${media.altText}" />
</c:if>


<c:if test="${fmComponentName == 'brand'}">

	<img title="${media.altText}" src="${media.url}" alt="${media.altText}" class="img-responsive"/>
</c:if>
<c:if test="${fmComponentName == 'brand2'}">
  
           <img title="${media.altText}" src="${media.url}" alt="${media.altText}" /><br><br>
           
   <!-- 
           <c:if test="${component.imageText!=null}">
              <a class="fa fa-sign-out orderPdf" href="${component.imageText}"></a>
              <a class="orderNo fileDownloadOrder fm-blue-text">${component.imageText} </a>
              <br><br>
          </c:if>
	 -->
</c:if>