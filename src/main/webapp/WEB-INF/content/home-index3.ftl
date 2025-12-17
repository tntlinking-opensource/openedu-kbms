<!DOCTYPE html>
<html lang="zh">
	<#assign s=JspTaglibs["/tags/struts-tags.tld"] /> 
	<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] /> 
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
	<title>知识库平台</title>
	<link href="${base}/mainface/lui/css/bootstrap.min.css" rel="stylesheet">
	<link href="${base}/mainface/lui/css/materialdesignicons.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${base}/mainface/lui/js/bootstrap-multitabs/multitabs.min.css">
	<link href="${base}/mainface/lui/css/style.min.css" rel="stylesheet">
	<script type="text/javascript" src="${base}/mainface/lui/js/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/mainface/layer/layer.js"></script>
  	<link rel="shortcut icon" href="./mainface/image/favicon.ico" />
</head>
<style>
	.no1menu
	{
		font-size: 16px;
		margin-right: 30px;
		cursor: pointer;
		padding: 15px;	
		color : #ffffff
	}
	.no1menu:hover
	{
		color : #ffffff;
		border-bottom: 2px solid #ffffff;
	}
	.no1menuactive
	{
		color : #ffffff;	
		border-bottom: 2px solid #ffffff;
		font-weight : 600;
	}
	.nav-subnav li .active
	{
		background-color: rgb(53,181,243,0.75) !important;
	}
</style>
<script>
	//layer弹窗封装 - 全屏
	function layerFull(url){
		var index = layer.open({
		  type: 2,
		  title:' ',
		  content: url,
		  area: ['700px', '450px'],
		  maxmin: true
		});
		layer.full(index);
	}
	//layer弹窗封装 - 普通
	function layerCommon(url){
		layer.open({
	      type: 2,
	      title: ' ',
	      maxmin: true, //开启最大化最小化按钮
	      area: ['893px', '600px'],
	      content: url
	    });
	}
