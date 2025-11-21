<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>

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

	<title></title>

	<meta content="width=device-width, initial-scale=1.0" name="viewport" />

	<meta content="" name="description" />

	<meta content="" name="author" />

	<!-- BEGIN GLOBAL MANDATORY STYLES -->

	<link href="<%=base%>/mainface/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

	<link href="<%=base%>/mainface/css/style.css" rel="stylesheet" type="text/css"/>


	<!-- END GLOBAL MANDATORY STYLES -->

	<!-- BEGIN PAGE LEVEL STYLES -->

	<link href="<%=base%>/mainface/css/error.css" rel="stylesheet" type="text/css"/>

</head>

<!-- END HEAD -->

<!-- BEGIN BODY -->

<body class="page-404-full-page">

	<div class="row-fluid">

		<div class="span12 page-404">

			<div class="number">

				Sorry

			</div>

			<div class="details">
      
				<h3>访问被拒绝</h3>

				<p>

					<font color="red">此的帐号已在其他地方登录！再次登录请点 <a href="<%=base%>/login.action">击这里  >>></a></font><br />

				</p>

			</div>

		</div>

	</div>


</body>

<!-- END BODY -->

