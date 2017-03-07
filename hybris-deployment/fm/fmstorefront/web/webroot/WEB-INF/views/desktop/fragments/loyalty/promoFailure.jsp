<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

	<c:set var="blockId" value="${promotype}Block" />
	<c:set var="inputFieldId" value="${promotype}id" />
	<c:set var="buttonId" value="${promotype}Btn" />
	 
	<tr class="${promotype}" style=" " id="${blockId}">
	    <td colspan="4">
		    <h2 class="text-uppercase DINWebBold"><spring:theme code="text.EarnPoints.EnterPromoCode"/></h2>
		    <p class="topMargn_15"><spring:theme code="text.EarnPoints.PromoCodetext"/></p>
		    <div class="errorMsg fm_fntRed">
			    ${errortype}	
            </div>			
		    <div class="form-group col-lg-3 lftPad_0">
			    <input type="text" value="Promo Code" placeholder="Promo Code" class="form-control" id="${inputFieldId}">
		    </div>
		    <button type="button" id="${buttonId}" class="btn  btn-sm btn-fmDefault" onclick="javascript:submitLoyaltyPromocode('${promotype}')">
		        <spring:theme   code="text.EarnPoints.PromoCodeSubmit"/>
		    </button>
		</td>
	</tr>
											 