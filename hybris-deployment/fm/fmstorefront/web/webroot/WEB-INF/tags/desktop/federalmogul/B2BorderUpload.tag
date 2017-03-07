<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="formTarget" value="" />
<c:if test="${displayIFrame}">
	<c:set var="formTarget" value="_parent" />
</c:if>
   <!--- Upload Order PANEL -->
        <div class="col-lg-4 b2b-home-pane">
          <div class="panel panel-default uploadOrderPanel">
            <div class="panel-body">
              <h2 class="panel-title text-uppercase uploadOrderTitle"><span class="fa fa-file-text"></span> <span class="text-uppercase"><spring:theme code="B2BHomepage.orderUpload.title"/></span></h2>
              <p><spring:theme code="B2BHomepage.orderUpload.description"/></p>
              <div class="">
            	 <c:choose>
              		<c:when test="${fn:contains(uploadStatus, 'successfully')}">
              			<span class="fm_fntGreen"> ${uploadStatus } </span>
              		</c:when>
              		<c:otherwise>
              			<span class="fm_fntRed">${uploadStatus}</span>
              		</c:otherwise> 
		</c:choose>                
		<form:form target="${formTarget}" method="POST" id="upload" commandName="orderUpload"  action="${request.contextPath}/uploadOrder/upload-order" enctype="multipart/form-data">
                 <div class="form-group">
                  <label for="descFile" ><spring:theme code="B2BHomepage.orderUpload.purchaseOrder"/></label>
                  <form:input id="purchaseNumber" class="form-control" maxlength="19" path="PONumber" type="text" placeholder="Purchase Order Number" required="required"/>
                </div>
                <div class="form-group">
                  <label for="uploadFile" ><spring:theme code="B2BHomepage.orderUpload.file"/></label>
                  <div class="input-group">
                      <input type="text" class="form-control  orderUploadInput" readonly>
                      <span class="input-group-btn text-right">
                          <span class="btn btn-primary btn-file orderUploadButton">
                              <span class="fa fa-arrow-circle-o-up "></span> 
                              <form:input id="uploadOrderFile" type="file" class="" path="uploadFile" onchange="ValidateFileExtension(this);" required="required"/>
                              <!-- <input type="file" multiple> -->
                          </span>
                      </span>
                  </div>
                </div>
                <div class="form-group">
                  <label for="descFile" ><spring:theme code="B2BHomepage.orderUpload.comments"/></label>
                  <form:input id="descFile" class="form-control"  maxlength="60" path="description" type="text" placeholder="Order Comments"/>
                </div>
                
                <div class="">
		<div class="reviewLnk pull-left">
		<span class="fa fa-table"></span>
		 <a data-toggle="modal" href="#upload-brandprefix"> <spring:theme code="B2BHomepage.orderUpload.brandPrefix"/></a>
					<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="upload-brandprefix" class="modal fade shipToModel in fixedModalHeight" style="display: none;">
				<federalmogul:brandprefix pageType="uploadOrder"/>
					</div>
			</br>
                     <a target="${formTarget}" href="/medias/Sample-format-for-Text.txt?context=bWFzdGVyfHJvb3R8MzYxfHRleHQvcGxhaW58aGI1L2hmNy84OTgxMzM1MDgwOTkwLnR4dHwzYjA1NTFiMDEyNTJmZDJlMzg0YmY5NGUyMTI3YWFhMjFkZDI2OGU1NTEzZjAzODhkZGM3NmNmMGQ0ZDVhNWZj&attachment=true">Sample format for Text</a>&nbsp;&nbsp;<span class="fa fa-chevron-right"> </span>
                   </br> <a target="${formTarget}" href="/medias/Sample-format-for-Excel.xls?context=bWFzdGVyfHJvb3R8MzU4NDB8YXBwbGljYXRpb24vZXhjZWx8aDI0L2hkZC84OTgxMzM1MDE1NDU0Lnhsc3wzNGU0MGQwMmUzMDRhNmY0OWI4MWFmNWQyZDc0YTYwMGViNzk5NjkzNWJiZDdmMmI1ODY4YmNkNDdiZmY0ZjFl&attachment=true">Sample format for Excel</a>&nbsp;&nbsp;<span class="fa fa-chevron-right"> </span> </div>
                  <div class="pull-right"> 
                  
                  <button type="submit" onsubmit="submit();" class="btn  btn-sm btn-fmDefault"><spring:theme code="B2BHomepage.orderUpload.uploadOrder"/></button>
                  
               <!--    <a href="uploadOrder/upload-order" class="btn  btn-sm btn-fmDefault ">Upload Order</a></div> -->
                </div>
                </div>
                
             
              </form:form>
              </div>
            </div>
           
          </div>
           
        </div>
     
