document.write("<div >");
document.write("<p id='loading-one'>");
document.write("页面载入中,请稍候...!");
document.write("</p>");
document.write("</div>");

function wait_show(){
	$("#loading-one").show();
}
function wait_fade(){
	$('#loading-one').fadeOut('slow'); 
}
