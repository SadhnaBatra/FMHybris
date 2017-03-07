<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<div class="alsoFitsScroll" style="display:block">  
     <h3>Also Fits</h3>     
            
      <div class="userTable">
          <table class="table">
            <thead>
                <tr>
   				 <th>Make</th>
				 <th>Model</th>
          		 <th>Year Range</th>
                 <th>Position</th>
                 <th>Drive Wheel</th>
                 <th>Veh. Qty.</th>
                 <th>Engine Base</th>
                 <th>Engine VIN</th>
                 <th>Engine Desg</th>
               </tr>
             </thead>
          <c:forEach items="${AlsoFits}" var="fits">  
             <tbody>
                 <tr>
                   <td>${fits.make}</td>
                    <td>${fits.model}</td>
                    <td>${fits.yearRange}</td>
                    <td>${fits.position}</td>
                    <td>${fits.driveWheel}</td>
                    <td>${fits.vehicleQuantity}</td>
                    <td>${fits.engineBase}</td>
                    <td>${fits.engineVIN}</td>
                    <td>${fits.engineDesignation}</td>
                 </tr>
               </tbody>
          </c:forEach>
       </table>
	</div>              
  
     
</div>


                   
  

