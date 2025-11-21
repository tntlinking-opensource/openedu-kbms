document.write("<div id='fullbg'></div>");
document.write("<div id='dialog'>");
document.write("<div style='text-align:right;width:100%;padding-top:6px;'>");
document.write("<a style='margin-right:12px' href='#' onclick='closeBg();'>关闭</a>");
document.write("</div>");
document.write("<div id='dialog_content' style='float:left;width:100%;'></div>");
document.write("</div>");
	

function showBg(ct,contentid){
	var bH=$("body").height();
	var bW=$("body").width()+16;
	$("#fullbg").css({width:bW,height:bH,display:"block"});
	$("#"+ct).css({top:'35%',left:'40%',display:"block"});
	$('#dialog_content').empty().append($("#"+contentid).html());
	$(window).scroll(function(){resetBg()});
	$(window).resize(function(){resetBg()});
}
function resetBg(){
	var fullbg=$("#fullbg").css("display");
	if(fullbg=="block"){
		var bH2=$("body").height();
		var bW2=$("body").width()+16;
		$("#fullbg").css({width:bW2,height:bH2});
		$("#dialog").css({top:'35%',left:'40%'});
	}
}
function closeBg(){
	$("#fullbg").css("display","none");
	$("#dialog").css("display","none");
}

	
