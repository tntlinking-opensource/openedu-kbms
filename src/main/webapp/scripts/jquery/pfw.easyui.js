	var main_tag_div_id='main_tag_div'; 
 	var tt = $('#'+main_tag_div_id); 
	tt.tabs({
		onSelect: function (title) {
			if(tt.tabs('exists', title)){
	    		var currTab = tt.tabs('getTab', title);//'getSelected'
	        	var iframe = $(currTab.panel('options').content);
	            var src = iframe.attr('pfwurl');
	            if(src){
		            tt.tabs('update', { 
		            	tab: currTab, 
		            	options: { 
		            		content: '<iframe scrolling="auto" frameborder="0" pfwurl='+src+' src="'+src+'" style="width:100%;height:auto;"></iframe>'
		            	} 
		            });
	            }
            }
         }
     });
                
    function addTab(title,href,icon){  
        var tt = $('#'+main_tag_div_id); 
        if (tt.tabs('exists', title)){//如果tab已经存在,则选中并刷新该tab    
            tt.tabs('select', title); 
            refreshTab({tabTitle:title,url:href,divid:main_tag_div_id});
        } else {  
            if (href){  
                var content = '<iframe scrolling="auto" pfwurl='+href+' frameborder="0" src="wait.html" style="width:100%;height:auto;"></iframe>';  
            } else {  
                var content = '未实现';  
            }  
            tt.tabs('add',{  
                title:title,  
                closable:true,  
                content:content,  
                iconCls:icon||'icon-default'
            });  
        }  
    }    
    function refreshTab(cfg){  
    	var tt = $('#'+cfg.divid);  
        var refresh_tab = cfg.tabTitle?tt.tabs('getTab',cfg.tabTitle):tt.tabs('getSelected');  
        if(refresh_tab && refresh_tab.find('iframe').length > 0){  
        	var _refresh_ifram = refresh_tab.find('iframe')[0];  
        	var refresh_url = cfg.url?cfg.url:_refresh_ifram.src;  
        	//_refresh_ifram.src = refresh_url;  
        	_refresh_ifram.contentWindow.location.href=refresh_url;  
        }  
    } 
    

    function addTabParent(title,href,icon){  
        var tt=$('#'+main_tag_div_id,window.parent.document);
        if (tt.tabs('exists', title)){//如果tab已经存在,则选中并刷新该tab          
            tt.tabs('select', title);  
            refreshTabParent({tabTitle:title,url:href,divid:main_tag_div_id});  
        } else {  
            if (href){  
                var content = '<iframe scrolling="no" frameborder="0" src="'+href+'" style="width:100%;height:100%;"></iframe>';  
            } else {  
                var content = '未实现';  
            }  
            tt.tabs('add',{  
                title:title,  
                closable:true,  
                content:content,  
                iconCls:icon||'icon-default'  
            });  
        }  
    }  
    function refreshTabParent(cfg){  
    	var tt=$('#'+main_tag_div_id,window.parent.document);
        var refresh_tab = cfg.tabTitle?tt.tabs('getTab',cfg.tabTitle):tt.tabs('getSelected');  
        if(refresh_tab && refresh_tab.find('iframe').length > 0){  
        var _refresh_ifram = refresh_tab.find('iframe')[0];  
        var refresh_url = cfg.url?cfg.url:_refresh_ifram.src;  
        //_refresh_ifram.src = refresh_url;  
        _refresh_ifram.contentWindow.location.href=refresh_url;  
        }  
    }  