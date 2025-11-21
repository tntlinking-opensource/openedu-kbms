<% 
	String base = request.getContextPath(); 
	//response.sendRedirect(base+"/cms/cms_show!cmsShowInfo.action");
%>
	<script>
		window.localStorage.setItem("base","<%=base%>");
		location.href = "<%=base%>/home!index3.action";
	</script>

