  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
   
   ${ReturnOrderHeaderStatus }
  <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
        <div class="manageUserPanel rightHandPanel clearfix">
          <h2 class="text-uppercase">Return History</h2>
          <div class="table-responsive col-lg-12 userTable returnHistory">
            <table class="table tablesorter" id="myTable">
              <thead>
                <tr>
                  <th class="">Return Confirmation #</th>
                  <th class="">Date of Return </th>
                  <th class="">Confirmation #</th>
                  <th class="">Sales Order #</th>
                  <th class="">Status</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td class="linkCol">
                  	<c:url value="/my-fmaccount/return-details" var="encodedUrl" />
                  <a href="${encodedUrl}">920000000</a></td>
                  <td>01/07/2015</td>
                  <td>60WAJ0347</td>
                  <td class="text-uppercase">ecc456</td>
                  <td class="text-capitalize">pending</td>
                </tr>
                <tr>
                  <td class="linkCol"><a href="viewOrderReturn.html">920000001</a></td>
                  <td>12/13/2014</td>
                  <td>60WAJ0310</td>
                  <td class="text-uppercase">ecc450</td>
                  <td class="text-capitalize">rejected</td>
                </tr>
                <tr>
                  <td class="linkCol"><a href="viewOrderReturn.html">920000002</a></td>
                  <td>11/02/2014</td>
                  <td>40WAJ0347</td>
                  <td class="text-uppercase">ecc432</td>
                  <td class="text-capitalize">approved</td>
                </tr>
                <tr>
                  <td class="linkCol"><a href="viewOrderReturn.html">920000003</a></td>
                  <td>11/23/2014</td>
                  <td>40WAJ0300</td>
                  <td class="text-uppercase">ecc336</td>
                  <td class="text-capitalize">settled</td>
                </tr>
              </tbody>
            </table>
          </div>
          
        </div>
      </div>
              
              
            

              
 