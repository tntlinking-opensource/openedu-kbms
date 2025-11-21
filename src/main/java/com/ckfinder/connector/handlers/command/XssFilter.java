package com.ckfinder.connector.handlers.command;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.pfw.framework.core.log.Log;

public class XssFilter implements Filter{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)arg0;  
        HttpServletResponse res=(HttpServletResponse)arg1;  
        
        req.setCharacterEncoding("UTF-8");
        
        String url  = req.getRequestURL().toString();
        if(url.indexOf("tonetable_mobank!bcmbjson.action") >= 0 || url.indexOf("tonetable_yewuk!save.action") >= 0 || url.indexOf("tonetable_mobank!bcmbjson.action") >= 0)  
        {
        	//白名单放行
        	arg2.doFilter(arg0, arg1);
        }else{
        	
        	//获得所有请求参数名  
            Enumeration params = req.getParameterNames();  
            String sql = "";  
            while (params.hasMoreElements()) {  
                //得到参数名  
                String name = params.nextElement().toString();  
                //System.out.println("name===========================" + name + "--");  
                //得到参数对应值  
                String[] value = req.getParameterValues(name);  
                for (int i = 0; i < value.length; i++) {  
                    sql = sql + value[i];  
                }  
            }  
            
            String referer = req.getHeader("referer");
            
            //System.out.println("============================SQL"+sql);  
            //有sql关键字，跳转到error.html  
            if (isValidate(sql) || isValidate(referer) ) {
            	res.setCharacterEncoding("GBK");
            	res.getWriter().write( ("您发送请求中的参数中含有非法字符") );
            	Log.log().info( " (IP:" + req.getRemoteAddr() + ")" + " 进行了xss攻击  ,攻击详情：" + req.getRequestURI() + ",报文："+sql);
            	return;
                //throw new IOException("您发送请求中的参数中含有非法字符："+sql);  
                //String ip = req.getRemoteAddr();  
            } else 
            {
            	arg2.doFilter(arg0, arg1);
            }  
        }
        
         
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	//效验  
    protected static boolean isValidate(String str) {  
    	String valstr = "";
    	if(StringUtils.isNotEmpty(str))
    		valstr = str;
    	valstr = valstr.toLowerCase();//统一转为小写  
        String badStr = 
        		"<script>|<script|</script|iframe|href|alert|oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged"
        				+"|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|ondragleave|ondragover|"
        				+"ondragstart|ondrop|onerror=|onerroupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp|"
        				+"onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmousedown|onmouseenter|onmouseleave|"
        				+"onmousemove|onmousout|onmouseover|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|"
        				+"onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy|onbeforecut|onbeforedeactivate|onbeforeeditocus|"
        				+"onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange|onchange|onclick|oncontextmenu|"
        				+"onpaste|onpropertychange|onreadystatechange|onreset|onresize|onresizend|onresizestart|onrowenter|onrowexit|"
        				+"onrowsdelete|onrowsinserted|onscroll|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload";
        		//+ "'|select|update|and|or|delete|insert|truncate|char|into"
        		//+ "|substr|declare|exec|master|drop|execute|"
        		//+ "union|;|--|+|,|like|//|/|%|#|*|$|@|\"|http|cr|lf|<|>|(|)";//过滤掉的sql关键字，可以手动添加  
        String[] badStrs = badStr.split("\\|");  
        for (int i = 0; i < badStrs.length; i++) {  
            if (valstr.indexOf(badStrs[i]) >= 0) {  
                return true;  
            }  
        }  
        return false;  
    }  

}
