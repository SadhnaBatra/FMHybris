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
                <li class="active"><a href="#vehicle" role="tab" data-toggle="tab">Vehicle</a></li>
                <li class="dropdown"> <a href="#" id="myTabDrop1" class="dropdown-toggle" data-toggle="dropdown">Category <span class="caret"></span></a>
                  <ul class="dropdown-menu" role="menu" aria-labelledby="myTabDrop1">
                    <li><a href="#dropdown2" tabindex="-1" role="tab" data-toggle="tab">Passenger &amp; Light Truck</a></li>
                    <li><a href="#dropdown3" tabindex="-1" role="tab" data-toggle="tab">Commercial, Industrial &amp; Ag.</a></li>
                    <li><a href="#dropdown4" tabindex="-1" role="tab" data-toggle="tab">Performance</a></li>
                    <li><a href="#dropdown5" tabindex="-1" role="tab" data-toggle="tab">Marine</a></li>
                    <li><a href="#dropdown6" tabindex="-1" role="tab" data-toggle="tab">Power Sport</a></li>
                    <li><a href="#dropdown7" tabindex="-1" role="tab" data-toggle="tab">Small Engine</a></li>
                  </ul>
                </li>
                <li><a href="#VIN" role="tab" data-toggle="tab">VIN</a></li>
                <li><a href="#competitor" role="tab" data-toggle="tab">Competitor</a></li>
              </ul>
              <!--/.nav-tabs.content-tabs -->
              
              <div class="tab-content">
                <div class="tab-pane ymm-tab-pane fade in active clearfix" id="vehicle">
                	
                  <form class="ymmForm pull-left col-sm-4">
                    <h3 class="ymmPanelHeading"> <strong>Vehicle</strong></h3>
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
                    <div class="checkbox">
                      <!-- <label>Model</label> -->
                      <select class="form-control">
                      	<option>Model</option>
                      </select>
                    </div>
                    <a href="productlist.html" class="btn btn-fmDefault">Look it Up</a>
                  </form>
                  <div class="pull-right visible-lg visible-md visible-sm" ><img class="img-responsive" src="C://TECH_TRAINING//FM//Hybris5.2.0.2//extensions//fm//fmstorefront//web//webroot//_ui//desktop//common//images//partner-img.png"/>
                  </div>
                </div>
                <!--/.tab-pane -->
                <div class="tab-pane fade" id="dropdown1">
                  <p>Dropdown1 - ...</p>
                </div>
                <!--/.tab-pane -->
                <div class="tab-pane fade" id="VIN">
                  <p>VIN : ...</p>
                </div>
                <!--/.tab-pane -->
                
                <div class="tab-pane fade" id="dropdown2">
                  <div >Dropdown 2 - ...</div>
                </div>
                <!--/.tab-pane -->
                <div class="tab-pane fade" id="competitor">
                  <div >Dropdown 2 - ...</div>
                </div>
              </div>
              <!--/.tab-content --> 
             
 </div>
<c:set var="fmComponentName" value="troubleShoot" scope="session" />
