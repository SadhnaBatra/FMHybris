<%@ taglib prefix="c"          uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="typeHeader" tagdir="/WEB-INF/tags/desktop/pricing" %>
<%@ taglib prefix="format"     tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fn"         uri="http://java.sun.com/jsp/jstl/functions" %> 

<typeHeader:headerForContentType>
</typeHeader:headerForContentType>

<row>

	<loggedInUserType>
		${loggedInUserType}
	</loggedInUserType>

	<dcCode>
		${dcCode}
	</dcCode>

	<totalItemPriceForDC>
		${totalItemPriceForDC}
	</totalItemPriceForDC>

	<totalFreightCostForDC>
		${totalFreightCostForDC}
	</totalFreightCostForDC>

	<totalDCValue>
		${totalDCValue}
	</totalDCValue>

	<totalItemPriceForAllDCs>
		${totalItemPriceForAllDCs}
	</totalItemPriceForAllDCs>

	<totalFreightCostForAllDCs>
		${totalFreightCostForAllDCs}
	</totalFreightCostForAllDCs>

	<totalOrderValue>
		${totalOrderValue}
	</totalOrderValue>
</row>