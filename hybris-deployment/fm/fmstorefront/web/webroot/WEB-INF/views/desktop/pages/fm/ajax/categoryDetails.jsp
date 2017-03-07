<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="typeHeader" tagdir="/WEB-INF/tags/desktop/pricing"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<typeHeader:headerForContentType></typeHeader:headerForContentType>
<row>
   
   <SubCategoryResults>
  <c:set var="sstr" value="0"/>
  <c:forEach items="${subcategory}" var="ssubcategory">
		<SubCategoryResultsvalue${sstr}>${fn:escapeXml(ssubcategory)}</SubCategoryResultsvalue${sstr}>
		<c:set var="sstr" value="${sstr+1}"/>
  </c:forEach>
  </SubCategoryResults>
  <SubCategoryResultsSize>${subcategorySize}</SubCategoryResultsSize>
</row>