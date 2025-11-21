<#include '/macro/crud-metro-page3-nowebpst.ftl' >

<#if id?exists>
	<#assign extPosition='调整问题回答'/>
<#else>
	<#assign extPosition='调整问题回答'/>
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
						<form id="save_inputForm" class="form-horizontal  form-bordered" action="${base}/aichat/t_ai_yhdhjg!save.action" method="post">
							<@s.token />
							<#if id?exists>
								<input type="hidden" name="id" value="${id}"/>
							</#if>
							<!-- 在这里编写输入的元素 --> 
							<div class="control-group">
								<label class="control-label"><b>用户提问:</b><span class="required"></span></label>
								<div class="controls">
									<span class="text">
										${yhtw!}
									</span>
								</div>
							</div> 
							<div class="control-group">
								<label class="control-label"><b>AI回答:</b><span class="required">*</span></label>
								<div class="controls">
									<textarea readonly class="span10 m-wrap"  rows="5" cols="80" name="beiz"  >${zzda?if_exists}</textarea>	
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label"><b>调整后用户提问:</b><span class="required">*</span></label>
								<div class="controls">
									<textarea  class="span10 m-wrap"  rows="3" cols="80" name="xghyhwt"  >${xghyhwt?if_exists}</textarea>	
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label"><b>调整后AI回答:</b><span class="required">*</span></label>
								<div class="controls">
									<textarea  class="span10 m-wrap"  rows="8" cols="80" name="xghzzda"  >${xghzzda?if_exists}</textarea>	
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label"><b>所属知识库:</b><span class="required">*</span></label>
								<div class="controls">
									<select name="sszsk.id" id="sszsk" class="m-wrap span10">
										<option value="">==请选择==</option>
										<#if reqlist3?exists>
											<#list reqlist3 as obj>
												<option value="${obj.id!}" <#if sszsk?exists && sszsk.id?exists && sszsk.id == obj.id>selected</#if> >${obj.zskmc!}</option>
											</#list>
										</#if>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><b>是否调整:</b><span class="required">*</span></label>
								<div class="controls">
									<span class="text">
										&nbsp;&nbsp;&nbsp;&nbsp;
										<label class="radio">
											<input type="radio" name="sftz"   value="1" <#if sftz?exists && sftz=='1'>checked<#else>checked</#if> >是
										</label>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<label class="radio">
											<input type="radio" name="sftz"  value="0" <#if sftz?exists && sftz=='0'>checked</#if>>否
										</label>
									</span>
								</div>
							</div>
							
							
							<div class="row-fluid">
								<div class="span12">
									<div class="form-actions">
										<center>
											<button class="btn green big" type="submit" id="commit" name="commit" >
												<i class="icon-ok"></i> 保存
											</button>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<button class="btn big" type="button" onclick="javascript:window.close()" >
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
									rules: {
										xghyhwt:{required:true},
										"sszsk.id":{required:true},
										xghzzda:{required:true}
										<!-- 在这里编写验证规则 -->						
									}
								});						
							});	
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