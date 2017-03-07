<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:set var="jspPage" value="${fn:split(request.requestURI, '/')[fn:length(fn:split(request.requestURI, '/'))-1]}" />


${gtmecommerce}

<c:if test="${not empty gtmContainerId}" >
    <!-- Google Tag Manager -->
    <noscript><iframe src="//www.googletagmanager.com/ns.html?id=${gtmContainerId}"
    height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
    <script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
    new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
    j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
    '//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
    })(window,document,'script','dataLayer','${gtmContainerId}');</script>
    <!-- End Google Tag Manager -->
</c:if>



<script>


<c:choose>
    <c:when test="${pageType == 'PRODUCT' && gtmecommerce!='legacy'}">
        window.dataLayer = window.dataLayer || [];
        <c:set var="categories" value="" />
        
        <c:forEach items="${product.categories}" var="category">
            //category ${category.name}
            <c:set var="categories">${categories}; ${category.name}</c:set>
        </c:forEach>
        //This is shown on product pages
        dataLayer.push({
            'event':'product-detail',
            'ecommerce':{
                'detail':{
                    'actionField':{},
                    'products':[{
                        'name':'${product.name}',
                        'price': Number(${product.price.value}),
                        'category':'${fn:substringAfter(categories, '; ')}',
                        'id':'${product.code}',
                        'variant':'${product.variantType}',
                        'brand':'${product.brands.corpname}'
                    }]
                }
            }
        });
    </c:when>

    <c:when test="${pageType == 'CATEGORY' && gtmecommerce!='legacy'}">
        window.dataLayer = window.dataLayer || [];
        //Enhanced ecommerce

        <c:set var="lastBreadcrumb" value="${breadcrumbs[fn:length(breadcrumbs)-1]}" />

        dataLayer.push({
            'event':'product-impressions',
            'list':'${lastBreadcrumb.name}',
            'ecommerce':{
                'impressions':[
                    <c:forEach items="${searchPageData.results}" var="product" varStatus="status">
                    {
                        'name':'${product.name}',
                        'price':Number(${product.price.value}),
                        'list':'${lastBreadcrumb.name}',
                        'position':${status.count},
                        'id':'${product.code}',
                        'brand':'${product.brands.corpname}',
                    },
                    </c:forEach>
                ]
            }
        });
    </c:when>

    
    <c:when test="${pageType == 'PRODUCTSEARCH'}">
        //number of search results: ${searchPageData.pagination.totalNumberOfResults} 
        window.dataLayer = window.dataLayer || [];
        dataLayer.push({
                'event':'search-results',
                'results':'${searchPageData.pagination.totalNumberOfResults}',
                'search-term':'${searchPageData.freeTextSearch}'
        });

        <c:if test="${gtmecommerce!='legacy'}">
            dataLayer.push({
                'event':'product-impressions',
                'list':'Search Results',
                'ecommerce':{
                    'impressions':[
                        <c:forEach items="${searchPageData.results}" var="product" varStatus="status">
                        {
                            'name':'${product.name}',
                            'price': Number(${product.price.value}),
                            'list':'Search Results',
                            'position':${status.count},
                            'id':'${product.code}',
                            'brand':'${fn:substringBefore(entry.product.name, ' ')}',
                        },
                        </c:forEach>
                    ]

                }
            });
        </c:if>
    </c:when>

    <c:when test="${pageType == 'CART' && jspPage =='fmCartPage.jsp' && gtmecommerce!='legacy'}">
        window.dataLayer = window.dataLayer || [];
        dataLayer.push({
            'event':'checkout-step',
            'ecommerce':{
                'checkout':{
                    'actionField':{'step':1},
                    'products':[
                        <c:forEach items="${cartData.entries}" var="entry">
                        {
                            'name':'${entry.product.name}',
                            'price':Number(${entry.basePrice.value}),

                            'id':'${entry.product.code}',
                            'variant':'${entry.product.variantType}',
                            'brand':'${fn:substringBefore(entry.product.name, ' ')}',
                            'quantity':${entry.quantity}
                        },
                        </c:forEach>
                    ]
                }
            }
        });

    </c:when>

 <c:when test="${pageType == 'CART' && jspPage =='fmGATPPage.jsp' && gtmecommerce!='legacy'}">
        window.dataLayer = window.dataLayer || [];
        dataLayer.push({
            'event':'checkout-step',
            'ecommerce':{
                'checkout':{
                    'actionField':{'step':2},
                    'products':[
                        <c:forEach items="${cartData.entries}" var="entry">
                        {
                            'name':'${entry.product.name}',
                            'price':Number(${entry.basePrice.value}),

                            'id':'${entry.product.code}',
                            'variant':'${entry.product.variantType}',
                            'brand':'${fn:substringBefore(entry.product.name, ' ')}',
                            'quantity':${entry.quantity}
                        },
                        </c:forEach>
                    ]
                }
            }
        });

    </c:when>

 <c:when test="${jspPage == 'checkoutMultiDeliveryMethod.jsp' && gtmecommerce!='legacy'}">
        window.dataLayer = window.dataLayer || [];
        dataLayer.push({
            'event':'checkout-step',
            'ecommerce':{
                'checkout':{
                    'actionField':{'step':3},
                    'products':[
                        <c:forEach items="${cartData.entries}" var="entry">
                        {
                            'name':'${entry.product.name}',
                            'price':Number(${entry.basePrice.value}),

                            'id':'${entry.product.code}',
                            'variant':'${entry.product.variantType}',
                            'brand':'${fn:substringBefore(entry.product.name, ' ')}',
                            'quantity':${entry.quantity}
                        },
                        </c:forEach>
                    ]
                }
            }
        });

    </c:when>

 <c:when test="${jspPage == 'checkoutMultiReviewPlaceOrder.jsp' && gtmecommerce!='legacy'}">
        window.dataLayer = window.dataLayer || [];
        dataLayer.push({
            'event':'checkout-step',
            'ecommerce':{
                'checkout':{
                    'actionField':{'step':4},
                    'products':[
                        <c:forEach items="${cartData.entries}" var="entry">
                        {
                            'name':'${entry.product.name}',
                            'price':Number(${entry.basePrice.value}),

                            'id':'${entry.product.code}',
                            'variant':'${entry.product.variantType}',
                            'brand':'${fn:substringBefore(entry.product.name, ' ')}',
                            'quantity':${entry.quantity}
                        },
                        </c:forEach>
                    ]
                }
            }
        });

    </c:when>
    <c:when test="${pageType == 'ORDERCONFIRMATION'}">
        <c:choose>
            <c:when test="${gtmecommerce == legacy}">
                //Legacy e-commerce transaction push
               dataLayer.push({
                    'event':'transaction',
                    'transactionPostalCode':'${orderData.deliveryAddress.postalCode}',
                    'transactionCountryName':'${orderData.deliveryAddress.country.name}',
                    'ecommerce':{
                        'purchase':{
                            'actionField':{
                                'id':'${orderData.code}',
                                'affiliation': '${siteName}',
                                'revenue':Number(${orderData.totalPrice.value}),
                                'tax':Number(${orderData.totalTax.value}),
                                'shipping':Number(${orderData.deliveryCost.value}),
                            },

                            'products': [
                                <c:forEach items="${orderData.entries}" var="entry">
                                {
                                    'id': '${entry.product.code}',
                                    'name': '${entry.product.name}',
                                     <c:choose>
                                        <c:when test="${not empty entry.product.categories}">
                                            'category': '${entry.product.categories[fn:length(entry.product.categories) - 1].name}',
                                        </c:when>
                                        <c:otherwise>
                                            'category':'',
                                        </c:otherwise>
                                    </c:choose>
                                    'price': Number(${entry.basePrice.value} ),
                                    'brand': '${fn:substringBefore(entry.product.name, ' ')}',
                                    'variant':'${entry.product.variantType}',
                                    'quantity':${entry.quantity}
                                },
                                </c:forEach>
                            ]
                        }
                    }
                });
