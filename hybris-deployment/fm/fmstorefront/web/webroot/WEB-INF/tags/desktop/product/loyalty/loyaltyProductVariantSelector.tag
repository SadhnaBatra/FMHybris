<%@ attribute name="product" required="true"
	type="de.hybris.platform.commercefacades.product.data.ProductData"%>

<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ attribute name="userLoyaltyPoints" required="true"
	type="java.lang.String"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:parseNumber var="productLoyaltyPoints"
	value="${product.loyaltyPoints}" type="number" />
<fmt:parseNumber var="userTotalLoyaltyPoints"
	value="${userLoyaltyPoints}" type="number" />

<div class="col-lg-12 topMargn_20 rgtPad_0">
	<c:choose>
		<%-- Verify if products is a multidimensional product --%>
		<c:when test="${product.multidimensional}">

			<c:set var="levels" value="${fn:length(product.categories)}" />
			<c:set var="selectedIndex" value="0" />

			<div class="variantOptions">
				<c:forEach begin="1" end="${levels}" varStatus="loop">
					<c:set var="i" value="0" />
					<div class=" clearfix">
						<c:choose>
							<c:when test="${loop.index eq 1}">
								<c:set var="productMatrix" value="${product.variantMatrix }" />
							</c:when>
							<c:otherwise>
								<c:set var="productMatrix"
									value="${productMatrix[selectedIndex].elements }" />
							</c:otherwise>

						</c:choose>
						<div class="variantName">${productMatrix[0].parentVariantCategory.name}</div>
						<c:choose>
							<c:when test="${productMatrix[0].parentVariantCategory.hasImage}">
								<ul class="variantList">
									<c:forEach items="${productMatrix}" var="variantCategory">
										<li
											<c:if test="${variantCategory.variantOption.url eq product.url}">class="selected"</c:if>>
											<c:url value="${variantCategory.variantOption.url}"
												var="productStyleUrl" /> <a href="${productStyleUrl}"
											class="swatchVariant"
											name="${variantCategory.variantOption.url}"> <product:productImage
													product="${product}"
													code="${variantCategory.variantOption.code}"
													format="styleSwatch" />
										</a>
										</li>

										<c:if
											test="${(variantCategory.variantOption.code eq product.code)}">
											<c:set var="selectedIndex" value="${i}" />
										</c:if>

										<c:set var="i" value="${i + 1}" />
									</c:forEach>
								</ul>
							</c:when>
							<c:otherwise>
								<select id="priority${loop.index}" class="selectPriority">
									<c:forEach items="${productMatrix}" var="variantCategory">
										<c:url value="${variantCategory.variantOption.url}"
											var="productStyleUrl" />
										<option value="${productStyleUrl}"
											${(variantCategory.variantOption.code eq product.code) ? 'selected="selected"' : ''} />${variantCategory.variantValueCategory.name}</option>

										<c:if
											test="${(variantCategory.variantOption.code eq product.code)}">
											<c:set var="selectedIndex" value="${i}" />
										</c:if>

										<c:set var="i" value="${i + 1}" />
									</c:forEach>
								</select>
							</c:otherwise>
						</c:choose>
					</div>
				</c:forEach>
			</div>
		</c:when>
		<c:otherwise>





			<%-- Determine if product is one of apparel style or size variant --%>
			<c:if test="${product.variantType eq 'ApparelStyleVariantProduct'}">
				<c:set var="variantStyles" value="${product.variantOptions}" />
			</c:if>
			<c:if
				test="${(not empty product.baseOptions[0].options) and (product.baseOptions[0].variantType eq 'ApparelStyleVariantProduct')}">
				<c:set var="variantStyles" value="${product.baseOptions[0].options}" />
				<c:set var="variantSizes" value="${product.variantOptions}" />
				<c:set var="currentStyleUrl" value="${product.url}" />
			</c:if>
			<c:if
				test="${(not empty product.baseOptions[1].options) and (product.baseOptions[0].variantType eq 'ApparelSizeVariantProduct')}">
				<c:set var="variantStyles" value="${product.baseOptions[1].options}" />
				<c:set var="variantSizes" value="${product.baseOptions[0].options}" />
				<c:set var="currentStyleUrl"
					value="${product.baseOptions[1].selected.url}" />
			</c:if>
			
			<c:url value="${currentStyleUrl}" var="currentStyledProductUrl" />
			<%-- Determine if product is other variant --%>
			<c:if test="${empty variantStyles}">
				<c:if test="${not empty product.variantOptions}">
					<c:set var="variantOptions" value="${product.variantOptions}" />
				</c:if>
				<c:if test="${not empty product.baseOptions[0].options}">
					<c:set var="variantOptions"
						value="${product.baseOptions[0].options}" />
				</c:if>
			</c:if>

			<c:if test="${not empty variantStyles or not empty variantSizes}">
				<c:choose>
					<c:when
						test="${product.purchasable and product.stock.stockLevelStatus.code ne 'outOfStock' }">
						<c:set var="showAddToCart" value="${true}" />
					</c:when>
					<c:otherwise>
						<c:set var="showAddToCart" value="${false}" />
					</c:otherwise>
				</c:choose>
				<%-- <div class="col-sm-3">
				<c:if test="${not empty variantStyles}">
							 <form class="form-horizontal">
								<div class="form-group">
									<label class="control-label DINWebBold prodQtyLabel " for="qty">Color</label>
									<div
										class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block btmMrgn_5 width118">
										<select id="apparelColor" class="form-control">
												<c:forEach items="${variantStyles}" var="variantStyle">
													<c:forEach items="${variantStyle.variantOptionQualifiers}"
														var="variantOptionQualifier">
														<c:if test="${variantOptionQualifier.qualifier eq 'style'}">
															<c:set var="styleValue"
																value="${variantOptionQualifier.value}" />
															<c:set var="imageData"
																value="${variantOptionQualifier.image}" />
														</c:if>
													</c:forEach>
													<c:url value="${variantStyle.url}" var="productStyleUrl" />
													<c:set var="productStyleVarientUrl" value="${fn:replace(productStyleUrl,'/p/', '/lp/')}" />
													<option ${variantStyle.url eq currentStyleUrl ? 'selected' : ''} value="${productStyleVarientUrl}" >${styleValue}</option>
												</c:forEach>
										</select>
									</div>
								</div>
							</form> 
				</c:if>
			</div>	 --%>
			<div class="col-lg-2"></div>
				<div class="col-sm-3 hideAddedtoCart  text-right">
					<c:if test="${not empty variantSizes}">
						<form class="form-horizontal">
							<div class="form-group">
								<!-- 	<div class=""> -->
								<label for="qty"
									class="control-label DINWebBold prodQtyLabel rghtMrgn_12">Size</label>
								<div
									class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block btmMrgn_5 width118 ">
									<select class="form-control" id="apparelSize">
										<c:if test="${empty variantSizes}">
											<!-- <option selected="selected">Select Size</option> -->
										</c:if>
										<c:if test="${not empty variantSizes}">
											 <option value="select" ${ isVariantSelected eq false ? 'selected="selected"' : ''}>Select Size</option> 
											
											<c:forEach items="${variantSizes}" var="variantSize">
												<c:set var="optionsString" value="" />
												<c:forEach items="${variantSize.variantOptionQualifiers}"
													var="variantOptionQualifier">
													<c:if test="${variantOptionQualifier.qualifier eq 'size'}">
														<c:set var="optionsString">${variantOptionQualifier.value}</c:set>
													</c:if>
												</c:forEach>

												<c:if
													test="${(variantSize.stock.stockLevel gt 0) and (variantSize.stock.stockLevelStatus ne 'outOfStock')}">
													<c:set var="stockLevel">${variantSize.stock.stockLevel}&nbsp;<spring:theme
															code="product.variants.in.stock" />
													</c:set>
												</c:if>
												<c:if
													test="${(variantSize.stock.stockLevel le 0) and (variantSize.stock.stockLevelStatus eq 'inStock')}">
													<c:set var="stockLevel">
														<spring:theme code="product.variants.available" />
													</c:set>
												</c:if>
												<c:if
													test="${(variantSize.stock.stockLevel le 0) and (variantSize.stock.stockLevelStatus ne 'inStock')}">
													<c:set var="stockLevel">
														<spring:theme code="product.variants.out.of.stock" />
													</c:set>
												</c:if>

												<c:if test="${(variantSize.url eq product.url)}">
													<c:set var="showAddToCart" value="${true}" />
												</c:if>

												<c:url value="${variantSize.url}" var="variantOptionUrl" />
												<c:set var="productSizeVarientUrl"
													value="${fn:replace(variantOptionUrl,'/p/', '/lp/')}" />
												<option value="${productSizeVarientUrl}"
													${(variantSize.url eq product.url and isVariantSelected eq true) ? 'selected="selected"' : ''}>
													<c:choose>
														<c:when test="${optionsString eq 'XXL'}">2XL</c:when>
														<c:when test="${optionsString eq 'XXXL'}">3XL</c:when>
														<c:otherwise>${optionsString}</c:otherwise>
													</c:choose>
												</option>
											</c:forEach>
										</c:if>
									</select>
								</div>
								<!-- </div> -->
								<a class="text-capitalize addNewAddLink viewSizeLink rghtMrgn_-68"
									onclick="$('.sizeChart').show();$('.hideSizeLink').show();$('.viewSizeLink').hide();">View
									size chart <span class="linkarow fa fa-angle-right "></span>
								</a> <a style="display: none"
									class="text-capitalize addNewAddLink hideSizeLink rghtMrgn_-68"
									onclick="$('.sizeChart').hide();$('.hideSizeLink').hide();$('.viewSizeLink').show();">Hide
									size chart <span class="linkarow fa fa-angle-right "></span>
								</a>
							</div>

						</form>
					</c:if>
					
				</div>

			</c:if>
			
				<c:if test="${empty variantStyles or  empty variantSizes}">
				<div class="col-lg-2"></div>
				<div class="col-sm-3 hideAddedtoCart  text-right">
				</div>
				</c:if>
			<c:if test="${not empty variantOptions}">
				<div class="variant_options">
					<div class="size">
						<select id="variant">
							<option selected="selected" disabled="disabled"><spring:theme
									code="product.variants.select.variant" /></option>
							<c:forEach items="${variantOptions}" var="variantOption">
								<c:set var="optionsString" value="" />
								<c:forEach items="${variantOption.variantOptionQualifiers}"
									var="variantOptionQualifier">
									<c:set var="optionsString">${optionsString}&nbsp;${variantOptionQualifier.name}&nbsp;${variantOptionQualifier.value}, </c:set>
								</c:forEach>

								<c:if
									test="${(variantOption.stock.stockLevel gt 0) and (variantSize.stock.stockLevelStatus ne 'outOfStock')}">
									<c:set var="stockLevel">${variantOption.stock.stockLevel} <spring:theme
											code="product.variants.in.stock" />
									</c:set>
								</c:if>
								<c:if
									test="${(variantOption.stock.stockLevel le 0) and (variantSize.stock.stockLevelStatus eq 'inStock')}">
									<c:set var="stockLevel">
										<spring:theme code="product.variants.available" />
									</c:set>
								</c:if>
								<c:if
									test="${(variantOption.stock.stockLevel le 0) and (variantSize.stock.stockLevelStatus ne 'inStock')}">
									<c:set var="stockLevel">
										<spring:theme code="product.variants.out.of.stock" />
									</c:set>
								</c:if>

								<c:choose>
									<c:when
										test="${product.purchasable and product.stock.stockLevelStatus.code ne 'outOfStock' }">
										<c:set var="showAddToCart" value="${true}" />
									</c:when>
									<c:otherwise>
										<c:set var="showAddToCart" value="${false}" />
									</c:otherwise>
								</c:choose>

								<c:url value="${variantOption.url}" var="variantOptionUrl" />
								<option value="${variantOptionUrl}"
									${(variantOption.url eq product.url) ? 'selected="selected"' : ''}>
									${optionsString}&nbsp;
									<format:price priceData="${variantOption.priceData}" />&nbsp;&nbsp;${variantOption.stock.stockLevel}
								</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</c:if>

			<div class="col-sm-3 hideAddedtoCart ">

				<form class="form-horizontal ">
			<!-- <div class="loayltycart-right">  -->
					<div class="form-group text-right">
						
							<label for="qty" class="control-label DINWebBold prodQtyLabel lftMrgn_106">Quantity</label>
							<input type="text" maxlength="5" size="1" id="qtyInput"
								name="qtyInput" class="form-control width58 prod-detail-pull-right text-center" value="1"
								width="30%" onkeypress="return validateNumber(event,this)">
						</div>
					<!-- </div> -->
				</form>
			</div>
			<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

			<fmt:parseNumber var="productLoyaltyPoints"
				value="${product.loyaltyPoints}" type="number" />
			<fmt:parseNumber var="userTotalLoyaltyPoints"
				value="${userLoyaltyPoints}" type="number" />

			<div class="col-lg-4 myRewardProDetailAddToCart text-right">
				<c:choose>
					<c:when test="${productLoyaltyPoints le userTotalLoyaltyPoints}">
						<form id="AddToCartForm" class="form-horizontal"
							action="<c:url value="/loyaltycart/add"/>" method="POST">
							<c:set var="buttonType">submit</c:set>
							<input type="hidden" maxlength="5" size="1" id="qty" name="qty"
								class="qty" value="1" /> <input type="hidden"
								name="productCodePost" value="${product.code}" />
							<div class="form-group topMargn_25">
								
							 	<div class="loayltycart-right">
									<c:choose>
										<c:when test="${showAddToCart and isVariantSelected eq true}">
											<button type="${buttonType}"
												class="btn btn-fmDefault prodDetAddtoCart prodDetailAddToCart">ADD TO
												CART</button>
										</c:when>
										<c:when test="${empty showAddToCart and isVariantSelected eq false}">
											<button type="${buttonType}"
												class="btn btn-fmDefault prodDetAddtoCart prodDetailAddToCart">ADD TO
												CART</button>
										</c:when>
										<c:otherwise>

											<button type="${buttonType}" disabled="disabled"
												class="btn btn-fmDefault prodDetAddtoCart prodDetailAddToCart">ADD TO
												CART</button>
										</c:otherwise>
									</c:choose>
								</div>
							</div> 
						</form>
					</c:when>
					<c:otherwise>
						<div class="form-group topMargn_25"> 
							<button type="${buttonType}" disabled="disabled"
								class="btn btn-fmDefault prodDetAddtoCart prodDetailAddToCart">ADD TO CART</button>
							<form role="form" class="form-horizontal rewardsPanelForm"
								id="rewardsform">
								<div class="controls clearfix">
									<a class="btn  btn-sm btn-fmDefault pull-right text-uppercase"
										href="loyalty/earn" id="btn-login">Earn More Points</a>
								</div>
							</form>
						</div>
					</c:otherwise>
				</c:choose>

			</div>

		</c:otherwise>
	</c:choose>
</div>