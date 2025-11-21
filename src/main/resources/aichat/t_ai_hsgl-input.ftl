<#include '/macro/crud-metro-page3-nowebpst.ftl' >

<#if id?exists>
	<#assign extPosition='自定义函数维护'/>
<#else>
	<#assign extPosition='自定义函数维护'/>
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
						<form id="save_inputForm" class="form-horizontal  " action="${base}/aichat/t_ai_hsgl!save.action" method="post">
							<@s.token />
							<#if id?exists>
								<input type="hidden" name="id" value="${id}"/>
							</#if>
							<h3 class="form-section">函数信息</h3>
							<!-- 在这里编写输入的元素 -->
							<!--
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
							-->
							<div class="control-group">
								<label class="control-label"><b>知识库:</b><span class="required">*</span></label>
								<div class="controls">
									<select id="sszsk" name="sszsk" class="span10 m-wrap" >
									</select>
								</div>
							</div>
							
							<div class="control-group">
								<label class="control-label"><b>所属角色:</b><span class="required"></span></label>
								<div class="controls">
									<select id="ssjs" name="ssjs" class="span10 m-wrap" >
									</select>
									<span class="text">注意：不选择则所有人皆可使用该函数！</span>
								</div>
							</div>
							
							
							<div class="row-fluid">
								<div class="span6">
									<div class="control-group">
										<label class="control-label"><b>函数名称:</b><span class="required">*</span></label>
										<div class="controls">
											<input type="text" class="span10 m-wrap" id="name" name="name" value="${name!}" maxlength="100" />
											<span class="text" style="color:red;"> 
												<p>注：必须唯一。</p>
												<p>如：get_current_weather</p>
												<p>获取当前天气</p>
											</span>
										</div>
									</div>
								</div>
								<div class="span6">
									<div class="control-group">
										<label class="control-label"><b>函数描述:</b><span class="required">*</span></label>
										<div class="controls">
											<input type="text" class="span10 m-wrap" id="description" name="description" value="${description!}" maxlength="100" />
											<span class="text" style="color:red;"> 
												<p>注：大模型会按照函数描述进行命中。</p>
												<p>如：Get weather of an location, the user shoud supply a location first</p>
												<p>获取一个地点的天气，用户应该先提供一个地点。</p>
											</span>
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span12">
									<div class="control-group">
										<label class="control-label"><b>回调api:</b><span class="required">*</span></label>
										<div class="controls">
											<input type="text" class="span10 m-wrap" id="apiurl" name="apiurl" value="${apiurl!}" maxlength="500" />
											<span class="text" style="color:red;"> 
												<p>如：http://demo1.teacu.cn/ausso/sjdp/school/getZdmj。</p>
												<p>这个是获取天气情况的例子，传入一个location(位置)</p>
												<p>注：自定义接口返回值为：{"code":"200","resultInfo":"上海天气为20°C"}</p>
											</span>
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span12">
									<div class="control-group">
										<label class="control-label"><b>回调信息模板:</b><span class="required">*</span></label>
										<div class="controls">
											<textarea  class="span10 m-wrap"  rows="3" cols="80" name="callbkmb"  maxlength="200">${callbkmb?if_exists}</textarea>	
											<span class="text" style="color:red;"> 
												<p>例如：{{location}}当前的气温为{{data}}°C</p>
												<p>注意：data写死，为回调api的返回值</p>
											</span>
										</div>
									</div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span12">
									<div class="control-group">
										<label class="control-label"><b>备注:</b><span class="required"></span></label>
										<div class="controls">
											<input type="text" class="span10 m-wrap" id="beiz" name="beiz" value="${beiz!}" maxlength="300" />
										</div>
									</div>
								</div>
							</div>
							<h3 class="form-section">参数信息</h3>
							<div class="row-fluid">
								<table style="width:100%;" class="table table-bordered" border="1" id="reptTable" >
									<thead style="">
										
										<tr style="height: 50px;color:#666;background-color: #eeeeee">
											<th style="vertical-align: middle;width: 10%;text-align: center;">参数名称</th>
											<th style="vertical-align: middle;width: 10%;text-align: center;">类型</th>  
											<th style="vertical-align: middle;width: 10%;text-align: center;">参数描述</th>  
											<th style="vertical-align: middle;width: 10%;text-align: center;">是否必填参数</th>  
											<th style="vertical-align: middle;width: 5%;text-align: center;" ><a href="javascript:;"  onclick="addtd()">添加+</a></th>  
										</tr>
										<tr style="height: 50px;color:#666;">
											<th style="vertical-align: middle;width: 10%;text-align: center;">location（位置）</th>
											<th style="vertical-align: middle;width: 10%;text-align: center;">String</th>  
											<th style="vertical-align: middle;width: 10%;text-align: center;">The city name（城市名称）</th>  
											<th style="vertical-align: middle;width: 10%;text-align: center;">是</th>  
											<th style="vertical-align: middle;width: 10%;text-align: center;"></th>  
										</tr>
									</thead>
									<tbody>
										<#if pzList?exists && pzList?size gt 0>
											<#list pzList as obj>
											 <tr  style="font-size:10px"  tit="'+index+'">
												<td>
													<input type="text"  name="cspz[${obj_index}].name" value="${obj.name!}"  maxlength="100" style="width:90%" />
												</td>		
												<td>
													<input type="text" name="cspz[${obj_index}].type" value="${obj.type!}"  maxlength="100" style="width:90%" />
												</td>		
												<td>
													<input type="text"  name="cspz[${obj_index}].description"  value="${obj.description!}"   maxlength="300"  style="width:90%" />
												</td>
												<td>
													<span class="text"> 
														&nbsp;&nbsp;&nbsp;&nbsp; 
														<label class="radio"> 
															<input type="radio" name="cspz[${obj_index}].required"   value="1"  <#if obj.required == '1' >checked</#if>  >是 
														</label> 
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
														<label class="radio"> 
															<input type="radio" name="cspz[${obj_index}].required"  value="0" <#if obj.required == '0' >checked</#if> >否 
														</label> 
													</span> 
												</td>		
												<td style="text-align: center;" class="dels">
													<a href="javascript:;" onclick="addtd(this)">+</a>&nbsp;&nbsp;&nbsp;
													<a href="javascript:;" onclick="rmvtd(this)">-</a>
												</td>	
											</tr>
											</#list>
										</#if>
									</tbody>
								</table>
							</div>
							<div class="control-group">
								<label class="control-label"><b>是否有效:</b><span class="required">*</span></label>
								<div class="controls">
									<span class="text">
										&nbsp;&nbsp;&nbsp;&nbsp;
										<label class="radio">
											<input type="radio" name="sfyx"   value="1" <#if sfyx?exists && sfyx=='1'>checked<#else>checked</#if> >是
										</label>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
										"sszsk":{required:true},	
										"name":{required:true}	,
										"description":{required:true},	
										"apiurl":{required:true}
									}
								});						
							});	
							
							
							function addtd(obj){
								var index=$("#reptTable tr:last").attr('tit');
								if(typeof(index) == 'undefined'  || index == 'NaN'){
									index="0";
								}else{
									index=Number(index)+1;
								}
								var htmlstr=' <tr  style="font-size:10px"  tit="'+index+'">'
								+'	<td>'
								+'		<input type="text"  name="cspz['+index+'].name" value=""  maxlength="100" style="width:90%" />'
								+'	</td>		'
								+'	<td>'
								+'		<input type="text" name="cspz['+index+'].type" value=""  maxlength="100" style="width:90%" />'
								+'	</td>		'
								+'	<td>'
								+'		<input type="text"  name="cspz['+index+'].description"    maxlength="300"  style="width:90%" />'
								+'	</td>		'
								+'	<td>' 
								+'		<span class="text"> '
								+'			&nbsp;&nbsp;&nbsp;&nbsp;  '
								+'			<label class="radio"> '
								+'				<input type="radio" name="cspz['+index+'].required"   value="1"    >是 '
								+'			</label> '
								+'			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; '
								+'			<label class="radio"> '
								+'				<input type="radio" name="cspz['+index+'].required"  value="0" checked >否 '
								+'			</label> '
								+'		</span> '
								+'	</td>		'
								 
								+'	<td style="text-align: center;" class="dels">'
								+'		<a href="javascript:;" onclick="addtd(this)">+</a>&nbsp;&nbsp;&nbsp;'
								+'		<a href="javascript:;" onclick="rmvtd(this)">-</a>'
								+'	</td>	'
								+'</tr>'
								if(typeof(obj) != 'undefined'){
									$(obj).parent().parent().after(htmlstr);
								}else{
									$("#reptTable tbody").append(htmlstr);
								} 
								//checkbox init
							   	var test = $("input[type=checkbox]:not(.toggle), input[type=radio]:not(.toggle, .star)");
								if (test.size() > 0) {
								    test.each(function () {
									    if ($(this).parents(".checker").size() == 0) {
									        $(this).show();
									        $(this).uniform();
									    }
									});
								}
								//checkbox init
						        
						        cxpx();
							}
							
							
							function rmvtd(obj)
							{
								//通过this找到父级元素节点
						        var tr = obj.parentNode.parentNode;
						        //找到表格
						        var tbody = tr.parentNode;
						        //删除行
						    	var hs= $("#reptTable").find("tr").length;
					           	tbody.removeChild(tr);
								cxpx();
							}
							var reg = /[0-9]/g; 
							function cxpx(){
								//重新排顺序号
								var index = 0;
								$("#reptTable tbody").find("tr").each(function(){
									$(this).attr('tit',index);
									$(this).find("input").each(function(){
										var old_name = $(this).attr('name');
										$(this).attr('name',"cspz["+index+"]." + old_name.split(".")[1]);
									})
									index++;
								})
							}
							
							
							
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
									url:  "${base}/onetable/tonetable_bdflgl!getrolejson.action",  
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
							<#if roleSet?exists && roleSet?size gt 0>
								<#list roleSet as role>
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