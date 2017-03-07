<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="typeHeader" tagdir="/WEB-INF/tags/desktop/pricing"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<typeHeader:headerForContentType></typeHeader:headerForContentType>
<row>
	<accountCode>
		${accountCode}
	</accountCode>

	<companyName>
		${companyName}
	</companyName>
	
	<companyAddressLine1>
		${companyAddressLine1}
	</companyAddressLine1>
	
	<companyAddressLine2>
		${companyAddressLine2}
	</companyAddressLine2>
	
	<companyCity>
		${companyCity}
	</companyCity>
	
	<companyStateOrProvIsoCode>
		${companyStateOrProvIsoCode}
	</companyStateOrProvIsoCode>
	
	<companyCountryIsoCode>
		${companyCountryIsoCode}
	</companyCountryIsoCode>
	
	<companyZipOrPostalCode>
		${companyZipOrPostalCode}
	</companyZipOrPostalCode>
</row>
