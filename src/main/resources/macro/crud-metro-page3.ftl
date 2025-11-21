<#macro crudmetropage3 ext...>


<!DOCTYPE html>
<html lang="en"> 
<!-- BEGIN HEAD -->
<#assign s=JspTaglibs["/tags/struts-tags.tld"] /> 
<!-- 审核状态 -->
<#assign dispShzt={"0":"无","1":"审核中", "2":"审核通过","3":"审核退回","4":"未提交","5":"中途撤回"}>
<!-- 是否通过 -->
<#assign dispSftg={"0":"不通过","1":"通过"}>
<!-- 通用是否 -->
<#assign dispYn={"0":"否","1":"是"}>
<!-- 是否已付 -->
<#assign dispSffk={"0":"未付","1":"已付"}>
<head>

	<meta charset="utf-8">
	<title></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" >
	<!-- BEGIN GLOBAL MANDATORY STYLES -->
	<link type="text/css" rel="stylesheet" href="${base}/mainface/css/bootstrap.min.css">
	<link type="text/css" rel="stylesheet" href="${base}/mainface/css/bootstrap-responsive.min.css">
	<link type="text/css" rel="stylesheet" href="${base}/mainface/css/font-awesome.min.css">
	<link type="text/css" rel="stylesheet" href="${base}/mainface/css/style-metro.css">
	<link type="text/css" rel="stylesheet" href="${base}/mainface/css/style.css">
	<link type="text/css" rel="stylesheet" href="${base}/mainface/css/style-responsive.css">
	<link type="text/css" rel="stylesheet" href="${base}/mainface/select2.4.0/select2.min.css">
	
	<link href="${base}/mainface/lui/css/materialdesignicons.min.css" rel="stylesheet">
	
	<link id="style_color" type="text/css" rel="stylesheet" href="${base}/mainface/css/default.css">
	<link type="text/css" rel="stylesheet" href="${base}/mainface/css/uniform.default.css">
	
	<script type="text/javascript" src="${base}/mainface/js/jquery-1.10.1.min.js"></script>
	
	<link type="text/css" rel="stylesheet" href="${base}/scripts/pfw/pfw.page.metro.css"/>
	<script type="text/javascript" src="${base}/scripts/pfw/pfw.page.metro.js"></script>
	
	<link type="text/css" rel="stylesheet" href="${base}/scripts/jquery/jquery-validate/jquery.validate.css"/>
	<script type="text/javascript" src="${base}/scripts/jquery/jquery-validate/jquery.validate.pack.js"></script>
  	<script type="text/javascript" src="${base}/scripts/jquery/jquery-validate/localization/messages_cn.js"></script>
  	
	<link rel="stylesheet" type="text/css" href="${base}/mainface/icon/icon_skin/icon_skin.css">
	<link rel="stylesheet" type="text/css" href="${base}/mainface/icon/icon_skin/icon.css">
	
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery/easyui/themes/default/easyui.css">
  	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery/easyui/themes/icon.css"> 
  	
	
	<script type="text/javascript" src="${base}/mainface/js/jquery-migrate-1.2.1.min.js"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script type="text/javascript" src="${base}/mainface/js/jquery-ui-1.10.1.custom.min.js"></script>      
	<script type="text/javascript" src="${base}/mainface/js/bootstrap.min.js"></script>
	<script type="text/javascript" async="" src="${base}/mainface/js/dc.js">

	<script type="text/javascript" src="${base}/mainface/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${base}/mainface/js/jquery.blockui.min.js"></script>  
	<script type="text/javascript" src="${base}/mainface/js/jquery.cookie.min.js"></script>
	<script type="text/javascript" src="${base}/mainface/js/jquery.uniform.min.js"></script>
	<script type="text/javascript" src="${base}/mainface/select2.4.0/select2-4.0.min.js"></script>
	
	<script type="text/javascript" src="${base}/scripts/jquery/easyui/jquery.easyui.min.js"></script>
	
	<script type="text/javascript" src="${base}/scripts/js/select.js"></script>
	<script type="text/javascript" src="${base}/scripts/My97DatePicker/WdatePicker.js"></script>
	
	<link rel="shortcut icon" href="${base}/mainface/image/favicon.ico" />
	
	<script type="text/javascript" src="${base}/scripts/js/tablemerge.js"></script>
	
	<script type="text/javascript" src="${base}/mainface/layer/layer.js"></script>
	
	<script type="text/javascript" src="${base}/scripts/echarts.min.js"></script>
	
	<!--ueditor-->
	<script type="text/javascript" charset="utf-8" src="${base}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${base}/ueditor/ueditor.all.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${base}/ueditor/lang/zh-cn/zh-cn.js"></script>
	
	<!-- END GLOBAL MANDATORY STYLES -->

	<!--Form CSS-->

	
	<!--End Form CSS-->
	
	<!--解决jquery-1.10.1.min.js不支持$.browser问题-->
  	<script>
	(function(jQuery){ 
	
	if(jQuery.browser) return; 
	
	jQuery.browser = {}; 
	jQuery.browser.mozilla = false; 
	jQuery.browser.webkit = false; 
	jQuery.browser.opera = false; 
	jQuery.browser.msie = false; 
	
	var nAgt = navigator.userAgent; 
	jQuery.browser.name = navigator.appName; 
	jQuery.browser.fullVersion = ''+parseFloat(navigator.appVersion); 
	jQuery.browser.majorVersion = parseInt(navigator.appVersion,10); 
	var nameOffset,verOffset,ix; 
	
	// In Opera, the true version is after "Opera" or after "Version" 
	if ((verOffset=nAgt.indexOf("Opera"))!=-1) { 
	jQuery.browser.opera = true; 
	jQuery.browser.name = "Opera"; 
	jQuery.browser.fullVersion = nAgt.substring(verOffset+6); 
	if ((verOffset=nAgt.indexOf("Version"))!=-1) 
	jQuery.browser.fullVersion = nAgt.substring(verOffset+8); 
	} 
	// In MSIE, the true version is after "MSIE" in userAgent 
	else if ((verOffset=nAgt.indexOf("MSIE"))!=-1) { 
	jQuery.browser.msie = true; 
	jQuery.browser.name = "Microsoft Internet Explorer"; 
	jQuery.browser.fullVersion = nAgt.substring(verOffset+5); 
	} 
	// In Chrome, the true version is after "Chrome" 
	else if ((verOffset=nAgt.indexOf("Chrome"))!=-1) { 
	jQuery.browser.webkit = true; 
	jQuery.browser.name = "Chrome"; 
	jQuery.browser.fullVersion = nAgt.substring(verOffset+7); 
	} 
	// In Safari, the true version is after "Safari" or after "Version" 
	else if ((verOffset=nAgt.indexOf("Safari"))!=-1) { 
	jQuery.browser.webkit = true; 
	jQuery.browser.name = "Safari"; 
	jQuery.browser.fullVersion = nAgt.substring(verOffset+7); 
	if ((verOffset=nAgt.indexOf("Version"))!=-1) 
	jQuery.browser.fullVersion = nAgt.substring(verOffset+8); 
	} 
	// In Firefox, the true version is after "Firefox" 
	else if ((verOffset=nAgt.indexOf("Firefox"))!=-1) { 
	jQuery.browser.mozilla = true; 
	jQuery.browser.name = "Firefox"; 
	jQuery.browser.fullVersion = nAgt.substring(verOffset+8); 
	} 
	// In most other browsers, "name/version" is at the end of userAgent 
	else if ( (nameOffset=nAgt.lastIndexOf(' ')+1) < 
	(verOffset=nAgt.lastIndexOf('/')) ) 
	{ 
	jQuery.browser.name = nAgt.substring(nameOffset,verOffset); 
	jQuery.browser.fullVersion = nAgt.substring(verOffset+1); 
	if (jQuery.browser.name.toLowerCase()==jQuery.browser.name.toUpperCase()) { 
	jQuery.browser.name = navigator.appName; 
	} 
	} 
	// trim the fullVersion string at semicolon/space if present 
	if ((ix=jQuery.browser.fullVersion.indexOf(";"))!=-1) 
	jQuery.browser.fullVersion=jQuery.browser.fullVersion.substring(0,ix); 
	if ((ix=jQuery.browser.fullVersion.indexOf(" "))!=-1) 
	jQuery.browser.fullVersion=jQuery.browser.fullVersion.substring(0,ix); 
	
	jQuery.browser.majorVersion = parseInt(''+jQuery.browser.fullVersion,10); 
	if (isNaN(jQuery.browser.majorVersion)) { 
	jQuery.browser.fullVersion = ''+parseFloat(navigator.appVersion); 
	jQuery.browser.majorVersion = parseInt(navigator.appVersion,10); 
	} 
	jQuery.browser.version = jQuery.browser.majorVersion; 
	})(jQuery); 
	
	//列合并
	//用法:_w_table_rowspan("#result_page_table",1)
	function _w_table_rowspan(_w_table_id,_w_table_colnum){  
	    _w_table_firsttd = "";  
	    _w_table_currenttd = "";  
	    _w_table_SpanNum = 0;  
	    _w_table_Obj = $(_w_table_id + " tr td:nth-child(" + _w_table_colnum + ")");  
	    _w_table_Obj.each(function(i){  
	        if(i==0){  
	            _w_table_firsttd = $(this);  
	            _w_table_SpanNum = 1;  
	        }else{  
	            _w_table_currenttd = $(this);  
	            if(_w_table_firsttd.text()==_w_table_currenttd.text()){  
	                _w_table_SpanNum++;  
	                _w_table_currenttd.hide(); //remove();   
	                _w_table_firsttd.attr("rowSpan",_w_table_SpanNum);  
	            }else{  
	                _w_table_firsttd = $(this);  
	                _w_table_SpanNum = 1;  
	            }  
	        }  
	    });   
	}
  </script>
  <!--解决jquery-1.10.1.min.js不支持$.browser问题-->
	
	<!--让jquery validate的错误信息在输入域同处一行-->
	<style>
		label{
			display:inline;
		}
		.oprt-a
		{
			color: #2d8cf0;
			/*color: #15c377;*/
			background: transparent;text-decoration: none;outline: none;cursor: pointer;transition: color .2s ease;
		}
		.oprt-spt
		{
			background:#e8eaec;margin: 0 8px;display: inline-block;height: .9em;width: 1px;vertical-align: middle;position: relative;top: -.06em;
		}
		.icon-edit::before
		{
			cursor: pointer;
		}
		.cpmidrow
		{
			margin:8px 0px;
			background-color:#f5f7f9
		}
		.cpquery
		{
			margin:0px;
			padding-left:8px;
		}
		.margin-right-20
		{
			margin-right : 10px;
		}
		.treeTable th
		{
			color:#666 !important;
			font-size: 15px !important;
		}
		.dot-green{
			background-color:#52c41a;
			width: 6px;
		    height: 6px;
		    display: inline-block;
		    position: relative;
		    border-radius: 50% !important;
		    top:-2px !important;
		}
		.dot-blue{
			background-color:#2d8cf0;
			width: 6px;
		    height: 6px;
		    display: inline-block;
		    position: relative;
		    border-radius: 50% !important;
		    top:-2px !important;
		}
		.dot-red{
			background-color:#ed4014;
			width: 6px;
		    height: 6px;
		    display: inline-block;
		    position: relative;
		    border-radius: 50% !important;
		    top:-2px !important;
		}
		.dot-grey{
			background-color:#e6ebf1;
			width: 6px;
		    height: 6px;
		    display: inline-block;
		    position: relative;
		    border-radius: 50% !important;
		    top:-2px !important;
		}
	</style>	

