<%@page import="com.ai.baas.mt.web.constants.BaaSMTConstants"%>
<%@page import="com.ai.opt.sdk.components.ccs.CCSClientFactory"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	
	//String baas_pt_index_url = CCSClientFactory.getDefaultConfigClient().get(BaaSMTConstants.URLConstant.BAAS_PT_INDEX_URL_KEY);
	String baas_pt_index_url = "";
	request.setAttribute("baas_pt_index_url",baas_pt_index_url );
%>
<script type="text/javascript">
function setBreadCrumb(_first_breadcrumb_title,_second_breadcrumb_title){
	$("#first_breadcrumb_title").html(_first_breadcrumb_title);
	$("#second_breadcrumb_title").html(_second_breadcrumb_title);
}
</script>
<div class="header">
   <div class="logo">
    <a href="${baas_pt_index_url }">LOGO</a>
    <!-- <i class="icon-angle-down"></i>  -->
   </div>
   <div class="subnav">
   	<div class="nav">
      <ul>
		<li>
			<a href="javascript:void(0);">配置管理</a>
			<div class="current-cnt" style="display:none">
				<dl>
					<!-- <dt><a href="javascript:void(0);">基础服务</a></dt> -->
					<dt><a href="javascript:void(0);">配置管理</a></dt>
					<dd>
						<a href="${_base}/configCenter/toQueryConfig">配置中心</a>
						<%-- <a href="${_base}/config/basic/key">缓存中心</a> --%>
						
					</dd>				
				</dl> 
			</div>
		</li>		
      </ul>
   	  
   </div>
   </div>
   <div class="breadcrumb">
    <ul>
     <!-- 面包屑一级导航 -->
     <li><span id="first_breadcrumb_title"></span></li>
     <!-- 面包屑二级导航 -->
     <li><i class="icon-angle-right"></i><span id="second_breadcrumb_title"></span></li>
    </ul>
   </div>

</div>

