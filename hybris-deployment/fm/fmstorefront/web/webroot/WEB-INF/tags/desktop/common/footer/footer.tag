<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="footer" tagdir="/WEB-INF/tags/desktop/common/footer"%>

<%-- <cms:pageSlot position="Footer" var="feature" element="div" class="footer">
	<cms:component component="${feature}"/>
</cms:pageSlot> --%>

<%-- working 
<div class="row footerRow">
	 <footer>
	 	
		 <cms:pageSlot position="Footer" var="feature" element="footer">
			<cms:component component="${feature}"/>
		</cms:pageSlot> 
	</footer> 
	

</div>
 --%>

<c:set var="newsletterEmailURL"><spring:eval expression="@propertyConfigurer.getProperty('newsletteremailurl')" /></c:set>
<div class="social-outreach">
	<div class="social-outreach-background page-content-container">
		<div class="social-outreach-newsletter social-outreach-segment">
			<h5><spring:theme code="newsletter.signup" text="Sign up for our newsletter"/></h5>
			<div class="social-outreach-newsletter-form">
				<form method="POST" action="https://fmmotorparts.us2.list-manage.com/subscribe?u=60eb451af370e6e2231feb590&id=6ae03b2b36" class="ng-pristine ng-valid social-outreach-newsletter-border">
					<button class="button-secondary" href="${newsletterEmailURL}" target="_self">
						<spring:theme code="newsletter.signup.button" text="Sign Up"/>
					</button>
				</form>
			</div>
		</div>
		<div class="social-outreach-content social-outreach-segment">
			<h5 class="social-outreach-title"></h5>
			<div class="social-outreach-icon-container">
				<a class="social-outreach-nav-icon" title="Facebook" href="https://www.facebook.com/FMmotorparts?fref=ts" target="_blank"><i class="fa fa-facebook"></i></a>
				<a class="social-outreach-nav-icon" title="Twitter" href="https://twitter.com/FMmotorparts" target="_blank"><i class="fa fa-twitter"></i></a>
				<a class="social-outreach-nav-icon" title="Instagram" href="https://instagram.com/fmmotorparts" target="_blank"><i class="fa fa-instagram"></i></a>
				<a class="social-outreach-nav-icon" title="LinkedIn" href="https://www.linkedin.com/company/federal-mogul-motorparts" target="_blank"><i class="fa fa-linkedin"></i></a>
				<a class="social-outreach-nav-icon" title="YouTube" href="https://www.youtube.com/channel/UCHG0iMdw5inMgvnXiOQS0YQ" target="_blank"><i class="fa fa-youtube-play"></i></a>
			</div>
		</div>
	</div>
</div>

<footer class="footerRow">
  	<div class="container">
	 	<div class="col-lg-10 col-lg-offset-1" style="margin-left: 0.333%; width: 106.333%; !important">
			<div class="row">
	  			<div class="footerLinks clearfix" >
				 	<cms:pageSlot position="Footer" var="feature" >
						<cms:component component="${feature}"/>
					</cms:pageSlot>
				 	<div class="col-xs-12 col-lg-2 link-container visible-lg visible-md visible-sm">
						<%-- 
						</br></br>
					       <h5>eStore</h5>
					        <div class="">
							<div class="" >
					                  <ul class="unstyled mobslider" >          
					                     <li class=""><a href="#" class="">Gear</a></li>
					                </ul>
					        </div>
							</div> 
						--%>

				    </div>
				</div>
				<div class="copyRightRow">
					<c:set var="fmComponentName" value="extendedfooterLinks"
						scope="session" />
						<cms:pageSlot position="Footer" var="feature">
							<cms:component component="${feature}"/>
						</cms:pageSlot>
					
				</div>
				<footer:language/>
			</div>	 
		</div>
	</div>
</footer>	
