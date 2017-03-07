<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<div class= "content">
     <h3>FAQs</h3>     
     
     	<c:if test="${product.faqQestion1 ne null}">
     	<div class="productDetailFAQ">
       <h4><span class="productDetailQuestion">Q.</span>${product.faqQestion1}</h4>
       
       <c:if test="${product.faqAnswer1 ne null}">
       <p><span class="productDetailAnswer">A.</span>${product.faqAnswer1}</p>
       </c:if>
       </div>
       </c:if>
     
   
     <c:if test="${product.faqQestion2 ne null}">
       <div class="productDetailFAQ">
       <h4><span class="productDetailQuestion">Q.</span>${product.faqQestion2}</h4>
        
         <c:if test="${product.faqAnswer2 ne null}">
       <p><span class="productDetailAnswer">A.</span>${product.faqAnswer2}</p>
       </c:if>
        </div>
        </c:if>
    
     
       <c:if test="${product.faqQestion3 ne null}">
        <div class="productDetailFAQ">
       <h4><span class="productDetailQuestion">Q.</span>${product.faqQestion3}</h4>
       
        <c:if test="${product.faqAnswer3 ne null}">
       <p><span class="productDetailAnswer">A.</span>${product.faqAnswer3}</p>
       </c:if>
       </div>
       </c:if>
     
     
      <c:if test="${product.faqQestion4 ne null}">
       <div class="productDetailFAQ">
       <h4><span class="productDetailQuestion">Q.</span>${product.faqQestion4}</h4>
       
       <c:if test="${product.faqAnswer4 ne null}">
       <p><span class="productDetailAnswer">A.</span>${product.faqAnswer4}</p>
       </c:if>
            </div>
       </c:if>

     
      <c:if test="${product.faqQestion5 ne null}">
       <div class="productDetailFAQ">
       <h4><span class="productDetailQuestion">Q.</span>${product.faqQestion5}</h4>
       
       <c:if test="${product.faqAnswer5 ne null}">
       <p><span class="productDetailAnswer">A.</span>${product.faqAnswer5}</p>
       </c:if>
       </div>
       </c:if>
     
     
</div>