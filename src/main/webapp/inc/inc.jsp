<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String _base = request.getContextPath();
    request.setAttribute("_base", _base);
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    response.setHeader("Pragma", "No-cache");
%>
<script>
    var _base = "${_base}";
</script>

<script src="${_base}/resources/spm_modules/jquery/1.9.1/jquery.js"></script>
<script src="${_base}/resources/spm_modules/bootstrap/dist/js/bootstrap.js"></script>
<script src="${_base}/resources/spm_modules/seajs/2.3.0/dist/sea.js"></script>
<script src="${_base}/resources/spm_modules/seajs/seajs-css.js"></script>
<script src="${_base}/resources/spm_modules/app/core/config.js"></script>

<link rel="stylesheet" type="text/css" href="${_base}/resources/baasmt/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${_base}/resources/baasmt/css/font-awesome.css">
<link rel="stylesheet" type="text/css" href="${_base}/resources/baasmt/css/frame.css">
<link rel="stylesheet" type="text/css" href="${_base}/resources/baasmt/css/global.css">
<link rel="stylesheet" type="text/css" href="${_base}/resources/baasmt/css/modular.css">
<script type="text/javascript" src="${_base}/resources/baasmt/js/frame.js" ></script>
<script type="text/javascript" src="${_base}/resources/baasmt/js/comp.js" ></script>

