<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DeepSeek 问答对话</title>
    <script src="${base}/mainface/aichat/marked.min.js"></script>
    <script src="${base}/mainface/aichat/jquery.min.js"></script>
    <link rel="stylesheet" href="${base}/mainface/aichat/styles.css">
    <style>
    	.message.bot{
    		background-color: #f1f1f1;
    		color: #333;
    		display: inline-block;
    		
		    padding: 10px;
		    border-radius: 4px;
		    max-width: 80%;
    	}
    </style>
</head>
<body>
	<div>
		<div  id="dhls">对话历史</div>
		<div style="color:blue" onclick="location.href='${base}/aichat/t_ai_znt!aiAgent.action?id=${id!}'">新对话+</div>
		<#if dhlsList?exists>
			<#list dhlsList as obj >
				<div style="color:blue" onclick="location.href='${base}/aichat/t_ai_znt!aiAgent.action?id=${id!}&dhlsid=${obj.id!}'">${obj.biaot!}</div>
			</#list>
		</#if>
	</div>
    <div class="chat-container" style="width:80%;">
        <div class="chat-header">
            <h2>DeepSeek 问答对话</h2>
        </div>
        <div class="chat-box" style="height:600px;" id="chat-box">
            <!-- 对话内容将显示在这里 -->
            <#if yhdhLsit?exists>
            	<#list yhdhLsit as obj>
            		<div id="message_${obj.id!}" class="message <#if obj.role == '1'>bot<#else>user</#if>">
            			<#if obj.role == '1'>
            				 <div id="think_${obj.id!}" class="thinking-indicator">${obj.skgc!}</div>
					   		 <div id="answer_${obj.id!}" class="answer-content">
					   		 	${obj.zzda!}
			   		 		</div>
				   		 	<script>
				   		 		$("#answer_${obj.id!}").html(marked.parse($("#answer_${obj.id!}").html()));
			   		 		</script>
					   	</#if>
					   	<#if obj.role == '2'>
					   		<div class="message user"><p>${obj.yhtw!}</p></div>
            			</#if>
					</div>
            	</#list>
            </#if>
            
        </div>
        <div class="chat-input">
            <input type="text" id="user-input" placeholder="请输入您的问题...">
            <button id="connectBtn">发送</button>
            <button id="disconnectBtn">断开</button>
        </div>
    </div>
