define('app/jsp/configcenter/editConfig', function (require, exports, module) {
    'use strict';
    var $ = require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    AjaxController = require('opt-ajax/1.0.0/index');
    
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    
    require("twbs-pagination/jquery.twbsPagination.min");
    require("opt-paging/aiopt.pagination");
    require("jsoneditor/5.1.5/jsoneditor.min.css");
    require("jsoneditor/5.1.5/jsoneditor.min");
    
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    //定义页面组件类
    var EditConfigPager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
    		//查询
            "click #search":"_searchBtnClick",
            "click #deleteId":"_deleteConfig",
            "click #addId":"_changeData",
            "click #addId":"_getAddPage",
            "click #cancelId":"_cancel",
            "click #submitId":"_submit",
            "click #checkAll":"_checkall",
            //"click #editConfig":"_editPage",   
            "click #uploadId":"_uploadConfigPage",
            "click #importId":"_uploadConfig",
            "click #cancelImpor":"_cancel",
            "click #exportId":"_exportConfig",
        },
    	//重写父类
    	setup: function () {
    		EditConfigPager.superclass.setup.call(this);
    		this._initPage();
    		//初始化操作
    		this._searchBtnClick();
    		this._hidePage();
    	},   
    	_initPage: function(){
      		//导航
      		setBreadCrumb("配置管理","配置中心");
      		//左侧菜单选中样式
      		$("#mnu_config_mng").addClass("current");
      	},
      	//点击添加触发标识数值改变
      	_changeData: function(){
      		$("#editOradd").val("01");
      	},
    	_getAddPage: function(){
		 	var _this = this;
		 	//将节点输入框置为可写入的
		 	$("#paramName").attr("disabled",false);
    		$("#editOradd").val("01");
 			$("#query").attr("style","display:none");
 			$("#add").attr("style","display:");
 			this._initJSONEditors();
    	},
    	
    	_hidePage: function(){
    		 var _this = this;
    		 $("#query").attr("style","display:");
    		 $("#add").attr("style","display:none");
    	},
    	_checkall: function() {
			var chkflag=$("#checkAll").prop("checked");
			$('input[type=checkbox][name=configId]').prop("checked",chkflag);
         },
         _searchBtnClick: function(){
        	 	var rowPath = $('#rowPath').val();
				if(rowPath==""){
					$('#path').val("");
				}else{
					var path = $('#path').val()+"/"+rowPath;
					$('#path').val(path);
				}
				var flag = $('#flag').val();
				if(flag=="00"){
					
				}else{
					var pa = $('#path').val();
					if(pa!=""){
						var path = $('#path').val();
						$('#Allpath').val(path);
					}
				}
				
			ajaxController.ajax({
			        type: "post",
			        processing: false,
			        url: _base+"/configCenter/getList",
			        dataType: "json",
			        data: {    	            	
		            	appName:$('#appName').val(),//模板名称
		            	path:$('#Allpath').val()//模板名称
		            },
			        message: "正在加载数据..",
			        success: function (data) {
			        	if(data.data != null && data.data != 'undefined' && data.data.length>0){
		            		var template = $.templates("#configDataTmpl");
	    					var htmlOutput = template.render(data.data);
	    					$("#configData").html(htmlOutput);
		            	}else{
	    					$("#configData").html("没有搜索到相关信息");
		            	}
			        },
			        error: function(XMLHttpRequest, textStatus, errorThrown) {
						 alert(XMLHttpRequest.status);
						 alert(XMLHttpRequest.readyState);
						 alert(textStatus);
						   }
			    });
	   },
	   
    
        _initJSONEditors: function(){
            var options = {
                mode: 'text',
                modes: ['code', 'form', 'text', 'tree', 'view'],
                error: function (err) {
                    alert(err.toString());
                }
            };
            //输入参数编辑器
            var reqEditors = new Array();
            var flag = $("#editOradd").val();
            //如果是添加清空内容
            if(flag!="00"){
            	 $("#REQ_JSONEDITOR").empty();
            }
            $("[name='DIV_REQ_PARAM_SETTING']").each(function(index,div){
                var obj = $(div).find("#REQ_JSONEDITOR");
                if(!obj){
                    return;
                }
                var jsoneditorId = obj.attr("id");
                if(jsoneditorId==undefined){
                    return;
                }
              //数据库中保存的json字符串
                var s = $('#pathValue').val();
				var paramjson = obj.attr("paramjson");
				var container = document.getElementById(jsoneditorId);
				var editor = new JSONEditor(container, options, {});
				if(flag=="00"){
				    editor.set(s?JSON.parse(s):{});
					//editor.set(s);
				}else{
					$("#paramName").val("");
					editor.set(paramjson?JSON.parse(paramjson):{});
					//editor.set(paramjson);
				}
				reqEditors.push(editor);
            });
            this.reqEditors = reqEditors;

        },
      
        _cancel: function(){
        	var name = $('#appName').val();//模板名称
        	window.location.href=_base+"/configCenter/toEditConfig?name="+name;
        },
        _submit: function(){
        	
			var _this = this;
			var flag = $("#editOradd").val();
			if(flag=="00"){
				_this._editConfig();
			}else{
				try{
					$("#pathValue").val("");
					var param  = $("#paramName").val();
					if(param==""){
						var msgDialog = Dialog({
							title: '提示',
							content: "节点名称不能为空"
						});
		      			msgDialog.showModal();
						return false;
					}
					var jsonStr = "";
					$("[name='DIV_REQ_PARAM_SETTING']").each(function(index,div){
//							var editor = _this.getReqJSONEditor(index);
//							jsonStr =JSON.stringify(editor.get());
						jsonStr = $(".jsoneditor-text").val();
					});
					ajaxController.ajax({
						method : "POST",
						url : _base + "/configCenter/addConfig",
						dataType : "json",
						data: {
							data: jsonStr,
							appName:$('#appName').val(),//模板名称
			            	path:$('#Allpath').val()+"/"+$('#paramName').val()
						},
						showWait : true,
						message : "正在提交设置...",
						success : function(data) {
								if(data.data=="9999"){
									var msgDialog = Dialog({
										title: '提示',
										content: "节点已存在！"
									});
					      			msgDialog.showModal();
									return false;
								}else if(data.data=="000000"){
									var path = $('#Allpath').val();
									$('#path').val(path);
									$('#flag').val("00");
									_this._searchBtnClick();
									_this._hidePage();
								}
						}
					});
				}catch(err){
					var msgDialog = Dialog({
						title: '提示',
						content: "数据格式错误！"
					});
	      			msgDialog.showModal();
				}
				
			}
			
		},
		_editConfig: function(){
			
			try{
				
				var _this = this;
				var param  = $("#paramName").val();
				var jsonStr = "";
				$("[name='DIV_REQ_PARAM_SETTING']").each(function(index,div){
					//获取编辑框中的数据
						jsonStr = $(".jsoneditor-text").val();
				});
				//JSON.parse(jsonStr);
				ajaxController.ajax({
					method : "POST",
					url : _base + "/configCenter/editConfig",
					dataType : "json",
					data: {
						data: jsonStr,
						appName:$('#appName').val(),//模板名称
		            	path:$('#Allpath').val()+"/"+$('#paramName').val()
					},
					showWait : true,
					message : "正在提交设置...",
					success : function(data) {
						 if(data.data=="000000"){
							var path = $('#Allpath').val();
							$('#path').val(path);
							$('#flag').val("00");
							_this._searchBtnClick();
							_this._hidePage();
							
						}
					}
				});
			}catch(err){
				var msgDialog = Dialog({
					title: '提示',
					content: "数据格式错误！"
				});
      			msgDialog.showModal();
			}
		
		},
		_checkIsJson: function(obj){
			            var isjson = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length
			            return isjson;
		},
		getReqJSONEditor: function(index){
			var editors = this.reqEditors;
			var editor = editors[index];
			return editor;
		},
		
		//展示编辑页面
		_editPage: function(curPath){
			//将节点名称置为只读
			$("#paramName").attr("disabled",true);
			$('#pathValue').val("");
			var _this = this;
   		 	/*if ($("input[type=checkbox][name='configId']:checked").length != 1) {
                alert("选择一个配置进行操作");
                return false;
            }
            var pathes = "";
            $("input[type=checkbox][name='configId']:checked").each(function (index) {
           	 pathes = ($(this).val());
            });*/
            
            
            var pathes=curPath;
			ajaxController.ajax({
				method : "POST",
				url : _base + "/configCenter/queryEditConfig",
				dataType : "json",
				data: {
					path: pathes,
					appName:$('#appName').val(),//模板名称
	            	allPath:$('#Allpath').val()
				},
				showWait : true,
				message : "正在提交设置...",
				success : function(data) {
						if(data.data!=""&&data.data!=null){
							$("#paramName").val(data.data.path);
							$('#pathValue').val(data.data.value);
							$("#editOradd").val("00");
							$("#query").attr("style","display:none");
			 	    		$("#add").attr("style","display:");
			 	    		//清空编辑器
			 	    		$("#REQ_JSONEDITOR").empty();
			 	    		_this._initJSONEditors();
						}
				}
			});
		},
		//删除
    	_deleteConfig: function(){
    		var _this = this;
    		 if ($("input[type=checkbox][name='configId']:checked").length < 1) {
                 var msgDialog = Dialog({
						title: '提示',
						content: "至少选择一个配置进行操作！"
					});
	      			msgDialog.showModal();
                 return false;
             }
             var pathes = [];
             $("input[type=checkbox][name='configId']:checked").each(function (index) {
            	 pathes[index] = ($(this).val());
            	 
             });
    		new Dialog({
    	        title: '提示',
    	        content: '确定要删除配置?(删除之后，无法恢复)',
    	        width: '300px',
    	        height: '60px',
    	        //hasMask: false,  //没有遮罩
    	        okValue: '确定',
    	        ok: function () {
    	            this.title('提交中…');
    	            $.ajax({
        				type : "POST",
        				url :_base+"/configCenter/deleteConfig",
        				data: {
        					pathes : pathes,
        					path:$('#Allpath').val(),
        					appName:$('#appName').val()//模板名称
        				},
        				processing: true,
        				message : "正在处理中，请稍候...",
        				success : function(data) {
        					if(data.data=='000000'){
        						var path = $('#Allpath').val();
        						$('#path').val(path);
        						$('#flag').val("00");
        						_this._searchBtnClick();
        					}
        					_this._searchBtnClick();//刷新页面
        					var str = (data.data=='000000'?'删除成功':'删除失败');
        					Dialog({
        					    title: '提示',
        					    width: '200px',
        	        	        height: '50px',
        					    content: str
        					}).show();
        				}
        			});
    	        },
    	        cancelValue: '取消',
    	        cancel: function () {}
    	    }).show();
    	},
    	//导出全部
    	_exportConfig: function(){
    		new Dialog({
    	        title: '提示',
    	        content: '该导出功能导出该用户下所有配置信息',
    	        width: '300px',
    	        height: '60px',
    	        //hasMask: false,  //没有遮罩
    	        okValue: '确定',
    	        ok: function () {
    	            this.title('提交中…');
    	            window.location.href=_base+"/configCenter/downConfig?appName="+$('#appName').val();
    	        },
    	        cancelValue: '取消',
    	        cancel: function () {}
    	    }).show();
    	},
    	//导出单个
		_exportOne: function(curPath){
			var _this = this;
            var pathes=curPath;
			new Dialog({
    	        title: '提示',
    	        content: '确定导出？',
    	        width: '300px',
    	        height: '60px',
    	        //hasMask: false,  //没有遮罩
    	        okValue: '确定',
    	        ok: function () {
    	            this.title('提交中…');
    	            window.location.href=_base+"/configCenter/downOneConfig?appName="+$('#appName').val()+"&path=" +
    	            		pathes;
    	        },
    	        cancelValue: '取消',
    	        cancel: function () {}
    	    }).show();
		},
    	//导入页面
    	_uploadConfigPage: function(){
    		//将路径清空
    		$("#Allpath").val("");
    		$("#query").attr("style","display:none");
 			$("#add").attr("style","display:none");
 			$("#importDiv").attr("style","display:");
    	},
    	//导入数据
    	_uploadConfig: function(){
    		var _this = this;
    		var form = new FormData();
		    form.append("uploadFile", document.getElementById("fileName").files[0]);
		    form.append("appName", $('#appName').val()); 
			// XMLHttpRequest 对象
		     var xhr = new XMLHttpRequest();
		     var uploadURL = _base+"/configCenter/importConfig";
		     xhr.open("post", uploadURL, true);
		    
			 xhr.onreadystatechange = function() {
				if (xhr.readyState == 4) {// 4 = "loaded"
					if (xhr.status == 200) {
						var responseData = $.parseJSON(xhr.response);
						//if(responseData.statusCode=="1"){
							var fileData = responseData.data;
							if(fileData=="000001"){
								 var msgDialog = Dialog({
										title: '提示',
										content: "导入数据格式错误！"
									});
					      			msgDialog.showModal();
								return;
							}else if(fileData=="000000"){
								$("#query").attr("style","display:");
					 			$("#add").attr("style","display:none");
					 			$("#importDiv").attr("style","display:none");
					 			$('#rowPath').val("");
								_this._searchBtnClick();
							}else{
								var msgDialog = Dialog({
									title: '提示',
									content: "文件上失败",
									ok: function () {
										this.close();
									}
								});
								msgDialog.showModal();
							}
						//}
					} 
					
				}
			 };
			xhr.send(form);
    		
    	},
    	
    	
    });
    
    module.exports = EditConfigPager
});

