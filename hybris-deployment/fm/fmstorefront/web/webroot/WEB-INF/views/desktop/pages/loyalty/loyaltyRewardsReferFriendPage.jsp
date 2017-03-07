<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>



<template:page pageTitle="${pageTitle}">

<style>
 
.loyalty-banner{
	color: white;
	position: relative;
}
.loyalty-banner	.loyaltyBannerContent {
	padding-left: 20px;
	padding-left: 2rem;
	padding-top: 40px;
	padding-top: 4rem;
	position: absolute;
	top: 0%;
	width: 100%;
}
</style>

<section class="breadcrumbPanel visible-lg visible-md visible-sm" itemscope itemtype="http://schema.org/Product">
	<div class="container">
		<div class="row">
			<ul class="breadcrumb">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}" />
			</ul>
		</div>
	</div>
</section>

<div id="fb-root"></div>
    <script>(function(d, s, id) {
      var js, fjs = d.getElementsByTagName(s)[0];
      if (d.getElementById(id)) return;
      js = d.createElement(s); js.id = id;
      js.src = "//connect.facebook.net/en_US/all.js#xfbml=1";
      fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));
	</script>


<section class="rewardsReferFriend contentPage"> 
    <section class="accountDetailPage pageContet" >
    <div class="container">
        <div class="row">
		    <c:url value="/loyalty/referEmail" var="sendEmail"/>
	        <div class="col-md-3 bggrey">
			    <c:set var="fmComponentName" value="leftnavgaragereward" scope="session" />
			    <cms:pageSlot position="FMAnonymousGarageRewardsLeftNavSection" var="feature">
			    <cms:component component="${feature}" />
			    </cms:pageSlot>
		    </div>
		    <div class="col-md-9 bgwhite">			
                <div class="col-md-9 loyalty-banner lftPad_0">
                    <c:set var="fmComponentName" value="loyaltyBanner" scope="session" />
			        <cms:pageSlot position="HomeRewardsBannerSection" var="feature">
			            <cms:component component="${feature}" />
			        </cms:pageSlot>	
                </div>
                <div class="col-md-3 rewardsPanel">
                    <h1 class="panel-title"><span class="fa fa-certificate"></span> <span class=" text-uppercase"><spring:theme code="text.Referfriend.MYREWARDS"/></span></h1>
                    <div class="rewardsPoints"><i class="fa fa-wrench"></i><strong>${TotalPoints}<sub><spring:theme code="text.Referfriend.POINTS"/></sub></strong></div>
                    <div class="text-center">
                        <form role="form" class="form-horizontal rewardsPanelForm" id="rewardsform">
                            <div class="controls clearfix"> 
						         <a class="btn  btn-sm btn-fmDefault pull-rightn text-uppercase" href="/fmstorefront/loyalty/en/USD/loyalty/earn" id="btn-login"><spring:theme code="text.Referfriend.Earn"/></a>
						    </div>
                        </form>
                        <a class="text-capitalize addNewAddLink" href="loyalty/history"><spring:theme code="text.Referfriend.POINTSHISTORY"/><span class="linkarow fa fa-angle-right "></span></a>
			         </div>
                </div> 
                <div class="col-md-12 rightHandPanel lftPad_0">
                    <c:set var="fmComponentName" value="benefits" scope="session" />
                    <cms:pageSlot position="HomeRewardsLatestProductsSection" var="feature">
			            <cms:component component="${feature}" />
			        </cms:pageSlot>
				<div>	
                    
                <div class="col-md-5">
                	<h2 class="text-capitalize"><spring:theme code="text.Referfriend.EMAIL"/></h2>
                	<div><spring:theme code="text.Referfriend.Technician"/></div>
                    <div id="globalMessages">					
			           <common:globalMessages />
				    </div>
                    <form:form class="topMargn_20" role="form" method="POST" commandName="ReferalForm" action="${sendEmail}"> 
                        <div class="form-group">
							<div>
								<div class="lftPad_0 col-lg-12">
									<label for="emailAddress"><spring:theme code="text.Referfriend.EmailAddress"/><br> </label>
								</div>
								
								<div class="lftPad_0 col-lg-12">
									<div class="lftPad_0 form-group col-lg-4">
										<form:input id="firstName0" type="text" class="form-control width100" placeholder="First Name" path="referals[0].fName"/>
									</div>
									<div class="lftPad_0 form-group col-lg-4">
										<form:input id="lastName0" type="text" class="form-control width100" placeholder="Last Name" path="referals[0].lName"/>
									</div>
									<div class="lftPad_0 form-group col-lg-4">
										<form:input id="email0" type="email" class="form-control width195" placeholder="Email" path="referals[0].mailId"/>
									</div>
								</div>
								
								<div class="lftPad_0 col-lg-12">
									<div class="lftPad_0 form-group col-lg-4">
										<form:input id="firstName1" type="text" class="form-control width100" placeholder="First Name" path="referals[1].fName"/>
									</div>
									<div class="lftPad_0 form-group col-lg-4">
										<form:input id="lastName1" type="text" class="form-control width100" placeholder="Last Name" path="referals[1].lName"/>
									</div>
									<div class="lftPad_0 form-group col-lg-4">
										<form:input id="email1" type="email" class="form-control width195" placeholder="Email" path="referals[1].mailId"/>
									</div>
								</div>
								
								<div class="lftPad_0 col-lg-12">
									<div class="lftPad_0 form-group col-lg-4">
										<form:input id="firstName2" type="text" class="form-control width100" placeholder="First Name" path="referals[2].fName"/>
									</div>
									<div class="lftPad_0 form-group col-lg-4">
										<form:input id="lastName2" type="text" class="form-control width100" placeholder="Last Name" path="referals[2].lName"/>
									</div>
									<div class="lftPad_0 form-group col-lg-4">
										<form:input id="email2" type="email" class="form-control width195" placeholder="Email " path="referals[2].mailId"/>
									</div>
								</div>
								
								<div class="lftPad_0 col-lg-12">
									<div class="lftPad_0 form-group col-lg-4">
										<form:input id="firstName3" type="text" class="form-control width100" placeholder="First Name" path="referals[3].fName"/>
									</div>
									<div class="lftPad_0 form-group col-lg-4">
										<form:input id="lastName3" type="text" class="form-control width100" placeholder="Last Name" path="referals[3].lName"/>
									</div>
									<div class="lftPad_0 form-group col-lg-4">
										<form:input id="email3" type="email" class="form-control width195" placeholder="Email" path="referals[3].mailId"/>
									</div>
								</div>
								
								<div class="lftPad_0 col-lg-12">
									<div class="lftPad_0 form-group col-lg-4">
										<form:input id="firstName4" type="text" class="form-control width100" placeholder="First Name " path="referals[4].fName"/>
									</div>
									<div class="lftPad_0 form-group col-lg-4">
										<form:input id="lastName4" type="text" class="form-control width100" placeholder="Last Name" path="referals[4].lName"/>
									</div>
									<div class="lftPad_0 form-group col-lg-4">
										<form:input id="email4" type="email" class="form-control width195" placeholder="Email" path="referals[4].mailId"/>
									</div>
								</div>
							</div>
					    </div>
                        
                        <div class="form-group clearfix">	
                        	<button type="submit" class="btn  btn-sm btn-fmDefault text-uppercase" onclick="return validateReferalForm(5);"><spring:theme code="text.Referfriend.SEND"/></button>    
                        </div>
                    </form:form>
                </div>
                <div class="col-md-5 col-md-offset-1 col-sm-12 topMargn_20">
                    <c:set var="fmComponentName" value="benefits" scope="session" />
                    <cms:pageSlot position="HomeRewardseLearnSection" var="feature">
				        <cms:component component="${feature}" />
			        </cms:pageSlot>       
                </div>
            </div>   
        </div>
      </div>
    </section>
</section>

<!-- InstanceEndEditable -->
	
	<div class="well well-sm well-brandstrip clearfix">
		<ul class="nav ">
			<!-- <c:set var="fmComponentName" value="brandStripB2B" scope="session" />-->
			<c:set var="fmComponentName" value="brandStrip" scope="session" />

			<cms:pageSlot position="FMBrandstrip" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
	</div>
	<c:set var="fmComponentName" value="footerImageLinks" scope="session" />
</section>
</template:page>


