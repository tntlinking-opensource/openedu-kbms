
//文件上传
	function uploadFile(fileid,filesize,filelength,wjlx,iptname,iptpath){
			// 选中文件后
		var flag = true;
		var flsts = $("#"+fileid).get(0).files;
		
		if($("#"+fileid).get(0).files.length > filelength){
			layer.msg("最多可上传"+filelength+"个文件！")
			flag = false;
			return false;
		}
		
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
			    layer.msg('附件的文件格式需为：'+wjlx);
			    flag = false;
			    return;
			}
		}
		
		//文件大小校验
		var fileSize = 0;
		for(var i=0;i<flsts.length;i++){
			fileSize = Number(fileSize) + Number((flsts[i].size/1024/1024));
		}
		if(fileSize > filesize ){
			layer.msg("单次上传文件不能超过"+filesize+"MB！")
			flag = false;
			return false;
		}
		
		
		if(flsts && flag){
			
			var formData = new FormData(); 
			var fileNmaeList = "";
			for(var i=0;i<flsts.length;i++){
				fileNmaeList += flsts[i].name+",";
		      	formData.append('fileList', flsts[i]);
			}
			
			fileNmaeList = fileNmaeList.substring(0,fileNmaeList.length-1);
			formData.append("fileNameList", fileNmaeList);
			$.ajax({
				url: window.localStorage["base"] + '/pblic/file_util!tjfjsc.action',
				type: 'post',
				data: formData,
				processData: false,
				contentType: false,
				success: function(result) {
					var resjson = JSON.parse(result)
					if(resjson.code  === "500"){
						layer.msg("发生异常，上传失败！！")
					}else{
						var tmpPath = resjson.data.split(",");						
						
						var tab = $("#ysc"+fileid).find("table").html();
						var tmp = '';
						if(tab == "" || tab == null || typeof(tab) == 'undefined'){
							tmp = '<table   style="width:60%" class="table table-striped table-bordered">';
						}
						
						for(var i = 0 ; i < flsts.length; i++)
						{
							var tmpfile = flsts[i]; 
							var wjsize = (flsts[i].size/1024/1024).toFixed(2);
							tmp += '<tr id="\index1'+(i+1)+'\"><td>'+(i+1)+'</td><td>'+tmpfile.name+'<input type="hidden"  value="'+tmpfile.name+'" class="fileName"/><input type="hidden"    value="'+tmpPath[i]+'"   class="filePath"/></td><td><a onclick="delfile(this,\''+fileid+'\',\''+iptname+'\',\''+iptpath+'\')" style="cursor:pointer" herf="javascript:;" ><font color="red">删除</font></a></td></tr>';
						}
						if(tab == "" || tab == null || typeof(tab) == 'undefined'){
							tmp += '</table>';
							$("#ysc"+fileid).append(tmp);
						}else{
							$("#ysc"+fileid).find("table").append(tmp);
						}
						
						//初始化文件名称为了form表单
						initiptForForm(fileid,iptname,iptpath)
						layer.msg("上传完成")
					}
				},
				error: function(err) {  
					layer.msg("发生异常，上传失败！！")
				}
			});
			
		}else{
			$("#"+fileid).val("")
		}
		
		
	}
	
	
 	//初始化input为了form表单提交
 	function initiptForForm(fileid,iptname,iptpath){
		var fileNameLiat = "";
		$("#ysc"+fileid).find(".fileName").each(function(){
			fileNameLiat +=$(this).val()+",";
		})
		
		fileNameLiat = fileNameLiat.substring(0,fileNameLiat.length - 1);
		$("input[name='"+iptname+"']").val(fileNameLiat);
		
		var filePath = "";
		$("#ysc"+fileid).find(".filePath").each(function(){
			filePath +=$(this).val()+",";
		})
		
		filePath = filePath.substring(0,filePath.length - 1);
 		$("input[name='"+iptpath+"']").val(filePath);
 		var flag = 1;
 		$("#ysc"+fileid).find("table tr").each(function(){
 			$(this).find("td").eq(0).html(flag);
 			flag++;
 		})
		$("#ysc"+fileid).find("table tr").eq(0).find("td").eq(0).html(1);
 		if($("#ysc"+fileid).find("table tr").length == 0){
 			
 			$("#ysc"+fileid).find("table").remove();
 		}
 		$("#"+fileid).val("")
 	}
 	
 	//删除
 	function delfile(obj,fileid,iptname,iptpath){
 		$(obj).parent().parent().remove();
 		initiptForForm(fileid,iptname,iptpath);
 	}
 	
	//验证金额
	jQuery.validator.addMethod("validmoney",function(value, element){
        var returnVal = true;
        inputZ=value;
        var ArrMen= inputZ.split(".");    //截取字符串
        if(ArrMen.length==2){
            if(ArrMen[1].length>6){    //判断小数点后面的字符串长度
                returnVal = false;
            }
        }
        return returnVal;
    },"小数点后最多为六位");  
