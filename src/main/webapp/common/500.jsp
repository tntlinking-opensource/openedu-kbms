<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@page import="org.springframework.dao.DataIntegrityViolationException"%>
<%@page import="org.hibernate.exception.ConstraintViolationException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>

<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->

<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->

<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->

<!-- BEGIN HEAD -->
<% 
	String base = request.getContextPath(); 
%>
<head>

	<meta charset="utf-8" />

	<title>500 - 系统内部错误</title>

	<meta content="width=device-width, initial-scale=1.0" name="viewport" />

	<meta content="" name="description" />

	<meta content="" name="author" />

	<!-- BEGIN GLOBAL MANDATORY STYLES -->

	<link href="<%=base%>/mainface/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

	<link href="<%=base%>/mainface/css/style-metro.css" rel="stylesheet" type="text/css"/>

	<link href="<%=base%>/mainface/css/style.css" rel="stylesheet" type="text/css"/>

	<link href="<%=base%>/mainface/css/error.css" rel="stylesheet" type="text/css"/>

</head>

<!-- END HEAD -->

<!-- BEGIN BODY -->

<body class="page-500-full-page">

<%
	Throwable ex = null;
	if (exception != null)
	{
		//ex.printStackTrace();
		ex = exception;
	}
%>

	<div class="row-fluid">

		<div class="span12 page-500">

			<div class=" number">

				500

			</div>

			<div class=" details">

				<h3>系统发生内部错误，请联系管理员</h3>

				<p>
			      <font color="#FF0000">
					<%
					if(request.getAttribute("errorInfo") != null)
						out.println("<font color=red size='3px'>" + request.getAttribute("errorInfo") + "</font>");
					if(ex != null)
					{
						if(ex.getCause() instanceof ConstraintViolationException)
							out.println("<font color=red size='3px'>执行不成功，存在该数据的引用，如果要删除，请先删除涉及到的数据！</font>");
					}
					%>      
				  </font>
				  <br><br>
				  <a class="btn blue disabled" href="javascript:void(0)" onclick="disp()">查看详细错误</a>
				  &nbsp;&nbsp;&nbsp;&nbsp;
				  <a class="btn green disabled" href="<%=request.getHeader("Referer")%>" >返回</a>
				</p>

			</div>

		</div>

	</div>
	
	<div id="div1" style="display:none">
		<font color="red"><%=ex.toString()%></font>
	</div>
	<script>
		function disp(){
			if(document.getElementById("div1").style.display == "none")
			{
				document.getElementById("div1").style.display = "";
			}else{
				document.getElementById("div1").style.display = "none";
			}
			
		}
	</script>	
	

<!-- END BODY -->

</html>


