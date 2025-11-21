/*
* pfwpage 0.1
* Copyright (c) 2011 libing.wu
* Date: 2011-02-14
* 使用pfwpage进行数据分页
* alert be used popup.js 
*/
(function($) {
	
	$.addPage=function(f,p){
		var p = $.extend($.fn.pfwpage.defaults, p);
		_f=$(f);
		
		var t = _f.find(p.page_table);
		t.addClass("page_table");
		
		addCol=function(page_col){
			if(page_col){
				_c=$('<tr/>');
				$.map(page_col,function(n,i){
					_bth=$('<th>'+n.col_name+'</th>');
					if(n.sort){
						_bth.addClass("page_table_th_sort").click(function(e){if(n.sort){sort(n.property,'asc');}});
					}
					_c.append(_bth);
				});
				_ch=$('<thead/>').append(_c);
				t.prepend(_ch);
			}
		};
		
		addCol(p.page_col);
		
		$("span[class='expander']").click(function(){
			$(this).parent().parent().toggleClass("page_tr_selected");
			alert($(this).size());
		});
		
		t.find("tr").each(function(){
			$(this).find("td").eq(0).each(function(){
				$(this).click(function(){
					$(this).parent().toggleClass("page_tr_selected");
				});
			});
		});
		
		t.find("tr").each(function(){$(this).find("td").eq(0).each(function(){$(this).click(function(){$(this).parent().toggleClass("page_tr_selected");});});});
		
		t.find("tr").each(function(){$(this).click(function(e){$(this).toggleClass("page_tr_selected");})});
		
		onSearch = function(e){$(p.page_table_search).toggle();};
		reLoad = function(e){_f.submit();};
		onButton=function(url){_f.attr('action',url);_f.submit();};
		getSelectData=function(){var d=[];$(".page_tr_selected",_f).each(function(){d[d.length]=this.title;});return d;};
		_show=function(c,t){
			var pop=new Popup({ contentType:4,isReloadOnClose:false,width:340,height:80});
            pop.setContent("title",t?t:"提示");
            pop.setContent("alertCon",c?c:"对话框的内容");
            pop.build();
            pop.show();
		};
		
		buttonInfo=function(_url,_par,_stag,_extn,_extv,_extn1,_extv1,_cfstr){
			if(_par){
				_sd=getSelectData();
				if(_stag){
					if(!_sd||_sd.length<0){
						_show("请选择一条需要操作的数据！",'提示');
						return ;
					}else{

						var reg = new RegExp(_stag);
						if(reg.test(_sd.length)){
							if(_cfstr)
							{
								if(!confirm(_cfstr))
									return;
							}
							if(_sd.length==1){
								_url+="?"+_par+"="+_sd[0];
							}else{
								_url+="?"+_par+"="+_sd[0];
								for(var vp=1;vp<_sd.length;vp++){
									_url+="&"+_par+"="+_sd[vp];
								}
							}
							if(_extn && _extv)
								_url += "&"+_extn+"="+_extv;
							if(_extn1 && _extv1)
								_url += "&"+_extn1+"="+_extv1;
						}else{
							_show("请选择需要操作的数据！",'提示');
							return ;
						}
					}
				}
			}
			location.href=_url;
			//onButton(_url);
		};
		
		
		_h = $('<div/>');
		if(p.page_table_search){
			$(p.page_table_search).addClass("page_search_table");
			//_find = function(){return $('<input type="button" class="page_table_botton" value="find"/>').click(onSearch);};
			//_h.append(_find);
		}
		if(p.page_button){
			$.map(p.page_button,function(n,i){
				var _par=n.paramName;
				var _stag=n.selectNum;
				var _url = n.b_url;
				var _extn = n.extparamName;
				var _extv = n.extparamVame;
				var _extn1 = n.extparamName1;
				var _extv1 = n.extparamVame1;
				var _cfstr = n.confirmStr;
				_h.append($('<input type="button" class="page_table_botton"  value="'+n.b_name+'"/>')
				.click(function(e){
					if(n.b_function){
						n.b_function();
					}else{
						buttonInfo(_url,_par,_stag,_extn,_extv,_extn1,_extv1,_cfstr);
					}
				}));
			});
		};
		
		t.before(_h);
		delete _h;		
		
		jumpPage = function(_pageNo){
			_f.find('input[name=\"'+p.page_pageNo_Name+'\"]').val(_pageNo);
			_f.append('<input type="hidden" name="fyclick" value="Y"/>');
			_f.submit();
		};
		sort = function(orderBy,defaultOrder){
			_ob = _f.find('input[name="page.orderBy"]');
			_o = _f.find('input[name="page.order"]');
			if (_ob.val() == orderBy) {
				if (_o.val() == "") {
					_o.val(defaultOrder);
				}
				else if (_o.val() == "desc") {
					_o.val("asc");
				}
				else if (_o.val() == "asc") {
					_o.val("desc");
				}
			}
			else {
				_ob.val(orderBy);
				_o.val(defaultOrder);
			}
			_f.submit();
		};
		
		if(p.page_able!=false){
			_a = $('<div/>').addClass("page_table_footer");
			_a.append('<input type="hidden" name="page.orderBy" value="'+p.page_orderBy+'"/>')
			.append('<input type="hidden" name="page.order" id="order" value="'+p.page_order+'"/>')
			.append('当前<b class="fontred"><input name="page.pageNo" type="hidden" value="'+p.page_pageNo+'"/>'+p.page_pageNo+'</b>/'+p.page_totalPages+'['+p.page_totalCount+']页&nbsp;&nbsp;&nbsp;&nbsp;');
			_a.append($('<a href="#">首页</a>').addClass("page_table_footer_a").click(function(e){jumpPage(1)}));
			if(p.page_pageNo>1){
				_a.append($('<a href="#">上一页</a>').addClass("page_table_footer_a").click(function(e){jumpPage(p.page_pageNo-1)}));
	        }
	       
	        if(p.page_pageNo<p.page_totalPages){
	        	_a.append($('<a href="#">下一页</a>').addClass("page_table_footer_a").click(function(e){jumpPage(p.page_pageNo+1)}));
	        }
	        _a.append($('<a href="#">末页</a>').addClass("page_table_footer_a").click(function(e){jumpPage(p.page_totalPages)}))
	        	.append('&nbsp;&nbsp;&nbsp;&nbsp;每页显示的记录数');
	        _s = $('<select name="'+p.page_pageSize_Name+'">');
	         switch(p.page_pageSize){
	        	case 5: 
	        		_s.append('<option value="5" selected>5</option>')
	        		.append('<option value="10">10</option>')
	        		.append('<option value="15">15</option>')
	        		.append('<option value="20">20</option>')
	        		.append('<option value="50">50</option>');
	        		break;
	        	case 10:
	        		_s.append('<option value="5">5</option>')
	        		.append('<option value="10" selected>10</option>')
	        		.append('<option value="15">15</option>')
	        		.append('<option value="20">20</option>')
	        		.append('<option value="50">50</option>');
	        		break;
	        	case 15:
	        		_s.append('<option value="5">5</option>')
	        		.append('<option value="10">10</option>')
	        		.append('<option value="15" selected>15</option>')
	        		.append('<option value="20">20</option>')
	        		.append('<option value="50">50</option>');
	        		break;
	        	case 20:
	        		_s.append('<option value="5">5</option>')
	        		.append('<option value="10">10</option>')
	        		.append('<option value="15">15</option>')
	        		.append('<option value="20" selected>20</option>')
	        		.append('<option value="50">50</option>');
	        		break;
	        	case 50:
	        		_s.append('<option value="5">5</option>')
	        		.append('<option value="10">10</option>')
	        		.append('<option value="15">15</option>')
	        		.append('<option value="20">20</option>')
	        		.append('<option value="50" selected>50</option>');
	        		break;
	        }
	        _a.append(_s);
	        _a.append($('<a href="#">go</a>').addClass("page_table_footer_a").click(reLoad));
			t.after(_a);
			delete _a;
		}
	};
	var docloaded = false;
	$(document).ready(function () {docloaded = true} );
	$.fn.pfwpage = function(p) {
		return this.each( function() {
				if (!docloaded){
					$(this).hide();
					var t = this;
					$(document).ready(
						function (){
						$.addPage(t,p);
						}
					);
				} else {
					$.addPage(this,p);
				}
			});
	}; 
	
	var pfw_encode_uri = function(text){
		if (encodeURIComponent)
			return encodeURIComponent(text);
		else
			return escape(text);
	};
	$.fn.pfwpage.defaults = {
		sumbitfunction:null,
		_del_url:null,
		_update_url:null,
		pageable:true,
		
		page_table:null,
		defaultOrderBy:'id',
		defaultOrder:'desc',
		page_orderBy:null,
		page_order:null,
		page_pageNo_Name:'page.pageNo',
		page_pageNo:1,
		page_pageSize_Name:'page.pageSize',
		page_pageSize:10,
		page_totalPages:0,
		page_totalCount:0
	};
})(jQuery); 