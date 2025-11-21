function moveSelectedOption(srcSelect, destSelect){
	for (var i=0; i<srcSelect.length; i++){
		if (srcSelect.options[i].selected){ 
			var op = srcSelect.options[i];
			if (!hasOption(destSelect, op)){
			   destSelect.options[destSelect.length]= new Option(op.text, op.value);
			}
		 }
	 }      
	 removeSelectedOption(srcSelect);   
     clearSelectStatus(srcSelect);
}

function removeSelectedOption(select){
	var options = select.options;
	for (var i=options.length-1; i>=0; i--){   
		if (options[i].selected){  
			options[i] = null;
		}
	}
}

function clearSelectStatus(select){
    //CLEAR
    for (var i=0; i<select.length; i++)
        select.options[i].selected = false;
}

function hasOption(select, op){ 
	for (var i=0; i<select.length; i++ ){
    		if (select.options[i].value == op.value)
            return true;
    }    
	return false;
}

function getAllOptionValue(select)
{
	var options = select.options;
	for (var i=0; i<options.length; i++)
	{	
		options[i].selected=true;
	}
	return true;
}

//����tabҳ����js
function switchTab(n,m){
    for(var i = 1; i <= m; i++){
        document.getElementById("tab_" + i).className = "";
        document.getElementById("tab_con_" + i).style.display = "none";
    }
    document.getElementById("tab_" + n).className = "on";
    document.getElementById("tab_con_" + n).style.display = "block";
}