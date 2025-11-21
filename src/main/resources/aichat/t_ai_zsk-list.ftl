<#include '/macro/crud-metro-page3.ftl' >

 <style>

    .action-bar {
        display: flex;
        justify-content: space-between;
        margin-bottom: 20px;
        align-items: center;
    }
    
    .add-btn {
        padding: 8px 15px;
        background-color: #27ae60;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }
    
    .filters {
        display: flex;
        gap: 15px;
    }
    
    .filter-item {
        padding: 8px 15px;
        border: 1px solid #ddd;
        border-radius: 4px;
        cursor: pointer;
    }
    
    .filter-item.active {
        background-color: #3498db;
        color: white;
        border-color: #3498db;
    }
    
    .card-container {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 20px;
    }
    
    .card {
        background: white;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
        padding: 20px;
        transition: transform 0.2s, box-shadow 0.2s;
    }
    
    .card:hover {
        transform: translateY(-5px);
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
    }
    
    /* 卡片头部 - 新增状态组布局 */
    .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 15px;
        padding-bottom: 10px;
        border-bottom: 1px solid #eee;
    }
    
    .kb-name {
        font-size: 18px;
        font-weight: 600;
        color: #2c3e50;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        flex: 1;
        margin-right: 15px;
    }
    
    /* 状态组容器 - 整合有效状态与运行状态 */
    .status-group {
        display: flex;
        gap: 8px;
        align-items: center;
    }
    
    /* 基础状态样式 - 保持统一 */
    .status {
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 12px;
        font-weight: 500;
        min-width: 50px;
        text-align: center;
    }
    
    /* 有效/无效状态 */
    .status.active {
        background-color: #e8f5e9;
        color: #27ae60;
    }
    
    .status.inactive {
        background-color: #ffebee;
        color: #e74c3c;
    }
    
    /* 运行状态 - 新增样式 */
    .status.run {
        background-color: #e3f2fd;
        color: #2196f3;
    }
    
    .status.maintain {
        background-color: #fff3e0;
        color: #ff9800;
    }
    
    .status.stop {
        background-color: #f5f5f5;
        color: #9e9e9e;
    }
    
    .kb-code {
        font-size: 14px;
        color: #7f8c8d;
        background-color: #f8f9fa;
        padding: 2px 8px;
        border-radius: 4px;
        margin-bottom: 12px;
        display: inline-block;
    }
    
    /* 创建人样式（保留之前的优化） */
    .creator-container {
        display: flex;
        align-items: center;
        margin-bottom: 15px;
        padding: 8px 12px;
        background-color: #f8f9fa;
        border-radius: 6px;
    }
    
    .creator-avatar {
        width: 32px;
        height: 32px;
        border-radius: 50%;
        background-color: #3498db;
        color: white;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        font-weight: 500;
        margin-right: 10px;
        flex-shrink: 0;
    }
    
    .creator-info {
        display: flex;
        flex-direction: column;
    }
    
    .creator-role {
        font-size: 11px;
        color: #95a5a6;
        text-transform: uppercase;
        letter-spacing: 0.5px;
        margin-bottom: 2px;
    }
    
    .creator-name {
        font-size: 14px;
        font-weight: 500;
        color: #2c3e50;
    }
    
    .creator-time {
        font-size: 12px;
        color: #95a5a6;
        margin-top: 2px;
    }
    
    .description {
        line-height: 1.5;
        color: #34495e;
        margin-bottom: 15px;
        font-size: 14px;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
    }
    
    /* 统计信息（保留之前的优化） */
    .stats {
        display: flex;
        gap: 15px;
        margin-bottom: 15px;
    }
    
    .stat-item {
        text-align: center;
        flex: 1;
        background-color: #f8f9fa;
        padding: 8px;
        border-radius: 6px;
    }
    
    .stat-value {
        font-size: 16px;
        font-weight: 600;
        color: #2c3e50;
    }
    
    .stat-label {
        font-size: 12px;
        color: #95a5a6;
    }
    
    .card-footer {
        display: flex;
        justify-content: flex-end;
        gap: 10px;
        padding-top: 10px;
        border-top: 1px solid #eee;
    }
    
    .action-btn {
        padding: 6px 12px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 13px;
        transition: background-color 0.2s;
    }
    
    .edit-btn {
        //background-color: #f39c12;
        background-color: #e3f2fd;
        color: #2196f3;
    }
    
    .delete-btn {
        background-color: #ffebee;
        color: #e74c3c;
    }
    
    .edit-btn:hover {
      box-shadow: 0 4px 8px rgba(0,0,0,0.15);
     transform: translateY(-2px); 
    }
    
    .delete-btn:hover {
      box-shadow: 0 4px 8px rgba(0,0,0,0.15);
     transform: translateY(-2px); 
    }
</style>

