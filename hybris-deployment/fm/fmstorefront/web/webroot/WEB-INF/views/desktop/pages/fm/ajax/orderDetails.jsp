<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="typeHeader" tagdir="/WEB-INF/tags/desktop/pricing"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<typeHeader:headerForContentType></typeHeader:headerForContentType>
<row>
  <msg>${errorMSG }</msg>
  <multipleQty>
  ${MULITPLE_PARTS_FOUND}
  </multipleQty>
  <multiselect>
  	 	<div class="col-xs-4">
	<select id="multiMatch" class="form-control" style="width: 100%;" onchange="javascript:onMultiSelect(this)">
	<option value="choose">Choose</option>
		<c:forEach items="${MULITPLE_PARTS_FOUND}" var="map" >
			<c:set value="${map.value}" var="item"/>
			<c:set var="optVal" value="${item.partNumber}~${item.productFlag}~${item.brandState }"/>
			<option value="${optVal}">${item.description }</option>
		</c:forEach>
	</select> 
	</div>
  </multiselect>
 
  <c:set var="mctr" value="0"/>
  <multipartselect>
  	<c:set var="mctr" value="0"/>
  	<c:forEach items="${MULITPLE_PARTS_FOUND}" var="map" >
		<c:set value="${map.value}" var="item"/>
		<c:set var="optVal" value="${item.partNumber}~${item.productFlag}~${item.brandState }|"/>
		<multiopt${ mctr}>${optVal}${item.description }</multiopt${mctr}>
		<c:set var="mctr" value="${mctr+1}"/>
	</c:forEach>
  </multipartselect>
   <multipartsize>
  	${mctr}
  </multipartsize>
  <rawPartNumber>
  	${RawPartNumber}
  </rawPartNumber>
  <DispPartNumber>
  	${DispPartNumber}
  </DispPartNumber>
  <removePart>
  	${removePart }
  </removePart>
  <partFlag>
  ${partFlag}
  </partFlag>
  <c:set var="ccctr" value="0"/>
  <carrier>
  	
  <c:forEach items="${carrier}" var="c" >
  	<carrieropt${ccctr} >${c} </carrieropt${ccctr} >
    <c:set var="ccctr" value="${ccctr+1}"/>
  </c:forEach>
  
  </carrier>
  <defaultCarrierCollect>
  	${defaultCarrierCollect}
  </defaultCarrierCollect>
  <defaultCACarrierCollect>
  	${defaultCACarrierCollect}
  </defaultCACarrierCollect>
  <carrierSize>
  	${ccctr}
  </carrierSize>
  <c:set var="smctr" value="0"/>
  <shipmethod>
  <c:forEach items="${shipmethod}" var="sm" >
    <smopt${smctr}>${sm}</smopt${smctr}>
    <c:set var="smctr" value="${smctr+1}"/>
  </c:forEach>
  </shipmethod>
  <smSize>
  ${smctr}
  </smSize>
  <itemDescription>
  ${fn:escapeXml(Description)}
  
  </itemDescription>
  <itemPrice>
  ${itemPrice.formattedValue }
  </itemPrice>
</row>