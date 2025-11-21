<#include '/macro/crud-metro-page3-nowebpst.ftl' >

<#if id?exists>
	<#assign extPosition='资源上传'/>
<#else>
	<#assign extPosition='资源上传'/>
</#if>

<@crudmetropage3> 
<div class="row-fluid">
	<div class="span12">
		<div class="row-fluid">
		<!--查询结果-->
		<div class="span12">
			<div class="portlet box green">
				<div class="portlet-title">
					<div class="caption"><i class="icon-edit"></i>
						${extPosition!}
					</div>
					<div class="actions" id="pagemenubutton" ></div>
				</div>
				<div class="portlet-body form">
				
				<!--内容表单-->
				<form id="save_inputForm" class="form-horizontal   form-bordered" action="${base}/aichat/t_ai_wdxx!batchsave.action" method="post">
					<@s.token />
					<#if id?exists>
						<input type="hidden" name="id" value="${id}"/>
					</#if>
					<div class="control-group">
						<label class="control-label"><b>所属知识库:</b><span class="required">*</span></label>
						<div class="controls">
							<select name="sszskid" id="sszsk" class="m-wrap span10">
								<option value="">==请选择==</option>
								<#if reqlist3?exists>
									<#list reqlist3 as obj>
										<option value="${obj.id!}" <#if sszsk?exists && sszsk.id?exists && sszsk.id == obj.id>selected</#if> >${obj.zskmc!}</option>
									</#list>
								</#if>
							</select>
						</div>
					</div>
					<!-- 在这里编写输入的元素 -->
					<div class="control-group">
						<label class="control-label"><b>来源:</b><span class="required">*</span></label>
						<div class="controls">
							<span class="text">
								&nbsp;&nbsp;&nbsp;&nbsp;
								<label class="radio">
									<input type="radio" name="laiy" onclick="clickly('1')"  value="1" <#if laiy?exists && laiy=='1'>checked<#else>checked</#if> >上传
								</label>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<label class="radio">
									<input type="radio" name="laiy"  onclick="clickly('2')" value="2" <#if laiy?exists && laiy=='2'>checked</#if>>手动录入
								</label>
							</span>
						</div>
					</div> 
					
					<div class="row-fluid laiy laiy_1">
						<div class="span12 ">
							<div class="control-group">
								<label class="control-label"><b>资源文件:</b><span class="required">*</span></label>
								<div class="controls" id="ggfjdiv">
									<span class="btn green fileinput-button" style="position:relative">
										<i class="icon-plus icon-white"></i>
										<span>添加文件...</span>
										<input id="fujfile" class="span10 m-wrap"  type="file" name="fujfile" multiple=""   >
									</span>
									<br><br>
									<input   class="span10 m-wrap"  type="hidden" id="fjwjmName" name="fjwjmName" value="${fjwjmName!}"  >
									<input   class="span10 m-wrap"  type="hidden" id="fjwjmPath" name="fjwjmPath" value="${fjwjmPath!}">
									<div class="row-fluid" id="yscfujfile">
										<#if fjwjmPath?exists && fjwjmPath != "">
											<table style="width:60%" class="table table-striped table-bordered">
												<#assign arr=(fjwjmPath?split(","))>
												<#list fjwjmName?split(",")  as ywjms>
													<tr ><td>${ywjms_index+1}</td>
														<td>
															<a name="${arr[ywjms_index]!}" href="${base}/userfiles/xmwj/${arr[ywjms_index]!}" target="_blank">${ywjms!}</a>
															<input type="hidden"  value="${ywjms!}" class="fileName"/>
															<input type="hidden"    value="${arr[ywjms_index]!}"   class="filePath"/>
														</td>
														<td>
															<a onclick="delfile(this,'fujfile','fjwjmName','fjwjmPath')" style="cursor:pointer" herf="javascript:;" >
																<font color="red">删除</font>
															</a>
														</td>
													</tr>
												</#list>
											</table>
										</#if>
									</div>
								</div>
							</div>
						</div>
					</div>
				
					<div class="control-group laiy laiy_1">
						<label class="control-label"><b>识别方式:</b><span class="required">*</span></label>
						<div class="controls">
							<span class="text">
								&nbsp;&nbsp;&nbsp;&nbsp;
								<label class="radio">
									<input type="radio" name="sbfs"   value="1" <#if sbfs?exists && sbfs=='1'>checked<#else>checked</#if> >基础识别
								</label>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<label class="radio">
									<input type="radio" name="sbfs"  value="2" <#if sbfs?exists && sbfs=='2'>checked</#if>>高级识别
								</label>
							</span>
						</div>
					</div>
					
					<div class="control-group laiy laiy_2">
						<label class="control-label"><b>名称:</b><span class="required">*</span></label>
						<div class="controls">
							<input type="text" class="span10 m-wrap" id="mc" name="mc" value="${mc!}" maxlength="100" />
						</div>
					</div>
					
					<div class="control-group laiy laiy_2">
						<label class="control-label"><b>内容:</b><span class="required">*</span></label>
						<div class="controls">
							<textarea  class="span10 m-wrap"  rows="3" cols="80" name="wjnr"  minlength="100" maxlength="1800">${wjnr?if_exists}</textarea>	
						</div>
					</div>
					
					
					
					
					<div class="control-group">
						<label class="control-label"><b>备注:</b><span class="required"></span></label>
						<div class="controls">
							<input type="text" class="span10 m-wrap" id="beiz" name="beiz" value="${beiz!}" maxlength="100" />
						</div>
					</div>
					
					<div class="row-fluid">
						<div class="span12">
							<div class="form-actions">
								<center>
									<button class="btn green big" type="button" onclick="submt()" id="commit" name="commit" >
										<i class="icon-ok"></i> 提交
									</button>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button class="btn big" type="button" onclick="window.close()" >
										<i class="icon-remove"></i> 关闭
									</button>
								</center>									
							</div>

						</div>
					</div>
				</form>																	

				<script type="text/javascript">	
					$(document).ready(function() {
						
						$("#save_inputForm").validate({
							ignore: ":hidden",
							rules: {
								<!-- 在这里编写验证规则 -->			
								 "sszskid":{required: true},
								 "mc":{required: true},
								 "fjwjmPath":{required: true},
								 "wjnr":{required: true}
							}
						});		
						
						
						<#if laiy?exists>
							clickly("${laiy}");
						<#else>
							clickly("1");
						</#if>
					});	
					function submt(){
						if($("#save_inputForm").valid()){
							var lay = $("input[name='laiy']:checked").val();
							if(lay == '1'){
								var fjwjmPath = $("#fjwjmPath").val();
								if(fjwjmPath != "" && fjwjmPath != null){
									$("#save_inputForm").submit();
								}else{
									layer.msg("请上传资源文件！");
								}
							}else{
								$("#save_inputForm").submit();
							}
						}
					}
					$("#fujfile").change(function(e){
						//参数说明（inputfileid 上传大小mb  上传数量,文件类型（为空则不限制）  数据库实际文件名  服务器文件名）
						uploadFile('fujfile',2048,10,"txt,log,xlsx,xls,doc,docx,pdf,pptx,ppt","fjwjmName","fjwjmPath")
					})	
					function gb(){
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);
					} 		
					function clickly(flag){
						$(".laiy").hide();
						$(".laiy_" + flag).show();
					}
				</script>
				<!--内容表单-->
				</div>
			</div>
		</div>
		<!--查询结果-->
		
		</div>
	</div>
</div>
		
</@crudmetropage3>