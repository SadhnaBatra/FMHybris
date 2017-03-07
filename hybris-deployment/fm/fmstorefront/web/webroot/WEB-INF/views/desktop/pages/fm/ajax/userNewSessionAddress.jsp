<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="typeHeader" tagdir="/WEB-INF/tags/desktop/pricing"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<typeHeader:headerForContentType></typeHeader:headerForContentType>
<row>
 <addNewShipToaddressSession>
 <c:if test="${not empty NewShiptToAddress }">

                     <div id="shipTonewAddress" class="tab-pane topMargn_10">
                      <div class="reviewFirstPanelMargin ">
                        <p class="reviewPlaceOrderBold text-capitalize">Ship To</p>
                       <p class="text-uppercase">${NewShiptToAddress.firstName} <%-- ${NewShiptToAddress.lastName} --%></br>
                        ${NewShiptToAddress.line1}&nbsp;${NewShiptToAddress.line2}</br>
						${NewShiptToAddress.town}, ${NewShiptToAddress.region.isocodeShort}&nbsp;${NewShiptToAddress.postalCode}&nbsp;${NewShiptToAddress.country.name}
						 
						</br>Phone: ${NewShiptToAddress.phone}</p>
                      </div>
                    </div>  
                    <p class="text-uppercase">NewAddress: ${NewShiptToAddress.firstName} <%-- ${NewShiptToAddress.lastName} --%><br/>${NewShiptToAddress.line1}<br/>${NewShiptToAddress.line2}<br/>${NewShiptToAddress.town}<br/>${NewShiptToAddress.region.isocodeShort}<br/>${NewShiptToAddress.country.name}<br/>${NewShiptToAddress.postalCode}<br/>Phone: ${NewShiptToAddress.phone}</p>
    </c:if>
</addNewShipToaddressSession>


<shipToSessionAddresschanged>
	${SessionAddresschanged}
</shipToSessionAddresschanged>


</row>