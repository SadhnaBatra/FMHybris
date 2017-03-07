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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<template:page pageTitle="${pageTitle}">

<script>
function submitLoyaltyPromocode(promotype)
{
	var inputField=promotype+'id';
	var buttonid=promotype+'Btn';
	var blockid=promotype+'Block';
	var promCode = $('#'+inputField).val();
	if(promCode!= null  || promCode != "")
	{
 		$('#'+buttonid).prop("disabled",true);
		var currentPath = window.location.href;
		var pathName = "";
		try{
			if(currentPath.indexOf("/USD") != -1)
			{
				pathName = currentPath.substring(0, currentPath.lastIndexOf("/USD") + 5);
			}
			else if(currentPath.indexOf("?site") != -1)
			{
				pathName = currentPath.substring(0, currentPath.lastIndexOf("/?site") + 1)+currentPath.substring(currentPath.lastIndexOf("site=")+5,currentPath.length)+"/en/";
			}else{
				pathName = window.location.href+"loyalty/en/USD";
			}
		}catch(e){
			//Earlier alert box was here..Removed it need to analyse at a later date to put the right exception handling
		}
		$.ajax({
			type : "POST",
			url : pathName+ "loyalty/promocode",
			data: { selectedPromo: promCode, promotype : promotype}, 
			success : function(response) {
				$('#'+blockid).replaceWith(response);
			},
			error : function(e) {
				
			}
		});
		}
}
</script>

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

<section class="breadcrumbPanel visible-lg visible-md visible-sm"
		itemscope itemtype="http://schema.org/Product">
		<div class="container">
			<div class="row">
				<ul class="breadcrumb">
					<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}" />
				</ul>
			</div>
		</div>
</section>


