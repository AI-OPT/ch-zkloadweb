define('app/jsp/configcenter/queryConfig', function (require, exports, module) {
    'use strict';
    var $ = require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    AjaxController = require('opt-ajax/1.0.0/index');
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("twbs-pagination/jquery.twbsPagination.min");
    require("opt-paging/aiopt.pagination");
    
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    //定义页面组件类
    var QueryConfigPager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
    		//查询
            //"click #config":"_search",    
        },
    	//重写父类
    	setup: function () {
    		QueryConfigPager.superclass.setup.call(this);
    		this._initPage();
    		//this._search();
    	},   
    	_initPage: function(){
      		//面包屑导航
      		setBreadCrumb("配置管理","配置中心");
      		//左侧菜单选中样式
      		$("#mnu_config_mng").addClass("current");
      	},
 
    	_search: function(ccsname){
//    		var name = $('#name').contents().text();
    		window.location.href=_base+"/configCenter/toEditConfig?name="+ccsname;
    	},
    });
    
    module.exports = QueryConfigPager
});

