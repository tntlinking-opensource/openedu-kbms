<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@taglib uri="./tags/struts-tags.tld" prefix="s" %>


<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="./mainface/newlogin/css/reset.css">
	<link rel="stylesheet" type="text/css" href="./mainface/newlogin/css/login.css">
	<link rel="shortcut icon" href="./mainface/image/favicon.ico" />
</head>
<style>
	.error input 
	{
		border:1px solid #ae4930;
	}
</style>
<body>
<div class="main">
	<div class="logo-box" >
		&nbsp;
	</div>
	<div class="login-box" style="margin-top:10vh;">
		<div class="login-left">知识库<br />平台</div>
		<div class="login-right">
			<!-- j_spring_security_check -->
			<form class="form-vertical login-form" action="./loginbk.action" method="post" style="" name="lgfrm">
				<s:token/>
				<!--
					<div class="tips">你还没有账户？<a href="#">马上注册</a></div>
				-->
				<div class="tips">系统登录
				</div>
				<div class="login-heading">用户登录</div>
				<div class="login-name">用户名</div>
				<div class="login-item control-group">
					<input type="text" name="j_username"/>
				</div>
				<div class="login-name">密&nbsp;&nbsp;码</div>
				<div class="login-item control-group">
					<input type="password" name="j_password" autocomplete="off"/>
				</div>
				<div class="login-name">验证码</div>
				<div class="login-item control-group code-box">
					<input type="text" nkeypress="enter_key_trap(event)" name="j_captcha"/>
					<div class="code-img">
						<a href="javascript:;" onclick="refreshYzm()">
							<img id="captchaImg" width=120 height=36 />
						</a>
					</div>
				</div>
				<!--
				<div class="affirm-box">
					<label for="checkbox-01" class="label_check c_on">
					  <input type="checkbox" checked="checked" id="checkbox-01" name="sample-checkbox">
					  记住密码
					</label>
				</div>
				-->
				<input type="submit" class="login-btn" style="margin-top:25px;border:0px;" value="登录">
				
			</form>
		</div>
	</div>
	<div class="footer">Copyright © 2023</div>
</div>
<script type="text/javascript" src="./mainface/newlogin/js/jquery360.min.js"></script>
<script type="text/javascript" src="./mainface/js/jquery.validate.min.js" ></script>
<script type="text/javascript" src="./mainface/layer/layer.js"></script>

<script type="text/javascript">	
function refreshYzm(){
	$("#captchaImg").attr("src","./jcaptcha.jpg?jcaptchafilter=1&ra="+Math.random());
	//alert("aa");
	//document.getElementById("#captchaImg").src="./jcaptcha.jpg?jcaptchafilter=1";
}
refreshYzm();
$(function(){ 
	//多选按钮
	$("input[type='checkbox']").click(function(){ 
		if($(this).is(':checked')){ 
			$(this).attr("checked","checked"); 
			$(this).parent().removeClass("c_off").addClass("c_on"); 
		}else{ 
			$(this).removeAttr("checked"); 
			$(this).parent().removeClass("c_on").addClass("c_off"); 
		} 
	}); 
}); 
</script>
<script>
	jQuery(document).ready(function() {     
	
	  $('.login-form').validate({
            errorElement: 'label', //default input error message container
            errorClass: 'help-inline', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
            	j_username: {
                    required: true
                },
                j_password: {
                    required: true
                },
                remember: {
                    required: false
                },
                j_captcha: {
                    required: true
                }
            },
            messages: {
            	j_username: {
                    required: "请填写用户名"
                },
                j_password: {
                    required: "请填写密码"
                }
            },

            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.control-group').addClass('error'); // set error class to the control group
            },

            success: function (label) {
            	var nmae = label[0].htmlFor;
            	$("input[name='"+nmae+"']").parent().removeClass('error');
            },

            errorPlacement: function (error, element) {
                error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
            },

            submitHandler: function (form) {
            	form.submit();
            }
        });

        $('.login-form input').keypress(function (e) {
            if (e.which == 13) {
                if ($('.login-form').validate().form()) {
                	$('.login-form').submit();
                }
                return false;
            }
        });
        
        function enter_key_trap(e){
			e = e?e:((window.event)?window.event:"");
			var key = e.keyCode?e.keyCode:e.which;
			if(key == 13)
			{
				lgfrm.submit();
			}
		  }
	  
	});
	
	
	
</script>

	<%
		if(request.getAttribute("promptInfo") != null && !request.getAttribute("promptInfo").equals(""))
		{
	%>
			<script>
				layer.msg('<%=request.getAttribute("promptInfo")%>');
			</script>
	<%
		}
	
		if(request.getParameter("error") != null )
		{
			if(request.getParameter("error").equals("9"))
			{
	%>
			<script>layer.msg("验证码错误!");</script>
	<%	
			}
			if(request.getParameter("error").equals("2"))
			{
	%>
				<script>layer.msg("用户名或密码错误!");</script>
	<%					
			}
		}
	%>
</body>
</html>