<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html lang="zh-cn">
<head>
   <%@ include file="/inc/inc.jsp"%>
<title>配置中心</title>
<script type="text/javascript">
var pager;
(function () {
	seajs.use('app/jsp/configcenter/queryConfig', function (QueryConfigPager) {
		pager = new QueryConfigPager({element: document.body});
		pager.render();
	});
})();
</script>
</head>

<body>

<!--头部和菜单-->
<%@ include file="/inc/head.jsp"%>
<!--头部和菜单结束-->
<div class="wrapper">     
<div class="management"><!--资费管理外侧-->
           

         
          <div class="management-cnt"><!--资费管理内容-->
          
           <div class="nav-tplist"><!--查询结果-->
           
            <div class="nav-tplist-title"><!--查询结果标题-->
            <!-- <div class="title-left">路径：/baas/opt </div> -->
            </div><!--查询结果标题结束-->
            
           <div class="nav-tplist-table"><!--查询结果列表-->
    
		     <table width="100%" border="0">
		          <tr class="tr-backgrond">                                                                                                            
		            <td>工程名称</td>
		            <td>工程编码</td>
		            <td>配置中心编码</td>
		            <td class="right-border">操作</td>
		          </tr>
		          <tr>
		            <td>统一运营、单点登录、基础公共</td>
		            <td id="project_name">general-mgmt-portal、general-uac-web、general-common</td>
		            <td id="ccs_name">aiopt-aiplatform</td>
		            <td class="right-border"><a href="javascript:void(0);" id="config" onclick="pager._search('aiopt-aiplatform');">配置</a></td>
		          </tr>
		          <tr>
		            <td>商户后场、商户Web</td>
		            <td id="project_name">ch-user、ch-user-web</td>
		            <td id="ccs_name">aiopt-ch-user</td>
		            <td class="right-border"><a href="javascript:void(0);" id="config" onclick="pager._search('aiopt-ch-user');">配置</a></td>
		          </tr>
		          <tr>
		            <td>订单后场、订单web</td>
		            <td id="project_name">slp-order、ch-order-web</td>
		            <td id="ccs_name">aiopt-ch-order</td>
		            <td class="right-border"><a href="javascript:void(0);" id="config" onclick="pager._search('aiopt-ch-order');">配置</a></td>
		          </tr>
		          <tr>
		            <td>商品后场</td>
		            <td id="project_name">slp-product</td>
		            <td id="ccs_name">aiopt-ch-product</td>
		            <td class="right-border"><a href="javascript:void(0);" id="config" onclick="pager._search('aiopt-ch-product');">配置</a></td>
		          </tr>
		          <tr>
		            <td>商品web</td>
		            <td id="project_name">slp-product-web</td>
		            <td id="ccs_name">aiopt-ch-product-web</td>
		            <td class="right-border"><a href="javascript:void(0);" id="config" onclick="pager._search('aiopt-ch-product-web');">配置</a></td>
		          </tr>
		          <tr>
		            <td>仓库后场</td>
		            <td id="project_name">slp-route</td>
		            <td id="ccs_name">aiopt-ch-route</td>
		            <td class="right-border"><a href="javascript:void(0);" id="config" onclick="pager._search('aiopt-ch-route');">配置</a></td>
		          </tr>
		          <tr>
		            <td>仓库web</td>
		            <td id="project_name">ch-route-web</td>
		            <td id="ccs_name">aiopt-ch-route-web</td>
		            <td class="right-border"><a href="javascript:void(0);" id="config" onclick="pager._search('aiopt-ch-route-web');">配置</a></td>
		          </tr>
		          
		 	</table>

 		</div>      
</div>
</div> 
  </div>
 </div>
  <div class="footer">©2016 版权所有 亚信集团股份有限公司 京ICP备11005544号-15 京公网安备110108007119号</div>
</body>
</html>