<!-- InstanceBeginEditable name="Page Content Section" -->
<section class="rewardsAboutPage contentPage">  
  <section class="wayToPoints accountDetailPage pageContet" >
    <div class="container">
	     <div class="col-md-3 col-sm-12 bggrey">
			<c:set var="fmComponentName" value="leftnavgaragereward" scope="session" />
			<cms:pageSlot position="FMAnonymousGarageRewardsLeftNavSection" var="feature">
			<cms:component component="${feature}" />
			</cms:pageSlot>
		 </div>
		 <div class="col-md-9 bgwhite lftPad_0">
            <div class="col-md-9 loyalty-banner">
    	        <c:set var="fmComponentName" value="loyaltyBanner" scope="session" />
  		        <cms:pageSlot position="FMSearchGridBannerSection" var="feature">
				    <cms:component component="${feature}" />
		        </cms:pageSlot>
            </div>
		    <div class="rewadsMyclassPanel redeemRewardsInfo"> 
                <div class="col-md-3 rewardsPanel">
                <h1 class="panel-title"><span class="fa fa-user"></span><span class=" text-uppercase">&nbsp;Garage rewards</span></h1>
                    <div class="rewardsPoints"><i class="fa fa-wrench"></i><strong><fmt:formatNumber type="number" value="${TotalPoints}" /><sub>pts</sub></strong></div>
                    <div class="text-center">
                      <a class="text-capitalize addNewAddLink" href="${contextPath}/federalmogul/${currentLanguage.isocode}/${currentCurrency.isocode}/loyalty/history?clear=true&site=federalmogul">View Points History <span class="linkarow fa fa-angle-right "></span></a>
					</div> 
                </div>
			</div>
		    <div class="col-md-12 rewards-earnpoints-how">
				<c:set var="fmComponentName" value="learnpara" scope="session" />
                <cms:pageSlot position="FMPointsSection" var="feature">
                    <cms:component component="${feature}" />
                </cms:pageSlot> 
                <div>					
                    <table><tbody> 					
					    <tr><td width="30%">Gurus-On-The-Go</td> <td width="20%">500<br /></td> <td width="28%">5,000 (10 training sessions)<br /></td>
				            <td>
					            <a onClick="$('.ggurus').show();$('.hideSizeLink').show();$('.promoCodeLink').hide();"
						           class="text-capitalize addNewAddLink promoCodeLink"><spring:theme code="text.EarnPoints.PromoCodeRequired"/> 
						           <span class="linkarow fa fa-angle-right"></span>
						        </a> 
						        <a onClick="$('.ggurus').hide();$('.hideSizeLink').hide();$('.promoCodeLink').show();"
						           class="text-capitalize addNewAddLink hideSizeLink" style="display: none">
						           <spring:theme code="text.EarnPoints.PromoCodeRequired"/>
						           <span class="linkarow fa fa-angle-right "></span>
						        </a>
					        </td>
				        </tr>
					    <tr class="ggurus" style="display: none;" id="ggurusBlock">
						    <td colspan="4">
							    <h2 class="text-uppercase DINWebBold"><spring:theme code="text.EarnPoints.EnterPromoCode"/></h2>
							    <p class="topMargn_15"><spring:theme code="text.EarnPoints.PromoCodetext"/></p>
							    <div class="form-group col-lg-3 lftPad_0">
							        <input type="text" value="Promo Code" placeholder="Promo Code" class="form-control" id="ggurusid">
							    </div>
							    <button type="button"  id="ggurusBtn" class="btn  btn-sm btn-fmDefault" onclick="javascript:submitLoyaltyPromocode('ggurus')">
							        <spring:theme code="text.EarnPoints.PromoCodeSubmit"/>
							    </button>
						    </td>
					    </tr>
		            </tbody></table>
                </div>     					    
                <cms:pageSlot position="FMPointsSection1" var="feature">
                    <cms:component component="${feature}" />
                </cms:pageSlot>  	
			    <div>					
                    <table><tbody> 					
					    <tr><td width="30%">Promotion</td> <td width="20%">Variable<br /></td> <td width="28%">5,000 <br /></td> 
					        <td>
						        <a onClick="$('.variablepromo').show();$('.hideSizeLink').show();$('.promoCodeLink').hide();"
							       class="text-capitalize addNewAddLink promoCodeLink"><spring:theme code="text.EarnPoints.PromoCodeRequired"/> 
							       <span class="linkarow fa fa-angle-right"></span>
							    </a> 
							    <a onClick="$('.variablepromo').hide();$('.hideSizeLink').hide();$('.promoCodeLink').show();"
							       class="text-capitalize addNewAddLink hideSizeLink" style="display: none"><spring:theme code="text.EarnPoints.PromoCodeRequired"/>
							       <span class="linkarow fa fa-angle-right "></span>
							    </a>
						    </td>
					    </tr>
					    <tr class="variablepromo" style="display: none;" id="variablepromoBlock">
						    <td colspan="4">
					            <h2 class="text-uppercase DINWebBold"><spring:theme code="text.EarnPoints.EnterPromoCode"/></h2>
					            <p class="topMargn_15"><spring:theme code="text.EarnPoints.PromoCodetext"/></p>
					            <div class="form-group col-lg-3 lftPad_0">
						            <input type="text" value="Promo Code" placeholder="Promo Code" class="form-control" id="variablepromoid">
					            </div>
					            <button type="button"  id="variablepromoBtn" class="btn  btn-sm btn-fmDefault" onclick="javascript:submitLoyaltyPromocode('variablepromo')">
								    <spring:theme code="text.EarnPoints.PromoCodeSubmit"/>
								</button>
						    </td>
						</tr>
		            </tbody></table>
                </div>     			
			    <c:set var="fmComponentName" value="learnpara" scope="session" />
                <cms:pageSlot position="FMPointsSection2" var="feature">
                    <cms:component component="${feature}" />
                </cms:pageSlot>  
			</div>
			
										 									
      	    <div class="col-md-12 contentRHS">
				<div class="pull-left rghtMrgn_20 rewardsCarousel">
					<h3 class="text-uppercase"><spring:theme code="text.EarnPoints.LatestGear"/></h3>
				</div>
				<div class="pull-right">
					<form  class="form-horizontal" role="form">
						<div class="controls clearfix topMargn_5"> 
							<a href="${contextPath}/${currentsiteUID}/${currentLanguage.isocode}/${currentCurrency.isocode}/lsearch?q=:name-asc:&text=#"  
							   class="btn  btn-sm btn-fmDefault text-uppercase"><spring:theme code="text.EarnPoints.viewallitems"/>
							</a>
						</div>
					</form>
				</div>
		    </div>	
           
			
            <div class="col-md-12">
                    <cms:pageSlot position="FMProductGridBannerSection" var="feature">
					    <cms:component component="${feature}" />
				    </cms:pageSlot>
            </div>
            
        </div>    
    </div>
  </section>
	
  <div class="well well-sm well-brandstrip clearfix">
	<ul class="nav ">
		<c:set var="fmComponentName" value="brandStrip" scope="session" />
		<cms:pageSlot position="FMBrandstrip" var="feature">
			<cms:component component="${feature}" />
		</cms:pageSlot>
	</ul>
  </div>
  <c:set var="fmComponentName" value="footerImageLinks" scope="session" />
</section>
</template:page>









