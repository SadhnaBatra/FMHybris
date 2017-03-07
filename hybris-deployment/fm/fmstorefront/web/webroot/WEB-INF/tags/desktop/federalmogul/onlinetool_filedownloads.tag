<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url value="/my-fmaccount/taxexemption" var="submitAction" />
<c:url value="/my-fmaccount/taxexemption-approval" var="sortAction" />

<%-- <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
	<div class="manageUserPanel rightHandPanel clearfix">
		<h2 class="text-uppercase">File Downloads</h2>





		<form class="topMargn_25" method="POST" action="${submitAction}"
			enctype="multipart/form-data">

			<div class="table-responsive userTable topMargn_10">
				<table id="regPlaceOrderTable"
					class="table tablesorter regPlaceOrderTable">
					<thead>
						<tr>
							<th style="width:09.5rem !important">Serial No</th>
							<th>File Name</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${ fmFileDownloadsList}" var="fmFileDownload"
							varStatus="status">
							<tr>
								<td><div class="form-group" >${status.index+1}</div></td>
								<td><div class="form-group ">
										<div class="input-group" style="width: 18.5rem !important">
											<c:url
												value="/my-fmaccount/taxfiledownload/${TaxDocument.pk}"
												var="downloadTaxDocument" />
											<a href="${fmFileDownload.downloadURL }">${fmFileDownload.realFileName}
											</a>

										</div>
										</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

		</form>

	</div>
</div> --%>


 
        <div class="fileDownloadPanel rightHandPanel clearfix">
        	<c:choose>
        		<c:when test="${not empty dirname}">
        			<h3 class="text-uppercase fileDownloadHeading">${dirname}</h3>
        		</c:when>
        		<c:otherwise>
        			<h3 class="text-uppercase fileDownloadHeading">File Downloads</h3>
        		</c:otherwise>
        	</c:choose>
          
         <!--  <div class="clearfix">
            <div class="col-lg-6 col-md-6  col-sm-12  col-xs-12 fileDownloadSubHeading">
              <p class="fileDownloadSubHLeft">The UPC barcode files listed below are for resale use ONLY!
                NOTE: See Listing/Layout for new standard layout for all UPC files.</p>
              <span>Click on the filename to download</span> </div>
            <div class="col-lg-6 col-md-6  col-sm-12  col-xs-12 fileDownloadSubHright" ><span class="pull-right" >Do not see what you need? <span><a href="mailto:womfileDownloads@federalmogul.com" class="fm-blue-text DINWebBold">Request Unlisted Files</a></span> </span></div>
          </div> -->
          <c:set var="fmComponentName" value="FileDownloadtext" scope="session" />
	        <cms:pageSlot position="OTOverViewFileDownloads" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
         <%--  <form class="orderStatusForm">
                <div class="row">
                  <div class="form-group col-sm-3">
                    <input id="pruchaseOrder" class="form-control" type="text" placeholder="File name">
                  </div>
                  <div class="form-group col-sm-3">
                    <input id="confirmationOrder" class="form-control" type="text" placeholder="Description">
                  </div>
                  <div class="form-group col-sm-3">
                    <input id="confirmationOrder" class="form-control" type="text" placeholder="File format">
                  </div>
                  <div class="form-group col-sm-3">
                    <button type="button" class="btn btn-default btn-fmDefault pull-right">Search</button>
                  </div>
                </div>
              </form> --%>
          <div class="table-responsive col-lg-12 userTable fileDownloadTable">
            <table id="myTable" class="table tablesorter">
              <thead>
                <tr class="text-capitalize fileDownloadThead">
                  <th class="header">File Name </th>
                  <th class="header">Description </th>
                  <th class="header">Last Revised </th>
                  <th class="header">File Format </th>
                  <th class="header">File Size</th>
                </tr>
              </thead>
              <tbody class="fileDownloadTbody">
              	<c:forEach items="${ fmFileDownloadsList}" var="fmFileDownload"
							varStatus="status">
							<tr class="">
							<td><a
								class="orderNo text-uppercase fileDownloadOrder fm-blue-text"
								href="${fmFileDownload.downloadURL}">${fmFileDownload.fileName}</a></td>
							<td>${fmFileDownload.description} </td>
							<td>${fmFileDownload.lastRevisedDate}</td>
							<td>${fmFileDownload.fileFormat}</td>
							<td>${fmFileDownload.fileSize}</td>
						</tr>

					</c:forEach>
              </tbody>
            </table>
          </div>
   </div>
   
          
     <%--     <section class="tableFilter col-lg-12 clearfix">
            <div class="visible-lg visible-md visible-sm ">
              
              <div class="col-lg-4 col-md-4 col-xs-4  col-lg-offset-8 text-right PaginationNav">
                <form class="form-horizontal">
                  <div class="form-group">
                    <label class="control-label visible-lg-inline visible-md-inline visible-sm-inline" for="page">Page</label>
                    <input type="text" class="form-control visible-lg-inline visible-md-inline visible-sm-inline width45" value="1"   />
                    <label class="control-label visible-lg-inline visible-md-inline visible-sm-inline"> of 32</label>
                    <button type="button" class="fa fa-angle-left pagination-prev-page visible-lg-inline visible-md-inline visible-sm-inline"></button>
                    <button type="button" class="fa fa-angle-right pagination-next-page "></button>
                  </div>
                </form>
              </div>
            </div>
            <div class="visible-xs ">
              <div class="col-lg-4 col-md-4 col-xs-2">
                <button value="Fiter" class="fa fa-gear"></button>
              </div>
              <div class="col-lg-4 col-md-4 col-xs-3">
                <select class="form-control" id="display">
                  <option> View </option>
                  <option>9</option>
                  <option>18</option>
                  <option>27</option>
                  <option>36</option>
                  <option>45</option>
                  <option>54</option>
                  <option>64</option>
                  <option>72</option>
                  <option>81</option>
                </select>
              </div>
              <div class="col-lg-4 col-md-4 col-xs-7 text-right">
                <button type="button" class="fa fa-chevron-left"></button>
                Page 2 of 5
                <button type="button" class="fa fa-chevron-right"></button>
              </div>
            </div>
          </section>
        </div>
      </div>   --%>







