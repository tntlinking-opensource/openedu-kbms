<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8"> 
  		<meta content="width=device-width, initial-scale=1, maximum-scale=1.2, user-scalable=no" name="viewport"> 
  		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title></title>
		<link rel="stylesheet" type="text/css" href="${base}/mainface/dapingdemo/studyNewUi/css/reset.css">
		<link rel="stylesheet" type="text/css" href="${base}/mainface/dapingdemo/studyNewUi/css/aichat.css">
		
		
	    <!-- 引入editor.md相关样式 -->
		<link rel="stylesheet" href="${base}/mainface/aichat/editormd/css/editormd.preview.css" />
		
	    
	    <script src="${base}/mainface/aichat/jquery.min.js"></script>
	    
		<!-- 引入editor.md js文件 -->
			    
		<script src="${base}/mainface/aichat/editormd/editormd.js"></script>
		
		<script src="${base}/mainface/aichat/editormd/lib/marked.min.js"></script>
		
		<script src="${base}/mainface/aichat/editormd/lib/prettify.min.js"></script>
		 
		<script type="text/javascript" src="${base}/mainface/layer/layer.js"></script>
		
	</head>
	<script>
		 
				
	</script>
	<body style="overflow-x: hidden;background-color:#f4f8fd;">
		
		<div id="article-md-body" class="article-md-body"></div>
		
		<div class="aichat-main" style="padding-top:3vh;">
			<div class="aichat-main-left">
				<div class="aichat-main-left-logo">
					<div  style="display:flex;align-items: center;width: 100%;margin:1vw;">
						<div style="width: 3vw;">
							<img  style="width:100%;height:100%;" src="${base}/userfiles/xmwj/${entity.tupianPath!}" / >
						</div>
						<div style="width: 70%;margin-left:1vh;font-size: 1vw;font-weight: 600;">
							${entity.zntmc!}
						</div>
					</div>
				</div>
				<div class="aichat-main-left-tp">
					<input style="" type="button" name="search_btn" onclick="location.href='${base}/aichat/t_ai_znt!aiAgentone.action?id=${entity.id!}'" class="search_btn mfbmbtn" value=" + 新 建 对 话 " onclick="">
				</div>
				
				
				<div class="aichat-main-left-lsdh" >
					<div  class="chatgroup-time-zjdh" onclick="openchat()">
						<div class="chatgroup-time-img">
							<img style="width:1vw;" src="${base}/mainface/dapingdemo/studyNewUi/images/message.png"/>
						</div>
						<div class="chatgroup-time-text">
							最近对话
						</div>
						<div class="chatgroup-time-right" >
							<img style="width:0.8vw;" src="${base}/mainface/dapingdemo/studyNewUi/images/jiant-xia.png"/>
						</div>
					</div>
				</div>
				
				<div class="aichat-main-left-bdy" style="height:70vh;">
					<div class="chatgroup-time-content">
					    <#if dhlsList?exists>
					        <#list dhlsList as obj >
					            <div class="chatgroup-item <#if dhlsid?exists && obj.id == dhlsid>chatgroup-item-checkls</#if>" onclick="location.href='${base}/aichat/t_ai_znt!aiAgentone.action?id=${id!}&dhlsid=${obj.id!}'" style="" title="${obj.biaot!}">
					                ${obj.biaot!}
					                <div class="delete-btn" onclick="deleteItem(event, '${obj.id!}')">🗑️</div>
					            </div>
					        </#list>
					    </#if>
					</div>
					
				</div>
				<div class="aichat-main-left-fot">
					<div class="kc_sub_btn" onclick="deleteAll()">
						<img src="${base}/mainface/dapingdemo/studyNewUi/images/kc_pj.png">清除所有对话
					</div>
				</div>
			</div>
			<style>
				.chat-box-index{
					margin: 0 1vw;
				}
				.chat-box-index-1{
					display: flex;
				    justify-content: center;
				    align-items: center;
				    font-size: 2vw;
				    margin-top:10vh;
				}
				.chat-box-index-1 img{
					width:3.5vw;
					margin-right:0.8vw;
				}
				.chat-box-index-2{
					font-size: 1vw;
					text-align: center;
					margin-top: 1.5vh;
				}
				.chat-box-index-3{
					font-size: 0.8vw;
					text-align: center;
					margin-top: 1.5vh;
					line-height: 3vh;
				}
				.chat-box-index-4{
					margin-top: 1.5vh;
				}
				.chat-box-index-4 ul{
					list-style: none;
					padding: 0 1vw;
					display: flex;
					justify-content: center;
				}
				.chat-box-index-4 ul li{
					width: 13vw;
					height: 8vh;
					float: left;
					display: inline-block;
				    cursor: pointer;
				    margin: 2vh 1.5vw;
				    background: #FFFFFF;
				    box-shadow: 0px 3px 15px rgba(0, 0, 0, 0.14);
				    -moz-transition: all 0.4s ease;
				    -ms-transition: all 0.4s ease;
				    -webkit-transition: all 0.4s ease;
				    transition: all 0.4s ease;
				    position: relative;
				    padding: 0.5vw;
				}
				.chat-chj-tit1{
					font-size: 1vw;
					text-align: center;
				}
				.chat-chj-tit2{
					font-size: 0.8vw;
					margin-top: 0.8vh;
					color: #8b8b8b;
				}
			</style>
			<div class="aichat-main-right">
				<div class="chat-box" style="height: 85vh;">
				
					<div class="chat-box-index">
						
						<#if dhlsid?exists && dhlsid != "">
							<#if yhdhLsit?exists>
            					<#list yhdhLsit as obj>
            						<#if obj.yhtw?exists && obj.yhtw != "">
										<div class="message user">
											<div class="div_p">${obj.yhtw!}</div>
										</div>
									</#if>
									<#if obj.zzda?exists && obj.zzda != "">
										<div class="message bot">
											<div class="msgbot-icon">
												<img src="${base}/userfiles/xmwj/${entity.tupianPath!}" style="width:1.5vw;">
											</div>
											<#if obj.skgc?exists && obj.skgc != "" && obj.skgc?trim != "">
												<div class="bot-think">
													<div class="div_p" id="think_${obj.id!}">${obj.skgc!}</div>
												</div>
												<script>
													$("#think_${obj.id!}").html(marked.parse($("#think_${obj.id!}").html()));
												</script>
											</#if>
											<#if obj.zzda?exists && obj.zzda != "">
												<div class="bot-reslut div_p" id="answer_${obj.id!}">${obj.zzda!}</div>
												<script>
													var htmlstr = $("#answer_${obj.id!}").html();
												    $("#answer_${obj.id!}").html("");
													var editor = editormd.markdownToHTML("answer_${obj.id!}", {
													  markdown: htmlstr, //待渲染的markdown文本字符串
													  path : '${base}/mainface/aichat/editormd/lib/',
													  tocContainer: 'article-md-body',	//指定目录容器的id
													    htmlDecode: "style,script,iframe", // 允许解码 HTML 标签
													    tocm: true, // 启用目录
													    emoji: true, // 启用表情符号
													    taskList: true, // 启用任务列表
													    tex: true, // 启用 TeX 公式
													})
								   		 		</script>
											</#if>
										</div>
										
									</#if>
								</#list>
							</#if>
						<#else>
							<div class="znt_clas">
								<div class="chat-box-index-1" style="margin-top:25vh;">
									<img src="${base}/userfiles/xmwj/${entity.tupianPath!}"  style="">
									${entity.zntmc!}
								</div>
								<div class="chat-box-index-2">
									Hi，欢迎使用${entity.zntmc!}
								</div>
								<div class="chat-box-index-3" style="display: flex;justify-content: center;">
									<div style="width:50%;">
										${entity?if_exists.jies?if_exists.replaceAll("\n", "<br/>")!}
									</div>
								</div>
								<div class="chat-box-index-4" style="display: flex;justify-content: center;margin-top:5vh;">
									<!--<#--
									<div class="container">
									    <#if zntList?exists && zntList?size gt 0>
										    <#list zntList as obj>
										    	<#if obj_index < 9>
												    <div class="card" onclick="window.open('${base}/aichat/t_ai_znt!aiAgentone.action?id=${obj.id!}')">
												      <div class="card-icon" >
												      	<img  style="width: 100%;height: 100%;" src="${base}/userfiles/xmwj/${obj.tupianPath!}" />
												      </div>
												      <div class="card-content">
												        <h3>${obj.zntmc!}</h3>
												        <p>${obj.fbt!}</p>
												      </div>
												    </div>
											    </#if>
										  </#list>
									  </#if>
								  </div>
								  -->-->
								</div>
							</div>
						</#if>
						
					</div>
				</div>
				<div class="chat-input">
					<input class="user-input" id="user-input" maxlength="${entity.usermaxTokens!}" placeholder="请输入您的问题..." />
					<button class="send-btn" id="connectBtn" >发送</button>
					<!--
						<button class="send-btn" style="background-color: #eee;color: #000;">断开</button>
					-->
				</div>
			</div>
		</div>
		
		<div class="aichat-foot">
			&nbsp;
		</div>
	</body>
		
	<script>
		var dhlsid = "${dhlsid!}";
		$(document).ready(function() {
		
		    let state = {
		        isConnected: false,
		        isInThinkTag: false,
		        eventSource: null,
		        rescontent:'',
		        thinkcontent:''
		    };
		    
		    $('#connectBtn').click(function() {
		        handleAction();
		    });
		    // 连接SSE
		
		    // 绑定回车键事件
		    $('#user-input').on('keydown', function(event) {
		        if (event.key === 'Enter' || event.keyCode === 13) {
		            event.preventDefault(); // 阻止默认行为（如换行）
		            handleAction(); // 执行相同的逻辑
		        }
		    });
		
		    // 提取公共逻辑
		    function handleAction() {
		        let biaot = $("#user-input").val();
		        if (biaot === "") {
		            return false; // 如果输入为空，不执行任何操作
		        }
		        if (dhlsid === "") {
		            insertYhdhls(biaot); // 调用插入函数
		        } else {
		            sendToAi(); // 调用发送函数
		        }
		    }
	 		
	 		
	 		
		 	function sendToAi(){
		 		//初始化
		 		state.rescontent = "";
		 		
		    	var sseurl = "${base}/aichat/t_ai_znt!aichat.action?id=${id!}&dhlsid=" + dhlsid;
		    	var userInput = $("#user-input").val();
		    	if(userInput === ""){
		    		return false;
		    	}
		    	
		    	appendUserMessage(userInput);
		    	
		    	sseurl += "&prompt=" + encodeURIComponent(userInput);
		    	$("#user-input").val("");
		    	
		        if (state.eventSource) return;
		        
		        state.eventSource = new EventSource(sseurl);
		        state.isConnected = true;
		        updateButtonState();
		        
		        state.eventSource.onmessage = function(e) {
		            //const msg = parseMessage(e.data);
		            const msg = JSON.parse(e.data);
					//删除思考
					$(".botThink").remove();
		            
		            //更新思考过程
		            updateReasoning(msg);
		            //更新结果
		            updateAnswer(msg);
		            autoScroll();
		        };
		
		        state.eventSource.onerror = function() {
		            disconnect();
		        };
		 	}
		 	
		 	
		     function parseMessage (data){
		    	
		        try {
		            const parsed = JSON.parse(data)
		            // 检查是否直接返回了 reasoning_content
		            const directReasoning = parsed.choices?.[0]?.delta?.reasoning_content
		            if (directReasoning) {
		                return {
		                    id: parsed.id,
		                    created: parsed.created,
		                    model: parsed.model,
		                    reasoning_content: directReasoning,
		                    content: parsed.choices?.[0]?.delta?.content || ''
		                }
		            }
		            var content = parsed.choices?.[0]?.delta?.content || ''
		            
		            // 处理 think 标签包裹的情况
		            if (content.includes('<think>')) {
		                state.isInThinkTag = true
		                const startIndex = content.indexOf('<think>') + '<think>'.length;
		                var tmp = {
		                    id: parsed.id,
		                    created: parsed.created,
		                    model: parsed.model,
		                    reasoning_content: true,
		                    content: content.substring(0, content.indexOf('<think>'))
		                };
		                return  tmp;
		            }
		            
		            if (content.includes('</think>')) {
		                state.isInThinkTag = false
		                const endIndex = content.indexOf('</think>')
		                return {
		                    id: parsed.id,
		                    created: parsed.created,
		                    model: parsed.model,
		                    reasoning_content: false,
		                    content: content.substring(endIndex + '</think>'.length)
		                }
		            }
		            
		            // 根据状态决定内容归属
		            return {
		                id: parsed.id,
		                created: parsed.created,
		                model: parsed.model,
		                reasoning_content: state.isInThinkTag ? content : '',
		                content: state.isInThinkTag ? '' : content
		            }
		        } catch (e) {
		            console.error('解析JSON失败:', e)
		            return null
		        }
		    }
		    
		    // 更新推理过程
		    function updateReasoning(msg) {
		    	
		    	if(msg.id){
		    		var chatbot = $("#message_" + msg.id).html();
		            const chatBox = $('.chat-box-index');
			    	if(typeof(chatbot) === 'undefined'){
			    		 chatBox.append(
						  '<div id="message_'+ msg.id +'" class="message bot">' +
						  	'<div class="msgbot-icon"><img src="${base}/userfiles/xmwj/${entity.tupianPath!}" style="width:1.5vw;"></div>'+
						    '<div  class="bot-think" style="display: none;">'+ 
						    	'<div id="think_'+ msg.id +'" class="div_p"> </div>'+
						    '</div>' +
						    '<div id="answer_'+ msg.id +'" style="display: none;" class="bot-reslut div_p"></div>' +
						  '</div>'
						);
			    	}
			        if (msg.thinkContent && $.trim(msg.thinkContent) !== "") {
			        	chatbot = $("#think_" + msg.id);
			        	state.thinkcontent += msg.thinkContent;
			        	
				        //chatbot.append(msg.content);
				        var formatted = marked.parse(state.thinkcontent);
				        chatbot.html(formatted);
				        chatbot.parent().show();
			            
			            //$('.reasoning-container').append(msg.content);
			        }
		    	}
		    }
		    // 更新最终答案
		    function updateAnswer(msg) {
			    if (msg.answerContent && msg.answerContent !== "") {
			        // 累积答案内容
			        state.rescontent += msg.answerContent;
			
			        // 解析 Markdown 内容
			        //var formatted = marked.parse(state.rescontent);
			
			        // 找到对应的聊天框
			        var chatbot = $("#answer_" + msg.id);
			
			        // 更新聊天框内容
			        chatbot.html("");
			        
					var editor = editormd.markdownToHTML("answer_" + msg.id, {
					  markdown: state.rescontent, //待渲染的markdown文本字符串
					  tocContainer: 'article-md-body',	//指定目录容器的id
				      htmlDecode: "style,script,iframe", // 允许解码 HTML 标签
				      tocm: true, // 启用目录
				      emoji: true, // 启用表情符号
				      taskList: true, // 启用任务列表
				      tex: true, // 启用 TeX 公式
					})
			        // 显示聊天框
			        chatbot.show();
			    }
			}
					 	 
		    // 自动滚动
		    function autoScroll() {
		        const objbot = $('.chat-box');
		        objbot.scrollTop(objbot[0].scrollHeight);
		    }
		
		    // 断开连接
		    $('#disconnectBtn').click(disconnect);
		    	function disconnect() {
		        if (state.eventSource) {
		            state.eventSource.close();
		            state.eventSource = null;
		        }
		        state.isConnected = false;
		        updateButtonState();
		    }
		
		    // 清空内容
		    $('#clearBtn').click(function() {
		        $('.message-content, .reasoning-container, .answer-container').empty();
		    });
		
		    // 更新按钮状态
		    function updateButtonState() {
		        $('#connectBtn').prop('disabled', state.isConnected);
		        $('#disconnectBtn').prop('disabled', !state.isConnected);
		    }
		
		    updateButtonState();
		    
		    $('#user-input').keypress(function(e) {
		    });
			    
		 
		    function appendUserMessage( message) {
		        const chatBox = $('.chat-box-index');
		        const messageElement = $('<div>').addClass('message').addClass('user');
		        $(".znt_clas").remove();
		        messageElement.html('<div class="div_p">' + message + '</div>');
		        chatBox.append(messageElement);
		        
		        var html = "";
		        html+='<div class="message bot botThink">';
				html+='	<div class="msgbot-icon">';
				html+='		<img src="${base}/userfiles/xmwj/${entity.tupianPath!}" style="width:1.5vw;">';
				html+='	</div>';
				html+='	<div style="margin-left: 2vw;display:flex;align-items: center;">';
				html+='		<div style="">思考中...</div>';
				html+='		<div style="width:1vw; height: 1vw;margin-left:1vh;" class="rotating-image">';
				html+='			<svg  viewBox="0 0 36 36" version="1.1" xmlns="http://www.w3.org/2000/svg" aria-hidden="true" data-icon="spin"><defs><linearGradient x1="0%" y1="100%" x2="100%" y2="100%" id="linearGradient-1"><stop stop-color="currentColor" stop-opacity="0" offset="0%"></stop><stop stop-color="currentColor" stop-opacity="0.50" offset="39.9430698%"></stop><stop stop-color="currentColor" offset="100%"></stop></linearGradient></defs><g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd"><rect fill-opacity="0.01" fill="none" x="0" y="0" width="36" height="36"></rect><path d="M34,18 C34,9.163444 26.836556,2 18,2 C11.6597233,2 6.18078805,5.68784135 3.59122325,11.0354951" stroke="url(#linearGradient-1)" stroke-width="4" stroke-linecap="round"></path></g></svg>';
				html+='		</div>';
				html+='	</div>';
				html+='</div>';
		        chatBox.append(html);
		        autoScroll();
		    }
		
		    //保存历史提问
		    function insertYhdhls(biaot){
	    		let parm = {};
		    	parm.zntid = "${id!}"
		    	parm.biaot = biaot;
		    	parm.loginName = "${usr.loginName!}"
		    	$.post("${base}/aichat/t_ai_znt!insertYhdhls.action",parm,function(res){
		    		dhlsid = res.resultinfo[0].entid;
		    		$(".chatgroup-time-content").prepend(
					    '<div class="chatgroup-item chatgroup-item-checkls" onclick="location.href=\'${base}/aichat/t_ai_znt!aiAgentone.action?id=${id!}&dhlsid=' + dhlsid + '\'">' +
					    biaot +
					    '<div class="delete-btn" onclick="deleteItem(event, \'' + dhlsid + '\')">🗑️</div>' +
					    '</div>'
					);
					 sendToAi();
					
					const targetElement = document.querySelector('.chatgroup-item-checkls'); // 获取第一个匹配的元素
				    if (targetElement) {
				        targetElement.scrollIntoView({
				            behavior: 'auto', // 平滑滚动
				            block: 'center'      // 滚动到元素的顶部
				        });
				    }
		    	})
		    }
		    
	    	//保存用户提问
		    /**
	    	function insertyhtw(biaot){
	    		let parm = {};
		    	parm.zntid = "${id!}"
		    	parm.dhlsid = dhlsid;
		    	parm.biaot = biaot;
		    	parm.loginName = "${usr.loginName!}"
		    	$.post("${base}/aichat/app_chat!insertyhtw.action",parm,function(res){
		    		console.log( res);
		    	})
	    	}
	    	**/
	    	//保存ai回答
		});
	
	
			    
		$(".header-menu-item").click(function () {
	        $(this).addClass("act").siblings().removeClass("act");
	    });
	    
	 	  // 页面加载时滚动到最下方
        window.onload = function() {
            const chatBox = document.querySelector('.chat-box'); 
            chatBox.scrollTop = chatBox.scrollHeight;
        };
		window.addEventListener('load', function() {
		    const targetElement = document.querySelector('.chatgroup-item-checkls'); // 获取第一个匹配的元素
		    if (targetElement) {
		        targetElement.scrollIntoView({
		            behavior: 'auto', // 平滑滚动
		            block: 'center'      // 滚动到元素的顶部
		        });
		    }
		});
		
		
		function deleteItem(event, id) {
		    event.stopPropagation(); // 阻止事件冒泡，避免触发父元素的点击事件
			var hhid = dhlsid;
		    if (confirm("确定要删除此项吗？")) {
		       $.post("${base}/aichat/t_ai_yhdhls!delete.action",{checks:id},function(res){
		       		if(id === hhid){
		       			dhlsid = "";
		       		}
					layer.msg(res, {
					    time: 300
					}, function () {
						location.href="${base}/aichat/t_ai_znt!aiAgentone.action?id=${entity.id!}&dhlsid=" + dhlsid;
					});
		       		
		       })
		    }
		}
		//清空所有
		function deleteAll(){
			if (confirm("确定要清除所有对话？")) {
				var zntid ="${entity.id!}";
				$.post("${base}/aichat/t_ai_yhdhls!delAll.action",{zntid:zntid},function(res){
		       		layer.msg(res, {
					    time: 300
					}, function () {
						location.href="${base}/aichat/t_ai_znt!aiAgentone.action?id=${entity.id!}";
					});
		       })
			}
		}
		function openchat() {
		    var $element = $(".chatgroup-time-content");
		    if ($element.is(":hidden")) {
		        $element.show();
		        $(".chatgroup-time-right").find("img").attr("src", "${base}/mainface/dapingdemo/studyNewUi/images/jiant-xia.png");
		    } else {
		        $element.hide();
		        $(".chatgroup-time-right").find("img").attr("src", "${base}/mainface/dapingdemo/studyNewUi/images/jiant-you.png");
		    }
		}
	</script>
</html>