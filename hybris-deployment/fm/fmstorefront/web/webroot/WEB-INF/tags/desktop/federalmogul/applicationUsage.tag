<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 "> 
        <!--<div class=""><h2 class="text-uppercase">Address Details</h2></div> -->
        <div class="manageUserPanel rightHandPanel clearfix">
        <h2 class="text-uppercase">search criteria</h2>
        <div id="globalMessages">
			<common:globalMessages />
		</div>
          <div class="uploadOrderStatus applicationUsageReport">
   			<form:form class="orderStatusForm" action="${request.contextPath}/csr-emulation/csrReport" enctype="multipart/form-data" method="post" id="fmApplicationUsageForm" commandName="fmApplicationUsageForm">              
 				<div class="row">
                  <div class="form-group col-sm-4">
                    <form:input id="accountNumber" path="accountNumber" class="form-control" type="text" placeholder="Account #" maxlength="10" onKeyPress="return isNumber(event)"/>
                  </div>
                  <div class="form-group col-sm-4">
                    <form:input id="userid" path="userid" class="form-control" type="text" placeholder="user ID #" onblur="validateuser()" />
                  </div>
                   <div class="form-group col-sm-2" >
                    <div class="controls" >
                      <div class="input-group " >
 					<form:input type="text" path="startDate" required="required"  id="date-picker-8"  placeholder="Start Date" class="date-picker form-control"/>
						<label for="date-picker-8" class="input-group-addon btn">
 							<span id="startDate" class="glyphicon glyphicon-calendar"></span>
  						</label>
                      </div>
                    </div>
                  </div>
                   <div class="form-group col-sm-2" >
                    <div class="controls" >
                      <div class="input-group " >
  					<form:input type="text" path="endDate" required="required"  id="date-picker-9" placeholder="End Date" class="date-picker form-control"/>
  						<label for="date-picker-9" class="input-group-addon btn">
  						<span id="endDate" class="glyphicon glyphicon-calendar"></span>
  						 </label>
                      </div>
                    </div>
                  </div>
		<span class="errorMsg fm_fntRed lftMrgn_14" id="userid1"> </span>
                </div>
                <div class="row">
                  <div class="form-group col-sm-12">
                    <div class="pull-right">
                      <button type="submit" class="btn btn-default btn-fmDefault">Search</button>
                      <button type="reset"  class="btn btn-default btn-fmDefault">Reset</button>
                    </div>
                  </div>
                </div>
                 <span class="errorMsg fm_fntRed" id="accountNumber1"> </span>
                </form:form>
                 </div>
                 <c:if test="${userUsageDetails !=null }">
              <div class="topMargn_25">
                <div class="table-responsive userTable">
                  <table class="table tablesorter" id="myTable">
                    <thead>
                      <tr class="text-capitalize">
                        <th class="">account # </th>
                        <th class="">customer name</th>
                        <th class="">emergency </th>
                        <th class="">regular </th>
                        <th class="">stock </th>
                         <th class="">pickup </th>
                          <th class="">IPO-order </th>
                        <th class="">upload-order</th>
			<th class="">Inventory</th>
			<th class="">OrderStatus</th>
			<th class="">BackOrder</th>
			<th class="">INVOICE</th>
			<th class="">Part
				Inter-Change</th>


			
			                       
                        
                      </tr>
                    </thead>
                    <tbody>
                       <c:forEach items="${userUsageDetails}" var="csrUsageList">  
                          <tr class="text-capitalize">                    	
                            <td>${csrUsageList.get(0)}</td>                           
                            <td>${csrUsageList.get(1)}</td>     
                            <td>${csrUsageList.get(2)}</td>
                             <td>${csrUsageList.get(3)}</td>
                             <td>${csrUsageList.get(4)}</td>
                             <td>${csrUsageList.get(5)}</td>
                             <td>${csrUsageList.get(6)}</td>
                             <td>${csrUsageList.get(7)}</td>
				<td>${csrUsageList.get(8)}</td>
				<td>${csrUsageList.get(9)}</td>
				<td>${csrUsageList.get(10)}</td>
				<td>${csrUsageList.get(11)}</td>
				<td>${csrUsageList.get(12)}</td>



				
                          </tr>
                      </c:forEach>
		                	


	


                   </tbody>
                  </table>
                 </div>
              </div>
              </c:if>
            </div>
        </div>
     
  