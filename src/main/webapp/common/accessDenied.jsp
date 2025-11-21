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

	<title>访问被拒绝</title>

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

				Denied

			</div>

			<div class="details">

				<h3>抱歉,您没有权限访问该页面</h3>

				<p>

					访问被拒绝<br />

				</p>

			</div>

		</div>

	</div>

</body>

<!-- END BODY -->

</html>
