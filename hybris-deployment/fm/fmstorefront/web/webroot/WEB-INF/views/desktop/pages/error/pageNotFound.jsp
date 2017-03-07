
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<html>
<body onload="javascript:loadErrorPage();"></body>

</html>

<script type="text/javascript">


var Url1 ="";

function loadErrorPage(){
        

ajaxUrl="USD/fm-GlobalError/";
currentPath= window.location.href;
pathName= "";

                try{
                        if(currentPath.indexOf("/USD") != -1){
                                pathName = currentPath.substring(0, currentPath.lastIndexOf("/USD") + 1);
                        }else if(currentPath.indexOf("?site") != -1){
                                pathName = currentPath.substring(0, currentPath.lastIndexOf("/?site") + 1)+currentPath.substring(currentPath.lastIndexOf("site=")+5,currentPath.length)+"/en/";
                        }else{
                                pathName = window.location.href;
                        }
                }catch(e){
                        alert(e);
                }

           Url1  = pathName + ajaxUrl+"ErrorPage";
           document.location.href= Url1;

}


           
       
</script>


