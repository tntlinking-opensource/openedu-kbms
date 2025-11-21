<#include '/macro/crud-metro-page3.ftl' >

<@crudmetropage3>
	<form id="query_form" action="${base}/aichat/t_ai_hsgl.action" method="post">
	
		<!--顶部查询条件，根据实际情况选是上下，还是左右布局-->
		<div class="row-fluid cpquery">
			<div class="span2">
				<label class="control-label">
					所属知识库:
				</label>
				<select id="filter_EQS_zskSet.id" name="filter_EQS_zskSet.id" class="m-wrap span12">
					<option value="">=全部=</option>
					<#if zskList?exists>
						<#list zskList as obj>
							<option value="${obj.id}" <#if  obj.id?exists && Parameters['filter_EQS_zskSet.id']?exists && obj.id == Parameters['filter_EQS_zskSet.id']>selected="true"</#if> >${obj.zskmc!}</option>
						</#list>
					</#if>
				</select>
			</div>
		  	<div class="span2">
		  		<label class="control-label">
					函数名称:
				</label>
				<input type="text" class="m-wrap span12" id="filter_LIKES_name" name="filter_LIKES_name"  value="${Parameters['filter_LIKES_name']?if_exists}" maxlength="100"/>
		  	</div>
		  	<div class="span2">
		  		<label class="control-label">
					函数描述:
				</label>
				<input type="text" class="m-wrap span12" id="filter_LIKES_description" name="filter_LIKES_description"  value="${Parameters['filter_LIKES_description']?if_exists}" maxlength="100"/>
		  	</div>
		  	<div class="span2">
		  		<label class="control-label">
					回调api:
				</label>
				<input type="text" class="m-wrap span12" id="filter_LIKES_apiurl" name="filter_LIKES_apiurl"  value="${Parameters['filter_LIKES_apiurl']?if_exists}" maxlength="500"/>
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
									  		<#if obj.zskSet?exists>
									 	 		<#list obj.zskSet as zsk>
									 	 			${zsk.zskmc!}.
									 	 		</#list>
									 	 	</#if>
									  	</td>
									  	<td>
									  		${obj.name!}
									  	</td>
									  	<td>
									  		${obj.description!}
									  	</td>
									  	<!--<#--
									  	 <td>
									  		${obj.apiurl!}
									  	</td>
									  	-->-->
									  	
								  		<td>
											<#if obj.roleSet?exists && obj.roleSet?size gt 0>
									  			<#list obj.roleSet as bd>
									  				${bd.name!},
									  			</#list>
									  		</#if>
									  	</td>
									  	<td>
									  		<#if obj.sfyx?exists && obj.sfyx == '1'>
									  			<span class="dot-green"></span> 是
									  		</#if>
									  		<#if obj.sfyx?exists && obj.sfyx == '0'>
									  			<span class="dot-red"></span> 否
									  		</#if>
									  	</td>
										 <td>
								 			<a class="oprt-a" href="javascript:;" onclick="window.open('${base}/aichat/t_ai_hsgl!input.action?id=${obj.id}')"> 修改</a>
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
											window.open('${base}/aichat/t_ai_hsgl!input.action');
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
									function test(){
										$.ajax({ 
											type : "post", 
											url:"http://localhost:8082/ausso/sjdp/school/getZdmj",
											async : true,
											data:{fileName:""},
											headers: {
										        "bkappid": "tqportal", // 设置授权令牌
										        "bksecret": "lV5W1nxSBdUweKI1hKReWOdehXb1ONsWzTn9kYi8vOV"
										    },
											success:function(data){
												console.log(data)
											},
											complete: function(res){
												console.log("complete:===>")
												console.log(res)
											},
											error: function(res){
												console.log("error:===>")
												console.log(res)
											}
										});
									
									}
									$('#query_form').pfwpage({
										page_button:[
											{b_name:'新增',b_function:add,bclass:'btn ',bicon:'icon-pencil'},
											//{b_name:'测试',b_function:test,bclass:'btn ',bicon:'icon-pencil'},
											{b_name:'删除',opttype:'delete',bclass:'btn redbtn',bicon:'icon-trash',b_url:'${base}/aichat/t_ai_hsgl!delete.action',confirmStr:'是否确认删除？',paramName:'checks',selectNum:'^[0-9]*[1-9][0-9]*$'}
										],
										page_col:
										[
											 //在这里编写结果集显示栏目名称，例：{col_name:'xxxx'};最后一列注意不能有","号
											  	{col_name:'所属知识库'},
											  	{col_name:'函数名称'},
											  	{col_name:'函数描述'},
											  	{col_name:'角色'},
											  	{col_name:'是否有效'},
											 	{col_name:''}	
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