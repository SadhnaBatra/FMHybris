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
 
        <div class="fileDownloadPanel rightHandPanel clearfix">
          <h3 class="text-uppercase fileDownloadHeading">HELP CENTER</h3>
          <hr/>
          <div class="clearfix">
             <c:set var="fmComponentName" value="HelpCenterHeader" scope="session" />
		        <cms:pageSlot position="OTOverViewFileDownloads" var="feature">
					<cms:component component="${feature}" />
				</cms:pageSlot>            
          </div>
       
          <div class="table-responsive col-lg-12 userTable fileDownloadTable">
            <table id="myTable" class="table tablesorter">
              <thead>
                <tr class="text-capitalize fileDownloadThead">
                  <th class="header">File Name </th>
                  <th class="header">Description</th>
                 
                  
                </tr>
              </thead>
              <tbody class="fileDownloadTbody">
              	<c:forEach items="${ fmFileDownloadsList}" var="fmFileDownload"
							varStatus="status">

						<tr class="">
							<c:choose>
								<c:when test="${fmFileDownload.fileFormat eq 'video' }">
									<td width="30%"><a	target="_blank" class="orderNo text-uppercase fileDownloadOrder fm-blue-text" href="${fmFileDownload.URL}">${fmFileDownload.fileName}</a></td>
									<td width="70%">${fmFileDownload.description}</td>
								</c:when>
								<c:otherwise>
									<td width="30%"><a	class="orderNo text-uppercase fileDownloadOrder fm-blue-text" href="${fmFileDownload.downloadURL}">${fmFileDownload.fileName}</a></td>
									<td width="70%">${fmFileDownload.description}</td>
								</c:otherwise>
							</c:choose>							
							
						</tr>

					</c:forEach>
              </tbody>
            </table>
          </div>
   </div>
   
          
     







