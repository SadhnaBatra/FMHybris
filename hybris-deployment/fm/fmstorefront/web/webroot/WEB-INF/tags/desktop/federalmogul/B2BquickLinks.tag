<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<c:url value="/my-fmaccount/order-history" var="orderStatus"/>

   <!--- Quick Links PANEL -->
        
            <div class="panel-body">
              <h3 class="panel-title text-uppercase"><strong class="text-uppercase">Quick links</strong></h3>
              <div class="quickLinksList">
                <ul>
                  <li><a href="${orderStatus}">Order Status <span class="quicLinkarow fa fa-angle-right "></span></a></li>
                  <li><a href="#">User Approval Dashboard <span class="quicLinkarow fa fa-angle-right"></span></a></li>
                  <li><a href="#">MP Monitor <span class="quicLinkarow fa fa-angle-right"></span></a></li>
                  <li><a href="#">Digital Assets <span class="quicLinkarow fa fa-angle-right"></span></a></li>
                  <li><a href="#">Order Promo (POP) Materials <span class="quicLinkarow fa fa-angle-right"></span></a></li>
                  <li><a href="#">Inventory Planning <span class="quicLinkarow fa fa-angle-right"></span></a></li>
                </ul>
              </div>
            </div>
         