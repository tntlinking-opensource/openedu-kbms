<script>
/*
$(document).ready(function(){
	$('#positonText').load('${base}/system/jquery!positon.action',{'uri':'${request.getRequestURI()?replace(base,'')}'},function(json) {
		//var json = $.parseJSON(json);
		if(json!=null){
			json=json.replace(',','->');
		}
	  	$("#positonText").html(json);
	});
});
*/
</script>	
<table width="100%" height="28" border="0" cellpadding="0" cellspacing="0" class="title">
	<tr>
	  <td>当前位置：<span id="positonText"></span> &nbsp;${extPosition?if_exists}</td>
	</tr>
</table>