<@crudmetropage3>
	<form id="query_form" action="${base}/aichat/t_ai_zsk.action" method="post">
	
		<!--顶部查询条件，根据实际情况选是上下，还是左右布局-->
		<div class="row-fluid cpquery">
		  	<div class="span2">
		  		<label class="control-label">
					知识库名称:
				</label>
				<input type="text" class="m-wrap span12" id="filter_LIKES_zskmc" name="filter_LIKES_zskmc"  value="${Parameters['filter_LIKES_zskmc']?if_exists}" maxlength="100"/>
		  	</div>
		  	<div class="span2">
		  		<label class="control-label">
					知识库代码:
				</label>
				<input type="text" class="m-wrap span12" id="filter_LIKES_zskdm" name="filter_LIKES_zskdm"  value="${Parameters['filter_LIKES_zskdm']?if_exists}" maxlength="100"/>
		  	</div>
			  
		  	<div class="span2">
		  		<label class="control-label">
					知识库代描述:
				</label>
				<input type="text" class="m-wrap span12" id="filter_LIKES_zskms" name="filter_LIKES_zskms"  value="${Parameters['filter_LIKES_zskms']?if_exists}" maxlength="20"/>
		  	</div>
			  
		  	<div class="span2">
		  		<label class="control-label">
					是否有效:
				</label>
				<select id="filter_EQS_sfyx" name="filter_EQS_sfyx" class="m-wrap span10">
					 <option value="">=全部=</option>
					 <option value="1"   <#if Parameters['filter_EQS_sfyx']?exists && Parameters['filter_EQS_sfyx']="1">selected</#if>>是</option>
					 <option value="0"   <#if Parameters['filter_EQS_sfyx']?exists && Parameters['filter_EQS_sfyx']="0">selected</#if>>否</option>
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
								
								<div class="card-container">
									<#list page.result as obj>
								        <!-- 卡片1 -->
								         <div class="card">
								            <div class="card-header">
								                <div class="kb-name">${obj.zskmc!}</div>
								                <div class="status-group">
								                	<#if obj.sfyx=='1'>
								                   		<span class="status active"><span class="dot-green"></span> 有效</span>
								                    <#else>
								                    	<span class="status inactive"><span class="dot-red"></span> 无效</span>
								                    </#if>
								                    <#if obj.status == "true">
								                    	<span class="status run"><span class="dot-green"></span> 运行中</span>
								                    <#else>
								                    	<span class="status stop"><span class="dot-red"></span> 已停止</span>
								                    </#if>
								                </div>
								            </div>
								            <div class="kb-code">${obj.zskdm!}</div>
								            
								            <div class="creator-container">
								                <div class="creator-avatar">U</div>
								                <div class="creator-info">
								                    <div class="creator-role">创建人</div>
								                    <div class="creator-name">${obj.czr?if_exists.name!}</div>
								                    <div class="creator-time">${obj.czsj!}</div>
								                </div>
								            </div>
								            
								            <div class="description">
								            	<#if obj.zskms?exists && obj.zskms != "">
								                	${obj.zskms!}
								                <#else>
								                	--暂无介绍--
								                </#if>
								            </div>
								            
								            <div class="stats">
								                <div class="stat-item">
								                    <div class="stat-value">${obj.wdsl!}</div>
								                    <div class="stat-label">文档数</div>
								                </div>
								                <div class="stat-item">
								                    <div class="stat-value">${obj.zfsl!}</div>
								                    <div class="stat-label">字符数</div>
								                </div>
								            </div>
								            
								            <div class="card-footer">
								                <button type="button" class="action-btn edit-btn" onclick="window.open('${base}/aichat/t_ai_zsk!input.action?id=${obj.id}')">编辑</button>
								                <button type="button" class="action-btn delete-btn" onclick="deletebyid('${obj.id!}')">删除</button>
								            </div>
								        </div>
							        </#list>
							        
							    </div>
							    
							    <table id="result_page_table">
								</table>		
							    
								<!--根据实际需要重新定义对话框的样式-->
								<style>
									#pfwmodal{
										width: 800px;
										margin: 0 0 0 -370px; 
									}
								</style>								
	
								<script type="text/javascript">
									function deletebyid(tmpid){
										if(confirm("确认删除吗？一旦删除不可恢复！")){
											$.post("${base}/aichat/t_ai_zsk!delete.action",{checks:tmpid},function(res){
												alert(res);
												$("#query_form").submit();
											})
										}
									}
									function add()
									{
											window.open('${base}/aichat/t_ai_zsk!input.action');
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
											{b_name:'创建知识库',b_function:add,bclass:'btn ',bicon:'icon-pencil'}
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
									
									
									function toogle(obj,entid) {
									    var sfxz=$(obj).find(".toggle").attr('checked');
									    var tmpval = "";
									    if(sfxz && sfxz == 'checked'){
									    	tmpval = "0";
									    	$(obj).find(".toggle").removeAttr("checked");
									    	$(obj).attr('style','left: -50%; width: 150px;height: 25px;')
									    }else{
									    	tmpval = "1";
									    	$(obj).find(".toggle").attr('checked','checked');
									    	$(obj).attr('style','left: 0px; width: 150px;height: 25px;')
									    }
									    $.post("${base}/aichat/t_ai_zsk!xgFiled.action",{sfyx:tmpval,entid:entid},function(res){
											layer.msg(res);
										})
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