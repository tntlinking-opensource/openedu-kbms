/* ajax请求，根据班级专业获取班级列表
 * xzbjId : 判断返回的查询条件，如果是此Id,则下拉框就选中此项
 * #sydckhui : 隐藏域，获取班级的id集合
 * post提交的路径始终为班级。
 * #xzbj : 为班级下拉框的id 
 */
function getClassList(xzbjId) {
	var zyIds = $("#sydckhui").val();
	var stNj = $("#stNj").val();
	$.post("${base}/xtgl/tjbxx_xzbj!getBjByZy.action", {
		zyIds : zyIds, stNj : stNj
	}, function(data) {
		var bjSel = $("#xzbjs");
		bjSel.empty();
		var bjs = eval(data);
		if (bjs != "undefine" && bjs != null) {
			//美观作用
			var op = $("<option>");
			op.text("==全部==");
			op.val("");
			op.appendTo(bjSel);
			for ( var i = 0; i < bjs.length; i++) {
				//下面的才是正真的数据列
				var op = $("<option>");
				op.text(bjs[i].mc);
				op.val(bjs[i].id);
				if (bjs[i].id == xzbjId) {
					op.attr("selected", "true");
				}
				op.appendTo(bjSel);
			}
		}

	});
}

//将选中的具体专业赋值给隐藏域
function getZyXx(){
	var obj = mini.get("sydck");
	$("#sydckhui").val(obj.getValue());
}	

//将选中的具体班级赋值给隐藏域
function getBjXx(){
	var xzbjId = $("#xzbjs").val();
	$("#xzbjId").val(xzbjId);
}

function loadZyXx(obj,nj){
	 $.ajax({
	    type : "POST",
	    data : "{}",
	    url : '${base}/eduplan/edu_plan!treeMajorList.action?studClass='+nj,
	    dataType : "json",
	    contentType : "application/json",
	    success : function(data) {
	      obj.loadList(data, "id", "pid");
	      //obj.setData(data);
	      if ($("#sydckhui").val() != "") {
	        obj.setValue($("#sydckhui").val());
          }
	    },
	    error : function(XMLHttpRequest, textStatus,errorThrown) {
	      window.alert("服务器未成功加载该年级下专业:" + errorThrown);
	    }
	 });
}
//专业的值发生变化时触发事件
function onValueChanged(e) {
	var obj = e.sender;
	$("#sydckhui").val(obj.getValue());
	getClassList("");
}
//点击专业的X清空专业时触发事件
function onCloseClick(e) {
    var obj = e.sender;
    obj.setValue("");
    obj.setText("请选择年级...");
    $("#sydckhui").val("");
    getClassList("");
}
//点击重置按钮可调用方法，清空专业和班级
function resetZyXx() {
    var obj = mini.get("sydck");
    obj.setData("[]");
    obj.setText("请选择年级...");
    $("#sydckhui").val("");
    getClassList("");
}

//判断是否选中年级和专业,没有则需要进行年级和专业的非空验证   待删除
function checkNjAndZyNull(){
	var nj = $("#stNjdm").val();//年级
	var zyIds = $("#sydckhui").val();//专业
	if(nj == "" || zyIds == ""){
		alert("请选择年级和专业...");
		return;
	}
}
//待删除
function chkNjAndZyNull(){
	var nj = $("#stNj").val();//年级
	var zyIds = $("#sydckhui").val();//专业
	if(nj == "" || zyIds == ""){
		alert("请选择年级和专业...");
		return;
	}
}

/* ajax请求，根据班级专业获取班级列表
 * xzbjId : 判断返回的查询条件，如果是此Id,则下拉框就选中此项
 * post提交的路径始终为班级。
 * #xzbjs : 为班级下拉框的id 
 * zyid : 专业下拉框的id
 */
function getClassByZy(xzbj) {
	 var t = $('#zyid').combotree('tree'); // 得到树对象  
     var n = t.tree('getSelected'); // 得到选择的节点  
	 if(n!=null&&n!="undefine"){
		  var zyidValue =n.id;
			if(zyidValue!=""){
				   $.post("${base}/xtgl/tjbxx_xzbj!getBjByZy.action", {
						zyIds : zyidValue
					}, function(data) {
						var bjSel = $("#xzbjs");
						bjSel.empty();
						var bjs = eval("("+data+")");
						if (bjs != "undefine" && bjs != null) {
							//美观作用
							var op = $("<option>");
							op.text("==全部==");
							op.val("");
							op.appendTo(bjSel);
							
							for ( var i = 0; i < bjs.length; i++) {
								//下面的才是正真的数据列
								var op = $("<option>");
								op.text(bjs[i].mc);
								op.val(bjs[i].id);
								if (bjs[i].id == xzbj) {
									op.attr("selected", "true");
								}
								op.appendTo(bjSel);
							}
								 
						}
				
				 });
			}else{
				$("#xzbjs").empty(); 
					
			    var op = $("<option>");
			    op.text("==全部==");
			    op.val("");
			    $("#xzbjs").append(op);
			}
	  }
	
	
}

//根据选择的专业带出专业下面的班级。
 function coverBj(zySelect){
	   var strId=new Array();
       var count=$("#zy option").length;//选择专业的个数
   var selectValue=$("#bj").val();//获取选择的班级。
   if(count>0){
	    for (var i=0;i<count;i++){
            var optValue=$("#zy").get(0).options[i].value;
            if(optValue!=""){
	           strId.push(optValue);
            }
        }
	}
	//zySelect等于0，表示专业有变化。
	if(strId.length>0&&zySelect==0){
	
	   var str=strId.toLocaleString();
	   $.post(
           "${base}/xtgl/tjbxx_xzbj!getBjJosnByZy.action",
            {zyIds:str},
            function(data){
            
               var bjSelect = $("#bj");
               bjSelect.empty();
               
                var op = $("<option>");
			    op.text("==全部==");
			    op.val("");
						 
				bjSelect.append(op);
                
               if(data!=""){
                    var obj = eval("("+data+")");
                   	
                   	if(obj.strList!="undefine"&&obj.strList.length>0){
                   	
						 $.each(obj.strList,function(i){
						 
						    var opt = $("<option>");
							opt.text(this.mc);
							opt.val(this.id);
							
                            bjSelect.append(opt);
                         });
                        bjSelect.val(selectValue);//班级下拉框变化之后，将之前选中的值赋给下拉框，让其选中。
                   	}
                  
               }
       });	
	}else if(strId.length==0&&zySelect==0){//zySelect等于0，表示专业有变化。strId.length==0表示没有选择任何专业。
	
	     $("#bj").empty();
	     
         var op = $("<option>");
		 op.text("==全部==");
		 op.val("");
		 
		 $("#bj").append(op);
	}
}

