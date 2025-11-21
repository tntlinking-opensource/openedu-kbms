<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-tags.tld" prefix="s"%>

<div style="padding-top:30px"></div>
<div id="systemFailed" style="text-align:left; padding-left:80px; padding-right:80px;">
    <s:property value="exception.message"/>
</div>
<br/>
<div style="text-align:left; padding-left:80px; padding-right:80px;display:none;">
    <s:property value="exceptionStack"/>
</div>


<s:if test="hasActionErrors()">   
    <div class="show_info">   
        <span>   
        <s:iterator value="actionErrors">   
            <s:property/>   
        </s:iterator>   
            </span>   
    </div>   
</s:if> 





