<#include '/macro/crud-metro-page3.ftl' >

<@crudmetropage3>
	<form id="query_form" action="${base}/aichat/t_ai_qpjg.action" method="post">
	
		<!--顶部查询条件，根据实际情况选是上下，还是左右布局-->
		<div class="row-fluid cpquery">
			<div class="span2">
				<label class="control-label">
					所属知识库:
				</label>
				<select id="filter_EQS_sszsk.id" name="filter_EQS_sswdxx.sszsk.id" class="m-wrap span12">
					<option value="">=全部=</option>
					<#if reqlist3?exists>
						<#list reqlist3 as obj>
							<option value="${obj.id}" <#if  obj.id?exists && Parameters['filter_EQS_sswdxx.sszsk.id']?exists && obj.id == Parameters['filter_EQS_sswdxx.sszsk.id']>selected="true"</#if> >${obj.zskmc!}</option>
						</#list>
					</#if>
				</select>
			</div> 
		  	<div class="span2">
		  		<label class="control-label">
					标题:
				</label>
				<input type="text" class="m-wrap span12" id="filter_LIKES_biaot" name="filter_LIKES_biaot"  value="${Parameters['filter_LIKES_biaot']?if_exists}" maxlength="20"/>
		  	</div>
		  	<div class="span2">
		  		<label class="control-label">
					切片id:
				</label>
				<input type="text" class="m-wrap span12" id="filter_LIKES_qpid" name="filter_LIKES_qpid"  value="${Parameters['filter_LIKES_qpid']?if_exists}" maxlength="20"/>
		  	</div>
		  	<div class="span2">
		  		<label class="control-label">
					切片内容:
				</label>
				<input type="text" class="m-wrap span12" id="filter_LIKES_qpnr" name="filter_LIKES_qpnr"  value="${Parameters['filter_LIKES_qpnr']?if_exists}" maxlength="20"/>
		  	</div>
			<div class="span1">
		  		<label class="control-label">
					是否训练:
				</label>
				<select id="filter_EQS_sfxl" name="filter_EQS_sfxl" class="m-wrap span10">
					 <option value="">=全部=</option>
					 <option value="1"   <#if Parameters['filter_EQS_sfxl']?exists && Parameters['filter_EQS_sfxl']=="1">selected</#if>>是</option>
					 <option value="0"   <#if Parameters['filter_EQS_sfxl']?exists && Parameters['filter_EQS_sfxl']=="0">selected</#if>>否</option>
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
									  		${obj.sswdxx?if_exists.sszsk?if_exists.zskmc!}
									  	</td>
										<td>
									  		${obj.sswdxx?if_exists.mc!}
									  	</td>
									  	<td>
									  		${obj.qpid!}
									  	</td>
									  	<td  >
									  		<a class="oprt-a" href="javascript:;" onclick="parentlayerFull2('${obj.id!}')">标题：${obj.biaot!}</a>
									  	</td>
									  	<td>
									  		${obj.tokens!}
									  	</td>
									  	<td>
									  		<#if obj.sfxl?exists>
									  			<#if obj.sfxl == "1">
									  				<span class="dot-green"></span> 是
									  			<#else>
									  				<span class="dot-red"></span>	否
									  			</#if>
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
									function parentlayerFull2(id){
										$.post("${base}/aichat/t_ai_qpjg!getwjnrbyid.action",{id:id},function(res){
											parent.layer.open({
											  type: 1,
											  title: ' ',
											  content: '<div style="margin:30px;"><span style="line-height: 25px;">'+res+'</span></div>',
											  area: ['50%', '70%'], 
											  closeBtn: 1, 
											  scrollbar: true
											});
										})
									}
									function add()
									{
											window.open('${base}/aichat/t_ai_qpjg!input.action');
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
											{b_name:'删除',opttype:'delete',bclass:'btn redbtn',bicon:'icon-trash',b_url:'${base}/aichat/t_ai_qpjg!delete.action',confirmStr:'是否确认删除？',paramName:'checks',selectNum:'^[0-9]*[1-9][0-9]*$'}
										],
										page_col:
										[
											 //在这里编写结果集显示栏目名称，例：{col_name:'xxxx'};最后一列注意不能有","号
										  	{col_name:'所属知识库'},
										  	{col_name:'标题',width:'10%'},
										  	{col_name:'切片id'},
										  	{col_name:'标题',width:'25%'},
										  	{col_name:'字符数'},
										  	{col_name:'是否训练'}
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