</body>
<script>
	$(document).ready(function() {
	    let state = {
	        isConnected: false,
	        isInThinkTag: false,
	        eventSource: null,
	        rescontent:'',
	    };
	
	    // 主题切换
	    $('#themeToggle').change(function() {
	        const isDark = $(this).prop('checked');
	        $('html').attr('data-theme', isDark ? 'dark' : 'light');
	        localStorage.setItem('theme', isDark ? 'dark' : 'light');
	    });
	
	    // 初始化主题
	    function initTheme() {
	        const savedTheme = localStorage.getItem('theme') || 'light';
	        $('#themeToggle').prop('checked', savedTheme === 'dark');
	        $('html').attr('data-theme', savedTheme);
	    }
	
 		var dhlsid = "${dhlsid!}";
 		
 		
	    // 连接SSE
	    $('#connectBtn').click(function() {
	    	let biaot = $("#user-input").val(); 
	    	if(biaot === ""){
	    		return false;
	    	}
		    if (dhlsid === "") {
		        // 假设需要获取biaot的值，比如从输入框获取
		        // 假设输入框的ID是inputField
		        insertYhdhls(biaot);
		    } else {
		        sendToAi();
		    }
		});
 		
	 	function sendToAi(){
	 		//初始化
	 		state.rescontent = "";
	 		
	    	var sseurl = "${base}/aichat/t_ai_znt!aichat2.action?id=${id!}&dhlsid=" + dhlsid;
	    	var userInput = $("#user-input").val();
	    	if(userInput === ""){
	    		return false;
	    	}
	    	appendMessage('user', userInput);
	    	
	    	sseurl += "&prompt=" + userInput;
	    	$("#user-input").val("");
	    	
	        if (state.eventSource) return;
	        
	        state.eventSource = new EventSource(sseurl);
	        state.isConnected = true;
	        updateButtonState();
	        
	        state.eventSource.onmessage = function(e) {
	            const msg = parseMessage(e.data);
	            //appendMessage(msg);
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
	                isInThinkTag = true
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
	                isInThinkTag = false
	                const endIndex = content.indexOf('</think>')
	                return {
	                    id: parsed.id,
	                    created: parsed.created,
	                    model: parsed.model,
	                    reasoning_content: false,
	                    content: content.substring(endIndex + '</think>'.length)
	                }
	            }
	            var ifwanc = parsed.choices?.[0]?.finish_reason;
	            //stop
	            console.log(ifwanc)
	            
	            // 根据状态决定内容归属
	            return {
	                id: parsed.id,
	                created: parsed.created,
	                model: parsed.model,
	                reasoning_content: isInThinkTag ? content : '',
	                content: isInThinkTag.value ? '' : content
	            }
	        } catch (e) {
	            console.error('解析JSON失败:', e)
	            return null
	        }
	    }
	    
	    // 更新推理过程
	    function updateReasoning(msg) {
            var chatbot = $("#message_" + msg.id).html();
            const chatBox = $('#chat-box');
	    	if(typeof(chatbot) === 'undefined'){
	    		 chatBox.append(
				  '<div id="message_'+ msg.id +'" class="message bot">' +
				    '<div id="think_'+ msg.id +'" class="thinking-indicator"></div>' +
				    '<div id="answer_'+ msg.id +'" class="answer-content"></div>' +
				  '</div>'
				);
	    	}
	        if (msg.reasoning_content) {
	        
	        	
	        
	        	chatbot = $("#think_" + msg.id);
		        chatbot.append(msg.content);
		        chatbot.scrollTop(chatBox[0].scrollHeight);
	            //$('.reasoning-container').append(msg.content);
	        }
	    }
	    // 更新最终答案
	    function updateAnswer(msg) {
	        if (!msg.reasoning_content && msg.content) {
	        	state.rescontent += msg.content;
	            var formatted = marked.parse(state.rescontent);
	            
	        	const chatBox = $('#chat-box');
	        	var chatbot = $("#answer_" + msg.id);
		        chatbot.html(formatted);
		        chatbot.scrollTop(chatBox[0].scrollHeight);
	            //$('.reasoning-container').append(msg.content);
	        }
	    }
	
	    // 自动滚动
	    function autoScroll() {
	        $('.messages-container, .reasoning-container, .answer-container').each(function() {
	            $(this).scrollTop($(this).prop('scrollHeight'));
	        });
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
	
	    // 初始化
	    initTheme();
	    updateButtonState();
	    
	    $('#user-input').keypress(function(e) {
	    });
		    
	    function sendMessage() {
	        const userInput = $('#user-input').val().trim();
	        if (userInput !== "") {
	            appendMessage('user', userInput);
	            $('#user-input').val('');
	            setTimeout(() => {
	                const botResponse = getBotResponse(userInput);
	                appendMessage('bot', botResponse);
	            }, 500);
	        }
	    }
	    function appendMessage(sender, message) {
	        const chatBox = $('#chat-box');
	        const messageElement = $('<div>').addClass('message').addClass(sender);
	        messageElement.html('<p>' + message + '</p>');
	        chatBox.append(messageElement);
	        chatBox.scrollTop(chatBox[0].scrollHeight);
	        
	        //用户保存问题
	    	//insertYhdhls(message);
	    }
	
	    function getBotResponse(userInput) {
	        // 这里可以替换为真实的 AI 接口调用
	        const responses = {
	            "你好": "你好！我是 DeepSeek，有什么可以帮您的吗？",
	            "你是谁": "我是 DeepSeek，一个智能问答助手。",
	            "再见": "再见！祝您有美好的一天！",
	            "默认": "抱歉，我不太明白您的问题。"
	        };
	        return responses[userInput] || responses["默认"];
	    }
	    
	    //保存历史提问
	    function insertYhdhls(biaot){
    		let parm = {};
	    	parm.zntid = "${id!}"
	    	parm.biaot = biaot;
	    	parm.loginName = "${usr.loginName!}"
	    	$.post("${base}/aichat/t_ai_znt!insertYhdhls.action",parm,function(res){
	    		dhlsid = res.resultinfo[0].entid;
	    		console.log("dhlsid====》" + dhlsid);
	    		//最终保存用户提问
	    		//insertyhtw(biaot);
	    		$("#dhls").append(
				    '<div style="color:blue" onclick="location.href=\'${base}/aichat/t_ai_znt!aiAgent.action?id=${id!}&dhlsid=' + dhlsid + '\'">' + 
				    biaot + 
				    '</div>'
				);
				 sendToAi();
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


		    
</script>
</html>