<#include '/macro/crud-metro-page3-nowebpst.ftl' >

<#if id?exists>
	<#assign extPosition='AI智能体维护'/>
<#else>
	<#assign extPosition='AI智能体维护'/>
</#if>

<style>
		.imdvcs
		{
			//width:145px;
			//height:145px;
			float:left;
			margin-right:10px;
			margin-bottom:15px;
			position: relative;
		}
		.imgpr
		{
			width:145px;
			height:145px;
		}
		.imdvcs .imgfile
		{
			font-size: 30px;
			width: 100%;
			height: 100%; 
			left:0px;
			top:0;
			z-index:999 ;
			position: absolute;
			cursor:pointer;
			-ms-filter:'alpha(opacity=0)';
			opacity: 0;
		}
		.layui-layer-loading{
			padding-left:0px!important;
		}
	</style>
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
						<form id="save_inputForm" class="form-horizontal  " action="${base}/aichat/t_ai_znt!save.action" method="post">
							<@s.token />
							<#if id?exists>
								<input type="hidden" name="id" value="${id}"/>
							</#if>
							<!-- 在这里编写输入的元素 -->
							<div class="control-group">
								<label class="control-label"><b>智能体名称:</b><span class="required">*</span></label>
								<div class="controls">
									<input type="text" class="span10 m-wrap" id="zntmc" name="zntmc" value="${zntmc!}" maxlength="100" />
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label"><b>智能体代码:</b><span class="required">*</span></label>
								<div class="controls">
									<input type="text" class="span10 m-wrap" id="zntdm" name="zntdm" value="${zntdm!}" maxlength="30" />
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label"><b>副标题:</b><span class="required">*</span></label>
								<div class="controls">
									<input type="text" class="span10 m-wrap" id="fbt" name="fbt" value="${fbt!}" maxlength="100" />
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label"><b>知识库:</b><span class="required"></span></label>
								<div class="controls">
									<select id="sszsk" name="sszsk" class="span10 m-wrap" >
									</select>
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label"><b>精准度:</b><span class="required">*</span></label>
								<div class="controls">
									<input type="number"  step="0.01" max="10" min="0.01"  class="span10 m-wrap" id="source" name="source" value="<#if source?exists>${source!}<#else>0.5</#if>" maxlength="5" />
									<span class="text">
										注：指的是匹配知识库的精准度,建议0.5,最大1。
									</span>
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label"><b>提示词:</b><span class="required">*</span></label>
								<div class="controls">
									<textarea  class="span10 m-wrap"  rows="5" cols="80" name="promptWords"  maxlength="2000">${promptWords?if_exists}</textarea>	
									<span class="text">
										例如：你是一个办事大厅专家，回答问题尽量使用表格、流程图等形式展现数据、请根据用户回答相关问题，不相关的问题，请回答暂不支持。
									</span>
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label"><b>多轮对话数:</b><span class="required">*</span></label>
								<div class="controls">
									<input type="number"  step="0" max="10" min="0"  class="span10 m-wrap" id="dldhs" name="dldhs" value="<#if dldhs?exists>${dldhs!}<#else>0</#if>" maxlength="5" />
									<span class="text">
										注：指的是和AI交互保留历史对话，数值越大消耗资源，tokens越大。
									</span>
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label"><b>可访问角色:</b><span class="required"></span></label>
								<div class="controls">
									<select id="ssjs" name="ssjs" class="span10 m-wrap" >
									</select>
									<br/>
									<span class="text" style="color:red;">
									注：不选择所有人都可访问。
									</span>
								</div>
							</div>
									
							<div class="control-group">
								<label class="control-label"><b>所属分类:</b><span class="required">*</span></label>
								<div class="controls">
									<select name="ssfl.id" id="ssfl" class="m-wrap span10">
										<option value="">==请选择==</option>
										<#if ssflList?exists>
											<#list ssflList as obj>
												<option value="${obj.id!}" <#if  ssfl?exists && ssfl.id == obj.id>selected</#if> >${obj.name!}</option>
											</#list>
										</#if>
									</select>
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label"><b>图标:</b><span class="required">*</span></label>
								<div class="controls">
									<div  id="imdv1"    class="imdvcs">
					                   	<img onerror="this.src='${base}/mainface/dapingdemo/studyNewUi/images/les_nopic.jpg'" id="tupianPathImg"  style="width: 160px;height:160px;" src="<#if tupianPath?exists && tupianPath != "">${base}/userfiles/xmwj/${tupianPath!}<#else>${base}/mainface/image/fupimg.png</#if>" class="imgpr"/>
				                        <input type="file" id="tupianPathsFile"  name="tupianPaths"  accpet="image/png,image/jpeg,image/jpg"   class="imgfile" multiple=""/>
				                        <input type="hidden" id="tupianPath"  name="tupianPath" value="${tupianPath!}" class="imgfile" />
				                        <input type="hidden" id="tupianName"  name="tupianName" value="${tupianName!}" class="imgfile" />
				                        <br/>
				                    </div>
								</div>
							</div>	
							
							
							<div class="control-group">
								<label class="control-label"><b>用户提问最大tokens:</b><span class="required">*</span></label>
								<div class="controls">
									<input type="number"  step="0" class="span10 m-wrap" id="usermaxTokens" name="usermaxTokens" value="<#if usermaxTokens?exists>${usermaxTokens!}<#else>1000</#if>" maxlength="10" />
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label"><b>ai回答最大tokens:</b><span class="required">*</span></label>
								<div class="controls">
									<input type="number" step="0" class="span10 m-wrap" id="aimaxTokens" name="aimaxTokens" value="<#if aimaxTokens?exists>${aimaxTokens!}<#else>2000</#if>" maxlength="10" />
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><b>介绍:</b><span class="required"></span></label>
								<div class="controls">	
									<textarea  class="span10 m-wrap"  rows="5" cols="80" name="jies"  maxlength="430">${jies?if_exists}</textarea>	
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label"><b>是否默认智能体:</b><span class="required">*</span></label>
								<div class="controls">
									<span class="text">
										&nbsp;&nbsp;&nbsp;
										<label class="radio">
											
											<input type="radio" name="sfmrznt"  value="0" <#if sfmrznt?exists && sfmrznt=='0'>checked<#else>checked</#if>>否
										</label>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<label class="radio">
											
											<input type="radio" name="sfmrznt"   value="1" <#if sfmrznt?exists && sfmrznt=='1'>checked</#if> >是
										</label>
									</span>
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label"><b>是否推荐:</b><span class="required">*</span></label>
								<div class="controls">
									<span class="text">
										&nbsp;&nbsp;&nbsp;
										<label class="radio">
											<input type="radio" name="sftj"   value="1" <#if sftj?exists && sfyx=='1'>checked<#else>checked</#if> >是
										</label>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<label class="radio">
											<input type="radio" name="sftj"  value="0" <#if sftj?exists && sfyx=='0'>checked</#if>>否
										</label>
									</span>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><b>顺序号:</b><span class="required">*</span></label>
								<div class="controls">
									<input type="number" step="0.01" class="span10 m-wrap" id="sxh" name="sxh" value="${sxh!}" maxlength="10" />
								</div>
							</div>
							 
							<div class="control-group">
								<label class="control-label"><b>是否有效:</b><span class="required">*</span></label>
								<div class="controls">
									<span class="text">
										&nbsp;&nbsp;&nbsp;
										<label class="radio">
											<input type="radio" name="sfyx"   value="1" <#if sfyx?exists && sfyx=='1'>checked<#else>checked</#if> >是
										</label>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<label class="radio">
											<input type="radio" name="sfyx"  value="0" <#if sfyx?exists && sfyx=='0'>checked</#if>>否
										</label>
									</span>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span12">
									<div class="form-actions">
										<center>
											<button class="btn green big" type="submit" id="commit" name="commit" >
												<i class="icon-ok"></i> 提交
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
										<!-- 在这里编写验证规则 -->			
										zntmc:{required:true},	
										zntdm:{required:true},	
										usermaxTokens:{required:true},	
										aimaxTokens:{required:true},
										source:{required:true},	
										tupianPath:{required:true}
									}
								});			
								
								
								$("#tupianPathsFile").change(function(e){
									var flsts = $("#tupianPathsFile").get(0).files;
									var file= flsts[0].name;
									var wjlx = "jpeg,jpg,bmp,png,webp";		
									var flag = true;
														
									if(wjlx != "" && wjlx != null){
										var fileType = wjlx.split(",");
										var count =0;
										for(var i=0;i<flsts.length;i++){
										    var file= flsts[i].name;
										    var start =file.indexOf('.')+1;
										    var end =file.length;
										    var suffix =file.substring(start,end);
										    for(var j=0;j<fileType.length;j++){
										        if(suffix == fileType[j]){
										            count ++;
										        }
										    }
										}
										if(count === 0){
										    parent.layer.msg('附件的文件格式需为：'+wjlx);
										    flag = false;
										    return false;
										}
									}
									
									if(flag){
										var formData = new FormData(); 
										var fileNmaeList = "";
										for(var i=0;i<flsts.length;i++){
											fileNmaeList += flsts[i].name+",";
									      	formData.append('fileList', flsts[i]);
										}
										formData.append('imgwidth','230');
										formData.append('imgheight','160');
										fileNmaeList = fileNmaeList.substring(0,fileNmaeList.length-1);
										formData.append("fileNameList", fileNmaeList);
										$.ajax({
											url: '${base}/pblic/file_util!tjfjscForslt.action',
											type: 'post',
											data: formData,
											processData: false,
											contentType: false,
											success: function(result) {
												var resjson = JSON.parse(result)
												if(resjson.code  === "500"){
													layer.msg("发生异常，上传失败！！")
												}else{
													var pat = resjson.data;
													$("#tupianName").val(flsts[0].name);
													$("#tupianPath").val(pat.substring(0,pat.length -1 ));
													$("#tupianPathImg").attr('src', "${base}/userfiles/xmwj/"+pat.substring(0,pat.length -1 ))
												}
											}
										});
									}
								})
													
							});	
							$('select[id=sszsk]').select2({  
								//minimumInputLength:1,
								 placeholder: "请选择",//必须设置这个allowClear:true才会生效
								allowClear:true,
								//是否允许用户输入任何值
								tags: true,
								multiple:true,
								ajax: {  
									url:  "${base}/aichat/t_ai_zsk!getzakjson.action",  
									dataType: 'json',  
									delay: 250,  
									data: function (params) {  
										return {  
											kword: params.term
											//page: params.page  //分页显示先不要，没有效果  
										};  
									},  
									processResults: function (data, params) {  
										var options = new Array();
										var txtstr = "";
										$(data).each(function(i, o) {
											txtstr = o.zskmc;
											options.push({　　　　　　　　　　//获取select2个必要的字段，id与text
												id : o.id,         
												text : txtstr
											});
										});
										return {
											results: options  //返回数据
										};
									},
									cache: true
								}
							});
							<#if entity?exists && entity?if_exists.zskSet?exists && entity?if_exists.zskSet?size gt 0>
								<#list entity?if_exists.zskSet as role>
									var option = new Option('${role.zskmc!}','${role.id}', true, true);
									$('select[id=sszsk]').append(option).trigger("change");
								</#list>
							</#if>
							
							
							$('select[id=ssjs]').select2({  
								//minimumInputLength:1,
								 placeholder: "请选择",//必须设置这个allowClear:true才会生效
								allowClear:true,
								//是否允许用户输入任何值
								tags: true,
								multiple:true,
								ajax: {  
									url:  "${base}/aichat/t_ai_znt!getrolejson.action",  
									dataType: 'json',  
									delay: 250,  
									data: function (params) {  
										return {  
											kword: params.term
											//page: params.page  //分页显示先不要，没有效果  
										};  
									},  
									processResults: function (data, params) {  
										var options = new Array();
										var txtstr = "";
										$(data).each(function(i, o) {
											txtstr = o.name;
											options.push({　　　　　　　　　　//获取select2个必要的字段，id与text
												id : o.id,         
												text : txtstr
											});
										});
										return {
											results: options  //返回数据
										};
									},
									cache: true
								}
							});
							<#if entity?exists && entity?if_exists.roleSet?exists && entity?if_exists.roleSet?size gt 0>
								<#list entity?if_exists.roleSet as role>
									var option = new Option('${role.name!}','${role.id}', true, true);
									$('select[id=ssjs]').append(option).trigger("change");
								</#list>
							</#if>
							
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