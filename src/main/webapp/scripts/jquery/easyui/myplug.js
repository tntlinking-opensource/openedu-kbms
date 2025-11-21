(function($){

	$.fn.myplug = function(){
		alert("d");
	};
	
	$.fn.myplug1 = function(){
		return this.each(function(){
			$(this).hover(
			function(){
				$(this).addClass("Add");
			},
			function(){
				$(this).removeClass("Add");
			}
			
			);
		
		});
	};
	
	$.fn.myplug2 = function(options){
		var defaultVal = {
			Text : 'a',
			Fcolor : 'red',
			Bcolor : 'gray'
		};
		
		var obj = $.extend(defaultVal,options);
		return this.each(function(){
			var selObj = $(this);
			var oldText = selObj.text();
			var oldBcolor = selObj.css("background-color");
			var oldfcolor = selObj.css("color");
			selObj.hover(
			function(){
				selObj.text(obj.Text);
				selObj.css("background-color",obj.Bcolor);
				selObj.css("color",obj.Fcolor);
			},
			function(){
				selObj.text(oldText); 
				selObj.css("background-color", oldBcolor); 
				selObj.css("color", oldfcolor); 
			}
			);
		});
	}
	
	$.fn.treeselect = function(options){
		
		var defaultVal = {
			id : 'a',
			name : 'red'
		};
		
		var obj = $.extend(defaultVal,options);
		
		var html ="";
		html += "<selec ><option value='1'>aaaa</option></select>";
		html += "<input type='text' value='bb'>";
		
		return this.each(function(){
			alert();
		});
		
	}

})(jQuery);