</script>
<body data-logobg="default" data-sidebarbg="default">
<div class="lyear-layout-web">
  <div class="lyear-layout-container">
    <!--左侧导航-->
    <aside class="lyear-layout-sidebar" style="top:67px;height:100%;">
      
      <!-- logo
      <div id="logo" class="sidebar-header" >
        <a href="${base}">
        	<img src="${base}/mainface/newlogin/images/pdjyj.png" style="left:0px">
        </a>
      </div>
      -->
      <div class="lyear-layout-sidebar-scroll"> 
        
        <nav class="sidebar-main" style="margin-top:10px;">
          <ul class="nav nav-drawer" id="lftmenu">
            
            <#--
            <li class="nav-item active"> 
            	<a class="multitabs" href="${base}/home!main.action">
            		<i class="mdi mdi-home"></i> <span>首页</span>
            	</a> 
            </li>
            -->
            
            
	            <!--直接左侧加截所有菜单，适应用一般小系统，没几个功能 最多只支持三级，太多级体验也不好-->
	            <!---->
	            <#---->
	            <#list menus as topmenu>
					<@security.authorize ifAnyGranted="${topmenu.authNames}">
						<li class="nav-item <#if topmenu.children?exists && topmenu.children?size gt 0>nav-item-has-subnav</#if> <#if topmenu_index == 0>open</#if> ">
							<#if topmenu.resourceType?exists && topmenu.resourceType == 'menu'>
								<a href="javascript:void(0)">
									<i class="mdi <#if topmenu.urlicon?exists && topmenu.urlicon != ''>${topmenu.urlicon!}<#else>mdi-format-align-justify</#if>"></i> <span>${topmenu.name!}</span>
								</a>
							<#else>
								<a href="${base+topmenu.value!}" onclick="uppath('${topmenu.value}')" <#if topmenu.sfxzdk?exists && topmenu.sfxzdk == '1'>target='_blank'<#else>class="multitabs"</#if> >
				            		<i class="mdi <#if topmenu.urlicon?exists && topmenu.urlicon != ''>${topmenu.urlicon!}<#else>mdi-format-align-justify</#if>"></i> <span>${topmenu.name!}</span>
				            	</a> 
							</#if>
							
							<#if topmenu.children?exists && topmenu.children?size gt 0>
								<ul class="nav nav-subnav">
									<#list topmenu.children as child1>
										<@security.authorize ifAnyGranted="${child1.authNames}">
											<!--
											<#--
											<li class="<#if child1.children?exists && child1.children?size gt 0>nav-item nav-item-has-subnav</#if>">
											-->
											-->
											<li class="nav-item">
												<#if child1.resourceType?exists && child1.resourceType == 'menu'>
													<a href="javascript:void(0)">
														${child1.name!}
													</a>
													
													<#list child1.children as child2>
														<@security.authorize ifAnyGranted="${child2.authNames}">
															<ul class="nav nav-subnav">
			                									<li> 
			                										<a href="${base+child2.value!}" onclick="uppath('${child2.value}')" <#if child2.sfxzdk?exists && child2.sfxzdk == '1'>target='_blank'<#else>class="multitabs"</#if> >
			                											${child2.name!}
			                										</a> 
			                									</li>
			                								</ul>
		                								</@security.authorize>
	                								</#list>
	                								
												<#else>
													<a href="${base+child1.value!}" onclick="uppath('${child1.value}')" <#if child1.sfxzdk?exists && child1.sfxzdk == '1'>target='_blank'<#else>class="multitabs"</#if> >
									            		${child1.name!}
									            	</a> 
												</#if>
											</li>
										</@security.authorize>
									</#list>
								</ul>
							</#if>
						</li>
					</@security.authorize>
				</#list>
								
          </ul>
        </nav>
        
        <!--
	        <div class="sidebar-footer">
	        </div>
        -->
      </div>
      
    </aside>
    <!--End 左侧导航-->  
    
    <!--头部信息-->
    <header class="lyear-layout-header" style="z-index:5;padding-left:0px;background:url(${base}/mainface/image/bg/header-bg3.png) center no-repeat;background-size:cover;">
      
      <nav class="navbar navbar-default" >
        <div class="topbar" style="">
          
          <div class="topbar-left" style="margin-left:0px;">
          	<span style="color: #fff;font-size: 25px;padding: 0px 16px;">
          		知识库平台
          	</span>
          	
          </div>
          
          <ul class="topbar-right">
            <li class="dropdown dropdown-profile">
              <a href="javascript:void(0)" data-toggle="dropdown" style="color:#fff !important;margin-right:15px;">
                <#if yg?exists && yg.ygtx?exists>
                	<img style="width:35px;height:35px;" class="img-avatar img-avatar-48 m-r-10" src="${base}/userfiles/gqxt/picture/${yg?if_exists.ygtx!}" />
                <#else>
                	<img style="width:35px;height:35px;" class="img-avatar img-avatar-48 m-r-10" src="${base}/mainface/image/userphoto/avatar2_small.jpg" />
                </#if>
                <span>${usr?if_exists.name?if_exists} <span class="caret"></span></span>
              </a>
              <ul class="dropdown-menu dropdown-menu-right">
                <li> <a class="multitabs" data-url="${base}/security/user!userprofile.action" href="javascript:void(0)"><i class="mdi mdi-account"></i> 个人信息</a> </li>
                <li> <a onclick="layerCommon('${base}/security/user!updatePassword.action')" href="javascript:void(0)"><i class="mdi mdi-lock-outline"></i> 修改密码</a> </li>
                <li>
                	<!-- 
                	<a class="multitabs" data-url="${base}/xtgl/t_xtgl_xxtx!yhxxtx.action" href="javascript:void(0)"><i class="mdi mdi-email-open-outline"></i> 我的消息</a>
                	--> 
                	<a  href="javascript:void(0)" onclick="window.open('${base}/xtgl/t_xtgl_xxtx!yhxxtx.action')"><i class="mdi mdi-email-open-outline"></i> 我的消息</a>
                </li>
                <li class="divider"></li>
                <li> <a href="${base}/j_spring_security_logout"><i class="mdi mdi-logout-variant"></i> 退出登录</a> </li>
              </ul>
            </li>
            
            <!-- 头部有一级菜单的代码，数据库中需要做3级菜单 
            <#---->
			-->
            
            <#assign curMenuId = ''>
            <c style="margin-left:15px">&nbsp;</c>
			<#list menus?reverse as topmenu>
				<@security.authorize ifAnyGranted="${topmenu.authNames}">
					<li id="tpMenu_${topmenu.id}" class="no1menu <#if curMenuId==topmenu.id>no1menuactive</#if>" onclick="chgleftmenu('${topmenu.id!}')">
		            	${topmenu.name!}
		            </li>  
					<#assign curMenuId = topmenu.id>
				</@security.authorize>
			</#list>
			<script type="text/javascript" src="${base}/mainface/lui/js/jquery.min.js"></script>
			<script>
				function chgleftmenu(parentid)
				{
					$("li[id^=tpMenu_").each(function(){
						$(this).removeClass("no1menuactive");
						var tmpId = $(this).attr("id");
						if(tmpId == "tpMenu_" + parentid)
						{
							$(this).addClass("no1menuactive");				
						}
					});
					$.ajax({ 
						type : "post", 
						async : true,
						url:"${base}/security/resource!getleftmenuhtml.action?pid="+parentid,
						success:function(data){
							$("#lftmenu").html(data);
					 	}
					});
						
				}
				$(document).ready(function() {
					<#if curMenuId?exists && curMenuId != ''>
						chgleftmenu('${curMenuId!}');
					</#if>
				});
			</script>
			
            
            <!--切换主题配色-->
            <!--
		    <li class="dropdown dropdown-skin">
			  <span data-toggle="dropdown" class="icon-palette"><i class="mdi mdi-palette"></i></span>
			  <ul class="dropdown-menu dropdown-menu-right" data-stopPropagation="true">
			    <li class="drop-title"><p>LOGO</p></li>
				<li class="drop-skin-li clearfix">
                  <span class="inverse">
                    <input type="radio" name="logo_bg" value="default" id="logo_bg_1" checked>
                    <label for="logo_bg_1"></label>
                  </span>
                  <span>
                    <input type="radio" name="logo_bg" value="color_2" id="logo_bg_2">
                    <label for="logo_bg_2"></label>
                  </span>
                  <span>
                    <input type="radio" name="logo_bg" value="color_3" id="logo_bg_3">
                    <label for="logo_bg_3"></label>
                  </span>
                  <span>
                    <input type="radio" name="logo_bg" value="color_4" id="logo_bg_4">
                    <label for="logo_bg_4"></label>
                  </span>
                  <span>
                    <input type="radio" name="logo_bg" value="color_5" id="logo_bg_5">
                    <label for="logo_bg_5"></label>
                  </span>
                  <span>
                    <input type="radio" name="logo_bg" value="color_6" id="logo_bg_6">
                    <label for="logo_bg_6"></label>
                  </span>
                  <span>
                    <input type="radio" name="logo_bg" value="color_7" id="logo_bg_7">
                    <label for="logo_bg_7"></label>
                  </span>
                  <span>
                    <input type="radio" name="logo_bg" value="color_8" id="logo_bg_8">
                    <label for="logo_bg_8"></label>
                  </span>
				</li>
				<li class="drop-title"><p>头部</p></li>
				<li class="drop-skin-li clearfix">
                  <span class="inverse">
                    <input type="radio" name="header_bg" value="default" id="header_bg_1" checked>
                    <label for="header_bg_1"></label>                      
                  </span>                                                    
                  <span>                                                     
                    <input type="radio" name="header_bg" value="color_2" id="header_bg_2">
                    <label for="header_bg_2"></label>                      
                  </span>                                                    
                  <span>                                                     
                    <input type="radio" name="header_bg" value="color_3" id="header_bg_3">
                    <label for="header_bg_3"></label>
                  </span>
                  <span>
                    <input type="radio" name="header_bg" value="color_4" id="header_bg_4">
                    <label for="header_bg_4"></label>                      
                  </span>                                                    
                  <span>                                                     
                    <input type="radio" name="header_bg" value="color_5" id="header_bg_5">
                    <label for="header_bg_5"></label>                      
                  </span>                                                    
                  <span>                                                     
                    <input type="radio" name="header_bg" value="color_6" id="header_bg_6">
                    <label for="header_bg_6"></label>                      
                  </span>                                                    
                  <span>                                                     
                    <input type="radio" name="header_bg" value="color_7" id="header_bg_7">
                    <label for="header_bg_7"></label>
                  </span>
                  <span>
                    <input type="radio" name="header_bg" value="color_8" id="header_bg_8">
                    <label for="header_bg_8"></label>
                  </span>
				</li>
				<li class="drop-title"><p>侧边栏</p></li>
				<li class="drop-skin-li clearfix">
                  <span class="inverse">
                    <input type="radio" name="sidebar_bg" value="default" id="sidebar_bg_1" checked>
                    <label for="sidebar_bg_1"></label>
                  </span>
                  <span>
                    <input type="radio" name="sidebar_bg" value="color_2" id="sidebar_bg_2">
                    <label for="sidebar_bg_2"></label>
                  </span>
                  <span>
                    <input type="radio" name="sidebar_bg" value="color_3" id="sidebar_bg_3">
                    <label for="sidebar_bg_3"></label>
                  </span>
                  <span>
                    <input type="radio" name="sidebar_bg" value="color_4" id="sidebar_bg_4">
                    <label for="sidebar_bg_4"></label>
                  </span>
                  <span>
                    <input type="radio" name="sidebar_bg" value="color_5" id="sidebar_bg_5">
                    <label for="sidebar_bg_5"></label>
                  </span>
                  <span>
                    <input type="radio" name="sidebar_bg" value="color_6" id="sidebar_bg_6">
                    <label for="sidebar_bg_6"></label>
                  </span>
                  <span>
                    <input type="radio" name="sidebar_bg" value="color_7" id="sidebar_bg_7">
                    <label for="sidebar_bg_7"></label>
                  </span>
                  <span>
                    <input type="radio" name="sidebar_bg" value="color_8" id="sidebar_bg_8">
                    <label for="sidebar_bg_8"></label>
                  </span>
				</li>
			  </ul>
			</li>
			-->
            <!--切换主题配色-->
            
          </ul>
          
        </div>
      </nav>
      
    </header>
    <!--End 头部信息-->
    
    <!--页面主要内容-->
    <main class="lyear-layout-content">
      
      <div id="iframe-content"></div>
      
    </main>
    <!--End 页面主要内容-->
<!--  
</div>
</div>
-->


<script>
	function uppath(url)
	{
		if(typeof(url) != "undefined" && url != "")
		{
			$.ajax({ 
				type : "post", 
				url:"${base}/security/public_service!getUrlJosn.action",
				data:{pageurl:url},
				success:function(data){
					if(typeof(returnVal) == "undefined")
					{
						var obj = eval("("+data+")");
						if (obj.length == 0) {
						} else {
							var str = "";
							for (var i =obj.length-1 ; i >=0; i--) {
								if(i==0){
								    str += "<cc style='color:#515a6e'>" + obj[i].name + "</cc>";
								}else{
									str += obj[i].name+"<cc style='margin:0 8px 0 8px;color:#dcdee2;font-size:12px'>/</cc>";	
								}
							}
							$("#breadcrumb").html(str);
						}
					}else{
						$("#breadcrumb").html("首页");
					}
			 	}
			});	
		}
	}
	
</script>

<script type="text/javascript" src="${base}/mainface/lui/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${base}/mainface/lui/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="${base}/mainface/lui/js/bootstrap-multitabs/multitabs.js"></script>
<script type="text/javascript" src="${base}/mainface/lui/js/index.min.js"></script>
</body>

</html>