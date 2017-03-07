<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<!-- Custom tag files for federalmogul -->
<%@ taglib prefix="fmproduct" tagdir="/WEB-INF/tags/desktop/fmproduct"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>

 <template:page pageTitle="${pageTitle}">

	<section class="breadcrumbPanel visible-lg visible-md visible-sm"
		itemscope itemtype="http://schema.org/Product">
		<div class="container">
			<div class="row">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}" />				
			</div>
		</div>
	</section>
	<div id="globalMessages">
		<common:globalMessages />
	</div>
	<section class="productDetailPage pageContet">
		<section class="productDetailContent">
			<div class="container">
				<div class="clearfix bgwhite">
					<div class="col-lg-12 ">
					<div class="pull-left"><a href="javascript:history.back()" class="addNewAddLink"><span class="linkarow fa fa-angle-left"></span> Back to Product List</a></div>
						<ul class="printShareSaveLink pull-right">
							<li><product:productShareTag /></li>
								<!-- <ul class="dropdown-menu">
									 <li class="visible-lg-inline"><a
										href="https://www.facebook.com/sharer/sharer.php?u="
										title="Share on Facebook" target="_blank" class="fbSprite"
										id="fbbtn"><i class="fa fa-facebook"></i></a></li> 
									 <li class="visible-lg-inline">
										<a href="http://twitter.com/home?status="
										title="Share on Twitter" target="_blank" class="twitterSprite"
										id="twitbtn"><i class="fa fa-twitter"></i></a></li>
									
									<li class="visible-lg-inline"> 
										  <a
										href="http://www.linkedin.com/shareArticle?mini=true&url=&title=&summary="
										title="Share on LinkedIn" target="_blank"
										class="linkedinSprite" id="lnkdbtn"><i
											class="fa fa-linkedin" ></i></a></li>
								</ul> -->
							<!-- <li><a href="#"><span class="fa fa-print"></span> Print</a></li>
							<li><a href="#"><span class="fa fa-floppy-o"></span>Save</a></li>  -->
						</ul>					</div>
				</div>
				<div class="clearfix bgwhite">
					<fmproduct:fmProductDetailsPanel product="${product}" galleryImages="${galleryImages}" categoryCode="${fn:trim(categoryCode)}"/>	
					<div class="clearfix bgwhite">
					 <product:productPageTabs />
				</div>
					
				</div>
				
			</div>

		</section>
		<%-- <section class="suggestedProduct">
			<div class="container">
				<fmproduct:productsuggestions product="${product}" galleryImages="${galleryImages}"/>
			</div>
		</section> --%>

		
	</section>
	<div class="clearfix">
		<div class="well well-sm well-brandstrip clearfix">
			<ul class="nav ">
				<c:set var="fmComponentName" value="brandStrip" scope="session" />

				<cms:pageSlot position="FMBrandstrip" var="feature">
					<cms:component component="${feature}" />
				</cms:pageSlot>

			</ul>
		</div>
	</div>
</template:page>


