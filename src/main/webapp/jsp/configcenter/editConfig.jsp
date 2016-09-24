<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html lang="zh-cn">
<head>
   <%@ include file="/inc/inc.jsp"%>
<title>配置中心</title>
<script type="text/javascript">
var pager;
(function () {
	seajs.use('app/jsp/configcenter/editConfig', function (EditConfigPager) {
		pager = new EditConfigPager({element: document.body});
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
          <!--查询区域-->
               <div class="nav-form">
                 <ul>
                 <li class="btn-margin center-btn" >
                 <p><A href="javascript:void(0)"><input type="button" value="新  增" id="addId" class="bass-btn newly-form-btn"></A></p>
                 <p><A href="javascript:void(0)"><input type="button" id="deleteId" value="删  除" class="bass-btn newly-form-btn"></A></p>
                 <p><A href="javascript:void(0)"><input type="button" value="导  入" id="uploadId" class="bass-btn newly-form-btn"></A></p>
                 <p><A href="javascript:void(0)"><input type="button" value="导  出" id="exportId" class="bass-btn newly-form-btn"></A></p>
                 </li>
                 </ul>
            
                </div><!--查询区域结束-->
           
           <div class="nav-tplist"><!--查询结果-->
           
            <div class="nav-tplist-title"><!--查询结果标题-->
            <div class="title-left">路径：/${param.name}
            	<input type="text"  id="Allpath" value=""/>
            	<input type="hidden"  id="rowPath" value=""/>
            </div>
            </div><!--查询结果标题结束-->
            
           <div class="nav-tplist-table" id="query"><!--查询结果列表-->
    
		     <table width="100%" border="0">
		          <tr class="tr-backgrond">                                                                                                            
		            <td width="10%"class="left-border"><input type="checkbox"  class="int-checkbox1"  id="checkAll"> 全选</td>                                                                                               
		            <td width="25%">配置路径</td>
		            <td width="50%">配置值
		            	<input type="hidden" name="appName" id="appName" value="${requestScope.appName}"/>
		            	<input type="hidden"  id="path" value=""/>
		            	<input type="hidden"  id="flag" />
		            	<input type="hidden" id="pathValue">
		            </td>
		            <td width="15%" class="right-border">操作</td>
		          </tr>
		          <tbody id="configData" style="word-break:break-all"></tbody>
		          <!-- 定义JsRender模版 -->
					<script id="configDataTmpl" type="text/x-jsrender">
					<tr>
						<td width="10%" class="left-border"><input type="checkbox" name="configId"  value="{{:path}}" class="int-checkbox"></td>
		   				<td width="25%" id="search_sub"><a href="javascript:void(0);" id="search" onclick="fun(this)">{{:path}}</a></td>
		   				<td width="50%">{{:value}}</td>
						<td width="15%">
						<a href="javascript:void(0)"  onclick="pager._editPage('{{:path}}')">编辑</a>
						&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="pager._exportOne('{{:path}}')">导出</a>
						</td>
					</tr>
		    		</script>
		 		</table>
 			</div>
       </div>
       
         <div class="peiz-wap" style="display:none" id="add"><!--新增-->
         <div class="nav-form nav-form-border"  name="DIV_REQ_PARAM_SETTING">
                 <ul>
	                  <li>
		                 <p class="word">节点名称:</p>
		                 <p><input type="text" id="paramName" class="int-medium" value=""></p>
	                 </li>
                 </ul>
                 <ul>
	                 <li>
		                 <p class="word">节点值</p>
		                 <p ><div id="REQ_JSONEDITOR" class="json-editor" style=" width:680px; height:300px; border:1px solid #e9e9e9; overflow:auto;  overflow-x:auto;" paramjson="">
		                 </div></p>
	                 </li>
                 </ul>
          </div>
        <div class="configure-btn-ctn pad130">   
         <div class="configure-btn-ctn-left">
           <ul>
           <li>
           	<input type="button" class="bass-btn peiz-btn" id="submitId" value="确  定">
            <input type="hidden" id="editOradd">
           </li>
           <li><input type="button" class="peiz-btn-cancel" id="cancelId" value="退出编辑"></li>
           </ul>
          </div>
         
         </div>
         </div>
         </div><!--新增结束-->
         
         <div class="peiz-wap" style="display:none" id="importDiv"><!--导入-->
         <div class="nav-form nav-form-border">
                 <ul>
	                  <li>
	                 <p class="word">注意事项</p>
	                 <p>只能上传以.properites结尾的文件</p>
	                 </li>
                 </ul>
                 <ul>
	                 <li>
	                 <p class="word">文件名称</p>
	                 <p><input type="text" class="int-medium" id="allfileName"></p>
	                 </li>
	                 <li class="btn-margin">
	                 	<p>
	                 	<input type="button" value="浏览" class="bass-btn nav-form-btn" id="viewFileId">
	                 	<input type="file" class="file" accept=".properties" id="fileName" onchange="getFileName()">
	                 	</p>
	                 </li>
	             </ul>
        </div>
        <div class="configure-btn-ctn pad130">   
         <div class="configure-btn-ctn-left">
           <ul>
           <li><input type="button" class="bass-btn peiz-btn" id="importId" value="上传配置"></li>
           <li><input type="button" class="peiz-btn-cancel" id = "cancelImpor"value="取  消"></li>
           </ul>
          </div>
         </div>
         </div><!--导入结束-->
         
         
    </div> 
  </div>
  <div class="footer">©2016 版权所有 亚信集团股份有限公司 京ICP备11005544号-15 京公网安备110108007119号</div>
<script type="text/javascript">
function getFileName(){
	var name = $("#fileName").val();
		$("#allfileName").val(name);
}
function fun(data){
     var path = data.innerHTML;
     $("#rowPath").val(path);
}
</script>
</body>
</html>
