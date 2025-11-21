<#include "/macro/crud-metro-page3.ftl" >
<script type="text/javascript" src="${base}/mainface/js/jquery-1.10.1.min.js"></script>
<script>
	<#if Request["promptInfo"]?exists>
			parent.layer.msg('${Request["promptInfo"]}');
	<#else>
	</#if>
	// layer 弹窗方案
		
	var index = parent.layer.getFrameIndex(window.name);
	var newInput = document.createElement("input");
	newInput.type = "hidden";
	newInput.name = "fyclick";
	newInput.value = "Y";
	var frames = parent.document.getElementsByTagName("IFRAME");
	var ccIfrm;
	for(var i = 0; i < frames.length;i++)
	{
		var curiframe = $(frames[i]);
		var cclass = curiframe.attr("class")
		if(cclass && cclass.indexOf("active") >= 0)
		{
			ccIfrm = curiframe.attr("id");
		}
	}
	var activeFrm = parent.document.getElementById(ccIfrm);
	var activeform = activeFrm.contentWindow.document.getElementsByTagName("form")[0];
	activeform.appendChild(newInput);
	activeform.submit();
	parent.layer.close(index);
	
</script>
bbb