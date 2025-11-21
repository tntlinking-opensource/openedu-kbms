	/**
	  * 根据所选省份级联市区列表
	  * shimc 市区控件id
	  * quxianmc 区县控件id
	  * shengId 省份id，根据该id级联加载市区数据
	  * shiId 市区id，若存在设为选中
	  * quxianId 区县id，为区县加载做准备，若存在则对应区县设为选中
	  */
	function findShi(shimc,quxianmc,shengId,shiId,quxianId) {
		//根据省份查找市区数据
		$.post("${base}/xtgl/tjbxx_xzdq!inputselect.action", {
			xzdqparent : shengId
		}, function(data) {
			//清空列表
			var shiList = $("#" +shimc);
			shiList.empty();
			//增加请选择
			var option = $("<option>");
			option.val("");
			option.text("==请选择==");
			option.appendTo(shiList);
			var shiqus =  eval("("+data+")");
			$.each(shiqus.list,function(){
		          var option = $("<option>");
				  option.val(this.id);
				  option.text(this.xzdqmc);
				  //设置选择
				  if (this.id == shiId) {
					  option.attr("selected", "true");
				  }
				  option.appendTo(shiList);
			});
			
		});
		findQuxian(quxianmc,shiId,quxianId)
	}
	
	/**
	  * 根据所选市区级联区县列表 
	  * quxianmc 区县控件id
	  * shiId 市区id，根据该id级联加载区县数据
	  * quxianId 区县id，若存在设为选中
	  *
	  */
	function findQuxian(quxianmc,shiId,quxianId) {
		
		//根据省份查找市区数据
		$.post("${base}/xtgl/tjbxx_xzdq!childselect.action", {
			xzdqparent : shiId
		}, function(data) {
			//清空列表
			var quxianList = $("#" +quxianmc);
			quxianList.empty();
			//增加请选择
			var option = $("<option>");
			option.val("");
			option.text("==请选择==");
			option.appendTo(quxianList);
			var quxians =  eval("("+data+")");
			$.each(quxians.list,function(){
		          var option = $("<option>");
				  option.val(this.id);
				  option.text(this.xzdqmc);
				  //设置选择
				  if (this.id == quxianId) {
					  option.attr("selected", "true");
				  }
				  option.appendTo(quxianList);
			});
			
		});
	}