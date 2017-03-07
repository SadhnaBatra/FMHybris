<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<c:if test="${fmComponentName == 'onlineToolOverView'}">
	${component.content}
	${component.subContent}
</c:if>

<c:if test="${fmComponentName == 'Reward'}">
 	${component.content}
		${component.subContent}
</c:if>
<c:if test="${fmComponentName == 'FAQ'}">
  	${component.content}
		${component.subContent}
</c:if>

<c:if test="${fmComponentName == 'B2BquickLinks'}">
	${component.content}
	${component.subContent}
</c:if>

<c:if test="${fmComponentName == 'helpCenter'}">
	${component.content}
	${component.subContent}
</c:if>

<c:if test="${fmComponentName == 'FileDownloadtext'}">
	${component.content}
	${component.subContent}
</c:if>


<c:if test="${fmComponentName == 'Researchtext'}">
	${component.content}
	${component.subContent}
</c:if>

<c:if test="${fmComponentName == 'msgAnounceOfferParagraph'}">
	${component.content}
	
</c:if>


<c:if test="${fmComponentName == 'HelpCenterHeader'}">
	${component.content}
	${component.subContent}
</c:if>

<c:if test="${fmComponentName == 'learning'}">
	${component.content}  	

</c:if>
<c:if test="${fmComponentName == 'learningarticle'}">
	${component.content}  	

</c:if>
<c:if test="${fmComponentName == 'learningvideos1'}">
	${component.content}  	

</c:if>
<c:if test="${fmComponentName == 'learningvideos2'}">
	${component.content}  	

</c:if>
<c:if test="${fmComponentName == 'learnpara'}">
	${component.content}  	
    
</c:if>

<c:if test="${fmComponentName == 'b2ttechforum'}">
 	${component.content}
</c:if>


<c:if test="${fmComponentName == 'b2tquicklinks'}">
 	${component.content}
</c:if>
<c:if test="${fmComponentName == 'program'}">
	${component.content}  	
    
</c:if>
<c:if test="${fmComponentName == 'benefits'}">
	${component.content}  	
    
</c:if>

<c:if test="${fmComponentName == 'registrationloginblock'}">
	${component.content}  	
    
</c:if>