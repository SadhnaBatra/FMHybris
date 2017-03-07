<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="pageTitle" required="true" type="java.lang.String"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>

<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
	<div class="fileDownloadPanel rightHandPanel clearfix">
		<h3 class="text-uppercase fileDownloadHeading">${pageTitle}</h3>
		<c:url value="/online-tools/research-uploadFile" var="submitAction" />
		<!-- <form class="topMargn_25" enctype="multipart/form-data" method="post"
			action="${submitAction}">
			<div class="table-responsive userTable topMargn_10">
				<div class="form-group ">
					<div class="input-group width375">
						<input type="text" readonly=""
							placeholder="Upload a Research File"
							class="form-control   orderUploadInput " >
						<span class="input-group-btn text-right"> <span
							class="btn btn-primary btn-file orderUploadButton"> <span
								class="fa fa-arrow-circle-o-up "></span> 
								<input type="file"
								class="" multiple="" id="researchFile" name="researchFile">
						</span>
						</span>
					</div>
				</div>
				<div class="clearfix btnHolder">
					<div class=" ">
						<button type="submit"
							class="btn btn-sm btn-fmDefault text-uppercase registrationBtns ">Submit</button>
					</div>
				</div>
			</div>
		</form> -->

		<c:if test="${pageTitle ne 'Market Your Shop'  }">
			<c:set var="fmComponentName" value="Researchtext" scope="session" />
		        <cms:pageSlot position="OTOverViewResearch" var="feature">
					<cms:component component="${feature}" />
				</cms:pageSlot>
		</c:if>
		
		<c:if test="${pageTitle eq 'Market Your Shop' }">
			<c:set var="fmComponentName" value="Researchtext" scope="session" />
		        <cms:pageSlot position="OTOverViewOrderPOPMaterials" var="feature">
					<cms:component component="${feature}" />
				</cms:pageSlot>
		</c:if>		
		



		<div class="table-responsive col-lg-12 userTable fileDownloadTable">
            <table id="myTable" class="table tablesorter">
              <thead>
                <tr class="text-capitalize fileDownloadThead">
                  <th class="header">File Name </th>
                  <th class="header">Description </th>
                  <th class="header">Last Revised </th>
                  <!-- <th class="header">File Format </th> -->
                  <th class="header">File Size(kb) </th>
                </tr>
              </thead>
              <tbody class="fileDownloadTbody">
              	<c:forEach items="${ fmFileDownloadsList}" var="fmFileDownload"
							varStatus="status">

						<tr class="">
							<td><a
								class="orderNo text-uppercase fileDownloadOrder fm-blue-text"
								href="${fmFileDownload.downloadURL}">${fmFileDownload.realFileName}</a></td>
							<td>${fmFileDownload.description}</td>
							<td><fmt:formatDate value="${fmFileDownload.modifiedtime }" pattern="MM-dd-yyyy"/></td>
							<!-- <td>${fmFileDownload.mime}</td> -->
							<td>${fmFileDownload.size}</td>
						</tr>

					</c:forEach>
              </tbody>
            </table>
          </div>
		


	</div>
</div>