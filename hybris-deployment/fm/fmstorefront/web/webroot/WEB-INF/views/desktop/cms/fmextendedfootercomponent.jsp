<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
 
 
 
<c:if test="${fmComponentName == 'extendedfooterLinks'}">
       <div class="footer-copyright-text">
          <img class="footer-logo" alt="Federal-Mogul Motorparts"
        src="${contextPath}/_ui/desktop/common/images/fmmp-footer-logo.png">
              <span class="footer-copyright">Copyright &copy; <script>document.write(new Date().getFullYear())</script> Federal-Mogul Motorparts</span>
              <span class="footer-extended-links">
                        
                <c:forEach items="${navigationNodes}" var="node">
                     <c:if test="${node.visible}">
                            <c:forEach items="${node.links}" step="${component.wrapAfter}"
                                   varStatus="i">
                                   <c:forEach items="${node.links}" var="childlink"
                                          begin="${i.index}" end="${i.index + component.wrapAfter - 1}">
                                         
                                          <a href="${childlink.url}">${childlink.linkName}</a>

                                   </c:forEach>
                            </c:forEach>
                     </c:if>
                </c:forEach>
              </span>
       </div>
</c:if>
 


