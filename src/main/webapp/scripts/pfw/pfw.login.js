function jsonpLogin(){
	$.ajax({
		url:'${base}/login.action',
		type: 'POST',
		dataType: 'html',
		data:{j_username:$('#j_username').val(),j_password:$('#j_password').val()},
		cache:false, 
		async:true,
		beforeSend:function(){
			$("#loading-one").show();
		}, 
		complete: function() {
			$('#loading-one').fadeOut('slow');  
		},  
		success:function(html){
            var pop=new Popup({ contentType:2,isReloadOnClose:false,width:340,height:300});
            pop.setContent("contentHtml",html);
            pop.setContent("title","Login");
            pop.build();
            pop.show();
		},
		error:function(t){}
	});
}