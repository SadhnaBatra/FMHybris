<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="col-lg-3 home-pane">
	<div class="rewadsMyclassPanel">
		<!-- SIGN IN -->
		<c:choose>
			<c:when test="${loyaltystatus eq false}">
				<div class="panel panel-default rewardsPanel" id="myrewards"
					style="display: block;">
					<div class="">
						<div class="">
							<c:set var="fmComponentName" value="b2tNotopted" scope="session" />
							<cms:pageSlot position="RewardsAndClassesB2T" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
						</div>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="panel panel-default rewardsPanel loginBoxPanel" id="myrewards"
					style="display: block;">
					<div class="panel-body">
						<h3 class="panel-title  btmMrgn_40">
							<span class="fa fa-user"></span> <span class=" text-uppercase">
								Garage rewards</span>
						</h3>
						<div class="rewardsPoints btmMrgn_40">
							<i class="fa fa-wrench"></i><strong><fmt:formatNumber
									type="number" value="${TotalPoints}" /><sub>pts</sub></strong>
						</div>
						<div class="rewardsNextlevel btmMrgn_25">
							<p class="rewardsNextlevelName text-uppercase">DON'T STOP
								THERE</p>
							<p class="rewardsNextlevelInfo">Earn more points for bigger
								rewards</p>
						</div>
						<form role="form" class="form-horizontal rewardsPanelForm"
							id="rewardsform">
							<div class="controls clearfix">
								<a id="btn-login"
									href="/fmstorefront/loyalty/en/USD?clear=true&site=loyalty"
								   <c:if test="${displayIFrame}">target="_parent"</c:if>
									class="btn  btn-sm btn-fmDefault pull-rightn text-uppercase">View
									rewards </a>
							</div>
							
						</form>
					</div>
					<div class="form-group regButtonHolder">
						<div class=" control">
							<button
								class="btn  btn-sm btn-labeled btn-fmDefault col-lg-12 col-xs-12 col-md-12  col-sm-12 myClassBtn btn-md"
								onclick="$('#myrewards').slideToggle(); $('#myclass').slideToggle()"
								type="button">
								<span class="btn-label"><i class="fa fa-plus"></i></span><span
									class="pull-left myClassBtnTxt text-uppercase">Garage
									Gurus&#8482;</span>
							</button>
						</div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	<!-- RESISTER PANEL -->
	<div class="panel panel-info myclassPanel clearfix" id="myclass"
		style="display: none;">
		<div class="form-group myClassBackbtnHolder">
			<div class=" control">
				<button type="button"
					onclick="$('#myclass').slideToggle(); $('#myrewards').slideToggle()"
					class="btn   btn-sm btn-labeled btn-fmDefault col-lg-12 col-xs-12 col-md-12  col-sm-12 myClassBackBtn btn-md">
					<span class="btn-label backToSignin"><i class="fa fa-minus"></i></span><span
						class="pull-left  myClassBtnTxt text-uppercase">back to garage rewards</span>
				</button>
			</div>
		</div>
		<div class="panel-body btmMrgn_40">
			<form id="myclassform" class="form-horizontal myclassform"
				role="form">
				<h3 class="panel-title btmMrgn_25">
					 <span
						class=" text-uppercase">Garage Gurus</span>
				</h3>
				
				<div id="myClassPointsPanel"
					class="myClassPointsPanel clearfix scroll-area" data-spy="scroll"
					data-target="#orderProgressPanel" data-offset="0"
					style="height: 100px;">
					<span>Your go-to source for training on the latest tools and technology to keep your next job on track. 
						Onsite, online or on-demand, train locally with our experts.
					</span>
				</div>
				<a href="https://federalmogul.plateau.com/learning/user/portal.do?siteID=FM-ALL&landingPage=login" target="_blank">
				<button id="btn-findclass" type="button" 
					class="btn  btn-sm btn-fmDefault btnFindClass text-uppercase topMargn_15">Join Garage Gurus</button>
					</a>
			</form>
		</div>
	</div>
</div>
</div>
