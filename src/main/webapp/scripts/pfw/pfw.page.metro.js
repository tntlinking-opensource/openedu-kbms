/*
 * pfwpage 0.1
 * Copyright (c) 
 * Date: 2011-02-14
 * 使用pfwpage进行数据分页
 * alert be used popup.js 
 */
(function($) {
	$.addPage = function(f, p) {
		var p = $.extend($.fn.pfwpage.defaults, p);
		_f = $(f);
		var t = _f.find(p.page_table);
		
		if(!p.page_nochbox)
		{
			$(p.page_table+' tr').prepend('<td><input type="checkbox" class="tdcheckbox"></td>');
		}
		
		
		var pagemenubutton = $("#pagemenubutton");//操作按钮栏
		
		t.addClass("table ");
					
		addCol = function(page_col) {
			if (page_col) {
				_c = $('<tr style="height: 50px;color:#666"/>');
				
				if(!p.page_nochbox){
					var sellAllChk = $('<th><input type="checkbox" id="sellAllChk"></th>').click(function(e){
						var cktmp = $("#sellAllChk").attr("checked");
						if(cktmp && typeof(cktmp) != "undefined")
						{
							selectall();
						}else{
							selectnone();
						}
					});
					_c.append(sellAllChk);
				}
				
				$.map(page_col, function(n, i) {
					if(n.width)
					{
						_bth = $('<th style="vertical-align: middle;font-size: 15px;" width="'+n.width+'">' + n.col_name + '</th>');
					}else{
						_bth = $('<th style="vertical-align: middle;font-size: 15px;">' + n.col_name + '</th>');
					}
					
					if(n.height)
					{
						_c.attr("height",n.height);
					}
					
					if (n.sort) {
						_bth.addClass("sorting").click(function(e) {
							if (n.sort) {
								sort(n.property, 'asc');
							}
						});
					}
					_c.append(_bth);
				});
				//_ch = $('<thead style="background-color: #E8F5F7" />').append(_c);
				_ch = $('<thead style="background-color: #eeeeee" />').append(_c);
				t.prepend(_ch);
			}
		};
		addCol(p.page_col);
		t.find("tr").each(function() {
			$(this).find("td").eq(0).each(function() {
				$(this).click(function() {
					var t1 = $(this).parent().parent().parent().attr("class");
					
					if(t1 != "undefined" && t1.indexOf("treeTable") >= 0)
					{
						$(this).parent().toggleClass("page_tr_selected");
					}
				});
			});
		});
		t.find("tr[titl]").each(function() {
			$(this).click(function(e) {
				if($(this).attr("class") && $(this).attr("class").indexOf("page_tr_selected") >= 0)
				{
					$(this).removeClass("page_tr_selected");
					$(this).children("td:first-child").find("input[class=tdcheckbox]").removeAttr("checked");
					$(this).children("td:first-child").find("input[class=tdcheckbox]").parent().removeClass();
				}else{
					$(this).addClass("page_tr_selected");
					$(this).children("td:first-child").find("input[class=tdcheckbox]").attr("checked","checked");
		 			$(this).children("td:first-child").find("input[class=tdcheckbox]").parent().addClass("checked");
				}
				
			})
		});
		
				
		onSearch = function(e) {
			$(p.page_table_search).toggle();
		};
		selectall = function(e) {
	 		$("tr[titl]").each(function(){
	 			$(this).addClass("page_tr_selected");
	 		});
	 		$("input[class=tdcheckbox]").each(function(e){
	 			$(this).attr("checked","checked");
	 			$(this).parent().addClass("checked");
	 		});
		};
		selectnone = function(e) {
	 		$("tr[titl]").each(function(){
	 			$(this).removeClass("page_tr_selected");
	 		});		
	 		$("input[class=tdcheckbox]").each(function(e){
	 			$(this).removeAttr("checked");
	 			$(this).parent().removeClass();
	 		});
		};
		reLoad = function(e) {
			_f.submit();
		};
		onButton = function(url) {
			_f.attr('action', url);
			_f.submit();
		};
		getSelectData = function() {
			var d = [];
			$(".page_tr_selected", _f).each(function() {
				if ($(this).attr("titl") != "" && typeof ($(this).attr("titl")) != "undefined") {
					d[d.length] = $(this).attr("titl");
				}
			});
			return d;
		};
		_show = function(c, t) {
			alert(c);
		};
		buttonInfo = function(_url, _par, _stag, _extn, _extv, _extn1, _extv1,
				_cfstr,_interbefore,_opttype) {
			
			var checkVal = "";
			
			if (_par) {
				_sd = getSelectData();
				if (_stag) {
					if (!_sd || _sd.length < 0) {
						_show("请选择一条需要操作的数据！", '提示');
						return;
					} else {
						var reg = new RegExp(_stag);
						if (reg.test(_sd.length)) {
							if (_cfstr) {
								if (!confirm(_cfstr))
									return;
							}
							if(typeof(_par) != "undefined")
							{
								if(_par == "id")
								{
									_url += "?" + _par + "=" + _sd[0];
									
									if (_extn && _extv) {
										_url += "&" + _extn + "=" + _extv;
									}
									if (_extn1 && _extv1) {
										_url += "&" + _extn1 + "=" + _extv1;
									}
							
							}else if( _par == "checks" ){
								var tmpStr = "";
								for( var vp = 0; vp < _sd.length; vp++)
								{
									tmpStr += _sd[vp] + ",";
								}
								if(tmpStr != "")
								{
									tmpStr = tmpStr.substring(0,tmpStr.length-1);
								}
								
								checkVal = tmpStr;
								
								var tmpObj = _f.find('input[name="hidfy"]');
								tmpObj.val(tmpStr);
								
								if (_extn && _extv) {
									_url += "?" + _extn + "=" + _extv;
								}
								if (_extn1 && _extv1) {
									_url += "&" + _extn1 + "=" + _extv1;
								}
							}else{
								alert("非法传值，非法操作");
								return;
							}
						  }
						} else {
							_show("请选择合法需要操作的数据！", '提示');
							return;
						}
					}
				}
			}
			
			if(_interbefore)
			{
				var jsStr=_interbefore+"()";  
				var returnVal = eval(jsStr);
				if(typeof(returnVal) == "undefined")
				{
					_show("错误，interBefore函数没有返回值", '提示');
					return;
				}
				if(typeof(returnVal) == "boolean")
				{
					if(returnVal != true)
					{
						//_show("interBefore验证不通过", '提示');
						return;
					}
				}else{
					_show(returnVal, '提示');
					return;
				}
			}
			
			if(_opttype)
			{
				if(typeof(_opttype) == "undefined")
				{
					_show("没有定义操作类型");
					return;
				}
				//---------------------------------------打印调用
				if(_opttype == "print")
				{
					if(!_url&&_par!="checks"){
						alert("非法传值，非法操作");
						return;
					}else{
						  if (!_sd || _sd.length < 0) {
					           _show("请选择一条需要操作的数据！", '提示');
					           return;
				           }else{
					          var form = $("<form>");  
                              form.attr('style','display:none');  
                              form.attr('target','');
                              form.attr('method','post');  
                              form.attr('action',_url); 
                              
                              $('body').append(form);
                              var input1="";
                              for(var i = 0; i < _sd.length; i++)
							     {
                            	   var input1 = $('<input>');  
                                   input1.attr('type','hidden');  
                                   input1.attr('name',_par);
                                   input1.attr('value',_sd[i]); 
                                   form.append(input1); 
							     }   
                              
                              form.submit(); 
                              form.remove(); 
					          return;
				        }
					}  
				}
				//---------------------------------------导出调用
				if(_opttype == "export")
				{
			          var form1 = $("<form>");  
                      form1.attr('style','display:none');  
                      form1.attr('target','');
                      form1.attr('method','post');  
                      form1.attr('action',_url); 
                      
                     _f.clone().prependTo(form1);
                      
                      $('body').append(form1);
                      
                      form1.submit(); 
                      form1.remove(); 
                      
			          return;
				}
				
				//---------------------------------------删除调用
				if(_opttype == "delete")
				{
					if(checkVal != "")
					{
						$.ajax({
						   type: "POST",
						   url: _url,
						   data: "hidfy="+checkVal,
						   success: function(msg){
						     alert( msg );
						     _f.submit();
						   }
						});
					}else{
						alert("请选择删除的数据");
						return;
					}
                      
			          return;
				}
				
			}

			_f.attr("action",_url);
			_f.submit();
			
			
		};
		
		_h = $('<div/>');

		if (p.page_button) {
			$
					.map(
							p.page_button,
							function(n, i) {
								var _par = n.paramName;
								var _extn = n.extparamName;
								var _extv = n.extparamValue;
								var _extn1 = n.extparamName1;
								var _extv1 = n.extparamValue1;
								var _cfstr = n.confirmStr;
								var _stag = n.selectNum;
								var _url = n.b_url;
								var _interbefore = n.interFunction;
								var _print=n.printFunction;
								var _disp=n.disp;
								var _expt=n.expt;
								var _bclass = n.bclass;
								var _bicon = n.bicon;
								var _opttype = n.opttype;
								//判断是否显示该按钮
								if(typeof (_disp) != "undefined" && _disp == "0")
								{}else
								{
									pagemenubutton
											.append("&nbsp;")
											.append(
													//$('&nbsp;<button class="'+_bclass+'" > <i class="'+_bicon+'" > </i>'+n.b_name+' </button> '
													$('<a class="'+_bclass+'" href="javascript:;" ><i class="'+_bicon+'" > </i>'+n.b_name+'</a>')
													.click(
															function(e) {
																if (n.b_function) {
																	n.b_function()
																} else {
																	buttonInfo(
																			_url,
																			_par,
																			_stag,
																			_extn,
																			_extv,
																			_extn1,
																			_extv1,
																			_cfstr,
																			_interbefore,
																			_opttype
																			)
																}
															}));
								}

							});
		}
		if (p.page_table_search) {
			//$(p.page_table_search).addClass("page_search_table");
			_selAll = function(){return $('<a class="btn" href="javascript:;" id="quanxuan"><i class="icon-chevron-down" > </i>全选</a>').click(selectall);};
			_selNon = function(){return $('<a class="btn" href="javascript:;" id="chongxuan"><i class="icon-chevron-up" > </i>重选</a>').click(selectnone);};
			pagemenubutton.append("&nbsp;").append(_selAll);
			pagemenubutton.append("&nbsp;").append(_selNon);
		}
		//t.before(_h);
		//delete _h;
		jumpPage = function(_pageNo) {
			_f.find('input[name=\"' + p.page_pageNo_Name + '\"]').val(_pageNo);
			_f.append('<input type="hidden" name="fyclick" value="Y"/>');
			_f.submit();
		};
		sort = function(orderBy, defaultOrder) {
			_ob = _f.find('input[name="page.orderBy"]');
			_o = _f.find('input[name="page.order"]');
			if (_ob.val() == orderBy) {
				if (_o.val() == "") {
					_o.val(defaultOrder);
				} else if (_o.val() == "desc") {
					_o.val("asc");
				} else if (_o.val() == "asc") {
					_o.val("desc");
				}
			} else {
				_ob.val(orderBy);
				_o.val(defaultOrder);
			}
			_f.submit();
		};
		if (p.page_table != false) {
			_a = $('<div id="fyfoot" style="display:flex;font-size: 14px;" />').addClass("");
			_a
					.append(
						'<input type="hidden" name="hidfy"/>')
					.append(
							'<input type="hidden" name="page.orderBy" value="' + p.page_orderBy + '"/>')
					.append(
							'<input type="hidden" name="page.order" id="order" value="' + p.page_order + '"/>')
					.append(
							'<div style="width:40%;padding-left: 10px;"><span style="margin-right:15px;">共'+p.page_totalCount+'条</span>第<input name="page.pageNo" type="hidden" value="'
									+ p.page_pageNo + '"/>' + p.page_pageNo
									+ '/' + p.page_totalPages + '页</div>'
									);
			//_a.append($('<div style="width:30%" />')).addClass("");
			_b = $('<div style="width: 60%;text-align: right;margin-right: 15px;" />').addClass("");
			_b1 = $('<a href="#">&nbsp;</a>').click(reLoad);
			_b.append(_b1);
			
			_b.append($('<a href="#">首页</a>').addClass("page_table_footer_a")
					.click(function(e) {
						jumpPage(1)
					}));
			if (p.page_pageNo > 1) {
				_b.append($('<a href="#">上一页</a>').addClass(
						"page_table_footer_a").click(function(e) {
					jumpPage(p.page_pageNo - 1)
				}));
			}
			if (p.page_pageNo < p.page_totalPages) {
				_b.append($('<a href="#">下一页</a>').addClass(
						"page_table_footer_a").click(function(e) {
					jumpPage(p.page_pageNo + 1)
				}));
			}
			_b.append(
					$('<a href="#">末页</a>').addClass("page_table_footer_a")
							.click(function(e) {
								jumpPage(p.page_totalPages)
							})).append('&nbsp;&nbsp;&nbsp;&nbsp;');
			_s = $('<select style="width:93px;margin:0px;" name="' + p.page_pageSize_Name + '">').change(function(){
				_b1.trigger("click");
			});
			switch (p.page_pageSize) {
			case 15:
				_s.append('<option value="15" selected>15条/页</option>')
				  .append('<option value="50">50条/页</option>')
				  .append('<option value="100">100条/页</option>')
				  .append('<option value="200">200条/页</option>')
				  .append('<option value="500">500条/页</option>');
				break;
			case 50:
				_s.append('<option value="15">15条/页</option>')
				  .append('<option value="50" selected>50条/页</option>')
				  .append('<option value="100">100条/页</option>')
				  .append('<option value="200">200条/页</option>')
				  .append('<option value="500">500条/页</option>');
				break;
			case 100:
				_s.append('<option value="15">15条/页</option>')
				  .append('<option value="50">50条/页</option>')
				  .append('<option value="100" selected>100条/页</option>')
				  .append('<option value="200">200条/页</option>')
				  .append('<option value="500">500条/页</option>');
				break;
			case 200:
				_s.append('<option value="15">15条/页</option>')
				  .append('<option value="50">50条/页</option>')
				  .append('<option value="100">100条/页</option>')
				  .append('<option value="200" selected>200条/页</option>')
				  .append('<option value="500">500条/页</option>');
				break;
			case 500:
				_s.append('<option value="15">15条/页</option>')
				  .append('<option value="50">50条/页</option>')
				  .append('<option value="100">100条/页</option>')
				  .append('<option value="200">200条/页</option>')
				  .append('<option value="500" selected>500条/页</option>');
			}
			_b.append(_s);
			_b.append($('').addClass("page_table_footer_a")
					.click(reLoad));
			_a.append(_b);
			t.after(_a);
			
			delete _a;
		}
	};
	var docloaded = false;
	$(document).ready(function() {
		docloaded = true;
	});
	$.fn.pfwpage = function(p) {
		return this.each(function() {
			if (!docloaded) {
				//$(this).hide();
				var t = this;
				$(document).ready(function() {
					$.addPage(t, p);
				});
			} else {
				$.addPage(this, p);
			}
		});
	};
	var pfw_encode_uri = function(text) {
		if (encodeURIComponent)
			return encodeURIComponent(text);
		else
			return escape(text);
	};
	$.fn.pfwpage.defaults = {
		sumbitfunction : null,
		_del_url : null,
		_update_url : null,
		pageable : true,
		page_nochbox : false,
		page_table : null,
		defaultOrderBy : 'id',
		defaultOrder : 'desc',
		page_orderBy : null,
		page_order : null,
		page_pageNo_Name : 'page.pageNo',
		page_pageNo : 1,
		page_pageSize_Name : 'page.pageSize',
		page_pageSize : 15,
		page_totalPages : 0,
		page_totalCount : 0
	};
})(jQuery);