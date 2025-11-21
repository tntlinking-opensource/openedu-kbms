package org.pfw.framework.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

public class AppUtil {
	/**
	 * 产生app需要的json字符串公共类
	 * 
	 * @param JsonPStr
	 * @param isSuccess
	 * @param promptinfo
	 * @param resultinfo
	 * @return
	 */
	public static String generateJson(String JsonPStr,boolean isSuccess,String promptinfo,String resultinfo,String...extStr){
		String returnStr = "";
		if(StringUtils.isEmpty(resultinfo))
		{
			resultinfo = "{}";
		}
		String extstrs = "";
		for(String tmps : extStr)
		{
			if(!StringUtils.isEmpty(tmps))
				extstrs += tmps + ",";
		}
		
		if(StringUtils.isNotEmpty(JsonPStr))
		{
			returnStr = JsonPStr+"({"+extstrs+"\"success\":\""+isSuccess+"\",\"promptinfo\":\""+promptinfo+"\",\"resultinfo\":"+resultinfo+"})";
			
		}else{
			returnStr = "{"+extstrs+"\"success\":\""+isSuccess+"\",\"promptinfo\":\""+promptinfo+"\",\"resultinfo\":"+resultinfo+"}";
			
		}
		System.out.println("===="+returnStr);
		return returnStr;
	}
	
	/** 
     * JSON字符串特殊字符处理，比如：“\A1;1300” 
     * @param s 
     * @return String 
     */  
	public static String string2Json(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 用于处理java代码中发送http请求
	 * @param url
	 * @param jsonStr
	 * @return
	 */
	public static String postJSON(String url,String jsonStr) 
    {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        
        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";
        
        try {

            StringEntity s = new StringEntity(jsonStr, "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();

            result = strber.toString();
            
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                
                    System.out.println("请求服务器成功，做相应处理");
                
            } else {
                
                System.out.println("请求服务端失败");
                
            }
            

        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }

        return result;
    }
}