dataLayer.push({
            'event':'checkout-step',
            'ecommerce':{
                'checkout':{
                    'actionField':{'step':5},
                    'products':[
                        <c:forEach items="${cartData.entries}" var="entry">
                        {
                            'name':'${entry.product.name}',
                            'price':Number(${entry.basePrice.value}),

                            'id':'${entry.product.code}',
                            'variant':'${entry.product.variantType}',
                            'brand':'${fn:substringBefore(entry.product.name, ' ')}',
                            'quantity':${entry.quantity}
                        },
                        </c:forEach>
                    ]
                }
            }
        });

 dataLayer.push({
                    'event':'transaction-legacy',
                  		    'transactionPostalCode':'${orderData.deliveryAddress.postalCode}',
                    'transactionCountryName':'${orderData.deliveryAddress.country.name}',
	            'transactionId': '${orderData.code}',
                    'transactionAffiliation': '${siteName}',
                    'transactionTotal': Number(${orderData.totalPrice.value}),
                    'transactionTax': Number(${orderData.totalTax.value}),
                    'transactionShipping': Number(${orderData.deliveryCost.value}),
                    'transactionProducts': [
                        <c:forEach items="${orderData.entries}" var="entry">
                        {
                            'sku': '${entry.product.code}',
                            'name': '${entry.product.name}',
                            'brand': '${fn:substringBefore(entry.product.name, ' ')}',
                                                      
                            <c:choose>
                                <c:when test="${not empty entry.product.categories}">
                                    'category': '${entry.product.categories[fn:length(entry.product.categories) - 1].name}',
                                </c:when>
                                <c:otherwise>
                                    'category':'',
                                </c:otherwise>
                            </c:choose>
                            'price': Number(${entry.basePrice.value} ),
                            'quantity': ${entry.quantity},
                          
			   
			    },
                        </c:forEach>
                    ]
                });
            </c:when>
            <c:otherwise>
                dataLayer.push({
                    'event':'transaction',
                    'transactionPostalCode':'${orderData.deliveryAddress.postalCode}',
                    'transactionCountryName':'${orderData.deliveryAddress.country.name}',
                    'ecommerce':{
                        'purchase':{
                            'actionField':{
                                'id':'${orderData.code}',
                                'affiliation': '${siteName}',
                                'revenue':Number(${orderData.totalPrice.value}),
                                'tax':Number(${orderData.totalTax.value}),
                                'shipping':Number(${orderData.deliveryCost.value}),
                            },

                            'products': [
                                <c:forEach items="${orderData.entries}" var="entry">
                                {
                                    'id': '${entry.product.code}',
                                    'name': '${entry.product.name}',
                                     <c:choose>
                                        <c:when test="${not empty entry.product.categories}">
                                            'category': '${entry.product.categories[fn:length(entry.product.categories) - 1].name}',
                                        </c:when>
                                        <c:otherwise>
                                            'category':'',
                                        </c:otherwise>
                                    </c:choose>

                                    'price': Number(${entry.product.price.value}),
                                    'brand': '${entry.product.manufacturer}',
                                    'variant':'${entry.product.variantType}',
                                    'quantity':${entry.quantity}
                                },
                                </c:forEach>
                            ]
                        }
                    }
                });
            </c:otherwise>
        </c:choose>

        

    </c:when>

    <c:otherwise>
        //DataLayer: No specific Page type page
    </c:otherwise>
