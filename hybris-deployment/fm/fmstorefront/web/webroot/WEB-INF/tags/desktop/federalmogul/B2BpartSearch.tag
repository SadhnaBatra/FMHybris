<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>

 <div class="col-lg-6"> 
          <!-- begin tabs going in wide content -->
              
              <ul class="nav nav-tabs content-tabs appLkpTabContent" id="appLkpTabContent" role="tablist">
                <li class="ymmTabHeading visible-lg visible-md" >
                  <div class="panel-heading">
                    <h3 class="panel-title"><strong>Parts Finder</strong></h3>
                  </div>
                </li>
                    <li class="active"><a href="#application" role="tab" data-toggle="tab">Application</a></li>
            <li> <a href="#category" data-toggle="tab" role="tab">Category </a></li>
            <li><a href="#interchange" role="tab" data-toggle="tab">Interchange</a></li>
          </ul>
          <!--/.nav-tabs.content-tabs -->
          
          <div class="tab-content appLkpTabContentBlock">
            <div class="tab-pane ymm-tab-pane fade in active clearfix" id="application">
              <form class="ymmForm pull-left col-sm-4">
                <h3 class="ymmPanelHeading"> <strong>Application</strong></h3>
                <div class="form-group"> 
                  <!--<label for="year" >Year</label> -->
                  <input id="year" class="form-control" type="text" placeholder="Year">
                </div>
                <div class="form-group"> 
                  <!-- <label for="make">Make</label> -->
                  <select id="make" class="form-control">
                    <option>Make</option>
                  </select>
                </div>
                <div class="form-group">  
                  <!-- <label>Model</label> -->
                  <select class="form-control">
                    <option>Model</option>
                  </select>
                </div>
                <div class="form-group">
                <a href="productlist.html" class="btn  btn-sm btn-fmDefault">Look it Up</a>
                </div>
              </form>
              <div class="pull-right visible-lg visible-md visible-sm vehicleBg" >
              <!-- <img class="img-responsive" src="images/partner-img.png"/> -->
              <c:set var="fmComponentName" value="partSearchImage" scope="session" />
	                  <cms:pageSlot position="B2BPartSearch" var="feature">
							<cms:component component="${feature}" />
					  </cms:pageSlot>
               </div>
            </div>
            <!--/.tab-pane -->
            <div class="tab-pane fade ymm-tab-pane" id="category">
              <ul class="">
                <li><a href="#">Passenger &amp; Light Truck</a></li>
                <li><a href="#">Commercial, Industrial &amp; Ag.</a></li>
                <li><a href="#">Performance</a></li>
                <li><a href="#">Marine</a></li>
                <li><a href="#">Power Sport</a></li>
                <li><a href="#">Small Engine</a></li>
              </ul>
            </div>
            <!--/.tab-pane -->
            <div class="tab-pane fade ymm-tab-pane " id="interchange">
              <h3 class="ymmPanelHeading"> <strong>Interchange</strong></h3>
              <div >Interchange Content</div>
            </div>
          </div>
          <!--/.tab-content --> 
          
        </div>
		
    <c:set var="fmComponentName" value="announcement" scope="session" />
<!-- <script>

function SearchQuery(){
	 var sel_year= $('#year option:selected').text();
	 var sel_make= $('#make option:selected').text();
	 var sel_model= $('#model option:selected').text();
	 var queryVal=window.location.href +"search?q=:name-desc:year:"+sel_year.trim()+":make:"+sel_make.trim()+":model:"+sel_model.trim()+"&text=#";
	 $("#ymmSearch").prop("href", queryVal);
	 location.href = queryVal;
}

</script> -->