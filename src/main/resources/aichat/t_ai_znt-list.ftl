<#include '/macro/crud-metro-page3.ftl' >
<link type="text/css" rel="stylesheet" href="${base}/mainface/css/bootstrap-toggle-buttons.css">
<link href="${base}/mainface/lui/css/style.min.css" rel="stylesheet">
<@crudmetropage3>
	<form id="query_form" action="${base}/aichat/t_ai_znt.action" method="post">
	
		<!--顶部查询条件，根据实际情况选是上下，还是左右布局-->
		<div class="row-fluid cpquery">
		  	<div class="span2">
		  		<label class="control-label">
					名称:
				</label>
				<input type="text" class="m-wrap span12" id="filter_LIKES_zntmc" name="filter_LIKES_zntmc"  value="${Parameters['filter_LIKES_zntmc']?if_exists}" maxlength="100"/>
		  	</div>
		   
							 
			<div class="span2" style="text-align:right">
				<label class="control-label">
					<br>
				</label>
				<button class="btn green" type="submit" >查询 <i class="m-icon-swapdown m-icon-white"></i></button>
				&nbsp;&nbsp;&nbsp;
				<button class="btn" type="button" id="reset">重置 </button>
			</div>
		</div>
		
		<div class="row-fluid cpmidrow">
			<center>
				&nbsp;&nbsp;
			</center>
		</div>
		<!--顶部查询条件，根据实际情况选是上下，还是左右布局-->
	
		<div class="row-fluid">
			<div class="span12 cpquery">
				<div class="row-fluid">
					
					<!--查询结果-->
					<div class="span12">
						<div class="portlet">
							<div class="portlet-title">
								<div class="caption">
									查询结果
								</div>
								<div class="actions" id="pagemenubutton" ></div>
							</div>
							<div class="portlet-body">
								<table id="result_page_table">
								<#list page.result as obj>
									<!-- 在这里编写结果列,使用新基础框架需要改成：<tr titl="${obj.id}"> -->
									<tr titl="${obj.id}">
								  		<td>	
									 	 	<#if obj.tupianPath?exists && obj.tupianPath != "">
												<img onerror="this.src='${base}/mainface/dapingdemo/studyNewUi/images/les_nopic.jpg'"  style="width:30px;height:30px;cursor:pointer;" onclick="window.open('${base}/userfiles/xmwj/${obj.tupianPath?split(',')[0]!}',' ')" src="${base}/userfiles/xmwj/${obj.tupianPath?split(',')[0]!}" alt="">
											</#if>
									  	</td>
									  	<td>
									  		${obj.zntmc!}
									  	</td>
									 	 <td>
									 	 	<#if obj.zskSet?exists>
									 	 		<#list obj.zskSet as zsk>
									 	 			${zsk.zskmc!}.
									 	 		</#list>
									 	 	</#if>
									  	</td>
									  	<td>
									  		${obj.ssfl?if_exists.name!}
									  	</td>
									  	
									  	<td>
											<#if obj.roleSet?exists && obj.roleSet?size gt 0>
									  			<#list obj.roleSet as bd>
									  				${bd.name!},
									  			</#list>
									  		</#if>
									  	</td>
									  	
									  	<td>
									  		${obj.sxh!}
									  	</td>
									  	<td>	
									  		<#if obj.sfmrznt?exists>
									  			<#if obj.sfmrznt== '1'>
									  				<span class="dot-green"></span> 是
									  			</#if>
									  			<#if obj.sfmrznt== '0'>
									  				<span class="dot-red"></span> 否
									  			</#if>
									  		</#if>

									  	</td>
									  	<td>	
									  		<#if obj.sftj?exists>
									  			<#if obj.sftj== '1'>
									  				<span class="dot-green"></span> 是
									  			</#if>
									  			<#if obj.sftj== '0'>
									  				<span class="dot-red"></span> 否
									  			</#if>
									  		</#if>

									  	</td>
									  	<td>
									  		<div class="switch" onclick="tooglek(this,'${obj.id}')">
										        <input type="checkbox" class="toggle" <#if obj.sfyx=='1'>checked</#if> >
										        <span class="slider"></span>
										    </div>
									  	</td>
										 <td>
								 			<a class="oprt-a" href="javascript:;" onclick="window.open('${base}/aichat/t_ai_znt!input.action?id=${obj.id}')"> 修改</a>
										 	&nbsp;
										 	&nbsp;
										 	<a class="oprt-a" href="javascript:;" onclick="window.open('${base}/aichat/t_ai_znt!aiAgentone.action?id=${obj.id}')">预览</a>
										 </td>
									</tr>
								</#list>
								</table>		
								
								<!--根据实际需要重新定义对话框的样式-->
								<style>
									#pfwmodal{
										width: 800px;
										margin: 0 0 0 -370px; 
									}
								</style>								
	
								<script type="text/javascript">
								
									function add()
									{
										window.open('${base}/aichat/t_ai_znt!input.action');
									}
									function startdh(){
										window.open('${base}/aichat/t_ai_znt!aiAgent.action');
									}
									function showReport_old(ywsjid,lcbh){
										if(ywsjid == "" || lcbh == "")
										{
											alert("错误：参数为空！");
											return;
										}
									    var url = "${base}/flow/t_flow_sjdqzt!shInfoUtil.action?id="+ywsjid+"&lcbh="+lcbh;
									    $("#pfwmodal").modal({backdrop: 'static', keyboard: false,remote:url});
									}
									function showReport(ywsjid,lcbh){
										if(ywsjid == "" || lcbh == "")
										{
											alert("错误：参数为空！");
											return;
										}
									    var url = "${base}/flow/t_flow_sjdqzt!shInfoUtil.action?id="+ywsjid+"&lcbh="+lcbh;
								     	//layerCommon(url);
									    //$("#pfwmodal").modal({backdrop: 'static', keyboard: false,remote:url});
									     parentlayerFull(url,' ');
									}
									$('#query_form').pfwpage({
										page_button:[
											{b_name:'新增',b_function:add,bclass:'btn ',bicon:'icon-pencil'},
											{b_name:'开始对话',b_function:startdh,bclass:'btn ',bicon:'icon-pencil'},
											{b_name:'删除',opttype:'delete',bclass:'btn redbtn',bicon:'icon-trash',b_url:'${base}/aichat/t_ai_znt!delete.action',confirmStr:'是否确认删除？',paramName:'checks',selectNum:'^[0-9]*[1-9][0-9]*$'}
										],
										page_col:
										[
											 //在这里编写结果集显示栏目名称，例：{col_name:'xxxx'};最后一列注意不能有","号
											  	{col_name:'图标'},
											  	{col_name:'名称'},
											  	{col_name:'使用知识库'},
											  	{col_name:'所属分类'},
											  	{col_name:'可访问角色'},
											  	{col_name:'顺序号'},
											  	{col_name:'是否默认'},
											  	{col_name:'是否推荐'},
											  	{col_name:'是否有效'},
											 	{col_name:'',width:'10%'}	
										],
											page_table:'#result_page_table',
											page_table_search:'#result_page_table',
											page_pageNo:${page?if_exists.pageNo!},
											page_pageSize:${page?if_exists.pageSize!},
											page_orderBy:'${page?if_exists.orderBy!}',
											page_order:'${page?if_exists.order!}',
											page_totalPages:${page?if_exists.totalPages!},
											page_totalCount:${page?if_exists.totalCount!}
										});
										
									$("#reset").click(function(){
										$("input[name^='filter_']").val("");
										$("select[name^='filter_']").val("");
									});
									function tooglek(obj,entid) {
										
										event.stopPropagation();
										
										var sfxz = $(obj).find(".toggle").attr('checked');
										
										var tmpval = "";
										if(sfxz){
											tmpval = "0";
											$(obj).find(".toggle").removeAttr('checked');
										}else{
											tmpval = "1";
											$(obj).find(".toggle").attr('checked','checked');
										}
									
										$.post("${base}/aichat/t_ai_znt!xgFiled.action",{sfyx:tmpval,entid:entid},function(res){
											layer.msg(res);
										});
										
									}
									
									
							</script>								
															
							</div>
						</div>
					</div>
					<!--查询结果-->
				
				</div>
			</div>
		</div>
		
	</form>	
</@crudmetropage3>