</c:choose>


<c:forEach items="${gtmcheckoutStepsList}" var="checkoutStep"  varStatus="status">
${checkoutStep};
    <c:if test="${jspPage == checkoutStep && gtmecommerce=='legacy'}">
        window.dataLayer = window.dataLayer || [];
        dataLayer.push({
            'event':'checkout-step',
            'ecommerce':{
                'checkout':{
                    'actionField':{'step':${status.count}+1},
                    'products':[
                        <c:forEach items="${cartData.entries}" var="entry">
                        {
                            'name':'${entry.product.name}',
                            'price': Number(${entry.basePrice.value}),
                            'id':'${entry.product.code}',
                            'variant':'${entry.product.variantType}',
                            'brand':'${entry.product.manufacturer}',
                            'quantity':${entry.quantity}
                        },
                        </c:forEach>
                    ]
                }
            }
        });
    </c:if>
</c:forEach>

<c:if test="${gtmecommerce!='legacy'}">
    function trackAddToCart_GTM(productCode, quantityAdded, name, price) {
        <c:set var="lastBreadcrumb" value="${breadcrumbs[fn:length(breadcrumbs)-1]}" />

        window.dataLayer = window.dataLayer || [];
        dataLayer.push({
          'event': 'add-to-cart',
          'productId': productCode,
          'productName': name,
          'quantity':quantityAdded,
          'ecommerce': {
            'currencyCode': '${currentCurrency.isocode}',


            'add': { 
              <c:if test="${pageType == 'CATEGORY'}">
                'actionField': {'list': '${lastBreadcrumb.name}'},
              </c:if>
              <c:if test="${pageType == 'PRODUCTSEARCH'}">
                'actionField': {'list': 'Search Results'},
              </c:if>
              'products': [{                       
                'name':  name,
                'id': productCode,
                'price': price,
                'quantity': quantityAdded
               }]
            }
          }
        });
    }

    function trackRemoveFromCart_GTM(productCode, initialQuantity, name, price) {
        window.dataLayer = window.dataLayer || [];
        dataLayer.push({
          'event': 'remove-from-cart',
          'productId': productCode,
          'productName': name,
          'price':price,
          'quantity':initialQuantity,
          'ecommerce': {
            'currencyCode': '${currentCurrency.isocode}',
            'remove': {
               
              'products': [{                        
                'name':  name,
                'id': productCode,
                'price': Number(price),
                'quantity': initialQuantity
               }]
            }
          }
        });
    }


    function trackUpdateCart_GTM(productCode, initialQuantity, newQuantity, name, price) {
            
            if (initialQuantity < newQuantity) {
                trackAddToCart_GTM(productCode, newQuantity-initialQuantity, name, price);
            } 
            else if (initialQuantity > newQuantity){
                trackRemoveFromCart_GTM(productCode, initialQuantity-newQuantity, name, price);
            }

    }


    window.mediator.subscribe('trackAddToCart', function(data) {


        if (data.productCode && data.quantity)
        {
            trackAddToCart_GTM(data.productCode, data.quantity, data.productName, data.productPrice);
        }

    });

    window.mediator.subscribe('trackUpdateCart', function(data) {
        if (data.productCode && data.initialCartQuantity && data.newCartQuantity)
        {
            trackUpdateCart_GTM(data.productCode, data.initialCartQuantity, data.newCartQuantity, data.productName, data.productPrice);
        }
    });

    window.mediator.subscribe('trackRemoveFromCart', function(data) {
        if (data.productCode && data.initialCartQuantity)
        {
            trackRemoveFromCart_GTM(data.productCode, data.initialCartQuantity,  data.productName, data.productPrice);
        }
    });
</c:if>
</script>

