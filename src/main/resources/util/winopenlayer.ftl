<#include "/macro/crud-metro-page3.ftl" >
<script>
	<#if Request["promptInfo"]?exists>
			alert('${Request["promptInfo"]}');
	<#else>
	</#if>
	// layer 弹窗方案
	var index = parent.layer.getFrameIndex(window.name);
	var newInput = document.createElement("input");
	newInput.type = "hidden";
	newInput.name = "fyclick";
	newInput.value = "Y";
	parent.$('form')[0].appendChild(newInput)
	parent.$('form')[0].submit();
	parent.layer.close(index);
	
</script>