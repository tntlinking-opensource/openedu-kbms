<#include '/macro/crud-metro-page3.ftl' >

<@crudmetropage3>
	<form id="query_form" action="${base}/aichat/t_ai_yhdhjg.action" method="post">
	
		<!--顶部查询条件，根据实际情况选是上下，还是左右布局-->
		<div class="row-fluid cpquery">
			   
		  	<div class="span2">
		  		<label class="control-label">
					操作人:
				</label>
				<input type="text" class="m-wrap span12" id="filter_LIKES_czr.name_OR_czr.loginName" name="filter_LIKES_czr.name_OR_czr.loginName"  value="${Parameters['filter_LIKES_czr.name_OR_czr.loginName']?if_exists}" maxlength="20"/>
		  	</div>
						
		  	<div class="span2">
		  		<label class="control-label">
					问题:
				</label>
				<input type="text" class="m-wrap span12" id="filter_LIKES_yhtw" name="filter_LIKES_yhtw"  value="${Parameters['filter_LIKES_yhtw']?if_exists}" maxlength="20"/>
		  	</div>
		  	
		  	<div class="span2">
		  		<label class="control-label">
					AI回答:
				</label>
				<input type="text" class="m-wrap span12" id="filter_LIKES_zzda" name="filter_LIKES_zzda"  value="${Parameters['filter_LIKES_zzda']?if_exists}" maxlength="20"/>
		  	</div>
			    
		  	<div class="span1">
		  		<label class="control-label">
					是否调整:
				</label>
				<select id="filter_EQS_sftz" name="filter_EQS_sftz" class="m-wrap span10">
					 <option value="">=全部=</option>
					 <option value="1"   <#if Parameters['filter_EQS_sftz']?exists && Parameters['filter_EQS_sftz']=="1">selected</#if>>是</option>
					 <option value="0"   <#if Parameters['filter_EQS_sftz']?exists && Parameters['filter_EQS_sftz']=="0">selected</#if>>否</option>
				</select>
		  	</div>
		  	
		  	<div class="span1">
		  		<label class="control-label">
					是否训练:
				</label>
				<select id="filter_EQS_sfxlh" name="filter_EQS_sfxlh" class="m-wrap span10">
					 <option value="">=全部=</option>
					 <option value="1"   <#if Parameters['filter_EQS_sfxlh']?exists && Parameters['filter_EQS_sfxlh']=="1">selected</#if>>是</option>
					 <option value="0"   <#if Parameters['filter_EQS_sfxlh']?exists && Parameters['filter_EQS_sfxlh']=="0">selected</#if>>否</option>
				</select>
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
									  		${obj.czr?if_exists.name!}(${obj.czr?if_exists.loginName!})
									  		<br/>
									  		${obj.czsj!}
									  	</td>
									  	<td>
									  		${obj.yhtw!}
									  	</td>
									  	<td title="${obj.zzda!}">
									  		<#if obj.zzda?exists && obj.zzda?length gt 200>
									  			${obj.zzda?substring(0,200)!}...
									  		<#else>
									  			${obj.zzda!}
									  		</#if>
									  	</td>
									  	<td>
									  		<#if obj.sftz?exists && obj.sftz == '1'>
									  			<span class="dot-green"></span> 是
									  			<br/>
									  			${obj.sszsk?if_exists.zskmc!}
									  			
									  		</#if>
									  		<#if obj.sftz?exists && obj.sftz == '0'>
									  			<span class="dot-red"></span> 否
									  		</#if>
									  	</td>
									  	<td>
									  		<#if obj.sfxlh?exists && obj.sfxlh == '1'>
									  			<span class="dot-green"></span> 是
									  		</#if>
									  		<#if obj.sfxlh?exists && obj.sfxlh == '0'>
									  			<span class="dot-red"></span> 否
									  		</#if>
									  	</td>
										 <td>
								 			<a class="oprt-a" href="javascript:;" onclick="window.open('${base}/aichat/t_ai_yhdhjg!input.action?id=${obj.id}')">调整问题和AI回答</a>
											<#if obj.sftz?exists && obj.sftz == '1' &&  obj.sfxlh?exists && obj.sfxlh == '0' >
												<a class="oprt-a" href="javascript:;" onclick="djxl('${obj.id!}')">点击训练</a>
											</#if>
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
									//
									function djxl(tmpid){
										if(confirm("确认训练吗？")){
											var url = "${base}/aichat/t_ai_yhdhjg!wdxl.action";
											$.post(url,{ckids:tmpid},function(res){
												alert(res);
												$("#query_form").submit();
											})
										}
									}
									
									
									$('#query_form').pfwpage({
										page_button:[
										{b_name:'删除',opttype:'delete',bclass:'btn redbtn',bicon:'icon-trash',b_url:'${base}/aichat/t_ai_yhdhjg!delete.action',confirmStr:'是否确认删除？',paramName:'checks',selectNum:'^[0-9]*[1-9][0-9]*$'}
										],
										page_col:
										[
											 //在这里编写结果集显示栏目名称，例：{col_name:'xxxx'};最后一列注意不能有","号
											  	{col_name:'所属用户'},
											  	{col_name:'问题',width:'10%;'},
											  	{col_name:'AI回答',width:'50%;'},
											 	{col_name:'是否调整'}	,
											 	{col_name:'是否训练'}	,
											 	{col_name:'',width:'10%;'}	
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