<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="pageType" required="true" type="java.lang.String"%>

  <div class="modal-dialog newAddressPopup brandprefix">
    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true" class="fa fa-close"></span><span class="sr-only">Close</span></button>
    <div class="modal-content">
      <div class="modal-header">
        <h2 class="text-uppercase DINWebBold">Brand Prefix List</h2>
      </div>
      <div class="modal-body">
        <div id="new">
         <div class="reviewFirstPanelMargin ">
         <table border="1" align="center" style="width:100%">
		 <tr><td>WAN</td>
			<td>ANCO WIPERS</td>
		</tr>
		<tr><td>KCH</td>
			<td>CHAMPION FILTERS</td>
		</tr>
		<tr><td>FCC</td>
			<td>CHAMPION PERFORMANCE ADDITIVES</td>
		</tr>
		<tr><td>CCH</td>
			<td>CHAMPION SPARK PLUGS</td>
		</tr>
		<c:if test="${pageType eq 'uploadOrder' }" >
			<tr><td>Q</td>
				<td>FEL PRO GASKETS</td>
			</tr>
		</c:if>	
		<tr><td>AMQ</td>
			<td>MCQUAY-NORRIS CHASSIS</td>
		</tr>
		<tr><td>AMG</td>
			<td>MOOG CHASSIS</td>
		</tr>
		<tr><td>HMG</td>
			<td>MOOG DRIVE LINE</td>
		</tr>
		<tr><td>NMG</td>
			<td>MOOG HUB ASSEMBLIES</td>
		</tr>
		<tr><td>NNT</td>
			<td>NATIONAL ANTI-FRICTION</td>
		</tr>
		<tr><td>RNT</td>
			<td>NATIONAL OIL SEALS</td>
		</tr>
		<tr><td>MQS</td>
			<td>QUICK STOP</td> 
		</tr>
		<tr><td>EQC</td>
			<td>QUICKSTEER</td>
		</tr>
		<tr><td>WRD</td>
			<td>RAINY DAY WIPERS</td>
		</tr>		
		<tr><td>ARA</td>
			<td>RAYBESTOS CHASSIS</td>
		</tr>
		<tr><td>ERA</td>
			<td>RAYBESTOS RED CHASSIS</td>
		</tr>
		<c:if test="${pageType eq 'uploadOrder'}">
			<tr><td>Z</td>
				<td>FP DIESEL PRODUCTS</td>
			</tr>
		</c:if>
		<tr><td>BSE</td>
			<td>SEALED POWER / SPEED PRO ENGINE PARTS</td>
		</tr>
		<tr><td>MWG</td>
			<td>WAGNER BRAKE (incl. THERMOQUIET)</td> 
		</tr>
		<tr><td>MSV</td>
			<td>WAGNER OEx</td>
		</tr>
		<tr><td>LWL</td>
			<td>WAGNER LIGHTING</td>
		</tr>
		
</table>
		 
		 </div>
        </div>
      </div>
    </div>
  </div>