</head>

<!-- END HEAD -->

<!-- BEGIN BODY -->

<body class="page-full-width" style="background-color:#f5f7f9;padding:15px 15px 0px 15px !important">


	<!-- BEGIN CONTAINER -->   

	<div class="page-container row-fluid" style="">

		<!-- BEGIN PAGE -->
		<div class="page-content" >

			<!-- BEGIN PAGE CONTAINER-->
			<div class="container-fluid" style="padding:10px 0px 0px 0px">
			
			
			<#nested>
			
			
			</div>

			<!-- END PAGE CONTAINER--> 
			
		</div>

		<!-- END PAGE -->    

	</div>

	<!-- END CONTAINER -->

	<!-- BEGIN FOOTER -->


	<!-- END FOOTER -->

	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->

	<!-- BEGIN CORE PLUGINS -->

	
	<!--[if lt IE 9]>

	<script src="${base}/mainface/js/excanvas.min.js"></script>

	<script src="${base}/mainface/js/respond.min.js"></script>  

	<![endif]-->   

	

	<!-- END CORE PLUGINS  <script type="text/javascript" src="${base}/mainface/js/app.js"></script> -->

	
	<!--Form JS-->


	<!--Form JS-->


	<script>

		jQuery(document).ready(function() {    
			
			//App.init();
			
			<#if page?exists && page.totalPages?exists && page.totalPages == 0>
				var pwidth = $("#result_page_table").width();
				var _cp = $("<div><img src='${base}/mainface/image/nodate.png'></div>").css("width",pwidth)
					.css("text-align","center").css("margin-top","80px");
				$("#result_page_table").after(_cp);
				$("#fyfoot").hide();
			</#if>
			
		   //checkbox init
		   	var test = $("input[type=checkbox]:not(.toggle), input[type=radio]:not(.toggle, .star)");
			if (test.size() > 0) {
			    test.each(function () {
				    if ($(this).parents(".checker").size() == 0) {
				        $(this).show();
				        $(this).uniform();
				    }
				});
			}
			//checkbox init
			
			//form表单提交弹出加载框
			$("form").bind("submit", function() 
			{
				var subFlg = true;
				$(this).find("label[class='error']").each(function(){
					if($(this).css("display") != "none")
					{
						subFlg = false;
					}
				});
				if(!subFlg)
				{
					return false;
				}
				
				/*
				$(this).find("button").each(function(){
					$(this).attr("disabled","true");
				});   
				*/
				
				$("#modalsubmit").modal({backdrop: 'static', keyboard: false});
							
			});
			//form表单提交弹出加载框
			
			//左侧查询条件显示/隐藏操作
			$('.hide-sidebar').click(function() {
				$('#sidebar').hide('fast', function() {
					$('.hide-sidebar').hide();
					$('.show-sidebar').show();
					$(".rightbody").attr("class","rightbody");
				});
				 $.cookie('ishideCx', '1');
			});
			$('.show-sidebar').click(function() {
				$('.show-sidebar').hide();
				$('.hide-sidebar').show();
				$('#sidebar').show('fast');
				$(".rightbody").attr("class","span10 rightbody");
				$.cookie('ishideCx', '0');
			});
			var ishideCxstr = $.cookie('ishideCx');
			if(ishideCxstr == '0')
			{
				$('.show-sidebar').hide();
				$('.hide-sidebar').show();
				$('#sidebar').show('fast');
				$(".rightbody").attr("class","span10 rightbody");
			}
			if(ishideCxstr == '1')
			{
				$('#sidebar').hide('fast', function() {
					$('.hide-sidebar').hide();
					$('.show-sidebar').show();
					$(".rightbody").attr("class","rightbody");
				});
			}
			//左侧查询条件显示/隐藏操作
		});
		
		//layer弹窗封装 - 全屏
		function layerFull(url,titlestr){
			var index = layer.open({
			  type: 2,
			  title: titlestr,
			  content: url,
			  area: ['100%', '100%'],
			  maxmin: true
			});
			layer.full(index);
		}
		
		//layer弹窗封装 - 跳出iframe
		function parentlayerFull(url,titlestr){
			parent.layer.open({
			  type: 2,
			  title: titlestr,
			  content: url,
			  area: ['80%', '80%'],
			  maxmin: true
			});
		}
		
		//layer弹窗封装 - 普通
		function layerCommon(url,titlestr){
			var index = layer.open({
		      type: 2,
		      title: titlestr,
		      //shadeClose: true,
		      //shade: false,
		      maxmin: true, //开启最大化最小化按钮
		      area: ['90%', '90%'],
		      content: url
		    });
		}
		

	</script>

	<!-- END JAVASCRIPTS -->
	<div style="display: none;" data-focus-on="input:first" tabindex="-1" class="modal hide fade in" id="modalsubmit" style="width:340;height:100;display: block; margin-top: -155px;" aria-hidden="false">
		<div class="modal-dialog">
			<div class="modal-body">
				<br><br>
				<center><img src='${base}/extend/image/progress.gif' />  系统正在处理中，请稍等......</center>
				<br><br>
			</div>
		</div>
	</div>
	
	<div style="display: none;" data-focus-on="input:first" tabindex="-1" class="modal hide fade in" id="pfwmodal" style="display: block; margin-top: -155px;" aria-hidden="false">
		<div class="modal-body">
			 &nbsp;
		</div>
		<div class="modal-footer">
			<a href="#" class="btn blue" data-dismiss="modal">确定</a>
	        <a href="#" class="btn" data-dismiss="modal">取消</a>
	    </div>
	</div>

</body>
<!-- END BODY -->
</html>
		<!-- 显示Action中传过来的提示信息 -->
		<#if Request['promptInfo']?exists>
			<script>
				alert('${Request['promptInfo']?if_exists}');
			</script>
		</#if>
</#macro>