<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>



<script type="text/javascript" src="//s7.addthis.com/js/350/addthis_widget.js#pubid=xa-4f28754e346e1aeb"></script>
<script type="text/javascript"> 

	var addthis_config = { 
			"data_track_clickback": true,
			"data_track_addressbar": true, 
			"services_expanded":'facebook,twitter',
			"services_compact":'facebook,twitter',
			"ui_header_color": '#FFF',
		    "ui_header_background": '#060',}
	

	function eventHandler(evt) { 
		 
	    if (evt.type == 'addthis.menu.share') {  
	    	if(evt.data.service =='facebook' || evt.data.service =='twitter' ){
	    		var ajaxURL = ACC.ymmsearch.pathName + "USD/Survey/socialMedia";
	    		$.ajax({			
					type: "POST",
					url : ajaxURL,success : function(xmlDoc) {
						var respDoc = $(xmlDoc).find("success").html();
						return true;
					},
					error : function(e) {
					alert("Error = " + e);
					}
				});
	    	}
	    }
	   
	}

	addthis.addEventListener('addthis.menu.share', eventHandler);
	
</script>


<div class="addthis_toolbox addthis_default_style addthis_32x32_style">
<a class="addthis_button_compact pull-left" 
	title="<spring:theme code="Share"/>"><span
	class="fa fa-share-alt"></span> Share</a>
</div>




